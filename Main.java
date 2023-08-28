/*
 * This project takes input as an .at file and analayses the dna
 * 
 * @author (Infinull0)
 * @version (1.0)
 */

import edu.duke.*;

public class Main {

    public static void run() {

        // start
        System.out.println(">> Start\n");

        // select file
        System.out.println(">> Select the file\n");
        FileResource fr = new FileResource();
        StorageResource sr = Gene.getAllGenes(fr.asString().toUpperCase());

        // iterate over storage resource
        System.out.println(">> The genes found are : ");
        int count = 1;
        for (String currGene : sr.data()) {
            System.out.println(count + ". " + currGene.toLowerCase());
            count++;
        }
        System.out.println("\n>> Total count of genes : " + (count - 1));
        System.out.println(">> The longest gene     : " + Gene.getLongestGene(sr).length());
        System.out.println(">> The shortest gene    : " + Gene.getSmallestGene(sr).length() + "\n");

        System.out.println(">> End\n");
    }

    public static void main(String[] args) {
        run();
    }
}

class Gene {
    public static StorageResource getAllGenes(String dna) {
        StorageResource geneList = new StorageResource();

        int startIndex = 0;

        while (true) {
            String currGene = findGene(dna, startIndex);
            if (!(currGene.isEmpty())) {
                geneList.add(currGene);
                startIndex = dna.indexOf(currGene, startIndex) + currGene.length();
            } else
                break;
        }
        return geneList;
    }

    public static String findGene(String dna, int index) {
        int startIndex = dna.indexOf("ATG", index);
        if (startIndex == -1) {
            return "";
        }
        int taaIndex = findEndIndex(dna, startIndex, "TAA");
        int tgaIndex = findEndIndex(dna, startIndex, "TGA");
        int tagIndex = findEndIndex(dna, startIndex, "TAG");
        int minIndex = Math.min(taaIndex, Math.min(tgaIndex, tagIndex));
        if (minIndex < dna.length())
            return dna.substring(startIndex, minIndex + 3);
        else
            return "";
    }

    public static int findEndIndex(String dna, int startIndex, String endCodon) {
        int endIndex = dna.indexOf(endCodon, startIndex + 3);
        // check if it is div by 3
        while (endIndex != -1) {
            // ----- if yes return it
            if (((endIndex - startIndex) % 3) == 0)
                return endIndex;
            // ----- if no find next TAA
            else
                endIndex = dna.indexOf(endCodon, endIndex + 1);
        }
        return dna.length();
    }

    public static String getLongestGene(StorageResource sr) {
        String longStr = "";
        for (String currGene : sr.data()) {
            if (longStr.length() < currGene.length())
                longStr = currGene;
        }
        return longStr;
    }

    public static String getSmallestGene(StorageResource sr) {
        String smallStr = null;
        for (String currGene : sr.data()) {
            if (smallStr == null)
                smallStr = currGene;
            else if (smallStr.length() > currGene.length())
                smallStr = currGene;
        }
        return smallStr;
    }

    // -------------------------tests-----------------------------

    // public static void testGetAllGenes() {
    // System.out.println("All genes in : ATGATCTAATTTATGCTGCAACGGTGAAGA");
    // StorageResource sr = getAllGenes("ATGATCTAATTTATGCTGCAACGGTGAAGA");
    // for (String s : sr.data()) {
    // System.out.println(s);
    // }
    // System.out.println("\nAll genes in : ATGATCATAAGAAGATAATAGAGGGCCATGTAA");
    // sr = getAllGenes("ATGATCATAAGAAGATAATAGAGGGCCATGTAA");
    // for (String s : sr.data()) {
    // System.out.println(s);
    // }
    // System.out.println("\nAll genes in : ");
    // sr = getAllGenes("");
    // for (String s : sr.data()) {
    // System.out.println(s);
    // }
    // }

    // -------------------------------------------------------------
}