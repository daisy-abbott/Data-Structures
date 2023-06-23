package onTheRoad;
/**
 * Class to read in and parse the input data that can then be used to
 * build the graph used in finding shortest paths
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileParser {
	// list of all vertices & edges in graph being build
	private List<String> vertices = new ArrayList<String>();
	private List<Segment> segments = new ArrayList<Segment>(); //edges
	
	// List of all trips that should be calculated
	private List<TripRequest> trips = new ArrayList<TripRequest>();

	/**
	 * Parse input to obtain lists of vertices, edges, and trip requests.
	 * @param fileName
	 * 		file containing information on road network
	 */
	public FileParser(String fileName) {
		try {
			// file with input data on transportation network
			BufferedReader input = new BufferedReader(new FileReader(fileName));

			// get intersections
			String line = getDataLine(input);
			int numLocations = Integer.parseInt(line);
			//System.out.print(line);
			for (int count = 0; count < numLocations; count++) {
				line = getDataLine(input);
				//System.out.print(line);
				vertices.add(line.trim());
			}
			
			// get road segments
			line = getDataLine(input);
			int numSegments = Integer.parseInt(line);

			for (int count = 0; count < numSegments; count++) {
				line = getDataLine(input);
				Segment segment = new Segment(line);
				if (segment.getStart() >= 0 && segment.getEnd() >= 0 && segment.getStart() != segment.getEnd()) 
					segments.add(segment);
				
			}
			// TO DO: Make sure all segments are legal, i.e., startIndex
			// and endIndex are legal for vertices.
			

			// get trip requests
			line = getDataLine(input);
			int numTrips = Integer.parseInt(line);
			//System.out.println("numTrips: " + numTrips);
			for (int count = 0; count < numTrips; count++) {
				line = getDataLine(input);
				//System.out.println("data from line: " + line);
				
				TripRequest trip = new TripRequest(line, vertices);
//				System.out.println(trip);
//				System.out.println(trip.getStart());
//				System.out.println(trip.getEnd());
//				System.out.println(line.split(" ")[2]); 
				
				if ((trip.getStart() >= 0 && trip.getEnd() >= 0) && (trip.getStart() != trip.getEnd()) && (line.split(" ")[2].equals("D") 
						|| line.split(" ")[2].equals("T")))
					trips.add(trip);
			}
			//System.out.println(trips);
			input.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * 
	 * @param input
	 *            file from which data is read
	 * @return next valid line of input, skips blank lines and lines starting
	 *         with #
	 * @throws IOException
	 */
	private String getDataLine(BufferedReader input) throws IOException {
		String line = input.readLine();
		while (line.trim().equals("") || line.charAt(0) == ('#')) {
			line = input.readLine();
		}

		return line;
	}

	/**
	 * 
	 * @return list of vertices (locations) from the input
	 */
	public List<String> getVertices() {
		return vertices;
	}

	/**
	 * 
	 * @return list of segments (edges) from the input
	 */
	public List<Segment> getSegments() {
		return segments;
	}

	/**
	 * 
	 * @return list of trip requests from input.
	 */
	public List<TripRequest> getTrips() {
		return trips;
	}

	/**
	 * Builds graph from input file
	 * @param isDistance Whether to make graph with edges for distance or for time.
	 * @return  Graph representing file read in
	 */
	public EdgeWeightedDigraph makeGraph(boolean isDistance) {
		//Create a new weighted digraph and populate it with edges based on the segments
	
		// new digraph with number of vertices 
		EdgeWeightedDigraph digraph = new EdgeWeightedDigraph(vertices.size());
		
		
		for (int i = 0; i < segments.size(); i++) {
			int v = segments.get(i).getEnd(); // this is end desitination
			int w = segments.get(i).getStart(); // gets starting position
			
			// if true, return graph weighted w distance
			if (isDistance) {
				double weight = segments.get(i).getDistance();
				
				DirectedEdge edge = new DirectedEdge(v, w, weight);
				digraph.addEdge(edge);
			}
			
			// if false, return graph weighted with time
			else {
				double weight = segments.get(i).getDistance() / segments.get(i).getSpeed();
//				System.out.println("speed :" + segments.get(i).getSpeed());
//				System.out.println("dist :" + segments.get(i).getDistance());
//				System.out.println("time :" + weight);
				DirectedEdge edge = new DirectedEdge(v, w, weight);
				digraph.addEdge(edge);
			}
		}
		return digraph;
	}
	
 

	// testing code for file parser
	public static void main(String[] args) {
		FileParser fp = new FileParser("data/sample.txt");
		
		EdgeWeightedDigraph roadNetworkDistance = fp.makeGraph(true);
		EdgeWeightedDigraph roadNetworkTime = fp.makeGraph(false);

		System.out.println("Distance graph:\n" + roadNetworkDistance);
		System.out.println("Time graph:\n" + roadNetworkTime);
	}
}