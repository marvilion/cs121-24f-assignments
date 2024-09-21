public class Main {

    // Run "java -ea Main" to run with assertions enabled (If you run
    // with assertions disabled, the default, then assert statements
    // will not execute!)
    
    public static void test1() {
	Graph g = new ListGraph();
	assert g.addNode("a");
	assert g.hasNode("a");
    }

    public static void test2() {
	Graph g = new ListGraph();
	EdgeGraph eg = new EdgeGraphAdapter(g);
	Edge e = new Edge("a", "b");
	assert eg.addEdge(e);
	assert eg.hasEdge(e);
    }

	public static void testUnionGraph() {
		Graph g1 = new ListGraph();
		Graph g2 = new ListGraph();
	
		assert g1.addNode("node1");
		assert g1.addNode("node2");
		assert g1.addEdge("node1", "node2");
	
		assert g2.addNode("node3");
		assert g2.addNode("node4");
		assert g2.addEdge("node3", "node4");
	
		Graph union = g1.union(g2);
			
		assert union.hasNode("node1");
		assert union.hasNode("node2");
		assert union.hasNode("node3");
		assert union.hasNode("node4");
			
		assert union.hasEdge("node1", "node2");
		assert union.hasEdge("node3", "node4");
		assert !union.hasEdge("node2", "node3");
	
		assert union.addEdge("node2", "node3");
		assert union.connected("node1", "node4");
	
	
		assert union.removeEdge("node3", "node4");
		assert !union.hasEdge("node3", "node4");
	}
    
    public static void main(String[] args) {
	test1();
	test2();
	testUnionGraph();
    }

}