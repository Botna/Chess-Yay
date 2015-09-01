package chess2.source;
import java.io.Serializable;
import java.net.*;
public class PlayerRecord implements Serializable{
	private String playerName = "";
	private String password = "";
	private int numWins = 0;
	private int numLoss = 0;
	private String motto = "-";
	private InetAddress ipaddress = null;
	private int port = 0;
	private String regID = "";

	public PlayerRecord(String name, String pword) {
		this.playerName = name;
		this.password = pword;
		this.numWins = 0;
		this.numLoss = 0;
		this.motto = "Temp disabled";
	}
	
	public PlayerRecord () {
		new PlayerRecord ("", "");
		
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getNumWins() {
		return numWins;
	}

	public void setNumWins(int numWins) {
		this.numWins = numWins;
	}

	public int getNumLoss() {
		return numLoss;
	}

	public void setNumLoss(int numLoss) {
		this.numLoss = numLoss;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public InetAddress getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(InetAddress ipaddress) {
		this.ipaddress = ipaddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getRegID() {
		return regID;
	}

	public void setRegID(String regID) {
		this.regID = regID;
	}

	
	

	

}