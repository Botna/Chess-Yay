package chess2.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.botna.chess2.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import chess2.Activities.GameHistoryActivity;
import chess2.source.GameBoard;
import chess2.source.GameHistoryContainer;
import chess2.source.PieceInf;
import chess2.source.SingleSpace;

/**
 * Created by Botna on 4/1/2015.
 */
public class SimulationView extends View {


    GameBoard myGame = null;

    GameBoard gameBackup = null;



    private final static String DEFENSE = "DEFENSE";
    private final static String PASS = "PASS";
    private final static String WW = "WHIRLWIND";
    private final static String DISPLAYING = "DISPLAYING";
    private final static String SELECTED = "SELECTED";
    private final static String FIRST = "FIRST";
    private final static String SECOND = "SECOND";
    private String STATE = null;
    private String TURN = FIRST;
    private String myName = "";
    private char myTeam = ' ';
    private double percentHeight = 0.55D;
    private double percentButtons = 0.1D;
    private double percentInfo = .35D;
    private double percentMessage = .1D; //represents the height of the messages.
    private boolean displayKillButton;
    private boolean displayWWButton;
    private int widthOffset;
    private int heightOffset;
    private int dimension;
    private int messageHeight;
    private int boardMaxHeight;
    private int boardMaxWidth;
    private int buttonHeight;
    private int buttonWidth;
    private int currentHeight;
    private int currentWidth;
    private int selectedX = 0;
    private int selectedY = 0;
    private int prevSelectedX = 0;
    private int prevSelectedY = 0;
    private int[] gameTL;
    private int[] gameBR;
    private Canvas theCanvas = null;
    private Bitmap oddSquare,evenSquare, moveSquare, fromSquare;
    private Bitmap whiteP, blackP, whiteR, blackR, whiteK, blackK, whiteB, blackB, whiteQ, blackQ, whiteX, blackX;
    private Bitmap curOS, curES, curMS, curFS, curWP, curBP, curWR, curBR, curWK, curBK, curWB, curBB, curWQ, curBQ, curWX, curBX;
    private Bitmap resetUp, resetDown, curResetUp,curResetDown, passUp, curPassUp, passDown, curPassDown;
    private Bitmap turnArrowRight, curArrowRight, turnArrowLeft, curArrowLeft;
    private Bitmap token, curToken;
    private Bitmap whirlWindUp, whirlWindDown, curWWUp, curWWDown;
    private Bitmap killUp, curKillUp, killDown, curKillDown;
    private int curIndex;
    private boolean passButtonDown, killButtonDown, wwButtonDown, resetButtonDown;


    //region Constructor and Bitmap stuff


    public SimulationView(Context context) {
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
        this.resetUp =  BitmapFactory.decodeResource(getResources(), R.drawable.resetup);
        this.resetDown =  BitmapFactory.decodeResource(getResources(), R.drawable.resetdown);
        this.token = BitmapFactory.decodeResource(getResources(), R.drawable.token);
        this.killUp = BitmapFactory.decodeResource(getResources(), R.drawable.killup);
        this.killDown = BitmapFactory.decodeResource(getResources(), R.drawable.killdown);
        this.whirlWindUp = BitmapFactory.decodeResource(getResources(), R.drawable.wwbuttonup);
        this.whirlWindDown = BitmapFactory.decodeResource(getResources(), R.drawable.wwbuttondown);
        this.passUp = BitmapFactory.decodeResource(getResources(), R.drawable.passup);
        this.passDown = BitmapFactory.decodeResource(getResources(), R.drawable.passdown);
        this.turnArrowRight = BitmapFactory.decodeResource(getResources(), R.drawable.turn_arrow_right);
        this.turnArrowLeft = BitmapFactory.decodeResource(getResources(), R.drawable.turn_arrow_left);
        STATE = DISPLAYING;
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
            this.curResetUp= Bitmap.createScaledBitmap(this.resetUp, this.buttonWidth,this.buttonHeight , true);
            this.curResetDown = Bitmap.createScaledBitmap(this.resetDown, this.buttonWidth,this.buttonHeight , true);
            this.curToken = Bitmap.createScaledBitmap(this.token, this.currentWidth / 18, this.currentWidth / 18, true);
            this.curKillUp = Bitmap.createScaledBitmap(this.killUp, this.buttonWidth,this.buttonHeight , true);
            this.curKillDown = Bitmap.createScaledBitmap(this.killDown, this.buttonWidth,this.buttonHeight , true);
            this.curWWUp =  Bitmap.createScaledBitmap(this.whirlWindUp, this.buttonHeight,this.buttonHeight , true);
            this.curWWDown =  Bitmap.createScaledBitmap(this.whirlWindDown, this.buttonHeight,this.buttonHeight , true);
            this.curPassUp = Bitmap.createScaledBitmap(this.passUp, this.buttonWidth,this.buttonHeight , true);
            this.curPassDown = Bitmap.createScaledBitmap(this.passDown, this.buttonWidth,this.buttonHeight , true);
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
            this.curToken.recycle();
            this.curResetUp.recycle();
            this.curResetDown.recycle();
            this.curKillUp.recycle();
            this.curKillDown.recycle();
            this.curWWUp.recycle();
            this.curWWDown.recycle();
            this.curPassUp.recycle();
            this.curPassDown.recycle();
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
            this.curResetUp= Bitmap.createScaledBitmap(this.resetUp, this.buttonWidth,this.buttonHeight , true);
            this.curResetDown = Bitmap.createScaledBitmap(this.resetDown, this.buttonWidth,this.buttonHeight , true);
            this.curToken = Bitmap.createScaledBitmap(this.token, this.currentWidth / 18, this.currentWidth / 18, true);
            this.curKillUp = Bitmap.createScaledBitmap(this.killUp, this.buttonWidth,this.buttonHeight , true);
            this.curKillDown = Bitmap.createScaledBitmap(this.killDown, this.buttonWidth,this.buttonHeight , true);
            this.curWWUp =  Bitmap.createScaledBitmap(this.whirlWindUp, this.buttonHeight,this.buttonHeight , true);
            this.curWWDown =  Bitmap.createScaledBitmap(this.whirlWindDown, this.buttonHeight,this.buttonHeight , true);
            this.curPassUp = Bitmap.createScaledBitmap(this.passUp, this.buttonWidth,this.buttonHeight , true);
            this.curPassDown = Bitmap.createScaledBitmap(this.passDown, this.buttonWidth,this.buttonHeight , true);
            this.curArrowRight = Bitmap.createScaledBitmap(this.turnArrowRight,  (int)(this.buttonHeight*.75),(int)(this.buttonHeight*.75)  , true);
            this.curArrowLeft = Bitmap.createScaledBitmap(this.turnArrowLeft, (int)(this.buttonHeight*.75),(int)(this.buttonHeight*.75)  , true);
        }
    }


//endregion

    //region Drawing Code
    protected void onDraw(Canvas canvas) {
        myGame.clearLastMove();
        this.theCanvas = canvas;
        drawBoard(canvas);
        drawUserInfo(canvas);

        if(!resetButtonDown)
            canvas.drawBitmap(this.curResetUp, 0, (currentHeight -(buttonHeight)), null);
        else
            canvas.drawBitmap(this.curResetDown, 0, (currentHeight -(buttonHeight)), null);

        if(TURN.equals(SECOND))
        {

            if(!passButtonDown)
                canvas.drawBitmap(this.curPassUp, (currentWidth/3) * 2, (currentHeight -(buttonHeight)), null);
            else
                canvas.drawBitmap(this.curPassDown, (currentWidth/3) * 2, (currentHeight -(buttonHeight)), null);
        }
        if(STATE.equals(SELECTED) && displayKillButton)
        {
            if(!killButtonDown)
                canvas.drawBitmap(this.curKillUp, (currentWidth/3) * 2, (currentHeight -(buttonHeight)), null);
            else
                canvas.drawBitmap(this.curKillDown, (currentWidth/3) * 2, (currentHeight -(buttonHeight)), null);
        }
        if(STATE.equals(SELECTED) && displayWWButton)
        {
            if(myGame.canWhirlWind()) {


                if(!wwButtonDown)
                    canvas.drawBitmap(this.curWWUp, ((currentWidth/2) - (buttonHeight/2)), (currentHeight - (buttonHeight)), null);
                else
                    canvas.drawBitmap(this.curWWDown, ((currentWidth/2) - (buttonHeight/2)), (currentHeight - (buttonHeight)), null);
            }

        }


    }

    public void drawBoard(Canvas canvas)
    {


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
                    if (myGame.getTheGame()[(7 - i)][j].getHighlight()) {
                        canvas.drawBitmap(this.curMS, xPos, yPos, null);
                    } else if (myGame.getTheGame()[(7 - i)][j].getLastMove()) {
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

                    if (myGame.getTheGame()[(i)][7-j].getHighlight()) {
                        canvas.drawBitmap(this.curMS, xPos, yPos, null);
                    } else if (myGame.getTheGame()[(i)][7-j].getLastMove()) {
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
        switch (myGame.getTheGame()[(7 - i)][j].getContents().getContents())
        {
            case 'p':
                if (myGame.getTheGame()[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWP;
                } else {
                    pieceToDraw = this.curBP;
                }
                break;
            case 'r':
                if (myGame.getTheGame()[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWR;
                } else {
                    pieceToDraw = this.curBR;
                }
                break;
            case 'k':
                if (myGame.getTheGame()[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWK;
                } else {
                    pieceToDraw = this.curBK;
                }
                break;
            case 'b':
                if (myGame.getTheGame()[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWB;
                } else {
                    pieceToDraw = this.curBB;
                }
                break;
            case 'q':
                if (myGame.getTheGame()[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWQ;
                } else {
                    pieceToDraw = this.curBQ;
                }
                break;
            case 'x':
                if (myGame.getTheGame()[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWX;
                } else {
                    pieceToDraw = this.curBX;
                }
                break;
        }
        if (myGame.getTheGame()[(7 - i)][j].getSelected())
        {
            int temp = (int)(this.dimension * 1.5D);
            pieceToDraw = Bitmap.createScaledBitmap(pieceToDraw, temp, temp, true);
            xPos -= this.dimension / 4;
            yPos -= this.dimension / 4;
        }
        if (pieceToDraw != null) {
            this.theCanvas.drawBitmap(pieceToDraw, xPos, yPos, null);
        }

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
                canvas.drawBitmap(this.curToken, (float)currentX, (float)(start + (currentHeight*.06)), null);
                currentX = currentX + this.curToken.getWidth();
            }

            //draw missing pieces
            int pieceXLoc =0,  pieceYLoc = 0, count = 1;
            pieceXLoc =(int) -(dimension*.1);
            pieceYLoc =  (int) (start + (currentHeight*.06)) + curToken.getHeight();
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

            currentX = currentWidth - this.curToken.getWidth();
            if(theirTeam.equals("White"))
            {
                token = myGame.getWhiteTokens();
            }
            else
                token = myGame.getBlackTokens();

            for(int i = 0; i<token; i++)
            {
                canvas.drawBitmap(this.curToken, (float)currentX, (float)(start - currentHeight*.13), null);
                currentX = currentX - this.curToken.getWidth();
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



    //endregion


    @SuppressLint({"WrongCall"})
    public boolean onTouchEvent(MotionEvent me)
    {
        super.onTouchEvent(me);
        performClick();

        float xPos = me.getX();
        float yPos = me.getY();
        int action = me.getAction();
        switch (action) {
            case 0:

                if (TURN.equals(SECOND)) {
                    //check for PASS button.
                    if (yPos > (currentHeight - buttonHeight)) {
                        if (xPos > (currentWidth - buttonWidth)) {
                            //Pass was clicked.
                            passButtonDown = true;
                        }
                    }
                }


                switch (STATE) {

                    case SELECTED:
                        if (displayWWButton) {
                            //turn == second.  This means we ahve selected a king on our two kings variant, and need to
                            //check for whirlwind.

                            // check to see if WW button was pressed. If so, we break, but if not
                            if (yPos > (currentHeight - buttonHeight)) {
                                if ((xPos > ((currentWidth/2) - (buttonHeight/2)) &&  (xPos < ((currentWidth/2) + (buttonHeight/2))))) {
                                    //check that its my turn and that the piece is actually mine.
                                    wwButtonDown = true;
                                }
                            }
                        }

                }
                if (displayKillButton) {
                    if (yPos > (currentHeight - buttonHeight)) {
                        if (xPos > 0 && xPos < (currentWidth / 3)) {
                            //reset button.
                            resetButtonDown = true;
                        } else if (xPos > 2 * (currentWidth / 3)) {
                            killButtonDown = true;
                        }

                    }
                }

                if ((xPos > 0 && xPos < (currentWidth / 3)) && yPos > (currentHeight - buttonHeight)) {
                    //reset button.
                    resetButtonDown = true;
                }
                invalidate();
                break;
            case 1:


                passButtonDown = false;
                killButtonDown = false;
                wwButtonDown = false;
                resetButtonDown = false;

                if(TURN.equals(SECOND))
                {
                    //check for PASS button.
                    if(yPos>(currentHeight-buttonHeight))
                    {
                        if(xPos > (currentWidth - buttonWidth))
                        {
                            //Pass was clicked.
                            this.myGame.clearSelected();
                            this.myGame.clearHighlight();
                            this.myGame.switchTurn();
                            this.myGame.determineTurn();
                            this.myGame.setState("WAITING");
                            displayKillButton = false;
                            STATE = DISPLAYING;
                            TURN = FIRST;
                            invalidate();
                        }
                    }
                }

                switch (STATE) {
                    case DISPLAYING :


                        if (touchesGameBoard(xPos, yPos)) {

                            if(myTeam == 'b')
                            {
                                //the board has been rotated.   we need to flip the location of the 'click'.
                                xPos = boardMaxWidth - xPos;
                                yPos = boardMaxHeight - yPos;
                            }



                            this.selectedX = ((int) (xPos - this.widthOffset) / this.dimension);
                            this.selectedY = ((int) (8.0F - (yPos - this.heightOffset) / this.dimension));


                            int code = this.myGame.simulateClick(this.selectedX, this.selectedY);
                            if (code == 1)
                            {
                                STATE = SELECTED;
                                if(myGame.getTheGame()[selectedY][selectedX].getContents().getContents() == 'x')
                                    displayKillButton = false;
                                else
                                    displayKillButton = true;

                                displayWWButton = false;
                                prevSelectedX = this.selectedX;
                                prevSelectedY = this.selectedY;
                                invalidate();

                            } else if (code == 2) {
//                                IMPOSSIBRU
//                                this.myGame.insinuateMove(this.selectedX, this.selectedY);
//                                this.myGame.getTheGame()[selectedY][selectedX].getContents().setHasMoved(true);
//                                this.myGame.clearSelected();
//                                this.myGame.switchTurn();
//                                this.myGame.determineTurn();
//                                displayWWButton = false;
//                                STATE = DISPLAYING;
//
//                                invalidate();


                            } else if (code == 3) {
                                displayKillButton = false;
                                displayWWButton = true;
                                prevSelectedX = this.selectedX;
                                prevSelectedY = this.selectedY;
                                invalidate();
                                STATE = SELECTED;
                            }
                            else if (code == 0)
                            {
                                STATE = DISPLAYING;
                                displayWWButton = false;
                                invalidate();
                            }
                            else
                            {
                                //defaulted, dont do shit.
                                invalidate();
                            }

                        }
                        else
                        {
                            if((xPos >0 && xPos < (currentWidth/3))&& yPos >(currentHeight - buttonHeight) )
                            {
                                //reset button.

                                reset();
                            }
                        }

                        break;

                    case SELECTED:
                        if (displayWWButton)
                        {
                            //turn == second.  This means we ahve selected a king on our two kings variant, and need to
                            //check for whirlwind.

                            // check to see if WW button was pressed. If so, we break, but if not
                            if(yPos > (currentHeight - buttonHeight))
                            {
                                //y position is correct.  See if its the yes or no
                                //or in between which doesnt count here.
                                char team = ' ';
                                if(myGame.getWhoseTurn().equals(myGame.getBlackTeam()))
                                {
                                    team = 'b';
                                }
                                else
                                    team = 'w';

                                if((xPos > ((currentWidth/2) - (buttonHeight/2)) &&  (xPos < ((currentWidth/2) + (buttonHeight/2)))))
                                {
                                    //check that its my turn and that the piece is actually mine.

                                    int[] temp = myGame.getSelectedIndex();
                                    if (myGame.getTheGame()[temp[1]][temp[0]].getContents().getTeam() == team) {
                                        //clickWW();
                                        if(myGame.canWhirlWind()) {


                                            try {
                                                this.myGame.makeMove(prevSelectedX, prevSelectedY, -1,-1);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            //myGame.whirlwind();
                                            this.myGame.clearSelected();

                                            if (TURN == SECOND){
                                                this.myGame.switchTurn();
                                                this.myGame.determineTurn();
                                                this.myGame.setState("WAITING");
                                                TURN = FIRST;
                                            }
                                            else
                                            {
                                                TURN = SECOND;
                                                this.myGame.setState("SECONDWAITING");
                                            }
                                            displayKillButton = false;
                                            STATE = DISPLAYING;

                                            invalidate();
                                        }
                                    }
                                }
                            }
                        }

                        if (touchesGameBoard(xPos, yPos)) {

                            if (myTeam == 'b') {
                                //the board has been rotated.   we need to flip the location of the 'click'.

                                xPos = boardMaxWidth - xPos;
                                yPos = boardMaxHeight - yPos;
                            }


                            this.selectedX = ((int) (xPos - this.widthOffset) / this.dimension);
                            this.selectedY = ((int) (8.0F - (yPos - this.heightOffset) / this.dimension));


                            int code = this.myGame.simulateClick(this.selectedX, this.selectedY);
                            if (code == 1) {
                                if(myGame.getTheGame()[selectedY][selectedX].getContents().getContents() == 'x')
                                    displayKillButton = false;
                                else
                                    displayKillButton = true;

                                displayWWButton = false;
                                STATE = SELECTED;
                                prevSelectedX = this.selectedX;
                                prevSelectedY = this.selectedY;
                                invalidate();
                            } else if (code == 2) {
                                // int returnCode = t
                                //int returnCode =
                                try {
                                    this.myGame.makeMove(prevSelectedX, prevSelectedY, selectedX, selectedY);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                               // this.myGame.insinuateMove(this.selectedX, this.selectedY);
                                this.myGame.getTheGame()[selectedY][selectedX].getContents().setHasMoved(true);
                                this.myGame.clearSelected();
                                this.myGame.switchTurn();
                                this.myGame.determineTurn();
                                displayKillButton = false;
                                displayWWButton = false;
                                STATE = DISPLAYING;
                                TURN = FIRST;

                                invalidate();


                            } else if (code == 3) {
                                displayKillButton = false;
                                displayWWButton = true;
                                STATE = SELECTED;
                                prevSelectedX = this.selectedX;
                                prevSelectedY = this.selectedY;
                                invalidate();
                            } else if (code == 0) {
                                displayKillButton = false;
                                displayWWButton = false;
                                STATE = DISPLAYING;
                                invalidate();
                            } else if (code == 4) {
                                TURN = SECOND;
                                STATE = DISPLAYING;
                                displayWWButton = false;
                                //this.myGame.insinuateMove(this.selectedX, this.selectedY);
                                try {
                                    this.myGame.makeMove(prevSelectedX, prevSelectedY, selectedX, selectedY);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                this.myGame.clearSelected();
                                invalidate();
                            }

                        } else if(displayKillButton)
                        {
                            if (yPos > (currentHeight - buttonHeight)) {
                                if (xPos > 2 * (currentWidth / 3)) {
                                    kill();
                                }

                            }
                        }

                        if (yPos > (currentHeight - buttonHeight)) {

                            if (xPos > 0 && xPos < (currentWidth / 3)) {
                                //reset button.
                                reset();
                            }
                        }

                        break;
                }
                invalidate();
                break;
        }
        return true;
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




    //region Button Presses

    public void reset()
    {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos;

            oos = new ObjectOutputStream(baos);

            oos.writeObject(gameBackup);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            this.myGame = (GameBoard) ois.readObject();
            this.STATE = DISPLAYING;
            this.TURN  = FIRST;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        invalidate();



    }


    public void kill()
    {
        myGame.deleteSelectedPiece();
        myGame.clearHighlight();
        STATE = DISPLAYING;
        invalidate();
    }

    //endregion

    //region Sets and Gets
    public void setMyGame(GameBoard myGame) {
        if(this.myGame == null) {
            //first time doing this, clone a backup for the 'reset' option.
            this.myGame = myGame;

            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos;

                oos = new ObjectOutputStream(baos);

                oos.writeObject(myGame);

                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                ObjectInputStream ois = new ObjectInputStream(bais);
                this.gameBackup = (GameBoard) ois.readObject();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }
    }


    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public boolean touchesGameBoard(float xPos, float yPos)
    {
        if ((this.gameBR[0] > xPos) && (this.gameTL[0] < xPos) && (this.gameBR[1] > yPos) && (this.gameTL[1] < yPos)) {
            return true;
        }
        return false;
    }
    //endregion

}
