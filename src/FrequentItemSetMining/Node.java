package FrequentItemSetMining;

import java.util.ArrayList;
import Graph.Vertex;

public class Node {
    private Vertex vertex;
    private ArrayList<Node> children;
    private int support;

    public Node() {
        this.children = new ArrayList<>();
    }

    public int getSupport() {
        return support;
    }

    public void setSupport(int support) {
        this.support = support;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public void addChild(Node child){
        this.children.add(child);
    }

    public Vertex getVertex() {
        return vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

}
