package Commonalities;

import MetaHeuristics.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CommonalitiesMiner {
    public List<Solution> db;
    public int minSupport;

    public CommonalitiesMiner(List<Solution> db, int minSupport) {
        this.db = db;
        this.minSupport = minSupport;
    }

    public HashMap<Short, ArrayList<Commonality>> mine(){
        HashMap<Short, ArrayList<Commonality>> commonalities = new HashMap<>();

        db.get(0).ordering.forEach(v -> commonalities.put(v, updateVertex(v)));
        commonalities.forEach((vertex, commonalitiesList) -> {
            ArrayList<Commonality> filtered = commonalitiesList.stream().filter(commonality -> commonality.getSupport() >= minSupport).collect(Collectors.toCollection(ArrayList::new));
            commonalities.replace(vertex, filtered);
        });

        return commonalities;
    }

    public ArrayList<Commonality> updateVertex(Short v){
        ArrayList<Commonality> vertexCommonalities = new ArrayList<>();

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

            Commonality commonality = new Commonality(v, maxPredecessor, minSuccessor);

            if(vertexCommonalities.contains(commonality)){
                commonality = vertexCommonalities.get(vertexCommonalities.indexOf(commonality));
            } else {
                vertexCommonalities.add(commonality);
            }
            commonality.setSupport(commonality.getSupport() + 1);
        }

        return vertexCommonalities;

    }

}
