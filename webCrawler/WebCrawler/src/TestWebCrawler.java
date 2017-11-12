import java.io.IOException;
import java.util.ArrayList;

public class TestWebCrawler {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename = "sample.txt";
		String result = "output.txt";
		FileUtil fileUtil =  new FileUtil();
		String doc = fileUtil.concatArrayList(fileUtil.readFile(filename));
		WikiCrawler crawler = new WikiCrawler("/wiki/Complexity theory", 20, null, "Sample");
		ArrayList<String> answerList = crawler.extractLinks(doc);
		try {
			fileUtil.writeToFile(answerList, result);
			crawler.crawl();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
