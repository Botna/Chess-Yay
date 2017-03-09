package chess2.Classes.Empowered;

import chess2.Classes.Classic.CBishop;
import chess2.Classes.Classic.CKnight;
import chess2.Classes.Classic.CRook;
import chess2.source.GameBoard;
import chess2.source.PieceInf;
 


public class EBishop implements PieceInf
{

	private char team = '-';
	private char moveSet = '-';
	private char contents = '-';
	private boolean hasMoved = false;
	
	public EBishop(char team, char moveSet, char contents, boolean hasMoved)
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
		
		
		//Check to see if adjacent Rook or Knight.
		char team = theGame.getTheGame()[row][col].getContents().getTeam();
		char variant = theGame.getTheGame()[row][col].getContents().getMoveSet();
		char contents = theGame.getTheGame()[row][col].getContents().getContents();
		boolean hasMoved = theGame.getTheGame()[row][col].getContents().getMoved();
		boolean knight = false, rook = false;
		
		if( (row-1) >=0)
		{
			if(theGame.getTheGame()[row-1][col].getContents().getTeam() == team && theGame.getTheGame()[row-1][col].getContents().getContents() == 'k')
			{
				knight = true;
			}
			else if(theGame.getTheGame()[row-1][col].getContents().getTeam() == team && theGame.getTheGame()[row-1][col].getContents().getContents() == 'r')
			{
				rook = true;
			}
		}
		
		if(  (col+1) < 8)
		{
			if(theGame.getTheGame()[row][col+1].getContents().getTeam() == team && theGame.getTheGame()[row][col+1].getContents().getContents() == 'k')
			{
				knight = true;
			}
			else if(theGame.getTheGame()[row][col+1].getContents().getTeam() == team && theGame.getTheGame()[row][col+1].getContents().getContents() == 'r')
			{
				rook = true;
			}
		}
		
		if( (col-1) >=0)
		{
			if(theGame.getTheGame()[row][col-1].getContents().getTeam() == team && theGame.getTheGame()[row][col-1].getContents().getContents() == 'k')
			{
				knight = true;
			}
			else if(theGame.getTheGame()[row][col-1].getContents().getTeam() == team && theGame.getTheGame()[row][col-1].getContents().getContents() == 'r')
			{
				rook = true;
			}
		}
		
		if( (row+1) <8 )
		{
			if(theGame.getTheGame()[row+1][col].getContents().getTeam() == team && theGame.getTheGame()[row+1][col].getContents().getContents() == 'k')
			{
				knight = true;
			}
			else if(theGame.getTheGame()[row+1][col].getContents().getTeam() == team && theGame.getTheGame()[row+1][col].getContents().getContents() == 'r')
			{
				rook = true;
			}
		}
		
		//Create a CBihop and do its highlight.
		PieceInf tempPiece = new CBishop(team, variant, contents, hasMoved);
		tempPiece.highlight(theGame, row, col);
		tempPiece = null;
		if(knight)
		{
			tempPiece = new CKnight(team, variant, contents, hasMoved);
			tempPiece.highlight(theGame, row, col);
			tempPiece = null;
		}
		
		if(rook)
		{
			tempPiece = new CRook(team, variant, contents, hasMoved);
			tempPiece.highlight(theGame, row, col);
			tempPiece = null;
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