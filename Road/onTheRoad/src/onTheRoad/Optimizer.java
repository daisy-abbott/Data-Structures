package onTheRoad;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class whose main method reads in description of graph and trip requests,
 * and then returns shortest paths (according to distance or time) from one
 * given vertex to another.  The input file is given by a command line argument.
 * @author Daisy Abbott & Brisa Salazar
 * @date 12/5/22
 */

public class Optimizer {
	public static void main(String[] args) {
		// FINISH THIS!!
		String fileName = args[0];
		FileParser parse = new FileParser(fileName);

		List<TripRequest> trips = parse.getTrips();

		for (int i = 0; i < trips.size(); i++) {
			List<String> vertices = parse.getVertices(); 
			
			if (trips.get(i).isDistance()) {
				EdgeWeightedDigraph distance = parse.makeGraph(true);
				if (GraphAlgorithms.isStronglyConnected(distance)) {
					ArrayList<DirectedEdge> arr = GraphAlgorithms.getShortestPath(distance, trips.get(i).getStart(), trips.get(i).getEnd());
					System.out.println("Shortest distance from " + vertices.get(trips.get(i).getStart()) + " to " + vertices.get(trips.get(i).getEnd()));
					GraphAlgorithms.printShortestPath(arr, true, parse.getVertices());
					System.out.println("\n");
				}
			}
			else {
				EdgeWeightedDigraph time = parse.makeGraph(false);
				if (GraphAlgorithms.isStronglyConnected(time)) {
					ArrayList<DirectedEdge> arr = GraphAlgorithms.getShortestPath(time, trips.get(i).getStart(), trips.get(i).getEnd());
					System.out.println("Shortest driving time from " + vertices.get(trips.get(i).getStart()) + " to " + vertices.get(trips.get(i).getEnd()));
					GraphAlgorithms.printShortestPath(arr, false, parse.getVertices());
					System.out.println("\n");
				}
			}
		}

	}
}

