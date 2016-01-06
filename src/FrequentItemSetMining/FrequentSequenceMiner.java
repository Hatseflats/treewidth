package FrequentItemSetMining;

import java.util.*;

import Graph.Vertex;

public class FrequentSequenceMiner {
    public ArrayList<ArrayList<Vertex>> database;
    public HashMap<Vertex, Set<Vertex>> adjacencyList;
    public int dbSize;

    public FrequentSequenceMiner(ArrayList<ArrayList<Vertex>> database, HashMap<Vertex, Set<Vertex>> adjacencyList){
        this.database = database;
        this.adjacencyList = adjacencyList;
        this.dbSize = database.size();
    }

    public ArrayList<ArrayList<Vertex>> freqAdjVertex(int minSupport){
        Tree tree = initTree();

        for(Node child:tree.root.getChildren()){

        }
    }

    public ArrayList<Node> extendTree(Node node, ArrayList<Node> layer){
        ArrayList<Node> candidates = new ArrayList<>();

        for(Node candidate: layer){
            if(!adjacent(node.getVertex(),candidate.getVertex())){
                continue;
            }

            Node extension = new Node();
            extension.setSupport(0);
            extension.setVertex(candidate.getVertex());
            candidates.add(extension);
        }
        return candidates;
    }

    public boolean treeContains(Iterator<Vertex> subSequence, Node node){
        if(!subSequence.hasNext()){
            return true;
        }
        Vertex v = subSequence.next();
        for(Node child: node.getChildren()){
            if(child.getVertex() == v){
                return treeContains(subSequence, child);
            }
        }
        return false;
    }

    public Tree initTree(){
        Node root = new Node();
        Tree tree = new Tree(root);

        adjacencyList.keySet().forEach(v -> {
            Node node = new Node();
            node.setVertex(v);
            node.setSupport(dbSize);
            root.addChild(node);
        });

        return tree;
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
        Iterator<Vertex> iterator = subSequence.iterator();
        Vertex u = iterator.next();
        for(Vertex v: sequence){
            if(v == u){
                if(iterator.hasNext()){
                    u = iterator.next();
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public Boolean adjacent(Vertex v, Vertex w){
        return adjacencyList.get(v).contains(w);
    }
}
