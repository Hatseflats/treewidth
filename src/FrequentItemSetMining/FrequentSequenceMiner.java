package FrequentItemSetMining;

import java.util.*;
import java.util.stream.Collectors;

import Graph.Vertex;

public class FrequentSequenceMiner {
    public ArrayList<ArrayList<Vertex>> database;
    public HashMap<Vertex, Set<Vertex>> adjacencyList;
    public int dbSize;
    public int minSupport;

    public FrequentSequenceMiner(ArrayList<ArrayList<Vertex>> database, HashMap<Vertex, Set<Vertex>> adjacencyList, int minSupport){
        this.database = database;
        this.adjacencyList = adjacencyList;
        this.dbSize = database.size();
        this.minSupport = minSupport;
    }

    public Tree freqAdjVertex(){
        Tree tree = initTree();

        for(Node node:tree.root.getChildren()){
            extendTree(node,new LinkedList<>(tree.root.getChildren()));
        }

        return tree;
    }

    public void extendTree(Node node, List<Node> candidates){
        candidates.remove(node);
        ArrayList<Node> pattern = getPattern(new ArrayList<>(), node);

        for(Node candidate:candidates){
            if(!adjacent(node.getVertex(),candidate.getVertex())){
                continue;
            }

            List<Vertex> sequence = pattern.stream().map(n -> n.getVertex()).collect(Collectors.toList());
            sequence.add(candidate.getVertex());
            System.out.println(count(sequence) + " - " + sequence);
            int supp = count(sequence);
            if(supp >= minSupport){
                Node extension = new Node();
                extension.setVertex(candidate.getVertex());
                extension.setParent(node);
                extension.setSupport(supp);
                node.addChild(extension);

                extendTree(extension, new LinkedList<>(candidates));
            }
        }
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

    public ArrayList<Node> getPattern(ArrayList<Node> pattern, Node node){
        Node parent = node.getParent();
        if(parent == null || parent.getVertex() == null){
            pattern.add(node);
            return pattern;
        } else {
            getPattern(pattern, node.getParent());
            pattern.add(node);
            return pattern;
        }
    }

    public Tree initTree(){
        Node root = new Node();
        Tree tree = new Tree(root);

        adjacencyList.keySet().forEach(v -> {
            Node node = new Node();
            node.setVertex(v);
            node.setSupport(dbSize);
            node.setParent(root);
            root.addChild(node);
        });

        return tree;
    }

    public int count(List<Vertex> subSequence){
        int supp = 0;
        for(List<Vertex> sequence: database){
            if(containsSubSequence(sequence,subSequence)){
                supp++;
            }
        }
        return supp;
    }

    public boolean containsSubSequence(List<Vertex> sequence, List<Vertex> subSequence){
        Iterator<Vertex> iterator = subSequence.iterator();
        Vertex u = iterator.next();
        for(Vertex v: sequence){
            if(v == u){
                if(iterator.hasNext()){
                    u = iterator.next();
                } else {
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
