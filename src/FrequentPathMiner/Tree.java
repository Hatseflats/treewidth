package FrequentPathMiner;

import java.util.ArrayList;

public class Tree {
    public Node root;

    public Tree(Node root){
        this.root = root;
    }

    public ArrayList<Path> getPaths(){
        return generatePaths(new ArrayList<Path>(), new Path(), root);
    }

    private ArrayList<Path> generatePaths(ArrayList<Path> paths, Path path, Node node){
        for(Node child:node.getChildren()){
            Path extension = path.copy();
            extension.add(child.getVertex());
            extension.support = child.getSupport();
            paths.add(extension);
            if(extension.path.size() < 2) {
                generatePaths(paths, extension, child);
            }
        }

        return paths;
    }
}
