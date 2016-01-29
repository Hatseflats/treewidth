package MetaHeuristics.GeneticAlgorithms.Mutation;

import MetaHeuristics.Solution;
import Util.Tuple.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class Mutation {
    protected Random random;
    protected double mutationRate;

    public Mutation(Random random, double mutationRate) {
        this.random = random;
        this.mutationRate = mutationRate;
    }

    abstract void operator(Solution s);

    public void mutate(List<Solution> population){
        population.parallelStream().forEach(individual -> {
            if(performMutation()){
                operator(individual);
            }
        });

    }


    private boolean performMutation(){
        return random.nextDouble() < mutationRate;
    }
}
