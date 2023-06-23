package sortCompare;
import java.io.*;
import java.util.*;
/**
 * @author Brisa Salazar & Daisy Abbott
 */
/**
 * Implements an external (on-disk) mergesort to efficiently sort data that do not fit into the main memory.
 * Instead, it reads files from the hard disk drive, loads in main memory small chunks that fit in it, sorts them individually, and saves them in temporary files.
 * It then repeatedly merges these temporary files into increasingly larger sorted files until it sorts the entire original dataset.
 */
public class OnDiskSort{	



	//TODO: add instance variables // do we need more??
	protected int maxSize;
	protected File workingDirectory;
	protected Sorter<String> sorter;
	
	/**
	 * Instantiates a new sorter for sorting String data, the working directory, and the maxSize of data that can fit in the main memory
	 * 
	 * @param maxSize the maximum number of Strings that fit in the main memory
	 * @param workingDirectory the directory where any temporary files created during sorting should be placed
	 * @param sorter the sorter to use to sort the chunks in memory
	 */
	public OnDiskSort(int maxSize, File workingDirectory, Sorter<String> sorter){
		this.maxSize = maxSize;
		this.workingDirectory = workingDirectory;
		 this.sorter = sorter;
		
		// create directory if it doesn't exist
		if( !workingDirectory.exists() ){
			workingDirectory.mkdir();
		}
	}
	
	/**
	 * Remove all files that end with fileEnding from the workingDirectory
	 * 
	 * If you name all of your temporary files with the same file ending, for example ".temp_sorted" 
	 * then it's easy to clean them up using this method
	 * 
	 * @param workingDirectory the directory to clear
	 * @param fileEnding clear only those files with fileEnding
	 */
	private void clearOutDirectory(File workingDirectory, String fileEnding){
		for( File file: workingDirectory.listFiles() ){
			if( file.getName().endsWith(fileEnding) ){
				file.delete();
			}
		}
	}
		
	/**
	 * Write the Strings stored in dataToWrite to outfile one String per line
	 * 
	 * @param outfile the output file
	 * @param dataToWrite the String data to write out
	 */
	private void writeToDisk(File outfile, ArrayList<String> dataToWrite){
		try{
			PrintWriter out = new PrintWriter(new FileOutputStream(outfile));
			
			for( String s: dataToWrite ){
				out.println(s);
			}
			
			out.close();
		}catch(IOException e){
			throw new RuntimeException(e.toString());
		}
	}
	
	/**
	 * Copy data from fromFile to toFile
	 * 
	 * @param fromFile the file to be copied from
	 * @param toFile the destination file to be copied to
	 */
	private void copyFile(File fromFile, File toFile){
		try{
			BufferedReader in = new BufferedReader(new FileReader(fromFile));
			PrintWriter out = new PrintWriter(new FileOutputStream(toFile));
			
			String line = in.readLine();
			
			while( line != null ){
				out.println(line);
				line = in.readLine();
			}
			
			out.close();
			in.close();
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	
	/** 
	 * Sort the data in data using an on-disk version of sorting
	 * 
	 * @param dataReader an Iterator for the data to be sorted. Use the next() and hasNext() methods
	 * @param outputFile the destination for the final sorted data
	 */
	public void sort(Iterator<String> dataReader, File outputFile){
		//counts num of file we r on 
		int fileNum = 0;
		// temporary array of size maxSize stores data from dataReader
		ArrayList<String> tempArray = new ArrayList<String>(maxSize);
		
		//Array list of sorted files
		ArrayList<File> sortedFiles = new ArrayList<File>();
		
		// read one string at a time and save to tempArray
		while (dataReader.hasNext()) {
			tempArray.add(dataReader.next());
			// if at maxSize, sort array
			if (tempArray.size() == maxSize) {
				sorter.sort(tempArray);
				// create new file write sorted array out to new file
				File tempFile = new File(workingDirectory.getAbsolutePath()+ File.separator + Integer.toString(fileNum) + ".tempSorted");
				writeToDisk(tempFile, tempArray);
				// add file to array list of sorted files
				sortedFiles.add(tempFile);
				tempArray = new ArrayList<String>(maxSize);
				fileNum++;
			}

		}
		//merges sorted files into outputFile and clears temporary files
		mergeFiles(sortedFiles, outputFile);
		clearOutDirectory(workingDirectory, ".tempSorted");
	}

	/**
	 * Merges all the Files in sortedFiles into one sorted file, whose destination is outputFile.
	 * 
	 * @pre All of the files in sortedFiles contained data that is sorted
	 * @param sortedFiles a list of files containing sorted data
	 * @param outputFile the destination file for the final sorted data
	 */
	protected void mergeFiles(ArrayList<File> sortedFiles, File outputFile){
		//temp file
		File tempMerged = new File(workingDirectory.getAbsolutePath()+ File.separator + "mergedData");
		// copy first file into tempMerged
		copyFile(sortedFiles.get(0), tempMerged);
		
		// for the size of sortedFiles, starting at the second file (index 1)
		for (int i = 1; i < sortedFiles.size()-1; i++) {
			// merge one more file into tempMerged
			merge(sortedFiles.get(i), tempMerged, outputFile);
			// outputFile is result of merge, so copy to tempMerged (holds the merged data so far)
			copyFile(outputFile, tempMerged);
		}
		//clearing temporary files
		clearOutDirectory(workingDirectory, ".tempSorted");
	}
	
	/**
	 * Given two files containing sorted strings, one string per line, merge them into
	 * one sorted file
	 * 
	 * @param file1 file containing sorted strings, one per line
	 * @param file2 file containing sorted strings, one per line
	 * @param outFile destination file for the results of merging the two files
	 */
	protected void merge(File file1, File file2, File outFile){	
		try {
			// open both files
			BufferedReader readFile1 = new BufferedReader(new FileReader(file1));
			BufferedReader readFile2 = new BufferedReader(new FileReader(file2));
			
			// read first line of files
			String lineFile1 = readFile1.readLine();
			String lineFile2 = readFile2.readLine();
			//System.out.println(lineFile2);
			
			// out file writer
			FileWriter writeOutFile = new FileWriter(outFile);
		 
			// while one of the files is not empty 
			while (lineFile1 != null && lineFile2 != null) {
				// n line in file1 was shorter than first line in file 2
				if (lineFile1.compareTo(lineFile2) < 0) { // what to do when equal?
					writeOutFile.write(lineFile1 + "\n");
					lineFile1 = readFile1.readLine();
				}
				// first line in file2 was smaller than n line in file1
				else if (lineFile1.compareTo(lineFile2) > 0) {
					writeOutFile.write(lineFile2 + "\n");
					lineFile2 = readFile2.readLine();
				}
				else {
					writeOutFile.write(lineFile1);
					lineFile1 = readFile1.readLine();
				}
			}
		
		//}
			// ran out of lines in file 1
			if (lineFile1 == null) {
				// loop through rest of file 2 and add to out file 
				while(lineFile2 != null) {
					writeOutFile.write(lineFile2+  "\n");
					lineFile2 = readFile2.readLine();
				}
			}
			// ran out of lines in file 2
			else {
				// loop through rest of file 1 and add to out file 
				while(lineFile1 != null) {
					writeOutFile.write(lineFile1 + "\n");
					lineFile1 = readFile1.readLine();
				}
			}
			readFile1.close();
			readFile2.close();
			writeOutFile.close();
			
		}
		catch (IOException e) {
			System.out.println(e);
			
		}
	}
	
	/**
	 * Create a sorter that does a mergesort in memory
	 * Create a diskSorter to do external merges
	 * Use subdirectory "sorting_run" of your project as the working directory
	 * Create a word scanner to read King's "I have a dream" speech.
	 * Sort all the words of the speech and put them in dile data.sorted
	 * @param args -- not used!
	 */
	public static void main(String[] args){
		MergeSort<String> sorter = new MergeSort<String>();
		OnDiskSort diskSorter = new OnDiskSort(10, new File("sorting_run"), 
				sorter);
		
		WordScanner scanner = new WordScanner(new File("sorting_run//Ihaveadream.txt"));
		System.out.println("running");		
		diskSorter.sort(scanner, new File("sorting_run//data.sorted"));
		System.out.println("done");
	}

}
