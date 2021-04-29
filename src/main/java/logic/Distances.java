package logic;

import java.util.List;

import defs.Constants;
import model.XY;

public class Distances {

	public static double calculateDistance(XY p, List<XY> all) {
		double distance = 0;
		for (XY xy : all) {
			distance += calculateDistance(p, xy);
		}
		return distance;
	}

	public static double calculateDistance(XY p, XY o) {
		return getDistance(p.getX(), p.getY(), o.getX(), o.getY());
	}

	private static double getDistance(double x1, double y1, double x2, double y2) {
		// Let's simply use Pythagoras here
		return Math.sqrt((Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2)));
	}

	/*
	 * This method simply calculates the arithmetic mean for all the date in the
	 * list given. This will not result to a center with smallest sum of distance to
	 * all the data:
	 * 
	 * avg(1,5,6) = 12/3 = 4
	 * 
	 * sum(|1-4|,|5-4|,|6-4|) = 6
	 * 
	 * sum(|1-5|,|5-5|,|6-5|) = 5
	 * 
	 * So as you can see, arithemtic mean of 1, 5 and 6 is 4, but center should be 5
	 * (sum of distances is smallest)
	 */
	public static XY guessCenter(List<XY> all) {
		double sumX = 0, sumY = 0;
		int i = 0;
		for (XY xy : all) {
			i++;
			sumX += xy.getX();
			sumY += xy.getY();
		}
		XY center = new XY(sumX / (double) i, sumY / (double) i);
		return center;
	}
}
