package LocalSearch.ScoreStrategy;

import Graph.Vertex;

import java.util.HashMap;
import java.util.Set;

public interface ScoreStrategy {
    public int score(HashMap<Vertex, Set<Vertex>> successors);
}
