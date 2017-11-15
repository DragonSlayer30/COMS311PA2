import java.io.IOException;
import java.util.ArrayList;

public class TestWebCrawler {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename = "sample.txt";
		String result = "output.txt";
		String crawlerOutput = "WikiCS.txt";
		String correctAnswer = "wikiCC.txt";
		HttpHelper helper = new HttpHelper(2, 3000);
		FileUtil fileUtil =  new FileUtil();
		String doc = fileUtil.concatArrayList(fileUtil.readFile(filename));
		String wikiComplexity =  "/wiki/Computer_Science";
		int maxVertices = 200;
		ArrayList<String> topics = new ArrayList<String>();
		topics.add("computer");
		WikiCrawler crawler = new WikiCrawler(wikiComplexity, maxVertices, null, crawlerOutput);
		ArrayList<String> answerList = crawler.extractLinks(doc);
		try {
			fileUtil.writeToFile(answerList, result);
			crawler.crawl();
			TestWebCrawler crawler2 = new TestWebCrawler();
			crawler2.verfifyAnswer(crawlerOutput, correctAnswer , fileUtil);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void verfifyAnswer(String given, String actual, FileUtil fileUtil) {
		ArrayList<String> calculated = fileUtil.readFile(given);
		ArrayList<String> correct = fileUtil.readFile(actual);
		ArrayList<String> error = new ArrayList<String>();
		//if(calculated.size() != correct.size()) error.add("Incorrect Length, correct length : " + correct.size() + ", calculated : " + calculated.size());
		int i = 0;
		while(true) {
			if(i > correct.size() && i > calculated.size()) {
				break;
			}
			else if(i < correct.size() && i < calculated.size()) {
				if(!calculated.get(i).equals(correct.get(i))) error.add(calculated.get(i) + ", " + correct.get(i));
			}
			else if(i < correct.size() - 1) error.add(", " + correct.get(i));  
			else if(i < calculated.size()) error.add(calculated.get(i) + ",");  
			i++;
		}
		try {
			fileUtil.writeToFile(error, "Error.txt", false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
