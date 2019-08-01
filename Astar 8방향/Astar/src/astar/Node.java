package astar;

import javax.swing.JButton;

class Node {
	JButton button;
	final int COST = 1;
	boolean road;
	Node parent;
	int x, y, g, h, f;

	public Node(JButton button, int x, int y) {
		this.button = button;
		this.x = x;
		this.y = y;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public boolean isRoad() {
		return road;
	}

	public void setRoad(boolean road) {
		this.road = road;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public JButton getButton() {
		return button;
	}

	public int calcG(Node parent) {
		return parent.getG() + COST;

	}

	public void calcH(Node endNode) {
		h = (Math.abs(getX() - endNode.getX()) + Math.abs(getY() - endNode.getY())) * COST;
	}

	public void calcF() {
		f = g + h;
	}

	@Override
	public String toString() {
		// return this.road==true? "[ x: "+x+" y: "+y+" g :"+g+" h :"+h+" f :"+f+" ]" :
		// "X";
		return this.road == true ? "O" : "X";
	}

}