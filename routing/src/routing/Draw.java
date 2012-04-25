package routing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;

public class Draw extends JPanel {
	Vector<Router> router_vector;
	static List<Router> router_list;
	private boolean drawRedLineFlag = false;

	Draw(Vector<Router> router) {
		router_vector = router;
	}

	void setRouterList(List<Router> list) {
		router_list = list;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.black);

		paintPoint(g2d, router_vector);

		paintLine(g, router_vector);
		g.setColor(Color.red);

		if (drawRedLineFlag) {
			paintRedLine(g, router_list);
		}

	}

	void paintPoint(Graphics2D g2d, Vector<Router> router_vector) {
		for (Router router : router_vector) {
			g2d.draw(new Ellipse2D.Double(router.getPoint().getX() - 5, router
					.getPoint().getY() - 5, 10, 10));
			g2d.drawString(router.getName(),
					(int) router.getPoint().getX() + 2, (int) router.getPoint()
							.getY() - 5);
		}
	}

	void paintLine(Graphics g, Vector<Router> router_vector) {
		for (Router router : router_vector) {
			int x = (int) router.getPoint().getX();
			int y = (int) router.getPoint().getY();
			for (Edge edge : router.getAdjacences()) {
				g.drawLine(x, y, (int) edge.getTarget().getPoint().getX(),
						(int) edge.getTarget().getPoint().getY());
				int x_mid = (x + (int) edge.getTarget().getPoint().getX()) / 2;
				int y_mid = (y + (int) edge.getTarget().getPoint().getY()) / 2;
				g.drawString(Double.toString(edge.getWeight()), x_mid,
						y_mid - 5);
			}
		}
	}

	void paintRedLine(Graphics g, List<Router> router_list) {
		// g2d.setColor(Color.RED);
		for (int index = 0; index < router_list.size() - 1; index++) {
			Point pointBegin = router_list.get(index).getPoint();
			Point pointEnd = router_list.get(index + 1).getPoint();
			g.drawLine(pointBegin.x, pointBegin.y, pointEnd.x, pointEnd.y);
		}
	}

	boolean getFlagTrue() {
		return drawRedLineFlag;
	}

	void setFlagFalse() {
		drawRedLineFlag = false;
	}

	void setFlagTrue() {
		drawRedLineFlag = true;
	}

}
