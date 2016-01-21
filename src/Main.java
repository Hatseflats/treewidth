import FrequentPathMiner.Tree;
import Graph.Graph;
import Graph.GraphReader;
import Graph.Vertex;

import MetaHeuristics.GeneticAlgorithms.Crossover.PositionCrossover;
import MetaHeuristics.MetaHeuristic;
import MetaHeuristics.GeneticAlgorithms.Mutation.InsertionMutation;
import MetaHeuristics.GeneticAlgorithms.Mutation.Mutation;
import MetaHeuristics.ScoreStrategy.FrequentPathScore;
import MetaHeuristics.ScoreStrategy.NormalScore;
import MetaHeuristics.ScoreStrategy.ScoreStrategy;
import MetaHeuristics.Solution;
import MetaHeuristics.LocalSearch.SimulatedAnnealing;
import MetaHeuristics.LocalSearch.TabuSearch;

import FrequentPathMiner.FrequentPathMiner;
import FrequentPathMiner.Path;
import Util.Tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        GraphReader gr = new GraphReader("myciel3.col");
        Graph g = gr.read();

        Random random = new Random(1234443679);

        PositionCrossover pos = new PositionCrossover(random);

        Solution s1 = new Solution(g.maxMinDegree());
        Solution s2 = new Solution(new ArrayList<>(g.vertices.values()));

        System.out.println(s1.ordering);
        System.out.println(s2.ordering);

        Pair<Solution,Solution> offspring = pos.crossover(s1,s2);

        System.out.println(offspring.fst().ordering);
        System.out.println(offspring.snd().ordering);




//        Solution initialSolution = new Solution(g.maxMinDegree());
//        System.out.println(initialSolution.ordering);
//        ScoreStrategy score = new NormalScore();
//        Mutation insertionMutation = new InsertionMutation();
//        insertionMutation.mutate(initialSolution, random);
//        System.out.println(initialSolution.ordering);


//        ArrayList<Solution> solutions = runSimulatedAnnealing(g, score, random, initialSolution);
//
//        CommonalitiesMiner commonalitiesMiner = new CommonalitiesMiner(solutions, 3);
//
//        HashMap<Vertex, ArrayList<Commonality>> commonalities = commonalitiesMiner.mine();
//
//        System.out.println(commonalities);
//
//        ScoreStrategy commonalityScore = new CommonalityScore(commonalities);
//
//        Random random1 = new Random(194315545);
//
//        TabuSearch commonalityTabuSearch = new TabuSearch(commonalityScore, 51, random1);
//        Solution sol1 = commonalityTabuSearch.run(g, new Solution(g.maxMinDegree()));
//        System.out.println(commonalityTabuSearch.treeWidth(sol1.successors) + " - " + commonalityTabuSearch.scoreStrategy.score(sol1));
//
//        Random random2 = new Random(194315545);
//
//        TabuSearch standardTabuSearch = new TabuSearch(score, 51, random2);
//        Solution sol2 = standardTabuSearch.run(g, new Solution(g.maxMinDegree()));
//        System.out.println(standardTabuSearch.treeWidth(sol2.successors) + " - " + standardTabuSearch.scoreStrategy.score(sol2));



    }

    public static ArrayList<Solution> runSimulatedAnnealing(Graph g, ScoreStrategy score, Random random, Solution initialSolution) {
        ArrayList<Solution> solutions = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            MetaHeuristic LS = new SimulatedAnnealing(score, 100, 0.99, random, 400);
            Solution s = LS.run(g);

            System.out.println(LS.treeWidth(s) + " - " + s.ordering);

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
        TabuSearch pathTabuSearch = new TabuSearch(path, 50, random1);
        Solution sol1 = pathTabuSearch.run(g);

        System.out.println(sol1.ordering);
        System.out.println(pathTabuSearch.treeWidth(sol1) + " - " + pathTabuSearch.scoreStrategy.score(sol1));

        Random random2 = new Random(195315545);

        TabuSearch standardTabuSearch = new TabuSearch(score, 50, random2);
        Solution sol2 = standardTabuSearch.run(g);

        System.out.println(sol2.ordering);
        System.out.println(standardTabuSearch.treeWidth(sol2) + " - " + standardTabuSearch.scoreStrategy.score(sol2));
    }
}
