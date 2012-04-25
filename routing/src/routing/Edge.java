package routing;

public class Edge {
	private Router target;
	private double weight;

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

	void setTarget(Router newRarget) {
		target = newRarget;
	}

	void setWeight(double newWeight) {
		weight = newWeight;
	}
}
