import FrequentPathMiner.Tree;
import Graph.Graph;
import Graph.GraphReader;
import Graph.Vertex;

import LocalSearch.LocalSearch;
import LocalSearch.ScoreStrategy.NormalScore;
import LocalSearch.ScoreStrategy.ScoreStrategy;
import LocalSearch.Solution;
import LocalSearch.SimulatedAnnealing;

import FrequentPathMiner.FrequentPathMiner;
import FrequentPathMiner.Path;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        GraphReader gr = new GraphReader("myciel3.col");
        Graph g = gr.read();
        Solution initialSolution = new Solution(g.maxMinDegree());
        Random random = new Random(545445545);
        ScoreStrategy score = new NormalScore();

        ArrayList<ArrayList<Vertex>> solutions = new ArrayList<>();

        for(int i=0; i<10; i++) {
            LocalSearch LS = new SimulatedAnnealing(score, 100, 0.99, random);
            Solution s = LS.run(g, initialSolution);

            HashMap<Vertex, Set<Vertex>> currentSuccessors = LS.triangulate(s, g.copy());
            System.out.println(LS.treeWidth(currentSuccessors) + " - " + s.ordering);

            solutions.add(s.ordering);
        }

        FrequentPathMiner freq = new FrequentPathMiner(solutions, g.adjacencyList, 2);
        Tree tree = freq.frequentPathTree();
        for(Path p:tree.getPaths()){
            System.out.println(p);
        }

    }
}
