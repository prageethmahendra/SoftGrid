package it.illinois.adsc.ema.common.file;

import it.illinois.adsc.ema.common.webservice.TransferResults;

import java.io.*;

/**
 * Created by prageethmahendra on 2/10/2016.
 * <p>
 * /**
 * This class is the main operation controller class
 * Created by prageeth_2 on 1/31/2016.
 */
public class FileProcessor {
//    private static final String SPLIT_STRING = "[{}().,;:\"!\t\r\n-=+/\\*$#%^|]";

    /**
     * this methbod move input stream to the output stream and close the streams
     * @param inputStream
     * @param outputStream
     * @return Process results which include maximum, minimum and median words
     */
    public static TransferResults downloadFile(InputStream inputStream, OutputStream outputStream) {
        TransferResults counterResults = new TransferResults();
        if (outputStream == null || inputStream == null) {
            counterResults.setSuccess(false);
            return counterResults;
        }
        try {
            byte[] buffer = new byte[1024];
            int noOfBytes = 0;
            System.out.println("Copying file using streams");
            // read bytes from source file and write to destination file
            while ((noOfBytes = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, noOfBytes);
            }
            counterResults.setSuccess(true);
        } catch (IOException e) {
            e.printStackTrace();
            counterResults.setSuccess(false);
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return counterResults;
    }

    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream("C:\\EMA\\Demo\\smartpower\\SmartPower\\log\\CCLog.log.0");
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\EMA\\Demo\\smartpower\\SmartPower\\log\\temp");
            downloadFile(fileInputStream, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
