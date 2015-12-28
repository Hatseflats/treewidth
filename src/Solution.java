import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

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

//    @Override
//    public int hashCode() {
//        String id = ordering.stream().map(v -> String.valueOf(v.hashCode())).collect(Collectors.joining());
//        return Integer.valueOf(id);
//    }



}
