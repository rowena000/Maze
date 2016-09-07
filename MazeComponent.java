import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.JComponent;

/**
 * MazeComponent class
 * A component that displays the maze and path through it if one has been found.
 */
public class MazeComponent extends JComponent {
	private Maze maze;

	private static final int START_X = 10; // where to start drawing maze in
	// frame
	private static final int START_Y = 10;
	private static final int BOX_WIDTH = 20; // width and height of one maze
	// unit
	private static final int BOX_HEIGHT = 20;
	private static final int INSET = 2;
	// how much smaller on each side to make entry/exit inner box

	/**
	 * Constructs the component.
	 * @param maze the maze to display
	 */
	public MazeComponent(Maze maze) {
		this.maze = maze;
	}

	/**
	 * Draws the current state of maze including the path through it if one has
	 * been found.
	 * @param g the graphics context
	 */
	public void paintComponent(Graphics g) {
		drawMaze(g);
		if (maze.getPath().size() > 0) {
			drawPath(g);
		}
	}

	/**
	 * Draw the maze without the path
	 * @param g the graphics context
	 */
	private void drawMaze(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		//draw the maze 
		for (int i = 0; i < maze.numRows(); i++) {
			for (int j = 0; j < maze.numCols(); j++) {
				if (maze.hasWallAt(new MazeCoord(i, j))) {
					g2.setColor(Color.BLACK);
				} else {
					g2.setColor(Color.WHITE);
				}
				int x = START_X + BOX_WIDTH * i;
				int y = START_Y + BOX_HEIGHT * j;
				g2.fill(new Rectangle(y, x, BOX_WIDTH, BOX_HEIGHT));
			}
		}

		//draw the boundary
		g2.setColor(Color.BLACK);
		g2.draw(new Rectangle(START_X, START_Y, BOX_WIDTH * maze.numCols(), BOX_HEIGHT * maze.numRows()));

		//draw the entry block
		int y = START_Y + maze.getEntryLoc().getRow() * BOX_HEIGHT + INSET;
		int x = START_X + maze.getEntryLoc().getCol() * BOX_WIDTH + INSET;
		g2.setColor(Color.YELLOW);
		g2.fill(new Rectangle(x, y, BOX_WIDTH - INSET * 2, BOX_HEIGHT - INSET * 2));

		//draw the exit block
		y = START_Y + maze.getExitLoc().getRow() * BOX_HEIGHT + INSET;
		x = START_X + maze.getExitLoc().getCol() * BOX_WIDTH + INSET;
		g2.setColor(Color.GREEN);
		g2.fill(new Rectangle(x, y, BOX_WIDTH - INSET * 2, BOX_HEIGHT - INSET * 2));
	}

	/**
	 * Draw the path
	 * @param g the graphics context
	 */
	private void drawPath(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		LinkedList<MazeCoord> path = maze.getPath();
		ListIterator<MazeCoord> iter = path.listIterator();
		g2.setColor(Color.BLUE);
		System.out.println("DEBUG: path size: " + path.size());
		MazeCoord start = iter.next();
		while (iter.hasNext()) {
			MazeCoord end = iter.next();
			int x1 = start.getCol() * BOX_WIDTH + START_X + BOX_WIDTH/2;
			int y1 = start.getRow() * BOX_HEIGHT + START_Y + BOX_HEIGHT/2;

			int x2 = end.getCol() * BOX_WIDTH + START_X + BOX_WIDTH/2;
			int y2 = end.getRow() * BOX_HEIGHT + START_Y + BOX_HEIGHT/2;	
			g2.drawLine(x1, y1, x2, y2);

			start = end;
		}
	}
}
