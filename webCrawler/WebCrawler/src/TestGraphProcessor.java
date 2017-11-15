import java.util.ArrayList;

public class TestGraphProcessor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String graphFile = "WikiCS.txt";
		String graphFile = "graphData.txt";
		//String graphFile = "wikiCC.txt";
		GraphProcessor graphProcessor = new GraphProcessor(graphFile);
		int totalEdges = 0;
		for (String string : graphProcessor.graph.keySet()) {
			int edgeCount = graphProcessor.outDegree(string);
			//System.out.println("Out degree of " + string + " : " + edgeCount);
			totalEdges = totalEdges + edgeCount;
		}
		System.out.println("Total edges : " + totalEdges);
		//String p1 = "/wiki/Computational_complexity_theory";
		//String p2 = "/wiki/Complexity_theory";
		String p1 = "Ames";
		String p2 = "Chicago";
		//String p1 = "U";
		//String p2 = "H";
		ArrayList<String> path = graphProcessor.bfsPath(p1, p2);
		System.out.println("Path size : " + path.size());
		if(path.size() == 0) {
			System.out.println("No path between : " + p1 + " " + p2);
		}
		for (String string : path) {
			System.out.println(string);
		}
		//System.out.println("Diameter : " + graphProcessor.diameter());
		String centrality = "Minneapolis";
		System.out.println("Centrality of " + centrality + " : " + graphProcessor.centrality(centrality));
		//graphProcessor.bfs(p1);
	}

}
