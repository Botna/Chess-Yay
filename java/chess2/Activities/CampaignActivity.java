package chess2.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ExpandableListActivity;

import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.botna.chess2.R;
import com.idunnololz.widgets.AnimatedExpandableListView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.UUID;

import chess2.Adapters.ExpandableGameListAdapter;

import chess2.Services.ChessService;
import chess2.Views.ChessGameView;
import chess2.source.GameBoard;
import chess2.source.GameContainer;
import chess2.source.GameHistoryContainer;

/**
 * Created by Botna on 5/27/2015.
 // */




public class CampaignActivity extends ActionBarActivity {

    private AnimatedExpandableListView expandableList;
    private ExpandableGameListAdapter adapter;
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();
    protected View rootView;
    protected String myName = "";
    protected String myPWord = "";
    protected GameContainer[] myGames;
    protected Toast toast = null;
    public static final String INITIAL = "INITIAL";
    public static final String PREFERENCES = "com.botna.chess2";
    public static final String LOGGED = "LOGGED";
    private static final String RM = "RETURNMESSSAGE";
    private static final String RS = "RETURNSTATE";
    private static final String RO = "RETURNOBJECT";
    private static final String RA = "RETURNOBJECTARRAY";
    private Intent myService;
    protected String[] variants={"Classic", "Reapers", "Nemesis","Empowered",
            "Animals","Two-Kings"};


    private BroadcastReceiver broadCastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            updateActivity(intent);
        }
    };


    private void updateActivity(Intent intent) {

        //Look at the comments!
        String returnState = intent.getStringExtra(RS);
        String returnMessage = intent.getStringExtra(RM);
        Bundle b = null;

        switch(returnState) {

            case "REQUESTEDGAMES":
                b = intent.getExtras();
                Object[] tempObj  = (Object[])b.getSerializable(RA);
                myGames = new GameContainer[tempObj.length];
                for(int i =0; i< myGames.length; i++)
                {
                    myGames[i] = (GameContainer)tempObj[i];
                }
                rebuildUI();
                break;

            case "GAMELOADED":


                b = intent.getExtras();
                Intent playGameIntent = new Intent(this, PlayGameActivity.class);
                playGameIntent.putExtras(b);
                startActivityForResult(playGameIntent, 0);
                break;
            case "BLACKUPDATE":
                ClassChoiceDialog classChoiceDialog = new ClassChoiceDialog();

                FragmentManager ft = getSupportFragmentManager();
                classChoiceDialog.show(ft, "Dialog Fragment");

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null) {
            String message = data.getStringExtra("RESULT");

            //if (message.equals("REFRESH"))
             //   regatherGames();

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        SharedPreferences prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        myName = prefs.getString("USERNAME", null);
        myPWord = prefs.getString("PASSWORD", null);

        myService = new Intent(this, ChessService.class);


        Bundle extras = getIntent().getExtras();


        Object[] tempObj  = (Object[])extras.getSerializable(RA);
        myGames = new GameContainer[tempObj.length];
        for(int i =0; i< myGames.length; i++)
        {
            myGames[i] = (GameContainer)tempObj[i];
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.campaign_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.create_game:


                //Kick off a new activity of NewGame!

                Intent intent = new Intent(this, NewGameActivity.class);


                startActivityForResult(intent, 0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onResume()
    {
        super.onResume();

        registerReceiver(broadCastReceiver, new IntentFilter(ChessService.SERVER_RESPONSE));
        regatherGames();
        rebuildUI();
    }
    public void onPause()
    {
        super.onPause();
        unregisterReceiver(broadCastReceiver);
    }

    public void regatherGames()
    {
        myService.putExtra("STATE", "REQUESTGAMES");
        myService.putExtra("USERNAME", myName);
        myService.putExtra("PASSWORD", myPWord);
        startService(myService);


    }

    public void rebuildUI() {

        if (myGames.length > 0) {

            setContentView(R.layout.campaign_activity);
            expandableList = (AnimatedExpandableListView) findViewById(R.id.list); // you can use (        ExpandableListView) findViewById(R.id.list)

            //expandableList.setDividerHeight(2);
            expandableList.setGroupIndicator(null);
            expandableList.setClickable(true);
            //expandableList.setChildDivider(this.getResources().getDrawable(R.drawable.childdivider));




            adapter = new ExpandableGameListAdapter(this);

            adapter.addItems(myGames, myName, this);


            //adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
            expandableList.setAdapter(adapter);
            expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    // We call collapseGroupWithAnimation(int) and
                    // expandGroupWithAnimation(int) to animate group
                    // expansion/collapse.
                    if (expandableList.isGroupExpanded(groupPosition)) {
                        expandableList.collapseGroupWithAnimation(groupPosition);
                    } else {
                        expandableList.expandGroupWithAnimation(groupPosition);
                    }
                    return true;
                }

            });
            // expandableList.invalidate();
            //expandableList.setOnChildClickListener(adapter);
        } else {


            TextView theView = new TextView(getApplicationContext());
            theView.setText("No games.  Click the '+' above!");
            theView.setBackgroundColor(0xFF00A2E8);
            theView.setTextSize(43);
            setContentView(theView);
        }
    }



    public void startGame(UUID guid)
    {

        myService.putExtra("STATE", "LOADGAME");
        myService.putExtra("USERNAME", myName);
        myService.putExtra("PASSWORD", myPWord);
        myService.putExtra("GUID", guid.toString());
        startService(myService);

    }


    @SuppressLint("ValidFragment")
    public class ClassChoiceDialog extends DialogFragment {

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

    public void createGame(View view)
    {


        //figure out who this was clicked for, and whether or not we have an active game

        int thing = 0;
        thing ++;
    }

}
