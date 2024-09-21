import java.util.*;

public class EdgeGraphAdapter implements EdgeGraph {

    private Graph g;

    public EdgeGraphAdapter(Graph g) { this.g = g; }

    public boolean addEdge(Edge e) {
      if(!g.hasNode(e.getSrc())) { 
          g.addNode(e.getSrc()); 
      }
      if(!g.hasNode(e.getDst())) {  
          g.addNode(e.getDst()); 
      }
      return g.addEdge(e.getSrc(), e.getDst());
    }

    public boolean hasNode(String n) {
	      return (!g.succ(n).isEmpty() || !g.pred(n).isEmpty());
    }

    public boolean hasEdge(Edge e) {
        return g.hasEdge(e.getSrc(), e.getDst());
    }

    public boolean removeEdge(Edge e) {
        boolean operationSuccessful = g.removeEdge(e.getSrc(), e.getDst());
        if (operationSuccessful) {
            if (g.succ(e.getSrc()).isEmpty() && g.pred(e.getSrc()).isEmpty()) {
                g.removeNode(e.getSrc());
            }
            if (g.succ(e.getDst()).isEmpty() && g.pred(e.getDst()).isEmpty()) {
                g.removeNode(e.getDst());
            }
        }
        return operationSuccessful;
    }

    public List<Edge> outEdges(String n) {
        List<Edge> outEdgesList = new ArrayList<>();
        for (String successorNode : g.succ(n)){
            outEdgesList.add(new Edge(n, successorNode));
        }
        return outEdgesList;
    }

    public List<Edge> inEdges(String n) {
        List<Edge> inEdgesList = new ArrayList<>();
        for (String predecessorNode : g.pred(n)){
            inEdgesList.add(new Edge(predecessorNode, n));
        }
        return inEdgesList;
    }

    public List<Edge> edges() {
        List<Edge> allEdges = new ArrayList<>();
        for (String originNode : g.nodes()) {
            for (String destinationNode : g.succ(originNode)) {
              allEdges.add(new Edge(originNode, destinationNode));
            }
        }
        return allEdges;
    }

    public EdgeGraph union(EdgeGraph otherGraph) {
        EdgeGraph unionGraph = new EdgeGraphAdapter(new ListGraph());
        
        for (Edge e : this.edges()) {
            unionGraph.addEdge(new Edge(e.getSrc(), e.getDst()));
        }

        for (Edge e : otherGraph.edges()) {
            if (!hasEdge(e)) {
                unionGraph.addEdge(new Edge(e.getSrc(), e.getDst()));
            }
        }
        return unionGraph;
    }

    public boolean hasPath(List<Edge> e) {
        if (e.isEmpty()) {
            return true;
        }
        for (Edge edge : e) {
            if (!this.hasEdge(edge)) {
                return false;
            }
        }
        for (int i = 0; i < e.size() - 1; i++) {
            if (!e.get(i).getDst().equals(e.get(i + 1).getSrc())) {
                throw new BadPath();
            }
        }
        return true;
    }

}
