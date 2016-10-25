package it.illinois.adsc.ema.agent.freamwork;

/**
 * Created by prageethmahendra on 1/7/2016.
 */
public interface IAgent {

    void execute();

    double getDecisionScore();

    AgentState getAgentState();
}
