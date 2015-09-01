package chess2.Classes.Reapers;
import chess2.source.GameBoard;
import chess2.source.PieceInf;
 


public class RQueen implements PieceInf
{

	private char team = '-';
	private char moveSet = '-';
	private char contents = '-';
	private boolean hasMoved = false;
	private int tier = 0;
	
	public RQueen(char team, char moveSet, char contents, boolean hasMoved, int tier)
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


        int[][] elephants = {{-1,-1},{-1,-1}};
		for(int a = 0; a<8; a++)
		{
			for(int b = 0; b< 8; b++)
			{
				if(theGame.getTheGame()[row][col].getContents().getTeam() != theGame.getTheGame()[a][b].getContents().getTeam() && theGame.getTheGame()[a][b].getContents().getContents() != 'x')
				{
					if((theGame.getTheGame()[row][col].getContents().getTeam() == 'w' && a != 7) || (theGame.getTheGame()[row][col].getContents().getTeam() == 'b' && a != 0))
					{

                       if((theGame.getTheGame()[a][b].getContents().getMoveSet() != 'N' || theGame.getTheGame()[a][b].getContents().getContents() != 'q')
                               &&
                               (theGame.getTheGame()[a][b].getContents().getMoveSet() != 'R' || theGame.getTheGame()[a][b].getContents().getContents() != 'r'  ) )
                       {

                           if ((theGame.getTheGame()[a][b].getContents().getContents() == 'r')
                                   && (theGame.getTheGame()[a][b].getContents().getMoveSet() == 'A')
                                   && (theGame.getTheGame()[a][b].getContents().getTeam() != theGame.getTheGame()[row][col].getContents().getTeam())) {
                               //we found a elephant.   save the index and figure it out later
                               if (elephants[0][0] == -1) {
                                   //first occurence.
                                   elephants[0][0] = a;
                                   elephants[0][1] = b;
                               } else {
                                   //second occurence.
                                   elephants[1][0] = a;
                                   elephants[1][1] = b;
                               }
                           }
                           else
                           {
                           theGame.getTheGame()[a][b].setHighlight(true);
                           theGame.getTheGame()[a][b].setNeedsRedraw(true);
                           }
                       }
					}
				}
			}
		}
		


        if(elephants[0][0] != -1)
        {
            int rowDiff = Math.abs(row-(elephants[0][0]));
            int colDiff = Math.abs(col-(elephants[0][1]));
            if( rowDiff <3 && colDiff <3)
            {
                theGame.getTheGame()[elephants[0][0]][elephants[0][1]].setHighlight(true);
                theGame.getTheGame()[elephants[0][0]][elephants[0][1]].setNeedsRedraw(true);
            }

            if(elephants[1][0] != -1)
            {
                rowDiff = Math.abs(row-(elephants[1][0]));
                colDiff = Math.abs(col-(elephants[1][1]));
                if( rowDiff <3 && colDiff <3)
                {
                    theGame.getTheGame()[elephants[1][0]][elephants[1][1]].setHighlight(true);
                    theGame.getTheGame()[elephants[1][0]][elephants[1][1]].setNeedsRedraw(true);
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