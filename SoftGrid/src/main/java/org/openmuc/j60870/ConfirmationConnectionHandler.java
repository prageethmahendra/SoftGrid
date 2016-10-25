package org.openmuc.j60870;

import it.illinois.adsc.ema.control.center.security.CCSecurityHandler;

import java.io.*;
import java.util.concurrent.TimeoutException;

/**
 * Created by prageethmahendra on 13/6/2016.
 */
public class ConfirmationConnectionHandler implements ConnectionEventListener {
    private static final int TIMEOUT = 100000;
    private Connection connection = null;

    public ConfirmationConnectionHandler() {

    }

    public void connectionIndication(Connection connection) {
        this.connection = connection;
        try {
            connection.waitForStartDT(this, TIMEOUT);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void newASdu(ASdu aSdu) {
        System.out.println("From Secure Gateway aSdu = " + aSdu);
        CCSecurityHandler.getInstance().newASdu(aSdu, System.nanoTime());
    }

    @Override
    protected void finalize() throws Throwable {
        connectionClosed(null);
    }

    @Override
    public void connectionClosed(IOException e) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }
}
