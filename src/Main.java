import Graph.Graph;
import Graph.GraphReader;
import Graph.Vertex;

import LocalSearch.LocalSearch;
import LocalSearch.Solution;
import LocalSearch.TabuSearch;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        GraphReader gr = new GraphReader("myciel5.col");
        Graph g = gr.read();
        Solution initialSolution = new Solution(g.maxMinDegree());
//        LocalSearch.LocalSearch LS = new LocalSearch.LocalSearch.SimulatedAnnealing(5000, 0.99, 909090999);


        LocalSearch LS = new TabuSearch(21);

        for(int i=0; i<1; i++) {
            long startTime = System.nanoTime();
            Solution s = LS.run(g, initialSolution);
            HashMap<Vertex, Set<Vertex>> currentSuccessors = LS.triangulate(s, g.copy());

            System.out.println(LS.treeWidth(currentSuccessors) + " - " + s.ordering);
            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / 1000000000.0;
            System.out.println(duration);
        }

    }
}
