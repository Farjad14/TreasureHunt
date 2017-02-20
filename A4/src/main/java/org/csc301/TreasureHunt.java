package org.csc301;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public class TreasureHunt {

	private final int DEFAULT_SONARS = 3; // default number of available sonars
	private final int DEFAULT_RANGE = 200; // default range of a sonar
	protected Grid islands; // the world where the action happens!
	protected int height, width, landPercent;
	protected int sonars, range; // user defined number of sonars and range
	protected String state; // state of the game (STARTED, OVER)
	protected ArrayList<Node> path; // the path to the treasure!

	public TreasureHunt() {
		// The default constructor
		islands = new Grid();
		sonars = DEFAULT_SONARS;
		range = DEFAULT_RANGE;
		state = "STARTED";
	}

	public TreasureHunt(int height, int width, int landPercent, int sonars,
			int range) {
		// The constructor thatuses parameters
		this.islands = new Grid(width, height, landPercent);
		this.sonars = sonars;
		this.range = range;
		this.state = "STARTED";
	}

	public void processCommand(String command) throws HeapFullException,
			HeapEmptyException {
		// The allowed commands are: 
		// SONAR to drop the sonar in hope to detect treasure
		// GO direction to move the boat in some direction
		// For example, GO NW means move the boat one cell up left (if the cell is navigable; if not simply ignore the command) 
	if (!(state.equals("OVER"))){
		
		//SONAR command
		if (command.equals("SONAR")){
			//Check if we have a sonar avail
			if(sonars > 0){
				//Find the booty!!
				Node booty = islands.getTreasure(range);
				sonars--;
				
				// find treasure and run a star alg to the booty(treasure)!
				if(booty != null){
					islands.findPath(islands.getBoat(), booty);
					path = islands.retracePath(islands.getBoat(), booty);
					state = "OVER";
				}
			}
		}
	}
	else{
		return;
	}
	
	}
	

	public int pathLength() {
		if (path == null)
			return 0;
		else return path.size();
	}

	public String getMap() {
		return islands.drawMap();
	}

	public void play(String pathName) throws FileNotFoundException,
			HeapFullException, HeapEmptyException {
		// Read a batch of commands from a text file and process them.
		Scanner sc = new Scanner(new File(pathName));
		while (sc.hasNextLine()){
			String cmd = sc.nextLine();
			processCommand(cmd);
		}
		sc.close();
	}

}
