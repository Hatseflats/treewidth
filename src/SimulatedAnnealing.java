import java.util.*;
import java.util.stream.Collectors;

public class SimulatedAnnealing extends LocalSearch {

    public double T;
    public double alpha;

    public Random random;

    public SimulatedAnnealing(double T, double alpha, int seed){
        super();

        this.T = T;
        this.alpha = alpha;

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

            System.out.println("Score:" + score + ", Treewidth:" + treeWidth(successors) + ", T:" + T);

            for(Solution neighbor: neighbors){
                HashMap<Vertex, Set<Vertex>> neighborSuccessors = triangulate(neighbor, g.copy());
                int neighborScore = score(neighborSuccessors);

                if(neighborScore < score){
                    s = neighbor;
                    break;
                } else {
                    int diff = score-neighborScore;
                    double lambda = diff != 0 ? -diff : 1 / T;
                    double x = Math.log(1-random.nextDouble())/(-lambda);
                    double p = random.nextDouble();

                    if(x < p){
                        s = neighbor;
                    }
                }

            }

            T = T * alpha;
        }
    }
}