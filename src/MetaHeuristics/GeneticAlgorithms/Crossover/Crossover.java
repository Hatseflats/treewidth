package MetaHeuristics.GeneticAlgorithms.Crossover;

import MetaHeuristics.Solution;
import Util.Tuple.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class Crossover {
    protected Random random;
    protected double crossoverRate;

    public Crossover(Random random, double crossoverRate) {
        this.random = random;
        this.crossoverRate = crossoverRate;
    }

    abstract Pair<Solution,Solution> operator(Solution s1, Solution s2);

    public List<Solution> crossover(List<Solution> population){
        List<Solution> offspring = new ArrayList<>();
        Iterator<Solution> populationIterator = population.iterator();

        while(populationIterator.hasNext()){
            Solution parent1 = populationIterator.next();
            Solution parent2 = populationIterator.next();

            if(performCrossover()){
                Pair<Solution,Solution> children = operator(parent1,parent2);
                offspring.add(children.fst());
                offspring.add(children.snd());
            } else {
                offspring.add(parent1);
                offspring.add(parent2);
            }
        }
        return offspring;
    }

    public boolean performCrossover(){
        return random.nextDouble() < crossoverRate;
    }
}
