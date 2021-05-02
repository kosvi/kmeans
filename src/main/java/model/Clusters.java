package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Clusters {

	private List<Cluster> clusters;

	public Clusters() {
		this.clusters = new ArrayList<>();
	}

	public void addCluster(Cluster c) {
		this.clusters.add(c);
	}

	public List<Cluster> getClusters() {
		Collections.sort(clusters);
		return clusters;
	}

	public double getSumOfDistances() {
		if (clusters == null) {
			return -1;
		}
		double sum = 0;
		for (Cluster c : clusters) {
			sum += c.getSumOfDistances();
		}
		return sum;
	}

	public String getClusterSizes() {
		StringBuilder sb = new StringBuilder();
		for (Cluster c : clusters) {
			sb.append(c.getName() + ": ");
			sb.append(c.getData().size());
			sb.append(" ");
		}
		return sb.toString();
	}

	public Cluster getClusterByName(String name) {
		for (Cluster c : clusters) {
			if (c.getName().equalsIgnoreCase(name)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Cluster c : clusters) {
			sb.append(c.toString());
			sb.append(" ");
		}
		return sb.toString();
	}
}
