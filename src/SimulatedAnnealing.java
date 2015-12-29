import java.util.*;
import java.util.stream.Collectors;

public class SimulatedAnnealing extends LocalSearch {

    public double T;
    public double alpha;
    public int Q;

    public int iteration;
    public Random random;

    public SimulatedAnnealing(double T, double alpha, int Q, int seed){
        super();

        this.T = T;
        this.alpha = alpha;
        this.Q = Q;
        this.iteration = 0;

        this.random = new Random(seed);
    }

    public void run(Graph g) {
        Solution s = new Solution(maximumMinimumDegree(g));
        HashMap<Vertex, Set<Vertex>> successors;
        int score;

        while(true){
            successors = triangulate(s, g.copy());
            score = score(successors);
            ArrayList<Solution> neighbors = neighborhood(s, successors);
            Collections.shuffle(neighbors, random);

//            System.out.println("Score:" + score + ", Treewidth:" + treeWidth(successors) + ", T:" + T);

            for(Solution neighbor: neighbors){
                HashMap<Vertex, Set<Vertex>> neighborSuccessors = triangulate(neighbor, g.copy());
                int neighborScore = score(neighborSuccessors);

                if(neighborScore < score){
                    s = neighbor;
                    break;
                } else {
                    double lambda = (neighborScore - score) / T;
                    double x = Math.log(1-random.nextDouble())/(-lambda);
                    double p = random.nextDouble();

                    System.out.println("x:" + x + ", p:" + p + ", lambda:" + lambda);

                    if(x < p){
                        s = neighbor;
                    }
                }

            }

            if(iteration % Q == 0){
                T = T * alpha;
            }

            iteration++;
        }
    }
}