import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename = "sample.txt";
		int lineNum = 1;
		int linkIndex = -1;
		int linkEnd = -1;
		FileUtil fileUtil = new FileUtil();
		ArrayList<String> strings = fileUtil.readFile(filename);
		String htmlString = fileUtil.concatArrayList(strings).trim().replaceAll(" +", " ");
		linkIndex = htmlString.indexOf("href=");
		while(linkIndex > -1) {
			linkEnd = htmlString.indexOf("\"", linkIndex + 6);
			String link = htmlString.substring(linkIndex + 6, linkEnd);
			if(link.startsWith("/wiki/") && !link.contains("#") && !link.contains(":")) System.out.println(htmlString.substring(linkIndex + 6, linkEnd));
			linkIndex = htmlString.indexOf("href=", linkEnd);
		}
		/*
		for (String string : strings) {
			string = string.trim().replaceAll(" +", " ");
			linkIndex = string.indexOf("<a href=");
			linkEnd = string.indexOf("\"", linkIndex + 9);
			if(linkIndex > 0) System.out.println(string.substring(linkIndex, linkEnd + 1));
			lineNum++;
		}
		*/
	}

	public ArrayList<String> readFile(String fileLocation) {

		ArrayList<String> fileLines = new ArrayList<String>();
		Boolean pTagCheck = false;
		Boolean topicsCheck = false;
		try (BufferedReader br = Files.newBufferedReader(Paths.get(fileLocation))) {
			String line;
			while ((line = br.readLine()) != null) {
				//System.out.println(line + " " + line.contains("<p>"));
				if(line.contains("<p>") || line.contains("<P>")) {
					pTagCheck = true; 
					fileLines.add(line); 
				}
				if(pTagCheck) {
					while((line = br.readLine()) != null) { 
						fileLines.add(line);
					}
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return fileLines;
	}
	
	public String concatArrayList(ArrayList<String> htmlLines) {
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < htmlLines.size(); i++) {
		   strBuilder.append(htmlLines.get(i));
		}
		return strBuilder.toString();
	}

}
