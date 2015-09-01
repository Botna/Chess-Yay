package chess2.Activities;


import chess2.Services.ChessService;
import chess2.Views.ChessGameView;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.botna.chess2.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

import chess2.source.GameBoard;
import chess2.source.GameHistoryContainer;
import chess2.source.SimulatedGame;
import chess2.source.SingleSpace;

public class PlayGameActivity
        extends ActionBarActivity
{

    protected String myName = "";
    protected String myPWord = "";
    private GameBoard myGame;
    protected Toast toast = null;
    public static final String INITIAL = "INITIAL";
    public static final String PREFERENCES = "com.botna.chess2";
    public static final String LOGGED = "LOGGED";
    private static final String RM = "RETURNMESSSAGE";
    private static final String RS = "RETURNSTATE";
    private static final String RO = "RETURNOBJECT";
    private static final String RA = "RETURNOBJECTARRAY";
    private UUID myCurrentGuid = null;
    private Intent myService;
    private View myView = null;


    private BroadcastReceiver broadCastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            updateActivity(intent);
        }
    };


    private void updateActivity(Intent intent) {



        String returnState = intent.getStringExtra(RS);
        String returnMessage = intent.getStringExtra(RM);
        Bundle b = null;

        switch(returnState) {

            case "SKIRMISHSENT":
            case "MOVEMADE":

                //need to update the game.
                myService.putExtra("STATE", "REFRESHGAME");
                myService.putExtra("GUID", myCurrentGuid.toString());
                myService.putExtra("USERNAME", myName);
                myService.putExtra("PASSWORD", myPWord);
                startService(myService);

                break;
            case "GAMELOADED":

                b = intent.getExtras();
                GameBoard temp = (GameBoard)b.getSerializable(RO);
                myGame = temp;
                ((ChessGameView)myView).setGame(temp);
                ((ChessGameView)myView).setName(myName);
                ((ChessGameView)myView).setMessage(null);
                ((ChessGameView) myView).forceInvalidate();
                break;
            case "JUSTTOAST":
            case "ERROR":
                if(toast != null)
                    toast.cancel();

                toast = Toast.makeText(getApplicationContext(),returnMessage,Toast.LENGTH_LONG);
                toast.show();
                break;

        }
    }


    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState)
    {
        //setContentView(R.layout.play_game_activity);
        super.onCreate(savedInstanceState);
        myService = new Intent(this, ChessService.class);

         myView = new ChessGameView(this);


        myView.setBackgroundColor(-1);

        Bundle extras = getIntent().getExtras();
        this.myGame = (GameBoard)extras.getSerializable(RO);
        myCurrentGuid = myGame.getGuid();
        myName = extras.getString(RM);
        ((ChessGameView)myView).setGame(myGame);
        ((ChessGameView)myView).setActivity(this);
        ((ChessGameView)myView).setName(myName);
        myView.setBackgroundResource(R.drawable.background);
        setContentView(myView);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.game_menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.game_history:
                Intent gameHistoryIntent = new Intent(this, GameHistoryActivity.class);
                Bundle b = new Bundle();
                File theFile = new File(this.getFilesDir(), myGame.getGuid().toString());

                try
                {
                    if(theFile.exists())
                    {
                        //file exists, which it should be guaranteed to.

                        //read in ze file.
                        InputStream fileIn = new FileInputStream(theFile);
                        InputStream bufferIn = new BufferedInputStream(fileIn);
                        ObjectInputStream in = new ObjectInputStream(bufferIn);
                        GameHistoryContainer theHistory = (GameHistoryContainer) in.readObject();


                        if(theHistory != null)
                        {
                            b.putSerializable("HISTORY", theHistory);
                            b.putString("NAME", myName);
                            gameHistoryIntent.putExtras(b);
                            startActivity(gameHistoryIntent);
                        }
                        else
                        {
                            this.toast("History not loaded right");
                        }
                    }
                    else
                    {
                        this.toast("File not created.");
                    }
                }
                catch(Exception e)
                {

                }

//
                return true;


            case R.id.game_forfeit:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                // Add the buttons
                builder.setTitle("Forfeit Game?");
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        myService.putExtra("STATE", "FORFEIT");
                        myService.putExtra("USERNAME", myName);
                        myService.putExtra("PASSWORD", myPWord);
                        myService.putExtra("GUID", myCurrentGuid.toString());
                        startService(myService);
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        //do nothing
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;

            case R.id.game_simulate:
                Intent gameSimulationIntent = new Intent(this, SimulateActivity.class);
                Bundle bundle = new Bundle();
                //clone the game, and cast as a SimulatedGame.
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos;

                    oos = new ObjectOutputStream(baos);

                    oos.writeObject(myGame);

                    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    GameBoard clonedGB = (GameBoard) ois.readObject();

                    if(clonedGB != null)
                    {
                        bundle.putSerializable("GAME", clonedGB);
                        bundle.putString("NAME", myName);
                        gameSimulationIntent.putExtras(bundle);
                        startActivity(gameSimulationIntent);
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }






        }


        return super.onOptionsItemSelected(item);
    }

    public void onResume()
    {
        super.onResume();
        registerReceiver(broadCastReceiver, new IntentFilter(ChessService.SERVER_RESPONSE));
        SharedPreferences prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        myName = prefs.getString("USERNAME", null);
        myPWord = prefs.getString("PASSWORD", null);


    }

    public void sendMove(int[] move, char promoteChar)
    {

        //something happened, we will want to refresh the CampaignActivity fragment.  Lets set a result

        Intent intent = new Intent();
        intent.putExtra("RESULT", "REFRESH");
        setResult(2,intent);
        myService.putExtra("STATE", "ATTEMPTMOVE");
        myService.putExtra("USERNAME", myName);
        myService.putExtra("PASSWORD", myPWord);
        myService.putExtra("MOVE", move);
        myService.putExtra("PROMOTE", promoteChar);
        myService.putExtra("GUID", myCurrentGuid.toString());
        startService(myService);
    }
    public void sendSkirmish(int wager)
    {
        Intent intent = new Intent();
        intent.putExtra("RESULT", "REFRESH");
        setResult(2, intent);
        myService.putExtra("STATE", "ENACTSKIRMISH");
        myService.putExtra("USERNAME", myName);
        myService.putExtra("PASSWORD", myPWord);
        myService.putExtra("WAGER", wager);
        myService.putExtra("GUID", myCurrentGuid.toString());
        startService(myService);
    }

    public void finishSkirmish(int wager)
    {
        Intent intent = new Intent();
        intent.putExtra("RESULT", "REFRESH");
        setResult(2,intent);
        myService.putExtra("STATE", "FINISHSKIRMISH");
        myService.putExtra("USERNAME", myName);
        myService.putExtra("PASSWORD", myPWord);
        myService.putExtra("WAGER", wager);
        myService.putExtra("GUID", myCurrentGuid.toString());
        startService(myService);
    }

    public void onPause()
    {
        super.onPause();
        unregisterReceiver(broadCastReceiver);
    }

    public void onDestroy()
    {
        super.onDestroy();
        ((ChessGameView)myView).deconstruct();

    }
    public void toast(String message)
    {
        toast = Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT);
        toast.show();



    }

    //@Override
//    public void onBackPressed()
//    {
//        Intent intent = new Intent();
//        intent.putExtra("RESULT", "REFRESH");
//        setResult(2,intent);
//        finish();
//    }

    public void suppressToast() {
        if(toast != null) {
            toast.cancel();
        }
    }
}
