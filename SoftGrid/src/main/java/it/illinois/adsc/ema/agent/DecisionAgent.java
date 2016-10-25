package it.illinois.adsc.ema.agent;


import it.illinois.adsc.ema.agent.freamwork.DelayDecisionProvider;
import it.illinois.adsc.ema.agent.freamwork.Executor;

import java.util.List;
import java.util.Random;

/**
 * Created by prageethmahendra on 30/6/2016.
 */
public class DecisionAgent extends Agent implements DelayDecisionProvider {

    public static double PROBABILITY = 0.70;

    public DecisionAgent() {
    }

    @Override
    public void execute() {
        super.execute();
        Random random = new Random(System.nanoTime());
        int randomValue = random.nextInt();
        double total = 0;
        // previouse knowledge
        decisionScore = (Math.abs(randomValue % 10) < PROBABILITY * 10) ? 1 : 0;
        List<Double> decisionList = Executor.getInstance().getDecisionList();
        for (Double decision : decisionList) {
            total += decision;
        }
        // new knowledge
        decisionScore = total / decisionList.size();
        if (decisionScore <= 0) {
            // create a knowledge
            decisionScore = (Math.abs(randomValue % 10) < PROBABILITY * 10) ? 1 : 0;
        }
        else
        {
            decisionScore = decisionScore > 0 ? 1 : 0;
        }
//        System.out.println("total=" + total + " decisionScore = " + decisionScore);
    }

    @Override
    public double getRiskScore() {
        return decisionScore;
    }

//    public static void main(String[] args) {
//        double total = 0;
//        double avg = 0;
//        double count = 0;
//        while(true)
//        {
//            count++;
//            Random random = new Random(System.nanoTime());
//            int randomValue = random.nextInt();
//            total += Math.abs(randomValue % 10) < 100 ? 1 : 0;
//            avg = total / count;
//            System.out.println(" " + avg * 100);
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
}
