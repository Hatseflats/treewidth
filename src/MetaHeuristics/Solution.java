package MetaHeuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {
    public ArrayList<Short> ordering;
    public HashMap<Short, Set<Short>> successors;
    public int score;

    public Solution(){
        ordering = new ArrayList<>();
        successors = new HashMap<>();
        score = Integer.MAX_VALUE;
    }

    public Solution(ArrayList<Short> ordering){
        this.ordering = ordering;
    }

    public Solution copy(){
        Solution s = new Solution();
        s.ordering.addAll(ordering);
        s.successors = successors;
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

    @Override
    public String toString() {
        return "Solution{" +
                "score=" + score +
                ", ordering=" + ordering +
                '}';
    }

    public int maxPredecessor(Short v){
        Set<Short> keys = successors.keySet();
        Set<Short> candidates = keys.stream().filter(k -> successors.get(k).contains(v)).collect(Collectors.toSet());
        if(candidates.isEmpty()){
            return -1;
        } else {
            return candidates.stream().mapToInt(ordering::indexOf).max().getAsInt();
        }
    }

    public int minSuccessor(Short v){
        Set<Short> succ = successors.get(v);
        int index = ordering.indexOf(v);
        ArrayList<Short> candidates = (ArrayList<Short>) ordering.subList(index+1,ordering.size()).stream().filter(succ::contains).collect(Collectors.toList());
        if(candidates.isEmpty()){
            return -1;
        } else {
            return ordering.indexOf(candidates.get(0));
        }
    }
}
