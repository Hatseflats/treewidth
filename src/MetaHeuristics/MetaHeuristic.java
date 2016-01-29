package MetaHeuristics;

import Graph.Graph;
import MetaHeuristics.ScoreStrategy.ScoreStrategy;

import java.util.*;
import java.util.stream.Collectors;

public abstract class MetaHeuristic {
    public ScoreStrategy scoreStrategy;

    public MetaHeuristic(ScoreStrategy scoreStrategy){
        this.scoreStrategy = scoreStrategy;
    }

    public HashMap<Short, Set<Short>> triangulate(Solution s, Graph g) {
        HashMap<Short, Set<Short>> successors = new HashMap<>();
        HashSet<Short> masked = new HashSet<>();

        for (Short v: s.ordering) {
            Set<Short> neighborhood = g.neighborhood(v);
            neighborhood.removeAll(masked);
            successors.put(v,neighborhood);
            g.makeClique(neighborhood);
            masked.add(v);
        }

        return successors;
    }

    public int treeWidth(Solution solution){
        List<Integer> sizes = solution.successors.values().stream().map(s -> s.size()).collect(Collectors.toList());
        return Collections.max(sizes);
    }

}
