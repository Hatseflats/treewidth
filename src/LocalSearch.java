import java.util.*;
import java.util.stream.Collectors;

public class LocalSearch {
    public Graph g;

    public LocalSearch(Graph g){
        this.g = g;
    }

    public HashMap<Vertex, Set<Vertex>> triangulate(ArrayList<Vertex> ordering) {
        Graph h = g.clone();
        HashMap<Vertex, Set<Vertex>> successors = new HashMap<>();

        for (Vertex v: ordering) {
            Set<Vertex> neighborhood = h.neighborhood(v);
            successors.put(v,neighborhood);
            h.makeClique(neighborhood);
            v.setMask(true);
        }

        return successors;
    }

    public int score(HashMap<Vertex, Set<Vertex>> successors){
        int n = g.vertices.size();
        List<Integer> sizes = successors.values().stream().map(s -> s.size()).collect(Collectors.toList());
        int w = Collections.max(sizes);
        int succ = sizes.stream().mapToInt(i -> i^2).sum();

        return (n^2)*(w^2)+succ;
    }

//    public int maxPredecessor(int[] ordering, Vertex v, HashMap<Vertex, Set<Vertex>> successors){
//
//    }

    public int minSuccessor(ArrayList<Vertex> ordering, Vertex v, Set<Vertex> succ){
        int index = ordering.indexOf(v);
        ArrayList<Vertex> candidates = (ArrayList<Vertex>) ordering.subList(index+1,ordering.size()).stream().filter(s -> succ.contains(s)).collect(Collectors.toList());
        System.out.println(candidates);
        return index;
    }

    public ArrayList<Vertex> initialOrdering(){
        ArrayList<Vertex> ordering = new ArrayList<>();
        ordering.addAll(g.vertices.values());
        return ordering;
    }
}
