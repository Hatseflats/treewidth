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
}
