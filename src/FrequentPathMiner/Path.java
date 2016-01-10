package FrequentPathMiner;

import Graph.Vertex;
import java.util.ArrayList;

public class Path {
    public int support;
    public ArrayList<Vertex> path;

    public Path() {
        path = new ArrayList<>();
    }

    public void add(Vertex v){
        path.add(v);
    }

    public Path copy(){
        Path copy = new Path();
        copy.path.addAll(path);

        return copy;
    }

    @Override
    public String toString() {
        return support + " - " + path.toString();
    }
}
