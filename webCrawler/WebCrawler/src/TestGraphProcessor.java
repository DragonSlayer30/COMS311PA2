import java.util.ArrayList;

public class TestGraphProcessor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String graphFile = "CrawlerResult.txt";
		String graphFile = "graphData.txt";
		GraphProcessor graphProcessor = new GraphProcessor(graphFile);
		int totalEdges = 0;
		for (String string : graphProcessor.graph.keySet()) {
			int edgeCount = graphProcessor.outDegree(string);
			System.out.println("Out degree of " + string + " : " + edgeCount);
			totalEdges = totalEdges + edgeCount;
		}
		System.out.println("Total edges : " + totalEdges);
		//String p1 = "/wiki/Lorenz_system";
		//String p2 = "/wiki/Complexity_theory";
		String p1 = "Omaha";
		String p2 = "Minneapolis";
		//String p1 = "U";
		//String p2 = "H";
		ArrayList<String> path = graphProcessor.bfsPath(p1, p2);
		if(path.size() == 0) {
			System.out.println("No path between : " + p1 + " " + p2);
		}
		for (String string : path) {
			System.out.println(string);
		}
		//System.out.println("Diameter : " + graphProcessor.diameter());
		//graphProcessor.bfs(p1);
	}

}
