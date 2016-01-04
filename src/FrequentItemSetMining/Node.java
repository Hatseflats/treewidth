package FrequentItemSetMining;

import java.util.ArrayList;
import Graph.Vertex;

public class Node {
    private Vertex data;
    private Node parent;
    private ArrayList<Node> children;
    private int support;

    public Node(){
        children = new ArrayList<>();
    }

    public int getSupport() {
        return support;
    }

    public void setSupport(int support) {
        this.support = support;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Vertex getData() {
        return data;
    }

    public void setData(Vertex data) {
        this.data = data;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }
}
