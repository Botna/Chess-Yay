package chess2.source;

import java.io.Serializable;

/**
 * Created by Botna on 3/16/2015.
 */
public class GameHistoryContainer implements Serializable {

    private GamePoint[] myGamePoints;
    private int length;



    public GameHistoryContainer(GameBoard theGame)
    {
        myGamePoints = new GamePoint[1];
        myGamePoints[0] = new GamePoint();
        myGamePoints[0].pushTurn(theGame);
        length = 1;
    }
    

    public void pushTurn(GameBoard theGame)
    {
        //create a new GamePoint array 1 bigger, copy, and push in a the new one

        GamePoint[] temp = new GamePoint[length+1];

        for(int i = 0; i< length; i++)
        {
            temp[i] = myGamePoints[i];
        }
        temp[length] = new GamePoint();
        temp[length].pushTurn(theGame);
        myGamePoints = temp;
        length++;
    }


    public void pushChallenge(GameBoard theGame)
    {
        //create a new GamePoint array 1 bigger, copy, and push in a the new one

        GamePoint[] temp = new GamePoint[length+1];

        for(int i = 0; i< length; i++)
        {
            temp[i] = myGamePoints[i];
        }
        temp[length] = new GamePoint();
        temp[length].pushChallenge(theGame);
        myGamePoints = temp;
        length++;

    }


    public SingleSpace[][] getPoint(int index)
    {
       return myGamePoints[index].getBoard();
    }
    public GamePoint[] getArray()
    {
        return myGamePoints;
    }

    public int getLength()
    {
        return myGamePoints.length;
    }

    public String getBlack()
    {
        if( myGamePoints != null)
            return myGamePoints[0].getBlackTeam();
        else
            return "";
    }

    public String getWhite()
    {
        if( myGamePoints != null)
            return myGamePoints[0].getWhiteTeam();
        else
            return "";

    }

}
