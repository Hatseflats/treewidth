package MetaHeuristics.GeneticAlgorithms.Selection;

import MetaHeuristics.Solution;

import java.util.Collection;

public interface Selection {
    Collection<Solution> selection(Collection<Solution> solutions);
}
