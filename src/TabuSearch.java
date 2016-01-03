import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TabuSearch extends LocalSearch {
    public int tabuSize;

    public TabuSearch(int tabuSize){
        this.tabuSize = tabuSize;
    }

    public Solution run(Graph g, Solution initialSolution){
        int i = 0;
        int a = 30;

        LinkedList<Solution> tabuList = new LinkedList<>();

        Solution currentSolution = initialSolution;
        HashMap<Vertex, Set<Vertex>> successors = triangulate(currentSolution, g.copy());

        int minScore = score(successors);

        while (i < a){
            tabuList.push(currentSolution);
            if(tabuList.size() > tabuSize){
                tabuList.poll();
            }

            ArrayList<Solution> neighbors = neighborhood(currentSolution, successors);
            neighbors.removeAll(tabuList);

            ArrayList<HashMap<Vertex, Set<Vertex>>> successorSets = (ArrayList<HashMap<Vertex, Set<Vertex>>>)
                    neighbors.parallelStream().map(sol -> triangulate(sol,g.copy())).collect(Collectors.toList());

            ArrayList<Integer> scores = (ArrayList<Integer>) successorSets.parallelStream().map(this::score).collect(Collectors.toList());
            int minIndex = IntStream.range(0,scores.size()).reduce((x, y) -> scores.get(x) > scores.get(y) ? y : x).getAsInt();
            minScore = scores.get(minIndex);
            successors = successorSets.get(minIndex);
            currentSolution = neighbors.get(minIndex);

//            System.out.println("Score:" + minScore + ", Treewidth:" + treeWidth(successors) + ", Minindex:"+minIndex);

            i++;
        }

        return currentSolution;
    }
}
