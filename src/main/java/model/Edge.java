package model;

public class Edge implements Comparable<Edge> {

	private XY a, b;
	private double weight;

	public Edge(XY a, XY b) {
		this.a = a;
		this.b = b;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public XY getA() {
		return a;
	}

	public XY getB() {
		return b;
	}

	@Override
	public int compareTo(Edge o) {
		return Double.compare(weight, o.weight);
	}

}
