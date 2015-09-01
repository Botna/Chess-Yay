package chess2.Classes.Classic;
import chess2.source.GameBoard;
import chess2.source.PieceInf;



public class CRook implements PieceInf
{

    private char team = '-';
    private char moveSet = '-';
    private char contents = '-';
    private boolean hasMoved = false;
    private int tier = 0;

    public CRook(char team, char moveSet, char contents, boolean hasMoved, int tier)
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
        //white rook moves
        if (((theGame.getTheGame())[row][col].getContents()).getTeam() == 'w') {
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
                                .getContents()).getTeam() == 'b'
                                &&
                                (theGame.getTheGame()[row+a][col].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row+a][col].getContents().getContents() != 'r')
                                &&
                                (theGame.getTheGame()[row+a][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row+a][col].getContents().getContents() != 'q')) {


                            if(theGame.getTheGame()[row+a][col].getContents().getMoveSet() == 'A' && theGame.getTheGame()[row+a][col].getContents().getContents() == 'r')
                            {
                                //we found a elephant.   save the index and figure it out later
                                if(elephants[0][0] == -1)
                                {
                                    //first occurence.
                                    elephants[0][0] = row+a;
                                    elephants[0][1] = col;
                                }
                                else
                                {
                                    //second occurence.
                                    elephants[1][0] = row+a;
                                    elephants[1][1] = col;
                                }
                            }
                            else {
                                (theGame.getTheGame())[row + a][col]
                                        .setHighlight(true);
                                (theGame.getTheGame())[row + a][col]
                                        .setNeedsRedraw(true);
                            }
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
                                .getContents()).getTeam() == 'b'
                                &&
                                (theGame.getTheGame()[row-a][col].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row-a][col].getContents().getContents() != 'r')
                                &&
                                (theGame.getTheGame()[row-a][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row-a][col].getContents().getContents() != 'q')) {

                            if(theGame.getTheGame()[row-a][col].getContents().getMoveSet() == 'A' && theGame.getTheGame()[row-a][col].getContents().getContents() == 'r')
                            {
                                //we found a elephant.   save the index and figure it out later
                                if(elephants[0][0] == -1)
                                {
                                    //first occurence.
                                    elephants[0][0] = row-a;
                                    elephants[0][1] = col;
                                }
                                else
                                {
                                    //second occurence.
                                    elephants[1][0] = row-a;
                                    elephants[1][1] = col;
                                }
                            }
                            else {
                                (theGame.getTheGame())[row - a][col]
                                        .setHighlight(true);
                                (theGame.getTheGame())[row - a][col]
                                        .setNeedsRedraw(true);

                            }
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
                                .getContents()).getTeam() == 'b'
                                &&
                                (theGame.getTheGame()[row][col+a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row][col+a].getContents().getContents() != 'r')
                                &&
                                (theGame.getTheGame()[row][col+a].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row][col+a].getContents().getContents() != 'q')) {

                            if(theGame.getTheGame()[row][col+a].getContents().getMoveSet() == 'A' && theGame.getTheGame()[row][col+a].getContents().getContents() == 'r')
                            {
                                //we found a elephant.   save the index and figure it out later
                                if(elephants[0][0] == -1)
                                {
                                    //first occurence.
                                    elephants[0][0] = row;
                                    elephants[0][1] = col+a;
                                }
                                else
                                {
                                    //second occurence.
                                    elephants[1][0] = row;
                                    elephants[1][1] = col+a;
                                }
                            }
                            else {
                                (theGame.getTheGame())[row][col + a]
                                        .setHighlight(true);
                                (theGame.getTheGame())[row][col + a]
                                        .setNeedsRedraw(true);
                            }
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
                                .getContents()).getTeam() == 'b'
                                &&
                                (theGame.getTheGame()[row][col-a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row][col-a].getContents().getContents() != 'r')
                                &&
                                (theGame.getTheGame()[row][col-a].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row][col-a].getContents().getContents() != 'q')) {

                            if(theGame.getTheGame()[row][col-a].getContents().getMoveSet() == 'A' && theGame.getTheGame()[row][col-a].getContents().getContents() == 'r')
                            {
                                //we found a elephant.   save the index and figure it out later
                                if(elephants[0][0] == -1)
                                {
                                    //first occurence.
                                    elephants[0][0] = row;
                                    elephants[0][1] = col-a;
                                }
                                else
                                {
                                    //second occurence.
                                    elephants[1][0] = row;
                                    elephants[1][1] = col-a;
                                }
                            }
                            else {
                                (theGame.getTheGame())[row][col - a]
                                        .setHighlight(true);
                                (theGame.getTheGame())[row][col - a]
                                        .setNeedsRedraw(true);
                            }
                        }
                        a = 99;
                    }
                }

            }
        }
        //black rook moves
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
                                .getContents()).getTeam() == 'w'
                                &&
                                (theGame.getTheGame()[row+a][col].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row+a][col].getContents().getContents() != 'r')
                                &&
                                (theGame.getTheGame()[row+a][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row+a][col].getContents().getContents() != 'q')) {

                            if (theGame.getTheGame()[row+a][col].getContents().getMoveSet() == 'A' && theGame.getTheGame()[row+a][col].getContents().getContents() == 'r') {
                                //we found a elephant.   save the index and figure it out later
                                if (elephants[0][0] == -1) {
                                    //first occurence.
                                    elephants[0][0] = row+a;
                                    elephants[0][1] = col;
                                } else {
                                    //second occurence.
                                    elephants[1][0] = row+a;
                                    elephants[1][1] = col;
                                }
                            }
                            else
                            {

                                (theGame.getTheGame())[row + a][col]
                                        .setHighlight(true);
                                (theGame.getTheGame())[row + a][col]
                                        .setNeedsRedraw(true);
                            }
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
                                .getContents()).getTeam() == 'w'
                                &&
                                (theGame.getTheGame()[row-a][col].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row-a][col].getContents().getContents() != 'r')
                                &&
                                (theGame.getTheGame()[row-a][col].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row-a][col].getContents().getContents() != 'q')) {

                            if(theGame.getTheGame()[row-a][col].getContents().getMoveSet() == 'A' && theGame.getTheGame()[row-a][col].getContents().getContents() == 'r')
                            {
                                //we found a elephant.   save the index and figure it out later
                                if(elephants[0][0] == -1)
                                {
                                    //first occurence.
                                    elephants[0][0] = row-a;
                                    elephants[0][1] = col;
                                }
                                else
                                {
                                    //second occurence.
                                    elephants[1][0] = row-a;
                                    elephants[1][1] = col;
                                }
                            }
                            else {
                                (theGame.getTheGame())[row - a][col]
                                        .setHighlight(true);
                                (theGame.getTheGame())[row - a][col]
                                        .setNeedsRedraw(true);
                            }
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
                                .getContents()).getTeam() == 'w'
                                &&
                                (theGame.getTheGame()[row][col+a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row][col+a].getContents().getContents() != 'r')
                                &&
                                (theGame.getTheGame()[row][col+a].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row][col+a].getContents().getContents() != 'q')) {

                            if(theGame.getTheGame()[row][col+a].getContents().getMoveSet() == 'A' && theGame.getTheGame()[row][col+a].getContents().getContents() == 'r')
                            {
                                //we found a elephant.   save the index and figure it out later
                                if(elephants[0][0] == -1)
                                {
                                    //first occurence.
                                    elephants[0][0] = row;
                                    elephants[0][1] = col+a;
                                }
                                else
                                {
                                    //second occurence.
                                    elephants[1][0] = row;
                                    elephants[1][1] = col+a;
                                }
                            }
                            else {
                                (theGame.getTheGame())[row][col + a]
                                        .setHighlight(true);
                                (theGame.getTheGame())[row][col + a]
                                        .setNeedsRedraw(true);
                            }
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
                                .getContents()).getTeam() == 'w'
                                &&
                                (theGame.getTheGame()[row][col-a].getContents().getMoveSet() != 'R' || theGame.getTheGame()[row][col-a].getContents().getContents() != 'r')
                                &&
                                (theGame.getTheGame()[row][col-a].getContents().getMoveSet() != 'N' || theGame.getTheGame()[row][col-a].getContents().getContents() != 'q')) {

                            if(theGame.getTheGame()[row][col-a].getContents().getMoveSet() == 'A' && theGame.getTheGame()[row][col-a].getContents().getContents() == 'r')
                            {
                                //we found a elephant.   save the index and figure it out later
                                if(elephants[0][0] == -1)
                                {
                                    //first occurence.
                                    elephants[0][0] = row;
                                    elephants[0][1] = col-a;
                                }
                                else
                                {
                                    //second occurence.
                                    elephants[1][0] = row;
                                    elephants[1][1] = col-a;
                                }
                            }
                            else {
                                (theGame.getTheGame())[row][col - a]
                                        .setHighlight(true);
                                (theGame.getTheGame())[row][col - a]
                                        .setNeedsRedraw(true);
                            }
                        }
                        a = 99;
                    }
                }

            }
        }

        if(elephants[0][0] != -1)
        {
            int rowDiff = Math.abs(row-(elephants[0][0]));
            int colDiff = Math.abs(col-(elephants[0][1]));
            if( rowDiff <4 && colDiff <4)
            {
                theGame.getTheGame()[elephants[0][0]][elephants[0][1]].setHighlight(true);
                theGame.getTheGame()[elephants[0][0]][elephants[0][1]].setNeedsRedraw(true);
            }

            if(elephants[1][0] != -1)
            {
                rowDiff = Math.abs(row-(elephants[1][0]));
                colDiff = Math.abs(col-(elephants[1][1]));
                if( rowDiff <4 && colDiff <4)
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
        // TODO Auto-generated method stub
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