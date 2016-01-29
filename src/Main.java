import Commonalities.CommonalitiesMiner;
import Commonalities.Commonality;
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
import MetaHeuristics.ScoreStrategy.CommonalityScore;
import MetaHeuristics.ScoreStrategy.NormalScore;
import MetaHeuristics.ScoreStrategy.ScoreStrategy;
import MetaHeuristics.Solution;
import MetaHeuristics.LocalSearch.SimulatedAnnealing;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        GraphReader gr = new GraphReader("dsjc125.1.col");
        Graph g = gr.read();

        Random random = new Random(76766776);

        Crossover positionCrossover = new PositionCrossover(random, 1.00);
        Mutation insertionMutation = new InsertionMutation(random, 0.30);
        Selection tournamentSelection = new TournamentSelection(random, 3);
        ScoreStrategy normalScore = new NormalScore();
        GeneticAlgorithm GA = new GeneticAlgorithm(normalScore,insertionMutation,tournamentSelection,positionCrossover,300,100, random);

        long startTime = System.nanoTime();
        List<Solution> population = GA.run(g);
        long endTime = System.nanoTime();

        double duration = (endTime - startTime) / 1000000000.0;
        System.out.println(duration);

        CommonalitiesMiner commonalitiesMiner = new CommonalitiesMiner(population,25);
        HashMap<Short, ArrayList<Commonality>> commonalities = commonalitiesMiner.mine();

        System.out.println(commonalities);

        Crossover positionCrossover2 = new PositionCrossover(random, 1.00);
        Mutation insertionMutation2 = new InsertionMutation(random, 0.30);
        Selection tournamentSelection2 = new TournamentSelection(random, 2);
        ScoreStrategy CommonalityScore = new CommonalityScore(commonalities);
        GeneticAlgorithm GA2 = new GeneticAlgorithm(normalScore,insertionMutation,tournamentSelection,positionCrossover,300,100, random);

        long startTime2 = System.nanoTime();
        List<Solution> population2 = GA.run(g);
        long endTime2 = System.nanoTime();

        double duration2 = (endTime - startTime) / 1000000000.0;
        System.out.println(duration);
    }
}
