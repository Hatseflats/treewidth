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
import MetaHeuristics.LocalSearch.SimulatedAnnealing;
import MetaHeuristics.LocalSearch.TabuSearch;
import MetaHeuristics.ScoreStrategy.EdgeCommonalityscore;
import MetaHeuristics.ScoreStrategy.NormalScore;
import MetaHeuristics.ScoreStrategy.ScoreStrategy;
import MetaHeuristics.ScoreStrategy.SuccPredCommonalityScore;
import MetaHeuristics.Solution;

import java.util.*;



public class Main {
    public static void main(String[] args){
        GAGA();
//        GraphReader gr = new GraphReader("games120.col");
//        Graph g = gr.read();
//
//        Random random = new Random(125512512);
//        ScoreStrategy normalScore = new NormalScore();
//
//        SimulatedAnnealing SA2 = new SimulatedAnnealing(normalScore, 300, 0.999, random, 2000);
//        SA2.run(g);


    }

    public static void GAGA() {
        GraphReader gr = new GraphReader("games120.col");
        Graph g = gr.read();

        Random random = new Random(235532532);

        int populationsize = 800;
        int maxGenerations = 800;
        int edgeSupport = 600;
        int spSupport = 400;

        int maxIterations = 4000;

        int nonImprovingIterations = 2000;
        int T = 350;
        double alpha = 0.9992;

        Crossover positionCrossover = new PositionCrossover(random, 1.00);
        Mutation insertionMutation = new InsertionMutation(random, 0.30);
        Selection tournamentSelection = new TournamentSelection(random, 3);
        ScoreStrategy normalScore = new NormalScore();
        GeneticAlgorithm GA = new GeneticAlgorithm(normalScore,insertionMutation,tournamentSelection,positionCrossover,maxGenerations,populationsize, random);
        System.out.println("INIT _-------------------;");

        long startTime = System.nanoTime();
        List<Solution> population = GA.run(g);
        long endTime = System.nanoTime();

        double duration = (endTime - startTime) / 1000000000.0;
        System.out.println(duration);
        System.out.println();

        CommonalitiesMiner commonalitiesMiner = new CommonalitiesMiner(population);

        HashMap<Short, Map<Short, Integer>> edgeCounts = commonalitiesMiner.getEdgeCommonalities(edgeSupport);
        HashMap<Short, ArrayList<Commonalities.SuccPredCommonality>> commonalities = commonalitiesMiner.getSuccPredCommonalities(spSupport);
        edgeCounts.forEach((k,v) -> {
            System.out.println(k + " " + v);

        });
        commonalities.forEach((k,v) -> {
            System.out.println(k + " " + v);

        });
        ScoreStrategy predSuccScore = new SuccPredCommonalityScore(commonalities);

        ScoreStrategy commonalityScore = new EdgeCommonalityscore(edgeCounts);
        GeneticAlgorithm GA2 = new GeneticAlgorithm(predSuccScore,insertionMutation,tournamentSelection,positionCrossover,maxGenerations,populationsize, random);
        System.out.println("GA _-------------------;");

        long startTime2 = System.nanoTime();
        List<Solution> population2 = GA2.run(g);
        long endTime2 = System.nanoTime();

        double duration2 = (endTime2 - startTime2) / 1000000000.0;
        System.out.println(duration2);

        GeneticAlgorithm GA3 = new GeneticAlgorithm(commonalityScore,insertionMutation,tournamentSelection,positionCrossover,maxGenerations,populationsize, random);

        long startTime3 = System.nanoTime();
        List<Solution> population3 = GA3.run(g);
        long endTime3 = System.nanoTime();

        double duration3 = (endTime3 - startTime3) / 1000000000.0;
        System.out.println(duration3);
        System.out.println("TS _-------------------;");

        TabuSearch TS = new TabuSearch(predSuccScore, 51, random, maxIterations);

        long startTime4 = System.nanoTime();
        Solution s1 = TS.run(g);
        long endTime4 = System.nanoTime();
        System.out.println(TS.treeWidth(s1));

        double duration4 = (endTime4 - startTime4) / 1000000000.0;
        System.out.println(duration4);

        TabuSearch TS2 = new TabuSearch(commonalityScore, 51, random, maxIterations);

        long startTime5 = System.nanoTime();
        Solution s2 = TS2.run(g);
        long endTime5 = System.nanoTime();
        System.out.println(TS.treeWidth(s2));


        double duration5 = (endTime5 - startTime5) / 1000000000.0;
        System.out.println(duration5);
        System.out.println("SA _-------------------;");
        SimulatedAnnealing SA = new SimulatedAnnealing(predSuccScore, T, alpha, random, nonImprovingIterations);

        long startTime6 = System.nanoTime();
        Solution s3 = SA.run(g);
        long endTime6 = System.nanoTime();
        System.out.println(SA.treeWidth(s3));

        double duration6 = (endTime6 - startTime6) / 1000000000.0;
        System.out.println(duration6);

        SimulatedAnnealing SA2 = new SimulatedAnnealing(commonalityScore, T, alpha, random, nonImprovingIterations);

        long startTime7 = System.nanoTime();
        Solution s4 = SA2.run(g);
        long endTime7 = System.nanoTime();
        System.out.println(SA.treeWidth(s4));

        double duration7 = (endTime7 - startTime7) / 1000000000.0;
        System.out.println(duration7);
    }

    public static void GATS() {
        GraphReader gr = new GraphReader("queen8_8.col");
        Graph g = gr.read();

        Random random = new Random(32525532);

        int populationsize = 1000;
        int maxGenerations = 1000;
        int edgeSupport = 750;
        int spSupport = 400;

        Crossover positionCrossover = new PositionCrossover(random, 1.00);
        Mutation insertionMutation = new InsertionMutation(random, 0.30);
        Selection tournamentSelection = new TournamentSelection(random, 3);
        ScoreStrategy normalScore = new NormalScore();
        GeneticAlgorithm GA = new GeneticAlgorithm(normalScore,insertionMutation,tournamentSelection,positionCrossover,maxGenerations,populationsize, random);

        long startTime = System.nanoTime();
        List<Solution> population = GA.run(g);
        long endTime = System.nanoTime();

        double duration = (endTime - startTime) / 1000000000.0;
        System.out.println(duration);
        System.out.println();

        CommonalitiesMiner commonalitiesMiner = new CommonalitiesMiner(population);

        HashMap<Short, Map<Short, Integer>> edgeCounts = commonalitiesMiner.getEdgeCommonalities(edgeSupport);
        HashMap<Short, ArrayList<Commonalities.SuccPredCommonality>> commonalities = commonalitiesMiner.getSuccPredCommonalities(spSupport);
        edgeCounts.forEach((k,v) -> {
            System.out.println(k + " " + v);

        });
        commonalities.forEach((k,v) -> {
            System.out.println(k + " " + v);

        });
        ScoreStrategy predSuccScore = new SuccPredCommonalityScore(commonalities);

        ScoreStrategy commonalityScore = new EdgeCommonalityscore(edgeCounts);
        TabuSearch TS = new TabuSearch(predSuccScore, 51, random, 30000);

        long startTime2 = System.nanoTime();
        Solution s1 = TS.run(g);
        long endTime2 = System.nanoTime();
        System.out.println(TS.treeWidth(s1));

        double duration2 = (endTime2 - startTime2) / 1000000000.0;
        System.out.println(duration2);

        TabuSearch TS2 = new TabuSearch(commonalityScore, 51, random, 30000);

        long startTime3 = System.nanoTime();
        Solution s2 = TS2.run(g);
        long endTime3 = System.nanoTime();
        System.out.println(TS.treeWidth(s2));


        double duration3 = (endTime3 - startTime3) / 1000000000.0;
        System.out.println(duration3);
    }

    public static void GASA() {
        GraphReader gr = new GraphReader("queen8_8.col");
        Graph g = gr.read();

        Random random = new Random(12563434);

        int populationsize = 1000;
        int maxGenerations = 1000;
        int edgeSupport = 750;
        int spSupport = 400;

        Crossover positionCrossover = new PositionCrossover(random, 1.00);
        Mutation insertionMutation = new InsertionMutation(random, 0.30);
        Selection tournamentSelection = new TournamentSelection(random, 3);
        ScoreStrategy normalScore = new NormalScore();
        GeneticAlgorithm GA = new GeneticAlgorithm(normalScore,insertionMutation,tournamentSelection,positionCrossover,maxGenerations,populationsize, random);

        long startTime = System.nanoTime();
        List<Solution> population = GA.run(g);
        long endTime = System.nanoTime();

        double duration = (endTime - startTime) / 1000000000.0;
        System.out.println(duration);
        System.out.println();

        CommonalitiesMiner commonalitiesMiner = new CommonalitiesMiner(population);

        HashMap<Short, Map<Short, Integer>> edgeCounts = commonalitiesMiner.getEdgeCommonalities(edgeSupport);
        HashMap<Short, ArrayList<Commonalities.SuccPredCommonality>> commonalities = commonalitiesMiner.getSuccPredCommonalities(spSupport);
        edgeCounts.forEach((k,v) -> {
            System.out.println(k + " " + v);

        });
        commonalities.forEach((k,v) -> {
            System.out.println(k + " " + v);

        });
        ScoreStrategy predSuccScore = new SuccPredCommonalityScore(commonalities);

        ScoreStrategy commonalityScore = new EdgeCommonalityscore(edgeCounts);
        SimulatedAnnealing SA = new SimulatedAnnealing(predSuccScore, 200, 0.999, random, 3000);

        long startTime2 = System.nanoTime();
        Solution s1 = SA.run(g);
        long endTime2 = System.nanoTime();
        System.out.println(SA.treeWidth(s1));

        double duration2 = (endTime2 - startTime2) / 1000000000.0;
        System.out.println(duration2);

        SimulatedAnnealing SA2 = new SimulatedAnnealing(commonalityScore, 200, 0.999, random, 3000);

        long startTime3 = System.nanoTime();
        Solution s2 = SA2.run(g);
        long endTime3 = System.nanoTime();
        System.out.println(SA.treeWidth(s2));

        double duration3 = (endTime3 - startTime3) / 1000000000.0;
        System.out.println(duration3);
    }


}
