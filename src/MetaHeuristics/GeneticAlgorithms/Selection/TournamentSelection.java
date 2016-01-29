package MetaHeuristics.GeneticAlgorithms.Selection;


import MetaHeuristics.Solution;

import java.util.*;

public class TournamentSelection implements Selection {
    public int tournamentSize;
    public Random random;

    public TournamentSelection(Random random, int tournamentSize) {
        this.tournamentSize = tournamentSize;
        this.random = random;
    }

    public List<Solution> selection(List<Solution> population) {
        List<Solution> winners = new ArrayList<>();
        int populationSize = population.size();

        Collections.shuffle(population);
        Iterator<Solution> iterator = population.iterator();
        while(winners.size() != populationSize){
            if(!iterator.hasNext()){
                Collections.shuffle(population);
                iterator = population.iterator();
            }

            Collection<Solution> candidates = new ArrayList<>();
            while(iterator.hasNext() && candidates.size() != tournamentSize){
                candidates.add(iterator.next());
            }
            Solution winner = Collections.max(candidates, (s1,s2) -> s1.score > s2.score ? -1 : 1);
            winners.add(winner);
        }


        return winners;
    }
}
