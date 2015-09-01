package chess2.Classes.Animals;

import chess2.Classes.Classic.CBishop;
import chess2.Classes.Classic.CKnight;
import chess2.Classes.Classic.CQueen;
import chess2.Classes.Classic.CRook;
import chess2.source.GameBoard;
import chess2.source.PieceInf;



public class AQueen implements PieceInf
{

	private char team = '-';
	private char moveSet = '-';
	private char contents = '-';
	private boolean hasMoved = false;
	private int tier = 0;

	public AQueen(char team, char moveSet, char contents, boolean hasMoved, int tier)
	{
		this.team = team;
		this.moveSet = moveSet;
		this.contents = contents;
		this.hasMoved = hasMoved;
		this.tier = tier;
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

		char team = theGame.getTheGame()[row][col].getContents().getTeam();
		char variant = theGame.getTheGame()[row][col].getContents().getMoveSet();
		char contents = theGame.getTheGame()[row][col].getContents().getContents();
		boolean hasMoved = theGame.getTheGame()[row][col].getContents().getMoved();
		

		//Create a CKnight and do its highlight.
		PieceInf tempPiece = new CKnight(team, variant, contents, hasMoved,0);
		tempPiece.highlight(theGame, row, col);
		tempPiece = null;
		
		tempPiece = new CRook(team, variant, contents, hasMoved,0);
		tempPiece.highlight(theGame, row, col);
		tempPiece = null;

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

	@Override
	public void setTier(int tier) {
		this.tier = tier;
		
	}

	@Override
	public int getTier() {
		return this.tier;
	}
}