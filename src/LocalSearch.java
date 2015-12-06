import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LocalSearch {
    public Graph g;

    public LocalSearch(Graph g){
        this.g = g;
    }

    public void run(){
        int i = 0;
        int a = 30;

        LinkedList<ArrayList<Vertex>> tabuList = new LinkedList<>();

        ArrayList<Vertex> ordering = maximumMinimumDegree();
        HashMap<Vertex, Set<Vertex>> successors = triangulate(ordering);
        int s = score(successors);

        while (i < a){
            tabuList.push(ordering);
            if(tabuList.size() > 7){
                tabuList.poll();
            }

            ArrayList<ArrayList<Vertex>> neighbors = neighborhood(ordering, successors, tabuList);

            ArrayList<HashMap<Vertex, Set<Vertex>>> successorSets = (ArrayList<HashMap<Vertex, Set<Vertex>>>)
                    neighbors.stream().map(this::triangulate).collect(Collectors.toList());
            ArrayList<Integer> scores = (ArrayList<Integer>) successorSets.stream().map(this::score).collect(Collectors.toList());
            int minIndex = IntStream.range(0,scores.size()).reduce((x,y) -> scores.get(x) > scores.get(y) ? y : x).getAsInt();
            s = scores.get(minIndex);
            successors = successorSets.get(minIndex);
            ordering = neighbors.get(minIndex);

            System.out.println("Score:" + s + ", Treewidth:" + treeWidth(successors) + ", Minindex:"+minIndex);

            i++;
        }

    }

    public HashMap<Vertex, Set<Vertex>> triangulate(ArrayList<Vertex> ordering) {
        Graph h = g.copy();
        HashMap<Vertex, Set<Vertex>> successors = new HashMap<>();

        for (Vertex v: ordering) {
            Set<Vertex> neighborhood = h.neighborhood(v);
            successors.put(v,neighborhood);
            h.makeClique(neighborhood);
            v.setMask(true);
        }
        h.unsetMasks();

        return successors;
    }

    public int score(HashMap<Vertex, Set<Vertex>> successors){
        int n = g.vertices.size();
        List<Integer> sizes = successors.values().stream().map(s -> s.size()).collect(Collectors.toList());
        int w = Collections.max(sizes);
        int succ = sizes.stream().mapToInt(i -> i^2).sum();

        return (n*n)*(w*w)+succ;
    }

    public ArrayList<ArrayList<Vertex>> neighborhood(ArrayList<Vertex> ordering, HashMap<Vertex, Set<Vertex>> successors, LinkedList<ArrayList<Vertex>> tabuList){
        ArrayList<ArrayList<Vertex>> neighbors = new ArrayList<>();
        int currentIndex = 0;

        for(Vertex v : ordering){
            int maxPred = maxPredecessor(ordering, v, successors);
            int minSucc = minSuccessor(ordering, v, successors.get(v));

            if(maxPred != -1){
                ArrayList<Vertex> neighbor1 = new ArrayList<>(ordering);
                Collections.swap(neighbor1, currentIndex, maxPred);
                if(!neighbors.contains(neighbor1) && !tabuList.contains(neighbor1)){
                    neighbors.add(neighbor1);
                }
            }
            if(minSucc != -1){
                ArrayList<Vertex> neighbor2 = new ArrayList<>(ordering);
                Collections.swap(neighbor2, currentIndex, minSucc);
                if(!neighbors.contains(neighbor2) && !tabuList.contains(neighbor2)){
                    neighbors.add(neighbor2);
                }
            }
            currentIndex = currentIndex + 1;
        }
        return neighbors;
    }

    public int treeWidth(HashMap<Vertex, Set<Vertex>> successors){
        List<Integer> sizes = successors.values().stream().map(s -> s.size()).collect(Collectors.toList());
        int max = Collections.max(sizes);
        return max;
    }

    public int maxPredecessor(ArrayList<Vertex> ordering, Vertex v, HashMap<Vertex, Set<Vertex>> successors){
        Set<Vertex> keys = successors.keySet();
        Set<Vertex> candidates = keys.stream().filter(s -> successors.get(s).contains(v)).collect(Collectors.toSet());
        if(candidates.isEmpty()){
            return -1;
        } else {
            return candidates.stream().mapToInt(ordering::indexOf).max().getAsInt();
        }
    }

    public int minSuccessor(ArrayList<Vertex> ordering, Vertex v, Set<Vertex> succ){
        int index = ordering.indexOf(v);
        ArrayList<Vertex> candidates = (ArrayList<Vertex>) ordering.subList(index+1,ordering.size()).stream().filter(succ::contains).collect(Collectors.toList());
        if(candidates.isEmpty()){
            return -1;
        } else {
            return ordering.indexOf(candidates.get(0));
        }
    }

    public ArrayList<Vertex> initialOrdering(){
        ArrayList<Vertex> ordering = new ArrayList<>();
        ordering.addAll(g.vertices.values());
        return ordering;
    }

    public ArrayList<Vertex> maximumMinimumDegree(){
        ArrayList<Vertex> ordering = new ArrayList<>();
        Collection<Vertex> vertices = new ArrayList<>(g.vertices.values());
        while(vertices.size() != 0){
            Vertex v = vertices.stream().reduce((u,w) -> g.neighborhood(u).size() < g.neighborhood(w).size() ? u : w ).get();
            ordering.add(v);
            vertices.remove(v);
            v.setMask(true);
        }

        g.unsetMasks();

        return ordering;
    }

}
