import java.util.*;

public class Main {
    public static void main(String[] args) {
        GraphReader gr = new GraphReader("myciel5.col");
        Graph g = gr.read();
//        Solution initialSolution = new Solution(g.maximumMinimumDegree());
        Solution initialSolution = new Solution(g.maxMinDegree());
        LocalSearch SA = new SimulatedAnnealing(5000, 0.99, 909090999);
//        LocalSearch TS = new TabuSearch(21);

        for(int i=0; i<10; i++) {
            long startTime = System.nanoTime();
            Solution s = SA.run(g, initialSolution);
            HashMap<Vertex, Set<Vertex>> currentSuccessors = SA.triangulate(s, g.copy());

            System.out.println(SA.treeWidth(currentSuccessors) + " - " + s.ordering);
            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / 1000000000.0;
            System.out.println(duration);
        }

    }
}
