package com.example.andrewapp;

import ServerStuff.ChessClass;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.ListPopupWindow;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

public class NewGameActivity extends ActionBarActivity {
	protected ChessClass theGame = null;
	protected ListPopupWindow listPopupWindow;
	protected String variant = null;
	protected String[] variants={"Classic", "Reapers", "Nemesis","Empowered",
			"Animals","Two Kings"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_game_menu);
		/*if (theGame == null)
		{
			Bundle b = this.getIntent().getExtras();
			if(b != null)
			{
				theGame= b.getParcelable("theGame");
			}
		}*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return true;
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
		FragmentManager fm = getSupportFragmentManager();
		choiceDialog.show(fm, "Dialog Fragment");
		
		
		
	}
	
	
	public class ClassChoiceDialog extends DialogFragment{
		
		 public Dialog onCreateDialog(Bundle savedInstanceState) {
			
			 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			 builder.setTitle("Choose a class").setItems(variants, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Bundle b = new Bundle();
					Intent i = new Intent();
					EditText editText = (EditText) findViewById(R.id.username);
					
					b.putString("MODE", "NEWGAME");
					b.putString("CLASS", variants[which]);
					b.putString("OPPONENT", editText.getText().toString());
					
					i.putExtras(b);
					setResult(RESULT_OK, i);
					finish();
				}
			});
			 
			 
			 return builder.create();
		 }
	}
	 
}
