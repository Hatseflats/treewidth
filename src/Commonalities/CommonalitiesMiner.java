package Commonalities;

import Graph.Vertex;
import MetaHeuristics.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class CommonalitiesMiner {
    public ArrayList<Solution> db;
    public int minSupport;

    public CommonalitiesMiner(ArrayList<Solution> db, int minSupport) {
        this.db = db;
        this.minSupport = minSupport;
    }

    public HashMap<Vertex, ArrayList<Commonality>> mine(){
        HashMap<Vertex, ArrayList<Commonality>> commonalities = new HashMap<>();

        db.get(0).ordering.forEach(v -> commonalities.put(v, updateVertex(v)));
        commonalities.forEach((vertex, commonalitiesList) -> {
            ArrayList<Commonality> filtered = commonalitiesList.stream().filter(commonality -> commonality.getSupport() >= minSupport).collect(Collectors.toCollection(ArrayList::new));
            commonalities.replace(vertex, filtered);
        });

        return commonalities;
    }

    public ArrayList<Commonality> updateVertex(Vertex v){
        ArrayList<Commonality> vertexCommonalities = new ArrayList<>();

        for(Solution solution:db){
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
