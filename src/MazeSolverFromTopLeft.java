import java.awt.Color;
import becker.robots.City;
import becker.robots.Direction;

public class MazeSolverFromTopLeft extends MazeSolverAbstract{
	
	
	public double probabilityOfTimeMovedWestBot2 = 0; // the accessor variables should be public, while the instance variables representing the count of each total time moved in a direction should be private
	public double probabilityOfTimeMovedEastBot2 = 0;
	public double probabilityOfTimeMovedNorthBot2 = 0;
	public double probabilityOfTimeMovedSouthBot2 = 0;
	
	private int numTimesMovedWest = 0;
	private int numTimesMovedEast = 0;
	private int numTimesMovedNorth = 0;
	private int numTimesMovedSouth = 0;
	
	
	
	public MazeSolverFromTopLeft(City city, int i, int j, Direction south, int k) {
		// TODO Auto-generated constructor stub
		super(city, i, j, south, k);
	}
	
	
	/**
	 * 
	 * solveMaze is a method which is called for the purposes of solving the maze based on turning left as much as possible whenever it can through going along the left side of the entire maze so that it can find a potential exit at the opposite side from its initial starting location
	 */
	
	public void solveMaze() { // tells the robot to solve the maze
		
		int mazeAttempts = 0; // represents the amount of times the robot has tried to solve the maze
		
		this.setSpeed(10); // increases default speed
		this.move(); // offset created for the purpose of ensuring that it does not initially spawn and start solving the maze from within a wall
		this.turnRight();
		this.move();
		if (canPickThing() == true) { // checks to see if it should try and pick up a thing on its initial location, so that the robot can properly progress and navigate throughout the maze
			this.pickThing();
		}
		this.move();
		this.putThing();
		do { // should continue attempting to solve the maze
			
			if (thingNotInFront() == false){ // in front of wall ahead immediately
				turnLeft();
				this.move();
		//		System.out.println("Moved North");
				numTimesMovedNorth += 1;
				if (thingNotInFront() == false){ // scenario when wall adjacent to robot on left is blocked
					turnAround();
					this.move();
		//			System.out.println("Moved North");
					numTimesMovedNorth += 1;
					if (thingNotInFront() == false){
						turnRight();
						this.move();
		//				System.out.println("Moved South");
						numTimesMovedSouth += 1;
					} else {
						if (thingNotInFront() == false) {
							this.move();
		//					System.out.println("Moved West");
							numTimesMovedWest += 1;
						}
					}
				} else {
					if (thingNotInFront() == false) {
						this.move();
		//				System.out.println("Moved North");
						numTimesMovedNorth += 1;
					}
				}
			} else {
				turnLeft();
				this.move();
				if (thingNotInFront() == true) {
					this.move();
		//			System.out.println("Moved East");
					numTimesMovedEast += 1;
				} else {
					turnRight();
					this.move();
		//			System.out.println("Moved West");
					numTimesMovedWest += 1;
				}
			}
			
			mazeAttempts += 1; // a counter is created for the sake of checking to see if the robot eventually gets trapped within the maze as if it is still cycling and proceeding through a looped puzzle that has it not progressing the maze, that will demonstrate that the robot was not able to find an exit from its initial starting location and it must try and rely on the other robot for it solve the maze via using a different pathway
//			System.out.println(mazeAttempts); // this can be used if you want to see how long it takes the robot to complete the maze
		} while (this.getStreet() < 20 && (mazeAttempts < 150));
		
		
		double numDirectionsPossible = (numTimesMovedNorth + numTimesMovedSouth + numTimesMovedEast + numTimesMovedWest);
		
		probabilityOfTimeMovedWestBot2 = numTimesMovedWest / numDirectionsPossible * 100;
		probabilityOfTimeMovedEastBot2 = numTimesMovedEast / numDirectionsPossible * 100;
		probabilityOfTimeMovedNorthBot2 = numTimesMovedNorth / numDirectionsPossible * 100;
		probabilityOfTimeMovedSouthBot2 = numTimesMovedSouth / numDirectionsPossible * 100;

		
		this.setColor(Color.PINK); // changes colour once it has picked up and finished the maze at the bottom of it from the top
	}
	
	
	/**
	 * 
	 * thingNotInFront is a method which is called to check to see if there is a thing in front of the robot from its current location, used to determine which direction the robot can navigate through, as it should not be able to move through walls within the maze
	 * @return the true or false value which showcases the fact that there can either be or not be a thing in front of the robot from its initial starting location, suggesting that there is a "wall" of a thing in front of it that it should not be able to go through
	 */
	
	private boolean thingNotInFront() { // note that thingNotInFront is the same as frontIsClear
		
		if (this.canPickThing() == true) { // is going to check if it is currently on a thing, if it is not, it should turn around to its current location and progress throughout the code either returning a boolean value to the method solving the maze that there is or is not a thing in front of it
			this.turnAround();
			super.move();
			this.turnAround();
			return false; // there is a thing in front of the robot
		} else {
			return true;
		}
		
	}
	
	// getter methods returning the probability values to the RobotTask class to be used
	public double getEastProbability(){
		return probabilityOfTimeMovedEastBot2;
	}
	
	public double getWestProbability(){
		return probabilityOfTimeMovedWestBot2;
	}
	
	public double getNorthProbability(){
		return probabilityOfTimeMovedNorthBot2;
	}
	
	public double getSouthProbability(){
		return probabilityOfTimeMovedSouthBot2;
	}
	
	
	// these have to be public due to the fact that they are abstract
	public void moveEast(){
		this.move(); // if the robot is already facing east we don't have to check for it since it will have faced this direction by default
		
	}
	
	public void moveWest(){
		this.move();
	}
	
	public void moveNorth(){
		this.move();
	}

	public void moveSouth(){
		this.move();
	}
	
	
}
