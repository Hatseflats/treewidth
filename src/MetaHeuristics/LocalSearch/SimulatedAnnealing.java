package MetaHeuristics.LocalSearch;

import Graph.Graph;
import Graph.Vertex;
import MetaHeuristics.MetaHeuristic;
import MetaHeuristics.ScoreStrategy.ScoreStrategy;
import MetaHeuristics.Solution;

import java.util.*;

public class SimulatedAnnealing extends MetaHeuristic {

    public double T;
    public double alpha;
    public Random random;
    public int nonImprovingIterations;

    public SimulatedAnnealing(ScoreStrategy scoreStrategy, double T, double alpha, Random random, int nonImprovingIterations){
        super(scoreStrategy);
        this.T = T;
        this.alpha = alpha;
        this.random = random;
        this.nonImprovingIterations = nonImprovingIterations;
    }

    public Solution run(Graph g) {
        Solution currentSolution =  new Solution(g.maxMinDegree(random));
        Solution bestSolution = currentSolution.copy();
        int bestScore = Integer.MAX_VALUE;
        int lastImprovement = 0;

        int currentScore;

        while(lastImprovement <= nonImprovingIterations){
            currentSolution.successors =  triangulate(currentSolution, g.copy());;
            currentScore = scoreStrategy.score(currentSolution);

            if(currentScore < bestScore){
                bestScore = currentScore;
                bestSolution = currentSolution;
                lastImprovement = 0;
            }

            while(true){
                short v = (short) random.nextInt(g.adjacencyList.keySet().size());
                if(v == 0){
                    v = 1;
                }

                int maxPred = currentSolution.maxPredecessor(v);
                int minSucc = currentSolution.minSuccessor(v);

                Solution s1 = new Solution();
                Solution s2 = new Solution();

                if(maxPred != -1){
                    s1 = currentSolution.copy();
                    s1.swap(v-1, maxPred);
                }
                if(minSucc != -1){
                    s2 = currentSolution.copy();
                    s2.swap(v-1, minSucc);
                }

                if(maxPred != -1) {
                    s1.successors = triangulate(s1, g.copy());
                    s1.score = scoreStrategy.score(s1);

                    if(s1.score < currentScore || acceptDeterioration(currentScore, s1.score)){
                        currentSolution = s1;
                        break;
                    }
                    lastImprovement++;
                } else if (minSucc != -1){
                    s2.successors = triangulate(s2, g.copy());
                    s2.score = scoreStrategy.score(s2);

                    if(s2.score < currentScore || acceptDeterioration(currentScore, s2.score)){
                        currentSolution = s2;
                        break;
                    }
                    lastImprovement++;
                }
            }

//            System.out.println("Score:" + currentScore + ", Treewidth:" + treeWidth(currentSolution) + ", T:" + T + ", lastImprovement:" + lastImprovement);
            T = T * alpha;
        }

        return bestSolution;
    }

    private boolean acceptDeterioration(int currentScore, int neighborScore){
        int diff = currentScore - neighborScore;
        double lambda = (diff != 0 ? -diff : 1)/T;
        double p = expDistribution(random.nextDouble(), lambda);
        double x = random.nextDouble();

        return x < p;
    }

    private double expDistribution(double x, double lambda){
        return Math.log(1-x)/(-lambda);
    }
}