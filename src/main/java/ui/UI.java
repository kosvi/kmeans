package ui;

import java.util.List;
import java.util.Scanner;

import javax.swing.WindowConstants;

import org.jfree.ui.RefineryUtilities;

import charts.ScatterChart;
import defs.Constants;
import logic.ClusterFactory;
import logic.DataGenerator;
import logic.Distances;
import model.Cluster;
import model.Clusters;
import model.XY;

/*
 * This is just the UI. Nothing interesting here. 
 */

public class UI {

	private ScatterChart sc;
	private Scanner scanner;
	private Clusters clusters;
	private List<XY> data;
	private int n, k, m;

	public UI(Scanner s, Clusters c) {
		this.sc = new ScatterChart();
		this.scanner = s;
		this.clusters = c;
		this.data = null;
		this.n = 0;
		this.k = 0;
	}

	public UI(Scanner s, Clusters c, int n, int k, int m) {
		this(s, c);
		this.n = n;
		this.k = k;
		this.m = m;
	}

	public void start() {
		while (true) {
			int s = printMenu();
			if (s == 5) {
				break;
			}
			if (s == 1) {
				setParams();
				continue;
			}
			if (s == 2) {
				generateData();
				continue;
			}
			if (s == 3) {
				drawRawData();
				continue;
			}
			if (s == 4) {
				generateAndDrawClusters();
				continue;
			}
		}
	}

	private int printMenu() {
		String data = (String) ((this.data == null) ? "null" : "" + this.data.size());
		System.out.println("--");
		System.out.println("1) set parameters (n=" + n + ", k=" + k + ")");
		System.out.println("2) generate data (data: " + data + ")");
		System.out.println("3) draw raw data");
		System.out.println("4) calculate and draw clusters");
		System.out.println("5) exit");
		System.out.print("> ");
		int s = 0;
		try {
			s = Integer.parseInt(scanner.nextLine());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return s;
	}

	private boolean generateAndDrawClusters() {
		if (this.data == null) {
			return false;
		}
		ClusterFactory factory = new ClusterFactory(this.data, this.n, this.k);
		factory.generateClusters();
		this.clusters = factory.getClusters();
		sc.reset();
		for (Cluster c : this.clusters.getClusters()) {
			sc.addData(c);
		}
		for (Cluster c : this.clusters.getClusters()) {
			sc.addK(c);
		}
		return this.drawChart();
	}

	private boolean drawRawData() {
		if (this.data == null) {
			return false;
		}
		Cluster c = new Cluster("Raw", this.data, Distances.guessCenter(this.data));
		sc.reset();
		sc.addData(c);
		sc.addK(c);
		return this.drawChart();
	}

	private boolean generateData() {
		if (this.n < 1) {
			return false;
		}
		DataGenerator dg = new DataGenerator();
		this.data = dg.generateXYList(n, m);
		return true;
	}

	private void setParams() {
		while (true) {
			System.out.println("--");
			System.out.print("set n>" + Constants.MIN_N + " : ");
			int n = 0;
			try {
				n = Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {
				System.err.println(e.getMessage());
				continue;
			}
			System.out.print("set k>" + Constants.MIN_K + " : ");
			int k = 0;
			try {
				k = Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {
				System.err.println(e.getMessage());
				continue;
			}
			System.out.print("set m>" + Constants.MIN_M + " : ");
			int m = 0;
			try {
				m = Integer.parseInt(scanner.nextLine());
			} catch (Exception e) {
				System.err.println(e.getMessage());
				continue;
			}
			if (n < Constants.MIN_N || k < Constants.MIN_K || m < Constants.MIN_M) {
				continue;
			}
			if (n > Constants.MAX_N || k > Constants.MAX_K || m > Constants.MAX_M) {
				continue;
			}
			this.initialize();
			this.n = n;
			this.k = k;
			this.m = m;
			break;
		}
	}

	private void initialize() {
		this.sc = new ScatterChart();
		this.clusters = new Clusters();
		this.data = null;
	}

	private boolean drawChart() {
		if (sc.prepare()) {
			sc.pack();
			RefineryUtilities.centerFrameOnScreen(sc);
			sc.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			sc.setVisible(true);
			return true;
		}
		return false;
	}
}
