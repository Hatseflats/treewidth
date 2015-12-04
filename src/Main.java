import java.util.*;

public class Main {
    public static void main(String[] args) {
        GraphReader gr = new GraphReader("myciel3.col");
        Graph g = gr.read();
        LocalSearch localSearch = new LocalSearch(g);
        ArrayList<Vertex> ordering = localSearch.initialOrdering();
        long startTime = System.nanoTime();
        HashMap<Vertex, Set<Vertex>> successors =  localSearch.triangulate(ordering);
        long endTime = System.nanoTime();

        double duration = (endTime - startTime)  / 1000000000.0;

        System.out.println(duration);
        System.out.println(localSearch.score(successors));
        Vertex v = g.vertices.get(1);
        System.out.println(localSearch.minSuccessor(ordering,v,successors.get(v)));
//        System.out.println(Arrays.toString(ordering));
    }
}
