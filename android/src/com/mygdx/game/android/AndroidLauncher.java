package com.mygdx.game.android;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.mygdx.game.android.socialmedia.AndroidGooglePlusInterface;
import com.mygdx.game.android.socialmedia.AndroidTwitterInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.socialmedia.SocialMediaActivityInterface;
import com.mygdx.game.socialmedia.SocialMediaSharedVariable;
import com.mygdx.game.socialmedia.TwitterResource;

public class AndroidLauncher extends AndroidApplication implements SocialMediaActivityInterface{
	
	private static final String TAG = AndroidLauncher.class.getName();
	public static final String SER_KEY = "AppOneGame";
	
	private MyGdxGame game;
	private AndroidTwitterInterface andTwitterInterface;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		game = new MyGdxGame();
		andTwitterInterface = new AndroidTwitterInterface(this);
		SocialMediaSharedVariable.instance.setTwitterInterface(andTwitterInterface);
		SocialMediaSharedVariable.instance.setSocialMediaActivityInterface(this);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(game, config);
	}
	
	@Override
	public void onResume(){
		
		super.onResume();
		
		Gdx.app.log(TAG, "resuming app from onResume");
		
		//check if twitter login button is pressed
		if (SocialMediaSharedVariable.instance.isTwitterBtnClicked() == true ){
			SocialMediaSharedVariable.instance.setTwitterBtnClicked(false);
			//check if user is already logged in
			if (TwitterResource.instance.isTwitterLoggedIn() == false){
				SocialMediaSharedVariable.instance.setTwitterBtnClicked(false);
				
				new AsyncTask<Void,Void,Void>(){
					@Override
	                protected Void doInBackground(Void... args){
	                	andTwitterInterface.getTwitterUserOauth();
	                    return null;
	                }
				}.execute();
			}
		}//end of Twitter button check
		
		//check if google plus login button is pressed
	}//end of onResume

	@Override
	public void startGooglePlusLoginActivity() {
		
		Gdx.app.log(TAG, "starting google plus login activity");
		Intent intent = new Intent(this, AndroidGooglePlusInterface.class);
		intent.putExtra("action", "LOG_IN");
		startActivity(intent);
		
	}
	
	@Override
	public void startGooglePlusRevokeAccessActivity(){
		
		Gdx.app.log(TAG, "starting google plus revoke access activity");
		Intent intent = new Intent(this, AndroidGooglePlusInterface.class);
		intent.putExtra("action", "REVOKE_ACCESS");
		startActivity(intent);
	}

}
