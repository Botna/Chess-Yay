package chess2.Classes.Classic;

import chess2.source.GameBoard;
import chess2.source.PieceInf;



public class CKnight implements PieceInf
{

	private char team = '-';
	private char moveSet = '-';
	private char contents = '-';
	private boolean hasMoved = false;
	private int tier = 0;
	
	public CKnight(char team, char moveSet, char contents, boolean hasMoved, int tier)
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


		//white knight moves
		if (this.team == 'w') {
			for (int a = -2; a < 3; a = a + 4) {
				for (int b = -1; b < 2; b = b + 2) {
					if ((row + a) >= 0 && (row + a) <= 7 && (col + b) >= 0
							&& (col + b) <= 7) {
						if (((theGame.getTheGame())[row + a][col + b]
								.getContents().getTeam() == 'b'
								|| (theGame.getTheGame())[row + a][col + b]
										.getContents().getContents() == '-')

										&& 

										(theGame.getTheGame()[row+a][col+b].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row+a][col+b].getContents().getContents() != 'r')
										&& 
										(theGame.getTheGame()[row+a][col+b].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row+a][col+b].getContents().getContents() != 'q')) {


							(theGame.getTheGame())[row + a][col + b]
									.setHighlight(true);
							(theGame.getTheGame())[row + a][col + b]
									.setNeedsRedraw(true);
						}

					}

					if ((row + b) >= 0 && (row + b) <= 7 && (col + a) >= 0
							&& (col + a) <= 7) {
						if (((theGame.getTheGame())[row + b][col + a]
								.getContents().getTeam() == 'b'
								|| (theGame.getTheGame())[row + b][col + a]
										.getContents().getContents() == '-')

										&& 

										(theGame.getTheGame()[row+b][col+a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row+b][col+a].getContents().getContents() != 'r')
										&&
										(theGame.getTheGame()[row+b][col+a].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row+b][col+a].getContents().getContents() != 'q')) {
							(theGame.getTheGame())[row + b][col + a]
									.setHighlight(true);
							(theGame.getTheGame())[row + b][col + a]
									.setNeedsRedraw(true);
						}

					}

				}

			}
		} 
		//black knight moves
		else {
			for (int a = -2; a < 3; a = a + 4) {
				for (int b = -1; b < 2; b = b + 2) {
					if ((row + a) >= 0 && (row + a) <= 7 && (col + b) >= 0
							&& (col + b) <= 7) {
						if (((theGame.getTheGame())[row + a][col + b]
								.getContents().getTeam() == 'w'
								|| (theGame.getTheGame())[row + a][col + b]
										.getContents().getContents() == '-')

										&& 

										(theGame.getTheGame()[row + a][col + b].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row + a][col + b].getContents().getContents() != 'r')
										&& 
										(theGame.getTheGame()[row + a][col + b].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row + a][col + b].getContents().getContents() != 'q')) {
							(theGame.getTheGame())[row + a][col + b]
									.setHighlight(true);
							(theGame.getTheGame())[row + a][col + b]
									.setNeedsRedraw(true);
						}

					}

					if ((row + b) >= 0 && (row + b) <= 7 && (col + a) >= 0
							&& (col + a) <= 7) {
						if (((theGame.getTheGame())[row + b][col + a]
								.getContents().getTeam() == 'w'
								|| (theGame.getTheGame())[row + b][col + a]
										.getContents().getContents() == '-')

										&& 				
										(theGame.getTheGame()[row + b][col + a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row + b][col + a].getContents().getContents() != 'r')
										&& 
										(theGame.getTheGame()[row + b][col + a].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row + b][col + a].getContents().getContents() != 'q')) {
							(theGame.getTheGame())[row + b][col + a]
									.setHighlight(true);
							(theGame.getTheGame())[row + b][col + a]
									.setNeedsRedraw(true);
						}

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

	@Override
	public void setTier(int tier) {
		this.tier = tier;
		
	}

	@Override
	public int getTier() {
		return this.tier;
	}
	
}