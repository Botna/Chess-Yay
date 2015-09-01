package chess2.source;



import java.io.Serializable;
import java.util.Date;
import java.util.UUID;



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
	private boolean blackCheck;
	private boolean whiteCheck;
	private boolean gameOver;
	private String winner;
    private boolean promptDefense;
    private boolean promptOffense;
    private Date  timeStart;
    private Date timeEnd;
    private Date lastPlay;
	
	
	public GameContainer(String white, String black, char whiteVar, UUID guid)
	{
		this.whiteTeam = white;
		this.blackTeam = black;
		this.whiteVar = whiteVar;
		this.guid = guid;
		this.whoseTurn = white;
		this.timeStart = new Date();
		this.lastPlay = new Date();
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

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}
    public boolean isPromptDefense() {
        return promptDefense;
    }

    public void setPromptDefense(boolean promptDefense) {
        this.promptDefense = promptDefense;
    }
    public boolean isPromptOffense() {
        return promptOffense;
    }

    public void setPromptOffense(boolean promptOffense) {
        this.promptOffense = promptOffense;
    }

	public Date getTimeStart() {
		return timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Date getLastPlay() {
		return lastPlay;
	}

	public void setLastPlay(Date lastPlay) {
		this.lastPlay = lastPlay;
	}


}