package darwin;

import java.util.ArrayList;
import java.util.Random;
import java.io.*;

/**
 * @author Brisa Salazar & Daisy Abbott
 */
/**
 * This class controls the simulation. The design is entirely up to you. You
 * should include a main method that takes the array of species file names
 * passed in and populates a world with species of each type. You class should
 * be able to support anywhere between 1 to 4 species.
 * 
 * Be sure to call the WorldMap.pause() method every time through the main
 * simulation loop or else the simulation will be too fast. For example:
 * 
 * 
 * public void simulate() { 
 * 	for (int rounds = 0; rounds < numRounds; rounds++) {
 * 		giveEachCreatureOneTurn(); 
 * 		WorldMap.pause(500); 
 * 	} 
 * }
 * 
 */
class Darwin {
	
	public static int x; 
	public static int y;
	public Position position;
	public ArrayList<Creature> creatures;
	
	public Darwin(String[] speciesFilenames) {
		WorldMap.createWorldMap(10, 10);
		World new_world = new World(10, 10); 
		creatures = new ArrayList<Creature>(speciesFilenames.length);
		try {
			for(int i = 0; i < speciesFilenames.length; i++) {
			
				//creates species object 
				BufferedReader file = new BufferedReader(new FileReader("./Creatures/" + speciesFilenames[i]));
				Species species = new Species(file);
				Random rand = new Random();

				// creates 10 creatures of speciesFilenames[i]
				int j = 1;
				while (j <= 10) {
					
					//create random position (rand x and rand y)
					x = rand.nextInt(new_world.height());
					y = rand.nextInt(new_world.width());
					
					position = new Position(x, y);
					
					// creatures new creature in position is open 
					if (new_world.get(position) == null) {
						Creature creature = new Creature(species, new_world, position, 0); 
						new_world.set(position, creature);
						
						creatures.add(creature);
						j++;
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println("Failed to populate.");
			e.printStackTrace();
			
		}
	}

	/**
	 * The array passed into main will include the arguments that appeared on the
	 * command line. For example, running "java Darwin Hop.txt Rover.txt" will call
	 * the main method with s being an array of two strings: "Hop.txt" and
	 * "Rover.txt".
	 * 
	 * The autograder will always call the full path to the creature files, for
	 * example "java Darwin /home/user/Desktop/Assignment02/Creatures/Hop.txt" So
	 * please keep all your creates in the Creatures in the supplied
	 * Darwin/Creatures folder.
	 *
	 * To run your code you can either: supply command line arguments through
	 * Eclipse ("Run Configurations -> Arguments") or by creating a temporary array
	 * with the filenames and passing it to the Darwin constructor. If you choose
	 * the latter options, make sure to change the code back to: Darwin d = new
	 * Darwin(s); before submitting. If you want to use relative filenames for the
	 * creatures they should be of the form "./Creatures/Hop.txt".
	 */
	public static void main(String s[]) {
		String fileNames[] = {"Flytrap.txt", "Food.txt", "Hop.txt", "Rover.txt"};
		Darwin d = new Darwin(s);
		d.simulate();
	}

	public void simulate() {
		// for loop with amount of rounds you want to take 
		for (int numRounds = 0; numRounds < 10; numRounds++) {
			// executes takeOneTurn() for every creature 
			for (int j = 0; j < creatures.size(); j++) {
				creatures.get(j).takeOneTurn();
				WorldMap.pause(500);
			}
		}
		
		// don't forget to call pause somewhere in the simulator's loop...
		// make sure to pause using WorldMap so that TAs can modify pause time
		// when grading
	}
}

