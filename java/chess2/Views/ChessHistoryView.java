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

import chess2.Activities.GameHistoryActivity;
import chess2.source.GameHistoryContainer;
import chess2.source.PieceInf;

@SuppressLint({"ClickableViewAccessibility"})
public class ChessHistoryView
        extends View {



    private String myName = "";
    private char myTeam = ' ';
    private double percentHeight = 0.55D;
    private double percentButtons = 0.1D;
    private double percentInfo = .25D;
    private double percentMessage = .1D; //represents the height of the messages.
    private int widthOffset;
    private int heightOffset;
    private int dimension;
    private int boardMaxHeight;
    private int boardMaxWidth;
    private int buttonHeight;
    private int buttonWidth;
    private int messageHeight;
    private int currentHeight;
    private int currentWidth;
    private int[] gameTL;
    private int[] gameBR;
    private Canvas theCanvas = null;
    private Bitmap oddSquare,evenSquare, moveSquare, fromSquare;
    private Bitmap whiteP, blackP, whiteR, blackR, whiteK, blackK, whiteB, blackB, whiteQ, blackQ, whiteX, blackX;
    private Bitmap curOS, curES, curMS, curFS, curWP, curBP, curWR, curBR, curWK, curBK, curWB, curBB, curWQ, curBQ, curWX, curBX;
    private Bitmap prevUp,prevDown, nextUp,nextDown, curPrevUp, curPrevDown, curNextUp, curNextDown,token, curToken;
    private GameHistoryContainer myHistory = null;
    private GameHistoryActivity myActivity;
    private boolean prevButtonDown, nextButtonDown;
    private int curIndex;
    private String messageToDisplay = null;

    //region Bitmap stuff
    public ChessHistoryView(Context context) {
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
        this.prevUp =  BitmapFactory.decodeResource(getResources(), R.drawable.prevup);
        this.prevDown =  BitmapFactory.decodeResource(getResources(), R.drawable.prevdown);
        this.nextUp = BitmapFactory.decodeResource(getResources(), R.drawable.nextup);
        this.nextDown = BitmapFactory.decodeResource(getResources(), R.drawable.nextdown);
        this.token = BitmapFactory.decodeResource(getResources(), R.drawable.token);
        curIndex = 0;
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
            this.curPrevUp = Bitmap.createScaledBitmap(this.prevUp, this.buttonWidth,this.buttonHeight , true);
            this.curPrevDown = Bitmap.createScaledBitmap(this.prevDown, this.buttonWidth,this.buttonHeight , true);
            this.curNextUp = Bitmap.createScaledBitmap(this.nextUp, this.buttonWidth,this.buttonHeight , true);
            this.curNextDown = Bitmap.createScaledBitmap(this.nextDown, this.buttonWidth,this.buttonHeight , true);
            this.curToken = Bitmap.createScaledBitmap(this.token, this.currentWidth / 18, this.currentWidth / 18, true);

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
            this.curPrevUp.recycle();
            this.curPrevDown.recycle();
            this.curNextUp.recycle();
            this.curNextDown.recycle();
            this.curToken.recycle();


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
            this.curPrevUp = Bitmap.createScaledBitmap(this.prevUp, this.buttonWidth,this.buttonHeight , true);
            this.curPrevDown = Bitmap.createScaledBitmap(this.prevDown, this.buttonWidth,this.buttonHeight , true);
            this.curNextUp = Bitmap.createScaledBitmap(this.nextUp, this.buttonWidth,this.buttonHeight , true);
            this.curNextDown = Bitmap.createScaledBitmap(this.nextDown, this.buttonWidth,this.buttonHeight , true);
            this.curToken = Bitmap.createScaledBitmap(this.token, this.currentWidth / 18, this.currentWidth / 18, true);

        }
    }


    //endregion


    protected void onDraw(Canvas canvas) {
        this.theCanvas = canvas;
        drawBoard(canvas);
        //drawUserInfo(canvas);
        drawButtons(canvas);
        drawUserInfo(canvas);

    }

    @SuppressLint({"WrongCall"})
    public boolean onTouchEvent(MotionEvent me)
    {
        super.onTouchEvent(me);
        performClick();
        float xPos = me.getX();
        float yPos = me.getY();

        int action = me.getAction();
        switch (action)
        {
            case 0:
                if(yPos > (currentHeight - buttonHeight))
                {
                    //y position is correct.  See if its the yes or no
                    //or in between which doesnt count here.
                    if(xPos < buttonWidth)
                    {
                        prevButtonDown = true;
                    }
                    else if(xPos > (currentWidth - buttonWidth))
                    {
                        nextButtonDown = true;
                    }
                }
                invalidate();
                break;


            case 1:

                prevButtonDown = nextButtonDown = false;
                if(yPos > (currentHeight - buttonHeight))
                {
                    //y position is correct.  See if its the yes or no
                    //or in between which doesnt count here.
                    if(xPos < buttonWidth)
                    {
                        clickPrev();
                    }
                    else if(xPos > (currentWidth - buttonWidth))
                    {
                        clickNext();
                    }

                }
                invalidate();
                break;
        }
        return true;
    }

    public void clickPrev()
    {
        if(this.curIndex >0)
            curIndex--;
        else{
            myActivity.toast("No more");
        }

        invalidate();
    }


    public void clickNext()
    {
        if(this.curIndex < myHistory.getLength()-1)
            curIndex++;
        else{
            myActivity.toast("No more");
        }
        invalidate();
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


    public void setActivity(GameHistoryActivity myActivity)
    {
        this.myActivity = myActivity;
    }

    public GameHistoryContainer getMyHistory() {
        return myHistory;
    }

    public void setMyHistory(GameHistoryContainer myHistory) {
        this.myHistory = myHistory;
        curIndex = myHistory.getLength() -1;
    }

    public void drawBoard(Canvas canvas)
    {


        if(myName.equals(myHistory.getBlack())) {
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
                    if (myHistory.getPoint(curIndex)[(7 - i)][j].getHighlight()) {
                        canvas.drawBitmap(this.curMS, xPos, yPos, null);
                    } else if (myHistory.getPoint(curIndex)[(7 - i)][j].getLastMove()) {
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

                    if (myHistory.getPoint(curIndex)[(i)][7-j].getHighlight()) {
                        canvas.drawBitmap(this.curMS, xPos, yPos, null);
                    } else if (myHistory.getPoint(curIndex)[(i)][7-j].getLastMove()) {
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
        switch (myHistory.getPoint(curIndex)[(7 - i)][j].getContents().getContents())
        {
            case 'p':
                if (myHistory.getPoint(curIndex)[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWP;
                } else {
                    pieceToDraw = this.curBP;
                }
                break;
            case 'r':
                if (myHistory.getPoint(curIndex)[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWR;
                } else {
                    pieceToDraw = this.curBR;
                }
                break;
            case 'k':
                if (myHistory.getPoint(curIndex)[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWK;
                } else {
                    pieceToDraw = this.curBK;
                }
                break;
            case 'b':
                if (myHistory.getPoint(curIndex)[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWB;
                } else {
                    pieceToDraw = this.curBB;
                }
                break;
            case 'q':
                if (myHistory.getPoint(curIndex)[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWQ;
                } else {
                    pieceToDraw = this.curBQ;
                }
                break;
            case 'x':
                if (myHistory.getPoint(curIndex)[(7 - i)][j].getContents().getTeam() == 'w') {
                    pieceToDraw = this.curWX;
                } else {
                    pieceToDraw = this.curBX;
                }
                break;
        }
        if (myHistory.getPoint(curIndex)[(7 - i)][j].getSelected())
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

    public void drawButtons(Canvas canvas)
    {
        if(!prevButtonDown)
            canvas.drawBitmap(this.curPrevUp, 0, (currentHeight - buttonHeight), null);
        else
            canvas.drawBitmap(this.curPrevDown, 0, (currentHeight - buttonHeight), null);

        if(!nextButtonDown)
            canvas.drawBitmap(this.curNextUp, (2*(currentWidth/3)), (currentHeight - buttonHeight), null);
        else
            canvas.drawBitmap(this.curNextDown, (2*(currentWidth/3)), (currentHeight - buttonHeight), null);
    }

    public void drawUserInfo(Canvas canvas)
    {
        if(currentHeight > currentWidth) {
            Paint brush = new Paint();
            brush.setTextSize((float)(currentHeight*.04));
            Rect bounds = new Rect();
            int width = 0;
            int token = 0;
            char myVar = ' ';
            char theirVar = ' ';
            if(myName.equals(myHistory.getArray()[curIndex].getWhiteTeam()))
            {
                myVar = myHistory.getArray()[curIndex].getWhiteVar();
                theirVar = myHistory.getArray()[curIndex].getBlackVar();
            }
            else
            {
                myVar = myHistory.getArray()[curIndex].getWhiteVar();
                theirVar= myHistory.getArray()[curIndex].getBlackVar();
            }

            String myVariant = "", theirVariant = "";
            switch (myVar)
            {
                case 'C':
                    myVariant = "Classic";
                    break;
                case 'N':
                    myVariant = "Nemesis";
                    break;
                case 'R':
                    myVariant = "Reapers";
                    break;
                case 'E':
                    myVariant = "Empowered";
                    break;
                case 'A':
                    myVariant = "Animals";
                    break;
                case 'T':
                    myVariant = "Two-Kings";
                    break;
            }

            switch (theirVar)
            {
                case 'C':
                    theirVariant = "Classic";
                    break;
                case 'N':
                    theirVariant = "Nemesis";
                    break;
                case 'R':
                    theirVariant = "Reapers";
                    break;
                case 'E':
                    theirVariant = "Empowered";
                    break;
                case 'A':
                    theirVariant = "Animals";
                    break;
                case 'T':
                    theirVariant = "Two-Kings";
                    break;
            }

            //my info goes on top on the left.
            double start = (currentHeight * percentHeight + (currentHeight * .05));
            double topOfUser = start;
            double temp = (currentHeight - (currentHeight *.1));
            canvas.drawText(myName, 5, (float) start, brush);

            canvas.drawText(myVariant,5,(float) (start + (currentHeight*.05)),brush);
            //canvas.drawText(myTeam, 5, (float) (start + (currentHeight*.1)), brush);
            if (myName.equals(myHistory.getWhite())) {
                token = myHistory.getArray()[curIndex].getWhiteToken();
            } else
                token = myHistory.getArray()[curIndex].getBlackToken();

            int currentX = 0;
            for (int i = 0; i < token; i++) {
                canvas.drawBitmap(this.curToken,(float)currentX, (float)(start + (currentHeight*.06)), null);
                currentX = currentX + this.curToken.getWidth();
            }

            //draw missing pieces
            int pieceXLoc =0,  pieceYLoc = 0, count = 1;
            pieceXLoc =(int) -(dimension*.1);
            pieceYLoc =  (int) (start + (currentHeight*.06)) + curToken.getHeight();
            PieceInf[] capturedPieces = myHistory.getArray()[curIndex].getCapturedPieces();

            if(capturedPieces != null) {
                for (int i = 0; i < capturedPieces.length; i++) {
                    if (myTeam == 'w') {
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



            String theirName = null;
            if (myName.equals(myHistory.getWhite())) {
                theirName = myHistory.getBlack();
                token = myHistory.getArray()[curIndex].getBlackToken();
            } else {
                theirName = myHistory.getWhite();
                token = myHistory.getArray()[curIndex].getWhiteToken();
            }
            //their info goes on bottom right
            start = currentHeight - buttonHeight - messageHeight + brush.getTextSize()*.5;

            topOfUser = topOfUser + ((start-topOfUser)/2);
            brush.getTextBounds(theirName, 0, theirName.length(), bounds);
            canvas.drawText(theirName,(currentWidth - bounds.width() - 15),(float) (start),brush );
            brush.getTextBounds(theirVariant,0,theirVariant.length(),bounds);
            canvas.drawText(theirVariant,(currentWidth - bounds.width() - 15),(float) (start - (currentHeight*.05) ), brush);

            currentX = currentWidth - this.curToken.getWidth();


            for (int i = 0; i < token; i++) {
                canvas.drawBitmap(this.curToken, (float)currentX, (float)(start - currentHeight*.13), null);
                currentX = currentX - this.curToken.getWidth();
            }

            pieceXLoc =(int) (currentWidth + (dimension*.1) - dimension);
            pieceYLoc =  (int) ((start - currentHeight*.13) - dimension);

            count = 1;
            if(capturedPieces != null) {
                for (int i = 0; i < capturedPieces.length; i++) {
                    if (myTeam == 'w') {
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


        }



    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }
}