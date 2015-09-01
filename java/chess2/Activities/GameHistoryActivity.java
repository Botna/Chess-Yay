package chess2.Activities;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.botna.chess2.R;

import chess2.Services.ChessService;
import chess2.Views.ChessGameView;
import chess2.Views.ChessHistoryView;
import chess2.source.GameHistoryContainer;


public class GameHistoryActivity
        extends ActionBarActivity
{
    protected Toast toast = null;
    private View myView = null;
    private GameHistoryContainer myHistory = null;
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        GameHistoryContainer myHistory = (GameHistoryContainer) extras.getSerializable("HISTORY");



        myView = new ChessHistoryView(this);


        myView.setBackgroundColor(-1);

        ((ChessHistoryView)myView).setMyHistory(myHistory);
        ((ChessHistoryView)myView).setActivity(this);
        ((ChessHistoryView)myView).setMyName(extras.getString("NAME"));
        myView.setBackgroundResource(R.drawable.background);
        setContentView(myView);



    }

    public void toast(String message)
    {
        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();



    }
}