package chess2.Classes.Animals;

import chess2.source.GameBoard;
import chess2.source.PieceInf;
import chess2.source.SingleSpace;


public class ARook implements PieceInf
{

	private char team = '-';
	private char moveSet = '-';
	private char contents = '-';
	private boolean hasMoved = false;

	public ARook(char team, char moveSet, char contents, boolean hasMoved)
	{
		this.team = team;
		this.moveSet = moveSet;
		this.contents = contents;
		this.hasMoved = hasMoved;
	}

	@Override
	public char getTeam() {

		return this.team;
	}

	@Override
	public char getMoveSet() {
		return this.moveSet;
	}

	@Override
	public char getContents() {
		return this.contents;
	}

	@Override
	public boolean getMoved() {
		return this.hasMoved;
	}

	@Override
	public GameBoard highlight(GameBoard theGame, int row, int col) {

		int temp = 0;
		boolean rampage = false, ownKing = false;
		char team = theGame.getTheGame()[row][col].getContents().getContents();
		
		//for this piece, its a little tricky.
		
		
		//we need to be able to highlight all spaces that have the potential to be attacked, and allow the
		// make move method to handle the finer points of this piece. 
		
		//so essentially, its just 3 spaces in any direction, and we will worry about what places can actually be moved.
		
		for(int i = 1; i<4; i++)
		{
			
			if(col+i < 8)
			{
				theGame.getTheGame()[row][col+i].setHighlight(true);
			}
			if(col-i >= 0)
			{
				theGame.getTheGame()[row][col-i].setHighlight(true);
			}
			if(row+i < 8)
			{
				theGame.getTheGame()[row+i][col].setHighlight(true);
			}
			if(row-i >=0)
			{
				theGame.getTheGame()[row-i][col].setHighlight(true);
			}
		}


		return theGame;
	}


	@Override
	public GameBoard makeMove(GameBoard theGame, int row1, int col1, int row2,
			int col2) {
		 
		return null;
	}

	@Override
	public void setHasMoved(boolean b) {
		this.hasMoved = b;
		
	}
	@Override
	public void setMoveSet(char charAt) {
		
		this.moveSet	 = charAt;
	}

	@Override
	public void setTeam(char team) {
		this.team = team;
		
	}

	@Override
	public void setContents(char contents) {
		this.contents = contents;
		
	}
	
	
	
}