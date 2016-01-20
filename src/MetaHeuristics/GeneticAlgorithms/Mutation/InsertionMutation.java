package MetaHeuristics.GeneticAlgorithms.Mutation;

import MetaHeuristics.Solution;

import java.util.Collections;
import java.util.Random;

public class InsertionMutation implements Mutation{
    public void mutate(Solution s, Random random) {
        int oldIndex = random.nextInt(s.ordering.size());
        int newIndex = random.nextInt(s.ordering.size());
        Collections.rotate(s.ordering.subList(oldIndex,newIndex+1), -1);
    }
}
