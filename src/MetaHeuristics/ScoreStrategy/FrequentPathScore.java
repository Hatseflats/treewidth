package MetaHeuristics.ScoreStrategy;

import FrequentPathMiner.Path;
import Graph.Vertex;
import MetaHeuristics.Solution;

import java.util.*;
import java.util.stream.Collectors;

public class FrequentPathScore implements ScoreStrategy {
    public ArrayList<Path> paths;

    public FrequentPathScore(ArrayList<Path> paths){
        this.paths = paths;
    }

    public boolean containsSubSequence(List<Vertex> sequence, List<Vertex> subSequence){
        Iterator<Vertex> iterator = subSequence.iterator();
        Vertex u = iterator.next();
        for(Vertex v: sequence){
            if(v == u){
                if(iterator.hasNext()){
                    u = iterator.next();
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public int score(Solution solution){
        int n = solution.ordering.size();
        List<Integer> sizes = solution.successors.values().stream().map(s -> s.size()).collect(Collectors.toList());
        int w = Collections.max(sizes);
        int succ = sizes.stream().mapToInt(i -> i^2).sum();

        int score = (n*n)*(w*w)+succ;

        for(Path path:paths){
            if(containsSubSequence(solution.ordering,path.path)){
                score -= 0.5*(path.path.size()*path.support);
            }
        }

        return score;
    }
}
