package com.botna.chess2;




import java.util.ArrayList;


import chess2.Services.ChessService;

import chess2.source.GameContainer;
import chess2.Activities.*;
import serverClasses.ChessClass;
import serverClasses.PushRegister;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;




public class MainActivity extends ActionBarActivity {

    public static final String PREFERENCES = "com.botna.chess2";
    public static final String INITIAL = "INITIAL";
    public static final String LOGGED = "LOGGED";
    private static final String RM = "RETURNMESSSAGE";
    private static final String RS = "RETURNSTATE";
    private static final String RO = "RETURNOBJECT";
    private static final String RA = "RETURNOBJECTARRAY";

    protected String myName = "";
    protected String myPWord = "";
    protected ChessClass theGame;
    protected Toast toast = null;
    protected String[] variants={"Classic", "Reapers", "Nemesis","Empowered",
            "Animals","Two-Kings"};
    protected String[] myGamesString;
    protected GameContainer[] myGames;
    protected String state = null;
    protected boolean transitioning = false;
    protected MainActivity pointer = this;
    private Intent myService;


    private BroadcastReceiver broadCastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            updateActivity(intent);
        }
    };


    private void updateActivity(Intent intent)
    {


        String returnState = intent.getStringExtra(RS);
        String returnMessage = intent.getStringExtra(RM);
        Bundle b = null;

        switch(returnState)
        {
            case "NOSAVEDLOGIN":

                displayLogin();
                break;
            case "USERCREATED":
                LayoutInflater inflater = this.getLayoutInflater();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);


                final String finalUName = intent.getExtras().getString("USERNAME");
                final String finalPWord = intent.getExtras().getString("PASSWORD");
                final View temp = inflater.inflate(R.layout.simple_edit_text,null);
                builder.setTitle("User Created. Login?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        loginButton(finalUName, finalPWord);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        displayLogin();
                    }
                });

                builder.show();



                break;
            case "ALREADYLOGGED":
                setContentView(R.layout.activity_main_menu);
                state = LOGGED;
                break;
            case "AUTHENTICATED":
                setContentView(R.layout.activity_main_menu);
                state = LOGGED;

                SharedPreferences prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
                String restoredUName = prefs.getString("USERNAME", null);
                String restoredPWord = prefs.getString("PASSWORD", null);
                myName = restoredUName;
                myPWord = restoredPWord;

                if(restoredUName == null || restoredPWord == null)
                {
                    //need to save our username and password in the preferences now.
                    //no previous shared preferences, lets update it.

                    SharedPreferences.Editor editor = getSharedPreferences(PREFERENCES, MODE_PRIVATE).edit();
                    editor.putString("USERNAME", myService.getStringArrayExtra("PAYLOAD")[0]);
                    editor.putString("PASSWORD",myService.getStringArrayExtra("PAYLOAD")[1]);
                    editor.commit();
                    myName = myService.getStringArrayExtra("PAYLOAD")[0];
                    myPWord = myService.getStringArrayExtra("PAYLOAD")[1];


                }
                break;

            case "REQUESTEDGAMES":

                suppressNotification();
                b = intent.getExtras();
                Object[] tempObj  = (Object[])b.getSerializable(RA);
                myGames = new GameContainer[tempObj.length];
                for(int i =0; i< myGames.length; i++)
                {
                    myGames[i] = (GameContainer)tempObj[i];
                }

                Intent campaign = new Intent(this, CampaignActivity.class);
                campaign.putExtras(b);
                startActivity(campaign);

                break;

            case "BLACKUPDATE":
                ClassChoiceDialog classChoiceDialog = new ClassChoiceDialog();

                FragmentManager fm = getSupportFragmentManager();
                classChoiceDialog.show(fm, "Dialog Fragment");
                break;
            case "GAMELOADED":

                state = "PLAYINGGAME";
                b = intent.getExtras();
                Intent playGameIntent = new Intent(this, PlayGameActivity.class);
                playGameIntent.putExtras(b);
                startActivityForResult(playGameIntent, 0);
                break;
            case "DISCONNECTED":
                //Cant connect with the server.

                //if we ahve useranme and passwrod saved, just take them to main screen,
                //else, take them to login screen.

                if(myName.equals(""))
                {
                    displayLogin();
                }
                else
                {

                    setContentView(R.layout.activity_main_menu);
                }

            case "JUSTTOAST":
            case "ERROR":


                    //the only error we can get here is a user already exists or user doesnt exist or wrong password.  ALl fo these prompt the login popup agian.
                if(toast != null)
                    toast.cancel();

                toast = Toast.makeText(getApplicationContext(),returnMessage,Toast.LENGTH_LONG);
                toast.show();

                if(!returnMessage.equals("Cant Connect"))
                {
                    displayLogin();
                }

                break;


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = "INITIAL";

        myService = new Intent(this, ChessService.class);

        setContentView(R.layout.activity_main_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
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
        pointer = this;

    }


    @Override
    public void onPause()
    {
        super.onPause();


        //only do this if we are actually going into a 'paused' state.
        //if we are transitioning into a new activity, just unbind the receiver
        //but do not stop the service.   the New activity
        //will setup the new reciever and communicate with the service as necessary.
        unregisterReceiver(broadCastReceiver);


    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();
        stopService(myService);
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

        //find out if username and password is stored already.

        registerReceiver(broadCastReceiver, new IntentFilter(ChessService.SERVER_RESPONSE));

        SharedPreferences prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        String restoredUName = prefs.getString("USERNAME", null);
        String restoredPWord = prefs.getString("PASSWORD", null);
        String restoredRegid = prefs.getString("REGID", null);
        if(restoredRegid == null)
        {
            //This way, we always make sure we have one for our current instance.
            PushRegister register = new PushRegister(this);
            register.getRegId();
        }
        if(restoredUName != null && restoredPWord != null && restoredRegid != null)
        {
            myName = restoredUName;
            myPWord = restoredPWord;
           login(restoredUName, restoredPWord,restoredRegid);

        }
        else
        {
            displayLogin();


        }


    }

    public void displayLogin()
    {

        //Just display login screen.
        LayoutInflater inflater = this.getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);



        final View temp = inflater.inflate(R.layout.activity_main,null);

        builder.setView(temp);
        builder.setCancelable(false);
        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                EditText nameText = (EditText) temp.findViewById(R.id.username);
                EditText pwordText = (EditText) temp.findViewById(R.id.password);
                String userName = nameText.getText().toString();
                String pWord = pwordText.getText().toString();
                loginButton(userName, pWord);
            }
        });

        builder.setNegativeButton("Register", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                EditText nameText = (EditText) temp.findViewById(R.id.username);
                EditText pwordText = (EditText) temp.findViewById(R.id.password);
                String userName = nameText.getText().toString();
                String pWord = pwordText.getText().toString();

                register(userName, pWord);
            }
        });

        builder.show();

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

            case R.id.action_settings:
                // openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void register(View view)
    {
        EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        hideSoftKeyboard();

        if(username.equals("") || password.equals(""))
        {
            toast = Toast.makeText(getApplicationContext(),"Enter Username/Password",Toast.LENGTH_LONG);
            toast.show();
            displayLogin();
        }
        else if(username.length() >12)
        {
            toast = Toast.makeText(getApplicationContext(),"Name max length is 12 chars",Toast.LENGTH_LONG);
            toast.show();
            displayLogin();
        }
        else
        {
            myService.putExtra("STATE", "REGISTERATTEMPT");
            String[] temp = new String[2];
            temp[0] = username;
            temp[1] = password;
            myService.putExtra("USERNAME", username);
            myService.putExtra("PASSWORD", password);
            startService(myService);
        }
    }

    public void register(String username, String pword)
    {


        hideSoftKeyboard();

        if(username.equals("") || pword.equals(""))
        {
            toast = Toast.makeText(getApplicationContext(),"Enter Username/Password",Toast.LENGTH_LONG);
            toast.show();
            displayLogin();
        }
        else
        {
            myService.putExtra("STATE", "REGISTERATTEMPT");
            String[] temp = new String[2];
            temp[0] = username;
            temp[1] = pword;
            myService.putExtra("USERNAME", username);
            myService.putExtra("PASSWORD", pword);
            startService(myService);
        }
    }
    public void loginButton(View view)
    {


        EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();


        SharedPreferences prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        String restoredRegid = prefs.getString("REGID", null);
        hideSoftKeyboard();

        if(username.equals("") || password.equals(""))
        {
            toast = Toast.makeText(getApplicationContext(),"Enter Username/Password",Toast.LENGTH_LONG);
            toast.show();
            displayLogin();
        }
        else
        {
            login(username, password,restoredRegid);
        }
    }
    public void loginButton(String username, String password)
    {
        SharedPreferences prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        String restoredRegid = prefs.getString("REGID", null);
        hideSoftKeyboard();

        if(username.equals("") || password.equals(""))
        {
            toast = Toast.makeText(getApplicationContext(),"Enter Username/Password",Toast.LENGTH_LONG);
            toast.show();
            displayLogin();
        }
        else
        {
            login(username, password,restoredRegid);
        }
    }
    public void login(String uname, String password, String regid)
    {
        //send the login request to the service, and await a response.
        myService.putExtra("STATE", "LOGINATTEMPT");
        String[] temp = new String[3];
        temp[0] = uname;
        temp[1] = password;
        temp[2] = regid;
        myService.putExtra("PAYLOAD", temp);
        startService(myService);
    }

    public void newGame(View view)
    {
        state = "NEWGAMELAUNCHED";
        transitioning = true;
        Intent intent = new Intent(this, NewGameActivity.class);


        startActivityForResult(intent, 0);
    }

    public void rulesLaunch(View view)
    {

        Intent intent = new Intent(this, TempActivity.class);


        startActivity(intent);

    }
    public void accountLaunch(View view)
    {
        Intent intent = new Intent(this, TempActivity.class);


        startActivity(intent);
    }
    public void settingsLaunch(View view)
    {
        Intent intent = new Intent(this, TempActivity.class);





        startActivity(intent);
    }

    public void logout(View view)
    {
        Intent intent = new Intent(this, TempActivity.class);


        startActivity(intent);
    }
    public void getGames()
    {

        myService.putExtra("STATE", "REQUESTGAMES");
        myService.putExtra("USERNAME", myName);
        myService.putExtra("PASSWORD", myPWord);
        startService(myService);

    }

    public void playGame(View view)
    {

        getGames();

    }

    public void suppressNotification()
    {
        NotificationManager NM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NM.cancel(38);
    }
    @SuppressLint("ValidFragment")
    public  class GameChoiceDialog extends DialogFragment{

        public  Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


            builder.setTitle("Pick a Game").setItems(myGamesString, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {


                    myService.putExtra("STATE", "LOADGAME");
                    myService.putExtra("USERNAME", myName);
                    myService.putExtra("PASSWORD", myPWord);
                    myService.putExtra("GUID", myGames[which].getGuid().toString());
                    startService(myService);
                }
            });


            return builder.create();
        }
    }
    @SuppressLint("ValidFragment")
    public class ClassChoiceDialog extends DialogFragment{

        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Choose a class").setItems(variants, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    myService.putExtra("STATE", "CHOOSEBLACK");
                    myService.putExtra("USERNAME", myName);
                    myService.putExtra("PASSWORD", myPWord);
                    myService.putExtra("CHOICE", variants[which].charAt(0));

                    startService(myService);
                }
            });


            return builder.create();
        }
    }
}

