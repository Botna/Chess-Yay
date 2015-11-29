package serverClasses;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ExecutionException;

//import com.example.andrewapp.loginTask;

import android.R.string;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;

import com.botna.chess2.R;

import chess2.customEXC.VersionMismatchException;


public class ServerClient 
{

	//private final String CURR_VERSION = "1.5";
	public ServerClient() throws UnknownHostException, IOException, InterruptedException, ExecutionException{
		new CreateTask().execute().get();
	}
	 
	
	
	
	public Boolean loggedIn()
	{
		return loggedIn;
	}

	public void login(String userName, String password, String regid, String version) throws Exception {

		loggedIn = false;

		String payload = "";
		String response = "";
		String[] splitString;
		if (client.isBound()) 
		{
			payload = "1:" + userName + ":" + password + ":" + regid;
			try {
				out.writeObject(payload);
			} catch (IOException e) {
				throw new Exception("Sending Login Information failed");
			}

		}
		else
			throw new Exception("Server is down");
		
		response = (String) new InTask().execute().get();

		splitString  = response.split(":");

		if(!splitString[3].equals(version))
		{
			//we have a version mismatch.   We need to throw and exception and prompt the user to update.
			throw new VersionMismatchException("You need to update your client");
		}
		if (splitString.length >1 )
		{//signifies the appropriate length for a return packet
			if(splitString[0].equals("1"))
			{//Login Functionality

				if(splitString[1].equals("0"))
				{
					loggedIn = true;
				}
				else if(splitString[1].equals("1"))
				{
					throw new Exception("Wrong Password");
				}
				else
				{
					throw new Exception("User Doesnt Exist");
				}
			}
		}
	}

	public String register(String userName, String password) throws Exception
	{
		String payload;
		//send
		if(!userName.contains(":") && !password.contains(":"))
		{
			payload = "0:" + userName + ":" + password;
		}
		else
		{
			throw new Exception("Bad username or password.  No ':' please.");
		}


		out.writeObject(payload);

		//spin off async task to receive.
		String response = (String) new InTask().execute().get();
		String temp[] = response.split(":");
		if(temp[1].equals("0"))
		{
			return "User Created";
		}
		else 
		{
			return "User already exists =(";
		}
	
	}

	
	public Object sendPacket(String packet)throws Exception
	{
		Object response = null;

			out.writeObject(packet);
			
			
			response = new InTask().execute().get();

		
		return response;
	}
	private 
	Socket client;
	OutputStream outToServer;
	ObjectOutputStream out ;
	InputStream inFromServer;
	ObjectInputStream in;
	String response = "";
	Boolean loggedIn = false;

    public void disconnect() {
      try {
          out.close();
          in.close();
          outToServer.close();
          inFromServer.close();
          client.close();
      }
      catch(Exception e)
      {
          System.out.println("Broke something while disconnecting");
      }

    }

    public boolean isAlive() {

        if(client == null
           || in == null
           || out == null
           || outToServer == null
           || inFromServer == null)
        return false;
        else
        return true;
    }

	private class CreateTask   extends AsyncTask<String, Void, String>  {
		@Override
		protected String doInBackground(String... urls) {
			if(android.os.Debug.isDebuggerConnected())
				android.os.Debug.waitForDebugger();
			try{
				if (client == null){
					InetSocketAddress temp = new InetSocketAddress("76.115.34.47", 9812);
                    client = new Socket();
                    client.connect(temp, 2000);

					
					outToServer = client.getOutputStream();
					out =
							new ObjectOutputStream(outToServer);
					inFromServer = client.getInputStream();
					in =
							new ObjectInputStream(inFromServer);
				}


			}
            catch(Exception e)
            {
                client = null;
                outToServer = null;
                out = null;
                in = null;
                inFromServer = null;

                return "failure";
            }
            return "success";
		}

	}

	private class InTask   extends AsyncTask<String, Void, Object>  {
		@Override
		protected Object doInBackground(String... urls) {


			if(android.os.Debug.isDebuggerConnected())
				android.os.Debug.waitForDebugger();

			Object object = null;

			try {
				object =  in.readObject();
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				
			}

			return object;

		}  
		//protected void onPostExecute(Long result) {

		// }
	}


	

	

	



}







