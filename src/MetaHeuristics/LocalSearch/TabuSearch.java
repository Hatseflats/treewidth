package MetaHeuristics.LocalSearch;

import Graph.Graph;
import Graph.Vertex;
import MetaHeuristics.MetaHeuristic;
import MetaHeuristics.ScoreStrategy.ScoreStrategy;
import MetaHeuristics.Solution;

import java.util.*;

public class TabuSearch extends MetaHeuristic {
    public int tabuSize;
    public Random random;

    public TabuSearch(ScoreStrategy scoreStrategy, int tabuSize, Random random){
        super(scoreStrategy);
        this.tabuSize = tabuSize;
        this.random = random;
    }

    public Solution run(Graph g){
        int i = 0;
        int a = 400;

        LinkedList<Solution> tabuList = new LinkedList<>();

        Solution currentSolution =  new Solution(g.maxMinDegree());;
        Solution bestSolution = currentSolution.copy();
        int bestScore = Integer.MAX_VALUE;
        currentSolution.successors = triangulate(currentSolution, g.copy());


        while (i < a){
            tabuList.push(currentSolution);
            if(tabuList.size() > tabuSize){
                tabuList.poll();
            }

            int currentScore = scoreStrategy.score(currentSolution);

            if(currentScore < bestScore){
                bestScore = currentScore;
                bestSolution = currentSolution.copy();
            }

            ArrayList<Solution> neighbors = neighborhood(currentSolution);
            neighbors.removeAll(tabuList);

            neighbors.parallelStream().forEach(sol -> {
                sol.successors =  triangulate(sol,g.copy());
            });

            Solution newSolution = neighbors.stream().reduce((sol1, sol2) -> scoreStrategy.score(sol1) > scoreStrategy.score(sol2) ? sol2 : sol1).get();

            if(scoreStrategy.score(newSolution) >= scoreStrategy.score(currentSolution)){
                diversification(currentSolution);
            } else {
                currentSolution = newSolution;
            }

//            System.out.println("Score:" + scoreStrategy.score(currentSolution) + ", Treewidth:" + treeWidth(currentSolution.successors));

            i++;
        }

        return bestSolution;
    }

    public ArrayList<Solution> neighborhood(Solution s){
        ArrayList<Solution> neighbors = new ArrayList<>();
        int currentIndex = 0;

        for(Vertex v : s.ordering){
            int maxPred = s.maxPredecessor(v);
            int minSucc = s.minSuccessor(v);

            if(maxPred != -1){
                Solution s1 = s.copy();
                s1.swap(currentIndex, maxPred);
                if(!neighbors.contains(s1)){
                    neighbors.add(s1);
                }
            }
            if(minSucc != -1){
                Solution s2 = s.copy();
                s2.swap(currentIndex, minSucc);
                if(!neighbors.contains(s2)){
                    neighbors.add(s2);
                }
            }
            currentIndex = currentIndex + 1;
        }
        return neighbors;
    }

    public void diversification(Solution solution){
        Vertex v = solution.successors.entrySet().stream().max((s1, s2) -> s1.getValue().size() > s2.getValue().size() ? 1 : -1).get().getKey();
        int index = solution.ordering.indexOf(v);
        int swapIndex = random.nextInt(solution.ordering.size());
//        System.out.println(swapIndex);
        solution.swap(index, swapIndex);

    }
}
