package MetaHeuristics.ScoreStrategy;

import Commonalities.SuccPredCommonality;
import MetaHeuristics.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SuccPredCommonalityScore implements ScoreStrategy {

    public HashMap<Short, ArrayList<Commonalities.SuccPredCommonality>> commonalities;

    public SuccPredCommonalityScore(HashMap<Short, ArrayList<Commonalities.SuccPredCommonality>> commonalities) {
        this.commonalities = commonalities;
    }

    public int score(Solution solution){
        int n = solution.ordering.size();
        List<Integer> sizes = solution.successors.values().stream().map(s -> s.size()).collect(Collectors.toList());
        int w = Collections.max(sizes);
        int succ = sizes.stream().mapToInt(i -> i^2).sum();

        int score = (n*n)*(w*w)+succ;

        for(Short v: solution.ordering){
            int maxPredIndex = solution.maxPredecessor(v);
            int minSuccIndex = solution.minSuccessor(v);

            Short maxPredecessor = -1;

            if(maxPredIndex != -1){
                maxPredecessor = solution.ordering.get(maxPredIndex);
            }

            Short minSuccessor = -1;

            if(minSuccIndex != -1){
                minSuccessor = solution.ordering.get(minSuccIndex);
            }

            Commonalities.SuccPredCommonality succPredCommonality = new Commonalities.SuccPredCommonality(v, maxPredecessor, minSuccessor);
            ArrayList<Commonalities.SuccPredCommonality> vertexCommonalities = commonalities.get(v);
            if(vertexCommonalities.contains(succPredCommonality)){
                succPredCommonality = vertexCommonalities.get(vertexCommonalities.indexOf(succPredCommonality));
                score -= succPredCommonality.getSupport() * succPredCommonality.getSupport();
            }
        }

        return score;
    }
}
