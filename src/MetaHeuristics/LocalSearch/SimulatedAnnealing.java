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
        Solution currentSolution =  new Solution(g.maxMinDegree());;
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

            ArrayList<Solution> neighbors = neighborhood(currentSolution);
            Collections.shuffle(neighbors, random);
//            System.out.println("Score:" + currentScore + ", Treewidth:" + treeWidth(currentSolution.successors) + ", T:" + T + ", lastImprovement:" + lastImprovement);

            for(Solution neighbor: neighbors){
                neighbor.successors = triangulate(neighbor, g.copy());
                neighbor.score = scoreStrategy.score(neighbor);

                if(neighbor.score < currentScore || acceptDeterioration(currentScore, neighbor.score)){
                    currentSolution = neighbor;
                    break;
                }
                lastImprovement++;
            }

            T = T * alpha;
        }

        return bestSolution;
    }

    public ArrayList<Solution> neighborhood(Solution s){
        ArrayList<Solution> neighbors = new ArrayList<>();
        int currentIndex = 0;

        for(Vertex v : s.ordering){
            int maxPred = s.maxPredecessor(v);
            int minSucc = s.minSuccessor(v);

            if(maxPred != -1){
                Solution s1 = s.copy();
                s1.swap(currentIndex, maxPred);
                if(!neighbors.contains(s1)){
                    neighbors.add(s1);
                }
            }
            if(minSucc != -1){
                Solution s2 = s.copy();
                s2.swap(currentIndex, minSucc);
                if(!neighbors.contains(s2)){
                    neighbors.add(s2);
                }
            }
            currentIndex = currentIndex + 1;
        }
        return neighbors;
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