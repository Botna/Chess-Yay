package chess2.Classes.Classic;

import chess2.source.GameBoard;
import chess2.source.PieceInf;



public class CBishop implements PieceInf
{

	private char team = '-';
	private char moveSet = '-';
	private char contents = '-';
	private boolean hasMoved = false;
	
	public CBishop(char team, char moveSet, char contents, boolean hasMoved)
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

		//white bishop moves
		if (this.team == 'w') {
			for (int a = 1; a < 8; a++) {
				if ((row + a) >= 0 && (row + a) <= 7 && (col + a) >= 0
						&& (col + a) <= 7) {
					if (((theGame.getTheGame())[row + a][col + a]
							.getContents()).getContents() == '-') {
						(theGame.getTheGame())[row + a][col + a]
								.setHighlight(true);
						(theGame.getTheGame())[row + a][col + a]
								.setNeedsRedraw(true);
					} else {
						if (((theGame.getTheGame())[row + a][col + a]
								.getContents()).getTeam() == 'b'
								&& 				
								(theGame.getTheGame()[row+1][col+1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row+1][col+1].getContents().getContents() != 'r')
								&& 
								(theGame.getTheGame()[row+1][col+1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row+1][col+1].getContents().getContents() != 'q')) {
							(theGame.getTheGame())[row + a][col + a]
									.setHighlight(true);
							(theGame.getTheGame())[row + a][col + a]
									.setNeedsRedraw(true);

						}
						a = 99;

					}

				}
			}
			for (int a = 1; a < 8; a++) {
				if ((row + a) >= 0 && (row + a) <= 7 && (col - a) >= 0
						&& (col - a) <= 7) {
					if (((theGame.getTheGame())[row + a][col - a]
							.getContents()).getContents() == '-') {
						(theGame.getTheGame())[row + a][col - a]
								.setHighlight(true);
						(theGame.getTheGame())[row + a][col - a]
								.setNeedsRedraw(true);
					} else {
						if (((theGame.getTheGame())[row + a][col - a]
								.getContents()).getTeam() == 'b'

								&& 				
								(theGame.getTheGame()[row+1][col-1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row+1][col-1].getContents().getContents() != 'r')
								&& 
								(theGame.getTheGame()[row+1][col-1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row+1][col-1].getContents().getContents() != 'q')) {
							(theGame.getTheGame())[row + a][col - a]
									.setHighlight(true);
							(theGame.getTheGame())[row + a][col - a]
									.setNeedsRedraw(true);

						}
						a = 99;

					}

				}
			}
			for (int a = 1; a < 8; a++) {
				if ((row - a) >= 0 && (row - a) <= 7 && (col + a) >= 0
						&& (col + a) <= 7) {
					if (((theGame.getTheGame())[row - a][col + a]
							.getContents()).getContents() == '-') {
						(theGame.getTheGame())[row - a][col + a]
								.setHighlight(true);
						(theGame.getTheGame())[row - a][col + a]
								.setNeedsRedraw(true);
					} else {
						if (((theGame.getTheGame())[row - a][col + a]
								.getContents()).getTeam() == 'b'
								&& 				
								(theGame.getTheGame()[row-1][col+1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row-1][col+1].getContents().getContents() != 'r')
								&& 
								(theGame.getTheGame()[row-1][col+1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row-1][col+1].getContents().getContents() != 'q')) {
							(theGame.getTheGame())[row - a][col + a]
									.setHighlight(true);
							(theGame.getTheGame())[row - a][col + a]
									.setNeedsRedraw(true);

						}
						a = 99;

					}

				}
			}

			for (int a = 1; a < 8; a++) {
				if ((row - a) >= 0 && (row - a) <= 7 && (col - a) >= 0
						&& (col - a) <= 7) {
					if (((theGame.getTheGame())[row - a][col - a]
							.getContents()).getContents() == '-') {
						(theGame.getTheGame())[row - a][col - a]
								.setHighlight(true);
						(theGame.getTheGame())[row - a][col - a]
								.setNeedsRedraw(true);
					} else {
						if (((theGame.getTheGame())[row - a][col - a]
								.getContents()).getTeam() == 'b'
								&& 				
								(theGame.getTheGame()[row-1][col-1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row-1][col-1].getContents().getContents() != 'r')
								&& 
								(theGame.getTheGame()[row-1][col-1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row-1][col-1].getContents().getContents() != 'q')) {
							(theGame.getTheGame())[row - a][col - a]
									.setHighlight(true);
							(theGame.getTheGame())[row - a][col - a]
									.setNeedsRedraw(true);

						}
						a = 99;

					}

				}
			}

		} 

		//black bishop moves
		else {
			for (int a = 1; a < 8; a++) {
				if ((row + a) >= 0 && (row + a) <= 7 && (col + a) >= 0
						&& (col + a) <= 7) {
					if (((theGame.getTheGame())[row + a][col + a]
							.getContents()).getContents() == '-') {
						(theGame.getTheGame())[row + a][col + a]
								.setHighlight(true);
						(theGame.getTheGame())[row + a][col + a]
								.setNeedsRedraw(true);
					} else {
						if (((theGame.getTheGame())[row + a][col + a]
								.getContents()).getTeam() == 'w'
								&& 				
								(theGame.getTheGame()[row+1][col+1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row+1][col+1].getContents().getContents() != 'r')
								&& 
								(theGame.getTheGame()[row+1][col+1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row+1][col+1].getContents().getContents() != 'q')) {
							(theGame.getTheGame())[row + a][col + a]
									.setHighlight(true);
							(theGame.getTheGame())[row + a][col + a]
									.setNeedsRedraw(true);

						}
						a = 99;

					}

				}
			}
			for (int a = 1; a < 8; a++) {
				if ((row + a) >= 0 && (row + a) <= 7 && (col - a) >= 0
						&& (col - a) <= 7) {
					if (((theGame.getTheGame())[row + a][col - a]
							.getContents()).getContents() == '-') {
						(theGame.getTheGame())[row + a][col - a]
								.setHighlight(true);
						(theGame.getTheGame())[row + a][col - a]
								.setNeedsRedraw(true);
					} else {
						if (((theGame.getTheGame())[row + a][col - a]
								.getContents()).getTeam() == 'w'
								&& 				
								(theGame.getTheGame()[row+1][col-1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row+1][col-1].getContents().getContents() != 'r')
								&& 
								(theGame.getTheGame()[row+1][col-1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row+1][col-1].getContents().getContents() != 'q')) {
							(theGame.getTheGame())[row + a][col - a]
									.setHighlight(true);
							(theGame.getTheGame())[row + a][col - a]
									.setNeedsRedraw(true);

						}
						a = 99;

					}

				}
			}
			for (int a = 1; a < 8; a++) {
				if ((row - a) >= 0 && (row - a) <= 7 && (col + a) >= 0
						&& (col + a) <= 7) {
					if (((theGame.getTheGame())[row - a][col + a]
							.getContents()).getContents() == '-') {
						(theGame.getTheGame())[row - a][col + a]
								.setHighlight(true);
						(theGame.getTheGame())[row - a][col + a]
								.setNeedsRedraw(true);
					} else {
						if (((theGame.getTheGame())[row - a][col + a]
								.getContents()).getTeam() == 'w'
								&& 				
								(theGame.getTheGame()[row-1][col+1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row-1][col+1].getContents().getContents() != 'r')
								&& 
								(theGame.getTheGame()[row-1][col+1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row-1][col+1].getContents().getContents() != 'q')) {
							(theGame.getTheGame())[row - a][col + a]
									.setHighlight(true);
							(theGame.getTheGame())[row - a][col + a]
									.setNeedsRedraw(true);

						}
						a = 99;

					}

				}
			}

			for (int a = 1; a < 8; a++) {
				if ((row - a) >= 0 && (row - a) <= 7 && (col - a) >= 0
						&& (col - a) <= 7) {
					if (((theGame.getTheGame())[row - a][col - a]
							.getContents()).getContents() == '-') {
						(theGame.getTheGame())[row - a][col - a]
								.setHighlight(true);
						(theGame.getTheGame())[row - a][col - a]
								.setNeedsRedraw(true);
					} else {
						if (((theGame.getTheGame())[row - a][col - a]
								.getContents()).getTeam() == 'w'
								&& 				
								(theGame.getTheGame()[row-1][col-1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row-1][col-1].getContents().getContents() != 'r')
								&& 
								(theGame.getTheGame()[row-1][col-1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row-1][col-1].getContents().getContents() != 'q')) {
							(theGame.getTheGame())[row - a][col - a]
									.setHighlight(true);
							(theGame.getTheGame())[row - a][col - a]
									.setNeedsRedraw(true);

						}
						a = 99;

					}

				}
			}

		}
		
		
		return null;
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