package cz3003.pptx.game.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import cz3003.pptx.game.PPTXGame;
import cz3003.pptx.game.android.socialmedia.AlertDialogManager;
import cz3003.pptx.game.android.socialmedia.AndroidGooglePlusInterface;
import cz3003.pptx.game.android.socialmedia.AndroidTwitterInterface;
import cz3003.pptx.game.android.socialmedia.ConnectionDetector;
import cz3003.pptx.game.socialmedia.SocialMediaActivityInterface;
import cz3003.pptx.game.socialmedia.SocialMediaSharedVariable;
import cz3003.pptx.game.socialmedia.TwitterResource;

public class AndroidLauncher extends AndroidApplication implements
		SocialMediaActivityInterface {
	
	ProgressDialog pDialog;
	
	private ConnectionDetector connectionDetector;
	
	AlertDialogManager alert = new AlertDialogManager();

	private static final String TAG = AndroidLauncher.class.getName();
	public static final String SER_KEY = "AppOneGame";

	private PPTXGame game;
	private AndroidTwitterInterface andTwitterInterface;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		game = PPTXGame.getInstance();
		andTwitterInterface = new AndroidTwitterInterface(this);
		SocialMediaSharedVariable.instance
				.setTwitterInterface(andTwitterInterface);
		SocialMediaSharedVariable.instance
				.setSocialMediaActivityInterface(this);
	
		connectionDetector = new ConnectionDetector(getApplicationContext());

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(game, config);
	}

	@Override
	public void onResume() {

		super.onResume();

		Gdx.app.log(TAG, "resuming app from onResume");
		
		//Check if Internet present
		if (!connectionDetector.isConnectingToInternet()){
			//Internet Connection is not present
			alert.showAlertDialog(AndroidLauncher.this, "Internet Connection Error" , 
				"Please connect to working Internet connection", false);
			//stop executing code by return
			return;
		}

		// check if twitter login button is pressed
		if (SocialMediaSharedVariable.instance.isTwitterBtnClicked() == true) {
			SocialMediaSharedVariable.instance.setTwitterBtnClicked(false);
			
			
			// check if user is already logged in
			if (TwitterResource.instance.isTwitterLoggedIn() == false) {

				new AsyncTask<Void, Void, Void>() {
					@Override
					protected void onPreExecute(){
						super.onPreExecute();
						pDialog = new ProgressDialog(AndroidLauncher.this);
						pDialog.setMessage("Connecting with twitter...");
						pDialog.setIndeterminate(false);
						pDialog.setCancelable(false);
						pDialog.show();
					}
					
					@Override
					protected Void doInBackground(Void... args) {
						andTwitterInterface.getTwitterUserOauth();
						return null;
					}
					
					@Override
					protected void onPostExecute(Void v){
						pDialog.dismiss();
					}
				}.execute();
			}
		}// end of Twitter button check

		// check if google plus login button is pressed
	}// end of onResume

	@Override
	public void startGooglePlusLoginActivity() {

		Gdx.app.log(TAG, "starting google+ login activity");
		Intent intent = new Intent(this, AndroidGooglePlusInterface.class);
		intent.putExtra("action", "LOG_IN");
		startActivity(intent);

	}

	@Override
	public void startGooglePlusRevokeAccessActivity() {

		Gdx.app.log(TAG, "starting google+ revoke access activity");
		Intent intent = new Intent(this, AndroidGooglePlusInterface.class);
		intent.putExtra("action", "REVOKE_ACCESS");
		startActivity(intent);
	}
}
