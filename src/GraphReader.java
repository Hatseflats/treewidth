import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by sebastiaan on 2-12-15.
 */
public class GraphReader {
   public String path;

    public GraphReader(String path){
        this.path = path;
    }

    public Graph read() throws IOException {
        Graph g = new Graph();

        Path filePath = Paths.get("/home/sebastiaan/Code/treewidth/graphs", path);

        try(Stream<String> lines = Files.lines(filePath).filter(s -> s.startsWith("e"))){
            lines.forEach((s) -> {
                String[] parts = s.split(" ");
                Vertex v = new Vertex(Integer.parseInt(parts[1]));
                Vertex w = new Vertex(Integer.parseInt(parts[2]));

                if(!g.vertices.containsKey(v.id)){
                    g.addVertex(v);
                } else {
                    v = g.vertices.get(v.id);
                }

                if(!g.vertices.containsKey(w.id)){
                    g.addVertex(w);
                } else {
                    w = g.vertices.get(w.id);
                }

                g.addEdge(v,w);

            });
        }

        return g;
    }
}
