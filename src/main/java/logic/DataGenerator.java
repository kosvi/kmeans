package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.XY;

public class DataGenerator {

	private Random r;

	public DataGenerator() {
		r = new Random();
	}

	public List<XY> generateXYClusters(int n, int m, int k) {
		List<XY> data = new ArrayList<>();
		int dotsPerCluster = n / k;
		// std = 5 % of matrix width
		double std = (double) m * 0.05;
		for (int i = 0; i < k; i++) {
			// Randomize cluster center
			// and make sure it is 1x std from borders
			int centerX = r.nextInt(m - (int) (2*std)) + (int) std;
			int centerY = r.nextInt(m - (int) (2*std)) + (int) std;
			int numberOfData = Math.min(dotsPerCluster, n);
			n -= dotsPerCluster;
			for (int j = 0; j < numberOfData; j++) {
				double x = centerX + r.nextGaussian() * std;
				double y = centerY + r.nextGaussian() * std;
				data.add(new XY(x, y));
			}
		}
		return data;
	}

	public XY generateXYPoint(int m) {
		int x = r.nextInt(m) + 1;
		int y = r.nextInt(m) + 1;
		return new XY((double) x, (double) y);
	}

	public List<XY> generateXYList(int n, int m) {
		List<XY> l = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			l.add(this.generateXYPoint(m));
		}
		return l;
	}
}
