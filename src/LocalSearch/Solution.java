package LocalSearch;

import Graph.Graph;
import Graph.Vertex;

import java.util.ArrayList;
import java.util.Collections;

public class Solution {
    public ArrayList<Vertex> ordering;

    public Solution(){
        ordering = new ArrayList<>();
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
}
