package chess2.source;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GamePoint implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SingleSpace[][] currentBoard;
	private String whiteTeam;
	private String blackTeam;



	private char whiteVar;


	private char blackVar;
	private int whiteToken;
	private int blackToken;
	private SingleSpace pieceChallenged;
	private String state;
	private PieceInf[] capturedPieces;
	private String message;

	public GamePoint()
	{
		//Dont really do anything here.
	}



	public void pushTurn(GameBoard theGame)
	{
		try {

			//CLONE teh gameboard.
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos;

			oos = new ObjectOutputStream(baos);

			oos.writeObject(theGame.getTheGame());

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			currentBoard = (SingleSpace[][]) ois.readObject();
			whiteTeam = theGame.getWhiteTeam().toString();
			blackTeam = theGame.getBlackTeam().toString();
			whiteVar = theGame.getWhiteVar();
			blackVar = theGame.getBlackVar();
			whiteToken = theGame.getWhiteTokens();
			blackToken = theGame.getBlackTokens();
			capturedPieces = theGame.getCapturedPieces();
			message = null;
			setState("TURN");
			oos.close();
			baos.close();
			ois.close();
			bais.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pushChallenge(GameBoard theGame)
	{
		try {
			//Clone the game
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos;

			oos = new ObjectOutputStream(baos);

			oos.writeObject(theGame.getTheGame());

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			currentBoard = (SingleSpace[][]) ois.readObject();
			whiteTeam = (theGame.getWhiteTeam()).toString();
			blackTeam = (theGame.getBlackTeam()).toString();
			whiteToken = theGame.getWhiteTokens();
			blackToken = theGame.getBlackTokens();
			capturedPieces = theGame.getCapturedPieces();
			whiteVar = theGame.getWhiteVar();
			blackVar = theGame.getBlackVar();
			message = null;
			oos.writeObject(theGame.getPieceJustTaken());
			pieceChallenged = (SingleSpace)ois.readObject();
			oos.close();
			baos.close();
			ois.close();
			bais.close();

			setState("CHALLENGE");
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public String getWhiteTeam() {
		return whiteTeam;
	}

	public void setWhiteTeam(String whiteTeam) {
		this.whiteTeam = whiteTeam;
	}

	public String getBlackTeam() {
		return blackTeam;
	}

	public void setBlackTeam(String blackTeam) {
		this.blackTeam = blackTeam;
	}

	public int getWhiteToken() {
		return whiteToken;
	}

	public void setWhiteToken(int whiteToken) {
		this.whiteToken = whiteToken;
	}

	public int getBlackToken() {
		return blackToken;
	}

	public void setBlackToken(int blackToken) {
		this.blackToken = blackToken;
	}

	public SingleSpace getPieceChallenged() {
		return pieceChallenged;
	}

	public void setPieceChallenged(SingleSpace pieceChallenged) {
		this.pieceChallenged = pieceChallenged;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public SingleSpace[][] getBoard()
	{
		return currentBoard;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCapturedPieces(PieceInf[] capturedPieces)
	{
		this.capturedPieces = capturedPieces;
	}
	public PieceInf[] getCapturedPieces()
	{

		return this.capturedPieces;

	}


	public char getWhiteVar() {
		return whiteVar;
	}
	public char getBlackVar() {
		return blackVar;
	}
}