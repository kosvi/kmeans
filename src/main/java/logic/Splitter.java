/*
 * This class uses Kruskal's algorithm to find two clusters from given data. 
 */

package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import model.Edge;
import model.XY;

public class Splitter {

	private List<XY> data;
	private Queue<Edge> edges;
	private HashMap<XY, List<Edge>> graph;
	private HashMap<XY, XY> parents;
	private List<XY> cluster1;
	private List<XY> cluster2;
	private XY center1;
	private XY center2;

	public Splitter(List<XY> data) {
		// we don't want to mess with original data
		this.data = new ArrayList<>();
		this.edges = new PriorityQueue<>();
		this.parents = new HashMap<>();
		for (XY xy : data) {
			XY xy2 = new XY(xy.getX(), xy.getY());
			this.data.add(xy2);
			this.parents.put(xy2, xy2);
			/*
			 * We want to create all edges in the graph and calculate weight for them
			 */
			for (XY i : data) {
				if (xy2.equals(i)) {
					continue;
				}
				Edge edge = new Edge(xy2, i);
				edge.setWeight(Distances.calculateDistance(xy2, i));
				this.edges.add(edge);
			}
		}
		this.generateGraph();
		this.createClusters();
	}

	private void generateGraph() {
		int edgesNeeded = this.data.size() - 2;
		this.graph = new HashMap<>();
		while (edgesNeeded > 0) {
			Edge shortest = this.edges.poll();
			if (findSet(shortest.getA()).equals(findSet(shortest.getB()))) {
				// these ends of the edge are already in the same set
				continue;
			}
			this.addEdgeToGraph(shortest);
			union(shortest.getA(), shortest.getB());
			edgesNeeded--;
		}
	}

	private void addEdgeToGraph(Edge edge) {
		XY a = edge.getA();
//		XY b = edge.getB();
		if (!this.graph.containsKey(a)) {
			this.graph.put(a, new ArrayList<Edge>());
		}
		this.graph.get(a).add(edge);
//		if (!this.graph.containsKey(b)) {
//			this.graph.put(b, new ArrayList<Edge>());
//		}
//		this.graph.get(b).add(edge);
	}

	private void createClusters2() {
		this.cluster1 = new ArrayList<>();
		this.cluster2 = new ArrayList<>();
		for (XY xy : this.data) {
			if (xy.equals(center1) || xy.equals(center2)) {
				continue;
			}
			double d1 = Distances.calculateDistance(xy, center1);
			double d2 = Distances.calculateDistance(xy, center2);
			if (d1 < d2) {
				this.cluster1.add(xy);
			} else {
				this.cluster2.add(xy);
			}
		}
	}

	private void createClusters() {
		this.cluster1 = new ArrayList<>();
		this.cluster2 = new ArrayList<>();
		HashSet<XY> visited = new HashSet<>();
		int cluster = 1;
		XY cluster1Set = this.findSet(this.data.get(0));
		// We can assume there is no cycles in the graph and it contains two separate
		// components
		for (int i = 0; i < this.data.size(); i++) {
			if (visited.contains(this.data.get(i))) {
				continue;
			}
			if (this.findSet(this.data.get(i)).equals(cluster1Set)) {
				this.depthFirstSearch(visited, 1, this.data.get(i));
			} else {
				this.depthFirstSearch(visited, 2, this.data.get(i));
			}
		}
		this.center1 = Distances.guessCenter(cluster1);
		this.center2 = Distances.guessCenter(cluster2);
		System.out.println("Cluster 1 size: " + this.cluster1.size());
		System.out.println("Cluster 2 size: " + this.cluster2.size());
	}

	private void depthFirstSearch(HashSet<XY> visited, int cluster, XY xy) {
		if (visited.contains(xy)) {
			return;
		}
		visited.add(xy);
		if (cluster == 1) {
			this.cluster1.add(xy);
		} else {
			this.cluster2.add(xy);
		}
		if (this.graph.get(xy) == null) {
			return;
		}
		for (Edge edge : this.graph.get(xy)) {
			this.depthFirstSearch(visited, cluster, edge.getB());
		}
	}

	private XY findSet(XY xy) {
		while (!xy.equals(this.parents.get(xy))) {
			xy = this.parents.get(xy);
		}
		return xy;
	}

	private void union(XY xy, XY xy2) {
		xy = findSet(xy);
		xy2 = findSet(xy2);
		this.parents.put(xy, xy2);
	}

	public List<XY> getCluster1() {
		return cluster1;
	}

	public List<XY> getCluster2() {
		return cluster2;
	}

	public XY getCenter1() {
		return center1;
	}

	public XY getCenter2() {
		return center2;
	}

}
