package org.openmuc.j60870;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Created by prageethmahendra on 13/6/2016.
 */
public class ConfirmationClient implements ConnectionEventListener {

    private String ip;
    private int port;
    private Connection ccConnection;
    private int TIMEOUT = 10000;
    private boolean encrypted = false;

    public ConfirmationClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    private void init() {
//        try {
//
//            Socket clientSocket = null;
//            if (encrypted) {
//                System.setProperty("javax.net.ssl.keyStore", IEC60870104Server.KEY_STORE);
//                System.setProperty("javax.net.ssl.keyStorePassword", "raspberry");
//                System.setProperty("javax.net.ssl.trustStore", IEC60870104Server.CER_STORE);
//                System.getProperty("javax.net.ssl.trustStorePassword","prageeth");
//
//                SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
//                clientSocket = ssf.createSocket(ip, port);
//            } else {
//                clientSocket = SocketFactory.getDefault().createSocket(ip, port);
//            }
//            ccConnection = new Connection(clientSocket, null, new ConnectionSettings());
//            ccConnection.startDataTransfer(this, TIMEOUT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }
//        if (ccConnection == null) {
//            reset();
//        }
//        while (true) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            sendAsdu(new ASdu(TypeId.C_IC_NA_1, false, CauseOfTransmission.ACTIVATION, false, false,
//                    0, 53,
//                    new InformationObject[]{new InformationObject(0,
//                            new InformationElement[][]{{new IeQualifierOfInterrogation(20)}})}));
//
//        }
    }

    @Override
    public void newASdu(ASdu aSdu) {
        System.out.println("Substation aSdu = " + aSdu);
    }

    @Override
    public void connectionClosed(IOException e) {
        reset();
    }

    private void reset() {
        if (ccConnection != null) {
            try {
                ccConnection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        init();
    }

    public void sendAsdu(ASdu aSdu) {
        if (ccConnection != null) {
            try {
                ccConnection.sendConfirmation(aSdu);
            } catch (IOException e) {

                e.printStackTrace();
                // reconnection
                reset();
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        if (ccConnection != null) {
            try {
                ccConnection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void connect() {
        init();
        System.out.println("Confirmation Client Connected..!");
    }
}

