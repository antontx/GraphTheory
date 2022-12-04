import java.io.IOException;
public class Main {
    public static void main(String[] args) {
        String fileName = "A2.dot";
        try {
            DotGraphTool graphTool =  new DotGraphTool();
            Graph graph = graphTool.dotFileToGraph(fileName);

            Vertex WI = graph.getVertices().get(0);
            graph.dijkstra3(WI);
//            System.out.println(graph.bfs(WI));


            graphTool.graphToDotFile(graph, "out_" + fileName );


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
