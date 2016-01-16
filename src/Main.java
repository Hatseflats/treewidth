import Commonalities.CommonalitiesMiner;
import Commonalities.Commonality;
import FrequentPathMiner.Tree;
import Graph.Graph;
import Graph.GraphReader;
import Graph.Vertex;

import LocalSearch.LocalSearch;
import LocalSearch.ScoreStrategy.CommonalityScore;
import LocalSearch.ScoreStrategy.FrequentPathScore;
import LocalSearch.ScoreStrategy.NormalScore;
import LocalSearch.ScoreStrategy.ScoreStrategy;
import LocalSearch.Solution;
import LocalSearch.SimulatedAnnealing;
import LocalSearch.TabuSearch;

import FrequentPathMiner.FrequentPathMiner;
import FrequentPathMiner.Path;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        GraphReader gr = new GraphReader("queen6_6.col");
        Graph g = gr.read();
        Solution initialSolution = new Solution(g.maxMinDegree());
        Random random = new Random(1234443679);
        ScoreStrategy score = new NormalScore();

        ArrayList<Solution> solutions = runSimulatedAnnealing(g, score, random, initialSolution);

        CommonalitiesMiner commonalitiesMiner = new CommonalitiesMiner(solutions, 5);

        HashMap<Vertex, ArrayList<Commonality>> commonalities = commonalitiesMiner.mine();

        System.out.println(commonalities);

        ScoreStrategy commonalityScore = new CommonalityScore(commonalities);

        Random random1 = new Random(194315545);

        SimulatedAnnealing pathTabuSearch = new SimulatedAnnealing(commonalityScore, 2000, 0.99, random1, 2000);
        Solution sol1 = pathTabuSearch.run(g, new Solution(g.maxMinDegree()));

        HashMap<Vertex, Set<Vertex>> successors1 = pathTabuSearch.triangulate(sol1, g.copy());
        System.out.println(sol1.ordering);
        System.out.println(pathTabuSearch.treeWidth(successors1) + " - " + pathTabuSearch.scoreStrategy.score(sol1));

        Random random2 = new Random(194315545);

        SimulatedAnnealing standardTabuSearch = new SimulatedAnnealing(score, 2000, 0.99, random2, 2000);
        Solution sol2 = standardTabuSearch.run(g, new Solution(g.maxMinDegree()));

        HashMap<Vertex, Set<Vertex>> successors2 = standardTabuSearch.triangulate(sol2, g.copy());
        System.out.println(sol2.ordering);
        System.out.println(standardTabuSearch.treeWidth(successors2) + " - " + standardTabuSearch.scoreStrategy.score(sol2));



    }

    public static ArrayList<Solution> runSimulatedAnnealing(Graph g, ScoreStrategy score, Random random, Solution initialSolution) {
        ArrayList<Solution> solutions = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            LocalSearch LS = new SimulatedAnnealing(score, 2000, 0.99, random, 2000);
            Solution s = LS.run(g, initialSolution);

            System.out.println(LS.treeWidth(s.successors) + " - " + s.ordering);

            solutions.add(s);
        }

        return solutions;
    }



    public static void freqPathMining(){
        GraphReader gr = new GraphReader("myciel5.col");
        Graph g = gr.read();
        Solution initialSolution = new Solution(g.maxMinDegree());
        Random random = new Random(12344679);
        ScoreStrategy score = new NormalScore();

        List<ArrayList<Vertex>> solutions = runSimulatedAnnealing(g, score, random, initialSolution).stream().map(s -> s.ordering).collect(Collectors.toList());

        FrequentPathMiner freq = new FrequentPathMiner(solutions, g.adjacencyList, 25);
        Tree tree = freq.frequentPathTree();


        Random random1 = new Random(195315545);

        ArrayList<Path> paths = tree.getPaths();
        ScoreStrategy path = new FrequentPathScore(paths);
        TabuSearch pathTabuSearch = new TabuSearch(path, 50);
        Solution sol1 = pathTabuSearch.run(g, new Solution(g.maxMinDegree()));

        HashMap<Vertex, Set<Vertex>> successors1 = pathTabuSearch.triangulate(sol1, g.copy());
        System.out.println(sol1.ordering);
        System.out.println(pathTabuSearch.treeWidth(successors1) + " - " + pathTabuSearch.scoreStrategy.score(sol1));

        Random random2 = new Random(195315545);

        TabuSearch standardTabuSearch = new TabuSearch(score, 50);
        Solution sol2 = standardTabuSearch.run(g, new Solution(g.maxMinDegree()));

        HashMap<Vertex, Set<Vertex>> successors2 = standardTabuSearch.triangulate(sol2, g.copy());
        System.out.println(sol2.ordering);
        System.out.println(standardTabuSearch.treeWidth(successors2) + " - " + standardTabuSearch.scoreStrategy.score(sol2));
    }
}
