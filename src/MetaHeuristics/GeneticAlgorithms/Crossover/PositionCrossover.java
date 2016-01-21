package MetaHeuristics.GeneticAlgorithms.Crossover;

import Graph.Vertex;
import MetaHeuristics.Solution;
import Util.Tuple.Pair;

import java.util.*;

public class PositionCrossover extends Crossover {

    public PositionCrossover(Random random, double crossoverRate) {
        super(random, crossoverRate);
    }

    public Pair<Solution, Solution> operator(Solution s1, Solution s2) {
        int index = 0;
        int size = s1.ordering.size();
        Iterator<Vertex> it1 = s1.ordering.iterator();
        Iterator<Vertex> it2 = s2.ordering.iterator();

        Vertex[] crossover1 = new Vertex[size];
        Vertex[] crossover2 = new Vertex[size];

        while(index != s1.ordering.size()){
            Vertex v1 = it1.next();
            Vertex v2 = it2.next();

            if(coinFlip()){
                crossover1[index] = v2;
                crossover2[index] = v1;
            }
            index++;
        }

        Solution offspring1 = createOffspring(s1,crossover1);
        Solution offspring2 = createOffspring(s2,crossover2);

        return new Pair<>(offspring1,offspring2);
    }

    private Solution createOffspring(Solution parent, Vertex[] crossover){
        ArrayList<Vertex> crossoverList = new ArrayList<>(Arrays.asList(crossover));
        ArrayList<Vertex> missing = new ArrayList<>(parent.ordering);
        missing.removeAll(crossoverList);

        ArrayList<Vertex> ordering = new ArrayList<>();
        Iterator<Vertex> missingIterator = missing.iterator();

        for(int index = 0; index<parent.ordering.size(); index++){
            Vertex v = crossover[index];
            if(v != null){
                ordering.add(v);
            } else {
                ordering.add(missingIterator.next());
            }
        }

        return new Solution(ordering);
    }

    private boolean coinFlip(){
        return random.nextDouble() > 0.5;
    }
}
