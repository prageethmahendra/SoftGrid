package it.illinois.adsc.ema.control.proxy.client;

import it.illinois.adsc.ema.control.ied.pw.PWModelDetails;
import it.illinois.adsc.ema.control.proxy.ProxyType;
import it.illinois.adsc.ema.control.proxy.context.ProxyClientContext;
import it.illinois.adsc.ema.control.proxy.context.ProxyContextFactory;
import it.illinois.adsc.ema.control.proxy.infor.ProxyInformation;
import it.illinois.adsc.ema.control.proxy.infor.ProxyVariant;
import it.illinois.adsc.ema.control.proxy.server.ProxyServer;
import it.illinois.adsc.ema.control.proxy.util.ProxyClientUtil;
import it.illinois.adsc.ema.control.proxy.util.ProxyTimeLoger;
import org.openmuc.openiec61850.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Prageeth Mahendra
 */
public class SubstationProxyClient implements ClientEventListener, PowerProxyClient {
    private final static Logger logger = LoggerFactory.getLogger(SubstationProxyClient.class);
    private PWModelDetails modelDetails;
    private static ProxyClientContext proxyClientContext = null;
    private ServerModel serverModel;
    private ClientAssociation association;
    private long startTime = System.currentTimeMillis();
    private int iedID;
    private boolean connectedToBus;

    public SubstationProxyClient(int iedID) {
        this.iedID = iedID;
    }

    public void init(ProxyType proxyType) {
        proxyClientContext = ProxyContextFactory.getInstance().getProxyContext(iedID, this);
        ProxyServer.getInstance().start();
    }

    @Override
    public List<ProxyInformation> interrogationRequest() {
        System.out.println(serverModel);
        System.out.println(modelDetails.getModelNodeReference());
        List<ProxyInformation> proxyInformations = null;
        if(serverModel == null)
        {
            try {
                startProxy(modelDetails);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ServiceError serviceError) {
                serviceError.printStackTrace();
            }
            if(serverModel == null)
            {
                return proxyInformations;
            }
        }
        FcModelNode modelNode = (FcModelNode) serverModel.findModelNode(modelDetails.getModelNodeReference(), Fc.CF);
        if (modelNode == null) {
            System.out.println("Null ModelNode");
            return proxyInformations;
        }
        logger.info("Re-Loading..." + modelDetails.getModelNodeReference());
        System.out.println("Re-Loading..." + modelDetails.getModelNodeReference());
        try {
            ProxyTimeLoger.resetStartTime();
            association.getDataValues(modelNode);
            for (String variableName : modelNode.getChildrenMap().keySet()) {
                proxyInformations = new ArrayList<ProxyInformation>();
                ProxyInformation proxyInformation = new ProxyInformation();
                BdaVisibleString lineStatus = (BdaVisibleString) modelNode.getChild(variableName);
                proxyInformation.setParamType(ProxyClientUtil.getObjectVariableType(variableName));
                proxyInformation.setParameter(variableName);
                proxyInformation.setVariant(new ProxyVariant());
                proxyInformation.getVariant().setString(lineStatus.getStringValue());
                ProxyTimeLoger.logDuration("interrogationRequest");
                proxyInformation.setDeviceType(ProxyClientUtil.getDeviceType(modelNode));
                proxyInformation.setIedId(this.iedID);
                proxyInformations.add(proxyInformation);
            }
        } catch (ServiceError serviceError) {
            serviceError.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return proxyInformations;
    }

    @Override
    public boolean handleControlCommand(int qualifier, Object valueObject) {
        if (qualifier > 0 && serverModel != null && modelDetails != null) {
            ProxyTimeLoger.resetStartTime();
            FcModelNode modCtlModel = (FcModelNode) serverModel.findModelNode(modelDetails.getModelNodeReference()
                    + "." + ProxyClientUtil.getObjectReference(qualifier), Fc.CF);
            if (modCtlModel != null) {
                ProxyClientUtil.setIedFeildValues(modCtlModel, qualifier, valueObject);
                try {
                    association.setDataValues(modCtlModel);
                    ProxyTimeLoger.logDuration("handleControlCommand");
                    return true;
                } catch (ServiceError serviceError) {
                    serviceError.printStackTrace();
                } catch (IOException serviceError) {
                    serviceError.printStackTrace();
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void newReport(Report report) {
        logger.info("got report with dataset ref: " + report.getDataSet().getReferenceStr());
        // do something with the report
    }

    @Override
    public void associationClosed(IOException e) {
        logger.info("Association was closed");
    }

    @Override
    public int getIedID() {
        return iedID;
    }

    public void setIedID(int iedID) {
        this.iedID = iedID;
    }

    public void startProxy(PWModelDetails modelDetails) throws IOException, ServiceError {
        String usageString = "usage: org.openmuc.openiec61850.SubstationProxyClient <host> <port>";
        logger.info("Default Proxy Server Port: " + 2404);
        this.modelDetails = modelDetails;
        System.out.println(modelDetails.toString());
        String proxyHost = modelDetails.getIpAddress();
        int clientPort = modelDetails.getPortNumber();
        if (proxyHost != null || clientPort > 0) {
            logger.info(usageString);
            logger.info("Default Host Address : " + proxyHost);
            logger.info("Default Client Port: " + clientPort);
        } else {
            return;
        }
        InetAddress address;
        try {
            address = InetAddress.getByName(proxyHost);
        } catch (UnknownHostException e) {
            logger.error("Unknown host: " + proxyHost);
            return;
        }

        ClientSap clientSap = new ClientSap();
        // alternatively you could use ClientSap(SocketFactory factory) to e.g. connect using SSL
        // optionally you can set some association parameters (but usually the default should work):
        // clientSap.setTSelRemote(new byte[] { 0, 1 });
        // clientSap.setTSelLocal(new byte[] { 0, 0 });
        SubstationProxyClient eventHandler = this;

        logger.info("Attempting to connect to server " + proxyHost + " on port " + clientPort);
        try {
            association = clientSap.associate(address, clientPort, null, eventHandler);
        } catch (IOException e) {
            // an IOException will always indicate a fatal exception. It indicates that the association was closed and
            // cannot be recovered. You will need to create a new association using ClientSap.associate() in order to
            // reconnect.
            logger.error("Error connecting to server: " + e.getMessage());
            return;
        }

        try {
            // requestModel() will call all GetDirectory and GetDefinition ACSI services needed to get the complete
            // server model
            serverModel = association.retrieveModel();

        } catch (ServiceError e) {
            logger.error("Service Error requesting model.", e);
            association.close();
            return;
        } catch (IOException e) {
            logger.error("Fatal IOException requesting model.", e);
            return;
        }
        // get the values of all data attributes in the model:
        association.getAllDataValues();
        for (ModelNode modelNode : serverModel.getChildren()) {
            connectedToBus = modelNode.getName().contains("IED_Bus");
        }

    }

    public boolean isConnectedToBus() {
        return connectedToBus;
    }

    public void setConnectedToBus(boolean connectedToBus) {
        this.connectedToBus = connectedToBus;
    }

    public void stop() {
        ProxyServer.getInstance().stop();
    }
}
