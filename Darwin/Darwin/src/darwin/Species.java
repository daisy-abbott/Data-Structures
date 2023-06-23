package darwin;

import java.io.*;



import java.util.ArrayList;



/**
 * The individual creatures in the world are all representatives of some
 * species class and share certain common characteristics, such as the species
 * name and the program they execute. Rather than copy this information into
 * each creature, this data can be recorded once as part of the description for
 * a species and then each creature can simply include the appropriate species
 * reference as part of its internal data structure.
 * 
 * Note: The instruction addresses start at one, not zero.
 */
public class Species {
	private String name;
	private String color;
	private char speciesChar; // the first character of Species name
	private ArrayList<Instruction> program;
	
	public static int opCode;
	private String opCodeName = new String();
	public static int address; 

	/**
	 * Create a species for the given fileReader. 
	 */
	public Species(BufferedReader fileReader) {
			try {
				name  = fileReader.readLine();
				color = fileReader.readLine();
				speciesChar = name.charAt(0);
	
				// insert code to read from Creatures file here (use readLine() )
				program = new ArrayList<Instruction>(); 
				
				String instruction = fileReader.readLine(); 
				//while (fileReader.readLine() != "") {
				while (!instruction.equals("")) {
					
					
					
					
					
					String [] list = instruction.split(" ");
					opCodeName = list[0].toUpperCase();
					
					switch (opCodeName) {
					case "HOP" : opCode = 1; 
						break;
					case "LEFT" : opCode = 2;	
						break;
					case "RIGHT" : opCode = 3;
						break;
					case "INFECT" : opCode = 4;
						break;
					
					case "IFEMPTY" : opCode = 5;
						break;
					case "IFWALL" : opCode = 6;
						break;
					case "IFSAME" : opCode = 7;
						break;
					case "IFENEMY" : opCode = 8;
						break;
					case "IFRANDOM" : opCode = 9;
						break;
					case "GO" : opCode = 10;
						break;
					}
					
					Instruction newInstruction;
					// if need grab address, convert string[1] to int
					
					
					if (list.length >= 2) {
						// problem line? 
						//address = Integer.parseInt(list[1]);
						newInstruction = new Instruction(opCode, Integer.parseInt(list[1]));
					}
					else {
						newInstruction = new Instruction(opCode);
					}
					
					program.add(newInstruction);
				}
				
			} catch (IOException e) {
				System.out.println(
					"Could not read file '"
						+ fileReader
						+ "'");
				System.exit(1);
			}
		 
	}


	/**
	* Return the char for the species
	*/
	public char getSpeciesChar() {
		return speciesChar;		// FIX
	}

	/**
	 * Return the name of the species.
	 */
	public String getName() {
		return name;    // FIX
	}

	/**
	 * Return the color of the species.
	 */
	public String getColor() {
		return color;    // FIX
	}

	/**
	 * Return the number of instructions in the program.
	 */
	public int programSize() {
		return program.size();    // FIX
	}

	/**
	 * Return an instruction from the program.
	 * @pre 1 <= i <= programSize().
	 * @post returns instruction i of the program.
	 */
	public Instruction programStep(int i) {
		return program.get(i-1);
				
			
		  
	}

	/**
	 * Return a String representation of the program.
	 * 
	 * do not change
	 */
	public String programToString() {
		String s = "";
		for (int i = 1; i <= programSize(); i++) {
			s = s + (i) + ": " + programStep(i) + "\n";
		}
		return s;
	}

}
