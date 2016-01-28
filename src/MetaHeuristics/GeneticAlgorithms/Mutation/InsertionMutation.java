package MetaHeuristics.GeneticAlgorithms.Mutation;

import MetaHeuristics.Solution;

import java.util.Collections;
import java.util.Random;

public class InsertionMutation extends Mutation{

    public InsertionMutation(Random random, double mutationRate) {
        super(random, mutationRate);
    }

    public void operator(Solution s) {
        int index1 = random.nextInt(s.ordering.size());
        int index2 = random.nextInt(s.ordering.size());

        if(index1 < index2){
            Collections.rotate(s.ordering.subList(index1,index2+1), -1);
        } else if(index2 < index1){
            Collections.rotate(s.ordering.subList(index2,index1+1), -1);
        }
    }
}
