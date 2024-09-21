import java.util.*;

public class ListGraph implements Graph {
    private HashMap<String, LinkedList<String>> nodes = new HashMap<>();

    public boolean addNode(String n) {
	    if (nodes.containsKey(n)) { return false; }
        nodes.put(n, new LinkedList<String>());
        return true;
    }

    public boolean addEdge(String n1, String n2) {
        if (!nodes.containsKey(n1) || !nodes.containsKey(n2)) {
            throw new NoSuchElementException();
        }
        if (nodes.get(n1).contains(n2)) { return false; } 
        else {
            nodes.get(n1).add(n2);
            return true;
        }
    }

    public boolean hasNode(String n) {
	     return nodes.containsKey(n);
    }

    public boolean hasEdge(String n1, String n2) {
        if (!nodes.containsKey(n1) || !nodes.containsKey(n2)) {
            return false;
        }
        return nodes.get(n1).contains(n2);
    }

    public boolean removeNode(String n) {
        if (nodes.containsKey(n)) {
            for (String key : nodes.keySet()) {
                LinkedList<String> destinationList = nodes.get(key);
                if (destinationList.contains(n)) {
                    destinationList.remove(n);
                }
            }
            nodes.remove(n);
            return true;
        } 
        else { 
            return false; 
        }
    }

    public boolean removeEdge(String n1, String n2) {
        if (!nodes.containsKey(n1) || !nodes.containsKey(n2)) {
            return false;
        }
        if (nodes.get(n1).contains(n2)) {
            nodes.get(n1).remove(n2);
            return true;
        }
        return false;
    }

    public List<String> nodes() {
        return new ArrayList<>(nodes.keySet());
    }

    public List<String> succ(String n) {
	    if ( !nodes.containsKey(n)) {
            throw new NoSuchElementException();
        }
        return new ArrayList<>(nodes.get(n));
    }

    public List<String> pred(String n) {
	    if ( !nodes.containsKey(n)) {
            throw new NoSuchElementException();
        }
        List<String> predecessorList = new ArrayList<>();
        for (String key : nodes.keySet()) {
            if ( nodes.get(key).contains(n) ) { 
                predecessorList.add(key); 
            }
        }
        return predecessorList;
    }

    public Graph union(Graph g) {
        //create new graph and add all nodes from both graphs to it
	    Graph unionGraph = new ListGraph();
        for (String mainGraphKey : this.nodes()) {
            unionGraph.addNode(mainGraphKey);
        }
        for (String secondGraphKey : g.nodes()) {
            unionGraph.addNode(secondGraphKey);
        }
        //for each node in the new graph, loop through list of edges
        //if edge doesn't exist in new graph, add it
        for (String unionGraphOrigin : unionGraph.nodes()){
            if (this.hasNode(unionGraphOrigin)) {
                for (String firstGraphDestination : this.succ(unionGraphOrigin)){
                    if (!unionGraph.hasEdge(unionGraphOrigin, firstGraphDestination)){
                        unionGraph.addEdge(unionGraphOrigin, firstGraphDestination);
                    }
                }
            }
            if (g.hasNode(unionGraphOrigin)) {
                for (String secondGraphDestination : g.succ(unionGraphOrigin)) {
                    if (!unionGraph.hasEdge(unionGraphOrigin, secondGraphDestination)){
                        unionGraph.addEdge(unionGraphOrigin, secondGraphDestination);
                    }
                }
            }
        }
        return unionGraph;
    }

    public Graph subGraph(Set<String> nodes) {
        //add all nodes that exist in the parent graph to the new sub graph
	    Graph newSubGraph = new ListGraph();
        for (String node : nodes) {
            if(this.hasNode(node)){
                newSubGraph.addNode(node);
            }
        }
        //for each node in the new sub graph, retrieve all the edges it had in the parent graph
        //if destination node of each edge exists in the new sub graph, replicate the edge
        for (String newSubGraphNode : newSubGraph.nodes()){
            for (String oldGraphNodeDestination : this.succ(newSubGraphNode)){
                if (newSubGraph.hasNode(oldGraphNodeDestination)) {
                    newSubGraph.addEdge(newSubGraphNode, oldGraphNodeDestination);
                }
            }
        }
        return newSubGraph;
    }

    public boolean connected(String n1, String n2) {
	    if (!nodes.containsKey(n1) || !nodes.containsKey(n2)) {
            return false;
        }
        
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        
        queue.add(n1);
        visited.add(n1);
        
        while (!queue.isEmpty()) {
            String current = queue.poll();
            
            if (current.equals(n2)) {
                return true;
            }
            
            for (String neighbor : nodes.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        
        return false;
    }
}
