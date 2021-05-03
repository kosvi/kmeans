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
		double std = (double) m / 25.0;
		for (int i = 0; i < k; i++) {
			int centerX = r.nextInt(m - (2 * m / 25)) + m / 25;
			int centerY = r.nextInt(m - (2 * m / 25)) + m / 25;
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
