import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class GraphVertex {

	private int maxCount = 0;
	private int currentIndex = 0;
	private String seedUrl = "";
	private HashSet<String> childrenHash= new HashSet<String>();
	private ArrayList<GraphVertex> childenLists = new ArrayList<GraphVertex>();
	private ArrayList<String> childenArray = new ArrayList<String>();
	private HttpHelper helper;
	private WikiCrawler crawler;
	private static ArrayList<String> topics;

	public GraphVertex(String seedUrl2, int max, HttpHelper http, WikiCrawler crawlerParent, ArrayList<String> topicsList) {
		// TODO Auto-generated constructor stub
		seedUrl = seedUrl2;
		maxCount = max;
		childenArray.add(seedUrl2);
		childrenHash.add(seedUrl2);
		helper = http;
		crawler = crawlerParent;
		topics = topicsList;
	}

	public ArrayList<String> intiateCrawl(GraphVertex grph) {
		ArrayList<String> hyperLinks = new ArrayList<String>();
		hyperLinks.add(seedUrl);
		String docLink = seedUrl;
		boolean breakLoop = false;
		//FileUtil fileUtil = new FileUtil();
		while(currentIndex < maxCount) {
			try {
				//System.out.println("Hyper Link : " + docLink);
				String htmlDoc2 = helper.getUrlContents(docLink);
				//ArrayList<String> wikiLinks = crawler.wikiLinksExtractor(htmlDoc2);
				// this is what https://piazza.com/class/j6oftfhptb0wp?cid=312 answer got extractLinks
				ArrayList<String> wikiLinks = crawler.extractLinks(htmlDoc2);
				for (String link : wikiLinks) {
					if(!childrenHash.contains(link)) {
						childenLists.add(new GraphVertex(link, maxCount, helper, crawler, topics));
						childrenHash.add(link);
						//System.out.println("Hyper Link : " + docLink + " " + link);
						hyperLinks.add(link);
					}
				}
				currentIndex++;
				if(currentIndex < hyperLinks.size()) docLink = hyperLinks.get(currentIndex);
				//fileUtil.writeToFile(seedUrl + " " + docLink, "crawler.txt", true);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ArrayList<String>(hyperLinks.subList(0, maxCount));
	}

	public ArrayList<Vertex> withTopics(GraphVertex vertex, ArrayList<String> topicsList) {
		ArrayList<Vertex> hyperLinks = new ArrayList<Vertex>();
		HashSet<String> topicsHash = new HashSet<String>();
		int pageCounter = 0;
		int vertexCounter = 1;
		if(topicsList != null) topicsHash.addAll(topicsList);
		String docLink = vertex.seedUrl;
		boolean limitReached = false;
		Vertex v = new Vertex(docLink, new ArrayList<String>());
		hyperLinks.add(v);
		while(pageCounter < maxCount) {
			try {
				String htmlContent = helper.getUrlContents(docLink);
				if(checkValidity(htmlContent)) {
					ArrayList<String> extractedLinks = crawler.extractLinks(htmlContent);
					System.out.println("Links size : " + extractedLinks.size());
					int childCounter = 0;
					for (String string : extractedLinks) {
						while(vertexCounter < maxCount && !limitReached) {
							hyperLinks.get(pageCounter).edgeLink.add(string);
							vertexCounter++;
							childCounter++;
							System.out.println("Adding : " + string);
							if(childCounter > extractedLinks.size()) limitReached = true; break;
						}
						if(limitReached) break;	
					} 
				}
				pageCounter++;
				if(pageCounter < hyperLinks.size()) docLink = hyperLinks.get(pageCounter).url;
				else break;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ArrayList<Vertex>(hyperLinks.subList(0, pageCounter));
	}

	private boolean checkValidity(String htmlContent) {
		// TODO Auto-generated method stub
		for (String string : childenArray) {
			if(!htmlContent.contains(string)) {
				return false;
			}
		}
		return true;
	}

	public void makeGraph(ArrayList<String> links, String fileName) throws InterruptedException, IOException {
		HashSet<String> allLinkHash = new HashSet<String>();
		allLinkHash.addAll(links);
		int counter = 0;
		int edgeCounter = 0;
		String allLocation = "wikiExtracted.txt";
		FileUtil fi = new FileUtil();
		HashSet<String> addedLinks = new HashSet<String>();
		//fi.writeToFile("", allLocation, false);
		for (String edge : links) {
			counter++;
			ArrayList<String> childRoutes = crawler.extractLinks(helper.getUrlContents(edge));
			//fi.writeToFile(edge + " " + childRoutes.size(), allLocation, true);
			//fi.writeToFile(edge + " " + childRoutes.size(), allLocation, true);
			addedLinks.add(edge);
			for (String child : childRoutes) {
				//if(child.equals("/wiki/Dynamical_system")) System.out.println(edge + " : " + "/wiki/Dynamical_system");
				if(allLinkHash.contains(child) && !addedLinks.contains(child)) {
					edgeCounter++;
					addedLinks.add(child);
					fi.writeToFile(edge + " " + child, fileName, true);
				}
			}
			addedLinks.clear();
			System.out.println("Finding for counter : " + counter + ", value : " + edge + ", edges : " + edgeCounter);
		}
	}

	public void graphWithTopics(String outputFile, ArrayList<Vertex> vertices) {
		FileUtil fi = new FileUtil();
		System.out.println("Vertex size : " + vertices.get(0).edgeLink.size());
		try {
			fi.writeToFile(maxCount+"", outputFile, false);
			for (Vertex vertex : vertices) {
				for (String edg : vertex.edgeLink) {
					fi.writeToFile(vertex.url + " " + edg, outputFile, true);
				}
			}
			for (String vertex : vertices.get(0).edgeLink) {
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public String getSeedUrl() {
		return seedUrl;
	}

	public void setSeedUrl(String seedUrl) {
		this.seedUrl = seedUrl;
	}

	public ArrayList<GraphVertex> getChilden() {
		return childenLists;
	}

	public void setChilden(ArrayList<GraphVertex> childen) {
		this.childenLists = childen;
	}

	public HashSet<String> getChildrenHash() {
		return childrenHash;
	}

	public void setChildrenHash(HashSet<String> childrenHash) {
		this.childrenHash = childrenHash;
	}

}
