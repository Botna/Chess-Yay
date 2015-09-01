package chess2.source;





import java.util.UUID;



public class SimulatedGame extends GameBoard{




    private static final long serialVersionUID = 1509888617043495978L;
    private static final int PAWNTIER = 1;
    private static final int KNIGHTTIER = 2;
    private static final int BISHOPTIER = 2;
    private static final int ROOKTIER = 3;
    private static final int QUEENTIER = 4;
    private static final int KINGTIER = 9;
    boolean justWW;
    //character variables
    private String whiteTeam;
    private String blackTeam;
    private char whiteVar;
    private char blackVar;
    private boolean whiteCheck;
    private boolean blackCheck;
    private int whiteTokens;
    private int blackTokens;
    //history info
    private int numberOfMoves = 0;
    //displayed info
    private PieceInf[] capturedPieces;
    private SingleSpace pieceJustTaken;
    private SingleSpace pieceJustMoved;
    private int insurance;
    private boolean promptDefense = false;
    private boolean promptOffense = false;
    private String winner;
    private boolean playable;
    private int beforeX, beforeY;
    private String whoseTurn;
    private String state;
    private boolean myTurn;
    private char myTeam;
    private String myName;
    private UUID guid;
    private GameContainer myContainer;
    private SingleSpace[][] theGame;
    private SingleSpace displaced,  insinuated;
    private SingleSpace[] WWDisplaced;
    private SingleSpace[] displacedRook;

    private int[][] highlighted;


    public SimulatedGame() {
        super();
    }


    public int simulateClick(int x, int y)
    {
        boolean isHighlighted = false;
        switch(state)
        {

            case "WAITING":
                if (!theGame[y][x].isEmpty())
                {
                    if(theGame[y][x].getContents().getContents() == 'x' && theGame[y][x].getContents().getMoveSet() == 'T')
                    {
                        state = "DISPLAYING";
                        clearHighlight();
                        this.highlight(x, y);
                        theGame[y][x].setSelected(true);
                        beforeX = x;
                        beforeY = y;
                        return 3;
                    }
                    else
                    {


                        state = "DISPLAYING";
                        clearHighlight();
                        //theGame[y][x].getContents().highlight(this, y, x);
                        this.highlight(x, y);
                        theGame[y][x].setSelected(true);
                        beforeX = x;
                        beforeY = y;
                        return 1;
                    }
                }
                break;
            case "DISPLAYING":

                isHighlighted = false;
                for(int i  =0; i < highlighted.length; i++)
                {
                    if(y == highlighted[i][1] && x == highlighted[i][0])
                    {
                        //we found it.  return the 'yes, we can move here'.
                        isHighlighted = true;
                    }

                }

                if( theGame[y][x].getSelected())
                {
                    //toggle the highlights off.
                    clearHighlight();
                    theGame[y][x].setSelected(false);
                    state = "WAITING";
                    x = -1;
                    y = -1;
                    return 1;
                }
                else if (!isHighlighted)
                {
                    if(!theGame[y][x].isEmpty())
                    {
                        clearHighlight();
                        clearSelected();
                        //theGame[y][x].getContents().highlight(this, y, x);
                        this.highlight(x, y);
                        theGame[y][x].setSelected(true);
                        beforeX = x;
                        beforeY = y;
                        if(theGame[y][x].getContents().getContents() == 'x' && theGame[y][x].getContents().getMoveSet() == 'T')
                        {
                            return 3;
                        }
                        return 1;
                    }//else its just a blank space, dont do anything.
                }
                else
                {
                    if (myTurn && isPlayable())
                    {
                        //we will respond to clicks on highlighted squares.  otherwise, we just ignore it and pretend its an accidental click.

                        //check to see if this is one of our saved highlighted spaces and that its actually our piece.


                        if(isHighlighted && theGame[beforeY][beforeX].getContents().getTeam() == myTeam)
                        {
                            //we found it.  return the 'yes, we can move here'.
                            state = "WAITING";
                            return 2;


                        }
                    }
                }
                break;
            case "SECONDWAITING":
                if (!theGame[y][x].isEmpty())
                {
                    if(theGame[y][x].getContents().getContents() == 'x' && theGame[y][x].getContents().getMoveSet() == 'T')
                    {
                        state = "SECONDDISPLAYING";
                        clearHighlight();
                        this.highlight(x, y);
                        theGame[y][x].setSelected(true);
                        beforeX = x;
                        beforeY = y;
                        return 3;
                    }
                    else if(theGame[y][x].getContents().getTeam() != myTeam)
                    {
                        //still want to be able to show enemy moves here, just not
                        //any of my moves other than king moves.
                        state = "SECONDDISPLAYING";
                        clearHighlight();
                        //theGame[y][x].getContents().highlight(this, y, x);
                        this.highlight(x, y);
                        theGame[y][x].setSelected(true);
                        beforeX = x;
                        beforeY = y;
                        return 1;
                    }
                }
                break;
            case "SECONDDISPLAYING":

                isHighlighted = false;
                for(int i  =0; i < highlighted.length; i++)
                {
                    if(y == highlighted[i][1] && x == highlighted[i][0])
                    {
                        //we found it.  return the 'yes, we can move here'.
                        isHighlighted = true;
                    }


                }
                if( theGame[y][x].getSelected())
                {
                    //toggle the highlights off.
                    clearHighlight();
                    theGame[y][x].setSelected(false);
                    state = "SECONDWAITING";
                    x = -1;
                    y = -1;
                    return 1;
                }
                else if (!isHighlighted)
                {
                    if(!theGame[y][x].isEmpty())
                    {
                        if(theGame[y][x].getContents().getContents() == 'x' && theGame[y][x].getContents().getMoveSet() == 'T')
                        {
                            clearHighlight();
                            clearSelected();
                            //theGame[y][x].getContents().highlight(this, y, x);
                            this.highlight(x, y);
                            theGame[y][x].setSelected(true);
                            beforeX = x;
                            beforeY = y;


                            return 3;
                        }
                    }//else its just a blank space, dont do anything.
                }
                else
                {
                    if (myTurn && isPlayable())
                    {
                        //we will respond to clicks on highlighted squares.  otherwise, we just ignore it and pretend its an accidental click.

                        if(isHighlighted && theGame[beforeY][beforeX].getContents().getTeam() == myTeam)
                        {
                            //we found it.  return the 'yes, we can move here'.
                            state = "SECONDWAITING";
                            return 2;
                        }


                    }
                }


                break;

        }
        return 0;
    }


    public void makeMove(int oldX, int oldY, int newX, int newY) throws Exception
    {
        this.promptDefense = false;
        this.promptOffense = false;
        char team = theGame[oldY][oldX].getContents().getTeam();


        //should only be used when we know it wont cause check.
        //we allow the client to determine this, so failures are possible when the
        //client determines incorrectly.

        theGame[oldY][oldX].getContents().highlight(this, oldY, oldX);

        if( newX <0 || newY <0)
        {

            for(int i = -1; i<2; i++) {

                for (int j = -1; j < 2; j++) {

                    if((oldX+j)>=0 && (oldX+j)<8 && (oldY+i)>=0 && (oldY+i)<8 && (j!= 0 || i != 0))
                    {

                        theGame[oldY + i][oldX + j] = new SingleSpace();

                    }
                }
            }
            theGame[oldY][oldX].setLastMove(true);
        }
        else
        {
            //dont capture King moves.

            //This way, this will always have the 'first move' of every persons turn,
            //unless its a king.  We dont care if its king, cause that cant be skirmished against
            //anyway.
            if(theGame[oldY][oldX].getContents().getContents() != 'x')
            {
                pieceJustMoved = theGame[oldY][oldX];
            }

            if(theGame[newY][newX].getHighlight())
            {
                if(theGame[oldY][oldX].getContents().getContents() == 'b' && theGame[oldY][oldX].getContents().getMoveSet() == 'A')
                {
                    if(theGame[newY][newX].getContents().getContents() != '-')
                    {
                        //take the piece.

                        pieceJustTaken = theGame[newY][newX];

                        theGame[newY][newX] = new SingleSpace();
                        theGame[newY][newX].setLastMove(true);
                        theGame[oldY][oldX].setLastMove(true);

                        if(theGame[newY][newX].getContents().getContents() == 'p')
                        {
                            if(theGame[newY][newX].getContents().getTeam() != theGame[oldY][oldX].getContents().getTeam())
                            {
                                //we took an opponents pawn.
                                increaseToken(theGame[oldY][oldX].getContents().getTeam());
                            }
                        }
                    }
                    else
                    {

                        theGame[newY][newX] = theGame[oldY][oldX];
                        theGame[newY][newX].setLastMove(true);
                        theGame[oldY][oldX] = new SingleSpace();
                        theGame[oldY][oldX].setLastMove(true);
                    }
                }
                else if (theGame[oldY][oldX].getContents().getContents() == 'r' && theGame[oldY][oldX].getContents().getMoveSet() == 'A')
                {
                    //find out which direction we are going.
                    if(newY != oldY)
                    {
                        //row movement.
                        if(newY>oldY)
                        {
                            //upMOvement
                            for(int i =1; i<4; i++)
                            {
                                if(oldY+i == newY)
                                {


                                    if(theGame[newY][newX].getContents().getContents() == 'p')
                                    {
                                        if(theGame[newY][newX].getContents().getTeam() != theGame[oldY][oldX].getContents().getTeam())
                                        {
                                            //we took an opponents pawn.
                                            increaseToken(theGame[oldY][oldX].getContents().getTeam());
                                        }
                                    }

                                    if(theGame[newY][newX].getContents().getContents() != '-')
                                    {
                                        if(theGame[newY][newX].getContents().getTeam() != team)
                                        {
                                            promptDefense = true;
                                        }

                                        if(pieceJustTaken == null)
                                            pieceJustTaken = theGame[newY][newX];
                                    }
                                    theGame[newY][newX] = theGame[oldY][oldX];
                                    theGame[oldY][oldX] = new SingleSpace();
                                    theGame[oldY][oldX].setLastMove(true);
                                    theGame[newY][newX].setLastMove(true);
                                    theGame[newY][newX].getContents().setHasMoved(true);

                                    i = 4;



                                }
                                else {

                                    if(theGame[oldY+i][oldX].getContents().getContents() == 'p')
                                    {
                                        if(theGame[oldY+i][oldX].getContents().getTeam() != theGame[oldY][oldX].getContents().getTeam())
                                        {
                                            //we took an opponents pawn.
                                            increaseToken(theGame[oldY][oldX].getContents().getTeam());
                                        }
                                    }
                                    if(theGame[oldY+i][oldX].getContents().getContents() != '-')
                                    {
                                        if(theGame[oldY+i][oldX].getContents().getTeam() != team)
                                        {
                                            promptDefense = true;
                                        }
                                        if(pieceJustTaken == null)
                                            pieceJustTaken = theGame[oldY+i][oldX];
                                    }
                                    theGame[oldY + i][oldX] = new SingleSpace();
                                }
                            }
                        }
                        else {
                            //down movement
                            for(int i =1; i<4; i++)
                            {
                                if(oldY-i == newY)
                                {

                                    if(theGame[newY][newX].getContents().getContents() == 'p')
                                    {
                                        if(theGame[newY][newX].getContents().getTeam() != theGame[oldY][oldX].getContents().getTeam())
                                        {
                                            //we took an opponents pawn.
                                            increaseToken(theGame[oldY][oldX].getContents().getTeam());
                                        }
                                    }


                                    if(theGame[newY][newX].getContents().getContents() != '-')
                                    {
                                        if(theGame[newY][newX].getContents().getTeam() != team)
                                        {
                                            promptDefense = true;
                                        }
                                        if(pieceJustTaken == null)
                                            pieceJustTaken = theGame[newY][newX];
                                    }
                                    theGame[newY][newX] = theGame[oldY][oldX];
                                    theGame[oldY][oldX] = new SingleSpace();
                                    theGame[oldY][oldX].setLastMove(true);
                                    theGame[newY][newX].setLastMove(true);
                                    theGame[newY][newX].getContents().setHasMoved(true);
                                    i = 4;
                                }
                                else {

                                    if(theGame[oldY-i][oldX].getContents().getContents() == 'p')
                                    {
                                        if(theGame[oldY-i][oldX].getContents().getTeam() != theGame[oldY][oldX].getContents().getTeam())
                                        {
                                            //we took an opponents pawn.
                                            increaseToken(theGame[oldY][oldX].getContents().getTeam());
                                        }
                                    }
                                    if(theGame[oldY-i][oldX].getContents().getContents() != '-')
                                    {
                                        if(theGame[oldY-i][oldX].getContents().getTeam() != team)
                                        {
                                            promptDefense = true;
                                        }
                                        if(pieceJustTaken == null)
                                            pieceJustTaken = theGame[oldY-i][oldX];
                                    }
                                    theGame[oldY - i][oldX] = new SingleSpace();
                                }
                            }
                        }
                    }
                    else {
                        //col movement
                        if (newX > oldX) {
                            //right
                            for (int i = 1; i < 4; i++) {
                                if (oldX + i == newX) {

                                    if(theGame[newY][newX].getContents().getContents() == 'p')
                                    {
                                        if(theGame[newY][newX].getContents().getTeam() != theGame[oldY][oldX].getContents().getTeam())
                                        {
                                            //we took an opponents pawn.
                                            increaseToken(theGame[oldY][oldX].getContents().getTeam());
                                        }
                                    }


                                    if(theGame[newY][newX].getContents().getContents() != '-')
                                    {
                                        if(theGame[newY][newX].getContents().getTeam() != team)
                                        {
                                            promptDefense = true;
                                        }
                                        if(pieceJustTaken == null)
                                            pieceJustTaken = theGame[newY][newX];
                                    }
                                    theGame[newY][newX] = theGame[oldY][oldX];
                                    theGame[oldY][oldX] = new SingleSpace();
                                    theGame[oldY][oldX].setLastMove(true);
                                    theGame[newY][newX].setLastMove(true);
                                    theGame[newY][newX].getContents().setHasMoved(true);
                                    i = 4;
                                } else {

                                    if(theGame[oldY][oldX+i].getContents().getContents() == 'p')
                                    {
                                        if(theGame[oldY][oldX+i].getContents().getTeam() != theGame[oldY][oldX].getContents().getTeam())
                                        {
                                            //we took an opponents pawn.
                                            increaseToken(theGame[oldY][oldX].getContents().getTeam());
                                        }
                                    }
                                    if(theGame[oldY][oldX+i].getContents().getContents() != '-')
                                    {
                                        if(theGame[oldY][oldX+i].getContents().getTeam() != team)
                                        {
                                            promptDefense = true;
                                        }
                                        if(pieceJustTaken == null)
                                            pieceJustTaken = theGame[oldY][oldX+i];
                                    }
                                    theGame[oldY][oldX+i] = new SingleSpace();
                                }
                            }
                        }
                        else
                        {
                            for(int i =1; i<4; i++)
                            {
                                if(oldX-i == newX)
                                {

                                    if(theGame[newY][newX].getContents().getContents() == 'p')
                                    {
                                        if(theGame[newY][newX].getContents().getTeam() != theGame[oldY][oldX].getContents().getTeam())
                                        {
                                            //we took an opponents pawn.
                                            increaseToken(theGame[oldY][oldX].getContents().getTeam());
                                        }
                                    }


                                    if(theGame[newY][newX].getContents().getContents() != '-')
                                    {
                                        if(theGame[newY][newX].getContents().getTeam() != team)
                                        {
                                            promptDefense = true;
                                        }
                                        if(pieceJustTaken == null)
                                            pieceJustTaken = theGame[newY][newX];
                                    }
                                    theGame[newY][newX] = theGame[oldY][oldX];
                                    theGame[oldY][oldX] = new SingleSpace();
                                    theGame[oldY][oldX].setLastMove(true);
                                    theGame[newY][newX].setLastMove(true);
                                    theGame[newY][newX].getContents().setHasMoved(true);
                                    i = 4;
                                }
                                else {

                                    if(theGame[oldY][oldX-i].getContents().getContents() == 'p')
                                    {
                                        if(theGame[oldY][oldX-i].getContents().getTeam() != theGame[oldY][oldX].getContents().getTeam())
                                        {
                                            //we took an opponents pawn.
                                            increaseToken(theGame[oldY][oldX].getContents().getTeam());
                                        }
                                    }

                                    if(theGame[oldY][oldX-i].getContents().getContents() != '-')
                                    {
                                        if(theGame[oldY][oldX-i].getContents().getTeam() != team)
                                        {
                                            promptDefense = true;
                                        }
                                        if(pieceJustTaken == null)
                                            pieceJustTaken = theGame[oldY][oldX-i];
                                    }
                                    theGame[oldY][oldX-i] = new SingleSpace();
                                }
                            }
                        }


                    }
                }
                else
                {
                    //go ahead and make the move if its anything but animals bishop/rook.
                    //those require special logic
                    SingleSpace temp = new SingleSpace();
                    temp.setLastMove(true);
                    theGame[oldY][oldX].getContents().setHasMoved(true);
                    theGame[oldY][oldX].setLastMove(true);

                    if(theGame[newY][newX].getContents().getContents() == 'p')
                    {
                        if(theGame[newY][newX].getContents().getTeam() != theGame[oldY][oldX].getContents().getTeam())
                        {
                            //we took an opponents pawn.
                            increaseToken(theGame[oldY][oldX].getContents().getTeam());
                        }
                    }

                    if(theGame[newY][newX].getContents().getContents() != '-')
                    {
                        pieceJustTaken = theGame[newY][newX];
                    }
                    theGame[newY][newX] = theGame[oldY][oldX];
                    theGame[oldY][oldX] = null;
                    theGame[oldY][oldX] = temp;
                    clearHighlight();
                }
            }
            else if(Math.abs(newX-oldX) == 2 && theGame[oldY][oldX].getContents().getContents() == 'x' && theGame[oldY][oldX].getContents().getMoveSet() == 'C')
            {
                //this might be a castle which wont be highlighted in this manner.
                theGame[newY][newX] = theGame[oldY][oldX];
                theGame[oldY][oldX] = new SingleSpace();
                theGame[oldY][oldX].setLastMove(true);
                theGame[newY][newX].setLastMove(true);
                theGame[newY][newX].getContents().setHasMoved(true);
                if(newX<3)
                {
                    theGame[newY][newX+1] = theGame[newY][0];
                    theGame[newY][newX+1].getContents().setHasMoved(true);
                    theGame[newY][0] = new SingleSpace();
                }
                else
                {
                    theGame[newY][newX-1] = theGame[newY][7];
                    theGame[newY][newX-1].getContents().setHasMoved(true);
                    theGame[newY][7] = new SingleSpace();
                }


            }
            else

                throw new Exception("Cant move to a space that isnt highlighted");
        }
    }


}