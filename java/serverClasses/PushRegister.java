package serverClasses;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.os.AsyncTask;
import java.io.IOException;

public class PushRegister
{
    public static final String PREFERENCES = "com.botna.chess2";
    GoogleCloudMessaging gcm;
    String regid;
    String PROJECT_NUMBER = "596327876756";
    Activity myActivity = null;

    public PushRegister (Activity myActivity)
    {
        this.myActivity = myActivity;
    }

    public void getRegId(){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {


                if(android.os.Debug.isDebuggerConnected())
                    android.os.Debug.waitForDebugger();


                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(myActivity.getApplicationContext());
                    }
                    regid = gcm.register(PROJECT_NUMBER);
                    msg =regid;
                    Log.i("GCM", msg);

                } catch (IOException ex) {
                    msg = null;

                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                //Now that we've registered, store our REGID in the shared preferences
                if(msg != null) {
                    SharedPreferences.Editor editor = myActivity.getSharedPreferences(PREFERENCES, myActivity.MODE_PRIVATE).edit();
                    editor.putString("REGID", msg);
                    editor.commit();
                }
                else
                {
                    //we need to get a regID no matter what, or push notifications fail.
                    getRegId();
                }
            }
        }.execute(null, null, null);
    }

}