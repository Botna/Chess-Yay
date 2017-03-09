package ServerStuff;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import chess2.source.GameBoard;
import chess2.source.GameContainer;

//import com.example.andrewapp.loginTask;

import android.R.string;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;


public class ChessClass 
{

	public ChessClass() throws UnknownHostException, IOException, InterruptedException, ExecutionException
	{
		this.theClient = new ServerClient();
	}

	public String createNewGame(String otherPlayer, String variant) throws Exception {
		// TODO Auto-generated method stub
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
	public String login(String userName, String password) throws Exception
	{
		theClient.login(userName, password);
		//if exception doenst break us out here, set loggedIn as true.
		this.myName = userName;
		return "Logged in";
	}

	public String register(String userName, String password) throws Exception
	{
		return theClient.register(userName, password);
	}
	public void refreshGames() throws Exception
	{
		String packet = "2:" + myName;


		if(theClient != null)
		{
			this.myGames = (GameContainer[]) theClient.sendPacket(packet);
		}
		else
		{
			throw new Exception("Problem in getGames");
		}
	}
	public GameContainer[] getCurrentGames()
	{

		return myGames;
	}

	public String loadGame(int index) throws Exception
	{
		//currentGameName = myGames[index][0] + ":" + myGames[index][1];
		String packet = "5:" + myName + ":" + myGames[index].getGuid().toString();
		GameBoard response = null;
		if(theClient != null)
		{
			response = (GameBoard) theClient.sendPacket(packet);
			this.currentGameID = myGames[index].getGuid();
		}
		else
		{
			throw new Exception("Problem in getGames");
		}



		if(response.getBlackTeam().equals(myName) && response.getBlackVar() == '\0')
		{
			//this specifies the player hasnt chosen a class yet, so we will need to 
			currentGame = response;	
			return "FAILURE";
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
	public void updateClassChoice(char choice) throws Exception
	{
		String packet = "7:" + currentGameID.toString() +  ":" + choice;
		GameBoard response = null;
		if(theClient != null)
		{
			response = (GameBoard) theClient.sendPacket(packet);
			this.currentGame = response;
		}
		else
		{
			throw new Exception("Problem in updateClassChoice");
		}
		//currentGame = response.split("_");
	}


	private ServerClient theClient;
	private String myName;
	private GameContainer[] myGames;

	//this is how we store the 'game'.  Not the best.
	private GameBoard currentGame;
	private UUID currentGameID;
	public String sendMove(int[] myMove) throws Exception {


		//String response, packet = "6:" + currentGame[1] + ":" + currentGame[2] + ":" + myName;
		//	packet = packet + ":" + oldY + ":" + oldX + ":" + newY + ":" + newX;
		//Convert myMove (a variably sized array of ints) into a string to pass

		String packet = "6:" + myName + ":" + currentGameID;
		String response = null;
		for(int i = 0; i < myMove.length; i++)
		{
			packet = packet + ":" + myMove[i];
		}
		if( theClient != null)
		{
				 response = (String) theClient.sendPacket(packet);
		}
		else
		{
			throw new Exception("Client not intialized");
		}

		String[] split = response.split(":");
			return split[1];

	}

}