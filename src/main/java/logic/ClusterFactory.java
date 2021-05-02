package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import defs.Constants;
import model.Cluster;
import model.Clusters;
import model.XY;

/*
 * Our cluster factory produces clusters with centers
 */

public class ClusterFactory {

	private int n, k;
	private Clusters clusters;
	private Queue<XY> queue;
	private List<XY> rawData;
	private List<XY> originalData;

	public ClusterFactory(List<XY> raw, int n, int k) {
		this.n = n;
		this.k = k;
		this.originalData = raw;
		this.rawData = new ArrayList<>();
		this.queue = new PriorityQueue<>();
		// We do not want to mess the original data,
		// so we store all the data this way
		for (XY xy : raw) {
			XY xy2 = new XY(xy.getX(), xy.getY());
			this.rawData.add(xy2);
		}
	}

	public void generateClusters() {
		// first we want to create clusters
		this.createKClusters();
		// After that we start moving data to closest cluster and cluster centers to
		// center of current cluster repeatedly
		this.moveAllToNearestK();
		String centers = "";
		System.out.println("Iterating cluster centers: ");
		for (int i = 0; i < Constants.NUMBER_OF_ITERATIONS; i++) {
			this.moveCenters();
			String newCenters = this.clusters.toString();
			System.out.println(newCenters);
			// If centers are not moved, then we do not need to iterate any more
			if (centers.equals(newCenters)) {
				break;
			}
			centers = newCenters;
			this.moveAllToNearestK();
		}
		System.out.println("Cluster sizes: ");
		System.out.println(this.clusters.getClusterSizes());
		System.out.println("Sum of all distances to nearest center: " + this.clusters.getSumOfDistances());
	}

	private void moveCenters() {
		for (Cluster c : clusters.getClusters()) {
			// guessCenter does not actually get actual center but usually a really good
			// guess where it could be
			XY newCenter = Distances.guessCenter(c.getData());
			c.addCenter(newCenter);
		}
	}

	private void moveAllToNearestK() {
		for (Cluster c : this.clusters.getClusters()) {
			c.clearXY();
		}
		// This is why we didn't want to mess with the original data
		// Now we can use the original data to calculate new clusters for each and every
		// entry in our dataset
		for (XY xy : originalData) {
			double smallestDistance = Constants.INFINITE;
			int cluster = -1;
			// Loop trough all clusters and compare distance to their centres finally
			// choosing the closest
			for (int i = 0; i < k; i++) {
				double distance = Distances.calculateDistance(xy, clusters.getClusters().get(i).getCenter());
				if (distance < smallestDistance) {
					smallestDistance = distance;
					cluster = i;
				}
			}
			// I am not absolutely sure that we don't accidentally loose a datapoint or two
			// This will probably need more investigation
			if (cluster >= 0) {
				clusters.getClusters().get(cluster).addXY(new XY(xy.getX(), xy.getY()));
			}
		}
	}

	private void createKClusters() {
		this.clusters = new Clusters();
		Random r = new Random();
		for (int i = 1; i <= k; i++) {
			// Let's first randomly put some "center" points here and there in the dataspace
			XY center = new XY(r.nextInt(n / 2) + (n / 4), r.nextInt(n / 2) + (n / 4));
			for (XY xy : this.rawData) {
				xy.setDistance(Distances.calculateDistance(xy, center));
				// By using priorityqueue we can easily find closest data to our newly created
				// center
				this.queue.add(xy);
			}
			List<XY> newClusterData = new ArrayList<>();
			// Here we add n/k number of closest datapoints to currenly generated cluster
			for (int j = 0; j < n / k; j++) {
				newClusterData.add(this.queue.poll());
			}
			// And we move the center to meet the data we just inserted
			center = Distances.guessCenter(newClusterData);
			Cluster c = new Cluster("Data" + i, newClusterData, center);
			this.clusters.addCluster(c);
			this.rawData = new ArrayList<>();
			// Finally add all remaining data to new "rawdata" to be used in giving birth to
			// new clusters
			while (!this.queue.isEmpty()) {
				this.rawData.add(this.queue.poll());
			}
		}
	}

	public Clusters getClusters() {
		return clusters;
	}

}
