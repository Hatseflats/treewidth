import java.util.*;

public class Main {
    public static void main(String[] args) {
        GraphReader gr = new GraphReader("myciel5.col");
        Graph g = gr.read();
//        Solution initialSolution = new Solution(g.maximumMinimumDegree());
        Solution initialSolution = new Solution(g.maxMinDegree());
//        LocalSearch SA = new SimulatedAnnealing(initialSolution, 5000, 0.99, 909090999);
        LocalSearch TS = new TabuSearch(initialSolution, 21);
        for(int i=0; i<1; i++) {
            long startTime = System.nanoTime();
            Solution s = TS.run(g);
            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / 1000000000.0;
            System.out.println(duration);
        }
//
//        Solution s = new Solution(g.maxMinDegree());
//        System.out.println(s.ordering);
//        System.out.println(g.maximumMinimumDegree());

    }
}
