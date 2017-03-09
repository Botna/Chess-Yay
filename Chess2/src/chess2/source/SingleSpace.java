package chess2.source;


import java.io.Serializable;

import chess2.Classes.Classic.CPawn;
import chess2.Classes.Classic.Empty;


public class SingleSpace implements Serializable{
	private PieceInf contents;
	private boolean highLight = false;
	private boolean lastMove = false;
	private boolean needsRedraw = true;
	private boolean isSelected = false;

	public SingleSpace() {
		
		this.contents = new Empty('-','-','-',false);
	
	
	}

	public PieceInf getContents() {
		return contents;
	}

	public void setContents(PieceInf contents) {
		this.contents = contents;
	}

	public boolean getHighlight() {
		return highLight;
	}

	public void setHighlight(boolean highLight) {
		this.highLight = highLight;
	}

	public boolean getNeedsRedraw() {
		return needsRedraw;
	}

	public void setNeedsRedraw(boolean needsRedraw) {
		this.needsRedraw = needsRedraw;
	}

	public boolean getLastMove() {
		return lastMove;
	}

	public void setLastMove(boolean lastMove) {
		this.lastMove = lastMove;
	}
	public void setSelected(boolean b)
	{
		isSelected = b;
	}
	public boolean getSelected()
	{
		return isSelected;
	}
	public boolean isEmpty()
	{
		if(contents.getContents() == '-')
		{
			return true;
		}
		else
			return false;
	}
}