package LocalSearch;

import Graph.Graph;
import Graph.Vertex;
import LocalSearch.ScoreStrategy.ScoreStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TabuSearch extends LocalSearch {
    public int tabuSize;

    public TabuSearch(ScoreStrategy scoreStrategy, int tabuSize){
        super(scoreStrategy);
        this.tabuSize = tabuSize;
    }

    public Solution run(Graph g, Solution initialSolution){
        int i = 0;
        int a = 100;

        LinkedList<Solution> tabuList = new LinkedList<>();

        Solution currentSolution = initialSolution;
        currentSolution.successors = triangulate(currentSolution, g.copy());

        int minScore = scoreStrategy.score(currentSolution);

        while (i < a){
            tabuList.push(currentSolution);
            if(tabuList.size() > tabuSize){
                tabuList.poll();
            }

            ArrayList<Solution> neighbors = neighborhood(currentSolution);
            neighbors.removeAll(tabuList);

            neighbors.parallelStream().forEach(sol -> {
                sol.successors =  triangulate(sol,g.copy());
            });

            Solution newSolution = neighbors.stream().reduce((sol1, sol2) -> scoreStrategy.score(sol1) > scoreStrategy.score(sol2) ? sol2 : sol1).get();

            currentSolution = newSolution;

//            System.out.println("Score:" + scoreStrategy.score(currentSolution) + ", Treewidth:" + treeWidth(currentSolution.successors));

            i++;
        }

        return currentSolution;
    }
}
