package MetaHeuristics;

import Graph.Graph;
import Graph.Vertex;
import MetaHeuristics.ScoreStrategy.ScoreStrategy;

import java.util.*;
import java.util.stream.Collectors;

public abstract class MetaHeuristic {
    public ScoreStrategy scoreStrategy;

    public MetaHeuristic(ScoreStrategy scoreStrategy){
        this.scoreStrategy = scoreStrategy;
    }

    public abstract Solution run(Graph g);

    public HashMap<Vertex, Set<Vertex>> triangulate(Solution s, Graph g) {
        HashMap<Vertex, Set<Vertex>> successors = new HashMap<>();
        HashSet<Vertex> masked = new HashSet<>();

        for (Vertex v: s.ordering) {
            Set<Vertex> neighborhood = g.neighborhood(v);
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
