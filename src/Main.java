import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        GraphReader gr = new GraphReader("myciel3.col");
        Graph g = new Graph();
        try {
            g = gr.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LocalSearch localSearch = new LocalSearch(g);
        int[] ordering = localSearch.initialOrdering();
        long startTime = System.nanoTime();
        HashMap<Vertex, Set<Vertex>> successors =  localSearch.triangulate(ordering);
        long endTime = System.nanoTime();

        double duration = (endTime - startTime)  / 1000000000.0;

        System.out.println(duration);
        int maxEntry = 0;
        for (Vertex v : successors.keySet())
        {
            Set<Vertex> n = successors.get(v);
            if (n.size() > maxEntry)
            {
                maxEntry = n.size();
            }
        }
        System.out.println(maxEntry);

    }
}
