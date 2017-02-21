package org.csc301;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Grid {

	private final int DEFAULT_WIDTH = 60; // default width of the world map - gridX runs from 0 to 59
	private final int DEFAULT_HEIGHT = 15; // default height of the map - gridY runs from 0 to 14
	private final int DEFAULT_PERCENT = 20; // this is the percentageof the map occupied by islands
	protected int width, height; // user defined width and height, if one is not using defaults
	protected int percent; // user defined percentage of islands on the map
	protected Node treasure; // points to the map node where the Redbeard treasure is sunken
	protected Node boat; // points to the current location of our boat on the map

	protected Node[][] map; // the map

	public Grid() {
		width = DEFAULT_WIDTH;
		height = DEFAULT_HEIGHT;
		percent = DEFAULT_PERCENT;
		buildMap();
	}

	public Grid(int width, int height, int percent) {
		this.width = width;
		this.height = height;
		if (percent <= 0 || percent >= 100)
			this.percent = DEFAULT_PERCENT;
		else
			this.percent = percent;
		buildMap();
	}

	private void buildMap() {
		// Your implementation goes here
		// For each map position (i,j) you need to generate a Node with can be navigable or it may belong to an island
		// You may use ideas from Lab3 here.
		// Don't forget to generate the location of the boat and of the treasure; they must be on navigable waters, not on the land!
		
		//create the map and place the boat
		map = new Node[height][width];
		Random rand = new Random();
		int boatX = rand.nextInt(width);
		int boatY = rand.nextInt(height);
		boat = new Node(true, boatX, boatY);
		map[boatX][boatY] = boat;
		
		int treasX = rand.nextInt(width);
		int treasY = rand.nextInt(height);
		
		//find a spot to place the treasure
		while (map[treasX][treasY] != null){
			treasX = rand.nextInt(width);
			treasY = rand.nextInt(height);
		}
		treasure = new Node(true, treasX, treasY);
		map[treasX][treasY] = treasure;
		
		//Fill the grid with island or navigable
		for(int i=0; i<width;i++){
			for(int j=0; j<height;j++){
				if (map[i][j] != null){
					if(rand.nextInt(99)<percent){
						map[i][j] = new Node(false, i, j);
					}
					else{
						map[i][j] = new Node(true, i, j);
					}
				}
			}
		}
	}

	public String drawMap() {
		// provided for your convenience
		String result = "";
		String hline = "       ";
		String extraSpace;
		for (int i = 0; i < width / 10; i++)
			hline += "         " + (i + 1);
		result += hline + "\n";
		hline = "       ";
		for (int i = 0; i < width; i++)
			hline += (i % 10);
		result += hline + "\n";
		for (int i = 0; i < height; i++) {
			if (i < 10)
				extraSpace = "      ";
			else
				extraSpace = "     ";
			hline = extraSpace + i;
			for (int j = 0; j < width; j++) {
				if (i == boat.gridY && j == boat.gridX)
					hline += "B";
				else if (i == treasure.gridY && j == treasure.gridX)
					hline += "T";
				else if (map[i][j].inPath)
					hline += "*";
				else if (map[i][j].walkable)
					hline += ".";
				else
					hline += "+";
			}
			result += hline + i + "\n";
		}
		hline = "       ";
		for (int i = 0; i < width; i++)
			hline += (i % 10);
		result += hline + "\n";
		return result;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getPercent() {
		return percent;
	}
	
	public Node getBoat() {
		return boat;
	}
	
	private ArrayList<Node> getNeighbours(Node node) {
		// each node has at most 8 neighbours
		// Lab3 may be useful here as well
		ArrayList<Node> neighbours = new ArrayList<Node>();
		boolean n,s,e,w;
		n = (node.gridY - 1) >=0;
		s = (node.gridY + 1) < height;
		e = (node.gridX + 1) < width;
		w = (node.gridX - 1) >= 0;
		
		//north
		if((node.gridY - 1) >=0){
			neighbours.add(map[node.gridY-1][node.gridX]);
		}
		
		//north west
		if(((node.gridY - 1) >=0) && ((node.gridX - 1) >= 0)){
			neighbours.add(map[node.gridY-1][node.gridX-1]);
		}
		
		//north east
		if(((node.gridY + 1) < height) && ((node.gridX + 1) < width)){
			neighbours.add(map[node.gridY-1][node.gridX-1]);
		}
		
		//south east
		if(((node.gridX + 1) < width) && ((node.gridY + 1) < height)){
			neighbours.add(map[node.gridY +1][node.gridX +1]);
		}
		
		//south
		if((node.gridY + 1) < height){
			neighbours.add(map[node.gridY+1][node.gridX]);
		}
		
		//south west
		if(((node.gridY + 1) < height) && ((node.gridX - 1) >= 0)){
			neighbours.add(map[node.gridY][node.gridX]);
		}
		
		//east
		if((node.gridX + 1) < width){
			neighbours.add(map[node.gridY][node.gridX+1]);
		}
		
		//west
		if((node.gridX - 1) >= 0){
			neighbours.add(map[node.gridY][node.gridX+1]);
		}
		
		
		return neighbours;
	}

	private int getDistance(Node nodeA, Node nodeB) {
		// helper method. Provided for your convenience.
		int dstX = Math.abs(nodeA.gridX - nodeB.gridX);
		int dstY = Math.abs(nodeA.gridY - nodeB.gridY);
		if (dstX > dstY)
			return 14 * dstY + 10 * (dstX - dstY);
		return 14 * dstX + 10 * (dstY - dstX);
	}

	public void findPath(Node startNode, Node targetNode)
			throws HeapFullException, HeapEmptyException {
		Heap<Node> openSet = new Heap<Node>(width * height); // this where we make use of our heaps
		// The rest of your implementation goes here.
		// This method implements A-star path search algorithm.
		// The pseudocode is provided in the appropriate web links.
		// Make sure to use the helper method getNeighbours
		
		startNode.hCost = getDistance(startNode, targetNode);
		startNode.gCost = 0;
		openSet.add(startNode);
	}

	public ArrayList<Node> retracePath(Node startNode, Node endNode) {
		Node currentNode = endNode;
	    ArrayList<Node> path = new ArrayList<Node>();
		while (currentNode != startNode && currentNode != null) {
			currentNode.inPath = true;
			path.add(currentNode);
			currentNode = currentNode.parent;
		}
		return path;
	}

	public void move(String direction) {
		// Direction may be: N,S,W,E,NE,NW,SE,SW
		// move the boat 1 cell in the required direction
	}
	
	public Node getTreasure(int range) {
		return boat;
		// range is the range of the sonar
		// if the distance of the treasure from the boat is less or equal that the sonar range,
		// return the treasure node. Otherwise return null.
	}

}
