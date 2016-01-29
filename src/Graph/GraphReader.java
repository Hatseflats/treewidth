package Graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class GraphReader {
   public String path;

    public GraphReader(String path){
        this.path = path;
    }

    public Graph read()  {
        Graph g = new Graph();

        Path filePath = Paths.get("/home/sebastiaan/Code/treewidth/graphs", path);

        try(Stream<String> lines = Files.lines(filePath).filter(s -> s.startsWith("e"))){
            lines.forEach((s) -> {
                String[] parts = s.split(" ");
                Short v = new Short(Short.parseShort(parts[1]));
                Short w = new Short(Short.parseShort(parts[2]));

                if(!g.adjacencyList.keySet().contains(v)){
                    g.addVertex(v);
                }

                if(!g.adjacencyList.keySet().contains(w)){
                    g.addVertex(w);
                }

                g.addEdge(v,w);

            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return g;
    }
}
