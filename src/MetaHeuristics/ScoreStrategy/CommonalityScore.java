package MetaHeuristics.ScoreStrategy;

import Commonalities.Commonality;
import Graph.Vertex;
import MetaHeuristics.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sebastiaan on 15-1-16.
 */
public class CommonalityScore implements ScoreStrategy {

    public HashMap<Vertex, ArrayList<Commonality>> commonalities;

    public CommonalityScore(HashMap<Vertex, ArrayList<Commonality>> commonalities) {
        this.commonalities = commonalities;
    }

    public int score(Solution solution){
        int n = solution.ordering.size();
        List<Integer> sizes = solution.successors.values().stream().map(s -> s.size()).collect(Collectors.toList());
        int w = Collections.max(sizes);
        int succ = sizes.stream().mapToInt(i -> i^2).sum();

        int score = (n*n)*(w*w)+succ;

        for(Vertex v: solution.ordering){
            int maxPredIndex = solution.maxPredecessor(v);
            int minSuccIndex = solution.minSuccessor(v);

            Vertex maxPredecessor = new Vertex(-1);

            if(maxPredIndex != -1){
                maxPredecessor = solution.ordering.get(maxPredIndex);
            }

            Vertex minSuccessor = new Vertex(-1);

            if(minSuccIndex != -1){
                minSuccessor = solution.ordering.get(minSuccIndex);
            }

            Commonality commonality = new Commonality(v, maxPredecessor, minSuccessor);
            ArrayList<Commonality> vertexCommonalities = commonalities.get(v);
            if(vertexCommonalities.contains(commonality)){
                commonality = vertexCommonalities.get(vertexCommonalities.indexOf(commonality));
                score -= commonality.getSupport() * commonality.getSupport() * commonality.getSupport();
            }
        }

        return score;
    }
}
