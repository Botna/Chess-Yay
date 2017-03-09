package chess2.Classes.Empowered;

import chess2.source.GameBoard;
import chess2.source.PieceInf;



public class EQueen implements PieceInf
{

	private char team = '-';
	private char moveSet = '-';
	private char contents = '-';
	private boolean hasMoved = false;
	
	public EQueen(char team, char moveSet, char contents, boolean hasMoved)
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
		
		
		if(this.team == 'w')
		{
			if ((row + 1) >= 0 && (row + 1) <= 7 && (col + 1) >= 0
			&& (col + 1) <= 7) {
		if (((theGame.getTheGame())[row + 1][col + 1].getContents())
				.getContents() == '-') {
			(theGame.getTheGame())[row + 1][col + 1]
					.setHighlight(true);
			(theGame.getTheGame())[row + 1][col + 1]
					.setNeedsRedraw(true);
		} else {
			if (((theGame.getTheGame())[row + 1][col + 1]
					.getContents()).getTeam() == 'b'
					&& 				
					(theGame.getTheGame()[row+1][col+1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row+1][col+1].getContents().getContents() != 'r')
					&& 
					(theGame.getTheGame()[row+1][col+1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row+1][col+1].getContents().getContents() != 'q')

					) {
				(theGame.getTheGame())[row + 1][col + 1]
						.setHighlight(true);
				(theGame.getTheGame())[row + 1][col + 1]
						.setNeedsRedraw(true);

			}

		}

	}

	if ((row + 1) >= 0 && (row + 1) <= 7 && (col - 1) >= 0
			&& (col - 1) <= 7) {
		if (((theGame.getTheGame())[row + 1][col - 1].getContents())
				.getContents() == '-') {
			(theGame.getTheGame())[row + 1][col - 1]
					.setHighlight(true);
			(theGame.getTheGame())[row + 1][col - 1]
					.setNeedsRedraw(true);
		} else {
			if (((theGame.getTheGame())[row + 1][col - 1]
					.getContents()).getTeam() == 'b'
					&& 				
					(theGame.getTheGame()[row+1][col-1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row+1][col-1].getContents().getContents() != 'r')
					&&
					(theGame.getTheGame()[row+1][col-1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row+1][col-1].getContents().getContents() != 'q')
					) {
				(theGame.getTheGame())[row + 1][col - 1]
						.setHighlight(true);
				(theGame.getTheGame())[row + 1][col - 1]
						.setNeedsRedraw(true);

			}

		}

	}

	if ((row - 1) >= 0 && (row - 1) <= 7 && (col + 1) >= 0
			&& (col + 1) <= 7) {
		if (((theGame.getTheGame())[row - 1][col + 1].getContents())
				.getContents() == '-') {
			(theGame.getTheGame())[row - 1][col + 1]
					.setHighlight(true);
			(theGame.getTheGame())[row - 1][col + 1]
					.setNeedsRedraw(true);
		} else {
			if (((theGame.getTheGame())[row - 1][col + 1]
					.getContents()).getTeam() == 'b'
					&& 				
					(theGame.getTheGame()[row-1][col+1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row-1][col+1].getContents().getContents() != 'r')
					&&
					(theGame.getTheGame()[row-1][col+1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row-1][col+1].getContents().getContents() != 'q')
					) {

				(theGame.getTheGame())[row - 1][col + 1]
						.setHighlight(true);
				(theGame.getTheGame())[row - 1][col + 1]
						.setNeedsRedraw(true);

			}

		}

	}

	if ((row - 1) >= 0 && (row - 1) <= 7 && (col - 1) >= 0
			&& (col - 1) <= 7) {
		if (((theGame.getTheGame())[row - 1][col - 1].getContents())
				.getContents() == '-') {
			(theGame.getTheGame())[row - 1][col - 1]
					.setHighlight(true);
			(theGame.getTheGame())[row - 1][col - 1]
					.setNeedsRedraw(true);
		} else {
			if (((theGame.getTheGame())[row - 1][col - 1]
					.getContents()).getTeam() == 'b'
					&& 				
					(theGame.getTheGame()[row-1][col-1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row-1][col-1].getContents().getContents() != 'r')
					&&
					(theGame.getTheGame()[row-1][col-1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row-1][col-1].getContents().getContents() != 'q')
					) {
				(theGame.getTheGame())[row - 1][col - 1]
						.setHighlight(true);
				(theGame.getTheGame())[row - 1][col - 1]
						.setNeedsRedraw(true);

			}

		}

	}

	if ((row + 1) >= 0 && (row + 1) <= 7) {
		if (((theGame.getTheGame())[row + 1][col].getContents())
				.getContents() == '-') {
			(theGame.getTheGame())[row + 1][col].setHighlight(true);
			(theGame.getTheGame())[row + 1][col]
					.setNeedsRedraw(true);
		} else {
			if (((theGame.getTheGame())[row + 1][col].getContents())
					.getTeam() == 'b'
					&& 				
					(theGame.getTheGame()[row+1][col].getContents().getMoveSet() != '2' || theGame.getTheGame()[row+1][col].getContents().getContents() != 'r')
					&&
					(theGame.getTheGame()[row+1][col].getContents().getMoveSet() != '3' || theGame.getTheGame()[row+1][col].getContents().getContents() != 'q')
					) {
				(theGame.getTheGame())[row + 1][col]
						.setHighlight(true);
				(theGame.getTheGame())[row + 1][col]
						.setNeedsRedraw(true);
			}

		}
	}

	if ((row - 1) >= 0 && (row - 1) <= 7) {
		if (((theGame.getTheGame())[row - 1][col].getContents())
				.getContents() == '-') {
			(theGame.getTheGame())[row - 1][col].setHighlight(true);
			(theGame.getTheGame())[row - 1][col]
					.setNeedsRedraw(true);
		} else {
			if (((theGame.getTheGame())[row - 1][col].getContents())
					.getTeam() == 'b'
					&& 				
					(theGame.getTheGame()[row-1][col].getContents().getMoveSet() != '2' || theGame.getTheGame()[row-1][col].getContents().getContents() != 'r')
					&&
					(theGame.getTheGame()[row-1][col].getContents().getMoveSet() != '3' || theGame.getTheGame()[row-1][col].getContents().getContents() != 'q')
					) {
				(theGame.getTheGame())[row - 1][col]
						.setHighlight(true);
				(theGame.getTheGame())[row - 1][col]
						.setNeedsRedraw(true);
			}

		}
	}

	if ((col + 1) >= 0 && (col + 1) <= 7) {
		if (((theGame.getTheGame())[row][col + 1].getContents())
				.getContents() == '-') {
			(theGame.getTheGame())[row][col + 1].setHighlight(true);
			(theGame.getTheGame())[row][col + 1]
					.setNeedsRedraw(true);
		} else {
			if (((theGame.getTheGame())[row][col + 1].getContents())
					.getTeam() == 'b'
					&& 				
					(theGame.getTheGame()[row][col+1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row][col+1].getContents().getContents() != 'r')
					&&
					(theGame.getTheGame()[row][col+1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row][col+1].getContents().getContents() != 'q')
					) {
				(theGame.getTheGame())[row][col + 1]
						.setHighlight(true);
				(theGame.getTheGame())[row][col + 1]
						.setNeedsRedraw(true);
			}

		}

	}

	if ((col - 1) >= 0 && (col - 1) <= 7) {
		if (((theGame.getTheGame())[row][col - 1].getContents())
				.getContents() == '-') {
			(theGame.getTheGame())[row][col - 1].setHighlight(true);
			(theGame.getTheGame())[row][col - 1]
					.setNeedsRedraw(true);
		} else {
			if (((theGame.getTheGame())[row][col - 1].getContents())
					.getTeam() == 'b'
					&& 				
					(theGame.getTheGame()[row][col-1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row][col-1].getContents().getContents() != 'r')
					&&
					(theGame.getTheGame()[row][col-1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row][col-1].getContents().getContents() != 'q')
					) {
				(theGame.getTheGame())[row][col - 1]
						.setHighlight(true);
				(theGame.getTheGame())[row][col - 1]
						.setNeedsRedraw(true);
			}

		}
	}


}
	
	else
	{
		if ((row + 1) >= 0 && (row + 1) <= 7 && (col + 1) >= 0
				&& (col + 1) <= 7) {
			if (((theGame.getTheGame())[row + 1][col + 1].getContents())
					.getContents() == '-') {
				(theGame.getTheGame())[row + 1][col + 1]
						.setHighlight(true);
				(theGame.getTheGame())[row + 1][col + 1]
						.setNeedsRedraw(true);
			} else {
				if (((theGame.getTheGame())[row + 1][col + 1]
						.getContents()).getTeam() == 'w'&& 				
						(theGame.getTheGame()[row+1][col+1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row+1][col+1].getContents().getContents() != 'r')
						&&
						(theGame.getTheGame()[row+1][col+1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row+1][col+1].getContents().getContents() != 'q')
						) {
					(theGame.getTheGame())[row + 1][col + 1]
							.setHighlight(true);
					(theGame.getTheGame())[row + 1][col + 1]
							.setNeedsRedraw(true);

				}

			}

		}

		if ((row + 1) >= 0 && (row + 1) <= 7 && (col - 1) >= 0
				&& (col - 1) <= 7) {
			if (((theGame.getTheGame())[row + 1][col - 1].getContents())
					.getContents() == '-') {
				(theGame.getTheGame())[row + 1][col - 1]
						.setHighlight(true);
				(theGame.getTheGame())[row + 1][col - 1]
						.setNeedsRedraw(true);
			} else {
				if (((theGame.getTheGame())[row + 1][col - 1]
						.getContents()).getTeam() == 'w'&& 				
						(theGame.getTheGame()[row+1][col-1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row+1][col-1].getContents().getContents() != 'r')
						&&
						(theGame.getTheGame()[row+1][col-1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row+1][col-1].getContents().getContents() != 'q')
						) {
					(theGame.getTheGame())[row + 1][col - 1]
							.setHighlight(true);
					(theGame.getTheGame())[row + 1][col - 1]
							.setNeedsRedraw(true);

				}

			}

		}

		if ((row - 1) >= 0 && (row - 1) <= 7 && (col + 1) >= 0
				&& (col + 1) <= 7) {
			if (((theGame.getTheGame())[row - 1][col + 1].getContents())
					.getContents() == '-') {
				(theGame.getTheGame())[row - 1][col + 1]
						.setHighlight(true);
				(theGame.getTheGame())[row - 1][col + 1]
						.setNeedsRedraw(true);
			} else {
				if (((theGame.getTheGame())[row - 1][col + 1]
						.getContents()).getTeam() == 'w'
						&& 				
						(theGame.getTheGame()[row-1][col+1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row-1][col+1].getContents().getContents() != 'r')
						&&
						(theGame.getTheGame()[row-1][col+1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row-1][col+1].getContents().getContents() != 'q')
						) {
					(theGame.getTheGame())[row - 1][col + 1]
							.setHighlight(true);
					(theGame.getTheGame())[row - 1][col + 1]
							.setNeedsRedraw(true);

				}

			}

		}

		if ((row - 1) >= 0 && (row - 1) <= 7 && (col - 1) >= 0
				&& (col - 1) <= 7) {
			if (((theGame.getTheGame())[row - 1][col - 1].getContents())
					.getContents() == '-') {
				(theGame.getTheGame())[row - 1][col - 1]
						.setHighlight(true);
				(theGame.getTheGame())[row - 1][col - 1]
						.setNeedsRedraw(true);
			} else {
				if (((theGame.getTheGame())[row - 1][col - 1]
						.getContents()).getTeam() == 'w'
						&& 				
						(theGame.getTheGame()[row-1][col-1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row-1][col-1].getContents().getContents() != 'r')
						&&
						(theGame.getTheGame()[row-1][col-1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row-1][col-1].getContents().getContents() != 'q')
						) {
					(theGame.getTheGame())[row - 1][col - 1]
							.setHighlight(true);
					(theGame.getTheGame())[row - 1][col - 1]
							.setNeedsRedraw(true);

				}

			}

		}


		if ((row + 1) >= 0 && (row + 1) <= 7) {
			if (((theGame.getTheGame())[row + 1][col].getContents())
					.getContents() == '-') {
				(theGame.getTheGame())[row + 1][col].setHighlight(true);
				(theGame.getTheGame())[row + 1][col]
						.setNeedsRedraw(true);
			} else {
				if (((theGame.getTheGame())[row + 1][col].getContents())
						.getTeam() == 'w'
						&& 				
						(theGame.getTheGame()[row+1][col].getContents().getMoveSet() != '2' || theGame.getTheGame()[row+1][col].getContents().getContents() != 'r')
						&&
						(theGame.getTheGame()[row+1][col].getContents().getMoveSet() != '3' || theGame.getTheGame()[row+1][col].getContents().getContents() != 'q')
						) {
					(theGame.getTheGame())[row + 1][col]
							.setHighlight(true);
					(theGame.getTheGame())[row + 1][col]
							.setNeedsRedraw(true);
				}

			}
		}

		if ((row - 1) >= 0 && (row - 1) <= 7) {
			if (((theGame.getTheGame())[row - 1][col].getContents())
					.getContents() == '-') {
				(theGame.getTheGame())[row - 1][col].setHighlight(true);
				(theGame.getTheGame())[row - 1][col]
						.setNeedsRedraw(true);
			} else {
				if (((theGame.getTheGame())[row - 1][col].getContents())
						.getTeam() == 'w'
						&& 				
						(theGame.getTheGame()[row-1][col].getContents().getMoveSet() != '2' || theGame.getTheGame()[row-1][col].getContents().getContents() != 'r')
						&&
						(theGame.getTheGame()[row-1][col].getContents().getMoveSet() != '3' || theGame.getTheGame()[row-1][col].getContents().getContents() != 'q')
						) {
					(theGame.getTheGame())[row - 1][col]
							.setHighlight(true);
					(theGame.getTheGame())[row - 1][col]
							.setNeedsRedraw(true);
				}

			}
		}

		if ((col + 1) >= 0 && (col + 1) <= 7) {
			if (((theGame.getTheGame())[row][col + 1].getContents())
					.getContents() == '-') {
				(theGame.getTheGame())[row][col + 1].setHighlight(true);
				(theGame.getTheGame())[row][col + 1]
						.setNeedsRedraw(true);
			} else {
				if (((theGame.getTheGame())[row][col + 1].getContents())
						.getTeam() == 'w'
						&& 				
						(theGame.getTheGame()[row][col+1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row][col+1].getContents().getContents() != 'r')
						&&
						(theGame.getTheGame()[row][col+1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row][col+1].getContents().getContents() != 'q')
						) {
					(theGame.getTheGame())[row][col + 1]
							.setHighlight(true);
					(theGame.getTheGame())[row][col + 1]
							.setNeedsRedraw(true);
				}

			}
		}

		if ((col - 1) >= 0 && (col - 1) <= 7) {
			if (((theGame.getTheGame())[row][col - 1].getContents())
					.getContents() == '-') {
				(theGame.getTheGame())[row][col - 1].setHighlight(true);
				(theGame.getTheGame())[row][col - 1]
						.setNeedsRedraw(true);
			} else {
				if (((theGame.getTheGame())[row][col - 1].getContents())
						.getTeam() == 'w'
						&& 				
						(theGame.getTheGame()[row][col-1].getContents().getMoveSet() != '2' || theGame.getTheGame()[row][col-1].getContents().getContents() != 'r')
						&&
						(theGame.getTheGame()[row][col-1].getContents().getMoveSet() != '3' || theGame.getTheGame()[row][col-1].getContents().getContents() != 'q')
						){
					(theGame.getTheGame())[row][col - 1]
							.setHighlight(true);
					(theGame.getTheGame())[row][col - 1]
							.setNeedsRedraw(true);
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