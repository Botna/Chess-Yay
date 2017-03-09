package chess2.source;



import java.io.Serializable;
import java.util.UUID;


import com.example.andrewapp.NewGameActivity.ClassChoiceDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.EditText;
import chess2.Classes.Animals.ABishop;
import chess2.Classes.Animals.AKnight;
import chess2.Classes.Animals.AQueen;
import chess2.Classes.Animals.ARook;
import chess2.Classes.Classic.CBishop;
import chess2.Classes.Classic.CEmpty;
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
import chess2.source.SingleSpace;

public class GameContainer implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9098436494834842183L;
	private UUID guid;
	private String whiteTeam;
	private String blackTeam;
	private char whiteVar;
	private char blackVar;
	private String whoseTurn;
	
	
	
	public GameContainer(String white, String black, char whiteVar, UUID guid)
	{
		this.whiteTeam = white;
		this.blackTeam = black;
		this.whiteVar = whiteVar;
		this.guid = guid;
		this.whoseTurn = white;
	}
	
	public GameContainer()
	{
		
	}
	public void setBlackVar(char blackVar)
	{
		this.blackVar = blackVar;
	}
	
	public UUID getGuid()
	{
		return this.guid;
	}
	
	public String getWhiteTeam()
	{
		return this.whiteTeam;
	}
	
	public String getBlackTeam()
	{
		return this.blackTeam;
	}
	
	public char getBlackVar()
	{
		return this.blackVar;
	}
	
	public char getWhiteVar()
	{
		return this.whiteVar;
	}
	
	public String getTurn()
	{
		return this.whoseTurn;
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
}