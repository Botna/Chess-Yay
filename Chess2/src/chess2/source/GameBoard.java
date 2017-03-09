package chess2.source;





import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;





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


public class GameBoard implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1509888617043495978L;
	private int beforeX, beforeY, afterX, afterY;
	private String whiteTeam;
	private String blackTeam;
	private char whiteVar;
	private char blackVar;
	private boolean whiteCheck;
	private boolean blackCheck;
	private String whoseTurn;
	private String state;
	private boolean myTurn;
	private char myTeam;
	private String myName;
	private UUID guid;
	private GameContainer myContainer;
	private SingleSpace[][] theGame;
	private SingleSpace displaced,  insinuated;
	private SingleSpace[] displacedRook;


	public GameBoard(GameContainer myGame) throws Exception
	{
		myContainer = myGame;
		whiteTeam = myGame.getWhiteTeam();
		blackTeam = myGame.getBlackTeam();
		whiteVar = myGame.getWhiteVar();
		whoseTurn = myGame.getTurn(); 
		guid = myGame.getGuid();

		theGame = new SingleSpace[8][8];
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				theGame[a][b] = new SingleSpace();
			}

		}

		// black back row (a8-h8)
		theGame[7][0].setContents(new CRook('b', '-', 'r', false));
		theGame[7][1].setContents(new CKnight('b', '-', 'k', false));
		theGame[7][2].setContents(new CBishop('b', '-', 'b', false));
		theGame[7][3].setContents(new CQueen('b', '-', 'q', false));
		theGame[7][4].setContents(new CKing('b', '-', 'x', false));
		theGame[7][5].setContents(new CBishop('b', '-', 'b', false));
		theGame[7][6].setContents(new CKnight('b', '-', 'k', false));
		theGame[7][7].setContents(new CRook('b', '-', 'r', false));

		for (int i = 0; i < 8; i++) {
			theGame[6][i].setContents(new CPawn('b', '-', 'p', false));
		}

		inflateRows(myGame, 'w');			// white back row (a1-h1)



		this.whiteCheck = false;
		this.blackCheck = false;

		this.whiteVar = myGame.getWhiteVar();

	}
	private void inflateRows(GameContainer myGame, char team) throws Exception {
		// TODO Auto-generated method stub

		if( team == 'w')
		{

			theGame[0][0].setContents( createPiece(myGame.getWhiteVar(), 'r', team));
			theGame[0][1].setContents(createPiece(myGame.getWhiteVar(), 'k', team));
			theGame[0][2].setContents(createPiece(myGame.getWhiteVar(), 'b', team));
			theGame[0][3].setContents(createPiece(myGame.getWhiteVar(), 'q', team));
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
			theGame[7][3].setContents(createPiece(myGame.getBlackVar(), 'q', team));
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

	private PieceInf createPiece(char variant, char piece, char team) throws Exception
	{

		PieceInf thePiece = null;
		switch (variant)
		{
		case 'C':
			//classic

			switch (piece)
			{
			case 'r':
				thePiece = new CRook(team, variant, piece, false);
				break;
			case 'k':
				thePiece = new CKnight(team, variant, piece, false);
				break;
			case 'b':
				thePiece = new CBishop(team, variant, piece, false);
				break;
			case 'q':
				thePiece = new CQueen(team, variant, piece, false);
				break;
			case 'x':
				thePiece = new CKing(team, variant, piece, false);
				break;
			case 'p':
				thePiece = new CPawn(team, variant, piece, false);
				break;
			}

			break;
		case 'R':
			//reapers
			switch (piece)
			{
			case 'r':
				thePiece = new RRook(team, variant, piece, false);
				break;
			case 'k':
				thePiece = new CKnight(team, variant, piece, false);
				break;
			case 'b':
				thePiece = new CBishop(team, variant, piece, false);
				break;
			case 'q':
				thePiece = new RQueen(team, variant, piece, false);
				break;
			case 'x':
				thePiece = new CKing(team, variant, piece, false);
				break;
			case 'p':
				thePiece = new CPawn(team, variant, piece, false);
				break;
			}
			break;

		case 'N':
			//nemesis
			switch (piece)
			{
			case 'r':
				thePiece = new CRook(team, variant, piece, false);
				break;
			case 'k':
				thePiece = new CKnight(team, variant, piece, false);
				break;
			case 'b':
				thePiece = new CBishop(team, variant, piece, false);
				break;
			case 'q':
				thePiece = new NQueen(team, variant, piece, false);
				break;
			case 'x':
				thePiece = new CKing(team, variant, piece, false);
				break;
			case 'p':
				thePiece = new NPawn(team, variant, piece, false);
				break;
			}
			break;

		case 'E':
			//empowered
			switch (piece)
			{
			case 'r':
				thePiece = new ERook(team, variant, piece, false);
				break;
			case 'k':
				thePiece = new EKnight(team, variant, piece, false);
				break;
			case 'b':
				thePiece = new EBishop(team, variant, piece, false);
				break;
			case 'q':
				thePiece = new EQueen(team, variant, piece, false);
				break;
			case 'x':
				thePiece = new CKing(team, variant, piece, false);
				break;
			case 'p':
				thePiece = new CPawn(team, variant, piece, false);
				break;
			}
			break;

		case 'A':
			//animals
			switch (piece)
			{
			case 'r':
				thePiece = new ARook(team, variant, piece, false);
				break;
			case 'k':
				thePiece = new AKnight(team, variant, piece, false);
				break;
			case 'b':
				thePiece = new ABishop(team, variant, piece, false);
				break;
			case 'q':
				thePiece = new AQueen(team, variant, piece, false);
				break;
			case 'x':
				thePiece = new CKing(team, variant, piece, false);
				break;
			case 'p':
				thePiece = new CPawn(team, variant, piece, false);
				break;
			}
			break;
		case 'T':
			//two kings
			switch (piece)
			{
			case 'r':
				thePiece = new CRook(team, variant, piece, false);
				break;
			case 'k':
				thePiece = new CKnight(team, variant, piece, false);
				break;
			case 'b':
				thePiece = new CBishop(team, variant, piece, false);
				break;
			case 'q':
				thePiece = new CKing(team, variant, piece, false);
				break;
			case 'x':
				thePiece = new CKing(team, variant, piece, false);
				break;
			case 'p':
				thePiece = new CPawn(team, variant, piece, false);
				break;
			}
			break;

		}
		if( thePiece == null)
		{
			throw new Exception("Something busted in create piece");
		}
		return thePiece;
	}
	public boolean myTurn()
	{
		//needs to be ran after determine turn
		return myTurn;
	}

	public int simulateClick(int x, int y)
	{
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
		case "DISPLAYING":
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
			else if (!theGame[y][x].getHighlight())
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
					return 1;
				}//else its just a blank space, dont do anything.
			}
			else
			{
				if (myTurn)
				{
					//we will respond to clicks on highlighted squares.  otherwise, we just ignore it and pretend its an accidental click.

					if(theGame[y][x].getHighlight() && theGame[beforeY][beforeX].getContents().getTeam() == myTeam)
					{
						//	//user just fed me a move.
						state = "WAITING";
						return 2;
					}
				}
			}
		}
		return 0;
	}

	public void insinuateMove(int x, int y)
	{

		this.clearHighlight();
		//Need to pretend to make the move to display whats gonna happen.
		if(theGame[beforeY][beforeX].getContents().getMoveSet() == 'A' && theGame[beforeY][beforeX].getContents().getContents() == 'r')
		{


			//simule Animal rook move.
			displacedRook = new SingleSpace[4];
			//figure out direction.
			if(x>beforeX)
			{
				for(int i  = 1; i< x; i++)
				{
					//grab the pieces in between my current spot and the new spot.
					displacedRook[i] = theGame[beforeY][beforeX + i];
					theGame[beforeY][beforeX+i] = new SingleSpace();
				}
				displacedRook[0] = theGame[y][x];
				theGame[y][x] = theGame[beforeY][beforeX];
				theGame[beforeY][beforeX] = new SingleSpace();
			}
			else if(x < beforeX)
			{
				for(int i  = 1; i< x; i++)
				{
					//grab the pieces in between my current spot and the new spot.
					displacedRook[i] = theGame[beforeY][beforeX - i];
					theGame[beforeY][beforeX-i] = new SingleSpace();
				}
				displacedRook[0] = theGame[y][x];
				theGame[y][x] = theGame[beforeY][beforeX];
				theGame[beforeY][beforeX] = new SingleSpace();
			}
			else if(y > beforeY)
			{
				for(int i  = 1; i< y; i++)
				{
					//grab the pieces in between my current spot and the new spot.
					displacedRook[i] = theGame[beforeY+ i][beforeX ];
					theGame[beforeY+ i][beforeX] = new SingleSpace();
				}
				displacedRook[0] = theGame[y][x];
				theGame[y][x] = theGame[beforeY][beforeX];
				theGame[beforeY][beforeX] = new SingleSpace();
			}
			else if(y < beforeY)
			{
				for(int i  = 1; i< y; i++)
				{
					//grab the pieces in between my current spot and the new spot.
					displacedRook[i] = theGame[beforeY-i][beforeX];
					theGame[beforeY-i][beforeX] = new SingleSpace();
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
			}
		}


		else{



			displaced = theGame[y][x];
			insinuated = theGame[beforeY][beforeX];
			theGame[beforeY][beforeX] = new SingleSpace();
			theGame[y][x] = insinuated;
		}

	}
	public void undoInsinuate(int x, int y)
	{
		if(theGame[y][x].getContents().getMoveSet() == 'A' && theGame[y][x].getContents().getContents() == 'r')
		{



			//figure out direction.
			if(x>beforeX)
			{
				theGame[beforeY][beforeX] = theGame[y][x];
				for(int i  = 1; i< x; i++)
				{
					//grab the pieces in between my current spot and the new spot.
					theGame[beforeY][beforeX + i] = displacedRook[i];

				}
				theGame[y][x] = displacedRook[0];


			}
			else if(x < beforeX)
			{
				theGame[beforeY][beforeX] = theGame[y][x];
				for(int i  = 1; i< x; i++)
				{
					//grab the pieces in between my current spot and the new spot.
					theGame[beforeY][beforeX - i] = displacedRook[i];

				}
				theGame[y][x] = displacedRook[0];

			}
			else if(y > beforeY)
			{
				theGame[beforeY][beforeX] = theGame[y][x];
				for(int i  = 1; i< y; i++)
				{
					//grab the pieces in between my current spot and the new spot.
					theGame[beforeY+ i][beforeX ] =  displacedRook[i];
				}
				theGame[y][x] = displacedRook[0];
			}
			else if(y < beforeY)
			{
				theGame[beforeY][beforeX] = theGame[y][x];
				for(int i  = 1; i< y; i++)
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
		else
		{
			theGame[y][x] = displaced;
			theGame[beforeY][beforeX] = insinuated;

		}
		theGame[beforeY][beforeX].setSelected(false);
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

	public String getWhoseTurn() {
		return whoseTurn;
	}

	public void setWhoseTurn(String whoseTurn) {
		this.whoseTurn = whoseTurn;
	}

	public boolean isBlackCheck() {
		return blackCheck;
	}

	public void setBlackCheck(boolean blackCheck) {
		this.blackCheck = blackCheck;
	}

	public boolean isWhiteCheck() {
		return whiteCheck;
	}

	public void setWhiteCheck(boolean whiteCheck) {
		this.whiteCheck = whiteCheck;
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

	public char getWhiteVar() {
		return whiteVar;
	}

	public void setWhiteVar(char whiteVar) {
		this.whiteVar = whiteVar;
	}

	public char getBlackVar() {
		return blackVar;
	}

	public UUID getGuid()
	{
		return this.guid;
	}

	public char getMyTeam()
	{
		return this.myTeam;
	}
	public void setBlackVar(char blackVar) {
		this.blackVar = blackVar;
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
		for(int i = 0; i < count; i++)
		{
			theGame[spaces[i][1]][spaces[i][0]].setHighlight(true);
		}
	}

	public void makeMove(int oldX, int oldY, int newX, int newY) throws Exception
	{
		//should only be used when we know it wont cause check.
		clearHighlight();
		clearLastMove();
		theGame[oldY][oldX].getContents().highlight(this, oldY, oldX);

		if( newX <0 || newY <0)
		{
			//two kings whirlwind move or pass. 
		}
		else
		{
			if(theGame[newY][newX].getHighlight())
			{


				if(theGame[oldY][oldX].getContents().getContents() == 'b' && theGame[oldY][oldX].getContents().getMoveSet() == 'A')
				{

				}
				else if (theGame[oldY][oldX].getContents().getContents() == 'r' && theGame[oldY][oldX].getContents().getMoveSet() == 'A') 
				{

				}
				else
				{
					//go ahead and make the move if its anything but animals bishop/rook.
					//those require special logic
					SingleSpace temp = new SingleSpace();
					temp.setLastMove(true);
					theGame[oldY][oldX].getContents().setHasMoved(true);
					theGame[oldY][oldX].setLastMove(true);
					theGame[newY][newX] = theGame[oldY][oldX];
					theGame[oldY][oldX] = null;
					theGame[oldY][oldX] = temp;
					clearHighlight();
				}




			}
			else
				throw new Exception("Cnat move to a space that isnt highlighted");
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
				return true;
			}
		}
		else
		{
			if(theGame[king[0][0]][king[0][1]].getHighlight())
			{
				return true;
			}
		}
		return false;
	}

	public boolean simulateMove(int x, int y, int a, int b)
	{

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
		boolean rampage = false, foundKing = false;
		int xMove = 0, yMove = 0, temp = 0;
		SingleSpace temp1 = null;
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
					}
				}

				if(rampage && ((b != y+3)&& b!=7))//b is not our maximum movement and we've rampaged. cant actually move here.
				{
					return false;
				}

				if(rampage && foundKing)
				{
					//we rampaged and we found our king.   We cant move here...return false
					return false;
				}
				else
				{
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
					}
				}

				if(rampage && ((b != y-3)&& b!=0))//b is not our maximum movement and we've rampaged. cant actually move here.
				{
					return false;
				}
				if(rampage && foundKing)
				{
					//we rampaged and we found our king.   We cant move here...return false
					return false;
				}
				else
				{
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
		}
		else
		{
			//we have column movement.  lets see if its positive or negative.
			if( a > x)
			{
				//piece is moving 'right
				for(int i = 1; i <4; i++ )
				{
					if(x+i < 8 && x+i <= a)
					{
						//count up till we hit our 'b' and see if we are going to rampage.
						if(theGame[y][x+i].getContents().getContents() != '-')
							rampage = true;
						if(theGame[y][x+i].getContents().getContents() == 'x' && theGame[y][x+i].getContents().getTeam() == team)
							foundKing = true;
					}
				}
				if(rampage && ((a != x+3)&& a!=7))//b is not our maximum movement and we've rampaged. cant actually move here.
				{
					return false;
				}
				if(rampage && foundKing)
				{
					//we rampaged and we found our king.   We cant move here...return false
					return false;
				}
				else
				{
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
					}
				}
				if(rampage && ((a != x-3)&&                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       a!=0))//b is not our maximum movement and we've rampaged. cant actually move here.
				{
					return false;
				}
				if(rampage && foundKing)
				{
					//we rampaged and we found our king.   We cant move here...return false
					return false;
				}
				else
				{
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



		}




	}

	public boolean simulateABishop(int x, int y, int a, int b)
	{
		return true;
	}

}