import edu.duke.*;

public class FindingGene {

	public static void main(String[] args) {
//		testFindGene();
//		testNumberOfAllGenes();
//		testTheTestGetAllGenes();
//		testTheTestCGRatio();
		testProcessGenes();
	}
	
	//part 2: a more complex find gene
	public static String findGene(String dna, int beginIndex) {
			
//		I added this line
		beginIndex = dna.indexOf("ATG", beginIndex);
		if(beginIndex==-1) {
//		System.out.printf("Starting codon: %s is not found!\n", startCodon);
			return "";
		}
		
//		System.out.printf("Starting codon: %s\n", dna.substring(beginIndex, beginIndex+3));
		
		int taaIndex = findStopCodon(dna, beginIndex, "TAA");
		int tagIndex = findStopCodon(dna, beginIndex, "TAG");
		int tgaIndex = findStopCodon(dna, beginIndex, "TGA");
		
		//the stopCodon will be the closest to the startCodon
		//but because I can't get the minimum of the 3
		//I have to find their smallest but !=-1 
		
		int minIndex = 0;
		
		if(taaIndex==-1 || (tgaIndex!=-1 && tgaIndex<taaIndex)) {
			minIndex = tgaIndex;
		}else {
			minIndex = taaIndex;
		}
		
		if(minIndex==-1 || (tagIndex!=-1 && tagIndex<minIndex)) {
			minIndex = tagIndex;
		}
		
		//in case there is no stop codon
		if(minIndex == -1)
			return "";
		
		String gene = dna.substring(beginIndex, minIndex+3);
//		System.out.printf("The found gene is: %s\n", gene);
		return gene;
	}
	
	public static int findStopCodon(String dna, int beginIndex, String stopCodon) {
		
		int endIndex = dna.indexOf(stopCodon, beginIndex+3);
		
		while(endIndex!=-1) {
			
			//a dna strand format is a multiple of 3
			if((endIndex-beginIndex)%3==0) {
				return endIndex;
			}else {
				endIndex = dna.indexOf(stopCodon, endIndex+1);
			}
		}
		
		if(endIndex==-1) {
//			System.out.printf("Ending codon: %s is not found!\n", stopCodon);
			return -1;
		}
		
//		return dna.length();
		return -1;
	}
	
	public static int findStopIndex(String dna, int beginIndex) {
		
		int taaIndex = findStopCodon(dna, beginIndex, "TAA");
		int tagIndex = findStopCodon(dna, beginIndex, "TAG");
		int tgaIndex = findStopCodon(dna, beginIndex, "TGA");
		
		int minIndex = 0;
		
		if(taaIndex==-1 || (tgaIndex!=-1 && tgaIndex<taaIndex)) {
			minIndex = tgaIndex;
		}else {
			minIndex = taaIndex;
		}
		
		if(minIndex==-1 || (tagIndex!=-1 && tagIndex<minIndex)) {
			minIndex = tagIndex;
		}
		
		//in case there is no stop codon
		return minIndex;
	}
		
	//part 3: print all genes 
	public static void printAllGenes(String dna) {
		
		System.out.printf("Testing printAllGenes on: %s\n", dna);
		
		if(dna.isEmpty())
			return;
		
		int beginIndex = 0;		
		int endIndex;
		
		while(beginIndex!=-1) {
			
			beginIndex = dna.indexOf("ATG", beginIndex);
//			System.out.printf("beginIndex: %d\n", beginIndex);
			if(beginIndex==-1)
				break;
//			System.out.printf("here");
			endIndex = findStopIndex(dna, beginIndex+3);
//			System.out.printf("endIndex: %d\n", endIndex);
			if(endIndex==-1) {
				beginIndex+=3;
			}else {
				System.out.printf("The current found gene is: %s\n", dna.substring(beginIndex, endIndex+3));
				beginIndex = endIndex+3;
			}
		}
		
	}
	
	public static void testFindGene() {
		printAllGenes("ATGATCTAATTTATGCTGCAACGGTGAAGA");
		printAllGenes("");
		printAllGenes("AATGCTAACTAGCTGACTAAT");
		printAllGenes("ATGATCATAAGAAGATAATAGAGGGCCATGTAA");
		printAllGenes("ATGATCA");
		printAllGenes(new URLResource("https://users.cs.duke.edu/~rodger/GRch38dnapart.fa").asString());
	}

	public static int numberOfAllGenes(String dna) {
		int count = 0;
		System.out.printf("Testing printAllGenes on: %s\n", dna);
		
		if(dna.isEmpty())
			return 0;
		
		int beginIndex = 0;	
		
		String currentGene;
		
		while(true) {
			
			currentGene = findGene(dna, beginIndex);
			if(currentGene.isEmpty())
				break;
			
//  			System.out.printf("The current found gene is: %s\n", currentGene);
			count++;
			beginIndex = dna.indexOf(currentGene, beginIndex) + currentGene.length();
		}
	
		return count;
		
	}
	
	public static void testNumberOfAllGenes() {
		System.out.println(numberOfAllGenes("ATGATCTAATTTATGCTGCAACGGTGAAGA"));
		System.out.println(numberOfAllGenes(""));
		System.out.println(numberOfAllGenes("ATGATCATAAGAAGATAATAGAGGGCCATGTAA"));
		System.out.println(numberOfAllGenes("ATGATCA"));
		System.out.println(numberOfAllGenes(new URLResource("https://users.cs.duke.edu/~rodger/GRch38dnapart.fa").asString()));
	}
	
	// part 4:
	public static StorageResource getAllGenes(String dna) {
		StorageResource geneList = new StorageResource();

		if(dna.isEmpty())
			return geneList;

		int beginIndex = 0;		
    
    		String currentGene;
					
		while(true) {
						
		  	currentGene = findGene(dna, beginIndex);
			if(currentGene.isEmpty())
					break;
						
//			System.out.printf("The current found gene is: %s\n", currentGene);
			geneList.add(currentGene);
						
			beginIndex = dna.indexOf(currentGene, beginIndex) + currentGene.length();
		}

		return geneList;
	}
	
	public static void testGetAllGenes(String dna) {
		
		System.out.printf("Testing getAllGenes on: %s\n", dna);
		StorageResource geneList = getAllGenes(dna);
		
		for(String gene: geneList.data()) {
			System.out.printf("Gene: %s\n", gene);
		}
	}
	
	public static void testTheTestGetAllGenes() {
		testGetAllGenes("ATGATCTAATTTATGCTGCAACGGTGAAGA");
		//empty string
		testGetAllGenes("");
		testGetAllGenes("ATGATCATAAGAAGATAATAGAGGGCCATGTAA");
		//no gene in the dna strand
		testGetAllGenes("ATGATCA");
	}

	//part 10:
	public static double cgRatio(String str) {
		
		if(str.isEmpty())
			return 0.0;
		
		//the charachter's count that was sent
		int cCount = 0;
		
		char[] dnaStrand = str.toLowerCase().toCharArray();
		
		for (char character: dnaStrand) {
			if(character=='c' || character=='g')
				cCount++;			
		}

		if(cCount==0)
			return 0.0;		
		
		//the following is recommended by coursera
		return ((double)cCount)/str.length();
//		return (double)cCount/str.length();
	}
		
	public static void testcgRatio(String dna) {	
		System.out.printf("Testing getAllGenes on: %s\n", dna);
		System.out.printf("cgRatio: %f\n",cgRatio(dna));
	}
	
	public static void testTheTestCGRatio() {
		testcgRatio("ATGATCTAATTTATGCTGCAACGGTGAAGA");
		//empty string
		testcgRatio("");
		testcgRatio("ATGATCATAAGAAGATAATAGAGGGCCATGTAA");
		testcgRatio("ATGATCA");
	}
	
	public static int ctgCount(String dna) {
		int count = 0;
		int index = dna.indexOf("CTG");
		
		while(index!=-1) {
			count++;
			index = dna.indexOf("CTG", index+3);
		}
		return count;
	}
	
	//part 11: is a test on all the above
	public static void processGenes(StorageResource sr, String dna) {
		
		int countLongStrs = 0;
		int maxGeneSize = -1;
		int countCGRatio = 0;
		
		for(String str: sr.data()) {
			
			maxGeneSize = str.length()>maxGeneSize?str.length():maxGeneSize;
			
			if(str.length()>60) {
//				System.out.printf("String +60 chars long: %s\n", str);
				countLongStrs++;
			}
			
			if(cgRatio(str)>0.35) {
//				System.out.printf("String that its cgRatio > 0.35: %s\n", str);
				countCGRatio++;
			}
				
		}
		
		System.out.printf("Number of strings +60 chars long: %d\n", countLongStrs);
		System.out.printf("Number of strings that their cgRatio > 0.35: %d\n", countCGRatio);
		System.out.printf("Length of the longest gene: %d\n", maxGeneSize);
		System.out.printf("CTG Codon Appeared: %d\n", ctgCount(dna));
		
	}
	
	public static void testProcessGenes() {

    FileResource fr = new FileResource();
		String dna = fr.asString();

		StorageResource sr = getAllGenes(dna);
		System.out.printf("number of genes: %d\n", numberOfAllGenes(dna));
//		printAllGenes(dna);
		
		processGenes(sr, dna);
	}
}
