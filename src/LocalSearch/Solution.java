package LocalSearch;

import Graph.Graph;
import Graph.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {
    public ArrayList<Vertex> ordering;
    public HashMap<Vertex, Set<Vertex>> successors;

    public Solution(){
        ordering = new ArrayList<>();
        successors = new HashMap<>();
    }

    public Solution(ArrayList<Vertex> ordering){
        this.ordering = ordering;
    }

    public Solution copy(){
        Solution s = new Solution();
        s.ordering.addAll(ordering);
        return s;
    }

    public void swap(int i, int j){
        Collections.swap(ordering, i, j);
    }

    @Override
    public boolean equals(Object obj) {
        Solution s = (Solution) obj;
        return s.ordering.equals(ordering);
    }
    public int maxPredecessor(Vertex v){
        Set<Vertex> keys = successors.keySet();
        Set<Vertex> candidates = keys.stream().filter(k -> successors.get(k).contains(v)).collect(Collectors.toSet());
        if(candidates.isEmpty()){
            return -1;
        } else {
            return candidates.stream().mapToInt(ordering::indexOf).max().getAsInt();
        }
    }

    public int minSuccessor(Vertex v){
        Set<Vertex> succ = successors.get(v);
        int index = ordering.indexOf(v);
        ArrayList<Vertex> candidates = (ArrayList<Vertex>) ordering.subList(index+1,ordering.size()).stream().filter(succ::contains).collect(Collectors.toList());
        if(candidates.isEmpty()){
            return -1;
        } else {
            return ordering.indexOf(candidates.get(0));
        }
    }
}
