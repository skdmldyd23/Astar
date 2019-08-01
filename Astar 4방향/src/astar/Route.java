package astar;

import java.util.LinkedList;
import java.util.List;

public class Route {
	private static final int WIDTH = 16;
	private static final int HEIGHT = 16;
	public static final int COST = 1;
	Node[][] nodes;
	Map map;
	List<Node> openList = new LinkedList<Node>();
	List<Node> closedList = new LinkedList<Node>();
	List<Node> copyopenList = new LinkedList<Node>();

	Route() {
		map = new Map(this);
		nodes = map.getNodes();
	}

	public LinkedList<Node> findPath(int startx, int starty, int endx, int endy) {

		if (startx == endx && starty == endy) {
			return new LinkedList<Node>();
		}
		nodes[startx][starty].setG(0);
		nodes[startx][starty].setH(0);
		nodes[startx][starty].setF(0);

		openList = new LinkedList<Node>();
		closedList = new LinkedList<Node>();
		copyopenList = new LinkedList<Node>();
		
		openList.add(nodes[startx][starty]);

		while (true) {
			Node current = lowestFInList(openList);
			openList.remove(current);
			System.out.println(current.getX() + ", " + current.getY());
			closedList.add(current);
			if (current.getX() == endx && current.getY() == endy) {

				return Calcpath(nodes[startx][starty], current);
			}
			List<Node> adjacentNodes = getAdjacent(current, closedList);

			for (Node adjacent : adjacentNodes) {

				if (!openList.contains(adjacent)) {

					adjacent.setParent(current);
					adjacent.setH((Math.abs(adjacent.getX() - endx)) + (Math.abs(adjacent.getY() - endy)));
					if (current.parent == null) {
						adjacent.setG(COST);
					} else {
						adjacent.setG(current.parent.getG() + COST);
					}
					adjacent.setF(adjacent.getG() + adjacent.getH());
					openList.add(adjacent);
					copyopenList.add(adjacent);
				} else if (adjacent.getH() > current.getH()) {
					adjacent.setParent(current);
					adjacent.setG(current.parent.getG() + COST);
				}

			}
			if (openList.isEmpty()) {
				return new LinkedList<Node>();
			}
		}
	}

	public void followPath() {

	}

	private LinkedList<Node> Calcpath(Node start, Node end) {
		LinkedList<Node> path = new LinkedList<Node>();

		Node node = end;

		while (true) {
			
			
			path.addFirst(node);
			node = node.getParent();
			if (node == start)
				break;
		}
		return path;
	}

	private Node lowestFInList(List<Node> list) {
		Node cheapest = list.get(0);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getF() < cheapest.getF()) {
				cheapest = list.get(i);
			}
		}
		return cheapest;
	}

	private List<Node> getAdjacent(Node current, List<Node> closedList) {
		// TODO Auto-generated method stub
		List<Node> adjacentNode = new LinkedList<Node>();
		Node adjacent;

		int x = current.getX();
		int y = current.getY();

		if (x > 0) {
			adjacent = nodes[x - 1][y];
			if (!closedList.contains(adjacent) && nodes[x - 1][y].isRoad() == true && (x - 1) > 0) {
				adjacentNode.add(adjacent);
			}
		}
		if (x < WIDTH - 1) {
			adjacent = nodes[x + 1][y];
			if (!closedList.contains(adjacent) && nodes[x + 1][y].isRoad() == true && (x + 1) < WIDTH) {
				adjacentNode.add(adjacent);
			}
		}
		if (y > 0) {
			adjacent = nodes[x][y - 1];
			if (!closedList.contains(adjacent) && nodes[x][y - 1].isRoad() == true && (y - 1) > 0) {
				adjacentNode.add(adjacent);
			}
		}
		if (y < HEIGHT - 1) {
			adjacent = nodes[x][y + 1];
			if (!closedList.contains(adjacent) && nodes[x][y + 1].isRoad() == true && (y + 1) < HEIGHT) {
				adjacentNode.add(adjacent);
			}
		}

		return adjacentNode;
	}
}