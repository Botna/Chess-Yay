package serverClasses;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import chess2.source.GameBoard;
import chess2.source.GameContainer;
import android.os.Bundle;



public class ChessClass 
{

	public ChessClass() throws Exception
	{
		this.theClient = new ServerClient();


        if(!theClient.isAlive())
        {
            throw new Exception("Cant Connect");
        }
	}


    public String createNewGame(Bundle b) throws Exception {
        String packet = "";
        String response  = "";
        packet = "4:" +b.getString("USERNAME") + ":" + b.getString("PASSWORD") + ":" + b.getString("OTHERPLAYER") + ":" + b.getString("MYVARIANT");

        response = (String) theClient.sendPacket(packet);
        String[] temp = response.split(":");
        return temp[2];

    }
	public String createNewGame(String otherPlayer, String variant) throws Exception {

		String packet = "";
		String response  = "";
		packet = "4:" + myName + ":" + otherPlayer + ":" + variant;
		//Create 'new game' packet.
		if( theClient != null)
		{
			response = (String) theClient.sendPacket(packet);
		}
		else
		{
			throw new Exception("Client not intialized");
		}


		String[] temp = response.split(":");

		if (temp[1].equals("0"))
		{
			return temp[2];

		}
		else 
		{
			throw new Exception(temp[2]);
		}
	}


	public String login(String userName, String password, String regid, String version) throws Exception
	{
		theClient.login(userName, password, regid,version);
		//if exception doenst break us out here, set loggedIn as true.
		this.myName = userName;
		return "Logged in";
	}

    public String register(Bundle b) throws Exception
    {
        String packet = "0:" + b.getString("USERNAME") + ":" + b.getString("PASSWORD");
        return (String) theClient.sendPacket(packet);
    }

	public String register(String userName, String password) throws Exception
	{
		return theClient.register(userName, password);
	}

    public void refreshGames(Bundle b) throws Exception{

        String packet = "2:" + b.getString("USERNAME") + ":" + b.getString("PASSWORD");
        this.myGames = (GameContainer[]) theClient.sendPacket(packet);
    }



    public GameContainer[] getCurrentGames()
	{

		return myGames;
	}

    public String loadGame(Bundle b) throws Exception
    {
        String packet = "5:" + b.getString("USERNAME") +":" + b.getString("PASSWORD") + ":" + b.getString("GUID");
        GameBoard response = null;
        response = (GameBoard) theClient.sendPacket(packet);
        //this.currentGameID = myGames[b.getInt("INDEX")].getGuid();

        if(response.getBlackTeam().equals(b.getString("USERNAME")) && response.getBlackVar() == '\0')
        {
            //this specifies the player hasnt chosen a class yet, so we will need to
            currentGame = response;
            return "BLACKNEEDSUPDATE";
        }
        else
        {
            //load in the game.
            currentGame = response;
            return "SUCCESS";
        }
    }


	public GameBoard getGame()
	{

		return currentGame;
	}



	public String getName()
	{
		return myName;
	}

    public void updateClassChoice(Bundle b) throws Exception
    {
        String packet = "7:" +b.getString("USERNAME") +":" + b.getString("PASSWORD") + ":" + b.getString("GUID") +  ":" + b.getChar("CHOICE");
        GameBoard response = null;
        response = (GameBoard) theClient.sendPacket(packet);
        this.currentGame = response;


    }



	private ServerClient theClient;
	private String myName;
	private GameContainer[] myGames;

	//this is how we store the 'game'.  Not the best.
	private GameBoard currentGame;
	private UUID currentGameID;


    public String sendMove(Bundle b) throws Exception
    {

        String packet = "6:" +b.getString("USERNAME") +":" + b.getString("PASSWORD") + ":"  + b.getString("GUID");
        int[] myMove = b.getIntArray("MOVE");
        for(int i = 0; i < myMove.length; i++)
        {
            packet = packet + ":" + myMove[i];
        }
        packet = packet + ":" + b.getChar("PROMOTE");
        String response;
        response = (String) theClient.sendPacket(packet);

        String[] split = response.split(":");
        return split[1];
    }



    public String sendSkirmish(Bundle b) throws Exception
    {
        String packet = "8:" +b.getString("USERNAME") +":" + b.getString("PASSWORD") + ":" + b.getString("GUID") + ":" + b.getInt("WAGER");
        String response;
        response = (String) theClient.sendPacket(packet);
        String[] split = response.split(":");
        return split[1];


    }




    public String finishSkirmish(Bundle b) throws Exception
    {
        String packet = "9:" +b.getString("USERNAME") +":" + b.getString("PASSWORD") + ":" + b.getString("GUID") + ":" + b.getInt("WAGER");
        String response = null;
        response = (String) theClient.sendPacket(packet);
        String[] split = response.split(":");
        return split[1];


    }


    public void disconnect() {
        theClient.disconnect();
    }

    public boolean isAlive() {

       if(theClient == null)
           return false;
        else
           return theClient.isAlive();
    }

    public String forfeitGame(Bundle b) throws Exception {

        String packet = "A" + ":" + b.getString("USERNAME") +":" + b.getString("PASSWORD") + ":" + b.getString("GUID");
        String response = null;
        response = (String) theClient.sendPacket(packet);
        return response;
    }

    public String promoteGame(Bundle b) throws Exception {

        String packet = "B" + ":" + b.getString("USERNAME") +":" + b.getString("PASSWORD") + ":" + b.getString("GUID");
        int[] myMove = b.getIntArray("MOVE");
        for(int i = 0; i < myMove.length; i++)
        {
            packet = packet + ":" + myMove[i];
        }

        packet = packet + ":" + b.getChar("PROMOTE");
        String response;
        response = (String) theClient.sendPacket(packet);
        return response;

    }
}