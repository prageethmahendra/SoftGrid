//package org.openmuc.j60870;
//
//import it.illinois.adsc.ema.control.SmartPowerControler;
//import it.illinois.adsc.ema.control.proxy.server.GatewayConListener;
//import it.illinois.adsc.ema.control.proxy.server.SecurityHandler;
//import it.illinois.adsc.ema.softgrid.common.ConfigUtil;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
///**
// * Created by prageethmahendra on 14/6/2016.
// */
//public class IEC60870104Server implements ServerSapListener, ConnectionEventListener, GatewayConListener {
//    public static String KEY_STORE = "C:\\Program Files\\Java\\jdk1.8.0_73\\bin\\keystore.jks";
//    public static String CER_STORE = "C:\\Program Files\\Java\\jdk1.8.0_73\\bin\\cacerts.jks";
//    public static String CONFIG_PEROPERTY_FILE = "config/config.properties";
//    private List<IEC60870D104Receiver> iec60870D104Receivers = new ArrayList<IEC60870D104Receiver>();
//    private ServerSap serverSap;
//    private ConfirmationClient confirmationClient;
//    protected static String substationAddress = "192.168.0.144";
//    protected static String securityModuleAddress = "192.168.0.144";
//    public static boolean SECURITY_INBUILT = false;
//    protected static boolean enableSecurity = false;
//    protected static int port = 2404;
//    protected static int securePort = 2405;
//
//    public void startProxy() throws IOException {
//        loadProperties();
//        startSecureSubstationClient();
//        startProxyServer();
//    }
//
//    private void startSecureSubstationClient() {
//        try {
//            SubstationSecureClient.getInstance().connect(this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    // asdu from substation
//    @Override
//    public void newASdu(ASdu aSdu) {
//        for (IEC60870D104Receiver receiver : iec60870D104Receivers) {
//            receiver.forwardToCC(aSdu);
//        }
//    }
//
//    // substation connection closed
//    @Override
//    public void connectionClosed(IOException e) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }
//        startSecureSubstationClient();
//    }
//
//    // asdu from cc
//    @Override
//    public void newAsduFromCC(ASdu aSdu) {
//        if (!enableSecurity) {
//            return;
//        }
//        startSecureConfirmationClient();
//        try {
//            if (confirmationClient != null) {
//                confirmationClient.sendAsdu(aSdu);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // cc connection cloased
//    @Override
//    public void ccConnectionClosed(IEC60870D104Receiver iec60870D104Receiver) {
//        iec60870D104Receivers.remove(iec60870D104Receiver);
//    }
//
//    private void startSecureConfirmationClient() {
//
//        if (confirmationClient == null) {
//            confirmationClient = new ConfirmationClient(securityModuleAddress, securePort);
//            confirmationClient.connect();
//        }
//    }
//
//    private void loadProperties() {
//        Properties properties = new Properties();
//        FileInputStream inputStream = null;
//
//        try {
//            inputStream = new FileInputStream(ConfigUtil.ACM_SECURITY_PROPERTY_FILE);
//            properties.load(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        CER_STORE = properties.get("CER_STORE").toString().trim();
//        KEY_STORE = properties.get("KEY_STORE").toString().trim();
//        securityModuleAddress = properties.get("securityModuleIp").toString().trim();
//        substationAddress = properties.get("substationIp").toString().trim();
//        port = Integer.parseInt(properties.get("securityModulePort").toString().trim());
//        securePort = Integer.parseInt(properties.get("securePort").toString().trim());
//        SECURITY_INBUILT = Boolean.parseBoolean(properties.get("securityInbuilt").toString().trim());
//        try {
//            enableSecurity = Boolean.parseBoolean(properties.get("enableSecurity").toString().trim());
//        } catch (Exception e) {
//            e.printStackTrace();
//            enableSecurity = false;
//        }
//        SecurityHandler.getInstance().setEnabled(enableSecurity);
////        startSecureConfirmationClient();
//    }
//
//    private void startProxyServer() {
//        if (serverSap == null) {
//            serverSap = new ServerSap(port, this);
//            try {
//                serverSap.startListening();
//            } catch (IOException e) {
//                System.out.println("Unable to startProxy listening: \"" + e.getMessage() + "\". Will quit.[" + port + "]");
//            }
//        }
//    }
//
//    @Override
//    public void connectionIndication(Connection connection) {
//        IEC60870D104Receiver iec60870D104Receiver = new IEC60870D104Receiver(this);
//        this.iec60870D104Receivers.add(iec60870D104Receiver);
//        iec60870D104Receiver.setConnection(connection);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        if(enableSecurity)
//        {
//            startSecureConfirmationClient();
//        }
//    }
//
//    @Override
//    public void serverStoppedListeningIndication(IOException e) {
//        System.out.println("Server Has Stopped Listening For New Connections : \"" + e.getMessage() + "\". Will Quit.");
//        stop();
//    }
//
//    @Override
//    public void connectionAttemptFailed(IOException e) {
//        System.out.println("Connection attempt failed: " + e.getMessage());
//        stop();
//    }
//
//    public void stop() {
//        if (serverSap != null) {
//            try {
//                serverSap.stopListening();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            serverSap = null;
//        }
//        try {
//            for (int i = 0; i < iec60870D104Receivers.size(); i++) {
//                iec60870D104Receivers.get(i).stop();
//                iec60870D104Receivers.remove(i);
//                i--;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        if (args != null && (args.length == 0 || args[0].equals("ACM"))) {
//            IEC60870104Server serverEventSapListener = new IEC60870104Server();
//            try {
//                serverEventSapListener.startProxy();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else if( args[0].equalsIgnoreCase("PRX")){
//            String[] proxyParams = {"-f", CONFIG_PEROPERTY_FILE, "PRX"};
//            SmartPowerControler.initiate(proxyParams);
//        }
//    }
//}