public class Vertex {
    public int id;
    public boolean mask;

    public Vertex(int id){
        this.id = id;
        this.mask = false;
    }

    public void setMask(boolean mask){
        this.mask = mask;
    }

    @Override
    public String toString() {
        return "Vertex."+this.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}
