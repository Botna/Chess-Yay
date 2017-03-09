package com.example.andrewapp;



import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import chess2.source.GameContainer;

import com.example.andrewapp.NewGameActivity.ClassChoiceDialog;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.ListPopupWindow;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import ServerStuff.ChessClass;
import ServerStuff.ServerClient; 


public class MainActivity extends ActionBarActivity {

	public static final String EXTRA_MESSAGE = "com.example.andrewApp.MESSAGE";
	protected ChessClass theGame;

	protected Toast toast = null;
	protected ListPopupWindow listPopupWindow;
	protected String variant = null;
	protected String[] variants={"Classic", "Reapers", "Nemesis","Empowered",
			"Animals","Two-Kings"};
	protected String[] myGamesString;
	protected GameContainer[] myGames;
	protected String myName;
	protected String state = null;
	protected MainActivity pointer = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		state = "INITIAL";

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onRestart()
	{
		super.onRestart();

	}
	@Override
	public void onStart()
	{
		super.onStart();

		//initializes the server.  Our first activity is now waiting with UserName/ Password prompts and will log in when ready. 
		try {
			if (theGame == null)
			{
				theGame = new ChessClass();

			}

		} catch (IOException | InterruptedException
				| ExecutionException e) {

			e.printStackTrace();
		}

	}


	@Override
	public void onPause()
	{
		super.onPause();


	}

	public void hideSoftKeyboard() {
		if(getCurrentFocus()!=null) {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		}
	}
	@Override
	public void onResume()
	{
		super.onResume();
		if ( state.equals("INITIAL"))
		{
			setContentView(R.layout.activity_main);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		else
		{
			setContentView(R.layout.activity_main_menu);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//		int id = item.getItemId();
		//		if (id == R.id.action_settings) {
		//			
		//			return true;
		//		}
		//		return super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case R.id.action_search:
			//  openSearch();
			return true;
		case R.id.action_settings:
			// openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}


	public void register(View view)
	{
		//Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText usernameText = (EditText) findViewById(R.id.username);
		EditText passwordText = (EditText) findViewById(R.id.password);
		String username = usernameText.getText().toString();
		String password = passwordText.getText().toString();

		if(username.equals("") || password.equals(""))
		{

			toast = Toast.makeText(getApplicationContext(),"Enter Username/Password",Toast.LENGTH_LONG);
			toast.show();
		}
		else
		{

			String response;
			state = "REGISTERING";
			try {
				//create the client and login in.
				response = theGame.register(username, password);
				toast = Toast.makeText(getApplicationContext(), response,Toast.LENGTH_LONG);
				toast.show();


			} catch (Exception e) {

				toast = Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
				toast.show();
			}

		}

	}
	public void login(View view) 
	{
		//Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText usernameText = (EditText) findViewById(R.id.username);
		EditText passwordText = (EditText) findViewById(R.id.password);
		String username = usernameText.getText().toString();
		String password = passwordText.getText().toString();
		state = "LOGGINGIN";
		hideSoftKeyboard();

		if(username.equals("") || password.equals(""))
		{

			toast = Toast.makeText(getApplicationContext(),"Enter Username/Password",Toast.LENGTH_LONG);
			toast.show();
		}
		else
		{
			try {
				//create the client and login in.
				String status = theGame.login(username, password);

				myName = username;
				//Logged in.  Toast and change layout.
				setContentView(R.layout.activity_main_menu);
				toast = Toast.makeText(getApplicationContext(),  status, Toast.LENGTH_SHORT);
				toast.show();


			} catch (Exception e) {

				//errorIntent.putExtra(EXTRA_MESSAGE, e.getMessage());
				//startActivity(errorIntent);
				toast = Toast.makeText(getApplicationContext(),  (CharSequence) e.getMessage(), Toast.LENGTH_LONG);
				toast.show();
			}



		}
	}

	public void newGame(View view) 
	{
		state = "NEWGAMELAUNCHED";
		Intent intent = new Intent(this, NewGameActivity.class);

		startActivityForResult(intent, 0);
	}

	public void playGame(View view)
	{


		try {
			theGame.refreshGames();
			myGames = theGame.getCurrentGames();
			
		} catch (Exception e) {

			toast = Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
			toast.show();
		}

		int counter = 1;


		if(myGames.length == 0 || myGames[0] == null)
		{
			toast = Toast.makeText(getApplicationContext(),"You dont have any games =(",Toast.LENGTH_LONG);
			toast.show();
		}
		else
		{
			ArrayList<String> list = new ArrayList<String>();
			String temp;
			for(int i = 0; i< myGames.length; i++)
			{
				if(myGames[i] != null)
				{
					temp = myGames[i].getWhiteTeam() + " vs " + myGames[i].getBlackTeam() ;

					if(myGames[i].getTurn().equals(myName))
						temp = temp + " - Your Turn";
					else
						temp = temp + " - Their Turn";
					list.add(temp);
					counter++;
				}
			}
			myGamesString = null;
			myGamesString = list.toArray(new String[list.size()]);
			GameChoiceDialog gameDialog = new GameChoiceDialog();
			state = "GAMESELECTED";
			FragmentManager fm = getSupportFragmentManager();
			gameDialog.show(fm, "Dialog Fragment");
		}


	}


	@SuppressWarnings("unused")
	@Override protected void onActivityResult(int requestCode, int resultCode, Intent Sender) { 
		super.onActivityResult(requestCode, resultCode, Sender); 



		if (resultCode == RESULT_OK){ 

			Bundle basket = Sender.getExtras(); 
			switch(state)
			{
			case "NEWGAMELAUNCHED":
				String mode = basket.getString("MODE");
				switch (mode) { 

				case "NEWGAME": 

					try 
					{
						String result = theGame.createNewGame(basket.getString("OPPONENT"),basket.getString("CLASS"));
						toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT);
						toast.show();
						break;
					} catch (Exception e) 
					{
						toast = Toast.makeText(getApplicationContext(),  e.getMessage(), Toast.LENGTH_SHORT);
						toast.show();
					}

					break;
				}

				break;

			case "PLAYINGGAME":
				//we should have a game move waiting for us.
				String result=  null;
				int[] move = (int[]) basket.getSerializable("MOVE");
				try {
					//result = theGame.sendMove(basket.getInt("OLDX"), basket.getInt("OLDY"), basket.getInt("NEWX"), basket.getInt("NEWY"));
					result = theGame.sendMove(move);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
					toast.show();
				}
				toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT);
				toast.show();
			}

		} 
	}


	public class GameChoiceDialog extends DialogFragment{

		public Dialog onCreateDialog(Bundle savedInstanceState) {

			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


			builder.setTitle("Pick a Game").setItems(myGamesString, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {


					String success = null;

					try {
						success = theGame.loadGame(which);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						toast = Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
						toast.show();
					}

					if(success.equals("SUCCESS"))
					{
						//start my intent with the game string
						state = "PLAYINGGAME";
						Intent intent = new Intent(pointer, PlayGameActivity.class);
						Bundle b = new Bundle();
						b.putSerializable("GAME", theGame.getGame());
						b.putString("MYNAME", theGame.getName());
						intent.putExtras(b);
						startActivityForResult(intent, 0);
					}
					else
					{
						ClassChoiceDialog classChoiceDialog = new ClassChoiceDialog();

						FragmentManager fm = getSupportFragmentManager();
						classChoiceDialog.show(fm, "Dialog Fragment");
					}
				}
			});


			return builder.create();
		}
	}

	public class ClassChoiceDialog extends DialogFragment{

		public Dialog onCreateDialog(Bundle savedInstanceState) {

			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Choose a class").setItems(variants, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					try {
						theGame.updateClassChoice(variants[which].charAt(0));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						toast = Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
						toast.show();
					}

					state = "PLAYINGGAME";
					Intent intent = new Intent(pointer, PlayGameActivity.class);
					Bundle b = new Bundle();
					b.putSerializable("GAME", theGame.getGame());
					b.putString("MYNAME", theGame.getName());
					intent.putExtras(b);
					startActivityForResult(intent, 0);
				}
			});


			return builder.create();
		}
	}
}

