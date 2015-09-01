package chess2.Activities;


import chess2.Services.ChessService;
import serverClasses.ChessClass;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
;
import android.support.v7.widget.ListPopupWindow;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;
import android.widget.Toast;

import com.botna.chess2.R;

public class NewGameActivity extends ActionBarActivity {

    protected String myName = "";
    protected String myPWord = "";
	protected ChessClass theGame = null;
    public static final String PREFERENCES = "com.botna.chess2";
    private static final String RM = "RETURNMESSSAGE";
    private static final String RS = "RETURNSTATE";
    private Toast toast = null;
    protected boolean backJustPressed = false;
	protected String variant = null;
	protected String[] variants={"Classic", "Reapers", "Nemesis","Empowered",
			"Animals","Two Kings"};

    Intent myService;
    private String opponentName="";

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

        switch(returnState)
        {
            case "GAMECREATED":
                if(toast != null)
                    toast.cancel();

                toast = Toast.makeText(getApplicationContext(), returnMessage, Toast.LENGTH_LONG);
                toast.show();
                Intent temp = new Intent();
                intent.putExtra("RESULT", "REFRESH");
                setResult(2,intent);
                finish();

                break;
            case "JUSTTOAST":
            case "ERROR":
                if(toast != null)
                    toast.cancel();

                toast = Toast.makeText(getApplicationContext(), returnMessage, Toast.LENGTH_LONG);
                toast.show();
                break;
        }
    }



    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        myService = new Intent(this, ChessService.class);
	}

    @Override
    public void onResume()
    {
        registerReceiver(broadCastReceiver, new IntentFilter(ChessService.SERVER_RESPONSE));
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        String restoredUName = prefs.getString("USERNAME", null);
        String restoredPWord = prefs.getString("PASSWORD", null);
        myName = restoredUName;
        myPWord = restoredPWord;
        setContentView(R.layout.new_game_menu);

        //we attempt to 'relog' everytime resume is called.
        //Since we have the ability to lose our connection if this activity is pusehd
        //to the background.   If it is, we want to rewind back to this position
        //and a re-authentication is necessary.
        //myService.putExtra("STATE", "LOGINATTEMPT");
       // String[] temp = new String[2];
       // temp[0] = restoredUName;
       // temp[1] = restoredPWord;
       // myService.putExtra("PAYLOAD", temp);
       // startService(myService);




    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return true;
	}

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
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	

    public void sendRequest(View view)
	{
		ClassChoiceDialog choiceDialog = new ClassChoiceDialog();
        //change the size of choice dialog to fit the screen better
		FragmentManager fm = getSupportFragmentManager();
        choiceDialog.show(fm, "Dialog Fragment");

	}

    public void startInviteByName(View view)
    {
       LayoutInflater inflater = this.getLayoutInflater();
       AlertDialog.Builder builder = new AlertDialog.Builder(this);



       final View temp = inflater.inflate(R.layout.simple_edit_text,null);
      builder.setTitle("Enter a name:");
       builder.setView(temp);
       builder.setNeutralButton("Send it", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {

               EditText thing = (EditText) temp.findViewById(R.id.sample);
               opponentName = thing.getText().toString();
               ClassChoiceDialog choiceDialog = new ClassChoiceDialog();
               //change the size of choice dialog to fit the screen better
               FragmentManager fm = getSupportFragmentManager();
               choiceDialog.show(fm, "Dialog Fragment");
           }
       });


       builder.show();
    }


    public void gatherRecentOpponents(View view)
    {
        //send a request to the server for our games, so we can gather all of the people we've played recently.

    }




    @SuppressLint("ValidFragment")
	public class ClassChoiceDialog extends DialogFragment{
		
		 public Dialog onCreateDialog(Bundle savedInstanceState) {
			
			 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
             View myView = getActivity().getLayoutInflater().inflate(R.layout.class_choice,null);
             builder.setView(myView);

			 return builder.create();
		 }
	}
	public void chooseTeam(View v)
    {
        String variant = "";
        switch(v.getId())
        {
            case R.id.classicbutton:
                variant = variants[0];
                break;

            case R.id.nemesisbutton:
                variant = variants[2];
                break;

            case R.id.reapersbutton:
                variant = variants[1];
                break;

            case R.id.empoweredbutton:
                variant = variants[3];
                break;

            case R.id.animalsbutton:
                variant = variants[4];
                break;

            case R.id.twokingsbutton:
                variant = variants[5];
                break;

        }



        myService.putExtra("STATE", "CREATEGAME");
        myService.putExtra("USERNAME", myName);
        myService.putExtra("PASSWORD", myPWord);
        myService.putExtra("OTHERPLAYER",opponentName);
        myService.putExtra("MYVARIANT", variant);
        startService(myService);
    }

}
