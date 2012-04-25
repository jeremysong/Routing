package routing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra_Core {
	List<Router> router_list = new ArrayList<Router>();;

	Dijkstra_Core(Router source, Router destination) {
		// TODO Auto-generated constructor stub
		computePaths(source);
		getShortestPathTo(destination);
	}

	private void computePaths(Router source) {
		source.setMinDistance(0.);
		PriorityQueue<Router> routerQueue = new PriorityQueue<Router>();
		routerQueue.add(source);

		while (!routerQueue.isEmpty()) {
			Router u = routerQueue.poll();

			// Visit each edge exiting u
			for (Edge e : u.getAdjacences()) {
				Router v = e.getTarget();
				double weight = e.getWeight();
				double distanceThroughU = u.getMinDistance() + weight;

				if (distanceThroughU < v.getMinDistance()) {
					routerQueue.remove(v);

					v.setMinDistance(distanceThroughU);
					v.setPreviouse(u);
					routerQueue.add(v);
				}
			}
		}
	}

	private void getShortestPathTo(Router destination) {
		for (Router router = destination; router != null; router = router
				.getPreviouse()) {
			router_list.add(router);

		}
		Collections.reverse(router_list);
	}
	
	List<Router> getRouterList()
	{
		return router_list;
	}
}
