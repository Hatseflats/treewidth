package MetaHeuristics.GeneticAlgorithms.Selection;


import MetaHeuristics.Solution;

import java.util.*;

public class TournamentSelection implements Selection {
    public int tournamentSize;
    public Random random;

    public TournamentSelection(int tournamentSize, Random random) {
        this.tournamentSize = tournamentSize;
        this.random = random;
    }

    public List<Solution> selection(List<Solution> solutions) {
        List<Solution> results = new ArrayList<>();
        Collections.shuffle(solutions,random);
        Iterator<Solution> iterator = solutions.iterator();

        while(iterator.hasNext()){
            Collection<Solution> candidates = new ArrayList<>();
            while(iterator.hasNext() && candidates.size() != tournamentSize){
                candidates.add(iterator.next());
            }
            Solution winner = Collections.max(candidates, (s1,s2) -> s1.score > s2.score ? 1 : -1);
            results.add(winner);
        }

        return results;
    }
}
