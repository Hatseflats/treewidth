package MetaHeuristics.GeneticAlgorithms.Mutation;

import MetaHeuristics.Solution;

import java.util.Random;

public interface Mutation {

    void mutate(Solution s, Random random);
}
