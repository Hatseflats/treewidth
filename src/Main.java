import Commonalities.CommonalitiesMiner;
import Graph.Graph;
import Graph.GraphReader;
import MetaHeuristics.GeneticAlgorithms.Crossover.Crossover;
import MetaHeuristics.GeneticAlgorithms.Crossover.PositionCrossover;
import MetaHeuristics.GeneticAlgorithms.GeneticAlgorithm;
import MetaHeuristics.GeneticAlgorithms.Mutation.InsertionMutation;
import MetaHeuristics.GeneticAlgorithms.Mutation.Mutation;
import MetaHeuristics.GeneticAlgorithms.Selection.Selection;
import MetaHeuristics.GeneticAlgorithms.Selection.TournamentSelection;
import MetaHeuristics.LocalSearch.TabuSearch;
import MetaHeuristics.ScoreStrategy.EdgeCommonalityscore;
import MetaHeuristics.ScoreStrategy.NormalScore;
import MetaHeuristics.ScoreStrategy.ScoreStrategy;
import MetaHeuristics.ScoreStrategy.SuccPredCommonalityScore;
import MetaHeuristics.Solution;

import java.util.*;



public class Main {
    public static void main(String[] args) {
        GraphReader gr = new GraphReader("games120.col");
        Graph g = gr.read();

        Random random = new Random(44);

        Crossover positionCrossover = new PositionCrossover(random, 1.00);
        Mutation insertionMutation = new InsertionMutation(random, 0.30);
        Selection tournamentSelection = new TournamentSelection(random, 3);
        ScoreStrategy normalScore = new NormalScore();
        GeneticAlgorithm GA = new GeneticAlgorithm(normalScore,insertionMutation,tournamentSelection,positionCrossover,500,300, random);

        long startTime = System.nanoTime();
        List<Solution> population = GA.run(g);
        long endTime = System.nanoTime();

        double duration = (endTime - startTime) / 1000000000.0;
        System.out.println(duration);
        System.out.println();

        CommonalitiesMiner commonalitiesMiner = new CommonalitiesMiner(population,100);

        HashMap<Short, HashMap<Short, Integer>> edgeCounts = commonalitiesMiner.getEdgeCommonalities();
        HashMap<Short, ArrayList<Commonalities.SuccPredCommonality>> commonalities = commonalitiesMiner.getSuccPredCommonalities();
        commonalities.forEach((k,v) -> {
            System.out.println(k + " " + v);
        });
        ScoreStrategy predSuccScore = new SuccPredCommonalityScore(commonalities);

//        Crossover positionCrossover2 = new PositionCrossover(random, 1.00);
//        Mutation insertionMutation2 = new InsertionMutation(random, 0.30);
//        Selection tournamentSelection2 = new TournamentSelection(random, 3);
//        ScoreStrategy commonalityScore = new EdgeCommonalityscore(edgeCounts);
//        GeneticAlgorithm GA2 = new GeneticAlgorithm(predSuccScore,insertionMutation2,tournamentSelection2,positionCrossover2,300,200, random);

        TabuSearch TS = new TabuSearch(predSuccScore, 51, random);

        long startTime2 = System.nanoTime();
        Solution s = TS.run(g);
//        List<Solution> population2 = GA2.run(g);
        long endTime2 = System.nanoTime();

        double duration2 = (endTime2 - startTime2) / 1000000000.0;
        System.out.println(duration2);
    }
}
