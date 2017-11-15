import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;


public class GraphProcessor
{
	HashMap<String, HashSet<String>> graph = new HashMap<String, HashSet<String>>();
	ArrayList<Vertex> allVertices = new ArrayList<Vertex>();
	FileUtil fileHelper = new FileUtil();
	int vertices = 0;
	int edges = 0;
	HashMap<String, Integer> centrality = new HashMap<String, Integer>();
	private Queue<String> queue;
	private boolean checkDiameter = false;
	int diameterCal = 0;

	// NOTE: graphData should be an absolute file path
	public GraphProcessor(String graphData) {
		ArrayList<String> file = fileHelper.readFile(graphData);
		int totalLength = file.size();
		vertices = Integer.parseInt(file.get(0));
		for(int i = 1; i < totalLength; i++) {
			edges++;
			String[] line = file.get(i).split(" ");
			if(!graph.containsKey(line[0])) {
				HashSet<String> ed = new HashSet<String>();
				ed.add(line[1]);
				graph.put(line[0], ed);
			}
			else graph.get(line[0]).add(line[1]);
		}
		queue = new LinkedList<String>();
	}

	public int outDegree(String v) {
		if(graph.get(v) == null) return -1;
		return graph.get(v).size();
	}

	public ArrayList<String> bfsPath(String u, String v) {
		HashSet<String> visited = new HashSet<String>();
		int i;
		visited.add(u);
		ArrayList<String> oneLevelParent = new ArrayList<String>();
		oneLevelParent.add(u);
		ArrayList<String> childPoss = new ArrayList<String>();
		i = 0;
		if(u.equals(v)) return oneLevelParent;
		//System.out.print(element + "\t");
		while(i < edges) {
			//System.out.println("index i : " + i + " " + totalSize);
			for (String p : oneLevelParent) {
				int cutIndex = p.lastIndexOf("#");
				String key = null;
				if(cutIndex < 0) {
					key = p;
				}
				else key = p.substring(cutIndex + 1);
				//System.out.println("Parent : " + p + " cutindex : " + cutIndex + " " + key);
				if(graph.get(key) != null) {
					for (String string : graph.get(key)) {
						i++;
						//System.out.println("Path from : " + key + " " + string);
						if(string.equals(v)) {
							String[] pathList = p.concat("#" + v).split("#");
							return new ArrayList<String>(Arrays.asList(pathList));
						}
						if(!visited.contains(string)) {
							visited.add(string);
							//System.out.println("Adding child : " + p.concat("#" + string));
							childPoss.add(p.concat("#" + string));
						}
					}
				}
			}
			i++;
			oneLevelParent.clear();
			oneLevelParent.addAll(childPoss);
			childPoss.clear();
		}
		return new ArrayList<String>();
	}

	public int diameter() {
		if(checkDiameter) return diameterCal;
		checkDiameter = true;
		int diameter = 0;
		for (String vertex : graph.keySet()) {
			for (String vertex2 : graph.keySet()) {
				if(!vertex2.equals(vertex)) {
					//System.out.println("Finding path from : " + vertex + " " + vertex2);
					ArrayList<String> path = bfsPath(vertex, vertex2);
					int d = path.size();
					for (int i = 1 ; i < d -1; i++) {
						if(!centrality.containsKey(path.get(i))) {
							centrality.put(path.get(i), 1);
						}
						else centrality.put(path.get(i), centrality.get(path.get(i)) + 1);
						//System.out.println("Centrality for " + path.get(i) + " : " + centrality.get(path.get(i)));
					}
					if(d == 0) { 
						//System.out.println("No path between : " + vertex + "  " + vertex2);
						d = graph.size()*2;
					}
					if(d > diameter) diameter = d;
				}
			}
		}
		diameterCal = diameter;
		return diameter;
	}

	public int centrality(String v) {
		if(!checkDiameter) {
			diameter();
		}
		if(centrality.get(v) != null) {
			return centrality.get(v);
		}
		return 0;
	}

	public void bfs(String p1)
	{
		int number_of_nodes = graph.get(p1).size() - 1;

		HashSet<String> visited = new HashSet<String>();
		int i;
		String element;
		visited.add(p1);
		queue.add(p1);

		while (!queue.isEmpty()) {
			element = queue.remove();
			i = 0;
			System.out.print(element + "\t");
			while (i <= number_of_nodes)
			{
				if(graph.get(element) != null) {
					for (String string : graph.get(element)) {
						if(!visited.contains(string)) {
							visited.add(string);
							queue.add(string);
						}
					}
					i++;
				}
				else break;
			}
		}
	}


}