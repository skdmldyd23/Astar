package astar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

class Game {
	Node[][] nodes;
	Map map;
	LinkedList<Node> openList;
	LinkedList<Node> closedList;
	LinkedList<Node> drawOpenList;
	//LinkedList<Node> drawCloseList;
	int startx , starty, endx, endy,count;	
	
	//게임 생성
	Game(){
		map = new Map(this);
		nodes = map.getNodes();
	}
	//리드로우
	public void redraw() {
		 while (true) {
             startx = (int) ((Math.random()) * Map.WIDTH);
             starty = (int) ((Math.random()) * Map.HEIGHT);
             endx = (int) ((Math.random()) * Map.WIDTH);
             endy = (int) ((Math.random()) * Map.HEIGHT);
             
             if (startx == endx && starty == endy) //출발지==도착지
                continue;
             
             if (!nodes[startx][starty].isRoad() || !nodes[endx][endy].isRoad()) //벽체크
                continue;
                             
             for (int i = 0; i < Map.WIDTH; ++i) { //redraw
     			for (int j = 0; j < Map.HEIGHT; ++j) {
     				if(!nodes[i][j].isRoad()) {
     					nodes[i][j].getButton().setBackground(Color.BLACK);
     				}else {
     					nodes[i][j].getButton().setBackground(Color.WHITE);
     				}
     			}
     		}
             nodes[startx][starty].getButton().setBackground(Color.YELLOW);
             nodes[endx][endy].getButton().setBackground(Color.BLUE);
             System.out.println("출발 : " +startx+", "+starty );
             System.out.println("끝 : " +endx+", "+endy );
             
             break;
		 }		  
	}
	public void pathDraw(LinkedList<Node> path) {
		path.removeLast(); //도착지는 경로에서 뺌 , 색깔 처리 때문	
		count = path.size();

		new Thread() {			
			@Override
			public void run() {
				Iterator<Node> it2 = drawOpenList.iterator();
				while(it2.hasNext()) {
					Node node = it2.next();
					if(!(node.getX()==endx && node.getY()==endy))
					node.getButton().setBackground(Color.GREEN);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}	
				}
				Iterator<Node> it = path.iterator();
				while(it.hasNext()) {
					Node node = it.next();
					node.getButton().setBackground(Color.RED);
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			}
		}.start();	   
	}

	//길 찾기
	public LinkedList<Node> search() {
		
		Node startNode = nodes[startx][starty];
		Node endNode = nodes[endx][endy];
		
		if (startNode.getX() == endNode.getX() && startNode.getY() == endNode.getY())
		{
			
			return new LinkedList<Node>();
		}
		openList = new LinkedList<Node>();
		closedList = new LinkedList<Node>();
		drawOpenList = new LinkedList<Node>();
		
		openList.add(startNode); //시작 노드 추가
		
		Node current = startNode;
		current.setG(0);
		current.calcH(endNode);
		current.calcF();
		System.out.println("시작 : "+current.getX()+" , "+current.getY());
		
		while(true) {			
			
			current = openList.getFirst();
			for (Node item : openList) {
				if(item.getF() < current.getF()) {
					current = item;					
				}else if(item.getF() == current.getF()){
					if(item.getG() < current.getG()) {
						current = item;
					}
				}
			}
			System.out.println("current : "+current.getX()+" , "+current.getY());
			System.out.println("current F : "+ current.getF());
			openList.remove(current);
			closedList.add(current);
			
						
			if(current.getX() == endNode.getX() && current.getY() == endNode.getY()) {
				return calcPath(startNode,current);
			}
			
			LinkedList<Node> adjacentNodes = adjacentList(current);
			for(Node adjacent : adjacentNodes) {
				if(!inOpenList(adjacent)) {
					 adjacent.setParent(current);
					 adjacent.calcH(endNode);
					 int g =adjacent.calcG(current);
					 adjacent.setG(g);					 
					 adjacent.calcF();
					 openList.add(adjacent);	
					 drawOpenList.add(adjacent);
				 }else if(adjacent.getG() > adjacent.calcG(current)) {
					 adjacent.setParent(current);
					 int g =adjacent.calcG(current);
					 adjacent.setG(g);
				 }
			 }
			 
			 if (openList.isEmpty())
				 return new LinkedList<Node>();			 		
		}		
	}
	
	public LinkedList<Node> calcPath(Node startNode, Node endNode){
		
		LinkedList<Node> path = new LinkedList<Node>();
		Node node = endNode;
		
		while(true) {
			path.addFirst(node);
			node = node.getParent();
			if(node.equals(startNode)) {
				break;
			}
		}
		return path;
	}
	
	public LinkedList<Node> adjacentList(Node current) {
		
		int currentX = current.getX();
		int currentY = current.getY();
		int maxX = nodes[0].length; //16
		int maxY = nodes.length; //16
		LinkedList<Node> adjacentNodes = new LinkedList<Node>();
		for(int y = -1 ; y <=1 ; y++) {
			for(int x = -1 ; x<= 1; x++) {
				if(currentX+x < maxX 
						&& currentX+x >=0
						&& currentY+y < maxX
						&& currentY+y >=0
						&&nodes[currentX+x][currentY+y].isRoad() 
						&& !closedList.contains(nodes[currentX+x][currentY+y])) { //downNode
					adjacentNodes.add(nodes[currentX+x][currentY+y]);
				}
			}
		}
//		if(currentY+1 < maxY && nodes[currentX][currentY+1].isRoad() && !closedList.contains(nodes[currentX][currentY+1])) { //rightNode
//			adjacentNodes.add(nodes[currentX][currentY+1]);
//		}
//		if(currentX+1 < maxX && nodes[currentX+1][currentY].isRoad() && !closedList.contains(nodes[currentX+1][currentY])) { //downNode
//			adjacentNodes.add(nodes[currentX+1][currentY]);
//		}
//		if(currentY-1 >= 0 && nodes[currentX][currentY-1].isRoad() && !closedList.contains(nodes[currentX][currentY-1]) ) { //leftNode
//			adjacentNodes.add(nodes[currentX][currentY-1]);
//		}
//		if(currentX-1 >= 0 && nodes[currentX-1][currentY].isRoad() && !closedList.contains(nodes[currentX-1][currentY])) { //upNode
//			adjacentNodes.add(nodes[currentX-1][currentY]);
//		}
		return adjacentNodes;
	}
	
	public boolean inOpenList(Node adjacent) {
		for(Node item : openList) {
			if(item.equals(adjacent)) {
				return true;
			}
		}
		return false;
	}
	
}
