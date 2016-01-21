package MetaHeuristics.GeneticAlgorithms;

import MetaHeuristics.GeneticAlgorithms.Crossover.Crossover;
import MetaHeuristics.GeneticAlgorithms.Mutation.Mutation;
import MetaHeuristics.GeneticAlgorithms.Selection.Selection;
import MetaHeuristics.MetaHeuristic;
import MetaHeuristics.ScoreStrategy.ScoreStrategy;

abstract class GeneticAlgorithm extends MetaHeuristic {
    public Mutation mutation;
    public Crossover crossover;
    public Selection selection;
    public int maxGenerations;
    public int populationSize;

    public GeneticAlgorithm(ScoreStrategy scoreStrategy, Mutation mutation, Crossover crossover, Selection selection, int maxGenerations, int populationSize) {
        super(scoreStrategy);
        this.mutation = mutation;
        this.crossover = crossover;
        this.selection = selection;
        this.maxGenerations = maxGenerations;
        this.populationSize = populationSize;
    }
}
