package it.illinois.adsc.ema.agent.freamwork;

import it.illinois.adsc.ema.agent.Agent;
import it.illinois.adsc.ema.agent.DecisionAgent;
import it.illinois.adsc.ema.agent.RiskAgent;
import it.illinois.adsc.ema.agent.SubstationStateAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prageethmahendra on 30/6/2016.
 */
public class Executor {
    private static Executor instance;
    List<Agent> agents = new ArrayList<>();

    private Executor() {
        runExecutor();
    }

    public static Executor getInstance() {
        if (instance == null) {
            instance = new Executor();
        }
        return instance;
    }

    public void registerAgent(Agent agent) {
        agents.add(agent);
    }

    private void runExecutor() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    for (Agent agent : agents) {
                        if (agent.getAgentState().equals(AgentState.START)) {
                            agent.execute();
                        }
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public DelayDecisionProvider getDelayDecisionProvier() {
        RiskAgent.getInstance().setAgentState(AgentState.START);
        DecisionAgent decisionAgent = new DecisionAgent();
        decisionAgent.setAgentState(AgentState.START);
        new SubstationStateAgent().setAgentState(AgentState.START);
        return decisionAgent;
    }

    public List<Double> getDecisionList() {
        List<Double> decisionList = new ArrayList<>();
        for (Agent agent : agents) {
            decisionList.add(agent.getDecisionScore());
        }
        return decisionList;
    }
}
