import FrequentPathMiner.Tree;
import Graph.Graph;
import Graph.GraphReader;
import Graph.Vertex;

import MetaHeuristics.GeneticAlgorithms.Crossover.Crossover;
import MetaHeuristics.GeneticAlgorithms.Crossover.PositionCrossover;
import MetaHeuristics.GeneticAlgorithms.GeneticAlgorithm;
import MetaHeuristics.GeneticAlgorithms.Selection.Selection;
import MetaHeuristics.GeneticAlgorithms.Selection.TournamentSelection;
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
        GraphReader gr = new GraphReader("myciel5.col");
        Graph g = gr.read();

        Random random = new Random();

        Crossover positionCrossover = new PositionCrossover(random, 0.00);
        Mutation insertionMutation = new InsertionMutation(random, 1.00);
        Selection tournamentSelection = new TournamentSelection(random, 2);
        ScoreStrategy normalScore = new NormalScore();
        GeneticAlgorithm GA = new GeneticAlgorithm(normalScore,insertionMutation,tournamentSelection,positionCrossover,5,20, random);

        Solution s = GA.run(g);

    }

    public static ArrayList<Solution> runSimulatedAnnealing(Graph g, ScoreStrategy score, Random random) {
        ArrayList<Solution> solutions = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            MetaHeuristic LS = new SimulatedAnnealing(score, 100, 0.99, random, 400);
            Solution s = LS.run(g);

            System.out.println(LS.treeWidth(s) + " - " + s.ordering);

            solutions.add(s);
        }

        return solutions;
    }



//    public static void freqPathMining(){
//        GraphReader gr = new GraphReader("myciel5.col");
//        Graph g = gr.read();
//        Random random = new Random(12344679);
//        Solution initialSolution = new Solution(g.maxMinDegree(random));
//
//        ScoreStrategy score = new NormalScore();
//
//        List<ArrayList<Vertex>> solutions = runSimulatedAnnealing(g, score, random).stream().map(s -> s.ordering).collect(Collectors.toList());
//
//        FrequentPathMiner freq = new FrequentPathMiner(solutions, g.adjacencyList, 25);
//        Tree tree = freq.frequentPathTree();
//
//
//        Random random1 = new Random(195315545);
//
//        ArrayList<Path> paths = tree.getPaths();
//        ScoreStrategy path = new FrequentPathScore(paths);
//        TabuSearch pathTabuSearch = new TabuSearch(path, 50, random1);
//        Solution sol1 = pathTabuSearch.run(g);
//
//        System.out.println(sol1.ordering);
//        System.out.println(pathTabuSearch.treeWidth(sol1) + " - " + pathTabuSearch.scoreStrategy.score(sol1));
//
//        Random random2 = new Random(195315545);
//
//        TabuSearch standardTabuSearch = new TabuSearch(score, 50, random2);
//        Solution sol2 = standardTabuSearch.run(g);
//
//        System.out.println(sol2.ordering);
//        System.out.println(standardTabuSearch.treeWidth(sol2) + " - " + standardTabuSearch.scoreStrategy.score(sol2));
//    }
}
