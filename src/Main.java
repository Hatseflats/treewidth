import java.util.*;

public class Main {
    public static void main(String[] args) {
        GraphReader gr = new GraphReader("myciel5.col");
        Graph g = gr.read();
        LocalSearch localSearch = new TabuSearch(5);
        long startTime = System.nanoTime();
        localSearch.run(g);
        long endTime = System.nanoTime();

        double duration = (endTime - startTime)  / 1000000000.0;


        System.out.println(duration);

    }
}
