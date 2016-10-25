package it.illinois.adsc.ema.control.proxy.server;

import it.illinois.adsc.ema.agent.freamwork.DecisionSupportFactory;
import it.illinois.adsc.ema.agent.freamwork.DelayDecisionProvider;
import it.illinois.adsc.ema.control.proxy.context.ProxyServerContext;
import org.openmuc.j60870.*;

import java.util.*;

/**
 * Created by prageethmahendra on 19/5/2016.
 */
public class SecurityHandler {
    private static SecurityHandler instance = null;
    private final Vector<ControlCommands> controlCommands = new Vector<ControlCommands>();
    private Thread cleanerThread = null;
    //  private Thread executerThread = null;
    private boolean enabled = false;
    public static SecurityEventListener securityEventListener;
    private static DelayDecisionProvider delayDecisionProvider;
    private boolean resetReceived = false;

    private SecurityHandler() {
        clean();
        delayDecisionProvider = DecisionSupportFactory.createDelayDecisionProvider();
    }

    public static SecurityHandler getInstance() {
        if (instance == null) {
            instance = new SecurityHandler();
        }
        return instance;
    }

    public void validateAndExecute(ASdu aSdu) {
        validateAndExecute(aSdu, calculateDelay());
    }

    public void validateAndExecute(ASdu aSdu, int timeInMillis) {
        ControlCommands controlCommand = new ControlCommands(aSdu, timeInMillis);
        synchronized (controlCommands) {
            if (enabled && isCriticalCommand(aSdu) && controlCommand.getDelayMilis() > 0) {
                controlCommand.executeWithDelay();
                controlCommands.add(controlCommand);
                controlCommands.notify();
            } else {
                controlCommand.executeCommand();
            }
        }
//  clean();
    }

    public void validateAndExecute(ProxyServer proxyServer, ProxyServerContext proxyContext, ASdu aSdu, int qualifier, Object newState) {
        ControlCommands controlCommand = new ControlCommands(proxyContext, aSdu, qualifier, newState, calculateDelay());
        synchronized (controlCommands) {
            if (enabled && isCriticalCommand(aSdu) && controlCommand.getDelayMilis() > 0) {
                controlCommand.executeWithDelay();
                controlCommands.add(controlCommand);
                controlCommands.notify();
            }  else {
                controlCommand.executeCommand();
            }
        }
//  clean();
    }

    public int calculateDelay() {
        return delayDecisionProvider.getRiskScore() > 0 || resetReceived&&enabled ? 380 : 0;
//        return 5500;
    }

    private boolean isCriticalCommand(ASdu aSdu) {
        boolean criticalCommand;
//        switch (aSdu.getTypeIdentification()) {
//            case C_SC_NA_1:
        criticalCommand = true;
//                break;
//            default:
//                criticalCommand = false;
//        }
        return criticalCommand;
    }

    public void clean() {
        if (cleanerThread == null) {
            cleanerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        ArrayList<ControlCommands> removableCommands = new ArrayList<ControlCommands>();
//                      synchronized (controlCommands) {
//                          if (controlCommands.isEmpty()) {
//                              try {
//                                  controlCommands.wait();
//                              } catch (InterruptedException e) {
//                                  e.printStackTrace();
//                              }
//                              continue;
//                          }
//                      }
                        synchronized (controlCommands) {
                            for (ControlCommands controlCommand : controlCommands) {
                                if (controlCommand.isCancelled() || controlCommand.isExecuted() || controlCommand.isErrored()) {
                                    removableCommands.add(controlCommand);
                                }
                            }
                            controlCommands.removeAll(removableCommands);

                                for (ControlCommands controlCommand : controlCommands) {
                                    if (!controlCommand.isCancelled() && !controlCommand.isExecuted() && !controlCommand.isErrored()) {
                                        if (controlCommand.getExecuteTime() <= System.currentTimeMillis()) {
                                            if (controlCommand.executeCommand()) {
                                                controlCommand.setExecuted(true);
                                            } else {
                                                controlCommand.setErrored(true);
                                            }
                                        }
                                    }
                                }

                        try {
                            if (controlCommands.isEmpty()) {
                                controlCommands.wait();
                            } else {
                                Thread.sleep(50);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    }
                }
            });
            cleanerThread.start();
        }

//        if (executerThread == null) {
//            executerThread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while (true) {
//                          synchronized (controlCommands) {
//                              if (controlCommands.isEmpty()) {
//                                  try {
//                                      controlCommands.wait();
//                                  } catch (InterruptedException e) {
//                                      e.printStackTrace();
//                                  }
//                                  continue;
//                              }
//                          }
//                        synchronized (controlCommands) {
//
//                        }
//
//                        try {
//                            Thread.sleep(10);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
//            executerThread.start();
//        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void addSecurityEventListener(SecurityEventListener securityEventListener) {
        this.securityEventListener = securityEventListener;
    }

    public void resetAll() {
        if (!enabled) {
            return;
        }
        synchronized (controlCommands) {
            resetReceived = true;
            int count = 0;
            for (ControlCommands controlCommand : controlCommands) {
                if (!controlCommand.isCancelled() && !controlCommand.isExecuted()) {
                    System.out.println(">>>>>>>>>>>>>>>>Cancelled : " + controlCommand.getaSdu().getTypeId().name());
                    controlCommand.setCancelled(true);
                    controlCommand.setExecuted(true);
                    count++;
                }
            }
        }
    }

    public static void main(String[] args) {
        double total = 0;
        double count = 0;
        double delay = 0;
        SecurityHandler securityHandler = new SecurityHandler();
        while (true) {
            count++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            delay = securityHandler.calculateDelay();
            total += (delay > 0.0 ? 1.0 : 0.0);
            System.out.println(delay + " avg = " + total / count);
        }
//        InetAddress IP= null;
//        try {
//            IP = InetAddress.getLocalHost();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        System.out.println("IP of my system is := "+IP.getHostAddress());
//        SecurityHandler.getInstance().addSecurityEventListener(new SecurityEventListener() {
//            @Override
//            public void readyToExecute(ASdu aSdu) {
//                System.out.println("Executed");
//            }
//
//            @Override
//            public void readyToExecute(ASdu aSdu, int qualifier, Object newState) {
//
//            }
//        });
//        SecurityHandler.getInstance().validateAndExecute(new ASdu(TypeId.C_SC_NA_1, false, CauseOfTransmission.ACTIVATION, false, false,
//                53, 0,
//                new InformationObject[]{new InformationObject(0,
//                        new InformationElement[][]{{new IeQualifierOfInterrogation(20)}})}));
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        SecurityHandler.getInstance().resetAll();
//
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        SecurityHandler.getInstance().resetAll();
    }
}

class ControlCommands {
    private ProxyServerContext proxyContext;
    private ASdu aSdu;
    private int qualifier;
    private Object newState;
    private transient boolean executed;
    private transient boolean errored;
    private transient boolean cancelled;
    private transient int delayMilis;
    private transient long executeTime = System.currentTimeMillis() + 24 * 60 * 60 * 1000;
//    public Timer uploadCheckerTimer = new Timer(true);

    public ControlCommands() {
    }

    public ControlCommands(ProxyServerContext proxyContext, ASdu aSdu, int qualifier, Object newState, int delayMilis) {
        this.proxyContext = proxyContext;
        this.aSdu = aSdu;
        this.qualifier = qualifier;
        this.newState = newState;
        this.delayMilis = delayMilis;
    }

    public ControlCommands(ASdu aSdu, int delayMilis) {
        this.aSdu = aSdu;
        this.delayMilis = delayMilis;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public boolean isErrored() {
        return errored;
    }

    public void setErrored(boolean errored) {
        this.errored = errored;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public ProxyServerContext getProxyContext() {
        return proxyContext;
    }

    public void setProxyContext(ProxyServerContext proxyContext) {
        this.proxyContext = proxyContext;
    }

    public ASdu getaSdu() {
        return aSdu;
    }

    public void setaSdu(ASdu aSdu) {
        this.aSdu = aSdu;
    }

    public int getQualifier() {
        return qualifier;
    }

    public void setQualifier(int qualifier) {
        this.qualifier = qualifier;
    }

    public Object getNewState() {
        return newState;
    }

    public void setNewState(Object newState) {
        this.newState = newState;
    }

    public int getDelayMilis() {
        return delayMilis;
    }

    public void setDelayMilis(int delayMilis) {
        this.delayMilis = delayMilis;
    }

    public void cancel() {
        cancelled = true;
    }

    public void executeWithDelay() {
//        uploadCheckerTimer.schedule(
//                new TimerTask() {
//                    public void run() {
//                        if (!cancelled) {
//                            executeCommand();
//                            executed = true;
//                        } else {
//                            errored = true;
//                            executed = true;
//                        }
//                    }
//                }, delayMilis);
        executeTime = System.currentTimeMillis() + delayMilis;
        System.out.println("delayMilis = " + delayMilis);
    }

    public boolean executeCommand() {
        if (executed || cancelled) {
            return false;
        }
        boolean result = false;
        if (SecurityHandler.securityEventListener != null) {
            if (proxyContext != null) {
                SecurityHandler.securityEventListener.readyToExecute(aSdu, qualifier, newState);
            } else {
                SecurityHandler.securityEventListener.readyToExecute(aSdu);
            }
        } else {
            System.out.println("SecurityHandler.securityEventListener = " + SecurityHandler.securityEventListener);
        }
        return result;
    }
}
