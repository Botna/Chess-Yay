package serverClasses;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;

import java.io.IOException;
import java.nio.channels.FileLock;

import java.util.UUID;


import chess2.Classes.Classic.CPawn;
import chess2.source.GameBoard;
import chess2.source.GameContainer;
import chess2.source.PlayerRecord;
import chess2.source.SingleSpace;



public class FileClass
{
	private final static String PLAYER_DIR = "\\Chess2Server\\Players\\";
	private final static String PLAYER_FILE = "\\Chess2Server\\Players\\playerlist.a";
	private final String GAME_LIST_FILE = "\\Chess2Server\\Games\\gamelist.a";
	private final String GAME_LIST_DIR = "\\Chess2Server\\Games\\";
	private final String GAME_END = ".g";

	public FileClass()
	{

	}


	
	public PlayerRecord[] loadPlayers()
	{
		//Check taht the Directory exists first

		File playerDir = new File(PLAYER_DIR);
		PlayerRecord[] thePlayers = null;
		
		if ( !playerDir.exists())
			playerDir.mkdirs();
		try {
			//Now the directory exists.  See if file exists.
			File playerList = new File(PLAYER_FILE);
			if( !playerList.exists())
			{
				return thePlayers;
				//return nothing, there isnt anything there
				//only create the file when we have something to put in it.
			}

			playerList = null;


			InputStream fileIn = new FileInputStream(PLAYER_FILE);
			//lock fileIn
			

			//specifies the file has data. recreate our FIleInputStream;
			InputStream bufferIn = new BufferedInputStream(fileIn);
			ObjectInputStream in = new ObjectInputStream(bufferIn);
			thePlayers = (PlayerRecord[]) in.readObject();
			in.close();
			bufferIn.close();

			fileIn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return thePlayers;
	}

	public void writeNewPlayer(PlayerRecord player)
	{
		File playerDir = new File(PLAYER_DIR);
		PlayerRecord[] thePlayers = null;
		if ( !playerDir.exists())
			playerDir.mkdirs();



		try
		{
			//Now the directory exists.  See if file exists.
			File playerList = new File(PLAYER_FILE);
			if( !playerList.exists())
			{
				playerList.createNewFile();
				//since we have something to write, create the file and write to it.
				thePlayers = new PlayerRecord[]{player};


				//write the file back out
				OutputStream fileOut = new FileOutputStream(PLAYER_FILE);
				
				OutputStream buffer = new BufferedOutputStream(fileOut);
				ObjectOutputStream out = new ObjectOutputStream(buffer);

				//close all the streams
				out.writeObject(thePlayers);
				out.close();
				buffer.close();
				fileOut.close();
				return;


			}	
		}
		catch(Exception e)
		{
			System.out.println("Problem creating file in writeNewPlayer");
			return;
		}


		int tries = 0;
		while(tries < 10)
		{


			try	
			{

				//STuff to read, read it in, add our new player, write it out, then close.
				
				
				//lock the output stream before we open the file so that we know we have the most up to date version fo the file
				//to deal with.
				InputStream fileIn = new FileInputStream(PLAYER_FILE);
				InputStream bufferIn = new BufferedInputStream(fileIn);
				ObjectInputStream in = new ObjectInputStream(bufferIn);

				thePlayers = (PlayerRecord[]) in.readObject();

				//add our new record

				PlayerRecord[] temp = new PlayerRecord[thePlayers.length+1];

				for(int i = 0; i< thePlayers.length; i++)
				{
					temp[i] = thePlayers[i];
				}
				temp[thePlayers.length] = player;
				thePlayers = null;
				thePlayers = temp;

				//write it out.
				FileOutputStream fileOut =  new FileOutputStream(PLAYER_FILE);
				OutputStream buffer = new BufferedOutputStream(fileOut);
				ObjectOutputStream out = new ObjectOutputStream(buffer);


				out.writeObject(thePlayers);
				out.close();
				buffer.close();
				fileOut.close();
				in.close();
				bufferIn.close();
				fileIn.close();
				tries = 10;

			}
			catch(Exception e)
			{
				System.out.println("Problem with opening playerfile to write new user attempt " + tries);	
				tries++;
			}
		}       
	}

	public void updatePlayer(PlayerRecord me)
	{
		File playerDir = new File(PLAYER_DIR);
		PlayerRecord[] thePlayers = null;
		if ( !playerDir.exists())
			playerDir.mkdirs();
		
		int tries = 0;
		while(tries < 10)
		{


			try	
			{

				//STuff to read, read it in, add our new player, write it out, then close.
				
				
				//lock the output stream before we open the file so that we know we have the most up to date version fo the file
				//to deal with.
				InputStream fileIn = new FileInputStream(PLAYER_FILE);
				InputStream bufferIn = new BufferedInputStream(fileIn);
				ObjectInputStream in = new ObjectInputStream(bufferIn);

				thePlayers = (PlayerRecord[]) in.readObject();

				//find our index.
				for(int i = 0; i< thePlayers.length; i++)
				{
					if(thePlayers[i].getPlayerName().equals(me.getPlayerName()))
					{
						//heres our index.   update
						thePlayers[i] = me;
					}
				}

				

				//write it out.
				FileOutputStream fileOut =  new FileOutputStream(PLAYER_FILE);
				OutputStream buffer = new BufferedOutputStream(fileOut);
				ObjectOutputStream out = new ObjectOutputStream(buffer);


				out.writeObject(thePlayers);
				out.close();
				buffer.close();
				fileOut.close();
				in.close();
				bufferIn.close();
				fileIn.close();
				tries = 10;

			}
			catch(Exception e)
			{
				System.out.println("Problem with opening playerfile to write new user attempt " + tries);	
				tries++;
			}
		}       
	}
	public GameContainer[] loadGames()
	{
		File playerDir = new File(GAME_LIST_DIR);
		GameContainer[] gameList = null;;
		if ( !playerDir.exists())
			playerDir.mkdirs();
		try {
			//Now the directory exists.  See if file exists.
			File playerList = new File(GAME_LIST_FILE);
			if( !playerList.exists())
			{
				return gameList;
				//return nothing, there isnt anything there
				//only create the file when we have something to put in it.
			}

			

			InputStream fileIn = new FileInputStream(GAME_LIST_FILE);
			InputStream bufferIn = new BufferedInputStream(fileIn);
			ObjectInputStream in = new ObjectInputStream(bufferIn);
			
			gameList = (GameContainer[]) in.readObject();
			in.close();
			bufferIn.close();

			fileIn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("problem in load game list");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("problem in load game list");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("problem in load game list");

		} 


		return gameList;
	}

	public void writeNewGame(GameContainer newGame)
	{
		File playerDir = new File(GAME_LIST_DIR);
		GameContainer[] gameList = null;;
		if ( !playerDir.exists())
			playerDir.mkdirs();
		try {
			//Now the directory exists.  See if file exists.
			File playerList = new File(GAME_LIST_FILE);
			if( !playerList.exists())
			{
				
				playerList.createNewFile();
				//create the file and put stuff in it.
				
				gameList = new GameContainer[]{newGame};
				
				
				OutputStream fileOut = new FileOutputStream(GAME_LIST_FILE);
				OutputStream bufferOut = new BufferedOutputStream(fileOut);
				ObjectOutputStream out = new ObjectOutputStream(bufferOut);
				
				out.writeObject(gameList);
				out.close();
				bufferOut.close();
				fileOut.close();
				return;
			}
			
			int tries = 0;
			while (tries < 5)
			{

			InputStream fileIn = new FileInputStream(GAME_LIST_FILE);
			InputStream bufferIn = new BufferedInputStream(fileIn);
			ObjectInputStream in = new ObjectInputStream(bufferIn);
			
			gameList = (GameContainer[]) in.readObject();
			
			GameContainer[] temp = new GameContainer[gameList.length + 1];
			
			for(int i = 0; i < gameList.length; i++)
			{
			
				temp[i] = gameList[i];
				
			}
			temp[gameList.length] = newGame;
			gameList = null;
			gameList = temp;
			
			
			
			OutputStream fileOut = new FileOutputStream(GAME_LIST_FILE);
			OutputStream bufferOut = new BufferedOutputStream(fileOut);
			ObjectOutputStream out = new ObjectOutputStream(bufferOut);
			
			out.writeObject(gameList);
			out.close();
			bufferOut.close();
			fileOut.close();
			
			in.close();
			bufferIn.close();
			fileIn.close();
			tries = 5;
		
			
			
			}
		}
		catch(Exception e)
		{
			
		}
		
	}


	public GameBoard loadSingleGame(GameContainer currentGame) {
		
		
		GameBoard newGame = null;
		try {
		String dir = GAME_LIST_DIR + currentGame.getGuid().toString() + GAME_END;
		
		File theGame = new File(dir);
		
		if(!theGame.exists())
		{
			//its the first time someone is trying to play this game, lets create it and save it and then return it.
			OutputStream fileOut = new FileOutputStream(theGame);
			OutputStream buffer = new BufferedOutputStream(fileOut);
			ObjectOutputStream out = new ObjectOutputStream(buffer);
			
		    newGame = new GameBoard(currentGame);
			
			out.writeObject(newGame);
			out.close();
			buffer.close();
			fileOut.close();
			
		}
		else
		{
			//just load the game and return it. 
			
			InputStream fileIn = new FileInputStream(theGame);
			InputStream buffer = new BufferedInputStream(fileIn);
			ObjectInputStream in = new ObjectInputStream(buffer);
			
			newGame = (GameBoard) in.readObject();
			in.close();
			buffer.close();
			fileIn.close();
		}
		
		
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return newGame;
	}

	public void updateGame(GameContainer game, GameBoard board)
	{
		File gameListFile = new File(GAME_LIST_FILE);
		String temp = GAME_LIST_DIR + game.getGuid().toString() + GAME_END;
		File gameFile = new File(temp);
		
		//both files should exist at this point.
		if(!gameListFile.exists() || !gameFile.exists())
		{
			System.out.println("Game busted as fuck in UPDATE GAME");
		}
		else
		{
			//need to reload the Game Container(s) and the Game itself and update them both. 
			//just load the game and return it. 
			try {
			InputStream fileIn;
			
			fileIn = new FileInputStream(gameListFile);
			
			InputStream bufferIn = new BufferedInputStream(fileIn);
			ObjectInputStream in = new ObjectInputStream(bufferIn);
			
			GameContainer[] tempContainer = (GameContainer[]) in.readObject();
			in.close();
			bufferIn.close();
			fileIn.close();
			
			OutputStream fileOut = new FileOutputStream(gameListFile);
			OutputStream bufferOut = new BufferedOutputStream(fileOut);
			ObjectOutputStream out = new ObjectOutputStream(bufferOut);
			
			
			//find the game container we are updating
			
			for(int i = 0; i< tempContainer.length; i++)
			{
				if( tempContainer[i].getGuid().equals(game.getGuid()))
				{
					//copy ourGame over to this index.
					tempContainer[i] = null;
					tempContainer[i] = game;
					i = tempContainer.length;
				}
			}
			
			
			out.writeObject(tempContainer);
			out.close();
			bufferOut.close();
			fileOut.close();
			
			//Game container is updated,
			
			
			//Time to update the Game Board;
			
			//this will be changed later, but for now, we just copy the state to the file.
			
			
			fileOut = new FileOutputStream(gameFile);
			bufferOut = new BufferedOutputStream(fileOut);
			out = new ObjectOutputStream(bufferOut);
			out.writeObject(board);
			out.close();
			bufferOut.close();
			fileOut.close();
			
			
			//gamecontainer and board is updated.
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
			
		}



	public void deleteGame(GameContainer toDelete) {
		// TODO Auto-generated method stub
		File gameListFile = new File(GAME_LIST_FILE);
		String temp = GAME_LIST_DIR + toDelete.getGuid().toString() + GAME_END;
		File gameFile = new File(temp);
		
		//both files should exist at this point.
		if(!gameListFile.exists() || !gameFile.exists())
		{
			System.out.println("Game busted as fuck in UPDATE GAME");
		}
		else
		{
			//need to reload the Game Container(s) and the Game itself and update them both. 
			//just load the game and return it. 
			try {
			InputStream fileIn;
			
			fileIn = new FileInputStream(gameListFile);
			
			InputStream bufferIn = new BufferedInputStream(fileIn);
			ObjectInputStream in = new ObjectInputStream(bufferIn);
			
			GameContainer[] tempContainer = (GameContainer[]) in.readObject();
			in.close();
			bufferIn.close();
			fileIn.close();
			
			OutputStream fileOut = new FileOutputStream(gameListFile);
			OutputStream bufferOut = new BufferedOutputStream(fileOut);
			ObjectOutputStream out = new ObjectOutputStream(bufferOut);
			
			
			//find the game container we are updating
			GameContainer[] newContainer = new GameContainer[tempContainer.length -1];
			int index = 0;
			for(int i = 0; i< tempContainer.length; i++)
			{
				if(tempContainer[i].getGuid().equals(toDelete.getGuid()))
				{
					//this is what we are deleting.
					//do nothing
					
				}
				else
				{
					newContainer[index] = tempContainer[i];
					index++;
				}
				
				
				
			}
			
			
			out.writeObject(newContainer);
			out.close();
			bufferOut.close();
			fileOut.close();
			
			//Game container is updated,
			
			
			//Time to update the Game Board;
			
			//this will be changed later, but for now, we just copy the state to the file.
			
			
			gameFile.delete();
			
			
			//gamecontainer and board is updated.
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
			
	}
		
		
		
	
	
	
	
	
}