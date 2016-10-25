package org.openmuc.j60870;

import it.illinois.adsc.ema.softgrid.common.ConfigUtil;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.*;
import java.net.ServerSocket;
import java.util.Properties;

/**
 * Created by prageethmahendra on 13/6/2016.
 */
public class ConfirmationServer implements ServerSapListener {
    private int port;
    private ServerSocket serverSocket;
    private int maxConnection = 50;
    private ServerThread serverThread = null;
    private ConnectionSettings settings = null;
    private boolean encripted = false;
    private boolean twoWayAuthentication = true;

    public ConfirmationServer(String ip, int port) {
        this.port = port;
    }

    public void start() {
        try {
            Properties properties = new Properties();
            FileInputStream inputStream = null;

            try {
                System.out.println("ACM_SECURITY_PROPERTY_FILE = " + ConfigUtil.ACM_SECURITY_PROPERTY_FILE);
                inputStream = new FileInputStream(ConfigUtil.ACM_SECURITY_PROPERTY_FILE);
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            String encrStr = properties.getProperty("encripted");
            if (encripted || (encrStr != null && encrStr.equalsIgnoreCase("true"))) {
                System.setProperty("javax.net.ssl.keyStore", properties.getProperty("KEY_STORE").trim());
                System.setProperty("javax.net.ssl.keyStorePassword", "prageeth");
                System.setProperty("javax.net.ssl.trustStore", properties.getProperty("CER_STORE").trim());
                System.getProperty("javax.net.ssl.trustStorePassword", "prageeth");
                SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
                serverSocket = ssf.createServerSocket(port, maxConnection, null);
                if (twoWayAuthentication) {
                    ((SSLServerSocket) serverSocket).setNeedClientAuth(true);
                }
            } else {
                serverSocket = ServerSocketFactory.getDefault().createServerSocket(port, maxConnection, null);
            }
            serverThread = new ServerThread(serverSocket, settings = new ConnectionSettings(), 50, this);
            serverThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionIndication(Connection connection) {
        new ConfirmationConnectionHandler().connectionIndication(connection);
    }

    @Override
    public void serverStoppedListeningIndication(IOException e) {
        stopServer();
    }

    @Override
    public void connectionAttemptFailed(IOException e) {
        stopServer();
        e.printStackTrace();
    }

    void stopServer() {
        if (serverSocket.isBound()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
            }
        }
    }


    public static void main(String[] args) {
        ConfirmationServer confirmationServer = new ConfirmationServer("127.0.0.1", 2405);
        confirmationServer.start();
//        ConfirmationClient confirmationClient = new ConfirmationClient("127.0.0.1", 2405);
//        confirmationClient.connect();
    }

}
