package chess2.source;

import java.io.Serializable;

public interface PieceInf extends Serializable
{
	
	public char getTeam();
	
	public char getMoveSet();
	
	public char getContents();
	
	public boolean getMoved();
	
	public GameBoard highlight(GameBoard theGame, int row, int col);
	
	public GameBoard makeMove(GameBoard theGame, int row1, int col1, int row2, int col2);

	public void setHasMoved(boolean b);

	public void setMoveSet(char charAt);
	
	public void setTeam(char team);
	
	public void setContents(char contents);
	
	
	
}