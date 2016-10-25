package it.illinois.adsc.ema.agent.test;


import it.illinois.adsc.ema.agent.RiskAgent;
import it.illinois.adsc.ema.agent.freamwork.DecisionSupportFactory;
import it.illinois.adsc.ema.agent.freamwork.DelayDecisionProvider;

/**
 * Created by prageethmahendra on 30/6/2016.
 */
public class AgentTest {
    public static void main(String[] args) {
        DelayDecisionProvider delayDecisionProvider = DecisionSupportFactory.createDelayDecisionProvider();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("RISK_SCORE=" + delayDecisionProvider.getRiskScore());
                    System.out.println("RISK_SCORE=" + delayDecisionProvider.getRiskScore());

                    RiskAgent.getInstance().missedResetOccured(System.currentTimeMillis());
                    System.out.println("RISK_SCORE=" + delayDecisionProvider.getRiskScore());
                    System.out.println("RISK_SCORE=" + delayDecisionProvider.getRiskScore());

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
