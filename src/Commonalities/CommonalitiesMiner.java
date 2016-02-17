package Commonalities;

import MetaHeuristics.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommonalitiesMiner {
    public List<Solution> db;

    public CommonalitiesMiner(List<Solution> db) {
        this.db = db;
    }

    public HashMap<Short, ArrayList<SuccPredCommonality>> getSuccPredCommonalities(int succPredSupport){
        HashMap<Short, ArrayList<SuccPredCommonality>> commonalities = new HashMap<>();

        db.get(0).ordering.forEach(v -> commonalities.put(v, updateVertex(v)));
        commonalities.forEach((vertex, commonalitiesList) -> {
            ArrayList<SuccPredCommonality> filtered = commonalitiesList.stream().filter(commonality -> commonality.getSupport() >= succPredSupport).collect(Collectors.toCollection(ArrayList::new));
            commonalities.replace(vertex, filtered);
        });

        return commonalities;
    }

    public ArrayList<SuccPredCommonality> updateVertex(Short v){
        ArrayList<SuccPredCommonality> vertexCommonalities = new ArrayList<>();

        for(Solution solution:db){
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

            SuccPredCommonality succPredCommonality = new SuccPredCommonality(v, maxPredecessor, minSuccessor);

            if(vertexCommonalities.contains(succPredCommonality)){
                succPredCommonality = vertexCommonalities.get(vertexCommonalities.indexOf(succPredCommonality));
            } else {
                vertexCommonalities.add(succPredCommonality);
            }
            succPredCommonality.setSupport(succPredCommonality.getSupport() + 1);
        }

        return vertexCommonalities;

    }

    public HashMap<Short, Map<Short, Integer>> getEdgeCommonalities(int edgeSupport){
        HashMap<Short, Map<Short, Integer>> edgeCommonalities = new HashMap<>();

        db.get(0).ordering.forEach(v -> {
            HashMap<Short, Integer> edgeCounts = new HashMap<Short, Integer>();
            Map<Short, Integer> accepted = new HashMap<Short, Integer>();

            db.forEach(s -> {
                s.successors.get(v).forEach(w -> edgeCounts.compute(w, (key,value) -> value == null ? 1 : value+1));
            });
            accepted = edgeCounts.entrySet().stream()
                    .filter(p -> p.getValue() > edgeSupport)
                    .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
            edgeCommonalities.put(v, accepted);
        });



        return edgeCommonalities;
    }

}
