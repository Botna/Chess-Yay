package chess2.Classes.Reapers;
import chess2.source.GameBoard;
import chess2.source.PieceInf;
 


public class RQueen implements PieceInf
{

	private char team = '-';
	private char moveSet = '-';
	private char contents = '-';
	private boolean hasMoved = false;
	
	public RQueen(char team, char moveSet, char contents, boolean hasMoved)
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
		 
		for(int a = 0; a<8; a++)
		{
			for(int b = 0; b< 8; b++)
			{
				if(theGame.getTheGame()[row][col].getContents().getTeam() != theGame.getTheGame()[a][b].getContents().getTeam() && theGame.getTheGame()[a][b].getContents().getContents() != 'x')
				{
					if((theGame.getTheGame()[row][col].getContents().getTeam() == 'w' && a != 7) || (theGame.getTheGame()[row][col].getContents().getTeam() == 'b' && a != 0))
					{
						theGame.getTheGame()[a][b].setHighlight(true);
						theGame.getTheGame()[a][b].setNeedsRedraw(true);
					}
				}
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