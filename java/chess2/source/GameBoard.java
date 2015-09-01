package chess2.source;





import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;
import chess2.Classes.Animals.ABishop;
import chess2.Classes.Animals.AKnight;
import chess2.Classes.Animals.AQueen;
import chess2.Classes.Animals.ARook;
import chess2.Classes.Classic.CBishop;
import chess2.Classes.Classic.CKing;
import chess2.Classes.Classic.CKnight;
import chess2.Classes.Classic.CPawn;
import chess2.Classes.Classic.CQueen;
import chess2.Classes.Classic.CRook;
import chess2.Classes.Empowered.EBishop;
import chess2.Classes.Empowered.EKnight;
import chess2.Classes.Empowered.EQueen;
import chess2.Classes.Empowered.ERook;
import chess2.Classes.Nemesis.NPawn;
import chess2.Classes.Nemesis.NQueen;
import chess2.Classes.Reapers.RQueen;
import chess2.Classes.Reapers.RRook;


public class GameBoard implements Serializable{




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




    public GameBoard(GameContainer myGame)
    {
        myContainer = myGame;
        whiteTeam = myGame.getWhiteTeam();
        blackTeam = myGame.getBlackTeam();
        whiteVar = myGame.getWhiteVar();
        whoseTurn = myGame.getTurn();
        guid = myGame.getGuid();
        whiteTokens = 3;
        blackTokens = 3;
        theGame = new SingleSpace[8][8];
        for (int a = 0; a < 8; a++) {
            for (int b = 0; b < 8; b++) {
                theGame[a][b] = new SingleSpace();
            }

        }

        // black back row (a8-h8)
        theGame[7][0].setContents(new CRook('b', '-', 'r', false, ROOKTIER));
        theGame[7][1].setContents(new CKnight('b', '-', 'k', false,KNIGHTTIER));
        theGame[7][2].setContents(new CBishop('b', '-', 'b', false,BISHOPTIER));
        theGame[7][3].setContents(new CQueen('b', '-', 'q', false,QUEENTIER));
        theGame[7][4].setContents(new CKing('b', '-', 'x', false,KINGTIER));
        theGame[7][5].setContents(new CBishop('b', '-', 'b', false,BISHOPTIER));
        theGame[7][6].setContents(new CKnight('b', '-', 'k', false,KNIGHTTIER));
        theGame[7][7].setContents(new CRook('b', '-', 'r', false,ROOKTIER));

        for (int i = 0; i < 8; i++) {
            theGame[6][i].setContents(new CPawn('b', '-', 'p', false,PAWNTIER));
        }

        inflateRows(myGame, 'w');			// white back row (a1-h1)



        this.whiteCheck = false;
        this.blackCheck = false;

        this.whiteVar = myGame.getWhiteVar();

    }

    public GameBoard()
    {}


    private void inflateRows(GameContainer myGame, char team) {


        if( team == 'w')
        {

            theGame[0][0].setContents( createPiece(myGame.getWhiteVar(), 'r', team));
            theGame[0][1].setContents(createPiece(myGame.getWhiteVar(), 'k', team));
            theGame[0][2].setContents(createPiece(myGame.getWhiteVar(), 'b', team));
            if(myGame.getWhiteVar() == 'T')
            {
                theGame[0][3].setContents(createPiece(myGame.getWhiteVar(), 'x', team));
            }
            else
            {
                theGame[0][3].setContents(createPiece(myGame.getWhiteVar(), 'q', team));
            }
            theGame[0][4].setContents(createPiece(myGame.getWhiteVar(), 'x', team));
            theGame[0][5].setContents(createPiece(myGame.getWhiteVar(), 'b', team));
            theGame[0][6].setContents(createPiece(myGame.getWhiteVar(), 'k', team));
            theGame[0][7].setContents(createPiece(myGame.getWhiteVar(), 'r', team));

            // set pawns
            for (int i = 0; i < 8; i++) {
                theGame[1][i].setContents(createPiece(myGame.getWhiteVar(), 'p', team));
            }

        }
        else
        {
            theGame[7][0].setContents( createPiece(myGame.getBlackVar(), 'r', team));
            theGame[7][1].setContents(createPiece(myGame.getBlackVar(), 'k', team));
            theGame[7][2].setContents(createPiece(myGame.getBlackVar(), 'b', team));
            if(myGame.getBlackVar() == 'T')
            {
                theGame[7][3].setContents(createPiece(myGame.getBlackVar(), 'x', team));
            }
            else
            {
                theGame[7][3].setContents(createPiece(myGame.getBlackVar(), 'q', team));
            }
            theGame[7][4].setContents(createPiece(myGame.getBlackVar(), 'x', team));
            theGame[7][5].setContents(createPiece(myGame.getBlackVar(), 'b', team));
            theGame[7][6].setContents(createPiece(myGame.getBlackVar(), 'k', team));
            theGame[7][7].setContents(createPiece(myGame.getBlackVar(), 'r', team));

            // set pawns
            for (int i = 0; i < 8; i++) {
                theGame[6][i].setContents(createPiece(myGame.getBlackVar(), 'p', team));
            }

        }
    }

    private PieceInf createPiece(char variant, char piece, char team)
    {

        PieceInf thePiece = null;
        switch (variant)
        {
            case 'C':
                //classic

                switch (piece)
                {
                    case 'r':
                        thePiece = new CRook(team, variant, piece, false, ROOKTIER);
                        break;
                    case 'k':
                        thePiece = new CKnight(team, variant, piece, false, KNIGHTTIER);
                        break;
                    case 'b':
                        thePiece = new CBishop(team, variant, piece, false,BISHOPTIER);
                        break;
                    case 'q':
                        thePiece = new CQueen(team, variant, piece, false,QUEENTIER);
                        break;
                    case 'x':
                        thePiece = new CKing(team, variant, piece, false,KINGTIER);
                        break;
                    case 'p':
                        thePiece = new CPawn(team, variant, piece, false,PAWNTIER);
                        break;
                }

                break;
            case 'R':
                //reapers
                switch (piece)
                {
                    case 'r':
                        thePiece = new RRook(team, variant, piece, false,ROOKTIER);
                        break;
                    case 'k':
                        thePiece = new CKnight(team, variant, piece, false, KNIGHTTIER);
                        break;
                    case 'b':
                        thePiece = new CBishop(team, variant, piece, false,BISHOPTIER);
                        break;
                    case 'q':
                        thePiece = new RQueen(team, variant, piece, false,QUEENTIER);
                        break;
                    case 'x':
                        thePiece = new CKing(team, variant, piece, false,KINGTIER);
                        break;
                    case 'p':
                        thePiece = new CPawn(team, variant, piece, false,PAWNTIER);
                        break;
                }
                break;

            case 'N':
                //nemesis
                switch (piece)
                {
                    case 'r':
                        thePiece = new CRook(team, variant, piece, false,ROOKTIER);
                        break;
                    case 'k':
                        thePiece = new CKnight(team, variant, piece, false, KNIGHTTIER);
                        break;
                    case 'b':
                        thePiece = new CBishop(team, variant, piece, false,BISHOPTIER);
                        break;
                    case 'q':
                        thePiece = new NQueen(team, variant, piece, false,QUEENTIER);
                        break;
                    case 'x':
                        thePiece = new CKing(team, variant, piece, false,KINGTIER);
                        break;
                    case 'p':
                        thePiece = new NPawn(team, variant, piece, false,PAWNTIER);
                        break;
                }
                break;

            case 'E':
                //empowered
                switch (piece)
                {
                    case 'r':
                        thePiece = new ERook(team, variant, piece, false,ROOKTIER);
                        break;
                    case 'k':
                        thePiece = new EKnight(team, variant, piece, false, KNIGHTTIER);
                        break;
                    case 'b':
                        thePiece = new EBishop(team, variant, piece, false,BISHOPTIER);
                        break;
                    case 'q':
                        thePiece = new EQueen(team, variant, piece, false,QUEENTIER);
                        break;
                    case 'x':
                        thePiece = new CKing(team, variant, piece, false,KINGTIER);
                        break;
                    case 'p':
                        thePiece = new CPawn(team, variant, piece, false,PAWNTIER);
                        break;
                }
                break;

            case 'A':
                //animals
                switch (piece)
                {
                    case 'r':
                        thePiece = new ARook(team, variant, piece, false,ROOKTIER);
                        break;
                    case 'k':
                        thePiece = new AKnight(team, variant, piece, false, KNIGHTTIER);
                        break;
                    case 'b':
                        thePiece = new ABishop(team, variant, piece, false,BISHOPTIER);
                        break;
                    case 'q':
                        thePiece = new AQueen(team, variant, piece, false,QUEENTIER);
                        break;
                    case 'x':
                        thePiece = new CKing(team, variant, piece, false,KINGTIER);
                        break;
                    case 'p':
                        thePiece = new CPawn(team, variant, piece, false,PAWNTIER);
                        break;
                }
                break;
            case 'T':
                //two kings
                switch (piece)
                {
                    case 'r':
                        thePiece = new CRook(team, variant, piece, false,ROOKTIER);
                        break;
                    case 'k':
                        thePiece = new CKnight(team, variant, piece, false, KNIGHTTIER);
                        break;
                    case 'b':
                        thePiece = new CBishop(team, variant, piece, false,BISHOPTIER);
                        break;
                    case 'q':
                        thePiece = new CKing(team, variant, piece, false,KINGTIER);
                        break;
                    case 'x':
                        thePiece = new CKing(team, variant, piece, false,KINGTIER);
                        break;
                    case 'p':
                        thePiece = new CPawn(team, variant, piece, false,PAWNTIER);
                        break;
                }
                break;

        }

        return thePiece;
    }
    public boolean myTurn()
    {
        //needs to be ran after determine turn
        return myTurn;
    }
    public char getWhiteVar()
    {
        return whiteVar;

    }


    public char getMyTeam() {
        return myTeam;
    }

    public int click(int x, int y)
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



    public int simulateClick(int x, int y)
    {
        boolean isHighlighted;
        char team;
        if(whoseTurn == whiteTeam)
        {
            team = 'w';
        }
        else
            team = 'b';
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
                    return 0;
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

                    if (theGame[beforeY][beforeX].getContents().getTeam() == team)
                    {
                        //we will respond to clicks on highlighted squares.  otherwise, we just ignore it and pretend its an accidental click.

                        //check to see if this is one of our saved highlighted spaces and that its actually our piece.


                        if(isHighlighted )
                        {
                            //we found it.  return the 'yes, we can move here'.

                            //if its two kings, we want to show that a second move can take place
                            if(theGame[beforeY][beforeX].getContents().getMoveSet() == 'T')
                            {
                                state = "SECONDWAITING";
                                return 4;
                            }
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
                    else if(theGame[y][x].getContents().getTeam() != team)
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
                    return 0;
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

                    //find out if we are moving a piece that is actually able to be moved.

                    if (theGame[beforeY][beforeX].getContents().getTeam() == team)
                    {
                        //we will respond to clicks on highlighted squares.  otherwise, we just ignore it and pretend its an accidental click.

                        if(isHighlighted)
                        {
                            //we found it.  return the 'yes, we can move here'.
                            state = "WAITING";
                            return 2;
                        }


                    }
                }


                break;

        }
        return 5;
    }

    public int insinuateMove(int x, int y)
    {
        //CODE is returned, which tells us a few things about what this insinuation did for the ChessGameView to
        //make decisions upon


        //Code = 0, regular move was made
        //Code = 1, Enemy piece was taken.

        int code = 0;

        this.clearHighlight();
        //Need to pretend to make the move to display whats gonna happen.



        //see if they tried to castle

        if(theGame[beforeY][beforeX].getContents().getMoveSet() == 'C' && theGame[beforeY][beforeX].getContents().getContents() == 'x' && Math.abs(x-beforeX) == 2)
        {
            displaced = theGame[y][x];
            insinuated = theGame[beforeY][beforeX];
            theGame[beforeY][beforeX] = new SingleSpace();
            theGame[y][x] = insinuated;
            if(x<3)
            {
                //we moved to the 'down'.  Need to pull that rook to our other side.
                theGame[y][x+1] = theGame[y][0];
                //theGame[y][x+1].setLastMove(true);
                theGame[y][0] = new SingleSpace();
                theGame[y][x+1].setSelected(true);
                //theGame[y][0].setLastMove(true);
            }
            else
            {
                //we moved to the 'up'.  Need to pull that rook to our other side.
                theGame[y][x-1] = theGame[y][7];
                theGame[y][x-1].setSelected(true);
                //theGame[y][x-1].setLastMove(true);
                theGame[y][7] = new SingleSpace();
                //theGame[y][7].setLastMove(true);
            }

        }
        else if(theGame[beforeY][beforeX].getContents().getMoveSet() == 'A' && theGame[beforeY][beforeX].getContents().getContents() == 'r')
        {
            //simulate Animal rook move.
            displacedRook = new SingleSpace[4];
            //figure out direction.
            if(x>beforeX)
            {
                for(int i  = 1; (i+beforeX)< x; i++)
                {
                    //grab the pieces in between my current spot and the new spot.
                    if(theGame[beforeY][beforeX].getContents().getTeam() != theGame[beforeY][beforeX+i].getContents().getTeam() && theGame[beforeY][beforeX+i].getContents().getContents() != '-')
                    {
                        //enemy piece taken during rampage
                        code = 1;
                    }
                    displacedRook[i] = theGame[beforeY][beforeX + i];
                    theGame[beforeY][beforeX+i] = new SingleSpace();
                }
                if(theGame[beforeY][beforeX].getContents().getTeam() != theGame[y][x].getContents().getTeam() && theGame[y][x].getContents().getContents() != '-')
                {
                    //enemy piece taken during rampage
                    code = 1;
                }
                displacedRook[0] = theGame[y][x];
                theGame[y][x] = theGame[beforeY][beforeX];
                theGame[beforeY][beforeX] = new SingleSpace();
            }
            else if(x < beforeX)
            {
                for(int i  = 1; (beforeX-i)> x; i++)
                {
                    //grab the pieces in between my current spot and the new spot.
                    if(theGame[beforeY][beforeX].getContents().getTeam() != theGame[beforeY][beforeX-i].getContents().getTeam() && theGame[beforeY][beforeX-i].getContents().getContents() != '-')
                    {
                        //enemy piece taken during rampage
                        code = 1;
                    }
                    displacedRook[i] = theGame[beforeY][beforeX - i];
                    theGame[beforeY][beforeX-i] = new SingleSpace();
                }
                if(theGame[beforeY][beforeX].getContents().getTeam() != theGame[y][x].getContents().getTeam()&& theGame[y][x].getContents().getContents() != '-')
                {
                    //enemy piece taken during rampage
                    code = 1;
                }
                displacedRook[0] = theGame[y][x];
                theGame[y][x] = theGame[beforeY][beforeX];
                theGame[beforeY][beforeX] = new SingleSpace();
            }
            else if(y > beforeY)
            {
                for(int i  = 1;(i+beforeY)< y; i++)
                {
                    //grab the pieces in between my current spot and the new spot.
                    if(theGame[beforeY][beforeX].getContents().getTeam() != theGame[beforeY+i][beforeX].getContents().getTeam() && theGame[beforeY+i][beforeX].getContents().getContents() != '-')
                    {
                        //enemy piece taken during rampage
                        code = 1;
                    }
                    displacedRook[i] = theGame[beforeY+ i][beforeX ];
                    theGame[beforeY+ i][beforeX] = new SingleSpace();
                }
                if(theGame[beforeY][beforeX].getContents().getTeam() != theGame[y][x].getContents().getTeam()&& theGame[y][x].getContents().getContents() != '-')
                {
                    //enemy piece taken during rampage
                    code = 1;
                }
                displacedRook[0] = theGame[y][x];
                theGame[y][x] = theGame[beforeY][beforeX];
                theGame[beforeY][beforeX] = new SingleSpace();
            }
            else if(y < beforeY)
            {
                for(int i  = 1; (beforeY-i)> y; i++)
                {
                    //grab the pieces in between my current spot and the new spot.
                    if(theGame[beforeY][beforeX].getContents().getTeam() != theGame[beforeY-i][beforeX].getContents().getTeam() && theGame[beforeY-i][beforeX].getContents().getContents() != '-')
                    {
                        //enemy piece taken during rampage
                        code = 1;
                    }
                    displacedRook[i] = theGame[beforeY-i][beforeX];
                    theGame[beforeY-i][beforeX] = new SingleSpace();
                }
                if(theGame[beforeY][beforeX].getContents().getTeam() != theGame[y][x].getContents().getTeam()&& theGame[y][x].getContents().getContents() != '-')
                {
                    //enemy piece taken during rampage
                    code = 1;
                }
                displacedRook[0] = theGame[y][x];
                theGame[y][x] = theGame[beforeY][beforeX];
                theGame[beforeY][beforeX] = new SingleSpace();
            }

        }
        else if(theGame[beforeY][beforeX].getContents().getMoveSet() == 'A' && theGame[beforeY][beforeX].getContents().getContents() == 'b')
        {
            //simulate animal bishop move.
            if(theGame[y][x].getContents().getContents() != '-')
            {
                displaced = theGame[y][x];
                theGame[y][x] = new SingleSpace();
                //piece was taken
                code = 1;
            }
            else
            {
                displaced = theGame[y][x];
                insinuated = theGame[beforeY][beforeX];
                theGame[y][x] = theGame[beforeY][beforeX];
                theGame[beforeY][beforeX] = new SingleSpace();
            }
        }


        else{


            //Pawn portion goes here, so we can check for en passant
            if(theGame[beforeY][beforeX].getContents().getContents() == 'p' && x != beforeX)
            {

                if(theGame[beforeY][x].getContents().getContents() == 'p' && theGame[beforeY][x].getLastMove())
                {
                    //heres our en passant.
                    displaced = theGame[beforeY][x];
                    theGame[beforeY][x] = new SingleSpace();
                    theGame[beforeY][x].setLastMove(true);
                    insinuated = theGame[y][x] = theGame[beforeY][beforeX];
                    theGame[beforeY][beforeX] = new SingleSpace();
                    code =1 ;
                }
                else {
                    displaced = theGame[y][x];
                    insinuated = theGame[beforeY][beforeX];
                    theGame[beforeY][beforeX] = new SingleSpace();
                    theGame[y][x] = insinuated;
                    if (displaced.getContents().getTeam() != insinuated.getContents().getTeam() && displaced.getContents().getContents() != '-') {
                        //enemy piece was taken.
                        code = 1;
                    }
                }
            }
            else {


                displaced = theGame[y][x];
                insinuated = theGame[beforeY][beforeX];
                theGame[beforeY][beforeX] = new SingleSpace();
                theGame[y][x] = insinuated;
                if (displaced.getContents().getTeam() != insinuated.getContents().getTeam() && displaced.getContents().getContents() != '-') {
                    //enemy piece was taken.
                    code = 1;
                }


                //check to see if we have a pawn that makes it to the backline.

                if ((insinuated.getContents().getContents() == 'p' && insinuated.getContents().getTeam() == 'w' && y == 7)
                        ||
                        (insinuated.getContents().getContents() == 'p' && insinuated.getContents().getTeam() == 'b' && y == 0)) {
                    code = 9;
                }
            }

        }
        return code;
    }
    public void undoInsinuate(int x, int y)
    {
        this.insurance = -1;
        if(justWW)
        {
            undoWW();
        }
        else if(theGame[y][x].getContents().getMoveSet() == 'A' && theGame[y][x].getContents().getContents() == 'r')
        {



            //figure out direction.
            if(x>beforeX)
            {
                theGame[beforeY][beforeX] = theGame[y][x];
                for(int i  = 1; beforeX+i< x; i++)
                {
                    //grab the pieces in between my current spot and the new spot.
                    theGame[beforeY][beforeX + i] = displacedRook[i];

                }
                theGame[y][x] = displacedRook[0];


            }
            else if(x < beforeX)
            {
                theGame[beforeY][beforeX] = theGame[y][x];
                for(int i  = 1; beforeX-i> x; i++)
                {
                    //grab the pieces in between my current spot and the new spot.
                    theGame[beforeY][beforeX - i] = displacedRook[i];

                }
                theGame[y][x] = displacedRook[0];

            }
            else if(y > beforeY)
            {
                theGame[beforeY][beforeX] = theGame[y][x];
                for(int i  = 1; beforeY+i< y; i++)
                {
                    //grab the pieces in between my current spot and the new spot.
                    theGame[beforeY+ i][beforeX ] =  displacedRook[i];
                }
                theGame[y][x] = displacedRook[0];
            }
            else if(y < beforeY)
            {
                theGame[beforeY][beforeX] = theGame[y][x];
                for(int i  = 1; beforeY-i> y; i++)
                {
                    //grab the pieces in between my current spot and the new spot.
                    theGame[beforeY-i][beforeX] = displacedRook[i];

                }
                theGame[y][x] = displacedRook[0];
            }

        }
        else if(theGame[y][x].getContents().getContents() == '-') //animal bishop attack without moving
        {
            theGame[y][x] = displaced;
        }
        else if(theGame[y][x].getContents().getMoveSet() == 'C' && theGame[y][x].getContents().getContents() == 'x' && Math.abs(x-beforeX) == 2)
        {
            theGame[y][x] = displaced;
            theGame[beforeY][beforeX] = insinuated;

            if(x<3)
            {
                theGame[y][0] = theGame[y][x+1];
                theGame[y][x+1] = new SingleSpace();
                theGame[y][0].setSelected(false);
            }
            else
            {
                theGame[y][7] = theGame[y][x-1];
                theGame[y][x-1] = new SingleSpace();
                theGame[y][7].setSelected(false);
            }
        }
        else
        {
            //figure out if we are attempting to undo an en passant.
            if(displaced.getContents().getContents() == 'p' && theGame[beforeY][x].getLastMove())
            {
                theGame[beforeY][beforeX] = theGame[y][x];
                theGame[y][x] = new SingleSpace();

                theGame[beforeY][x] = displaced;
            }
            else {

                theGame[y][x] = displaced;
                theGame[beforeY][beforeX] = insinuated;
            }
        }
        theGame[beforeY][beforeX].setSelected(false);
        clearHighlight();
    }

    private void undoWW() {

        //undo the whirlwind.




        justWW = false;

        int count = 0;
        int row = 0, col = 0;
        for(int x = 0; x<8; x++)
        {
            for(int y =0; y<8; y++)
            {
                if(theGame[y][x].getSelected())
                {
                    col = x;
                    row = y;
                    x = 8;
                    y = 8;
                }


            }
        }

        for(int i = -1; i<2; i++) {

            for (int j = -1; j < 2; j++) {

                if((col+j)>=0 && (col+j)<8 && (row+i)>=0 && (row+i)<8 && (j!= 0 || i != 0))
                {
                    if(WWDisplaced[count] != null) {
                        theGame[row + i][col + j] = WWDisplaced[count];

                    }
                    count++;
                }
            }
        }


    }

    public int[] getSelectedIndex()
    {
        return new int[]{beforeX,beforeY};
    }
    public void determineTurn()
    {
        //determine if its my turn.


        if(whoseTurn.equals(myName))
        {
            myTurn = true;
        }
        else{
            myTurn = false;
        }


        //find out if game is playable yet.
        if(blackVar < 'A')
        {
            playable = false;
        }
        else{
            playable = true;
        }
        if(winner != null)
        {
            playable = false;
        }
        //also determine what color i am.
        if(myName.equals(whiteTeam))
        {
            myTeam = 'w';
        }
        else
            myTeam = 'b';



    }
    public void clearSelected()
    {
        for(int i = 0; i <8; i++)
        {
            for (int j = 0; j<8; j++)
            {
                if (theGame[i][j].getSelected())
                {
                    theGame[i][j].setSelected(false);
                    theGame[i][j].setNeedsRedraw(true);
                }

            }
        }
    }

    public String getName()
    {
        return myName;
    }

    public void setName(String name)
    {
        myName = name;
    }

    public void clearHighlight()
    {
        for(int i = 0; i <8; i++)
        {
            for (int j = 0; j<8; j++)
            {
                if (theGame[i][j].getHighlight())
                {
                    theGame[i][j].setHighlight(false);
                    theGame[i][j].setNeedsRedraw(true);
                }

            }
        }
    }

    public void clearLastMove()
    {
        for(int i = 0; i <8; i++)
        {
            for (int j = 0; j<8; j++)
            {

                if(theGame[i][j].getLastMove())
                {
                    theGame[i][j].setLastMove(false);
                    theGame[i][j].setNeedsRedraw(true);
                }

            }
        }
    }
    public SingleSpace[][] getTheGame() {
        return this.theGame;
    }


    public String getBlackTeam() {
        return blackTeam;
    }
    public String getWhiteTeam()
    {
        return whiteTeam;
    }


    public char getBlackVar() {
        return blackVar;
    }

    public UUID getGuid()
    {
        return this.guid;
    }


    public void setState(String string) {
        this.state = string;
    }
    public void switchTurn()
    {
        if(whoseTurn.equals(whiteTeam))
        {
            whoseTurn = blackTeam;
        }
        else
            whoseTurn = whiteTeam;



    }

    public void highlight(int x, int y)
    {
        theGame[y][x].getContents().highlight(this, y, x);
        char team = theGame[y][x].getContents().getTeam();
        int row = y, col = x;
        int[][] kings = new int[2][2];
        int numFound = 0;

        //we have all the moves highlighted and the team that we currently care about.
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i<8; i++)
        {

            for(int j = 0; j<8; j++)
            {
                if(theGame[j][i].getContents().getContents() == 'x')
                {
                    if(numFound == 0)
                    {
                        kings[0][0] = i;
                        kings[0][1] = j;
                        numFound++;
                    }
                    else
                    {
                        kings[1][0] = i;
                        kings[1][1] = j;
                    }
                }


                //find the highlighted squares.
                if(theGame[j][i].getHighlight())
                {
                    list.add(i);
                    list.add(j);
                }
            }
        }
        this.clearHighlight();



        //Since castleing is so odd, we will do it here.  Need to see if we are classic and our king has not moved
        //then well check that it can actually castle
        if(this.getTheGame()[row][col].getContents().getMoveSet() == 'C'
                && this.getTheGame()[row][col].getContents().getContents() == 'x'
                && this.getTheGame()[row][col].getContents().getMoved() == false)
        {
            //we are classic, we clicked teh king, and we ahvent moved yet.

            //find out if we are in check or not
            if((myTeam == 'w' && this.whiteCheck == false)
                    ||
                    (myTeam == 'b' && this.blackCheck == false))
            {
                boolean occupied = false;
                //we can attempt to castle.  Lets find our rooks
                if(this.getTheGame()[row][0].getContents().getContents() == 'r'
                        && this.getTheGame()[row][0].getContents().getTeam() == this.getTheGame()[row][col].getContents().getTeam()
                        && this.getTheGame()[row][0].getContents().getMoved() == false)
                {
                    // we can see if there is space and it wont involve check to the <col side.
                    for(int i = 1; i <7 && !occupied; i++)
                    {
                        if(this.getTheGame()[row][i].getContents().getContents() == 'x')
                            break;
                        else if(this.getTheGame()[row][i].getContents().getContents() != '-')
                            occupied = true;
                    }

                    if(!occupied)
                    {
                        //simulate the king movement and see if it results in check
                        if ( this.simulateMove(col,row,col-1, row) && this.simulateMove(col,row,col-2,row) )
                        {
                            //no check found in those 2 spots.  Removal of the rook cant result in check
                            //so we can add this as a square to move!
                            list.add(col-2);
                            list.add(row);
                        }
                    }



                }
                occupied = false;
                if(this.getTheGame()[row][7].getContents().getContents() == 'r'
                        && this.getTheGame()[row][7].getContents().getTeam() == this.getTheGame()[row][col].getContents().getTeam()
                        && this.getTheGame()[row][7].getContents().getMoved() == false)
                {
                    // we can see if there is space and it wont involve check to the >col side.
                    for(int i = 6; i >0 && !occupied; i--)
                    {
                        if(this.getTheGame()[row][i].getContents().getContents() == 'x')
                            break;
                        else if(this.getTheGame()[row][i].getContents().getContents() != '-')
                            occupied = true;
                    }

                    if(!occupied)
                    {
                        if ( this.simulateMove(col,row,col+1, row) && this.simulateMove(col,row,col+2,row) )
                        {
                            //no check found in those 2 spots.  Removal of the rook cant result in check
                            //so we can add this as a square to move!
                            list.add(col+2);
                            list.add(row);
                        }
                    }
                }



            }
        }
        Integer[] array =  list.toArray(new Integer[0]);
        int[][] spaces = new int[array.length/2][2];
        int count = 0;
        for(int i =0; i< array.length; i = i + 2)
        {
            //simulate a move to each space, and see if it results in check
            if ( this.simulateMove(x,y,array[i], array[i+1]) )
            {
                //returned true, it didnt result in check. append to our final 2d array of spaces to highlight.
                spaces[count][0] = array[i];
                spaces[count][1] = array[i+1];
                count++;
            }
        }



        //highlight all the spaces;
        this.clearHighlight();
        highlighted = new int[count][2];
        for(int i = 0; i < count; i++)
        {
            theGame[spaces[i][1]][spaces[i][0]].setHighlight(true);
            //throw it into highlightedSpots to be used for actual move logic.
            highlighted[i][1] = spaces[i][1];
            highlighted[i][0] = spaces[i][0];

        }
    }

    public void makeMove(int oldX, int oldY, int newX, int newY) throws Exception
    {
        //this.promptDefense = false;
        this.promptOffense = false;
        char team = theGame[oldY][oldX].getContents().getTeam();
        if(theGame[oldY][oldX].getContents().getContents() != 'x')
        {


            //if insurance != -1, then well give the defender a chance to
            //initiate a skirmish.

            //BUT, only if they have at least a single token.
            //they would probably never do that, technically they could.
            if(theGame[newY][newX].getContents().getContents() != '-')
            {
                if(theGame[newY][newX].getContents().getTeam() == 'w' && theGame[oldY][oldX].getContents().getTeam() != 'w')
                {
                    //check that W has at least one token
                    if(this.whiteTokens >=1)

                        this.promptDefense = true;

                    else
                        this.promptDefense = false;
                }
                else if(theGame[newY][newX].getContents().getTeam() == 'b' && theGame[oldY][oldX].getContents().getTeam() != 'b')
                {
                    //check that W has at least one token
                    if(this.blackTokens >=1)

                        this.promptDefense = true;

                    else
                        this.promptDefense = false;
                }
            }
            else
                promptDefense = false;

        }


        //should only be used when we know it wont cause check.
        //we allow the client to determine this, so failures are possible when the
        //client determines incorrectly.

        theGame[oldY][oldX].getContents().highlight(this, oldY, oldX);

        
        
        if( newX <0 || newY <0)
        {
        	this.clearLastMove();
            int numPawns = 0;
            char myTeam = theGame[oldY][oldX].getContents().getTeam();
            for(int i = -1; i<2; i++) {

                for (int j = -1; j < 2; j++) {

                    if((oldX+j)>=0 && (oldX+j)<8 && (oldY+i)>=0 && (oldY+i)<8 && (j!= 0 || i != 0))
                    {
                        if(theGame[oldY+i][oldX+j].getContents().getContents() != 'r' || theGame[oldY+i][oldX + j].getContents().getMoveSet() != 'R') {
                            if (theGame[oldY + i][oldX + j].getContents().getTeam() != myTeam && theGame[oldY + i][oldX + j].getContents().getContents() == 'p') {
                                numPawns++;
                            }
                            if (theGame[oldY + i][oldX + j].getContents().getContents() != '-') {
                                //there was a piece here.  Add it to taken pieces.
                                this.addCaptured(theGame[oldY + i][oldX + j].getContents());
                            }
                            theGame[oldY + i][oldX + j] = new SingleSpace();
                        }
                    }
                }
            }
            theGame[oldY][oldX].setLastMove(true);

            if(numPawns >0)
            {
                if(myTeam == 'w')
                {
                    this.setWhiteTokens(this.getWhiteTokens() + numPawns);
                }
                else
                {
                    this.setBlackTokens(this.getBlackTokens() + numPawns);
                }
            }

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
                    	this.clearLastMove();
                        pieceJustTaken = theGame[newY][newX];
                        this.addCaptured(pieceJustTaken.getContents());
                        if(theGame[newY][newX].getContents().getContents() == 'p')
                        {
                            if(theGame[newY][newX].getContents().getTeam() != theGame[oldY][oldX].getContents().getTeam())
                            {
                                //we took an opponents pawn.
                                increaseToken(theGame[oldY][oldX].getContents().getTeam());
                            }
                        }
                        theGame[newY][newX] = new SingleSpace();
                        theGame[newY][newX].setLastMove(true);
                        theGame[oldY][oldX].setLastMove(true);


                    }
                    else
                    {
                    	this.clearLastMove();
                        theGame[newY][newX] = theGame[oldY][oldX];
                        theGame[newY][newX].setLastMove(true);
                        theGame[oldY][oldX] = new SingleSpace();
                        theGame[oldY][oldX].setLastMove(true);
                    }
                }
                else if (theGame[oldY][oldX].getContents().getContents() == 'r' && theGame[oldY][oldX].getContents().getMoveSet() == 'A')
                {
                	this.clearLastMove();
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
                                        this.addCaptured(theGame[newY][newX].getContents());
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
                                        this.addCaptured(theGame[oldY+i][oldX].getContents());
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
                                        this.addCaptured(theGame[newY][newX].getContents());
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
                                        this.addCaptured(theGame[oldY-i][oldX].getContents());
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
                                        this.addCaptured(theGame[newY][newX].getContents());
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
                                        this.addCaptured(theGame[oldY][oldX+i].getContents());
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
                                        this.addCaptured(theGame[newY][newX].getContents());
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
                                        this.addCaptured(theGame[oldY][oldX-i].getContents());
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
                    //Pawn portion goes here, so we can check for en passant
                    if(theGame[oldY][oldX].getContents().getContents() == 'p' && oldX != newX)
                    {

                        if(theGame[oldY][newX].getContents().getContents() == 'p' && theGame[oldY][newX].getLastMove())
                        {
                        	this.clearLastMove();
                            //heres our en passant.
                            pieceJustTaken = theGame[oldY][newX];
                            this.addCaptured(pieceJustTaken.getContents());
                            displaced = theGame[oldY][newX];
                            theGame[oldY][newX] = new SingleSpace();
                            theGame[newY][newX] = theGame[oldY][oldX];
                            theGame[oldY][oldX] = new SingleSpace();
                            theGame[newY][newX].setLastMove(true);
                            theGame[oldY][oldX].setLastMove(true);
                            clearHighlight();
                            
                            //check for defense
                            if(theGame[newY][newX].getContents().getTeam() == 'b')
                            {
                                //check that W has at least one token
                                if(this.whiteTokens >=1)

                                    this.promptDefense = true;
                                
                            }
                            else if(theGame[newY][newX].getContents().getTeam() == 'w' )
                            {
                                //check that W has at least one token
                                if(this.blackTokens >=1)

                                    this.promptDefense = true;
                            }

                        }
                        else {
                        	this.clearLastMove();
                           if(theGame[newY][newX].getContents().getContents() != '-')
                           {
                               pieceJustTaken = theGame[newY][newX];
                               this.addCaptured(pieceJustTaken.getContents());
                               if(theGame[newY][newX].getContents().getContents() == 'p')
                               {
                                   increaseToken(theGame[oldY][oldX].getContents().getTeam());
                               }
                           }
                            SingleSpace temp = new SingleSpace();
                            temp.setLastMove(true);
                            theGame[newY][newX] = theGame[oldY][oldX];
                            theGame[oldY][oldX] = null;
                            theGame[oldY][oldX] = temp;
                            theGame[newY][newX].setLastMove(true);
                            theGame[newY][newX].getContents().setHasMoved(true);
                            clearHighlight();
                        }
                    }
                    else {
                    	
                    	this.clearLastMove();
                        SingleSpace temp = new SingleSpace();
                        temp.setLastMove(true);
                        theGame[oldY][oldX].getContents().setHasMoved(true);
                        theGame[oldY][oldX].setLastMove(true);

                        if (theGame[newY][newX].getContents().getContents() == 'p') {
                            if (theGame[newY][newX].getContents().getTeam() != theGame[oldY][oldX].getContents().getTeam()) {
                                //we took an opponents pawn.
                                increaseToken(theGame[oldY][oldX].getContents().getTeam());
                            }
                        }

                        if (theGame[newY][newX].getContents().getContents() != '-') {
                            pieceJustTaken = theGame[newY][newX];
                            this.addCaptured(pieceJustTaken.getContents());
                        }
                        theGame[newY][newX] = theGame[oldY][oldX];
                        theGame[oldY][oldX] = null;
                        theGame[oldY][oldX] = temp;
                        clearHighlight();
                    }
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

    public boolean inCheck(char team)
    {
        //to clarify, we are seeing if a move puts the team listed in check.  So if 'w' is passed, it will return TRUE if white will be in check after that
        //allowing this to be used for both checking if i just put someone in check, as well as checking if i just entered check mistakenly.
        boolean inCheck = false;
        int[][] king = {{-1,-1} ,{-1,-1}};
        char teamCheck;

        if( team == 'w')
        {
            teamCheck = 'b';
        }
        else
            teamCheck = 'w';
        for(int i = 0; i< 8; i++)
        {
            for(int j = 0; j<8; j++)
            {
                if(theGame[i][j].getContents().getTeam() == teamCheck)
                {
                    theGame[i][j].getContents().highlight(this,i,j);
                }
                else if(theGame[i][j].getContents().getTeam() == team && theGame[i][j].getContents().getContents() == 'x')
                {
                    if(king[0][0] == -1)
                    {
                        king[0][0] = i;
                        king [0][1] = j;
                    }
                    else
                    {
                        king[1][0] = i;
                        king[1][1] = j;
                    }
                }
            }
        }

        //check if either king has been highlighted.
        if(king[1][0]  != -1)
        {
            if(theGame[king[0][0]][king[0][1]].getHighlight() || theGame[king[1][0]][king[1][1]].getHighlight() )
            {
                this.clearHighlight();
                return true;
            }
        }
        else
        {
            if(theGame[king[0][0]][king[0][1]].getHighlight())
            {
                this.clearHighlight();
                return true;
            }
        }
        return false;
    }

    public boolean simulateMove(int x, int y, int a, int b)
    {
        clearHighlight();
        char team = theGame[y][x].getContents().getTeam();
        boolean rampage = false, foundKing = false;
        int xMove = 0, yMove = 0, temp = 0;
        SingleSpace temp1 = null;
        //check if its aRook or aBishop.  If so, special logic is necessary
        if(theGame[y][x].getContents().getContents() == 'b' && theGame[y][x].getContents().getMoveSet() == 'A')
        {

            return simulateABishop(x,y,a,b);
        }
        else if (theGame[y][x].getContents().getContents() == 'r' && theGame[y][x].getContents().getMoveSet() == 'A')
        {

            //this is our AROOK piece.
            //have it do the highlihgt to keep code in a safe place.

            return simulateARook(x,y,a,b);


        }
        else
        {
            //make the move
            temp1 = theGame[b][a];
            theGame[b][a] = theGame[y][x];
            theGame [y][x] = new SingleSpace();

            if(inCheck(team))
            {
                //undo the move and it resulted in check.
                theGame[y][x] = theGame[b][a];
                theGame[b][a] = temp1;
                return false;
            }
            else
            {
                //undo the move anyway.
                theGame[y][x] = theGame[b][a];
                theGame[b][a] = temp1;
                return true;
            }
        }
    }

    public void updateBlackVariant(char var) throws Exception
    {
        this.blackVar = var;
        this.myContainer.setBlackVar(var);
        inflateRows(myContainer, 'b');
    }

    public boolean simulateARook(int x, int y, int a, int b)

    {
        char team = theGame[y][x].getContents().getTeam();
        boolean rampage = false, foundKing = false, foundBlock = false;
        SingleSpace[] rook= new SingleSpace[4];
        if(b-y != 0)
        {
            //we have column movement.  lets see if its positive or negative.
            if( b > y)
            {
                //piece is moving 'up'
                for(int i = 1; i <4; i++ )
                {
                    if(y+i < 8 && y+i <= b)
                    {
                        //count up till we hit our 'b' and see if we are going to rampage.
                        if(theGame[y+i][x].getContents().getContents() != '-')
                            rampage = true;
                        if(theGame[y+i][x].getContents().getContents() == 'x' && theGame[y+i][x].getContents().getTeam() == team)
                            foundKing = true;
                        if(theGame[y+i][x].getContents().getContents() == 'r' && theGame[y+i][x].getContents().getMoveSet() == 'R'
                                ||
                                theGame[y+i][x].getContents().getContents() == 'q' && theGame[y+i][x].getContents().getMoveSet() == 'N')
                            foundBlock = true;
                        if(theGame[y+i][x].getContents().getContents() == 'r' && theGame[y+i][x].getContents().getMoveSet() == 'A' && i == 3)
                            foundBlock = true;
                    }
                }




                if (rampage)
                {
                    if(foundKing || foundBlock)
                    {
                        //we either would have taken our own king, or we would have taken a ghost in this rampage.
                        //either way we can move here.
                        return false;
                    }
                    //we rampaged.  Need to see if we are currently looking at the farthest point we can travel
                    //if the next spot in the direction we are traveling or the boards edge, then we can go ahead
                    //and rampage, and it just terminates at the edge/ghost.
                    if ( (b+1) < 8 &&

                            (theGame[b+1][a].getContents().getContents() == 'r' && theGame[b+1][a].getContents().getMoveSet() == 'A' && (b+1) - y  == 3))
                    {
                        //right in front of us is a Reaper rook.  we can move here.
                        return true;

                    }  else if(b+1 < 8)
                    {
                        if(theGame[b+1][a].getContents().getContents() == 'r' && theGame[b+1][a].getContents().getMoveSet() == 'R')
                        {
                            //right in front of us is a Reaper rook.  we can move here.
                            return true;
                        }
                    }

                    else
                    {
                        //since we incrememnted by 1 and its == 8, then that means we were moving right to the edge. its ok
                        //to rampage here.
                        return true;
                    }

                }
                if(rampage && ((b != y+3)&& b!=7))//b is not our maximum movement and we've rampaged. cant actually move here.
                {
                    return false;
                }



                //we can make our move and see if we got put into check.
                //count up to 3 or B, whichever comes first, and replace each piece.
                for(int i = 1; i<4; i++)
                {
                    if ( y+i == b)
                    {
                        //at b, switch rook with this spot break out of loop.
                        rook[i] = theGame[y+i][x];
                        theGame[y+i][x] = theGame[y][x];
                        theGame[y][x] = new SingleSpace();
                        i = 4;
                    }
                    else
                    {
                        //add this index to our array for reversal, and remove the piece.
                        rook[i] = theGame[y+i][x];
                        theGame[y+i][x] = new SingleSpace();
                    }

                }

                //now check for in check
                if(inCheck(team))
                {
                    for(int i = 1; i<4; i++)
                    {
                        if ( y+i == b)
                        {
                            //at b, switch rook with this spot break out of loop.


                            theGame[y][x] = theGame[y+i][x];
                            theGame[y+i][x] = rook[i];
                            i = 4;
                        }
                        else
                        {
                            //add this index to our array for reversal, and remove the piece.
                            theGame[y+i][x] = rook[i];
                        }

                    }
                    return false;
                }
                else
                {
                    for(int i = 1; i<4; i++)
                    {
                        if ( y+i == b)
                        {
                            //at b, switch rook with this spot break out of loop.


                            theGame[y][x] = theGame[y+i][x];
                            theGame[y+i][x] = rook[i];
                            i = 4;
                        }
                        else
                        {
                            //add this index to our array for reversal, and remove the piece.
                            theGame[y+i][x] = rook[i];
                        }

                    }
                    return true;
                }


            }
            else
            {

                //piece is moving down.


                for(int i = 1; i <4; i++ )
                {
                    if(y-i >= 0 && y-i >= b)
                    {
                        //count up till we hit our 'b' and see if we are going to rampage.
                        if(theGame[y-i][x].getContents().getContents() != '-')
                            rampage = true;
                        if(theGame[y-i][x].getContents().getContents() == 'x' && theGame[y-i][x].getContents().getTeam() == team)
                            foundKing = true;
                        if(theGame[y-i][x].getContents().getContents() == 'r' && theGame[y-i][x].getContents().getMoveSet() == 'R'
                                ||
                                theGame[y-i][x].getContents().getContents() == 'q' && theGame[y-i][x].getContents().getMoveSet() == 'N')
                            foundBlock = true;
                        if(theGame[y-i][x].getContents().getContents() == 'r' && theGame[y-i][x].getContents().getMoveSet() == 'A' && i == 3)
                            foundBlock = true;
                    }
                }



                if (rampage) {
                    if (foundKing || foundBlock) {
                        //we either would have taken our own king, or we would have taken a ghost in this rampage.
                        //either way we can move here.
                        return false;
                    }
                    //we rampaged.  Need to see if we are currently looking at the farthest point we can travel
                    //if the next spot in the direction we are traveling or the boards edge, then we can go ahead
                    //and rampage, and it just terminates at the edge/ghost.
                    if ( (b-1) >=0 &&

                            (theGame[b-1][a].getContents().getContents() == 'r' && theGame[b-1][a].getContents().getMoveSet() == 'A' && y - (b-1) == 3)) {
                        //right in front of us is a Reaper rook.  we can move here.
                        return true;
                    }
                    else if (b - 1 >= 0) {
                        if (theGame[b - 1][a].getContents().getContents() == 'r' && theGame[b - 1][a].getContents().getMoveSet() == 'R') {
                            //right in front of us is a Reaper rook.  we can move here.
                            return true;
                        }

                    } else {
                        //since we incrememnted by 1 and its == 8, then that means we were moving right to the edge. its ok
                        //to rampage here.
                        return true;
                    }
                }

                if(rampage && ((b != y-3)&& b!=0))//b is not our maximum movement and we've rampaged. cant actually move here.
                {
                    return false;
                }
                //we can make our move and see if we got put into check.
                //count up to 3 or B, whichever comes first, and replace each piece.
                for(int i = 1; i<4; i++)
                {
                    if ( y-i == b)
                    {
                        //at b, switch rook with this spot break out of loop.
                        rook[i] = theGame[y-i][x];
                        theGame[y-i][x] = theGame[y][x];
                        theGame[y][x] = new SingleSpace();
                        i = 4;
                    }
                    else
                    {
                        //add this index to our array for reversal, and remove the piece.
                        rook[i] = theGame[y-i][x];
                        theGame[y-i][x] = new SingleSpace();
                    }

                }

                //now check for in check
                if(inCheck(team))
                {
                    for(int i = 1; i<4; i++)
                    {
                        if ( y-i == b)
                        {
                            //at b, switch rook with this spot break out of loop.


                            theGame[y][x] = theGame[y-i][x];
                            theGame[y-i][x] = rook[i];
                            i = 4;
                        }
                        else
                        {
                            //add this index to our array for reversal, and remove the piece.
                            theGame[y-i][x] = rook[i];
                        }

                    }
                    return false;
                }
                else
                {
                    for(int i = 1; i<4; i++)
                    {
                        if ( y-i == b)
                        {
                            //at b, switch rook with this spot break out of loop.


                            theGame[y][x] = theGame[y-i][x];
                            theGame[y-i][x] = rook[i];
                            i = 4;
                        }
                        else
                        {
                            //add this index to our array for reversal, and remove the piece.
                            theGame[y-i][x] = rook[i];
                        }

                    }
                    return true;
                }


            }
        }
        else
        {
            //we have column movement.  lets see if its positive or negative.
            if( a > x)
            {
                //piece is moving 'right
                for(int i = 1; i <4; i++ ) {
                    if (x + i < 8 && x + i <= a) {
                        //count up till we hit our 'b' and see if we are going to rampage.
                        if (theGame[y][x + i].getContents().getContents() != '-')
                            rampage = true;
                        if (theGame[y][x + i].getContents().getContents() == 'x' && theGame[y][x + i].getContents().getTeam() == team)
                            foundKing = true;
                        if (theGame[y][x + i].getContents().getContents() == 'r' && theGame[y][x + i].getContents().getMoveSet() == 'R'
                                ||
                                theGame[y][x + i].getContents().getContents() == 'q' && theGame[y][x + i].getContents().getMoveSet() == 'N')
                            foundBlock = true;
                        if(theGame[y][x+i].getContents().getContents() == 'r' && theGame[y][x+i].getContents().getMoveSet() == 'A' && i == 3)
                            foundBlock = true;
                    }
                }
                if (rampage) {
                    if (foundKing || foundBlock) {
                        //we either would have taken our own king, or we would have taken a ghost in this rampage.
                        //either way we can move here.
                        return false;
                    }
                    //we rampaged.  Need to see if we are currently looking at the farthest point we can travel
                    //if the next spot in the direction we are traveling or the boards edge, then we can go ahead
                    //and rampage, and it just terminates at the edge/ghost.
                    if ((a + 1) < 8 &&

                            (theGame[b][a + 1].getContents().getContents() == 'r' && theGame[b][a + 1].getContents().getMoveSet() == 'A' && (a + 1) - x == 3)) {
                        //right in front of us is a Reaper rook.  we can move here.
                        return true;
                    } else if (a + 1 < 8) {
                        if (theGame[b][a + 1].getContents().getContents() == 'r' && theGame[b][a + 1].getContents().getMoveSet() == 'R') {
                            //right in front of us is a Reaper rook.  we can move here.
                            return true;
                        }
                    } else {
                        //since we incrememnted by 1 and its == 8, then that means we were moving right to the edge. its ok
                        //to rampage here.
                        return true;
                    }
                }
                if(rampage &&((a != x+3)&& a!=7))//a is not our maximum movement and we've rampaged. cant actually move here.
                {
                    return false;
                }


                //we can make our move and see if we got put into check.
                //count up to 3 or B, whichever comes first, and replace each piece.
                for(int i = 1; i<4; i++)
                {
                    if ( x+i == a)
                    {
                        //at b, switch rook with this spot break out of loop.
                        rook[i] = theGame[y][x+i];
                        theGame[y][x+i] = theGame[y][x];
                        theGame[y][x] = new SingleSpace();
                        i = 4;
                    }
                    else
                    {
                        //add this index to our array for reversal, and remove the piece.
                        rook[i] = theGame[y][x+i];
                        theGame[y][x+i] = new SingleSpace();
                    }

                }

                //now check for in check
                if(inCheck(team))
                {
                    for(int i = 1; i<4; i++)
                    {
                        if ( x+i == a)
                        {
                            //at b, switch rook with this spot break out of loop.


                            theGame[y][x] = theGame[y][x+i];
                            theGame[y][x+i] = rook[i];
                            i = 4;
                        }
                        else
                        {
                            //add this index to our array for reversal, and remove the piece.
                            theGame[y][x+i] = rook[i];
                        }

                    }
                    return false;
                }
                else
                {
                    for(int i = 1; i<4; i++)
                    {
                        if ( x+i == a)
                        {
                            //at b, switch rook with this spot break out of loop.


                            theGame[y][x] = theGame[y][x+i];
                            theGame[y][x+i] = rook[i];
                            i = 4;
                        }
                        else
                        {
                            //add this index to our array for reversal, and remove the piece.
                            theGame[y][x+i] = rook[i];
                        }

                    }
                    return true;
                }


            }
            else
            {

                //piece is moving 'left
                for(int i = 1; i <4; i++ )
                {
                    if(x-i >=0 && x-i >= a)
                    {
                        //count up till we hit our 'b' and see if we are going to rampage.
                        if(theGame[y][x-i].getContents().getContents() != '-')
                            rampage = true;
                        if(theGame[y][x-i].getContents().getContents() == 'x' && theGame[y][x-i].getContents().getTeam() == team)
                            foundKing = true;
                        if(theGame[y][x-i].getContents().getContents() == 'r' && theGame[y][x-i].getContents().getMoveSet() == 'R'
                                ||
                                theGame[y][x-i].getContents().getContents() == 'q' && theGame[y][x-i].getContents().getMoveSet() == 'N')
                            foundBlock = true;
                        if(theGame[y][x-i].getContents().getContents() == 'r' && theGame[y][x-i].getContents().getMoveSet() == 'A' && i == 3)
                            foundBlock = true;
                    }
                }

                if (rampage) {
                    if (foundKing || foundBlock) {
                        //we either would have taken our own king, or we would have taken a ghost in this rampage.
                        //either way we can move here.
                        return false;
                    }
                    //we rampaged.  Need to see if we are currently looking at the farthest point we can travel
                    //if the next spot in the direction we are traveling or the boards edge, then we can go ahead
                    //and rampage, and it just terminates at the edge/ghost.
                    if ((a - 1) >= 0 &&
                            (theGame[b][a - 1].getContents().getContents() == 'r' && theGame[b][a - 1].getContents().getMoveSet() == 'A' && x - (a - 1) == 3)) {
                        //right in front of us is a Reaper rook.  we can move here.
                        return true;
                    } else if (a - 1 >= 0) {
                        if (theGame[b][a - 1].getContents().getContents() == 'r' && theGame[b][a - 1].getContents().getMoveSet() == 'R') {
                            //right in front of us is a Reaper rook.  we can move here.
                            return true;
                        }
                    } else {
                        //since we incrememnted by 1 and its == 8, then that means we were moving right to the edge. its ok
                        //to rampage here.
                        return true;
                    }

                }
                if(rampage && ((a != x-3)&& a!=0))//a is not our maximum movement and we've rampaged. cant actually move here.
                {
                    return false;
                }
            }

            //we can make our move and see if we got put into check.
            //count up to 3 or B, whichever comes first, and replace each piece.
            for(int i = 1; i<4; i++)
            {
                if ( x-i == a)
                {
                    //at b, switch rook with this spot break out of loop.
                    rook[i] = theGame[y][x-i];
                    theGame[y][x-i] = theGame[y][x];
                    theGame[y][x] = new SingleSpace();
                    i = 4;
                }
                else
                {
                    //add this index to our array for reversal, and remove the piece.
                    rook[i] = theGame[y][x-i];
                    theGame[y][x-i] = new SingleSpace();
                }

            }

            //now check for in check
            if(inCheck(team))
            {
                for(int i = 1; i<4; i++)
                {
                    if ( x-i == a)
                    {
                        //at b, switch rook with this spot break out of loop.


                        theGame[y][x] = theGame[y][x-i];
                        theGame[y][x-i] = rook[i];
                        i = 4;
                    }
                    else
                    {
                        //add this index to our array for reversal, and remove the piece.
                        theGame[y][x-i] = rook[i];
                    }

                }
                return false;
            }
            else
            {
                for(int i = 1; i<4; i++)
                {
                    if ( x-i == a)
                    {
                        //at b, switch rook with this spot break out of loop.


                        theGame[y][x] = theGame[y][x-i];
                        theGame[y][x-i] = rook[i];
                        i = 4;
                    }
                    else
                    {
                        //add this index to our array for reversal, and remove the piece.
                        theGame[y][x-i] = rook[i];
                    }

                }
                return true;
            }

        }
    }


    public boolean simulateABishop(int x, int y, int a, int b)
    {
        char team = theGame[y][x].getContents().getTeam();
        SingleSpace temp = new SingleSpace();

        if(theGame[b][a].getContents().getContents() != '-' && theGame[b][a].getContents().getTeam() != team)
        {

            //we can take it.
            temp = theGame[b][a];
            theGame[b][a] = new SingleSpace();

            if(inCheck(team))
            {
                theGame[b][a] = temp;
                return false;
            }
            else
            {
                theGame[b][a] = temp;
                return true;
            }
        }
        else {
            temp = theGame[b][a];
            theGame[b][a] = theGame[y][x];
            theGame[y][x] = new SingleSpace();


            if (inCheck(team)) {

                theGame[y][x] = theGame[b][a];
                theGame[b][a] = temp;

                return false;
            } else {
                theGame[y][x] = theGame[b][a];
                theGame[b][a] = temp;
                return true;
            }
        }
    }

    public void whirlwind() {

        WWDisplaced = new SingleSpace[8];
        this.justWW = true;
        int count = 0;
        int row = 0, col = 0;
        for(int x = 0; x<8; x++)
        {
            for(int y =0; y<8; y++)
            {
                if(theGame[y][x].getSelected())
                {
                    col = x;
                    row = y;
                    x = 8;
                    y = 8;
                }


            }
        }

        for(int i = -1; i<2; i++) {

            for (int j = -1; j < 2; j++) {

                if((col+j)>=0 && (col+j)<8 && (row+i)>=0 && (row+i)<8 && (j!= 0 || i != 0))
                {


                    if(theGame[row+i][col+j].getContents().getContents() != 'r' || theGame[row+i][col+j].getContents().getMoveSet() !='R') {
                        WWDisplaced[count] = theGame[row + i][col + j];
                        theGame[row + i][col + j] = new SingleSpace();
                    }
                    count++;
                }
            }
        }
        clearHighlight();

    }

    public boolean canWhirlWind() {

        //loop through to find selected piece, and see if a whirlwind will
        //A: Not kill our own king.
        //B:Not put us in check.

        boolean returnFlag = false;
        int row = 0, col = 0;
        for(int x = 0; x<8; x++)
        {
            for(int y =0; y<8; y++)
            {
                if(theGame[y][x].getSelected())
                {
                    col = x;
                    row = y;
                    x = 8;
                    y = 8;
                }


            }
        }


        //can replace a maximum of 8 pieces.
        SingleSpace[] temp = new SingleSpace[8];
        char team = theGame[row][col].getContents().getTeam();
        int count = 0;
        boolean kingFound = false;
        for(int i = -1; i<2; i++) {

            for (int j = -1; j < 2; j++) {

                if((col+j)>=0 && (col+j)<8 && (row+i)>=0 && (row+i)<8 && (j!= 0 || i != 0))
                {
                    //we can check this place/remove it.
                    if(theGame[row+i][col+j].getContents().getTeam() == team && theGame[row+i][col+j].getContents().getContents() == 'x')
                    {
                        kingFound = true;
                    }
                    temp[count] = theGame[row + i][col + j];
                    theGame[row+i][col+j] = new SingleSpace();
                    count++;
                }
            }
        }
        this.clearHighlight();
        if(!inCheck(team) && !kingFound)
        {
            count = 0;
            for(int i = -1; i<2; i++) {

                for (int j = -1; j < 2; j++) {

                    if((col+j)>=0 && (col+j)<8 && (row+i)>=0 && (row+i)<8 && (j!= 0 || i != 0))
                    {

                        theGame[row + i][col + j] = temp[count];
                        count++;
                    }
                }
            }

            returnFlag =  true;
        }
        else
        {
            count = 0;
            for(int i = -1; i<2; i++) {

                for (int j = -1; j < 2; j++) {

                    if((col+j)>=0 && (col+j)<8 && (row+i)>=0 && (row+i)<8 && (j!= 0 || i != 0))
                    {

                        theGame[row + i][col + j] = temp[count];
                        count++;
                    }
                }
            }

            returnFlag = false;
        }



        this.clearHighlight();
        int[] index =  this.getSelectedIndex();
        this.highlight(index[0],index[1]);
        return returnFlag;
    }

    public boolean getWWD()
    {
        return justWW;
    }
    public void setWWD(boolean temp)
    {

        justWW = temp;

    }

    public char getMyVar()
    {
        if(myTeam == 'w')
        {
            return this.whiteVar;
        }
        else
            return this.blackVar;
    }

    public void determineCheck()
    {
        //used to determine if either team is in check.
        //highlight all black pieces.

        for(int i = 0; i<8; i++)
        {
            for (int j = 0; j<8; j++)
            {
                if(theGame[j][i].getContents().getTeam() == 'b')
                {
                    theGame[j][i].getContents().highlight(this, j, i);//highlights every possible move.
                }
            }
        }
        if(inCheck('w'))

            this.whiteCheck = true;
        else
            this.whiteCheck = false;

        clearHighlight();

        for(int i = 0; i<8; i++)
        {
            for (int j = 0; j<8; j++)
            {
                if(theGame[j][i].getContents().getTeam() == 'w')
                {
                    theGame[j][i].getContents().highlight(this, j, i);//highlights every possible move.
                }
            }
        }

        if(inCheck('b'))

            this.blackCheck = true;
        else
            this.blackCheck = false;

        clearHighlight();

    }


    public boolean isWhiteCheck() {
        return whiteCheck;
    }

    public void setWhiteCheck(boolean whiteCheck) {
        this.whiteCheck = whiteCheck;
    }

    public boolean isBlackCheck() {
        return blackCheck;
    }

    public void setBlackCheck(boolean blackCheck) {
        this.blackCheck = blackCheck;
    }


    public boolean isPlayable() {
        return playable;
    }

    public void setPlayable(boolean playable) {
        this.playable = playable;
    }


    public String getWhoseTurn() {
        return whoseTurn;
    }

    public void setWhoseTurn(String whoseTurn) {
        this.whoseTurn = whoseTurn;
    }

    public int[][] validMoves(int row , int col)
    {
        //highlihgt all of that pieces  moves.
        this.clearHighlight();
        theGame[row][col].getContents().highlight(this, row, col);

        ArrayList<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i<8; i++) {
            for (int j = 0; j < 8; j++) {
                if (theGame[i][j].getHighlight()) {
                    //its highlighted.  We want to put it in a temporary array.
                    list.add(i);
                    list.add(j);

                }
            }
        }
        Integer[] array =  list.toArray(new Integer[0]);
        int[][] spaces = new int[array.length/2][2];

        int count = 0;
        for(int i =0; i< array.length; i = i + 2)
        {
            //simulate a move to each space, and see if it results in check
            if ( this.simulateMove(col,row,array[i+1], array[i]) )
            {
                //returned true, it didnt result in check. append to our final 2d array of spaces to highlight.
                spaces[count][0] = array[i];
                spaces[count][1] = array[i+1];
                count++;
            }
        }
        if (count == 0)
        {
            return null;
        }
        else
        {
            return spaces;
        }

    }

    public String gameIsOver()
    {
        if(winner != null)
        {
            //we have a winner already, so lets return that.

            return winner;
        }

        int[][] availableMoves;
        boolean checkmate= true;
        //if not, lets check  and see if we have some checkmate.
        if(isWhiteCheck())
        {

            //white might be in checkmate, lets evaluate their moves.
            int[][] kings = {{-1,-1},{-1,-1}};
            for (int i = 0; i<8; i++)
            {
                for(int j = 0; j<8; j++)
                {
                    if(theGame[i][j].getContents().getTeam() == 'w')
                    {
                        availableMoves = validMoves(i,j);
                        if(availableMoves != null)
                        {
                            checkmate = false;
                        }
                    }
                    if(theGame[i][j].getContents().getTeam() == 'w' && theGame[i][j].getContents().getContents() == 'x')
                    {
                        if(kings[0][0] == -1)
                        {
                            kings[0][0] = i;
                            kings [0][1]= j;
                        }
                        else
                        {
                            kings[1][0] = i;
                            kings [1][1]= j;
                        }



                    }
                }
            }
            //check to see if a whirlwind can get us out of our check
            if(this.getWhiteVar() == 'T')
            {

                theGame[kings[0][0]][kings[0][1]].setSelected(true);
                if(canWhirlWind()) {
                    checkmate = false;
                }
                theGame[kings[0][0]][kings[0][1]].setSelected(false);

                theGame[kings[1][0]][kings[1][1]].setSelected(true);
                if(canWhirlWind()) {
                    checkmate = false;
                }
                theGame[kings[1][0]][kings[1][1]].setSelected(false);



            }
            if(checkmate)
            {
                //white has been placed in checkmate.  Update the game
                this.winner = this.blackTeam;
                return winner;
            }

            //check to see if white won by midline invasion.


            for (int i = 0; i<8; i++)
            {
                for(int j = 0; j<8; j++)
                {
                    if(theGame[i][j].getContents().getContents() == 'x' && theGame[i][j].getContents().getTeam() == 'w')
                    {
                        if( kings[0][0] == -1)
                        {
                            kings[0][0] = i;
                            kings[0][1] = j;
                        }
                        else
                        {
                            kings[1][0] = i;

                            kings[1][1] = j;
                        }
                    }
                }
            }

            if(kings[1][0] != -1)
            {
                //two kings must be passed.
                if(kings[0][0] >3 && kings[1][0] > 3)
                {
                    this.winner = this.whiteTeam;
                    return winner;
                }
            }
            else
            {
                //one king must be passed.
                if(kings[0][0] > 3)
                {
                    this.winner = this.whiteTeam;
                    return winner;
                }
            }

        }
        else if (isBlackCheck())
        {
            //black might be in checkmate, lets evaluate their moves.
            //white might be in checkmate, lets evaluate their moves.
            for (int i = 0; i<8; i++)
            {
                for(int j = 0; j<8; j++)
                {
                    if(theGame[i][j].getContents().getTeam() == 'b')
                    {
                        availableMoves = validMoves(i,j);
                        if(availableMoves != null)
                        {
                            checkmate = false;
                        }
                    }
                }
            }
            if(checkmate)
            {
                //black has been placed in checkmate.  Update the game
                this.winner = this.whiteTeam;
                return winner;
            }

            //check to see if black won by midline invasion.
            //find king(s)
            int[][] kings = {{-1,-1},{-1,-1}};
            for (int i = 0; i<8; i++)
            {
                for(int j = 0; j<8; j++)
                {
                    if(theGame[i][j].getContents().getContents() == 'x' && theGame[i][j].getContents().getTeam() == 'b')
                    {
                        if( kings[0][0] == -1)
                        {
                            kings[0][0] = i;
                            kings[0][1] = j;
                        }
                        else
                        {
                            kings[1][0] = i;

                            kings[1][1] = j;
                        }
                    }
                }
            }

            if(kings[1][0] != -1)
            {
                //two kings must be passed.
                if(kings[0][0] <4 && kings[1][0] <4)
                {
                    this.winner = this.blackTeam;
                    return winner;
                }
            }
            else
            {
                //one king must be passed.
                if(kings[0][0] <4)
                {
                    this.winner = this.blackTeam;
                    return winner;
                }
            }




        }
        return null;

    }
    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void increaseToken(char team)
    {
        if(team == 'w')
        {
            if(whiteTokens <6)
                whiteTokens++;
        }
        else
        {
            if(blackTokens <6)
                blackTokens++;
        }



    }


    public int getWhiteTokens() {
        return whiteTokens;
    }

    public void setWhiteTokens(int whiteTokens) {
        this.whiteTokens = whiteTokens;
    }

    public int getBlackTokens() {
        return blackTokens;
    }

    public void setBlackTokens(int blackTokens) {
        this.blackTokens = blackTokens;
    }

    public int getInsurance() {
        return insurance;
    }

    public void setInsurance(int insurance) {
        this.insurance = insurance;
    }



    public boolean isPromptDefense() {
        return promptDefense;
    }

    public void setPromptDefense(boolean promptDefense) {
        this.promptDefense = promptDefense;
    }

    public SingleSpace getPieceJustTaken()
    {
        return this.pieceJustTaken;
    }

    public void setPieceJustTaken(SingleSpace pieceJustTaken) {
        this.pieceJustTaken = pieceJustTaken;
    }

    public SingleSpace getPieceJustMoved() {
        return pieceJustMoved;
    }

    public void setPieceJustMoved(SingleSpace pieceJustMoved) {
        this.pieceJustMoved = pieceJustMoved;
    }

    public boolean whiteMidline()
    {
        //find white's king(s).

        boolean result = false;
        int[][] kings = {{-1,-1},{-1,-1}};
        for(int i = 0; i <8; i++)
        {

            for(int j = 0; j<8; j++)
            {
                if(theGame[i][j].getContents().getTeam() == 'w' && theGame[i][j].getContents().getContents() == 'x')
                {
                    if(kings[0][0] == -1)
                    {
                        kings[0][0] = i;
                        kings [0][1] = j;

                    }
                    else
                    {
                        kings[1][0] = i;
                        kings[1][1] = j;
                    }


                }
            }



        }

        if(kings[0][0] > 3)
        {
            result = true;
        }

        if(kings[1][0] >-1 && result == true)
        {
            if(kings[1][0] > 3)
                result = true;
            else
                result = false;
        }


        return result;
    }



    public boolean blackMidline()
    {
        //find black's king(s)
        boolean result = false;
        int[][] kings = {{-1,-1},{-1,-1}};
        for(int i = 0; i <8; i++)
        {

            for(int j = 0; j<8; j++)
            {
                if(theGame[i][j].getContents().getTeam() == 'b' && theGame[i][j].getContents().getContents() == 'x')
                {
                    if(kings[0][0] == -1)
                    {
                        kings[0][0] = i;
                        kings [0][1] = j;

                    }
                    else
                    {
                        kings[1][0] = i;
                        kings[1][1] = j;
                    }


                }
            }



        }

        if(kings[0][0] < 4)
        {
            result = true;
        }

        if(kings[1][0] >-1 && result == true)
        {
            if(kings[1][0] <4)
                result = true;
            else
                result = false;
        }


        return result;

    }
    public boolean isPromptOffense() {
        return promptOffense;
    }
    public void setPromptOffense(boolean promptOffense) {
        this.promptOffense = promptOffense;
    }
    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public void increaseNumberOfMoves() {
        this.numberOfMoves = this.numberOfMoves+1;
    }


    public void deleteSelectedPiece() {

        //find selected

        for(int i = 0; i < 8; i++)
        {

            for(int j = 0; j<8; j++)
            {


                if(theGame[i][j].getSelected())
                {
                    theGame[i][j] = new SingleSpace();
                }


            }
        }
    }

    public void promotePiece(int col, int row, char promote)
    {

        if(row ==0 || row == 7)
        {
            if(theGame[row][col].getContents().getContents() == 'p')
            {
                if(theGame[row][col].getContents().getTeam() == 'w')
                {
                    switch (this.whiteVar)
                    {

                        case 'C':
                            switch(promote)
                            {
                                case 'Q':

                                    theGame[row][col].setContents(new CQueen('w', 'C', 'q', true, QUEENTIER));
                                    break;

                                case 'R':

                                    theGame[row][col].setContents(new CRook('w', 'C', 'r', true,ROOKTIER));
                                    break;
                                case 'K':
                                    theGame[row][col].setContents(new CKnight('w', 'C', 'k', true,KNIGHTTIER));
                                    break;
                                case 'B':
                                    theGame[row][col].setContents(new CBishop('w', 'C', 'b', true,BISHOPTIER));
                                    break;
                            }
                            break;
                        case 'R':
                            switch(promote)
                            {
                                case 'Q':
                                    theGame[row][col].setContents(new RQueen('w', 'R', 'q', true, QUEENTIER));
                                    break;

                                case 'R':
                                    theGame[row][col].setContents(new RRook('w', 'R', 'r', true,ROOKTIER));
                                    break;
                                case 'K':
                                    theGame[row][col].setContents(new CKnight('w', 'R', 'k', true,KNIGHTTIER));
                                    break;
                                case 'B':
                                    theGame[row][col].setContents(new CBishop('w', 'R', 'b', true,BISHOPTIER));
                                    break;
                            }
                            break;
                        case 'N':
                            switch(promote)
                            {
                                case 'Q':
                                    theGame[row][col].setContents(new NQueen('w', 'N', 'q', true, QUEENTIER));
                                    break;

                                case 'R':
                                    theGame[row][col].setContents(new CRook('w', 'N', 'r', true,ROOKTIER));
                                    break;
                                case 'K':
                                    theGame[row][col].setContents(new CKnight('w', 'N', 'k', true,KNIGHTTIER));
                                    break;
                                case 'B':
                                    theGame[row][col].setContents(new CBishop('w', 'N', 'b', true,BISHOPTIER));
                                    break;
                            }
                            break;
                        case 'E':
                            switch(promote)
                            {
                                case 'Q':
                                    theGame[row][col].setContents(new EQueen('w', 'E', 'q', true, QUEENTIER));
                                    break;

                                case 'R':
                                    theGame[row][col].setContents(new ERook('w', 'E', 'r', true,ROOKTIER));
                                    break;
                                case 'K':
                                    theGame[row][col].setContents(new EKnight('w', 'E', 'k', true,KNIGHTTIER));
                                    break;
                                case 'B':
                                    theGame[row][col].setContents(new EBishop('w', 'E', 'b', true,BISHOPTIER));
                                    break;
                            }
                            break;
                        case 'A':
                            switch(promote)
                            {
                                case 'Q':
                                    theGame[row][col].setContents(new AQueen('w', 'A', 'q', true, QUEENTIER));
                                    break;

                                case 'R':
                                    theGame[row][col].setContents(new ARook('w', 'A', 'r', true,ROOKTIER));
                                    break;
                                case 'K':
                                    theGame[row][col].setContents(new AKnight('w', 'A', 'k', true,KNIGHTTIER));
                                    break;
                                case 'B':
                                    theGame[row][col].setContents(new ABishop('w', 'A', 'b', true,BISHOPTIER));
                                    break;
                            }
                            break;
                        case 'T':
                            switch(promote)
                            {

                                case 'R':
                                    theGame[row][col].setContents(new CRook('w', 'T', 'r', true,ROOKTIER));
                                    break;
                                case 'K':
                                    theGame[row][col].setContents(new CKnight('w', 'T', 'k', true,KNIGHTTIER));
                                    break;
                                case 'B':
                                    theGame[row][col].setContents(new CBishop('w', 'T', 'b', true,BISHOPTIER));
                                    break;
                            }
                            break;

                    }
                }
            }
        }



    }

    public void addCaptured(PieceInf capped)
    {
        if(capturedPieces == null)
        {
            capturedPieces = new PieceInf[1];
            capturedPieces[0] = capped;
        }
        else
        {
            PieceInf[] temp = new PieceInf[capturedPieces.length + 1];
            for(int i = 0; i < capturedPieces.length; i++)
            {
                temp[i] = capturedPieces[i];
            }
            temp[capturedPieces.length] = capped;

            capturedPieces = temp;
        }


    }


    public PieceInf[] getCapturedPieces() {
        return capturedPieces;
    }

    public void deconstruct()
    {



    }

}