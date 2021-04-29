package model;

public class XY implements Comparable<XY> {

	private double x, y;
	private double dist;

	public XY(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setDistance(double d) {
		this.dist = d;
	}

	/*
	 * By allowing us to sort data by distance to some specific center, we can
	 * easily find the closest data when we need to
	 */
	@Override
	public int compareTo(XY o) {
		return Double.compare(dist, o.dist);
	}

}
