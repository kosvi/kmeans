package model;

import java.util.ArrayList;
import java.util.List;

public class Cluster implements Comparable<Cluster> {

	private List<XY> data;
	private XY center;
	private String name;

	public Cluster() {
		this.data = new ArrayList<>();
	}

	public Cluster(String name) {
		this();
		this.name = name;
	}

	public Cluster(String name, List<XY> data) {
		this(name);
		/*
		 * We do NOT want to mess the data given as parameter, so we will create new
		 * instances and use the values given as parameter.
		 */
		for (XY a : data) {
			this.data.add(new XY(a.getX(), a.getY()));
		}
	}

	public Cluster(String name, List<XY> data, XY center) {
		this(name, data);
		this.center = new XY(center.getX(), center.getY());
	}

	public void clearXY() {
		this.data = new ArrayList<>();
	}

	public boolean addXY(XY a) {
		if (this.data != null) {
			// Like in the constructor: let's keep the original data as it is
			// so we don't poke the same object in all over the code
			this.data.add(new XY(a.getX(), a.getY()));
			return true;
		}
		return false;
	}

	public void addCenter(XY c) {
		this.center = new XY(c.getX(), c.getY());
	}

	public List<XY> getData() {
		return data;
	}

	public XY getCenter() {
		return center;
	}

	public String getName() {
		return name;
	}

	/*
	 * This might not have much use for now
	 */
	@Override
	public int compareTo(Cluster o) {
		return this.name.compareTo(o.name);
	}

	@Override
	public String toString() {
		if (center == null) {
			return null;
		}
		return "(" + center.getX() + ", " + center.getY() + ")";
	}
}
