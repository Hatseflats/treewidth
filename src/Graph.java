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

    public void unsetMasks(){
        for(Vertex v: vertices.values()){
            v.setMask(false);
        }
    }

    public Set<Vertex> neighborhood(Vertex v){
        Set<Vertex> adjacent = adjacencyList.get(v);
        adjacent = adjacent.stream().filter(w -> !w.mask).collect(Collectors.toSet());

        return adjacent;
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
}
