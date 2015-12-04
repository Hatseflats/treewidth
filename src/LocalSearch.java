import java.util.HashMap;
import java.util.Set;

public class LocalSearch {
    public Graph g;

    public LocalSearch(Graph g){
        this.g = g;
    }

    public HashMap<Vertex, Set<Vertex>> triangulate(int[] ordering) {
        Graph h = g.clone();
        HashMap<Vertex, Set<Vertex>> successors = new HashMap<>();

        for (int i : ordering) {
            Vertex v = h.vertices.get(i);
            Set<Vertex> neighborhood = h.neighborhood(v);
            successors.put(v,neighborhood);
            h.makeClique(neighborhood);
            v.setMask(true);
        }

        return successors;
    }

    public int[] initialOrdering(){
        Set<Integer> keySet = g.vertices.keySet();
        return keySet.stream().mapToInt(i->i).toArray();
    }
}
