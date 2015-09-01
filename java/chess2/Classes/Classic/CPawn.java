package chess2.Classes.Classic;


import chess2.source.GameBoard;
import chess2.source.PieceInf;


public class CPawn implements PieceInf
{

	private char team = '-';
	private char moveSet = '-';
	private char contents = '-';
	private boolean hasMoved = false;
	private int tier = 0;
	
	public CPawn(char team, char moveSet, char contents, boolean hasMoved, int tier)
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
	
		
		if (this.team == 'w') 
		{
			// double move1
			if (this.hasMoved == false) 
			{
				if (theGame.getTheGame()[row + 2][col].getContents()
						.getContents() == '-'
						&& theGame.getTheGame()[row + 1][col]
								.getContents().getContents() == '-') 
				{
					(theGame.getTheGame())[row + 2][col].setHighlight(true);
					(theGame.getTheGame())[row + 2][col]
							.setNeedsRedraw(true);
				}
			}
			if (row != 7) 
			{
				if (((theGame.getTheGame())[row + 1][col].getContents())
						.getContents() == '-') {
					(theGame.getTheGame())[row + 1][col].setHighlight(true);
					(theGame.getTheGame())[row + 1][col]
							.setNeedsRedraw(true);
				}

				// enpassant
				if (row == 4) {
					if (col > 0 && col < 7) {
						// black pawn to my right
						if (((theGame.getTheGame())[row][col + 1]
								.getContents()).getContents() == 'p'
								&& ((theGame.getTheGame())[row][col + 1]
										.getContents()).getTeam() == 'b') {
							// diagonal right is empty
							if (((theGame.getTheGame())[row + 1][col + 1]
									.getContents()).getContents() == '-') {

								// checks if a pawn double jumped to escape
								// capture
								if ((theGame.getTheGame())[row + 2][col + 1]
										.getLastMove()
										&& (theGame.getTheGame())[row][col + 1]
												.getLastMove()) {
									(theGame.getTheGame())[row + 1][col + 1]
											.setHighlight(true);
									(theGame.getTheGame())[row + 1][col + 1]
											.setNeedsRedraw(true);
								}
							}
						}
						// black pawn to my left
						if (((theGame.getTheGame())[row][col - 1]
								.getContents()).getContents() == 'p'
								&& ((theGame.getTheGame())[row][col - 1]
										.getContents()).getTeam() == 'b') {
							// diagonal right is empty
							if (((theGame.getTheGame())[row + 1][col - 1]
									.getContents()).getContents() == '-') {

								// checks if a pawn double jumped to escape
								// capture
								if ((theGame.getTheGame())[row + 2][col - 1]
										.getLastMove()
										&& (theGame.getTheGame())[row][col - 1]
												.getLastMove()) {
									(theGame.getTheGame())[row + 1][col - 1]
											.setHighlight(true);
									(theGame.getTheGame())[row + 1][col - 1]
											.setNeedsRedraw(true);
								}
							}
						}
					} else if (col == 0) {
						// black pawn to my right
						if (((theGame.getTheGame())[row][col + 1]
								.getContents()).getContents() == 'p'
								&& ((theGame.getTheGame())[row][col + 1]
										.getContents()).getTeam() == 'b') {
							// diagonal right is empty
							if (((theGame.getTheGame())[row + 1][col + 1]
									.getContents()).getContents() == '-') {

								// checks if a pawn double jumped to escape
								// capture
								if ((theGame.getTheGame())[row + 2][col + 1]
										.getLastMove()
										&& (theGame.getTheGame())[row][col + 1]
												.getLastMove()) {
									(theGame.getTheGame())[row + 1][col + 1]
											.setHighlight(true);
									(theGame.getTheGame())[row + 1][col + 1]
											.setNeedsRedraw(true);
								}
							}
						}
					} else if (col == 7) {
						// black pawn to my left
						if (((theGame.getTheGame())[row][col - 1]
								.getContents()).getContents() == 'p'
								&& ((theGame.getTheGame())[row][col - 1]
										.getContents()).getTeam() == 'b') {
							// diagonal right is empty
							if (((theGame.getTheGame())[row + 1][col - 1]
									.getContents()).getContents() == '-') {

								// checks if a pawn double jumped to escape
								// capture
								if ((theGame.getTheGame())[row + 2][col - 1]
										.getLastMove()
										&& (theGame.getTheGame())[row][col - 1]
												.getLastMove()) {
									(theGame.getTheGame())[row + 1][col - 1]
											.setHighlight(true);
									(theGame.getTheGame())[row + 1][col - 1]
											.setNeedsRedraw(true);
								}
							}
						}
					}
				}
				// move+capture
				if (col >= 1 && col <= 6) {
					if (((theGame.getTheGame())[row + 1][col + 1]
							.getContents()).getTeam() == 'b'&&

							(theGame.getTheGame()[row+1][col+1].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row+1][col+1].getContents().getContents() != 'r')
							&& 
							(theGame.getTheGame()[row+1][col+1].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row+1][col+1].getContents().getContents() != 'q')) {
						(theGame.getTheGame())[row + 1][col + 1]
								.setHighlight(true);
						(theGame.getTheGame())[row + 1][col + 1]
								.setNeedsRedraw(true);
					}
					if (((theGame.getTheGame())[row + 1][col - 1]
							.getContents()).getTeam() == 'b' &&

							(theGame.getTheGame()[row+1][col-1].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row+1][col-1].getContents().getContents() != 'r')
							&& 
							(theGame.getTheGame()[row+1][col-1].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row+1][col-1].getContents().getContents() != 'q')) {

						(theGame.getTheGame())[row + 1][col - 1]
								.setHighlight(true);
						(theGame.getTheGame())[row + 1][col - 1]
								.setNeedsRedraw(true);
					}

				} else if (col == 0) {
					if (((theGame.getTheGame())[row + 1][col + 1]
							.getContents()).getTeam() == 'b'
							&& 

							(theGame.getTheGame()[row+1][col+1].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row+1][col+1].getContents().getContents() != 'r')
							&& 
							(theGame.getTheGame()[row+1][col+1].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row+1][col+1].getContents().getContents() != 'q')) {
						(theGame.getTheGame())[row + 1][col + 1]
								.setHighlight(true);
						(theGame.getTheGame())[row + 1][col + 1]
								.setNeedsRedraw(true);
					}
				} else {
					if (((theGame.getTheGame())[row + 1][col - 1]
							.getContents()).getTeam() == 'b'
							&& 
							(theGame.getTheGame()[row+1][col-1].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row+1][col-1].getContents().getContents() != 'r')
							&& 
							(theGame.getTheGame()[row+1][col-1].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row+1][col-1].getContents().getContents() != 'q')) {
						(theGame.getTheGame())[row + 1][col - 1]
								.setHighlight(true);
						(theGame.getTheGame())[row + 1][col - 1]
								.setNeedsRedraw(true);
					}
				}
			}
		}
		else
		{
		//Start of BLACK portion
			// double move1
			if (hasMoved == false) {
				if (((theGame.getTheGame())[row - 2][col].getContents())
						.getContents() == '-'
						&& ((theGame.getTheGame())[row - 1][col]
								.getContents()).getContents() == '-') {
					(theGame.getTheGame())[row - 2][col].setHighlight(true);
					(theGame.getTheGame())[row - 2][col]
							.setNeedsRedraw(true);
				}
			}
		// standard move
		if (row != 0) {
			if (((theGame.getTheGame())[row - 1][col].getContents())
					.getContents() == '-') {
				(theGame.getTheGame())[row - 1][col].setHighlight(true);
				(theGame.getTheGame())[row - 1][col]
						.setNeedsRedraw(true);
			}
			// enpassant
			if (row == 3) {
				if (col > 0 && col < 7) {
					// white pawn to my right
					if (((theGame.getTheGame())[row][col + 1]
							.getContents()).getContents() == 'p'
							&& ((theGame.getTheGame())[row][col + 1]
									.getContents()).getTeam() == 'w') {
						// diagonal right is empty
						if (((theGame.getTheGame())[row - 1][col + 1]
								.getContents()).getContents() == '-') {
							// checks if a pawn double jumped to escape
							// capture
							if ((theGame.getTheGame())[row - 2][col + 1]
									.getLastMove()
									&& (theGame.getTheGame())[row][col + 1]
											.getLastMove()) {
								(theGame.getTheGame())[row - 1][col + 1]
										.setHighlight(true);
								(theGame.getTheGame())[row - 1][col + 1]
										.setNeedsRedraw(true);
							}
						}
					}
					// white pawn to my left
					if (((theGame.getTheGame())[row][col - 1]
							.getContents()).getContents() == 'p'
							&& ((theGame.getTheGame())[row][col - 1]
									.getContents()).getTeam() == 'w') {
						// diagonal right is empty
						if (((theGame.getTheGame())[row - 1][col - 1]
								.getContents()).getContents() == '-') {
							// checks if a pawn double jumped to escape
							// capture
							if ((theGame.getTheGame())[row - 2][col - 1]
									.getLastMove()
									&& (theGame.getTheGame())[row][col - 1]
											.getLastMove()) {
								(theGame.getTheGame())[row - 1][col - 1]
										.setHighlight(true);
								(theGame.getTheGame())[row - 1][col - 1]
										.setNeedsRedraw(true);
							}
						}
					}
				} else if (col == 0) {
					// black pawn to my right
					if (((theGame.getTheGame())[row][col + 1]
							.getContents()).getContents() == 'p'
							&& ((theGame.getTheGame())[row][col + 1]
									.getContents()).getTeam() == 'w') {
						// diagonal right is empty
						if (((theGame.getTheGame())[row - 1][col + 1]
								.getContents()).getContents() == '-') {
							// checks if a pawn double jumped to escape
							// capture
							if ((theGame.getTheGame())[row - 2][col + 1]
									.getLastMove()
									&& (theGame.getTheGame())[row][col + 1]
											.getLastMove()) {
								(theGame.getTheGame())[row - 1][col + 1]
										.setHighlight(true);
								(theGame.getTheGame())[row - 1][col + 1]
										.setNeedsRedraw(true);
							}
						}
					}
				} else if (col == 7) {
					// white pawn to my left
					if (((theGame.getTheGame())[row][col - 1]
							.getContents()).getContents() == 'p'
							&& ((theGame.getTheGame())[row][col - 1]
									.getContents()).getTeam() == 'w') {
						// diagonal right is empty
						if (((theGame.getTheGame())[row - 1][col - 1]
								.getContents()).getContents() == '-') {
							// checks if a pawn double jumped to escape
							// capture
							if ((theGame.getTheGame())[row - 2][col - 1]
									.getLastMove()
									&& (theGame.getTheGame())[row][col - 1]
											.getLastMove()) {
								(theGame.getTheGame())[row - 1][col - 1]
										.setHighlight(true);
								(theGame.getTheGame())[row - 1][col - 1]
										.setNeedsRedraw(true);
							}
						}
					}
				}
			}

			// move+capture
			if (col >= 1 && col <= 6) {
				if (((theGame.getTheGame())[row - 1][col + 1]
						.getContents()).getTeam() == 'w'
						&& 

						(theGame.getTheGame()[row-1][col+1].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row-1][col+1].getContents().getContents() != 'r')
						&& 
						(theGame.getTheGame()[row-1][col+1].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row-1][col+1].getContents().getContents() != 'q')) {
					(theGame.getTheGame())[row - 1][col + 1]
							.setHighlight(true);
					(theGame.getTheGame())[row - 1][col + 1]
							.setNeedsRedraw(true);
				}
				if (((theGame.getTheGame())[row - 1][col - 1]
						.getContents()).getTeam() == 'w'
						&& 

						(theGame.getTheGame()[row-1][col-1].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row-1][col-1].getContents().getContents() != 'r')
						&& 
						(theGame.getTheGame()[row-1][col-1].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row-1][col-1].getContents().getContents() != 'q')) {
					(theGame.getTheGame())[row - 1][col - 1]
							.setHighlight(true);
					(theGame.getTheGame())[row - 1][col - 1]
							.setNeedsRedraw(true);
				}

			} else if (col == 0) {
				if (((theGame.getTheGame())[row - 1][col + 1]
						.getContents()).getTeam() == 'w'
						&& 

						(theGame.getTheGame()[row-1][col+1].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row-1][col+1].getContents().getContents() != 'r')
						&& 
						(theGame.getTheGame()[row-1][col+1].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row-1][col+1].getContents().getContents() != 'q')) {
					(theGame.getTheGame())[row - 1][col + 1]
							.setHighlight(true);
					(theGame.getTheGame())[row - 1][col + 1]
							.setNeedsRedraw(true);
				}
			} else {
				if (((theGame.getTheGame())[row - 1][col - 1]
						.getContents()).getTeam() == 'w'
						&& 

						(theGame.getTheGame()[row-1][col-1].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row-1][col-1].getContents().getContents() != 'r')
						&& 
						(theGame.getTheGame()[row-1][col-1].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row-1][col-1].getContents().getContents() != 'q')) {
					(theGame.getTheGame())[row - 1][col - 1]
							.setHighlight(true);
					(theGame.getTheGame())[row - 1][col - 1]
							.setNeedsRedraw(true);
				}
			}

		}
	
		}
		
		return theGame;
	}

	@Override
	public GameBoard makeMove(GameBoard theGame, int row1, int col1, int row2,  int col2) {

		
		
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