import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class GraphVertex {

	private int maxCount = 0;
	private int currentIndex = 0;
	private String seedUrl = "";
	private HashSet<String> childrenHash= new HashSet<String>();
	private ArrayList<GraphVertex> childen = new ArrayList<GraphVertex>();
	private HttpHelper helper;
	private WikiCrawler crawler;

	public GraphVertex(String seedUrl2, int max, HttpHelper http, WikiCrawler crawlerParent) {
		// TODO Auto-generated constructor stub
		seedUrl = seedUrl2;
		maxCount = max;
		childrenHash.add(seedUrl2);
		helper = http;
		crawler = crawlerParent;
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
				ArrayList<String> wikiLinks = crawler.wikiLinksExtractor(htmlDoc2);
				for (String link : wikiLinks) {
					if(!childrenHash.contains(link)) {
						childen.add(new GraphVertex(link, maxCount, helper, crawler));
						childrenHash.add(link);
						//System.out.println("Hyper Link : " + docLink + " " + link);
						hyperLinks.add(link);
					}
					if(breakLoop) break;
				}
				docLink = hyperLinks.get(currentIndex);
				//fileUtil.writeToFile(seedUrl + " " + docLink, "crawler.txt", true);
				currentIndex++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ArrayList<String>(hyperLinks.subList(0, maxCount));
	}
	
	public void makeGraph(ArrayList<String> links, String fileName) throws InterruptedException, IOException {
		HashSet<String> allLinkHash = new HashSet<String>();
		allLinkHash.addAll(links);
		int counter = 0;
		int edgeCounter = 0;
		String allLocation = "wikiExtracted.txt";
		FileUtil fi = new FileUtil();
		HashSet<String> addedLinks = new HashSet<String>();
		fi.writeToFile("", allLocation, false);
		for (String edge : links) {
			counter++;
			edgeCounter = 0;
			ArrayList<String> childRoutes = crawler.extractLinks(helper.getUrlContents(edge));
			fi.writeToFile(edge + " " + childRoutes.size(), allLocation, true);
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
			//System.out.println("Finding for counter : " + counter + ", value : " + edge + ", edges : " + edgeCounter);
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
		return childen;
	}

	public void setChilden(ArrayList<GraphVertex> childen) {
		this.childen = childen;
	}

	public HashSet<String> getChildrenHash() {
		return childrenHash;
	}

	public void setChildrenHash(HashSet<String> childrenHash) {
		this.childrenHash = childrenHash;
	}

}
