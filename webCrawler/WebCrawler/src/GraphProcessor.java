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
	private Queue<String> queue;
	
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
		queue = new LinkedList<String>();
	}

	public int outDegree(String v) {
		if(graph.get(v) == null) return -1;
		return graph.get(v).size();
	}

	public ArrayList<String> bfsPath(String u, String v) {
		int number_of_nodes = graph.get(u).size() - 1;
		 
        HashSet<String> visited = new HashSet<String>();
        int i;
        String element;
        visited.add(u);
        queue.add(u);
        ArrayList<String> oneLevelParent = new ArrayList<String>();
        oneLevelParent.add(u);
        while (!queue.isEmpty()) {
        	ArrayList<String> childPoss = new ArrayList<String>();
            element = queue.remove();
            i = 0;
            //System.out.print(element + "\t");
            while (i <= number_of_nodes)
            {
                if(graph.get(element) != null) {
                	for (String p : oneLevelParent) {
                		int cutIndex = p.lastIndexOf("#");
                		String key = null;
                		if(cutIndex < 0) {
                			key = p;
                		}
                		else key = p.substring(cutIndex + 1);
                		System.out.println("Parent : " + p + " cutindex : " + cutIndex + " " + key);
                		if(graph.get(key) != null) {
                			for (String string : graph.get(key)) {
                    			if(string.equals(v)) {
                    				String[] pathList = p.concat("#" + v).split("#");
                    				return new ArrayList<String>(Arrays.asList(pathList));
                    			}
            					if(!visited.contains(string)) {
            						visited.add(string);
            						childPoss.add(p.concat("#" + string));
            						queue.add(string);
            					}
            				}
                		}
					}
                }
                i++;
            }
            oneLevelParent.clear();
            oneLevelParent.addAll(childPoss);
        }
		return new ArrayList<String>();
	}

	public int diameter() {
		return 0;
	}

	public int centrality(String v) {
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