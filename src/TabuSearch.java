import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TabuSearch extends LocalSearch {
    public int tabuSize;

    public TabuSearch(int tabuSize){
        super();

        this.tabuSize = tabuSize;
    }

    public void run(Graph g){
        int i = 0;
        int a = 10;

        LinkedList<Solution> tabuList = new LinkedList<>();

        Solution s = new Solution(maximumMinimumDegree(g));
        HashMap<Vertex, Set<Vertex>> successors = triangulate(s, g.copy());

        int minScore = score(successors);

        while (i < a){
            tabuList.push(s);
            if(tabuList.size() > tabuSize){
                tabuList.poll();
            }

            ArrayList<Solution> neighbors = neighborhood(s, successors);
            ArrayList<HashMap<Vertex, Set<Vertex>>> successorSets = (ArrayList<HashMap<Vertex, Set<Vertex>>>)
                    neighbors.parallelStream().map(sol -> triangulate(sol,g.copy())).collect(Collectors.toList());

            ArrayList<Integer> scores = (ArrayList<Integer>) successorSets.parallelStream().map(this::score).collect(Collectors.toList());
            int minIndex = IntStream.range(0,scores.size()).reduce((x, y) -> scores.get(x) > scores.get(y) ? y : x).getAsInt();
            minScore = scores.get(minIndex);
            successors = successorSets.get(minIndex);
            s = neighbors.get(minIndex);

            System.out.println("Score:" + minScore + ", Treewidth:" + treeWidth(successors) + ", Minindex:"+minIndex);

            i++;
        }
    }
}
