import java.util.*;

public class Graph {

    private final List<Vertex> vertices = new LinkedList<>();

    public List<Vertex> getVertices() {
        return Collections.unmodifiableList(vertices);
    }

    public void addVertex(Vertex newVertex) {
        vertices.add(newVertex);
    }

    public void addEdge(Vertex from, Vertex to, int weight){
        from.getEdges().add(new Edge(from,to,weight));
    }

    public void removeVertex(Vertex vertex) {
        vertices.remove(vertex); //aus vertex menge entfernen

        // alle kanten entfernen die auf den knoten zeigen
        for (Vertex from: vertices) {
            List<Edge> toRemove = new LinkedList<>();
            for (Edge e: from.getEdges()) {
                if (e.getTo() == vertex){
                    toRemove.add(e);
                }
            }
            from.getEdges().removeAll(toRemove);
        }
    }

    public List<Edge> getEdges() {
        List<Edge> edges = new LinkedList<>();

        for (Vertex v : vertices) {
            edges.addAll(v.getEdges());
        }

        return edges;
    }

    public List<Vertex> dfs(Vertex from){
            return dfs(from,null); //traversalja
    }

    public List<Vertex> dfs(Vertex from, Vertex to){
        LinkedList<Vertex> visited = new LinkedList<>(); // besuchte, "markierte" knoten
        List<Vertex> neighbours = new LinkedList<>();
        Stack<Vertex> stack = new Stack<>();
        //1. start knoten auf stack
        stack.push(from);

        while (!stack.isEmpty()) {
            //2. obersten vertex betrachten + markieren
            Vertex currentVertex = stack.pop();
            visited.add(currentVertex);  // "markieren"

            // (falls aktueller knoten gesucht: return)
            if (currentVertex == to){
                return visited;
            }
            neighbours = currentVertex.getNeighbours();

            // 2. alle nicht markierten nachbarn in den stack
            for (Vertex neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    stack.push(neighbour);
                }
            }
        }

        return visited;
    }

    public HashSet<Vertex> bfs(Vertex from){
        return bfs(from,null);
    }

    public HashSet<Vertex> bfs(Vertex from, Vertex to){
        HashSet<Vertex> visited = new HashSet<>(); // besuchte, "markierte" knoten
        List<Vertex> neighbours = new LinkedList<>();
        Queue<Vertex> queue = new LinkedList<>();
        Vertex currentVertex;
        //1. start knoten auf stack
        queue.add(from);

        while (!queue.isEmpty()) {
            //2. obersten vertex betrachten + markieren
            currentVertex = queue.poll();
            visited.add(currentVertex);  // "markieren"

            // (falls aktueller knoten gesucht: return)
            if (currentVertex == to){
                return visited;
            }
            neighbours = currentVertex.getNeighbours();

            // 2. alle nicht markierten nachbarn in den stack
            for (Vertex neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    queue.add(neighbour);
                }
            }
        }

        return visited;
    }


    public HashMap<Vertex, Integer> dijkstra2(Vertex from){
        HashMap<Vertex, Integer> distance = new HashMap<>();
        for (Vertex v : vertices) {
            distance.put(v,Integer.MAX_VALUE);
        }
        distance.put(from,0);

        HashMap<Vertex, Vertex> m1 = new HashMap<>();
        HashMap<Vertex, Vertex> previous = new HashMap<>();
        Queue<Vertex> m2 = new LinkedList<>();
        Vertex cVertex = null;
        List<Vertex> neighbours;
        Boolean start = true;


        while (!m2.isEmpty() || start){
            start = false;


            //find smallest, not locked distance & corresponding vertex
            int minDistance = Integer.MAX_VALUE;
            Vertex minVertex = null;
            for (Vertex v : vertices) {
                if (!m1.containsKey(v) && distance.get(v) <= minDistance){
                    minDistance = distance.get(v);
                    minVertex = v;
                }
            }

            //make smallest vertex the current vertex & add previous vertex to m1 with key to backtrack
            m1.put(minVertex,previous.get(minVertex));
            cVertex = minVertex;
            m2.remove(cVertex);

            //add all neigbours to m2
            neighbours = cVertex.getNeighbours();
            for (Vertex neighbour : neighbours) {
                if (!m1.containsKey(neighbour) && !m2.contains(neighbour)){
                    m2.add(neighbour);
                }
            }


            //go through m2 and check if a new shorter distance to a neighbour was found
            int dist = 0;
            for (Vertex v : m2) {
                dist = cVertex.getDirectDistance(v);
                if(dist > -1 && distance.get(cVertex) + dist < distance.get(v)){
                    distance.put(v, distance.get(cVertex) + dist);
                    previous.put(v, cVertex);
                }
            }

            System.out.println("distances: "+ distance);
            System.out.println("m2: " + m2);
            System.out.println("m1: " + m1);
        }



        return distance;
    }

    private VertexDistance getPair(PriorityQueue<VertexDistance> distances, Vertex key){
        for(VertexDistance pair: distances){
            if (pair.getVertex() == key){
                return pair;
            }
        }
        return null;
    }
    private int getDistance(PriorityQueue<VertexDistance> distances, Vertex key){
        for(VertexDistance pair: distances){
            if (pair.getVertex() == key){
                return pair.getDistance();
            }
        }
        return -1;
    }

    private int getDistance(Vertex from, Vertex to){
        List<Edge> edges = from.getEdges();
        for (Edge e : edges) {
            if (e.getTo() == to){
                return (int )e.getWeight();
            }
        }
        return -1;
    }


}

