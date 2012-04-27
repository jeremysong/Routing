package routing;

public class Edge {
	private Router target;
	private double weight;
	private double congestion;

	Edge(Router newTarge, double newWeight) {
		target = newTarge;
		weight = newWeight;
	}

	Router getTarget() {
		return target;
	}

	double getWeight() {
		return weight;
	}
	
	double getCongestion()
	{
		return congestion;
	}

	void setTarget(Router newRarget) {
		target = newRarget;
	}

	void setWeight(double newWeight) {
		weight = newWeight;
	}
	
	void setCongestion(double newCon)
	{
		congestion = newCon;
	}
}
