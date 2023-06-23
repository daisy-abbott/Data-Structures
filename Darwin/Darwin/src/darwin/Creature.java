package darwin;

import java.util.*;

/**
 * @author Brisa Salazar & Daisy Abbott
 */
/**
 * 
 */
/**
 * This class represents one creature on the board. Each creature must remember
 * its species, position, direction, and the world in which it is living.
 * In addition, the Creature must remember the next instruction out of its
 * program to execute.
 * The creature is also responsible for making itself appear in the WorldMap. In
 * fact, you should only update the WorldMap from inside the Creature class.
 */
public class Creature {
	public Species species; 
	public World world; 
	public Position pos;
	public int dir;
	

	/**
	 * Create a creature of the given species, with the indicated position and
	 * direction. Note that we also pass in the world-- remember this world, so
	 * that you can check what is in front of the creature and update the board
	 * when the creature moves.
	 */
	public Creature(Species species, World world, Position pos, int dir) {
		this.species = species;
		this.world = world;
		this.pos = pos;
		this.dir = dir;
		WorldMap.displaySquare(pos, species.getSpeciesChar(), dir, species.getColor());
	}

	/**
	 * Return the species of the creature.
	 */
	public Species species() {
		return this.species;
	}

	/**
	 * Return the current direction of the creature.
	 */
	public int direction() {
		return this.dir;
	}

	/**
	 * Sets the current direction of the creature to the given value 
	 */
	public void setDirection(int dir){
		this.dir = dir;
	}

	/**
	 * Return the position of the creature.
	 */
	public Position position() {
		return this.pos;
	}

	/**
	 * Execute steps from the Creature's program
	 *   starting at step #1
	 *   continue until a hop, left, right, or infect instruction is executed.
	 */
	public void takeOneTurn() {
		
		int i = 1;
		while (true) {
			Instruction step = species.programStep(i);
			Position nextSquare = pos.getAdjacent(dir);
			Creature creature = new Creature(species, world, pos, dir);
			 
			if (i > species.programSize()) {
				i = 1;
			}
			// HOP
			if (step.getOpcode() == 1) {
				// checks world is in range and the next square is empty so that creature can move
				while (world.inRange(nextSquare) && world.get(nextSquare) == null) {
					world.set(pos, null);
					WorldMap.displaySquare(pos, ' ', dir, species.getColor());
					
					world.set(nextSquare, this);
					pos = nextSquare;	
					WorldMap.displaySquare(nextSquare, species.getSpeciesChar(), dir, species.getColor());
				}
			return;	
				
			}
			// LEFT 
			else if (step.getOpcode() == 2) {
				// changes direction to be left facing 
				if (world.inRange(pos)) {
					int new_dir = leftFrom(dir);
					setDirection(new_dir);
					WorldMap.displaySquare(pos, species.getSpeciesChar(), new_dir, species.getColor());
				}
			return;		
			}
			// RIGHT
			else if (step.getOpcode() == 3) {
				//changes direction to be right facing 
				if (world.inRange(pos)) {
					int new_dir = rightFrom(dir);
					setDirection(new_dir);
					WorldMap.displaySquare(pos, species.getSpeciesChar(), new_dir, species.getColor());
				}
			return;
			}
			// INFECT N
			else if (step.getOpcode() == 4) {
				if (world.inRange(nextSquare) && world.get(nextSquare) != null) {
					Creature adjacentCreature = world.get(nextSquare);
					if (!(creature.species().equals(adjacentCreature.species()))) {
						world.set(nextSquare, null);
						
						// changes internal species indicator 
						Species adjacentCreature_species = adjacentCreature.species();
						adjacentCreature_species = creature.species();
						adjacentCreature.species = adjacentCreature_species;

						//starts executing the same program as infected species, with step n
						i = 1;
						world.set(nextSquare, adjacentCreature);
						WorldMap.displaySquare(nextSquare, adjacentCreature_species.getSpeciesChar(), adjacentCreature.direction(), adjacentCreature_species.getColor());	
					}
				}
			return;
			}
			
			//IFEMPTY N
			else if (step.getOpcode() == 5) {
				// if square in front of the creature is unoccupied, update the next instruction field in the creature so that the program continues from step n
				if (world.inRange(nextSquare) && nextSquare ==  null) {
					int address = step.getAddress();	
					i = address;
				}
				else {
					i++;
					
				}
			}
			
			// IFWALL N
			else if (step.getOpcode() == 6) {
				// if facing the border jump to step n
				if (!world.inRange(nextSquare) ) {
					int address = step.getAddress();	
					i = address;
				}
				// otherwise, go on with the next instruction in sequence.
				else {
					i++;
				}
			}
				
			// IFSAME N
			else if (step.getOpcode() == 7) {
				//if of the same species, jump to step n
				if (world.inRange(nextSquare) && world.get(nextSquare) != null) {
					Creature adjacentCreature = world.get(nextSquare);
					
					if(species().equals(adjacentCreature.species())) {
						int address = step.getAddress();	
						i = address;
					}
					else {
						i++;
					}
				}
				else {
					i++;
				}
			}
			
			// IF ENEMY N
			else if (step.getOpcode() == 8) {
				// if next square was occupied, jump to step n
				if (world.inRange(nextSquare) && world.get(nextSquare) != null) {
					Creature adjacentCreature = world.get(nextSquare);
					if (!species().equals(adjacentCreature.species())){
						int address = step.getAddress();	
						i = address; 
					}
					else {
						i++;
					}	
				}
				// otherwise, go on with the next instruction.
				else { 
					i++;
				}
			}
			//IFRANDOM N
			else if (step.getOpcode() == 9){
				Random rand = new Random();
				int randNum = rand.nextInt(2);
				// jumps to n half the time 
				if (randNum == 0) {
					int address = step.getAddress();	
					i = address;
				}
				// does not just to n other half, continues with instructions 
				else {
					i++;
				}
			}
			//GO N
			else if (step.getOpcode() == 10) {
				//always go to n 
				int address = step.getAddress();	
				i = address;
			}
		}
	}

	/**
	 * Return the compass direction that is 90 degrees left of the one passed in.
	 */
	public static int leftFrom(int direction) {
		return (4 + direction - 1) % 4;
	}

	/**
	 * Return the compass direction that is 90 degrees right of the one passed
	 * in.
	 */
	public static int rightFrom(int direction) {
		return (direction + 1) % 4;
	}
	}


