import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;

public class HttpHelper {

	private int requestLimit = 0;
	private int sleepTime = 3000;
	private int requestCounter = 0;
	static final String BASE_URL = "https://en.wikipedia.org";

	public HttpHelper(int limit, int sleepDuration) {
		// TODO Auto-generated constructor stub
		requestLimit = limit;
		sleepTime = sleepDuration;
	}

	public String getUrlContents(String theUrl) throws InterruptedException {
		requestCounter++;
		System.out.println("Request Counter : " + requestCounter);
		if(requestCounter % requestLimit == 0) { 
			System.out.println("I am sleeping ....");
			Thread.sleep(sleepTime);
		}
		StringBuilder content = new StringBuilder();
		try {
			URL url = new URL(BASE_URL + theUrl);
			URLConnection urlConnection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line;
			while ((line = bufferedReader.readLine()) != null)
			{
				content.append(line + System.lineSeparator());
			}
			bufferedReader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}

	public static void main(String[] args) { 
		HttpHelper helper = new HttpHelper(10, 1000);
		String wiki = "/wiki/Complexity";
		String vertices = "vertices.txt";
		String allLinks = "allLinks.txt";
		String test = "Testing.txt";
		FileUtil fileUtil = new FileUtil();
		HashSet<String> hashSet = new HashSet<String>();
		HashSet<String> alreadyAdded = new HashSet<String>();
		WikiCrawler crawler = new WikiCrawler("", 50, null, "output.txt");
		ArrayList<String> lines = new ArrayList<String>();
		try {
			//fileUtil.writeToFile(crawler.extractLinks(helper.getUrlContents(wiki)), "wiki.html");
			hashSet.addAll(fileUtil.readFile(vertices));
			fileUtil.writeToFile("", test, false);
			lines = crawler.extractLinks(helper.getUrlContents(wiki));
			fileUtil.writeToFile(lines, allLinks, false);
			for (String string : lines) {
				if(hashSet.contains(string)) {
					if(!alreadyAdded.contains(string)) fileUtil.writeToFile(wiki + " " + string, test, true); alreadyAdded.add(string);
				}
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

