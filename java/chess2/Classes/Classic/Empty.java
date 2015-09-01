package chess2.Classes.Classic;

import chess2.source.GameBoard;
import chess2.source.PieceInf;



@SuppressWarnings("serial")
public class Empty implements PieceInf
{

	private char team = '-';
	private char moveSet = '-';
	private char contents = '-';
	private boolean hasMoved = false;
	
	public Empty(char team, char moveSet, char contents, boolean hasMoved)
	{
		
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

			return theGame;
	}

	
	@Override
	public GameBoard makeMove(GameBoard theGame, int row1, int col1, int row2,
			int col2) {
		 
		return null;
	}

	@Override
	public void setHasMoved(boolean b) {
		
		
	}
	
	@Override
	public void setMoveSet(char charAt) {
		
		
	}

	@Override
	public void setTeam(char team) {
		
		
	}

	@Override
	public void setContents(char contents) {
		this.contents = contents;
		
	}

    @Override
    public void setTier(int tier) {

    }

    @Override
    public int getTier() {
        return 0;
    }
}