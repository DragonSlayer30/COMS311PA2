
public class TestGraphProcessor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String graphFile = "graphData.txt";
		GraphProcessor graphProcessor = new GraphProcessor(graphFile);
		System.out.println("Out degree of Ames : " + graphProcessor.outDegree("Ames"));
	}

}
