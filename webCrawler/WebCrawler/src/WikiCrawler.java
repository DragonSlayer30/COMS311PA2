// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class WikiCrawler
{
	static final String BASE_URL = "https://en.wikipedia.org";

	private int limit = 50;
	private int sleepduration = 3000;

	private HttpHelper helper = new HttpHelper(limit, sleepduration);
	private FileUtil fileUtil = new FileUtil();

	private GraphVertex root;

	private String resultFile = "";

	private ArrayList<String> topics = new ArrayList<>();



	public WikiCrawler(String seedUrl, int max, ArrayList<String> topicsList, String fileName) {
		root = new GraphVertex(seedUrl, max, helper, this, topicsList);
		topics = topicsList;
		resultFile = fileName;
		limit  = max;
	}

	// NOTE: extractLinks takes the source HTML code, NOT a URL
	public ArrayList<String> extractLinks(String doc) {
		int linkIndex = -1;
		int linkEnd = -1;
		int pTagIndx = -1;
		int pTagIndx2 = -1;
		HashSet<String> uniqueLinks = new HashSet<String>();
		ArrayList<String> wikiLinks = new ArrayList<>();
		String htmlString = doc.trim().replaceAll(" +", " ");
		pTagIndx = htmlString.indexOf("<p>");
		pTagIndx2 = htmlString.indexOf("<P>");
		if(pTagIndx > pTagIndx2 && pTagIndx > -1 && pTagIndx2 > -1) pTagIndx = pTagIndx2;
		linkIndex = htmlString.indexOf("href=", pTagIndx);
		while(linkIndex > -1) {
			linkEnd = htmlString.indexOf("\"", linkIndex + 6);
			String link = htmlString.substring(linkIndex + 6, linkEnd);
			if(link.startsWith("/wiki/") && !link.contains("#") && !link.contains(":") && !uniqueLinks.contains(link)) { 
				wikiLinks.add(htmlString.substring(linkIndex + 6, linkEnd)); 
				uniqueLinks.add(link);
			}
			linkIndex = htmlString.indexOf("href=", linkEnd);
		}
		return wikiLinks;
	}

	public ArrayList<String> wikiLinksExtractor(String doc) {
		int linkIndex = -1;
		int linkEnd = -1;
		int pTagIndx = -1;
		int pTagIndx2 = -1;
		int externalLink = -1;
		int wikiPage = -1;
		String wikiContentID = "mw-content-text"; //bodyContent , content
		String wikiEnd = "id=\"External_links\"";
		HashSet<String> uniqueLinks = new HashSet<String>();
		ArrayList<String> wikiLinks = new ArrayList<>();
		String htmlString = doc.trim().replaceAll(" +", " ");
		wikiPage = htmlString.indexOf(wikiContentID);
		externalLink = htmlString.indexOf(wikiEnd);
		pTagIndx = wikiPage;
		if(externalLink > -1) htmlString = htmlString.substring(pTagIndx, externalLink);
		else htmlString = htmlString.substring(pTagIndx);
		linkIndex = htmlString.indexOf("href=");
		while(linkIndex > -1) {
			linkEnd = htmlString.indexOf("\"", linkIndex + 6);
			//System.out.println("index : " + linkIndex + " end : " + linkEnd);
			String link = htmlString.substring(linkIndex + 6, linkEnd);
			//System.out.println(link);
			if(link.startsWith("/wiki/") && !link.contains("#") && !link.contains(":")) { 
				if(!uniqueLinks.contains(link)) {
					wikiLinks.add(link); 
					uniqueLinks.add(link);
				}
			}
			linkIndex = htmlString.indexOf("href=", linkEnd);
		}
		//System.out.println("Size of list : " + wikiLinks.size());
		/*
		for (String string : wikiLinks) {
			System.out.println(string);
		}
		 */
		return wikiLinks;

	}

	public void crawl()	{
		ArrayList<String> allLinks = root.intiateCrawl(root);
		try {
			fileUtil.writeToFile("" + allLinks.size(), resultFile, false);
			//root.makeGraph(allLinks, resultFile);
			root.graphWithTopics(resultFile, root.withTopics(root, topics));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}



