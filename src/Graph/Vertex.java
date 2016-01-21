package Graph;

public class Vertex {
    public int id;

    public Vertex(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        return ""+this.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        return id == vertex.id;
    }
}
