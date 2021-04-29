package charts;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import defs.Constants;
import model.Cluster;
import model.XY;

public class ScatterChart extends JFrame {

	/**
	 * Generated serialVersion
	 */
	private static final long serialVersionUID = 5693393203073677539L;

	private XYSeriesCollection fullChart;
	private List<XYSeries> data;
	private List<XYSeries> k;
	private boolean okToDraw;

	public ScatterChart() {
		super();
		this.okToDraw = false;
	}

	/*
	 * This method prepares the chart for drawing
	 */
	public boolean prepare() {
		this.fullChart = new XYSeriesCollection();
		// check to make sure we have data in the chart
		if (data == null) {
			this.okToDraw = false;
			return false;
		}
		// Add all series in data to the chart
		for (XYSeries serie : data) {
			this.fullChart.addSeries(serie);
		}
		// series in 'k' are actually single dots that represent the center of the
		// cluster
		if (k != null) {
			for (XYSeries serie : k) {
				this.fullChart.addSeries(serie);
			}
		}
		// Create dataset from all the chart-series
		XYDataset dataset = this.fullChart;
		JFreeChart chart = ChartFactory.createScatterPlot("", "X", "Y", dataset, PlotOrientation.VERTICAL, true, false,
				false);
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(new Color(255, 255, 255));

		// make window same width & height for easier reading
		ChartPanel panel = new ChartPanel(chart);
		panel.setPreferredSize(new Dimension(Constants.WINDOW_SIZE, Constants.WINDOW_SIZE));
		setContentPane(panel);

		this.okToDraw = true;
		return true;
	}

	public void reset() {
		this.resetData();
		this.resetK();
	}

	public void resetData() {
		this.data = new ArrayList<>();
	}

	public void resetK() {
		this.k = new ArrayList<>();
	}

	public void addData(Cluster cluster) {
		if (cluster == null) {
			return;
		}
		XYSeries serie = this.createSerie(cluster, false);
		if (serie != null) {
			this.data.add(serie);
		}
	}

	public void addK(Cluster cluster) {
		if (cluster == null) {
			return;
		}
		XYSeries serie = this.createSerie(cluster, true);
		if (serie != null) {
			this.k.add(serie);
		}
	}

	public boolean isOk() {
		return this.okToDraw;
	}

	private XYSeries createSerie(Cluster cluster, boolean k) {
		if (cluster == null) {
			return null;
		}
		// if k=true we are adding single dot serie where only datapoint is the center
		// of the cluster
		if (k) {
			if (this.k == null) {
				this.k = new ArrayList<>();
			}
			// In case someone tried to add center from a cluster that has not yet
			// calculated it's center
			if (cluster.getCenter() == null) {
				return null;
			}
			XYSeries serie = new XYSeries(cluster.getName() + " center");
			serie.add(cluster.getCenter().getX(), cluster.getCenter().getY());
			return serie;
		} else {
			if (this.data == null) {
				this.data = new ArrayList<>();
			}
			XYSeries serie = new XYSeries(cluster.getName());
			for (XY xy : cluster.getData()) {
				serie.add(xy.getX(), xy.getY());
			}
			return serie;
		}
	}
}
