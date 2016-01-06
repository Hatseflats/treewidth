import Graph.Graph;
import Graph.GraphReader;
import Graph.Vertex;

import LocalSearch.LocalSearch;
import LocalSearch.Solution;
import LocalSearch.SimulatedAnnealing;

import FrequentItemSetMining.FrequentSequenceMiner;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        GraphReader gr = new GraphReader("myciel5.col");
        Graph g = gr.read();
        Solution initialSolution = new Solution(g.maxMinDegree());
//        Solution initialSolution = new Solution(new ArrayList<Vertex>(g.adjacencyList.keySet()));
//        System.out.println(initialSolution.ordering);
        Random random = new Random(13999234);
//        LocalSearch LS = new TabuSearch(21);

        ArrayList<ArrayList<Vertex>> solutions = new ArrayList<>();

        for(int i=0; i<10; i++) {
            LocalSearch LS = new SimulatedAnnealing(100, 0.99, random);
            long startTime = System.nanoTime();
            Solution s = LS.run(g, initialSolution);
            HashMap<Vertex, Set<Vertex>> currentSuccessors = LS.triangulate(s, g.copy());

//            System.out.println(LS.treeWidth(currentSuccessors) + " - " + s.ordering);
            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / 1000000000.0;
//            System.out.println(duration);
            solutions.add(s.ordering);
        }


        FrequentSequenceMiner freq = new FrequentSequenceMiner(solutions, g.adjacencyList);
        ArrayList<Pattern> patterns = freq.freqAdjVertex(10);

        System.out.println(patterns);
    }
}
