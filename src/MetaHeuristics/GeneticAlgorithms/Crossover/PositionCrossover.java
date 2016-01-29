package MetaHeuristics.GeneticAlgorithms.Crossover;

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
        Iterator<Short> it1 = s1.ordering.iterator();
        Iterator<Short> it2 = s2.ordering.iterator();

        Short[] crossover1 = new Short[size];
        Short[] crossover2 = new Short[size];

        while(index != s1.ordering.size()){
            Short v1 = it1.next();
            Short v2 = it2.next();

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

    private Solution createOffspring(Solution parent, Short[] crossover){
        ArrayList<Short> crossoverList = new ArrayList<>(Arrays.asList(crossover));
        ArrayList<Short> missing = new ArrayList<>(parent.ordering);
        missing.removeAll(crossoverList);

        ArrayList<Short> ordering = new ArrayList<>();
        Iterator<Short> missingIterator = missing.iterator();

        for(int index = 0; index<parent.ordering.size(); index++){
            Short v = crossover[index];
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
