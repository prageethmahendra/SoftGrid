package it.illinois.adsc.ema.control.center;

import it.illinois.adsc.ema.control.center.command.Command;
import it.illinois.adsc.ema.control.conf.IedNodeType;
import it.illinois.adsc.ema.control.conf.PWModelType;
import it.illinois.adsc.ema.control.conf.generator.ConfigGenerator;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by prageethmahendra on 29/2/2016.
 * This file contains the context parameters required for the control center
 */
public class ControlCenterContext {
    private String configFileName;
    private HashMap<Integer, IedNodeType> iedVariableMap = new HashMap<Integer, IedNodeType>();
    // todo this should not be hard corded
    private int portoffset = 10003;
    private boolean remoteInteractive;

    public ControlCenterContext(boolean consoleInteractive, String configFileName) {
        this.remoteInteractive = consoleInteractive;
        this.configFileName = configFileName;
        init();
    }

    public ControlCenterContext(String configFileName) {
        this.configFileName = configFileName;
        init();
    }

    private void init() {
        /**
         * This implementation is based on below assumption
         *
         * 01. There is only one Proxy
         * 02. All the ieds are connected to the same proxy
         * 03. all the ied port numbers are starting from the portoffset and the number of ieds are withing the range of 65535
         */
        try {
            PWModelType pwModelType = ConfigGenerator.deserializeConfigXml(this.configFileName);
            for (IedNodeType ied : pwModelType.getProxyNode().get(0).getIedNode()) {
                int port = Integer.parseInt(ied.getPort());
                iedVariableMap.put(port - portoffset, ied);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    public String getConfigFileName() {
        return configFileName;
    }

    public void setConfigFileName(String configFileName) {
        this.configFileName = configFileName;
    }

    public boolean validate(Command command) {
        if (command.getSubtationAddressSpace() >= 65534) {
            return true;
        } else {
            for (Integer iedID : iedVariableMap.keySet()) {
                if (iedID.equals(command.getSubtationAddressSpace())) {
                    return true;
                }
            }
        }
        System.out.println("Invalid IED-ID...!");
        return false;
    }

    public HashMap<Integer, IedNodeType> getIedVariableMap() {
        return iedVariableMap;
    }

    public void setIedVariableMap(HashMap<Integer, IedNodeType> iedVariableMap) {
        this.iedVariableMap = iedVariableMap;
    }

    public boolean isRemoteInteractive() {
        return remoteInteractive;
    }

    public void setRemoteInteractive(boolean remoteInteractive) {
        this.remoteInteractive = remoteInteractive;
    }
}
