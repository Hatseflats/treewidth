package Commonalities;

import Graph.Vertex;
import LocalSearch.Solution;

import java.util.ArrayList;
import java.util.HashMap;

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

        return commonalities;
    }

    public ArrayList<Commonality> updateVertex(Vertex v){
        ArrayList<Commonality> vertexCommonalities = new ArrayList<>();

        for(Solution solution:db){
            int maxPredIndex = solution.maxPredecessor(v);
            int minSuccIndex = solution.minSuccessor(v);

            if(maxPredIndex == -1 || minSuccIndex == -1){
                continue;
            }

            Vertex maxPredecessor = solution.ordering.get(maxPredIndex);
            Vertex minSuccessor = solution.ordering.get(minSuccIndex);

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
