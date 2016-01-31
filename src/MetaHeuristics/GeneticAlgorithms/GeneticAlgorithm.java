package MetaHeuristics.GeneticAlgorithms;

import MetaHeuristics.GeneticAlgorithms.Crossover.Crossover;
import MetaHeuristics.GeneticAlgorithms.Mutation.Mutation;
import MetaHeuristics.GeneticAlgorithms.Selection.Selection;
import MetaHeuristics.MetaHeuristic;
import MetaHeuristics.ScoreStrategy.ScoreStrategy;
import MetaHeuristics.Solution;
import Graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm extends MetaHeuristic {
    public Mutation mutation;
    public Crossover crossover;
    public Selection selection;
    public int maxGenerations;
    public int populationSize;
    public Random random;

    public GeneticAlgorithm(ScoreStrategy scoreStrategy, Mutation mutation, Selection selection, Crossover crossover, int maxGenerations, int populationSize, Random random) {
        super(scoreStrategy);
        this.mutation = mutation;
        this.selection = selection;
        this.crossover = crossover;
        this.maxGenerations = maxGenerations;
        this.populationSize = populationSize;
        this.random = random;
    }

    public List<Solution> run(Graph g){
        int generations = 0;
        List<Solution> population = initialPopulation(g);
        evaluatePopulation(population, g);
        for(Solution s: population){
            System.out.print(treeWidth(s) + " ");
        }
        System.out.println("-----------");
        while(generations < maxGenerations){
            generations++;
            List<Solution> newPopulation = selection(population);
            newPopulation = crossover(newPopulation);
            mutate(newPopulation);
            evaluatePopulation(newPopulation, g);

            population = newPopulation;
        }

        for(Solution s: population){
            System.out.println(treeWidth(s) + " " + s.ordering);
        }
        System.out.println("");

        return population;
    }

    public List<Solution> initialPopulation(Graph g) {
        List<Solution> population = new ArrayList<>();
        for(int i = 0; i<populationSize; i++){
            Solution individual = new Solution(g.randomOrder(random));
            population.add(individual);
        }
        return population;
    }

    public void evaluatePopulation(List<Solution> population, Graph g){

        population.parallelStream().forEach((individual) -> {
            individual.successors = triangulate(individual, g.copy());
            individual.score = scoreStrategy.score(individual);
        });
    }

    private void mutate(List<Solution> population){
        mutation.mutate(population);
    }

    private List<Solution> crossover(List<Solution> population){
        return crossover.crossover(population);
    }

    private List<Solution> selection(List<Solution> population) {
        return selection.selection(population);
    }
}
