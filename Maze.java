import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;


/**
   Maze class

   Stores information about a maze and can find a path through the maze
   (if there is one).
   
   Assumptions about structure of the maze, as given in mazeData, startLoc, and endLoc
   (parameters to constructor), and the path:
     -- no outer walls given in mazeData -- search assumes there is a virtual 
        border around the maze (i.e., the maze path can't go outside of the maze
        boundaries)
     -- start location for a path is maze coordinate startLoc
     -- exit location is maze coordinate exitLoc
     -- mazeData input is a 2D array of booleans, where true means there is a wall
        at that location, and false means there isn't (see public FREE / WALL 
        constants below) 
     -- in mazeData the first index indicates the row. e.g., mazeData[row][col]
     -- only travel in 4 compass directions (no diagonal paths)
     -- can't travel through walls
 */

public class Maze {
   
   public static final boolean FREE = false;
   public static final boolean WALL = true;
   private static final int NUM_DIRECTIONS = 4;
   //MazeCoord changes in four compass directions
   private static final MazeCoord[] directions = {new MazeCoord(-1, 0), new MazeCoord(1, 0),
                                                new MazeCoord(0, -1), new MazeCoord(0, 1)};
   
   /*Representation invariant: 
    --mazeData is a 2D array with first index indicating the and second index indicating the column.
    --maze.length = maze.numRows(), maze[0].length = maze.numCols().
    --value of mazeData indicates whether the coordination is wall or free.
    --for any mazeData[i][j], the value is unchangeable
    */
   private boolean[][] mazeData;
   private MazeCoord entryLoc;
   private MazeCoord exitLoc;
   private LinkedList<MazeCoord> path;
   private boolean searched;
  

   /**
      Constructs a maze.
      @param mazeData the maze to search.  See general Maze comments for what
      goes in this array.
      @param startLoc the location in maze to start the search (not necessarily on an edge)
      @param endLoc the "exit" location of the maze (not necessarily on an edge)
      PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
         and 0 <= endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length

    */
   public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord endLoc)
   {
       copyMazeData(mazeData);
       entryLoc = startLoc;
       exitLoc = endLoc;
       path = new LinkedList<MazeCoord>();
       searched = false;
   }

   /**
    * A helper function to copy values from parameter to mazeData, making mazeData unchangeable from outside.
    * @param mazeData mazeData
    */
   private void copyMazeData(boolean[][] mazeData) {
       this.mazeData = new boolean[mazeData.length][mazeData[0].length];
       for (int i = 0; i < mazeData.length; i++) {
           this.mazeData[i] = mazeData[i].clone();
       }
   }

   /**
   Returns the number of rows in the maze
   @return number of rows
   */
   public int numRows() {
      return mazeData.length; 
   }

   
   /**
   Returns the number of columns in the maze
   @return number of columns
   */   
   public int numCols() {
      return mazeData[0].length;  
   } 
 
   
   /**
      Returns true iff there is a wall at this location
      @param loc the location in maze coordinates
      @return whether there is a wall here
      PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
   */
   public boolean hasWallAt(MazeCoord loc) {
      return mazeData[loc.getRow()][loc.getCol()];  
   }
   

   /**
      Returns the entry location of this maze.
    */
   public MazeCoord getEntryLoc() {
      return entryLoc;  
   }
   
   
   /**
   Returns the exit location of this maze.
   */
   public MazeCoord getExitLoc() {
      return exitLoc;  
   }

   
   /**
      Returns the path through the maze. First element is starting location, and
      last element is exit location.  If there was not path, or if this is called
      before search, returns empty list.

      @return the maze path
    */
   public LinkedList<MazeCoord> getPath() {

      return (LinkedList<MazeCoord>)path.clone();   

   }


   /**
      Find a path through the maze if there is one.  Client can access the
      path found via getPath method.
      @return whether path was found.
    */
   public boolean search()  {  
      
       if (!searched) {
           boolean[][] visit = new boolean[numRows()][numCols()];

           for (int i = 0; i < numRows(); i++) {
               for (int j = 0; j < numCols(); j++) {
                   visit[i][j] = false;
               }
           }

           boolean hasPath = searchHelper(visit, entryLoc.getRow(), entryLoc.getCol());
           searched = true;
           return hasPath;
       } else {
           if (path.size() > 0) {
               return true;
           } else {
               return false;
           }
       }
   }
   
   /**
    * Helper function for search()
    * @param visit whether the position is visited
    * @param row row of the coordinate
    * @param col column of the coordinate
    * @return whether there is a path
    */
   private boolean searchHelper(boolean[][] visit, int row, int col) {
       if (row >= numRows() ||  row < 0 || col >= numCols() || col < 0) {
           return false;
       }
       if (mazeData[row][col] == WALL) {
           return false;
       }
       
       if (visit[row][col]) {
           return false;
       }
       
       if (row == exitLoc.getRow() && col == exitLoc.getCol()) {
           path.add(exitLoc);
           return true;
       }
       
       visit[row][col] = true;
       boolean hasPath = false;
       for (int i = 0; i < NUM_DIRECTIONS; i++) {
           MazeCoord direction = directions[i];
           hasPath = searchHelper(visit, row + direction.getRow(), col + direction.getCol());
           if (hasPath) {
               path.addFirst(new MazeCoord(row, col)); 
               break;
           }
       }
       visit[row][col] = false;
           
       return hasPath;
   }

}
