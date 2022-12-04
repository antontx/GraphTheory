import java.util.LinkedList;
import java.util.List;

public class Vertex {

    private final String name;
    private final List<Edge> edges = new LinkedList<>();

    public Vertex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Vertex> getNeighbours(){
        List<Vertex> neighbours = new LinkedList<>();

        for (Edge e: edges) {
            neighbours.add(e.getTo());
        }

        return neighbours;

    }

    public int getDirectDistance(Vertex to){
        for (Edge e: edges) {
            if (e.getTo() == to){
                return (int) e.getWeight();
            }
        }
        return -1;
    }

    @Override
    public String toString(){
        return name;
    }
}
