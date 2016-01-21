package MetaHeuristics.GeneticAlgorithms.Mutation;

import MetaHeuristics.Solution;
import Util.Tuple.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

abstract class Mutation {
    protected Random random;
    protected double mutationRate;

    public Mutation(Random random, double mutationRate) {
        this.random = random;
        this.mutationRate = mutationRate;
    }

    abstract void operator(Solution s);

    public void mutate(List<Solution> population){
        List<Solution> mutatedPopulation = new ArrayList<>();
        Iterator<Solution> populationIterator = population.iterator();

        for(Solution solution:population) {
            if (performMutation()) {
                operator(solution);
            }
        }
    }


    private boolean performMutation(){
        return random.nextDouble() < mutationRate;
    }
}
