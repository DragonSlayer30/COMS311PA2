import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class GraphProcessor
{
	HashMap<String, HashSet<String>> graph = new HashMap<String, HashSet<String>>();
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
		return null;
	}

	public int diameter() {
		return 0;
	}

	public int centrality(String v) {
		return 0;
	}

}