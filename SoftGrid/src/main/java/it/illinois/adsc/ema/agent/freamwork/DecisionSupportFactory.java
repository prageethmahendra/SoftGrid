package it.illinois.adsc.ema.agent.freamwork;

/**
 * Created by prageethmahendra on 30/6/2016.
 */
public class DecisionSupportFactory {
    public static DelayDecisionProvider createDelayDecisionProvider() {
        return Executor.getInstance().getDelayDecisionProvier();
    }
}
