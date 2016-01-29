package Graph;

import java.util.*;

public class Graph {
    public HashMap<Short, Set<Short>> adjacencyList;

    public Graph(){
        adjacencyList = new HashMap<>();
    }

    public void addVertex(Short v) {
        adjacencyList.put(v,  new HashSet<>());
    }

    public void addEdge(Short v, Short w) {
        adjacencyList.get(v).add(w);
        adjacencyList.get(w).add(v);
    }

    public Set<Short> neighborhood(Short v) {
        return adjacencyList.get(v);
    }

    public void makeClique(Set<Short> subgraph) {
        ArrayList<Short> vertices = new ArrayList<>();
        vertices.addAll(subgraph);
        int i = 0;
        for(Short v : vertices){
            for(Short w : vertices.subList(i+1, vertices.size())){
                addEdge(v,w);
                addEdge(w,v);
            }
            i++;
        }
    }

    public Graph copy() {
        Graph h = new Graph();
        for(Short v: adjacencyList.keySet()){
            h.adjacencyList.put(v, new HashSet<>(adjacencyList.get(v)));
        }

        return h;
    }

    public ArrayList<Short> randomOrder(Random random){
        ArrayList<Short> ordering = new ArrayList<>(adjacencyList.keySet());
        Collections.shuffle(ordering,random);
        return ordering;
    }

    public ArrayList<Short> maxMinDegree(Random random){
        ArrayList<Short> ordering = new ArrayList<>();
        HashMap<Short, Integer> degree = new HashMap<>();
        HashMap<Integer, ArrayList<Short>> buckets = new HashMap<>();

        adjacencyList.forEach((v,n) -> {
            int d = n.size();
            degree.put(v, d);
            if(!buckets.containsKey(d)){
                buckets.put(d, new ArrayList<>());
            }
            buckets.get(d).add(v);
        });

        while(buckets.size() != 0){
            int minDegree = Collections.min(buckets.keySet());
            ArrayList<Short> bucket = buckets.get(minDegree);
            Short v = bucket.remove(random.nextInt(bucket.size()));
            degree.replace(v,0);
            Set<Short> neighbors = neighborhood(v);

            if(buckets.get(minDegree).size() == 0){
                buckets.remove(minDegree);
            }

            for(Short w: neighbors){
                int oldDegree = degree.get(w);

                if(oldDegree == 0){
                    continue;
                }

                int newDegree = oldDegree-1;
                degree.replace(w, newDegree);

                ArrayList<Short> oldBucket = buckets.get(oldDegree);
                oldBucket.remove(w);

                if(oldBucket.size() == 0){
                    buckets.remove(oldDegree);
                }

                if (!buckets.containsKey(newDegree)) {
                    buckets.put(newDegree, new ArrayList<>());
                }
                buckets.get(newDegree).add(w);

            }

            ordering.add(v);
        }

        return ordering;
    }
}
