import java.util.*;
import java.util.stream.Collectors;

public class SimulatedAnnealing extends LocalSearch {

    public double T;
    public double alpha;

    public Random random;

    public SimulatedAnnealing(double T, double alpha, int seed){

        this.T = T;
        this.alpha = alpha;
        this.random = new Random(seed);
    }

    public Solution run(Graph g, Solution initialSolution) {
        Solution currentSolution = initialSolution;
        Solution bestSolution = currentSolution.copy();
        int bestScore = Integer.MAX_VALUE;
        int lastImprovement = 0;

        HashMap<Vertex, Set<Vertex>> currentSuccessors;
        int currentScore;

        while(lastImprovement <= 200){
            currentSuccessors = triangulate(currentSolution, g.copy());
            currentScore = score(currentSuccessors);

            if(currentScore < bestScore){
                bestScore = currentScore;
                bestSolution = currentSolution;
                lastImprovement = 0;
            }

            ArrayList<Solution> neighbors = neighborhood(currentSolution, currentSuccessors);
            Collections.shuffle(neighbors, random);
//            System.out.println("Score:" + currentScore + ", Treewidth:" + treeWidth(currentSuccessors) + ", T:" + T + ", lastImprovement:" + lastImprovement);

            for(Solution neighbor: neighbors){
                HashMap<Vertex, Set<Vertex>> neighborSuccessors = triangulate(neighbor, g.copy());
                int neighborScore = score(neighborSuccessors);

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