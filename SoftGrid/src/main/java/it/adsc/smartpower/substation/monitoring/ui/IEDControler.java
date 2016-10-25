package it.adsc.smartpower.substation.monitoring.ui;

/**
 * Created by prageethmahendra on 2/9/2016.
 */
public interface IEDControler {
    void displayMonitorWindow();
    void loadConfigurations();
    void startIEDs(String caseFile);

    int getStatus();

    void stopIEDServers();


}
