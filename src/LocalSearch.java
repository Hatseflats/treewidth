import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

abstract class LocalSearch {

    abstract void run(Graph g);

    public HashMap<Vertex, Set<Vertex>> triangulate(Solution s, Graph g) {
        HashMap<Vertex, Set<Vertex>> successors = new HashMap<>();
        HashSet<Vertex> masked = new HashSet<>();

        for (Vertex v: s.ordering) {
            Set<Vertex> neighborhood = g.neighborhood(v);
            neighborhood.removeAll(masked);
            successors.put(v,neighborhood);
            g.makeClique(neighborhood);
            masked.add(v);
        }

        return successors;
    }

    public int score(HashMap<Vertex, Set<Vertex>> successors){
        int n = successors.keySet().size();
        List<Integer> sizes = successors.values().stream().map(s -> s.size()).collect(Collectors.toList());
        int w = Collections.max(sizes);
        int succ = sizes.stream().mapToInt(i -> i^2).sum();

        return (n*n)*(w*w)+succ;
    }

    public ArrayList<Solution> neighborhood(Solution s, HashMap<Vertex, Set<Vertex>> successors){
        ArrayList<Solution> neighbors = new ArrayList<>();
        int currentIndex = 0;

        for(Vertex v : s.ordering){
            int maxPred = maxPredecessor(s, v, successors);
            int minSucc = minSuccessor(s, v, successors.get(v));

            if(maxPred != -1){
                Solution s1 = s.copy();
                s1.swap(currentIndex, maxPred);
                if(!neighbors.contains(s1)){
                    neighbors.add(s1);
                }
            }
            if(minSucc != -1){
                Solution s2 = s.copy();
                s2.swap(currentIndex, minSucc);
                if(!neighbors.contains(s2)){
                    neighbors.add(s2);
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

    public int maxPredecessor(Solution s, Vertex v, HashMap<Vertex, Set<Vertex>> successors){
        Set<Vertex> keys = successors.keySet();
        Set<Vertex> candidates = keys.stream().filter(k -> successors.get(k).contains(v)).collect(Collectors.toSet());
        if(candidates.isEmpty()){
            return -1;
        } else {
            return candidates.stream().mapToInt(s.ordering::indexOf).max().getAsInt();
        }
    }

    public int minSuccessor(Solution s, Vertex v, Set<Vertex> succ){
        int index = s.ordering.indexOf(v);
        ArrayList<Vertex> candidates = (ArrayList<Vertex>) s.ordering.subList(index+1,s.ordering.size()).stream().filter(succ::contains).collect(Collectors.toList());
        if(candidates.isEmpty()){
            return -1;
        } else {
            return s.ordering.indexOf(candidates.get(0));
        }
    }

    public ArrayList<Vertex> initialOrdering(Graph g){
        ArrayList<Vertex> ordering = new ArrayList<>();
        ordering.addAll(g.vertices.values());
        return ordering;
    }

    public ArrayList<Vertex> maximumMinimumDegree(Graph g){
        ArrayList<Vertex> ordering = new ArrayList<>();
        Collection<Vertex> vertices = new ArrayList<>(g.vertices.values());
        HashSet<Vertex> masked = new HashSet<>();

        while(vertices.size() != 0){
            Vertex v = vertices.stream().reduce((u,w) -> {
                Set<Vertex> neighborhoodU = g.neighborhood(u);
                Set<Vertex> neighborhoodW = g.neighborhood(w);
                neighborhoodU.removeAll(masked);
                neighborhoodW.removeAll(masked);

                return neighborhoodU.size() < neighborhoodW.size() ? u : w;
            }).get();

            ordering.add(v);
            vertices.remove(v);
            masked.add(v);
        }

        return ordering;
    }

}
