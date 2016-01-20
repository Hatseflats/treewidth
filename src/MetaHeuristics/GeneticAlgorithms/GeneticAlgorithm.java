package MetaHeuristics.GeneticAlgorithms;

import MetaHeuristics.GeneticAlgorithms.Crossover.Crossover;
import MetaHeuristics.GeneticAlgorithms.Mutation.Mutation;
import MetaHeuristics.GeneticAlgorithms.Selection.Selection;
import MetaHeuristics.MetaHeuristic;
import MetaHeuristics.ScoreStrategy.ScoreStrategy;
import MetaHeuristics.Solution;
import Graph.Graph;

public class GeneticAlgorithm extends MetaHeuristic {
    public Mutation mutation;
    public Crossover crossover;
    public Selection selection;

    public GeneticAlgorithm(ScoreStrategy score){
        super(score);
    }

    public Solution run(Graph g){

        return new Solution();
    }

}
