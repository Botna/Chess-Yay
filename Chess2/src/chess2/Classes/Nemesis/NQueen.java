package chess2.Classes.Nemesis;

import chess2.source.GameBoard;
import chess2.source.PieceInf;
 


public class NQueen implements PieceInf
{

	private char team = '-';
	private char moveSet = '-';
	private char contents = '-';
	private boolean hasMoved = false;
	
	public NQueen(char team, char moveSet, char contents, boolean hasMoved)
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

		//white queen diagonal moves
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


						if (theGame.getTheGame()[row + a][col + a]
								.getContents().getTeam() == 'b' &&  //piece is enemy

								(theGame.getTheGame()[row+a][col+a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row+a][col+a].getContents().getContents() != 'r') && //

								(theGame.getTheGame()[row][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row+a][col+a].getContents().getContents() == 'x')) {
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
								.getContents()).getTeam() == 'b' &&
								(theGame.getTheGame()[row+a][col-a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row+a][col-a].getContents().getContents() != 'r') && 

								(theGame.getTheGame()[row][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row+a][col-a].getContents().getContents() == 'x')) {

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
								.getContents()).getTeam() == 'b' &&

								(theGame.getTheGame()[row-a][col+a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row-a][col+a].getContents().getContents() != 'r') && 

								(theGame.getTheGame()[row][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row-a][col+a].getContents().getContents() == 'x')) {
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
								.getContents()).getTeam() == 'b' &&

								(theGame.getTheGame()[row-a][col-a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row-a][col-a].getContents().getContents() != 'r') && 

								(theGame.getTheGame()[row][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row-a][col-a].getContents().getContents() == 'x')) {
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
		//black queen diagonal moves
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
								.getContents()).getTeam() == 'w' &&

								(theGame.getTheGame()[row+a][col+a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row+a][col+a].getContents().getContents() != 'r') && 

								(theGame.getTheGame()[row][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row+a][col+a].getContents().getContents() == 'x')) {
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
								.getContents()).getTeam() == 'w' &&

								(theGame.getTheGame()[row+a][col-a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row+a][col-a].getContents().getContents() != 'r') && 

								(theGame.getTheGame()[row][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row+a][col-a].getContents().getContents() == 'x')) {
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
								.getContents()).getTeam() == 'w' &&

								(theGame.getTheGame()[row-a][col+a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row-a][col+a].getContents().getContents() != 'r') && 

								(theGame.getTheGame()[row][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row-a][col+a].getContents().getContents() == 'x')) {
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
								.getContents()).getTeam() == 'w' &&

								(theGame.getTheGame()[row-a][col-a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row-a][col-a].getContents().getContents() != 'r') && 

								(theGame.getTheGame()[row][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row-a][col-a].getContents().getContents() == 'x')) {
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
		//white queen orthogonal moves
		if (this.team == 'w') {
			for (int a = 1; a < 8; a++) {
				if ((row + a) >= 0 && (row + a) <= 7) {
					if (((theGame.getTheGame())[row + a][col].getContents())
							.getContents() == '-') {
						(theGame.getTheGame())[row + a][col]
								.setHighlight(true);
						(theGame.getTheGame())[row + a][col]
								.setNeedsRedraw(true);
					} else {
						if (((theGame.getTheGame())[row + a][col]
								.getContents()).getTeam() == 'b' &&

								(theGame.getTheGame()[row+a][col].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row+a][col].getContents().getContents() != 'r') && 

								(theGame.getTheGame()[row][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row+a][col].getContents().getContents() == 'x')) {
							(theGame.getTheGame())[row + a][col]
									.setHighlight(true);
							(theGame.getTheGame())[row + a][col]
									.setNeedsRedraw(true);
						}
						a = 99;
					}
				}

			}

			for (int a = 1; a < 8; a++) {
				if ((row - a) >= 0 && (row - a) <= 7) {
					if (((theGame.getTheGame())[row - a][col].getContents())
							.getContents() == '-') {
						(theGame.getTheGame())[row - a][col]
								.setHighlight(true);
						(theGame.getTheGame())[row - a][col]
								.setNeedsRedraw(true);
					} else {
						if (((theGame.getTheGame())[row - a][col]
								.getContents()).getTeam() == 'b' &&
								(theGame.getTheGame()[row-a][col].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row-a][col].getContents().getContents() != 'r') && 

								(theGame.getTheGame()[row][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row-a][col].getContents().getContents() == 'x')) {
							(theGame.getTheGame())[row - a][col]
									.setHighlight(true);
							(theGame.getTheGame())[row - a][col]
									.setNeedsRedraw(true);
						}
						a = 99;
					}
				}

			}

			for (int a = 1; a < 8; a++) {
				if ((col + a) >= 0 && (col + a) <= 7) {
					if (((theGame.getTheGame())[row][col + a].getContents())
							.getContents() == '-') {
						(theGame.getTheGame())[row][col + a]
								.setHighlight(true);
						(theGame.getTheGame())[row][col + a]
								.setNeedsRedraw(true);
					} else {
						if (((theGame.getTheGame())[row][col + a]
								.getContents()).getTeam() == 'b' &&

								(theGame.getTheGame()[row][col+a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row][col+a].getContents().getContents() != 'r') && 

								(theGame.getTheGame()[row][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row][col+a].getContents().getContents() == 'x')) {
							(theGame.getTheGame())[row][col + a]
									.setHighlight(true);
							(theGame.getTheGame())[row][col + a]
									.setNeedsRedraw(true);
						}
						a = 99;
					}
				}

			}

			for (int a = 1; a < 8; a++) {
				if ((col - a) >= 0 && (col - a) <= 7) {
					if (((theGame.getTheGame())[row][col - a].getContents())
							.getContents() == '-') {
						(theGame.getTheGame())[row][col - a]
								.setHighlight(true);
						(theGame.getTheGame())[row][col - a]
								.setNeedsRedraw(true);
					} else {
						if (((theGame.getTheGame())[row][col - a]
								.getContents()).getTeam() == 'b' &&
								(theGame.getTheGame()[row][col-a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row][col-a].getContents().getContents() != 'r') && 

								(theGame.getTheGame()[row][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row][col-a].getContents().getContents() == 'x')) {
							(theGame.getTheGame())[row][col - a]
									.setHighlight(true);
							(theGame.getTheGame())[row][col - a]
									.setNeedsRedraw(true);
						}
						a = 99;
					}
				}

			}
		}
		//black queen orthogonal moves
		else {
			for (int a = 1; a < 8; a++) {
				if ((row + a) >= 0 && (row + a) <= 7) {
					if (((theGame.getTheGame())[row + a][col].getContents())
							.getContents() == '-') {
						(theGame.getTheGame())[row + a][col]
								.setHighlight(true);
						(theGame.getTheGame())[row + a][col]
								.setNeedsRedraw(true);
					} else {
						if (((theGame.getTheGame())[row + a][col]
								.getContents()).getTeam() == 'w' &&
								(theGame.getTheGame()[row+a][col].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row+a][col].getContents().getContents() != 'r') && 

								(theGame.getTheGame()[row][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row+a][col].getContents().getContents() == 'x')) {
							(theGame.getTheGame())[row + a][col]
									.setHighlight(true);
							(theGame.getTheGame())[row + a][col]
									.setNeedsRedraw(true);
						}
						a = 99;
					}
				}

			}

			for (int a = 1; a < 8; a++) {
				if ((row - a) >= 0 && (row - a) <= 7) {
					if (((theGame.getTheGame())[row - a][col].getContents())
							.getContents() == '-') {
						(theGame.getTheGame())[row - a][col]
								.setHighlight(true);
						(theGame.getTheGame())[row - a][col]
								.setNeedsRedraw(true);
					} else {
						if (((theGame.getTheGame())[row - a][col]
								.getContents()).getTeam() == 'w' &&
								(theGame.getTheGame()[row-a][col].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row-a][col].getContents().getContents() != 'r') && 

								(theGame.getTheGame()[row][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row-a][col].getContents().getContents() == 'x')) {
							(theGame.getTheGame())[row - a][col]
									.setHighlight(true);
							(theGame.getTheGame())[row - a][col]
									.setNeedsRedraw(true);
						}
						a = 99;
					}
				}

			}

			for (int a = 1; a < 8; a++) {
				if ((col + a) >= 0 && (col + a) <= 7) {
					if (((theGame.getTheGame())[row][col + a].getContents())
							.getContents() == '-') {
						(theGame.getTheGame())[row][col + a]
								.setHighlight(true);
						(theGame.getTheGame())[row][col + a]
								.setNeedsRedraw(true);
					} else {
						if (((theGame.getTheGame())[row][col + a]
								.getContents()).getTeam() == 'w' &&
								(theGame.getTheGame()[row][col+a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row][col+a].getContents().getContents() != 'r') && 

								(theGame.getTheGame()[row][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row][col+a].getContents().getContents() == 'x')) {
							(theGame.getTheGame())[row][col + a]
									.setHighlight(true);
							(theGame.getTheGame())[row][col + a]
									.setNeedsRedraw(true);
						}
						a = 99;
					}
				}

			}

			for (int a = 1; a < 8; a++) {
				if ((col - a) >= 0 && (col - a) <= 7) {
					if (((theGame.getTheGame())[row][col - a].getContents())
							.getContents() == '-') {
						(theGame.getTheGame())[row][col - a]
								.setHighlight(true);
						(theGame.getTheGame())[row][col - a]
								.setNeedsRedraw(true);
					} else {
						if (((theGame.getTheGame())[row][col - a]
								.getContents()).getTeam() == 'w' &&
								(theGame.getTheGame()[row][col-a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row][col-a].getContents().getContents() != 'r') && 

								(theGame.getTheGame()[row][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row][col-a].getContents().getContents() == 'x')) {
							(theGame.getTheGame())[row][col - a]
									.setHighlight(true);
							(theGame.getTheGame())[row][col - a]
									.setNeedsRedraw(true);
						}
						a = 99;
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