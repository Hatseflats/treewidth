package MetaHeuristics.ScoreStrategy;

import MetaHeuristics.Solution;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EdgeCommonalityscore implements ScoreStrategy {
    public HashMap<Short, HashMap<Short, Integer>> edgeCounts;

    public EdgeCommonalityscore(HashMap<Short, HashMap<Short, Integer>> edgeCounts) {
        this.edgeCounts = edgeCounts;
    }

    public int score(Solution solution) {
        int n = solution.ordering.size();
        List<Integer> sizes = solution.successors.values().stream().map(s -> s.size()).collect(Collectors.toList());
        int w = Collections.max(sizes);
        int succ = sizes.stream().mapToInt(i -> i^2).sum();

        int score = (n*n)*(w*w)+succ;

        for(Short v: solution.ordering) {
            HashMap<Short, Integer> counts = edgeCounts.get(v);
            Set<Short> edges = solution.successors.get(v);

            for (Short u : edges) {
                if (counts.containsKey(u)) {
                    int count = counts.get(u);
                    score -= 3 * count * count;
                }
            }
        }

        return score;
    }
}
