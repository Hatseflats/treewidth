package Graph;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    public HashMap<Integer, Vertex> vertices;
    public HashMap<Vertex, Set<Vertex>> adjacencyList;

    public Graph(){
        adjacencyList = new HashMap<>();
        vertices = new HashMap<>();
    }

    public void addVertex(Vertex v){
        Set<Vertex> emptySet = new HashSet<>();
        vertices.put(v.id, v);
        adjacencyList.put(v, emptySet);
    }

    public void addEdge(Vertex v, Vertex w){
        adjacencyList.get(v).add(w);
        adjacencyList.get(w).add(v);
    }

    public Set<Vertex> neighborhood(Vertex v){
        return  adjacencyList.get(v);
    }

    public void makeClique(Set<Vertex> subgraph){
        ArrayList<Vertex> vertices = new ArrayList<>();
        vertices.addAll(subgraph);
        int i = 0;
        for(Vertex v : vertices){
            for(Vertex w : vertices.subList(i+1, vertices.size())){
                addEdge(v,w);
                addEdge(w,v);
            }
            i++;
        }
    }

    public Graph copy(){
        Graph h = new Graph();
        h.vertices.putAll(vertices);
        for(Vertex v: vertices.values()){
            h.adjacencyList.put(v, new HashSet<>(adjacencyList.get(v)));
        }

        return h;
    }

    public ArrayList<Vertex> maxMinDegree(){
        ArrayList<Vertex> ordering = new ArrayList<>();
        HashMap<Vertex, Integer> degree = new HashMap<>();
        HashMap<Integer, ArrayList<Vertex>> buckets = new HashMap<>();


        adjacencyList.forEach((v,n) -> {
            int d = n.size();
            degree.put(v, d);
            if(!buckets.containsKey(d)){
                buckets.put(d, new ArrayList<>());
            }
            buckets.get(d).add(v);
        });

        while(buckets.size() != 0){
            int minDegree = Collections.min(buckets.keySet());
            Vertex v = buckets.get(minDegree).remove(0);
            degree.replace(v,0);
            Set<Vertex> neighbors = neighborhood(v);

            if(buckets.get(minDegree).size() == 0){
                buckets.remove(minDegree);
            }

            for(Vertex w: neighbors){
                int oldDegree = degree.get(w);

                if(oldDegree == 0){
                    continue;
                }

                int newDegree = oldDegree-1;
                degree.replace(w, newDegree);

                ArrayList<Vertex> oldBucket = buckets.get(oldDegree);
                oldBucket.remove(w);

                if(oldBucket.size() == 0){
                    buckets.remove(oldDegree);
                }

                if (!buckets.containsKey(newDegree)) {
                    buckets.put(newDegree, new ArrayList<>());
                }
                buckets.get(newDegree).add(w);

            }

            ordering.add(v);
        }

        return ordering;
    }
}
