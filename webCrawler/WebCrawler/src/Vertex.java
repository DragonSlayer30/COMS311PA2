import java.util.ArrayList;

public class Vertex {
	String url;
	ArrayList<String> edgeLink;
	
	public Vertex(String seed, ArrayList<String> links) {
		url = seed;
		edgeLink = links;
	}
	
}
