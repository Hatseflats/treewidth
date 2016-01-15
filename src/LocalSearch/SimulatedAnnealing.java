package LocalSearch;

import Graph.Graph;
import Graph.Vertex;
import LocalSearch.ScoreStrategy.ScoreStrategy;

import java.util.*;

public class SimulatedAnnealing extends LocalSearch {

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

    public Solution run(Graph g, Solution initialSolution) {
        Solution currentSolution = initialSolution;
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
                int neighborScore = scoreStrategy.score(neighbor);

                if(neighborScore < currentScore || acceptDeterioration(currentScore, neighborScore)){
                    currentSolution = neighbor;
                    break;
                }
                lastImprovement++;
            }

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