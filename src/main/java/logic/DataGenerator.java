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

	public XY generateXYPoint(int m) {
		int x = r.nextInt(m) + 1;
		int y = r.nextInt(m) + 1;
//		int x = m/2, y=m/2;
//		while(x>m/4 && x<(m-m/4) && y>m/4 && y<(m-m/4)) {
//			x = r.nextInt(m) + 1;
//		y = r.nextInt(m) + 1;
//		}
//		if (r.nextBoolean()) {
//			x = r.nextInt(m / 8) + m / 8;
//			x += m / 2;
//		} else {
//			y = r.nextInt(m / 8) + m / 8;
//			y += m / 2;
//		} 
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
