package it.illinois.adsc.ema.control.ied.pw;

import it.illinois.adsc.ema.control.center.ControlCenterClient;
import it.illinois.adsc.ema.control.center.ControlCenterClient_Attacker;
import it.illinois.adsc.ema.control.center.ControlCenterContext;
import it.illinois.adsc.ema.control.conf.*;
import it.illinois.adsc.ema.control.conf.generator.ConfigGenerator;
import it.illinois.adsc.ema.control.ied.StatusHandler;
import it.illinois.adsc.ema.control.proxy.ProxyClientFactory;
import it.illinois.adsc.ema.softgrid.common.ConfigUtil;
import org.openmuc.openiec61850.ServiceError;

import java.io.IOException;
import java.util.*;

/**
 * Created by prageethmahendra on 15/2/2016.n
 */
public class IEDServerFactory {
    final static List<IEDWorkerThread> iedWorkerThreads = new ArrayList<IEDWorkerThread>();
    public static final HashMap<String, Integer> proxyIpPorts = new HashMap<String, Integer>();
    public static ControlCenterClient controlCenterClient = null;

    public static void createAndStartIEDServer(String confFileName, String serverType, boolean consoleInteractive) throws ServiceError, IOException {
        PWModelType pwModelType = null;
        if (confFileName == null) {
            throw new IOException("IED configuration file is null.1");
        }
        pwModelType = ConfigGenerator.deserializeConfigXml(confFileName);
        ConfigGenerator.generateCIDFile(pwModelType);
        if (pwModelType == null) {
            throw new IOException("Unable to open the configuration file : " + confFileName);
        } else {
            final List<PWModelDetails> iedConfigDetails = new ArrayList<PWModelDetails>();
            List<IEDWorkerThread> workerThreads = new ArrayList<IEDWorkerThread>();
            final IEDThreadPool iedThreadPool = new IEDThreadPool();
            proxyIpPorts.clear();
            for (ProxyNodeType proxyNodeType : pwModelType.getProxyNode()) {
                for (IedNodeType iedNodeType : proxyNodeType.getIedNode()) {
                    if (!iedNodeType.getActive().equalsIgnoreCase("True")) {
                        System.out.println("Inactive IED Detected..!");
                        continue;
                    }
                    PWModelDetails pwModelDetails = new PWModelDetails(iedNodeType.getPWCaseFileName());
                    pwModelDetails.setModelNodeReference(iedNodeType.getReference());
                    pwModelDetails.setDeviceName(iedNodeType.getDevice());// from bus
                    ParametersType parametersType = iedNodeType.getParameters();
                    if (parametersType != null) {
                        for (KeyType keyType : parametersType.getKey()) {
                            pwModelDetails.addKeyField(keyType.getPwname(), keyType.getValue());
                        }
                    }
                    for (DataType dataType : parametersType.getData()) {
                        pwModelDetails.addDataField(dataType.getSclName(), dataType.getValue(), dataType.getPwname());
                    }
                    pwModelDetails.setIpAddress(iedNodeType.getIp());
                    pwModelDetails.setPortNumber(Integer.parseInt(iedNodeType.getPort()));
                    IEDWorkerThread iedWorkerThread = new IEDWorkerThread(pwModelDetails);
                    workerThreads.add(iedWorkerThread);
                    iedConfigDetails.add(pwModelDetails);
                }
                proxyIpPorts.put(proxyNodeType.getIp(), Integer.parseInt(proxyNodeType.getPort()));
            }

            if (serverType.equals("IED")) {
                Collections.sort(workerThreads);
                System.out.println("workerThreads = " + workerThreads.size());
                iedWorkerThreads.clear();
                iedWorkerThreads.addAll(workerThreads);
                updateStatus(true);
                iedThreadPool.execute(iedWorkerThreads);
                //PWCaseInformationPWCaseInformation/PWCaseInformation_MMXU1.PWCaseInformation_
                //PWCaseInformationPWCaseInformation/PWCaseInformation_MMXU1.PWCaseInformation_
            } else if (serverType.equals("PRX")) {
                Collections.sort(iedConfigDetails);
                for (PWModelDetails iedConfigDetail : iedConfigDetails) {
                    try {
                        ProxyClientFactory.startNormalProxy(iedConfigDetail);
                    } catch (ServiceError serviceError) {
                        serviceError.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (serverType.equals("CC")) {
                try {
                    ControlCenterContext controlCenterContext = new ControlCenterContext(consoleInteractive, confFileName);
                    String ipPort = "";
                    for (String s : proxyIpPorts.keySet()) {
                        ipPort = s+":"+ ConfigUtil.GATEWAY_CC_PORT;
                    }
                    controlCenterClient = ControlCenterClient.getInstance(controlCenterContext, ipPort);
                    controlCenterClient.startClient();

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            if (serverType.equals("ATK")) {
                try {
                    ControlCenterClient_Attacker.getInstance(new ControlCenterContext(confFileName)).startClient(proxyIpPorts, "CB", "linestatus", "true", 100);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void updateStatus(boolean started) {
        Runnable statusThread = new Runnable() {
            @Override
            public void run() {

                ArrayList<IEDWorkerThread> pendingThreads = new ArrayList<IEDWorkerThread>();
                pendingThreads.addAll(iedWorkerThreads);
                while (true) {
                    for (int i = 0; i < pendingThreads.size(); i++) {
                        if (started && pendingThreads.get(i).isServerStarted() ||
                                !started && !pendingThreads.get(i).isServerStarted()) {
                            pendingThreads.remove(i);
                            i--;
                        }
                    }
                    if (pendingThreads.isEmpty()) {
                        try {
                            StatusHandler.statusChanged(started ? StatusHandler.STARTED : StatusHandler.STOPED);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        new Thread(statusThread).start();
    }

    public static void killAll() {
        for (IEDWorkerThread iedWorkerThread : iedWorkerThreads) {
            if (iedWorkerThread.getSmartPowerIEDServer() != null) {
                iedWorkerThread.getSmartPowerIEDServer().stop();
            }
        }
        updateStatus(false);
        iedWorkerThreads.clear();

        ProxyClientFactory.killAll();
    }
}
