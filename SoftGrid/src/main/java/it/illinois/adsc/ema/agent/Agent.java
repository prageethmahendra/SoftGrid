package it.illinois.adsc.ema.agent;


import it.illinois.adsc.ema.agent.freamwork.AgentState;
import it.illinois.adsc.ema.agent.freamwork.Executor;
import it.illinois.adsc.ema.agent.freamwork.IAgent;

/**
 * Created by prageethmahendra on 30/6/2016.
 */
public class Agent implements IAgent {
    protected double decisionScore = 0;
    private AgentState agentState = AgentState.INIT;

    public Agent() {
        Executor.getInstance().registerAgent(this);
    }

    public void execute() {
    }

    public double getDecisionScore() {
        return decisionScore;
    }

    public void setDecisionScore(double decisionScore) {
        this.decisionScore = decisionScore;
    }

    public AgentState getAgentState() {
        return agentState;
    }

    public void setAgentState(AgentState agentState) {
        this.agentState = agentState;
    }
}
