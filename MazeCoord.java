/**
 * MazeCoord class
 * 
 * Immutable class for storing maze coordinates. 
 */

public class MazeCoord {
   final private int row; // final (non-static) means it can't be updated once         
   final private int col; //      it's initialized

   // create a MazeCoord with the given row and column
   public MazeCoord(int row, int col) {
      this.row = row;
      this.col = col;
   }

   // get the row of this MazeCoord
   public int getRow() { return row; }

   // get the col of this MazeCoord;
   public int getCol() { return col; }
   
   // return true iff these two MazeCoords have the same row and col
   // (second arg is type Object because this override equals in Object class)
   public boolean equals(Object other) {
      
      if (other == null) { return false; }
      
      if (!(other instanceof MazeCoord)) { return false; }
      
      MazeCoord otherCoord = (MazeCoord) other;
      
      return this.row == otherCoord.row && this.col == otherCoord.col;
      
   }

   // return a String representation of the maze coordinate
   public String toString() {
      return "MazeCoord[row=" + row + ",col=" + col + "]";
   }

}
