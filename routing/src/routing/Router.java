package routing;

import java.awt.Point;
import java.util.Vector;

public class Router implements Comparable<Router> {
	private String router_name;
	private double minDistance = Double.POSITIVE_INFINITY;
	private double minDistance_congestion = Double.POSITIVE_INFINITY;
	private Vector<Edge> adjacenciese = new Vector<Edge>(10);
	private Router previous;
	private Point point = new Point();

	@Override
	public int compareTo(Router adjacent) {
		// TODO Auto-generated method stub
		return Double.compare(minDistance_congestion, adjacent.minDistance_congestion);
	}

	Router(String name) {
		router_name = name;
	}

	String getName() {
		return router_name;
	}

	Vector<Edge> getAdjacences() {
		return adjacenciese;
	}

	void addAdjacent(Edge adj) {
		adjacenciese.add(adj);
	}

	void removeAdjacent(Edge adj) {
		adjacenciese.remove(adj);
	}

	void setPreviouse(Router pre) {
		previous = pre;
	}

	Router getPreviouse() {
		return previous;
	}

	void setMinDistance(double min) {
		minDistance = min;
	}
	
	void setMinDistanceCongestion(double min)
	{
		minDistance_congestion = min;
	}

	double getMinDistance() {
		return minDistance;
	}
	
	double getMinDistanceCongestion()
	{
		return minDistance_congestion;
	}

	void setName(String name) {
		router_name = name;
	}

	void setPoint(int x, int y) {
		point.setLocation(x, y);
	}

	Point getPoint() {
		return point;
	}
}
