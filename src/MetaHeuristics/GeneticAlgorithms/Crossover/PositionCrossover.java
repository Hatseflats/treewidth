package MetaHeuristics.GeneticAlgorithms.Crossover;

import Graph.Vertex;
import MetaHeuristics.Solution;
import Util.Tuple.Pair;

import java.util.Iterator;
import java.util.Random;

public class PositionCrossover implements Crossover {
    public Random random;

    public PositionCrossover(Random random) {
        this.random = random;
    }

    public Pair<Solution, Solution> crossover(Solution s1, Solution s2) {
        int index = 0;
        Iterator<Vertex> it1 = s1.ordering.iterator();
        Iterator<Vertex> it2 = s2.ordering.iterator();
        Solution offspring1 = new Solution();
        Solution offspring2 = new Solution();

        while(index != s1.ordering.size()){
            
        }

        return null;
    }
}
