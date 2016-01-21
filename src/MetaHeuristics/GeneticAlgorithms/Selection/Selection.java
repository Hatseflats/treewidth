package MetaHeuristics.GeneticAlgorithms.Selection;

import MetaHeuristics.Solution;

import java.util.Collection;
import java.util.List;

public interface Selection {
    List<Solution> selection(List<Solution> solutions);
}
