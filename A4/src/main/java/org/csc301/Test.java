package org.csc301;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import junit.framework.Assert;

public class Test {
	
	public final void testTreasureCons11() {
		TreasureHunt game = new TreasureHunt(5,5,50,10,1);
		Assert.assertEquals("state should be started", game.state, "STARTED");
		Assert.assertEquals("should have 10 sonars", game.sonars, 10);
	}
	
	public final void testTreasureCons111() {
		TreasureHunt game = new TreasureHunt(5,5,50,10,1);
		Assert.assertEquals("should have 10 sonars", game.sonars, 10);
	}
	public final void testTreasureCons2() {
		TreasureHunt game = new TreasureHunt(5,5,50,10,1);
		Assert.assertNotNull(game.islands);
	}
	
	public final void testTreasureCons3() {
		TreasureHunt game = new TreasureHunt(5,5,50,10,1);
		Assert.assertEquals("should have 5 high", game.islands.height, 5);
	}
	
	public final void testTreasureCons1() {
		TreasureHunt game = new TreasureHunt(5,5,50,10,1);
		Assert.assertEquals("should have 5 wide", game.islands.width, 5);
	}
	
	public final void testTreasureCons() {
		TreasureHunt game = new TreasureHunt(5,5,50,10,1);
		Assert.assertEquals("should have 50% distribution", game.islands.percent, 50);
	}
	
	public final void testPathLength() {
		TreasureHunt game = new TreasureHunt(5,5,50,10,1);
		game.path = new ArrayList<Node>();
		Assert.assertEquals("should 0 path", game.pathLength(), 0);
	}
	public final void testPlay() throws FileNotFoundException, HeapFullException, HeapEmptyException {
		TreasureHunt game = new TreasureHunt(5,5,50,10,1);
		game.play("game.txt");
		Assert.assertEquals("state should be 0 after done playing", game.state, "OVER");
	}
	
	public final void testProcessCommandN() throws HeapFullException, HeapEmptyException {
		TreasureHunt game = new TreasureHunt(5,5,50,10,1);
		int y = game.islands.getBoat().gridY;
		game.processCommand("GO N");
		Assert.assertEquals("should have decreased boatY by 1", y-1, game.islands.getBoat().gridY);
	}
	
	public final void testMoveN() throws HeapFullException, HeapEmptyException {
		TreasureHunt game = new TreasureHunt(5,5,50,10,1);
		int y = game.islands.getBoat().gridY;
		game.islands.move("N");
		Assert.assertEquals("should have decreased boatY by 1", y-1, game.islands.getBoat().gridY);
	}
	
	public final void testProcessCommandNW() throws HeapFullException, HeapEmptyException {
		TreasureHunt game = new TreasureHunt(5,5,50,10,1);
		int y = game.islands.getBoat().gridY;
		int x = game.islands.getBoat().gridX;
		game.processCommand("GO NW");
		Assert.assertEquals("should have decreased boatX and boatY by 1", y-1, game.islands.getBoat().gridY);
		Assert.assertEquals("should have decreased boatX and boatY by 1", x-1, game.islands.getBoat().gridX);
	}
	
	public final void testMoveNW() throws HeapFullException, HeapEmptyException {
		TreasureHunt game = new TreasureHunt(5,5,50,10,1);
		int y = game.islands.getBoat().gridY;
		int x = game.islands.getBoat().gridX;
		game.islands.move("NW");
		Assert.assertEquals("should have decreased boatX and boatY by 1", y-1, game.islands.getBoat().gridY);
		Assert.assertEquals("should have decreased boatX and boatY by 1", x-1, game.islands.getBoat().gridX);
	}
	
	public final void testProcessCommandS() throws HeapFullException, HeapEmptyException {
		TreasureHunt game = new TreasureHunt(5,5,50,10,1);
		int y = game.islands.getBoat().gridY;
		game.processCommand("GO S");
		Assert.assertEquals("should have increased boatY by 1", y+1, game.islands.getBoat().gridY);
	}
	
	public final void testMoveS() throws HeapFullException, HeapEmptyException {
		TreasureHunt game = new TreasureHunt(5,5,50,10,1);
		int y = game.islands.getBoat().gridY;
		game.islands.move("S");
		Assert.assertEquals("should have increased boatY by 1", y+1, game.islands.getBoat().gridY);
	}
	
	public final void testGridCons() {
		Grid grid = new Grid();
		Assert.assertEquals("should have default width", grid.width, 60);
	}
	
	public final void testGridCons1() {
		Grid grid = new Grid();
		Assert.assertEquals("should have default height", grid.height, 15);
	}
	
	public final void testGridCons2() {
		Grid grid = new Grid();
		Assert.assertEquals("should have default percent", grid.percent, 20);
	}
	
	public final void testGridGetTreasure() {
		Grid grid = new Grid(5,5,0);
		Assert.assertEquals("should be in range of the treasure", grid.treasure, grid.getTreasure(25));
	}
	
	public final void testHeapSortUp() throws HeapFullException {
		Heap heap = new Heap(1);
		Node n = new Node(true, 0,0);
		heap.add(n);
		Assert.assertEquals("heap of size 1 should be sorted already", heap.items[0], n);
	}
	
	public final void testHeapSortDown() throws HeapEmptyException, HeapFullException {
		Heap heap = new Heap(1);
		Node n = new Node(true, 0,0);
		heap.add(n);
		heap.add(n);
		heap.removeFirst();
		Assert.assertEquals("heap of size 1 should be sorted already", heap.items[0], n);
	}
	
	public final void testNodeCompareTo(){
		Node n = new Node(true, 0,0);
		n.gCost = 0;
		n.hCost = 0;
		Node n2 = new Node(true, 0,0);
		n.gCost = 0;
		n.hCost = 0;
		int result = n.compareTo(n2);
		Assert.assertEquals("both nodes should be equal", result, 0);
	}
	
	public final void testNodeEquals(){
		Node n = new Node(true, 0,0);
		n.gridX = 0;
		n.gridY = 0;
		Node n2 = new Node(true, 0,0);
		n.gridX = 0;
		n.gridY = 0;
		boolean result = n.equals(n2);
		Assert.assertEquals("both nodes should be equal", result, true);
	}


}
