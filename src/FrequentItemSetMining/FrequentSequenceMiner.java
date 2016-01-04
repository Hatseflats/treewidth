package FrequentItemSetMining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import Graph.Vertex;

public class FrequentSequenceMiner {
    public ArrayList<ArrayList<Vertex>> database;
    public HashMap<Vertex, Set<Vertex>> adjacencyList;

    public FrequentSequenceMiner(ArrayList<ArrayList<Vertex>> database, HashMap<Vertex, Set<Vertex>> adjacencyList){
        this.database = database;
        this.adjacencyList = adjacencyList;
    }

    public ArrayList<ArrayList<Vertex>> freqAdjVertex(int minSupport){
        Tree sequenceTree = new Tree();

        adjacencyList.keySet().forEach(v -> {
            Node node = new Node();
            node.setData(v);
            node.setSupport(1);
            node.setParent(sequenceTree.root);

            sequenceTree.root.getChildren().add(node);
        });

        return database;
    }

    public int count(ArrayList<Vertex> subSequence){
        int supp = 0;
        for(ArrayList<Vertex> sequence: database){
            if(containsSubSequence(sequence,subSequence)){
                supp++;
            }
        }
        return supp;
    }

    public boolean containsSubSequence(ArrayList<Vertex> sequence, ArrayList<Vertex> subSequence){
        Vertex u = subSequence.remove(0);
        for(Vertex v: sequence){
            if(v == u){
                u = subSequence.remove(0);
                if(subSequence.isEmpty()){
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean adjacent(Vertex v, Vertex w){
        return adjacencyList.get(v).contains(w);
    }
}
