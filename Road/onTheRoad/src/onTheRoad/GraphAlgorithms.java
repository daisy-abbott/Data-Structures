package onTheRoad;

import java.text.DecimalFormat;

/**
 * Common algorithms for Graphs. 
 * They all assume working with a EdgeWeightedDirected graph.
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GraphAlgorithms {
	

	/**
	 * Reverses the edges of a graph
	 * 
	 * @param g
	 *            edge weighted directed graph
	 * @return graph like g except all edges are reversed
	 */
	public static EdgeWeightedDigraph graphEdgeReversal(EdgeWeightedDigraph g) {
		EdgeWeightedDigraph newGraph = new EdgeWeightedDigraph(g.V());
		for (DirectedEdge e : g.edges()) {
			DirectedEdge newEdge = new DirectedEdge(e.to(), e.from(),e.weight());
			newGraph.addEdge(newEdge);
		}
		return newGraph;
	}

	/**
	 * Performs breadth-first search of g from vertex start.
	 * 
	 * @param g
	 *            directed edge weighted graph
	 * @param start
	 *            index of starting vertex for search
	 */
	public static void breadthFirstSearch(EdgeWeightedDigraph g, int start) {
//		Declare a queue and insert the starting vertex.
//		Initialize a visited array and mark the starting vertex as visited.
//		Follow the below process till the queue becomes empty:
	//		Remove the first vertex of the queue.
	//		Mark that vertex as visited.
	//		Insert all the unvisited neighbours of the vertex into the queue.
//		
		Deque<Integer> queue = new ArrayDeque<Integer>();
		g.reset();
		DirectedEdge edge = new DirectedEdge(start, start, 0.0);
		g.visit(edge, start);
		queue.addLast(start); //enqueues the edge to vertex at start 
		
		while(!queue.isEmpty()) {
			int v =  queue.pop();
			for (DirectedEdge w : g.adj(v)) { 
				if (!g.isVisited(w.to())){
					g.visit(w, g.getDist(v) + 1);
					// this is ehat visit does 
//					marked[e.to()] = true;
//			    	distTo[e.to()] = distance;
//			    	edgeTo[e.to()] = e;
					queue.addLast(w.to());
				}
			}
		}	
	}

	/**
	 * Calculates whether the graph is strongly connected
	 * 
	 * @param g
	 *            directed edge weighted graph
	 * @return whether graph g is strongly connected.
	 */
	public static boolean isStronglyConnected(EdgeWeightedDigraph g) {
		// do breadth-first search from start and make sure all vertices
		// have been visited. If not, return false.
		breadthFirstSearch(g, 0);
		for (int i = 0; i < g.V(); i++) {
			if (!g.isVisited(i)) {
				return false;
			}
		}
		// now reverse the graph, do another breadth-first search,
		// and make sure all visited again. If not, return false
		graphEdgeReversal(g);
		breadthFirstSearch(g, 0);
		for (int j = 0; j < g.V(); j++) {
			if (!g.isVisited(j)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Runs Dijkstra's algorithm on path to calculate the shortest path from
	 * starting vertex to every other vertex of the graph.
	 * 
	 * @param g
	 *            directed edge weighted graph
	 * @param s
	 *            starting vertex
	 * @return a hashmap where a key-value pair <i, path_i> corresponds to the i-th
	 *         vertex and path_i is an arraylist that contains the edges along the
	 *         shortest path from s to i.
	 */
	public static HashMap<Integer, ArrayList<DirectedEdge>> dijkstra(EdgeWeightedDigraph g, int s) {
		//reset graph
		g.reset();
		HashMap<Integer, ArrayList<DirectedEdge>> hash = new HashMap<Integer, ArrayList<DirectedEdge>>();
		
		for (int v = 0; v < g.V(); v++) {
			g.setDist(v, Double.POSITIVE_INFINITY);
		}
		g.setDist(s, 0.0);
		
		// relax vertices in order of ditstance from s
		IndexMinPQ<Double> pq = new IndexMinPQ<Double> (g.V());
		pq.insert(s, g.getDist(s));
		while(!pq.isEmpty()) {
			int v = pq.delMin();
			for (DirectedEdge e : g.adj(v)) {
				relax(e, g, pq);
			}
		}
		
		// for loop on v 
		// if distance of v is <inifinity (realxed), created array with directed edge (this is the path) 
		// once new path created,
//		for(int i = 0; i < g.edgeTo.length; i++) {
//			System.out.println(g.edgeTo[i]);
//		}
		for (int i = 0; i < g.V(); i++) { // loopping through vertices 
			if (g.getDist(i) <= Double.POSITIVE_INFINITY) { // the vertex is relaxed
				ArrayList<DirectedEdge> arr =  new ArrayList<DirectedEdge>();
				//System.out.println(arr);
				for (DirectedEdge e = g.getEdgeTo(i); e != null; e = g.getEdgeTo(e.from())) {
					arr.add(e);
//					System.out.println("arr" + arr);
//					System.out.println("e" + e);

					//System.out.println(arr);
				}
				hash.put(i,arr);
				//System.out.println(hash);
			}
			else {
				hash.put(i, null);
			}
		}
		return hash;
	}
	// relax edge e and updated pq if changed
	private static void relax(DirectedEdge e, EdgeWeightedDigraph g, IndexMinPQ<Double> pq ) {
		int v = e.from(), w = e.to();
		//System.out.println("v " + v + ", w " + w + ", dist v "+ g.getDist(v) + ", dist w "+ g.getDist(w) + "e weight " + e.weight());

		if (g.getDist(w) > (g.getDist(v) + e.weight())) {
			//System.out.println("45");
			double distW = g.getDist(w);
			distW =  g.getDist(v) + e.weight();
			g.setEdgeTo(e);
			if (pq.contains(w)) {
				pq.decreaseKey(w, distW);
				g.setDist(w, distW);
			}
			else {
				pq.insert(w, distW);
				g.setDist(w, distW);
			}
		}
	}

	/**
	 * Computes shortest path from start to end using Dijkstra's algorithm.
	 *
	 * @param g
	 *            directed graph
	 * @param start
	 *            starting node in search for shortest path
	 * @param end
	 *            ending node in search for shortest path
	 * @return a list of edges in that shortest path in correct order
	 */
	public static ArrayList<DirectedEdge> getShortestPath(EdgeWeightedDigraph g, int start, int end) {
		// run dijkstra and create a new ArrayList with edges running from start to end.
		HashMap<Integer, ArrayList<DirectedEdge>> hash = dijkstra(g, start);
		//System.out.println(hash);
		ArrayList<DirectedEdge> array = hash.get(end);
		//System.out.println(array);
		if (array != null) {
			Collections.reverse(array);
			return array;
		}
		else {
			return null;
		}
		
	}

	/**
	 * Using the output from getShortestPath, print the shortest path
	 * between two nodes
	 * 
	 * @param path shortest path from start to end
	 * @param isDistance prints it based on distance (true) or time (false)
	 */
	public static void printShortestPath(ArrayList<DirectedEdge> path, boolean isDistance, List<String> vertices) {
		double totalWeight = 0.0;
		System.out.print("Begin at " + vertices.get(0) + "\n");
		if (path != null) {
			for (DirectedEdge e : path) {
				//System.out.println(isDistance);
				if (e != null) {
					if (isDistance) {
						System.out.print("Conitnue to " + vertices.get(e.to()) + " (" + e.weight() + " miles)\n");
					}
					else {
						System.out.print("Conitnue to " + vertices.get(e.to()) + " (" + hoursToHMS(e.weight()) + ")\n");
					}
					totalWeight += e.weight();
				} 
			}
			
			DecimalFormat df = new DecimalFormat("#.#");
			if (isDistance) {
				System.out.println("Total distance: " + df.format(totalWeight) + " miles.");
			}
			else {
				System.out.println("Total time: " + hoursToHMS(totalWeight));
			}
		}
	}

	/**
	 * Converts hours (in decimal) to hours, minutes, and seconds
	 * 
	 * @param rawhours
	 *            time elapsed
	 * @return Equivalent of rawhours in hours, minutes, and seconds (to nearest
	 *         10th of a second)
	 */
	private static String hoursToHMS(double rawhours) {
		int numHours = (int)rawhours;
		double fractionalHours = rawhours - (double)numHours;
		int tenthSeconds = (int)Math.round(fractionalHours * 36000);
		int minutes = tenthSeconds / 600;
		int tenthSecondsLeft = tenthSeconds - 600 * minutes;
		double secondsLeft = (double)tenthSecondsLeft / 10.0;
		if (numHours != 0) {
			return numHours +  " hrs " + minutes + " mins " + secondsLeft + " secs";
		}
		else {
			return  minutes + " mins " + secondsLeft + " secs"; 
		}
		
	}
}