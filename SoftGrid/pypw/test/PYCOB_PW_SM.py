from __future__ import division

import os
import sys
import csv
import shutil
import time
import datetime

csvpath = ""
resultMainpath = ""
lib_path = os.path.abspath('..\\pypw')
experimentNo = 0
deviceCountIncrementAmmount = 10
duration = 60.0
sys.path.append(lib_path)
generate = 1
summery = 1
plot = 0
experimentCount = 100
startTime = datetime.datetime.now()
import pypwconst
import pypw


def writeToFile(fileName, Header, Data):
    with open(csvpath + fileName, 'w') as target:
        column = str(Header)
        column = column[1:]
        column = column[:-1]
        target.write(str(column))
        target.write("\n")
        for dataline in Data:
            data = str(dataline)
            data = data[1:]
            data = data[:-1]
            target.write(data)
            target.write("\n")


def processFrequencyCSV(type):
    if (summery is 0):
        return
    resultPath = logPath + "ViolationCount.csv"
    with open(resultPath, "a") as writeFile:
        for file in os.listdir(csvpath):
            filename = str(file)
            isvalid = ("BUS_FREQ.csv" in filename and (type is "OF" or type is "UF"))
            isvalid = isvalid or (
            "BUS_VOLT.csv" in filename and (type is "UV5" or type is "UV20" or type is "OV5" or type is "OV20"))
            isvalid = isvalid or ("BRANCH_MWTO.csv" in filename and type is "BranchLimit")
            # isvalid = isvalid or "LOAD_MW.csv" in filename
            if isvalid:
                with open(csvpath + filename, 'rb') as f:
                    reader = csv.reader(f)
                    header = reader.next()
                    i = 0
                    for column in header:
                        if i is not 0:
                            with open(csvpath + filename, 'rb') as tempF:
                                tempReader = csv.reader(tempF)
                                tempReader.next()  # discard the header
                                value = ""
                                for row in tempReader:
                                    x = time.strptime(row[0], '%S.%f')
                                    newTime = startTime + datetime.timedelta(seconds=x.tm_sec)
                                    violatedValue = int(float(row[i]) * 100) / 100
                                    # if type is "UOF" and (float(str(row[i])) <= 59.5 or float(str(row[i])) >= 60.5):
                                    #     value = type + "," + str(row[i]) + "," +column +",Time" +"," + row[0]
                                    #     break
                                    if type is "UF" and float(str(row[i])) <= 59.5:
                                        value = "Under Frequency :" + str(violatedValue) + ",Time :" + newTime.strftime(
                                            '%H:%M:%S:%f') + "," + column
                                        break
                                    if type is "OF" and float(str(row[i])) >= 60.5:
                                        value = "Over Frequency :" + str(violatedValue) + ",Time :" + newTime.strftime(
                                            '%H:%M:%S:%f') + "," + column
                                        break
                                    if type is "BranchLimit" and float(str(row[i])) >= 100.0:
                                        value = "Branch Limit :" + str(violatedValue) + ",Time :" + newTime.strftime(
                                            '%H:%M:%S:%f') + "," + column
                                        break
                                    if type is "UV5" and float(str(row[i])) <= 0.95:
                                        value = "Under Voltage(5) :" + str(
                                            violatedValue) + ",Time :" + newTime.strftime('%H:%M:%S:%f') + "," + column
                                        break
                                    if type is "OV5" and float(str(row[i])) >= 1.05:
                                        value = "Over Voltage(5) :" + str(violatedValue) + ",Time :" + newTime.strftime(
                                            '%H:%M:%S:%f') + "," + column
                                        break
                                    if type is "UV20" and float(str(row[i])) <= 0.8:
                                        value = "Under Voltage(20) :" + str(
                                            violatedValue) + ",Time :" + newTime.strftime('%H:%M:%S:%f') + "," + column
                                        break
                                    if type is "OV20" and float(str(row[i])) >= 1.20:
                                        value = "Over Voltage(20) :" + str(
                                            violatedValue) + ",Time :" + newTime.strftime('%H:%M:%S:%f') + "," + column
                                        break

                                if value != "":
                                    value = value + "\n"
                                    createdTime = lambda: int(round(time.time() * 1000))
                                    writeFile.write(str(createdTime()) + "," + value)
                                    print value
                        i = i + 1
                if type is "UF" or type is "OV20" or type is "BranchLimit":
                    os.remove(csvpath + filename)


PW_FILE = "%s\\%s" % (os.getcwd(), "..\\..\\casefiles\\37Bus\\GSO_37Bus_dm.PWB")
loadrank = []
mainExperimentPath = "%s\\%s" % (os.getcwd(), "..\\..\\auxFile\\")
logPath = "%s\\%s" % (os.getcwd(), "..\\..\\log\\")
archiveDir = "%s\\%s" % (os.getcwd(), "..\\..\\auxFileBkp\\")
stateFile = "%s\\%s" % (os.getcwd(), "..\\..\\casefiles\\state.file")
print sys.argv[1]
count = 0
# startTime = datetime.datetime.now()#.strftime('%H:%M:%S:%f')
# startTime = startTime + datetime.timedelta(seconds=10)

sa = pypw.SimAuto()
sa.Connect(sys.argv[1])
TEMP_PW_FILE = PW_FILE[:-4] + "_Monitor.PWB"
if (os.path.exists(TEMP_PW_FILE)):
    os.remove(TEMP_PW_FILE)
shutil.copy(PW_FILE, TEMP_PW_FILE)
while (1):
    count = count + 1
    if (not os.path.exists(stateFile)):
        print "Python Exiting...!"
        break
    for dirs in os.listdir(mainExperimentPath):
        if (not os.path.exists(stateFile)):
            print "Python Exiting...!"
            break
        # try:
        filename = str(dirs)
        dirName = str(dirs) + "\\"
        if filename[:13].find("ContingencyAux") and dirName.find("result") == -1 and dirName.find("csv") == -1:
            csvpath = mainExperimentPath + dirName + "csv\\"
            resultMainpath = mainExperimentPath + dirName + "result\\"
            if count == 1:
                resultPath = logPath + "ViolationCount.csv"
                with open(resultPath, "w") as writeFile:
                    print "RESET : " + resultMainpath
            for file in os.listdir(mainExperimentPath + dirName):
                filename = str(file)
                if filename.find("result") == -1 and \
                                filename.find("csv") == -1 and \
                                generate == 1 and \
                                filename[-3:].find("pwb") == -1 and \
                                filename[-3:].find("PWB") == -1 and \
                                filename.find("OPEN_ALL.aux") == -1:
                    print filename
                    # AUX_FILE = "%s\\%s"%(os.getcwd(), mainExperimentPath+dirName+file)
                    AUX_FILE = "%s\\%s" % (mainExperimentPath, dirName + file)
                    TEMP_PW_FILE = AUX_FILE[:-4] + ".PWB"
                    if not os.path.exists(TEMP_PW_FILE):
                        print "File " + TEMP_PW_FILE + " Does Not Exists...!"
                        break
                    sa.OpenCase(TEMP_PW_FILE)
                    print "loading aux file..."
                    startTime = datetime.datetime.now()
                    print AUX_FILE
                    print sa.ProcessAuxFile(AUX_FILE)
                    print "runing contingency..."
                    print sa.TSRunAndPause("OPEN_ALL", "10")
                    # Get average system frequency
                    # FieldList = ["Bus %d | Frequency" % 1]
                    # get the header and data for the generator fieldlist
                    # Header, Data = sa.TSGetContingencyResults(pypw.TS_DEFAULT_CTG,FieldList, str(Time[i]), str(Time[i+1]))
                    # file = file[:-4]
                    print "loading Bus frequency data..."
                    FieldList = ["Plot \'BUS_FREQ\'"]
                    Header, Data = sa.TSGetContingencyResults("OPEN_ALL", FieldList)
                    writeToFile(file + "_BUS_FREQ.csv", Header, Data)

                    print "loading Bus voltage data..."
                    FieldList = ["Plot \'BUS_VOLT\'"]
                    Header, Data = sa.TSGetContingencyResults("OPEN_ALL", FieldList)
                    writeToFile(file + "_BUS_VOLT.csv", Header, Data)

                    # print "loading Bus branch MW at To bus data..."
                    # FieldList = ["Plot \'BRANCH_MWTO\'"]
                    # Header, Data = sa.TSGetContingencyResults("OPEN_ALL", FieldList)
                    # writeToFile(file + "_BRANCH_MWTO.csv", Header, Data)

                    # print "loading Bus Load MW data..."
                    # FieldList = ["Plot \'LOAD_MW\'"]
                    # Header, Data = sa.TSGetContingencyResults("OPEN_ALL", FieldList)
                    # writeToFile(file + "_LOAD_MW.csv", Header, Data)

                    # print "loading Bus Generator MW data..."
                    # FieldList = ["Plot \'GEN_MW\'"]
                    # Header, Data = sa.TSGetContingencyResults("OPEN_ALL", FieldList)
                    # writeToFile(file + "_GEN_MW.csv", Header, Data)

                    shutil.copy(mainExperimentPath + dirName + filename, archiveDir + dirName + filename)
                    os.remove(mainExperimentPath + dirName + filename)
                    os.remove(mainExperimentPath + dirName + filename[:-4] + ".PWB")
                    sa._SaveState()
                    sa.SaveCase(TEMP_PW_FILE)

            processFrequencyCSV("OF")
            processFrequencyCSV("UF")
            processFrequencyCSV("BranchLimit")
            processFrequencyCSV("UV5")
            processFrequencyCSV("UV20")
            processFrequencyCSV("OV5")
            processFrequencyCSV("OV20")
            # processFrequencyCSV("Incomplete")
            # processFrequencyCSV("UnservedLoad")
    print "new iteration"
    time.sleep(0.01)
if plot is 1:
    generateSummeryComparisonPlots()
