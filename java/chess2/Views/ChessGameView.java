package chess2.Views;

import com.botna.chess2.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import chess2.Activities.PlayGameActivity;
import chess2.source.GameBoard;
import chess2.source.PieceInf;

@SuppressLint({"ClickableViewAccessibility"})
public class ChessGameView
        extends View
{
    private final static String[] promoteList = {"Queen", "Rook", "Knight", "Bishop"};
    private final static String CONFIRM = "CONFIRM";
    private final static String DEFENSE = "DEFENSE";
    private final static String DEFENSEWAGER = "DEFENSEWAGER";
    private final static String DEFENSEDEFAULT = "DEFENSEDEFAULT";
    private final static String PASS = "PASS";
    private final static String WAGER = "WAGER";
    private final static String WW = "WHIRLWIND";
    private final static String DISPLAYING = "DISPLAYING";
    private final static String FIRST = "FIRST";
    private final static String SECOND = "SECOND";
    private final static String PROMOTE = "PROMOTE";
    private double percentHeight = 0.55D; //represents the height of the game board
    private double percentButtons = 0.1D; //represents the height of the button area
    private double percentInfo = .25D; // represent the height of the names/variants/etc
    private double percentMessage = .1D; //represents the height of the messages.
    private PlayGameActivity myActivity;
    private Bitmap oddSquare;
    private Bitmap evenSquare;
    private Bitmap moveSquare;
    private Bitmap fromSquare;
    private Bitmap token, currToken;
    private Bitmap whiteP, blackP, whiteR, blackR, whiteK, blackK, whiteB, blackB, whiteQ, blackQ, whiteX, blackX;
    private Bitmap yesButtonUp, noButtonUp, wwButtonUp, wwButtonDown, curWWUp, curWWDown, passButtonUp,yesButtonDown, noButtonDown, passButtonDown,
            curYesUp, curYesDown, curNoUp, curNoDown, curPassUp, curPassDown;
    private Bitmap button1up,button1down, button2up, button2down, button0up, button0down, cur1up, cur1down, cur2up, cur2down, cur0up, cur0down;
    private Bitmap curOS, curES, curMS, curFS, curWP, curBP, curWR, curBR, curWK, curBK, curWB, curBB, curWQ, curBQ, curWX, curBX;
    private Bitmap turnArrowRight, curArrowRight, turnArrowLeft, curArrowLeft;

    private int widthOffset;
    private int heightOffset;
    private int dimension;
    private int boardMaxHeight;
    private int boardMaxWidth;
    private int buttonHeight;
    private int messageHeight;
    private int buttonWidth;
    private int selectedX = 0;
    private int selectedY = 0;
    private int currentHeight;
    private int currentWidth;
    private int[] gameTL;
    private int[] gameBR;
    private Canvas theCanvas;
    private GameBoard myGame;
    private char myVariant;
    private String STATE;
    private String TURN;
    private int[] twoKingsFirst;
    private boolean firstPass = true;
    private boolean promote = false;
    private char myTeam;
    private int[] moveHolder = {9,9,9,9,9,9,9,9};
    private char promoteChar = ' ';
    private boolean passDown, yesDown, noDown, wwDown, zeroDown, oneDown, twoDown;
    private String messageToDisplay = null;




    public ChessGameView(Context context)
    {
        super(context);

        this.evenSquare = BitmapFactory.decodeResource(getResources(), R.drawable.even);
        this.oddSquare = BitmapFactory.decodeResource(getResources(), R.drawable.odd);
        this.moveSquare = BitmapFactory.decodeResource(getResources(), R.drawable.move);
        this.fromSquare = BitmapFactory.decodeResource(getResources(),R.drawable.from);
        this.whiteP = BitmapFactory.decodeResource(getResources(), R.drawable.w_pawn);
        this.blackP = BitmapFactory.decodeResource(getResources(), R.drawable.b_pawn);
        this.whiteR = BitmapFactory.decodeResource(getResources(), R.drawable.w_rook);
        this.blackR = BitmapFactory.decodeResource(getResources(), R.drawable.b_rook);
        this.whiteK = BitmapFactory.decodeResource(getResources(), R.drawable.w_knight);
        this.blackK = BitmapFactory.decodeResource(getResources(), R.drawable.b_knight);
        this.whiteB = BitmapFactory.decodeResource(getResources(), R.drawable.w_bishop);
        this.blackB = BitmapFactory.decodeResource(getResources(), R.drawable.b_bishop);
        this.whiteQ = BitmapFactory.decodeResource(getResources(), R.drawable.w_queen);
        this.blackQ = BitmapFactory.decodeResource(getResources(), R.drawable.b_queen);
        this.whiteX = BitmapFactory.decodeResource(getResources(), R.drawable.w_king);
        this.blackX = BitmapFactory.decodeResource(getResources(), R.drawable.b_king);
        this.yesButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.yesup);
        this.yesButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.yesdown);
        this.noButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.noup);
        this.noButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.nodown);
        this.wwButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.wwbuttonup);
        this.wwButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.wwbuttondown);
        this.passButtonUp = BitmapFactory.decodeResource(getResources(),R.drawable.passup);
        this.passButtonDown = BitmapFactory.decodeResource(getResources(),R.drawable.passdown);
        this.token = BitmapFactory.decodeResource(getResources(), R.drawable.token);
        this.button1up = BitmapFactory.decodeResource(getResources(), R.drawable.singletokenup);
        this.button2up = BitmapFactory.decodeResource(getResources(), R.drawable.doubletokenup);
        this.button0up = BitmapFactory.decodeResource(getResources(), R.drawable.zerotokenup);
        this.button1down = BitmapFactory.decodeResource(getResources(), R.drawable.singletokendown);
        this.button2down = BitmapFactory.decodeResource(getResources(), R.drawable.doubletokendown);
        this.button0down = BitmapFactory.decodeResource(getResources(), R.drawable.zerotokendown);
        this.turnArrowRight = BitmapFactory.decodeResource(getResources(), R.drawable.turn_arrow_right);
        this.turnArrowLeft = BitmapFactory.decodeResource(getResources(), R.drawable.turn_arrow_left);
        STATE = DISPLAYING;
        TURN = FIRST;

    }


    public void deconstruct()
    {
        this.curOS.recycle();
        this.curES.recycle();
        this.curMS.recycle();
        this.curFS.recycle();
        this.curWP.recycle();
        this.curBP.recycle();
        this.curWR.recycle();
        this.curBR.recycle();
        this.curWK.recycle();
        this.curBK.recycle();
        this.curWB.recycle();
        this.curBB.recycle();
        this.curWQ.recycle();
        this.curBQ.recycle();
        this.curWX.recycle();
        this.curBX.recycle();
        this.curYesUp.recycle();
        this.curNoUp.recycle();
        this.curWWUp.recycle();
        this.curWWDown.recycle();
        this.curPassUp.recycle();
        this.currToken.recycle();
        this.cur1up.recycle();
        this.cur1down.recycle();
        this.cur2up.recycle();
        this.cur2down.recycle();
        this.cur0up.recycle();
        this.cur0down.recycle();
        this.curArrowRight.recycle();
        this.curArrowLeft.recycle();

        this.curOS = null;
        this.curES= null;
        this.curMS= null;
        this.curFS= null;
        this.curWP= null;
        this.curBP= null;
        this.curWR= null;
        this.curBR= null;
        this.curWK= null;
        this.curBK= null;
        this.curWB= null;
        this.curBB= null;
        this.curWQ= null;
        this.curBQ= null;
        this.curWX= null;
        this.curBX= null;
        this.curYesUp= null;
        this.curNoUp= null;
        this.curWWUp= null;
        this.curWWDown= null;
        this.curPassUp= null;
        this.currToken= null;
        this.cur1up= null;
        this.cur1down= null;
        this.cur2up= null;
        this.cur2down= null;
        this.cur0up= null;
        this.cur0down= null;
        this.curArrowRight= null;
        this.curArrowLeft= null;

        myActivity = null;
        myGame = null;
    }



    protected void onDraw(Canvas canvas)
    {
        this.theCanvas = canvas;

        this.myGame.determineTurn();
        this.myVariant = myGame.getMyVar();

        if (firstPass) {





            char myTeam = myGame.getMyTeam();

            myGame.determineCheck();



            if (!myGame.isPlayable())
            {
                if(myGame.getWinner() != null)
                {
                    //game is over and someone won.
                    messageToDisplay = "Game Over";
                }
                else {
                    messageToDisplay = "Waiting for Black to choose";
                }
            }
            else
            {
                if(myGame.isPromptDefense() && myGame.myTurn())
                {
                    messageToDisplay = "Piece Taken. Initiate Duel?";
                    STATE = DEFENSE;

                }
            }
            if(myGame.isPromptOffense() && !myGame.myTurn())
            {
                myGame.setPlayable(false);
                messageToDisplay = "Duel Begun! Choose wager:";
                STATE = WAGER;
            }



            firstPass = false;
        }

        if(TURN.equals(FIRST)) {
            if (myTeam == 'w') {
                if (myGame.isWhiteCheck() && myGame.getWinner() == null) {
                    messageToDisplay = "Check!";

                }
            } else {
                if (myGame.isBlackCheck() && myGame.getWinner() == null) {
                    messageToDisplay = "Check!";

                }
            }
        }
        if(myGame.isPromptOffense() && !myGame.myTurn())
        {
            myGame.setPlayable(false);
            STATE = WAGER;
        }
        if(myGame.isPromptOffense() && myGame.myTurn())
        {
            myGame.setPlayable(false);
        }

        drawBoard(canvas);
        drawUserInfo(canvas);
        drawMessage(canvas);


        if(STATE.equals(CONFIRM) || STATE.equals(DEFENSE) || STATE.equals(DEFENSEDEFAULT))
        {
            drawConfirmButtons(canvas);
        }
        if(STATE.equals(WW))
        {
            drawWhirlWind(canvas);
        }
        if(STATE.equals(WAGER) || STATE.equals(DEFENSEWAGER))
        {
            drawWager(canvas);
        }
        if(TURN.equals(SECOND))
        {
            if(STATE.equals(CONFIRM)||STATE.equals(PASS))
            {
                drawConfirmButtons(canvas);
            }
            else if (STATE.equals(WW))
            {
                drawWhirlWind(canvas);
            }
            else
            {
                drawPassButton(canvas);
            }

        }
    }

    private void drawMessage(Canvas canvas) {

        if(messageToDisplay !=  null) {
            Rect bounds = new Rect();
            float startSize = (float) (currentHeight *.06);
            float startingY = 0;
            float startingX = 0;
            Paint brush = new Paint();
            brush.setTextSize(startSize);
            brush.getTextBounds(messageToDisplay, 0, messageToDisplay.length(), bounds);

            if(bounds.width() < currentWidth && bounds.height() < (currentHeight*percentMessage)*.5)
            {
                //its smaller than it needs to be. while it up until its too big then take it 2 steps back.
                while(bounds.width() < currentWidth && bounds.height() <(currentHeight*percentMessage)*.5) {
                    startSize++;
                    brush.setTextSize(startSize);
                    brush.getTextBounds(messageToDisplay, 0, messageToDisplay.length(), bounds);
                }
                startSize = startSize - 2;

            }
            else
            {
                //we need to shrink it
                while(bounds.width() > currentWidth ||bounds.height() > (currentHeight*percentMessage)*.5)
                {
                    startSize--;
                    brush.setTextSize(startSize);
                    brush.getTextBounds(messageToDisplay, 0, messageToDisplay.length(), bounds);
                }
                startSize = startSize + 2;
            }
            brush.setTextSize(startSize);
            brush.getTextBounds(messageToDisplay, 0, messageToDisplay.length(), bounds);
            startingX = (float)((currentWidth - bounds.width() )/2);
            startingY = (float)(currentHeight- currentHeight*percentButtons - (currentHeight*percentMessage)*.25);
            canvas.drawText(messageToDisplay,startingX,startingY, brush);
        }
    }

    private void drawPassButton(Canvas canvas) {

        if(!passDown)
            canvas.drawBitmap(this.curPassUp,buttonWidth, (currentHeight - buttonHeight), null);
        else
            canvas.drawBitmap(this.curPassDown, buttonWidth, (currentHeight - buttonHeight), null);


    }


    public void drawConfirmButtons(Canvas canvas)
    {
        if(!noDown)
            canvas.drawBitmap(this.curNoUp, 0, (currentHeight - buttonHeight), null);
        else
            canvas.drawBitmap(this.curNoDown, 0, (currentHeight - buttonHeight), null);

        if(!yesDown)
            canvas.drawBitmap(this.curYesUp, (2*(currentWidth/3)), (currentHeight - buttonHeight), null);
        else
            canvas.drawBitmap(this.curYesDown, (2*(currentWidth/3)), (currentHeight - buttonHeight), null);
    }

    public void drawWhirlWind(Canvas canvas)
    {
        if(myGame.canWhirlWind()) {

            if(!wwDown)
                canvas.drawBitmap(this.curWWUp, ((currentWidth/2) - (buttonHeight/2)), (currentHeight - buttonHeight), null);
            else
                canvas.drawBitmap(this.curWWDown, ((currentWidth/2) - (buttonHeight/2)), (currentHeight - buttonHeight), null);

        }

    }

    public void drawWager(Canvas canvas)
    {


        if(!zeroDown)
            canvas.drawBitmap(this.cur0up,((((currentWidth /2) - buttonHeight/2)/2) - buttonHeight/2), (currentHeight - buttonHeight), null);
        else
            canvas.drawBitmap(this.cur0down, ((((currentWidth /2) - buttonHeight/2)/2) - buttonHeight/2), (currentHeight - buttonHeight), null);


        if(!oneDown)
            canvas.drawBitmap(this.cur1up, ((currentWidth /2) - buttonHeight/2), (currentHeight - buttonHeight), null);
        else
            canvas.drawBitmap(this.cur1down, ((currentWidth /2) - buttonHeight/2), (currentHeight - buttonHeight), null);

        if(!twoDown)
            canvas.drawBitmap(this.cur2up, currentWidth - ((((currentWidth /2) - buttonHeight/2)/2) - buttonHeight/2) - buttonHeight, (currentHeight - buttonHeight), null);
        else
            canvas.drawBitmap(this.cur2down,  currentWidth - ((((currentWidth /2) - buttonHeight/2)/2) - buttonHeight/2) - buttonHeight , (currentHeight - buttonHeight), null);



    }

    public void drawUserInfo(Canvas canvas) {

        String myName, theirName,
                tempVar, theirTempVar,
                myTeam, theirTeam;
        char myVar, theirVar;

        Paint brush = new Paint();
        brush.setTextSize((float)(currentHeight*.04));

        myName = myGame.getName();
        if(myName.equals(myGame.getBlackTeam())) {
            myTeam = "Black";
            myVar = myGame.getBlackVar();
            theirTeam = "White";
            theirName = myGame.getWhiteTeam();
            theirVar = myGame.getWhiteVar();
        }
        else
        {
            myTeam = "White";
            myVar = myGame.getWhiteVar();
            theirTeam = "Black";
            theirName = myGame.getBlackTeam();
            theirVar = myGame.getBlackVar();
        }
        tempVar = "Empty";
        theirTempVar = "Empty";
        switch (myVar)
        {
            case 'C':
                tempVar = "Classic";
                break;
            case 'N':
                tempVar = "Nemesis";
                break;
            case 'R':
                tempVar = "Reapers";
                break;
            case 'E':
                tempVar = "Empowered";
                break;
            case 'A':
                tempVar = "Animals";
                break;
            case 'T':
                tempVar = "Two-Kings";
                break;
        }

        switch (theirVar)
        {
            case 'C':
                theirTempVar = "Classic";
                break;
            case 'N':
                theirTempVar = "Nemesis";
                break;
            case 'R':
                theirTempVar = "Reapers";
                break;
            case 'E':
                theirTempVar = "Empowered";
                break;
            case 'A':
                theirTempVar = "Animals";
                break;
            case 'T':
                theirTempVar = "Two-Kings";
                break;
        }
        //will need to determine portrait vs landscape.
        if(currentHeight > currentWidth)
        {

            Rect bounds = new Rect();
            int width = 0;
            int token = 0;
//            if(myGame.myTurn())
//            {
//                //my info goes on top on the left.
//                double start = (currentHeight * percentHeight + (currentHeight*.05));
//                double temp = (currentHeight - (currentHeight *.1));
//                canvas.drawText(myName,5,(float) start, brush);
//                canvas.drawText(tempVar,5,(float) (start + (currentHeight*.05)),brush);
//                //canvas.drawText(myTeam, 5, (float) (start + (currentHeight*.1)), brush);
//                if(myTeam.equals("White"))
//                {
//                    token = myGame.getWhiteTokens();
//                }
//                else
//                    token = myGame.getBlackTokens();
//
//                int currentX = 0;
//                for(int i = 0; i<token; i++)
//                {
//                    canvas.drawBitmap(this.currToken, (float)currentX, (float)(start + (currentHeight*.1)), null);
//                    currentX = currentX + this.currToken.getWidth();
//                }
//
//
//
//                //their info goes on bottom right
//                brush.getTextBounds(theirName,0,theirName.length(),bounds);
//                canvas.drawText(theirName,(currentWidth - bounds.width() - 15),(float)(temp - (currentHeight *.15)),brush );
//                brush.getTextBounds(theirTempVar,0,theirTempVar.length(),bounds);
//                canvas.drawText(theirTempVar,(currentWidth - bounds.width() - 15),(float) (temp - (currentHeight*.1)), brush);
//                // brush.getTextBounds(theirTeam,0,theirTeam.length(),bounds);
//                // canvas.drawText(theirTeam,(currentWidth - bounds.width() - 15),(float) (temp - (currentHeight*.05)), brush);
//
//                currentX = currentWidth - this.currToken.getWidth();
//                if(theirTeam.equals("White"))
//                {
//                    token = myGame.getWhiteTokens();
//                }
//                else
//                    token = myGame.getBlackTokens();
//
//                for(int i = 0; i<token; i++)
//                {
//                    canvas.drawBitmap(this.currToken, (float)currentX, (float)(temp- (currentHeight*.05)), null);
//                    currentX = currentX - this.currToken.getWidth();
//                }
//
//            }
//            else
//            {
//                //my Info goes on the bottom on the left
//                //my info goes on top on the left.
//                double start = (currentHeight - (currentHeight *.1));
//                double temp = (currentHeight * percentHeight + (currentHeight*.05));
//                canvas.drawText(myName,5,(float) (start - (currentHeight *.15)),brush);
//                canvas.drawText(tempVar,5,(float) (start - (currentHeight*.1)), brush);
//                // canvas.drawText(myTeam, 5, (float) (start - (currentHeight*.05)), brush);
//
//                if(myTeam.equals("White"))
//                {
//                    token = myGame.getWhiteTokens();
//                }
//                else
//                    token = myGame.getBlackTokens();
//
//                int currentX = 0;
//                for(int i = 0; i<token; i++)
//                {
//                    canvas.drawBitmap(this.currToken, (float)currentX, (float)(start - (currentHeight*.05)), null);
//                    currentX = currentX + this.currToken.getWidth();
//                }
//
//
//
//                //their info goes on top right
//                brush.getTextBounds(theirName,0,theirName.length(),bounds);
//                canvas.drawText(theirName,(currentWidth - bounds.width() - 15),(float)temp,brush );
//                brush.getTextBounds(theirTempVar,0,theirTempVar.length(),bounds);
//                canvas.drawText(theirTempVar,(currentWidth - bounds.width() - 15),(float) (temp + (currentHeight*.05)), brush);
//                // brush.getTextBounds(theirTeam,0,theirTeam.length(),bounds);
//                //canvas.drawText(theirTeam,(currentWidth - bounds.width() - 15),(float) (temp + (currentHeight*.1)), brush);
//                currentX = currentWidth - this.currToken.getWidth();
//                if(theirTeam.equals("White"))
//                {
//                    token = myGame.getWhiteTokens();
//                }
//                else
//                    token = myGame.getBlackTokens();
//
//                for(int i = 0; i<token; i++)
//                {
//                    canvas.drawBitmap(this.currToken, (float)currentX, (float)(temp + (currentHeight*.1)), null);
//                    currentX = currentX - this.currToken.getWidth();
//                }
//
//            }



            double start = (currentHeight * percentHeight + (currentHeight*.05));
            double topOfUser = start;
            double temp = (currentHeight - (currentHeight *.1));
            canvas.drawText(myName,5,(float) start, brush);
            canvas.drawText(tempVar,5,(float) (start + (currentHeight*.05)),brush);
            if(myTeam.equals("White"))
            {
                token = myGame.getWhiteTokens();
            }
            else
                token = myGame.getBlackTokens();

            int currentX = 0;
            for(int i = 0; i<token; i++)
            {
                canvas.drawBitmap(this.currToken, (float)currentX, (float)(start + (currentHeight*.06)), null);
                currentX = currentX + this.currToken.getWidth();
            }

            //draw missing pieces
            int pieceXLoc =0,  pieceYLoc = 0, count = 1;
            pieceXLoc =(int) -(dimension*.1);
            pieceYLoc =  (int) (start + (currentHeight*.06)) + currToken.getHeight();
            PieceInf[] capturedPieces = myGame.getCapturedPieces();

            if(capturedPieces != null) {
                for (int i = 0; i < capturedPieces.length; i++) {
                    if (myTeam.equals("White")) {
                        if (capturedPieces[i].getTeam() == 'w') {
                            switch (capturedPieces[i].getContents()) {
                                case 'p':
                                    canvas.drawBitmap(this.curWP, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;
                                case 'r':
                                    canvas.drawBitmap(this.curWR, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;
                                case 'k':
                                    canvas.drawBitmap(this.curWK, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;
                                case 'b':
                                    canvas.drawBitmap(this.curWB, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;
                                case 'q':
                                    canvas.drawBitmap(this.curWQ, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;

                            }
                            count++;
                            pieceXLoc = (int) (pieceXLoc + dimension * .8);
                            if (count == 7)//set back to our '0' to start a new row
                            {
                                pieceXLoc = (int) -(this.curWP.getWidth() * .1);
                                pieceYLoc = (int) (pieceYLoc + this.curWP.getHeight() - (this.curWP.getHeight() * .25));
                            }

                        }
                    } else {
                        if (capturedPieces[i].getTeam() == 'b') {
                            switch (capturedPieces[i].getContents()) {
                                case 'p':
                                    canvas.drawBitmap(this.curBP, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;
                                case 'r':
                                    canvas.drawBitmap(this.curBR, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;
                                case 'k':
                                    canvas.drawBitmap(this.curBK, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;
                                case 'b':
                                    canvas.drawBitmap(this.curBB, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;
                                case 'q':
                                    canvas.drawBitmap(this.curBQ, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;

                            }
                            count++;
                            pieceXLoc = (int) (pieceXLoc + dimension * .8);
                            if (count == 7)//set back to our '0' to start a new row
                            {
                                pieceXLoc = (int) -(this.curWP.getWidth() * .1);
                                pieceYLoc = (int) (pieceYLoc + this.curWP.getHeight() - (this.curWP.getHeight() * .25));
                            }


                        }
                    }
                }
            }


            brush.getTextBounds(theirName,0,theirName.length(),bounds);
            start = currentHeight - buttonHeight - messageHeight + brush.getTextSize()*.5;

            topOfUser = topOfUser + ((start-topOfUser)/2);
            canvas.drawText(theirName,(currentWidth - bounds.width() - 15),(float) (start),brush );
            brush.getTextBounds(theirTempVar,0,theirTempVar.length(),bounds);
            canvas.drawText(theirTempVar,(currentWidth - bounds.width() - 15),(float) (start - (currentHeight*.05) ), brush);

            currentX = currentWidth - this.currToken.getWidth();
            if(theirTeam.equals("White"))
            {
                token = myGame.getWhiteTokens();
            }
            else
                token = myGame.getBlackTokens();

            for(int i = 0; i<token; i++)
            {
                canvas.drawBitmap(this.currToken, (float)currentX, (float)(start - currentHeight*.13), null);
                currentX = currentX - this.currToken.getWidth();
            }



            pieceXLoc =(int) (currentWidth + (dimension*.1) - dimension);
            pieceYLoc =  (int) ((start - currentHeight*.13) - dimension);

            count = 1;
            if(capturedPieces != null) {
                for (int i = 0; i < capturedPieces.length; i++) {
                    if (myTeam.equals("White")) {
                        if (capturedPieces[i].getTeam() == 'b') {
                            switch (capturedPieces[i].getContents()) {
                                case 'p':
                                    canvas.drawBitmap(this.curBP, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;
                                case 'r':
                                    canvas.drawBitmap(this.curBR, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;
                                case 'k':
                                    canvas.drawBitmap(this.curBK, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;
                                case 'b':
                                    canvas.drawBitmap(this.curBB, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;
                                case 'q':
                                    canvas.drawBitmap(this.curBQ, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;

                            }
                            count++;
                            pieceXLoc = (int) (pieceXLoc - dimension * .8);
                            if (count == 7)//set back to our '0' to start a new row
                            {
                                pieceXLoc = (int) (currentWidth + (dimension * .1)-dimension);
                                pieceYLoc = (int) (pieceYLoc - this.curWP.getHeight() + (this.curWP.getHeight() * .25));
                            }

                        }
                    } else {
                        if (capturedPieces[i].getTeam() == 'w') {
                            switch (capturedPieces[i].getContents()) {
                                case 'p':
                                    canvas.drawBitmap(this.curWP, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;
                                case 'r':
                                    canvas.drawBitmap(this.curWR, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;
                                case 'k':
                                    canvas.drawBitmap(this.curWK, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;
                                case 'b':
                                    canvas.drawBitmap(this.curWB, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;
                                case 'q':
                                    canvas.drawBitmap(this.curWQ, (float) pieceXLoc, (float) pieceYLoc, null);
                                    break;

                            }
                            count++;
                            pieceXLoc = (int) (pieceXLoc - dimension * .8);
                            if (count == 7)//set back to our '0' to start a new row
                            {
                                pieceXLoc = (int) (currentWidth + (dimension * .1)-dimension);
                                pieceYLoc = (int) (pieceYLoc - this.curWP.getHeight() + (this.curWP.getHeight() * .25));
                            }


                        }
                    }
                }
            }


            //Draw the turn arrow.
            if(!myGame.myTurn()) {
                canvas.drawBitmap(this.curArrowRight, (float) (currentWidth / 2 - buttonHeight/2), (float) (topOfUser - buttonHeight/2), null);
           }
            else
            {
                canvas.drawBitmap(this.curArrowLeft, (float) (currentWidth / 2 - buttonHeight/2), (float) (topOfUser - buttonHeight/2), null);

            }

        }


    }

    public void drawBoard(Canvas canvas)
    {
        String myName;
        myName = myGame.getName();
        if(myName.equals(myGame.getBlackTeam())) {
            myTeam = 'b';
        }
        else
        {
            myTeam = 'w';
        }
        this.widthOffset = (this.boardMaxWidth - this.dimension * 8);
        this.heightOffset = (this.boardMaxHeight - this.dimension * 8);




        this.widthOffset /= 2;
        this.heightOffset /= 2;



        this.gameTL = new int[] { this.widthOffset, this.heightOffset };

        this.gameBR = new int[] { this.widthOffset + 8 * this.dimension, this.heightOffset + 8 * this.dimension };

        //if we are white, draw it so white is on the bottom.
        if(myTeam == 'w') {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    int xPos = this.widthOffset + j * this.dimension;
                    int yPos = this.heightOffset + i * this.dimension;
                    if (this.myGame.getTheGame()[(7 - i)][j].getHighlight()) {
                        canvas.drawBitmap(this.curMS, xPos, yPos, null);
                    } else if (this.myGame.getTheGame()[(7 - i)][j].getLastMove()) {
                        canvas.drawBitmap(this.curFS, xPos, yPos, null);
                    } else if ((j + i) % 2 == 0) {
                        canvas.drawBitmap(this.curOS, xPos, yPos, null);
                    } else {
                        canvas.drawBitmap(this.curES, xPos, yPos, null);
                    }
                    drawPieces(i, j, xPos, yPos);

                }
            }
        }
        else
        {
            //if we are black, draw it so black is on the bottom.
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    int xPos = this.widthOffset + j * this.dimension;
                    int yPos = this.heightOffset + i * this.dimension;

                    if (this.myGame.getTheGame()[(i)][7-j].getHighlight()) {
                        canvas.drawBitmap(this.curMS, xPos, yPos, null);
                    } else if (this.myGame.getTheGame()[(i)][7-j].getLastMove()) {
                        canvas.drawBitmap(this.curFS, xPos, yPos, null);
                    } else if ((j + i) % 2 == 0) {
                        canvas.drawBitmap(this.curOS, xPos, yPos, null);
                    } else {
                        canvas.drawBitmap(this.curES, xPos, yPos, null);
                    }
                    drawPieces((7-i),(7- j), xPos, yPos);
                }
            }
        }
    }

    public void drawPieces(int i, int j, int xPos, int yPos)
    {
        Bitmap pieceToDraw = null;
        Bitmap selected = null;
        switch (this.myGame.getTheGame()[(7 - i)][j].getContents().getContents())
        {
            case 'p':
                if (this.myGame.getTheGame()[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWP;
                } else {
                    pieceToDraw = this.curBP;
                }
                break;
            case 'r':
                if (this.myGame.getTheGame()[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWR;
                } else {
                    pieceToDraw = this.curBR;
                }
                break;
            case 'k':
                if (this.myGame.getTheGame()[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWK;
                } else {
                    pieceToDraw = this.curBK;
                }
                break;
            case 'b':
                if (this.myGame.getTheGame()[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWB;
                } else {
                    pieceToDraw = this.curBB;
                }
                break;
            case 'q':
                if (this.myGame.getTheGame()[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWQ;
                } else {
                    pieceToDraw = this.curBQ;
                }
                break;
            case 'x':
                if (this.myGame.getTheGame()[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWX;
                } else {
                    pieceToDraw = this.curBX;
                }
                break;
        }
        if (this.myGame.getTheGame()[(7 - i)][j].getSelected())
        {
            int temp = (int)(this.dimension * 1.5D);
            pieceToDraw = Bitmap.createScaledBitmap(pieceToDraw, temp, temp, true);
            xPos -= this.dimension / 4;
            yPos -= this.dimension / 4;
        }
        if (pieceToDraw != null) {
            this.theCanvas.drawBitmap(pieceToDraw, xPos, yPos, null);
        }
        this.myGame.getTheGame()[(7 - i)][j].setNeedsRedraw(false);
    }

    public void setGame(GameBoard theGame)
    {
        this.myGame = theGame;
        this.myGame.setState("WAITING");

    }

    public void onSizeChanged(int w, int h, int oldW, int oldH)
    {
        currentHeight = h;
        currentWidth = w;
        if (h > w)
        {
            this.boardMaxHeight = ((int)(h * this.percentHeight));
            this.boardMaxWidth = w;
            if (this.boardMaxHeight > this.boardMaxWidth) {
                this.dimension = (this.boardMaxWidth / 8);
            } else {
                this.dimension = (this.boardMaxHeight / 8);
            }

            this.buttonHeight = ((int)(h*this.percentButtons));
            this.messageHeight = ((int)(h*this.percentMessage));
            this.buttonWidth = w/3;
        }
        createNewBitmaps();
    }


    public void createNewBitmaps()
    {
        if ((this.curOS == null) || (this.curES == null))
        {
            this.curOS = Bitmap.createScaledBitmap(this.oddSquare, this.dimension, this.dimension, true);
            this.curES = Bitmap.createScaledBitmap(this.evenSquare, this.dimension, this.dimension, true);
            this.curMS = Bitmap.createScaledBitmap(this.moveSquare, this.dimension, this.dimension, true);
            this.curFS = Bitmap.createScaledBitmap(this.fromSquare, this.dimension, this.dimension, true);
            this.curWP = Bitmap.createScaledBitmap(this.whiteP, this.dimension, this.dimension, true);
            this.curBP = Bitmap.createScaledBitmap(this.blackP, this.dimension, this.dimension, true);
            this.curWR = Bitmap.createScaledBitmap(this.whiteR, this.dimension, this.dimension, true);
            this.curBR = Bitmap.createScaledBitmap(this.blackR, this.dimension, this.dimension, true);
            this.curWK = Bitmap.createScaledBitmap(this.whiteK, this.dimension, this.dimension, true);
            this.curBK = Bitmap.createScaledBitmap(this.blackK, this.dimension, this.dimension, true);
            this.curWB = Bitmap.createScaledBitmap(this.whiteB, this.dimension, this.dimension, true);
            this.curBB = Bitmap.createScaledBitmap(this.blackB, this.dimension, this.dimension, true);
            this.curWQ = Bitmap.createScaledBitmap(this.whiteQ, this.dimension, this.dimension, true);
            this.curBQ = Bitmap.createScaledBitmap(this.blackQ, this.dimension, this.dimension, true);
            this.curWX = Bitmap.createScaledBitmap(this.whiteX, this.dimension, this.dimension, true);
            this.curBX = Bitmap.createScaledBitmap(this.blackX, this.dimension, this.dimension, true);
            this.curYesUp = Bitmap.createScaledBitmap(this.yesButtonUp, this.buttonWidth,this.buttonHeight , true);
            this.curYesDown = Bitmap.createScaledBitmap(this.yesButtonDown, this.buttonWidth,this.buttonHeight , true);
            this.curNoUp = Bitmap.createScaledBitmap(this.noButtonUp, this.buttonWidth, this.buttonHeight, true);
            this.curNoDown = Bitmap.createScaledBitmap(this.noButtonDown, this.buttonWidth, this.buttonHeight, true);
            this.curWWUp = Bitmap.createScaledBitmap(this.wwButtonUp, this.buttonHeight, this.buttonHeight, true);
            this.curWWDown = Bitmap.createScaledBitmap(this.wwButtonDown, this.buttonHeight, this.buttonHeight, true);
            this.curPassUp = Bitmap.createScaledBitmap(this.passButtonUp, this.buttonWidth, this.buttonHeight, true);
            this.curPassDown = Bitmap.createScaledBitmap(this.passButtonDown, this.buttonWidth, this.buttonHeight, true);
            this.currToken = Bitmap.createScaledBitmap(this.token, this.currentWidth / 18, this.currentWidth / 18, true);
            this.cur1up =  Bitmap.createScaledBitmap(this.button1up, this.buttonHeight,this.buttonHeight , true);
            this.cur1down =  Bitmap.createScaledBitmap(this.button1down, this.buttonHeight,this.buttonHeight , true);
            this.cur2up = Bitmap.createScaledBitmap(this.button2up, this.buttonHeight,this.buttonHeight , true);
            this.cur2down = Bitmap.createScaledBitmap(this.button2down, this.buttonHeight,this.buttonHeight , true);
            this.cur0up = Bitmap.createScaledBitmap(this.button0up, this.buttonHeight,this.buttonHeight , true);
            this.cur0down = Bitmap.createScaledBitmap(this.button0down, this.buttonHeight,this.buttonHeight , true);
            this.curArrowRight = Bitmap.createScaledBitmap(this.turnArrowRight,  (int)(this.buttonHeight*.75),(int)(this.buttonHeight*.75)  , true);
            this.curArrowLeft = Bitmap.createScaledBitmap(this.turnArrowLeft, (int)(this.buttonHeight*.75),(int)(this.buttonHeight*.75)  , true);


        }
        else
        {
            this.curOS.recycle();
            this.curES.recycle();
            this.curMS.recycle();
            this.curFS.recycle();
            this.curWP.recycle();
            this.curBP.recycle();
            this.curWR.recycle();
            this.curBR.recycle();
            this.curWK.recycle();
            this.curBK.recycle();
            this.curWB.recycle();
            this.curBB.recycle();
            this.curWQ.recycle();
            this.curBQ.recycle();
            this.curWX.recycle();
            this.curBX.recycle();
            this.curYesUp.recycle();
            this.curNoUp.recycle();
            this.curWWUp.recycle();
            this.curWWDown.recycle();
            this.curPassUp.recycle();
            this.currToken.recycle();
            this.cur1up.recycle();
            this.cur1down.recycle();
            this.cur2up.recycle();
            this.cur2down.recycle();
            this.cur0up.recycle();
            this.cur0down.recycle();
            this.curArrowRight.recycle();
            this.curArrowLeft.recycle();

            this.curOS = Bitmap.createScaledBitmap(this.oddSquare, this.dimension, this.dimension, true);
            this.curES = Bitmap.createScaledBitmap(this.evenSquare, this.dimension, this.dimension, true);
            this.curMS = Bitmap.createScaledBitmap(this.moveSquare, this.dimension, this.dimension, true);
            this.curFS = Bitmap.createScaledBitmap(this.fromSquare, this.dimension, this.dimension, true);
            this.curWP = Bitmap.createScaledBitmap(this.whiteP, this.dimension, this.dimension, true);
            this.curBP = Bitmap.createScaledBitmap(this.blackP, this.dimension, this.dimension, true);
            this.curWR = Bitmap.createScaledBitmap(this.whiteR, this.dimension, this.dimension, true);
            this.curBR = Bitmap.createScaledBitmap(this.blackR, this.dimension, this.dimension, true);
            this.curWK = Bitmap.createScaledBitmap(this.whiteK, this.dimension, this.dimension, true);
            this.curBK = Bitmap.createScaledBitmap(this.blackK, this.dimension, this.dimension, true);
            this.curWB = Bitmap.createScaledBitmap(this.whiteB, this.dimension, this.dimension, true);
            this.curBB = Bitmap.createScaledBitmap(this.blackB, this.dimension, this.dimension, true);
            this.curWQ = Bitmap.createScaledBitmap(this.whiteQ, this.dimension, this.dimension, true);
            this.curBQ = Bitmap.createScaledBitmap(this.blackQ, this.dimension, this.dimension, true);
            this.curWX = Bitmap.createScaledBitmap(this.whiteX, this.dimension, this.dimension, true);
            this.curBX = Bitmap.createScaledBitmap(this.blackX, this.dimension, this.dimension, true);
            this.curYesUp = Bitmap.createScaledBitmap(this.yesButtonUp, this.buttonWidth,this.buttonHeight , true);
            this.curNoUp = Bitmap.createScaledBitmap(this.noButtonUp, this.buttonWidth, this.buttonHeight, true);
            this.curWWUp = Bitmap.createScaledBitmap(this.wwButtonUp, this.buttonHeight, this.buttonHeight, true);
            this.curWWDown = Bitmap.createScaledBitmap(this.wwButtonDown, this.buttonHeight, this.buttonHeight, true);
            this.curPassUp = Bitmap.createScaledBitmap(this.passButtonUp, this.buttonWidth, this.buttonHeight, true);
            this.currToken = Bitmap.createScaledBitmap(this.token, this.currentWidth/18, this.currentWidth/18, true);
            this.cur1up =  Bitmap.createScaledBitmap(this.button1up, this.buttonHeight,this.buttonHeight , true);
            this.cur1down =  Bitmap.createScaledBitmap(this.button1down, this.buttonHeight,this.buttonHeight , true);
            this.cur2up = Bitmap.createScaledBitmap(this.button2up, this.buttonHeight,this.buttonHeight , true);
            this.cur2down = Bitmap.createScaledBitmap(this.button2down, this.buttonHeight,this.buttonHeight , true);
            this.cur0up = Bitmap.createScaledBitmap(this.button0up, this.buttonHeight,this.buttonHeight , true);
            this.cur0down = Bitmap.createScaledBitmap(this.button0down, this.buttonHeight,this.buttonHeight , true);
            this.curArrowRight = Bitmap.createScaledBitmap(this.turnArrowRight, (int)(this.buttonHeight*.75),(int)(this.buttonHeight*.75) , true);
            this.curArrowLeft = Bitmap.createScaledBitmap(this.turnArrowLeft,  (int)(this.buttonHeight*.75),(int)(this.buttonHeight*.75) , true);
        }
    }

    public void setName(String name)
    {
        this.myGame.setName(name);
    }

    public boolean performClick()
    {
        super.performClick();

        return true;
    }

    @SuppressLint({"WrongCall"})
    public boolean onTouchEvent(MotionEvent me)
    {
        super.onTouchEvent(me);
        performClick();
        float xPos = me.getX();
        float yPos = me.getY();

        int token0 = ((((currentWidth /2) - buttonHeight/2)/2) - buttonHeight/2);
        int token1 = ((currentWidth /2) - buttonHeight/2);
        int token2 = currentWidth - ((((currentWidth /2) - buttonHeight/2)/2) - buttonHeight/2) - buttonHeight;
        int action = me.getAction();
        switch (action)
        {
            case 0:
                switch (STATE) {
                    case WW:
                        // check to see if WW button was pressed. If so, we break, but if not
                        //we let DISPLAYING take care of the rest.
                        if (yPos > (currentHeight - buttonHeight)) {
                            //y position is correct.  See if its the yes or no
                            //or in between which doesnt count here.
                            if ((xPos > ((currentWidth/2) - (buttonHeight/2)) &&  (xPos < ((currentWidth/2) + (buttonHeight/2))))){
                                wwDown = true;
                            }
                        }
                        break;
                    case DISPLAYING:
                        if (TURN.equals(SECOND)) {
                            //check for PASS button.
                            if (yPos > (currentHeight - buttonHeight)) {
                                if (xPos > buttonWidth && xPos < (currentWidth - buttonWidth)) {
                                    passDown = true;
                                }
                            }
                        }
                        break;
                    case DEFENSE:
                    case PASS:
                    case DEFENSEDEFAULT:
                    case CONFIRM:
                        if (yPos > (currentHeight - buttonHeight)) {
                            //y position is correct.  See if its the yes or no
                            //or in between which doesnt count here.
                            if (xPos < buttonWidth) {
                                noDown = true;
                                invalidate();
                            } else if (xPos > (currentWidth - buttonWidth)) {
                                yesDown = true;
                            }
                        }
                        break;
                    case DEFENSEWAGER:
                        if (yPos > (currentHeight - buttonHeight)) {
                            //y position is correct.  See if its the yes or no
                            //or in between which doesnt count here.
                            if (xPos > token0 && xPos < (token0 + buttonHeight))
                            {
                                zeroDown = true;
                            }
                            else if (xPos > token2 && xPos < (token2 + buttonHeight) )
                            {
                                twoDown = true;
                            } else if (xPos > token1 && xPos < (token1 + buttonHeight))
                            {
                                oneDown = true;
                            }
                        }
                        break;
                    case WAGER:
                        if (yPos > (currentHeight - buttonHeight)) {
                            //y position is correct.  See if its the yes or no
                            //or in between which doesnt count here.
                            if  (xPos > token0 && xPos < (token0 + buttonHeight))
                            {
                                zeroDown = true;
                            } else if  (xPos > token2 && xPos < (token2 + buttonHeight) )
                            {
                                twoDown = true;
                            } else if (xPos > token1 && xPos < (token1 + buttonHeight))
                            {
                                oneDown = true;
                            }
                        }
                        break;
                }
                invalidate();
                break;
            case 1:
                //on release
                passDown =  yesDown = noDown = wwDown = false;
                zeroDown = twoDown = oneDown = false;
                //float xPos = me.getX();
                //float yPos = me.getY();
                switch (STATE) {

                    case WW:
                        // check to see if WW button was pressed. If so, we break, but if not
                        //we let DISPLAYING take care of the rest.

                        if(yPos > (currentHeight - buttonHeight))
                        {
                            //y position is correct.  See if its the yes or no
                            //or in between which doesnt count here.
                            char team = myGame.getMyTeam();
                            if(xPos > buttonWidth &&  xPos <(currentWidth - buttonWidth))
                            {
                                //check that its my turn and that the piece is actually mine.
                                int[] temp = myGame.getSelectedIndex();
                                if (myGame.myTurn() && myGame.getTheGame()[temp[1]][temp[0]].getContents().getTeam() == team) {
                                    clickWW();
                                    messageToDisplay = "Confirm Move?";
                                    STATE = CONFIRM;
                                }
                            }
                            break;
                        }
                    case DISPLAYING :
                        if(TURN.equals(SECOND))
                        {
                            //check for PASS button.
                            if(yPos>(currentHeight-buttonHeight))
                            {
                                if(xPos>buttonWidth && xPos < (currentWidth - buttonWidth))
                                {
                                    clickPass();
                                    messageToDisplay = "Confirm Pass?";
                                }
                            }
                        }
                        if(myTeam == 'b')
                        {
                            //the board has been rotated.   we need to flip the location of the 'click'.
                            xPos = boardMaxWidth - xPos;
                            yPos = boardMaxHeight - yPos;
                        }
                        if (touchesGameBoard(xPos, yPos)) {
                            this.selectedX = ((int) (xPos - this.widthOffset) / this.dimension);
                            this.selectedY = ((int) (8.0F - (yPos - this.heightOffset) / this.dimension));
                            int code = this.myGame.click(this.selectedX, this.selectedY);
                            if (code == 1) {
                                STATE = DISPLAYING;
                                invalidate();
                            } else if (code == 2) {
                                // int returnCode = t
                                int returnCode = this.myGame.insinuateMove(this.selectedX, this.selectedY);
                                myGame.setInsurance(-1);
                                if(returnCode == 9) {
                                    promote = true;
                                }
                                STATE = CONFIRM;
                                messageToDisplay = "Confirm Move?";
                                invalidate();
                            } else if (code == 3) {
                                invalidate();
                                STATE = WW;
                            }
                        }
                        break;
                    case DEFENSE:
                    case PASS:
                    case DEFENSEDEFAULT:
                    case CONFIRM:
                        if(yPos > (currentHeight - buttonHeight))
                        {
                            //y position is correct.  See if its the yes or no
                            //or in between which doesnt count here.
                            if(xPos < buttonWidth)
                            {
                                clickNo();
                                STATE = DISPLAYING;
                            }
                            else if(xPos > (currentWidth - buttonWidth))
                            {
                                clickYes();
                            }
                        }
                        break;
                    case DEFENSEWAGER:
                        if(yPos > (currentHeight - buttonHeight))
                        {
                            //y position is correct.  See if its the yes or no
                            //or in between which doesnt count here.
                            if (xPos > token0 && xPos < (token0 + buttonHeight))
                            {
                                enactSkirmish(0);
                            }
                            else if (xPos > token2 && xPos < (token2 + buttonHeight) )
                            {
                                enactSkirmish(2);
                            }
                            else if (xPos > token1 && xPos < (token1 + buttonHeight) )
                            {
                                enactSkirmish(1);
                            }
                        }
                        break;
                    case WAGER:
                        if(yPos > (currentHeight - buttonHeight))
                        {
                            //y position is correct.  See if its the yes or no
                            //or in between which doesnt count here.
                            if (xPos > token0 && xPos < (token0 + buttonHeight))
                            {
                                confirmWager(0);
                            }
                            else if (xPos > token2 && xPos < (token2 + buttonHeight) )
                            {
                                confirmWager(2);
                            }
                            else if (xPos > token1 && xPos < (token1 + buttonHeight) )
                            {
                                confirmWager(1);
                            }
                        }

                        break;
                }
                invalidate();
                break;
        }
        return true;
    }

    public boolean touchesGameBoard(float xPos, float yPos)
    {
        if ((this.gameBR[0] > xPos) && (this.gameTL[0] < xPos) && (this.gameBR[1] > yPos) && (this.gameTL[1] < yPos)) {
            return true;
        }
        return false;
    }


//


    public void clickYes()
    {
        messageToDisplay = null;
        int[] originalSelected = myGame.getSelectedIndex();
        if(STATE.equals(CONFIRM) ) {
            if(TURN.equals(FIRST))
            {
                if(promote)
                {
                    //we had a promotion, we dont know what to send yet..but we can prepare
                    //move Holder.
                    moveHolder[0] = originalSelected[0];
                    moveHolder[1] = originalSelected[1];
                    moveHolder[2] = this.selectedX;
                    moveHolder[3] = this.selectedY;
                    AlertDialog.Builder builder = new AlertDialog.Builder(myActivity);
                    builder.setTitle("Promote to:").setItems(promoteList, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            promoteChar = promoteList[which].charAt(0);


                            //if we are a two kings variant, we cna send the move now.
                            if(myVariant != 'T') {
                                myActivity.sendMove(moveHolder, promoteChar);
                            }
                            else
                                invalidate();
                        }
                    });

                    AlertDialog promoteChoice = builder.create();
                    promoteChoice.setOnCancelListener(new DialogInterface.OnCancelListener() {

                        public void onCancel(DialogInterface dialog) {

                            STATE = CONFIRM;
                            clickNo();
                            STATE = DISPLAYING;
                        }
                    });
                    promoteChoice.show();

                }
                else{
                    if (myGame.getWWD()) {
                        moveHolder[0] = originalSelected[0];
                        moveHolder[1] = originalSelected[1];
                        moveHolder[2] = -1;
                        moveHolder[3] = -1;
                        myGame.setWWD(false);
                    }
                    else
                    {
                        moveHolder[0] = originalSelected[0];
                        moveHolder[1] = originalSelected[1];
                        moveHolder[2] = this.selectedX;
                        moveHolder[3] = this.selectedY;
                    }

                    if(myVariant != 'T')
                    {
                        //we are ready to send.
                        myActivity.sendMove(moveHolder,promoteChar);
                    }
                    else
                    {
                        //set to second move
                        TURN = SECOND;
                        STATE = DISPLAYING;
                        myGame.clearHighlight();
                        myGame.clearSelected();
                        myGame.setState("SECONDWAITING");
                        invalidate();
                    }
                }
            }
            else
            {
                if (myGame.getWWD()) {
                    moveHolder[4] = originalSelected[0];
                    moveHolder[5] = originalSelected[1];
                    moveHolder[6] = -1;
                    moveHolder[7] = -1;
                }
                else
                {
                    moveHolder[4] = originalSelected[0];
                    moveHolder[5] = originalSelected[1];
                    moveHolder[6] = this.selectedX;
                    moveHolder[7] = this.selectedY;
                }

                //we are definitely done now.

                myActivity.sendMove(moveHolder,promoteChar);
            }
        }
        else if(STATE.equals(DEFENSE))
        {
            //find out if enemy has any tokens.
            //if they dont, we dont get the option to choose our wager
            //we have to bet 1 and we immediately win, and it comes back to our turn.
            int enemyTokens = -1;
            if(myTeam == 'w')
                enemyTokens = myGame.getBlackTokens();
            else
                enemyTokens = myGame.getWhiteTokens();

            if(enemyTokens != 0) {
                STATE = DEFENSEWAGER;
                int myTokens, payment = 0;
                if (myGame.getPieceJustTaken().getContents().getTier() < myGame.getPieceJustMoved().getContents().getTier()) {
                    payment = 1;
                }
                if (myTeam == 'w') {
                    myTokens = myGame.getWhiteTokens();
                    myTokens = myTokens - payment;
                    myGame.setWhiteTokens(myTokens);
                } else {
                    myTokens = myGame.getBlackTokens();
                    myTokens = myTokens - payment;
                    myGame.setBlackTokens(myTokens);
                }
                myActivity.suppressToast();
                messageToDisplay = "What do you wager?";
                myGame.setPromptDefense(false);
                invalidate();
            }
            else
            {
                STATE = DEFENSEDEFAULT;
                myActivity.suppressToast();
                messageToDisplay = "Enemy Broke. Bid 1 to win?";
                int myTokens, payment = 0;
                if (myGame.getPieceJustTaken().getContents().getTier() < myGame.getPieceJustMoved().getContents().getTier()) {
                    payment = 1;
                }
                if (myTeam == 'w') {
                    myTokens = myGame.getWhiteTokens();
                    myTokens = myTokens - payment;
                    myGame.setWhiteTokens(myTokens);
                } else {
                    myTokens = myGame.getBlackTokens();
                    myTokens = myTokens - payment;
                    myGame.setBlackTokens(myTokens);
                }
                invalidate();
            }
        }
        else if (STATE.equals(PASS))
        {
            moveHolder[4] = 9;
            moveHolder[5] = 9;
            moveHolder[6] = 9;
            moveHolder[7] = 9;
            myActivity.sendMove(moveHolder, promoteChar);
            invalidate();
        }
        else if(STATE.equals(DEFENSEDEFAULT))
        {
            myActivity.sendSkirmish(1);
            invalidate();
        }
    }



    public void clickNo()
    {
        messageToDisplay = null;
        promote = false;
        if(STATE.equals(CONFIRM)) {
            myGame.undoInsinuate(ChessGameView.this.selectedX, ChessGameView.this.selectedY);
            myActivity.suppressToast();
            invalidate();
        }
        else if (STATE.equals(DEFENSE))
        {
            myGame.setPromptDefense(false);
            myActivity.suppressToast();
            myActivity.toast("Skirmish Forfeited");
            STATE = DISPLAYING;
            invalidate();
        }
        else if(STATE.equals(PASS))
        {
            STATE = DISPLAYING;
            myActivity.suppressToast();
            invalidate();
        }
        else if(STATE.equals(DEFENSEDEFAULT))
        {
            STATE = DISPLAYING;
            if (myGame.getPieceJustTaken().getContents().getTier() < myGame.getPieceJustMoved().getContents().getTier()) {
                if (myTeam == 'w') {
                    //increment it back up by 1 to offset the 1 that was subtracted.
                    myGame.setWhiteTokens(myGame.getWhiteTokens() + 1);
                }
                else {
                    myGame.setBlackTokens(myGame.getBlackTokens() + 1);
                }

            }
            invalidate();
        }
    }

    public void clickWW()
    {
        myGame.whirlwind();
        invalidate();
    }

    public void clickPass()
    {
        STATE = PASS;
        //erase the contents of move.

        invalidate();
    }



    public void setActivity(PlayGameActivity playGameActivity)
    {
        this.myActivity = playGameActivity;
    }

    public void enactSkirmish(int wager)
    {
        //check that i didnt try to duel with more tokens than i have.
        int myTokens = 0;
        if(myTeam == 'w')
        {
            myTokens = myGame.getWhiteTokens();
        }
        else
            myTokens = myGame.getBlackTokens();

        if(wager > myTokens)
        {
            myActivity.toast("Not Enough Tokens");
            invalidate();
        }
        else
        {
            myActivity.sendSkirmish(wager);
            STATE = DISPLAYING;
            invalidate();
        }
    }

    public void confirmWager(int wager)
    {
        messageToDisplay = null;
        int myTokens;
        if(myTeam == 'w')
            myTokens = myGame.getWhiteTokens();
        else
            myTokens = myGame.getBlackTokens();

        if(wager <= myTokens) {
            myActivity.finishSkirmish(wager);
        }
        else
        {
            myActivity.suppressToast();
            myActivity.toast("Not Enough Tokens");
            invalidate();
        }


    }

    public void forceInvalidate()
    {
        firstPass = true;
        STATE = DISPLAYING;
        TURN = FIRST;
        invalidate();
    }

    public void setMessage(String message)
    {
        this.messageToDisplay = message;


    }

}
