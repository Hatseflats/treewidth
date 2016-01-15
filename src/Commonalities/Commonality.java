package Commonalities;

import Graph.Vertex;

/**
 * Created by sebastiaan on 15-1-16.
 */
public class Commonality {
    public Vertex vertex;
    public Vertex successor;
    public Vertex predecessor;
    public int support;

    public Commonality(Vertex vertex, Vertex predecessor, Vertex successor) {
        this.vertex = vertex;
        this.predecessor = predecessor;
        this.successor = successor;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    public Vertex getSuccessor() {
        return successor;
    }

    public void setSuccessor(Vertex successor) {
        this.successor = successor;
    }

    public int getSupport() {
        return support;
    }

    public void setSupport(int support) {
        this.support = support;
    }

    public Vertex getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Vertex predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public boolean equals(Object obj) {
        Commonality commonality = (Commonality) obj;
        return this.vertex.hashCode() == commonality.getVertex().hashCode() &&
                this.predecessor.hashCode() == commonality.getPredecessor().hashCode() &&
                this.successor.hashCode() == commonality.getSuccessor().hashCode();
    }

    @Override
    public String toString() {
        return "Commonality{" +
                "vertex=" + vertex +
                ", successor=" + successor +
                ", predecessor=" + predecessor +
                ", support=" + support +
                '}';
    }
}
