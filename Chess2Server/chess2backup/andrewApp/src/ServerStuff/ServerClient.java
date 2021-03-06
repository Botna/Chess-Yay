package ServerStuff;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
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


public class ServerClient 
{

	public ServerClient() throws UnknownHostException, IOException, InterruptedException, ExecutionException{
		new CreateTask().execute().get();
	}
	 
	
	
	
	public Boolean loggedIn()
	{
		return loggedIn;
	}

	public void login(String userName, String password) throws Exception {

		loggedIn = false;

		String payload = "";
		String response = "";
		String[] splitString;
		if (client.isBound()) 
		{
			payload = "1:" + userName + ":" + password;
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

	
	public Object sendPacket(String packet)
	{
		Object response = null;
		try {
			out.writeObject(packet);
			
			
			response = new InTask().execute().get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

	private class LoginTask   extends AsyncTask<String, Void, String>  {
		@Override
		protected String doInBackground(String... urls) {


			if(android.os.Debug.isDebuggerConnected())
				android.os.Debug.waitForDebugger();

			String[] temp;


			if (client != null){


				temp = urls[0].split(":");
				try {
					return loginAttempt(temp[0], temp[1]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return "Client Not initialized";

		}


	}


	private class CreateTask   extends AsyncTask<String, Void, String>  {
		@Override
		protected String doInBackground(String... urls) {


			if(android.os.Debug.isDebuggerConnected())
				android.os.Debug.waitForDebugger();

			String[] temp;

			try{
				if (client == null){
					
					client = new Socket("50.158.235.234", 9812);
					
					outToServer = client.getOutputStream();
					out =
							new ObjectOutputStream(outToServer);
					inFromServer = client.getInputStream();
					in =
							new ObjectInputStream(inFromServer);

					return "success";
				}

			}
			catch (Exception e)
			{
				return e.getMessage();
			}
			return "problem";
		}

	}
	private String loginAttempt(String userName, String password) throws Exception
	{
		//String temp = username + ":" + password;
		//new LoginTask().execute(temp).get();

		loggedIn = false;

		String payload = "";
		String response = "";
		String[] splitString;
		if (client.isBound()) 
		{
			payload = "1:" + userName + ":" + password;
			try {
				out.writeObject(payload);
			} catch (IOException e) {
				throw new Exception("Sending Login Information failed");
			}


			response = (String) in.readObject();

			splitString  = response.split(":");
			if (splitString.length >1 )
			{//signifies the appropriate length for a return packet
				if(splitString[0].equals("1"))
				{//Login Functionality

					if(splitString[1].equals("0"))
					{
						loggedIn = true;
						return "Logged in!";


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
		else
		{
			throw new Exception("Server is down.");
		}

		return "Shouldnt happen";
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







