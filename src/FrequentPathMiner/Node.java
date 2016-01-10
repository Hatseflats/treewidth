package FrequentPathMiner;

import java.util.ArrayList;
import java.util.List;

import Graph.Vertex;

public class Node {
    private Vertex vertex;
    private List<Node> children;
    private Node parent;
    private int support;

    public Node() {
        this.children = new ArrayList<>();
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getSupport() {
        return support;
    }

    public void setSupport(int support) {
        this.support = support;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
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

    @Override
    public String toString() {
        if(vertex == null){
            return "root";
        } else {
            return ""+this.vertex.id;
        }
    }

    public boolean isLeaf(){
        return children.size() == 0;
    }
}
