import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;


public class GraphProcessor
{
	HashMap<String, HashSet<String>> graph = new HashMap<String, HashSet<String>>();
	ArrayList<Vertex> allVertices = new ArrayList<Vertex>();
	FileUtil fileHelper = new FileUtil();
	int vertices = 0;
	int edges = 0;
	
	// NOTE: graphData should be an absolute file path
	public GraphProcessor(String graphData) {
		ArrayList<String> file = fileHelper.readFile(graphData);
		int totalLength = file.size();
		vertices = Integer.parseInt(file.get(0));
		for(int i = 1; i < totalLength; i++) {
			String[] line = file.get(i).split(" ");
			if(!graph.containsKey(line[0])) {
				HashSet<String> ed = new HashSet<String>();
				ed.add(line[1]);
				graph.put(line[0], ed);
			}
			else graph.get(line[0]).add(line[1]);
		}
	}

	public int outDegree(String v) {
		if(graph.get(v) == null) return -1;
		return graph.get(v).size();
	}

	public ArrayList<String> bfsPath(String u, String v) {
		int pathCounter = 0;
		ArrayList<String> pathArr = new ArrayList<String>();
		ArrayList<String> queue = new ArrayList<String>();
		HashSet<String> links = new HashSet<String>();
		HashSet<String> visited = new HashSet<String>();
		queue.add(u);
		pathArr.add(u);
		visited.add(u);
		links = graph.get(u);
		if(links == null) return new ArrayList<String>();
		for (String edgeLink : links) {
			if(edgeLink.equals(v)) {
				pathArr.add(v);
			}
		}
		return pathArr;
	}

	public int diameter() {
		return 0;
	}

	public int centrality(String v) {
		return 0;
	}
	

}