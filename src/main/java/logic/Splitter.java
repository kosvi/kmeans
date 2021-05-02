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
			this.data.add(xy);
			this.parents.put(xy, xy);
			/*
			 * We want to create all edges in the graph and calculate weight for them
			 */
			for (XY xy2 : data) {
				if (xy.equals(xy2)) {
					continue;
				}
				Edge edge = new Edge(xy, xy2);
				edge.setWeight(Distances.calculateDistance(xy, xy2));
				this.edges.add(edge);
			}
		}
		this.findCenters();
		this.createClusters();
	}

	private void findCenters() {
		int edgesNeeded = this.data.size() - 2;
		Edge longest = this.edges.poll();
		while (edgesNeeded > 0) {
			Edge shortest = this.edges.poll();
			if (findSet(shortest.getA()).equals(findSet(shortest.getB()))) {
				// these ends of the edge are already in the same set
				continue;
			}
			union(shortest.getA(), shortest.getB());
			longest = shortest;
			edgesNeeded--;
		}
		this.center1 = longest.getA();
		this.center2 = longest.getB();
	}

	private void createClusters() {
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
