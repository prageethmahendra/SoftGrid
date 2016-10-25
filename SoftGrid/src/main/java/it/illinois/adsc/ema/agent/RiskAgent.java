package it.illinois.adsc.ema.agent;

import it.illinois.adsc.ema.agent.freamwork.RiskLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by prageethmahendra on 30/6/2016.
 */
public class RiskAgent extends Agent {
    private static RiskAgent instance;
    private Vector<Long> resetTime = new Vector<>();
    private RiskLevel riskLevel;

    private RiskAgent() {
    }

    public static RiskAgent getInstance() {
        if (instance == null) {
            instance = new RiskAgent();
        }
        return instance;
    }

    public void missedResetOccured(long timeinmillis) {
        synchronized (resetTime) {
            resetTime.add(timeinmillis);
        }
    }

    @Override
    public void execute() {
        super.execute();
        long currentTime = System.currentTimeMillis();
        synchronized (resetTime) {
            List<Long> removable = new ArrayList<>();
            for (Long resetTime : this.resetTime) {
                if ((resetTime + 60000) < currentTime) {
                    removable.add(resetTime);
                }
            }
            this.resetTime.removeAll(removable);
        }
        if (resetTime.size() > 0) {
            riskLevel = RiskLevel.HIGH;
        }
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }
}
