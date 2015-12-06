import java.util.*;

public class Main {
    public static void main(String[] args) {
        GraphReader gr = new GraphReader("myciel7.col");
        Graph g = gr.read();
        LocalSearch localSearch = new LocalSearch(g);
        long startTime = System.nanoTime();
        localSearch.run();
        long endTime = System.nanoTime();

        double duration = (endTime - startTime)  / 1000000000.0;


        System.out.println(duration);

    }
}
