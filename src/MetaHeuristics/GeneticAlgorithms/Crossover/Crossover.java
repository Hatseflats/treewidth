package MetaHeuristics.GeneticAlgorithms.Crossover;

import MetaHeuristics.Solution;
import Util.Tuple.Pair;

public interface Crossover {
    public Pair<Solution,Solution> crossover(Solution s1, Solution s2);
}
