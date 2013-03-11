package dictionary;

import java.io.FileNotFoundException;
import dictionary.InsertComplexities;
import dictionary.InsertComplexities.InstrumentedKey;

import java.io.PrintStream;
import java.io.File;
import java.util.Random;


public class Main {

    private static final int MAX_SIZE = 500;
    private static final int REPITITIONS = 500;

    public static void main(String[] args) throws FileNotFoundException {
    	
    	Random random = new Random();
    	
    	InsertComplexities complexities = new InsertComplexities(random);
    	
    	int[] bstComplexities = complexities.getInsertComplexities(
    			new BinarySearchTree<InstrumentedKey, Integer>(), MAX_SIZE, REPITITIONS);
    	int[] ollComplexities = complexities.getInsertComplexities(
    			new OrderedLinkedList<InstrumentedKey, Integer>(), MAX_SIZE, REPITITIONS);

    	File bst = new File("BinarySearchTree.dat");
    	PrintStream bstOutput = new PrintStream(bst);
    	
    	File oll = new File("OrderedLinkedList.dat");
    	PrintStream ollOutput = new PrintStream(oll);
    	
    	for(int i = 0 ; i < MAX_SIZE ; i++) {
    		bstOutput.println(i + "\t" + bstComplexities[i]);
    		ollOutput.println(i + "\t" + ollComplexities[i]);
    	}
    }
}
