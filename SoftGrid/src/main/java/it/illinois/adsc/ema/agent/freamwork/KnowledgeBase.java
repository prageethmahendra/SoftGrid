package it.illinois.adsc.ema.agent.freamwork;


import it.illinois.adsc.ema.agent.RiskAgent;

/**
 * Created by prageethmahendra on 30/6/2016.
 */
public class KnowledgeBase {
    public static RiskLevel getRiskLevel() {
        return RiskAgent.getInstance().getRiskLevel();
    }
}
