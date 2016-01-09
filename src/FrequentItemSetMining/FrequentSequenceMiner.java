package FrequentItemSetMining;

import java.lang.reflect.Array;
import java.security.cert.Extension;
import java.util.*;
import java.util.stream.Collector;
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

        buildTree(tree.root.getChildren());

        return tree;
    }

    public void buildTree(List<Node> layer){
        List<List<Node>> nextLayers = new ArrayList<>();
        for(Node node:layer){
            ArrayList<Node> extensions = extendTree(node, layer);
            node.setChildren(extensions);
            nextLayers.add(extensions);
        }

        for(List<Node> nextLayer:nextLayers){
            if(nextLayer.size() > 0){
                buildTree(nextLayer);
            }
        }
    }

    public ArrayList<Node> extendTree(Node node, List<Node> layer){
        ArrayList<Node> candidates = generateCanidates(node, layer);
        ArrayList<Node> pattern = getPattern(new ArrayList<Node>(), node);
        ArrayList<Node> extensions = new ArrayList<>();

        for(Node candidate: candidates){
            if(candidate.getVertex().id == node.getVertex().id){
                continue;
            }

            ArrayList<Node> extension = new ArrayList<>(pattern);
            extension.add(candidate);
            List<Vertex> sequence = extension.stream().map(n -> n.getVertex()).collect(Collectors.toList());
            System.out.println(count(sequence) + " - " + sequence);
            int supp = count(sequence);
            if(supp >= minSupport){
                candidate.setParent(node);
                candidate.setSupport(supp);
                extensions.add(candidate);
            }
        }

        return extensions;
    }

    public ArrayList<Node> generateCanidates(Node node, List<Node> layer){
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
