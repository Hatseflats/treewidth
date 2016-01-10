package LocalSearch.ScoreStrategy;

import Graph.Vertex;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NormalScore implements ScoreStrategy {
    public int score(HashMap<Vertex, Set<Vertex>> successors){
        int n = successors.keySet().size();
        List<Integer> sizes = successors.values().stream().map(s -> s.size()).collect(Collectors.toList());
        int w = Collections.max(sizes);
        int succ = sizes.stream().mapToInt(i -> i^2).sum();

        return (n*n)*(w*w)+succ;
    }
}
