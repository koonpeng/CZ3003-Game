package cz3003.pptx.game.android.socialmedia;

import java.util.concurrent.TimeUnit;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import cz3003.pptx.game.socialmedia.GooglePlusInterface;
import cz3003.pptx.game.socialmedia.Profile;
import cz3003.pptx.game.socialmedia.SocialMediaSharedVariable;

//###FOR GOOGLE PLUS TO WORK, DEBUG.KEYSTORE LOCATED IN ANDROID>LIB MUST BE USED #######
//###TO SIGN THE APK.###################################################################

@SuppressLint("NewApi")
public class AndroidGooglePlusInterface extends Fragment implements GooglePlusInterface, 
	ConnectionCallbacks, OnConnectionFailedListener{
	
	private static final String TAG = AndroidGooglePlusInterface.class.getName();
	
	public static final int RC_SIGN_IN = 0;
	private static GoogleApiClient mGoogleApiClient;
	 /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;
    private ConnectionResult mConnectionResult;
    private String action;
    private Activity mActivity;
    private Context mContext;
    private boolean singleLoginQuickFix = false;
	
	public AndroidGooglePlusInterface(String action){
		super();
		this.action = action;
	}

	@Override
	public void loginToSocialMedia() {
		if (mGoogleApiClient.isConnected()){
    		Gdx.app.log(TAG, "user is connected");
    		return;
    	}else{
    		Gdx.app.log(TAG, "user is not connected");
    	}
        if (!mGoogleApiClient.isConnecting()) {
        	SocialMediaSharedVariable.instance.setGoogleBtnClicked(true);
            resolveSignInError();
        }
	}

	@Override
	public void publishMaterialToSocialMedia(String message) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void logoutOfSocialMedia(){
		
		SocialMediaSharedVariable.instance.setlogOutBtnClicked(false);
		SocialMediaSharedVariable.instance.setUserLoggedIn(false);
		
		//return to splash page after logging out
		//Intent intent = new Intent(this, AndroidLauncher.class);
		//startActivity(intent);
	}
    
	@Override
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	Log.e(TAG, "creating GPlus Interface");
    	
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
         
        SocialMediaSharedVariable.instance.setGooglePlusInterface(this);
        
    }
    
    @Override
    public void onStart(){
    	super.onStart();
    	Gdx.app.log(TAG, "starting google+ fragment");
    	
    	mActivity = getActivity();
    	mContext = getActivity().getApplicationContext();
    	
    	//mGoogleApiClient.connect();
    }
    
    @Override
    public void onStop(){
    	super.onStop();
    	if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
		
    }
    
    @Override
    public void onResume(){
    	
    	super.onResume();
    	
    	new AsyncTask<Void,Void,Void>(){
			@Override
            protected Void doInBackground(Void... args){
				mGoogleApiClient.blockingConnect(5L, TimeUnit.SECONDS);
				
				Gdx.app.log(TAG, "connecting on resume");
		    
		    	int choice = 0;
		    	
		    	Gdx.app.log(TAG, "action = " + action);
		    	
		    	if (action.equals("LOG_IN")){
		    		choice = 1;
		    	}else if (action.equals("REVOKE_ACCESS")){
		    		choice = 2;
		    	}
		    	
		    	switch (choice){
		    	case 1:
		    		loginToSocialMedia();
		    		Gdx.app.log(TAG, "logged into google+");
		    		break;
		    	case 2:
		    		revokeGplusAccess();
		    		logoutOfSocialMedia();
		    		Gdx.app.log(TAG, "logged out of google+");
		    		break;
		    	default:
		    		Gdx.app.log(TAG, "invalid action");
		    		break;
		    	}
                return null;
            }
		}.execute();
    }
    
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		 if (!result.hasResolution()) {
	            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), mActivity,
	                    0).show();
	            return;
	        }
	     
	        if (!mIntentInProgress) {
	            // Store the ConnectionResult for later usage when user
	        	//clicks 'sign-in'.
	        	
	            mConnectionResult = result;
	     
	       if (SocialMediaSharedVariable.instance.isGoogleBtnClicked()) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
	          }
	      }
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int responseCode,
            Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != Activity.RESULT_OK) {
            	SocialMediaSharedVariable.instance.setGoogleBtnClicked(false);
            }
     
            mIntentInProgress = false;
     
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

	@Override
	public void onConnected(Bundle arg0) {
		
		//Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
		
		
		Gdx.app.log(TAG, "google + connected");
		SocialMediaSharedVariable.instance.setGoogleBtnClicked(false);
		SocialMediaSharedVariable.instance.setUserLoggedIn(true);
		
		if (!SocialMediaSharedVariable.instance.isLogOutBtnClicked()){
			//return to splash page after logging in if not logging out
			this.populateProfile();
			//Intent intent = new Intent(this, AndroidLauncher.class);
			//startActivity(intent);
		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
	}
	
	/**
     * Method to resolve any signin errors
     * */
    private void resolveSignInError() {
    	if (mConnectionResult == null){
    		Log.e(TAG, "mConnectionResult is null");
    		return;
    	}
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                Gdx.app.log(TAG, "Error: "+mConnectionResult.getErrorCode() + 
                	"String:" + mConnectionResult.toString());
                if (mConnectionResult.getErrorCode() == 4 && this.singleLoginQuickFix == true){
                	return;
                }
                this.singleLoginQuickFix = true;
                mConnectionResult.startResolutionForResult(mActivity, RC_SIGN_IN);
            } catch (SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
    
    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
        	Gdx.app.log(TAG, "signing out from gplus");
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
    }
    
    private void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
        	Gdx.app.log(TAG, "revoking user access");
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.e(TAG, "User access revoked!");
                            mGoogleApiClient.connect();
                        }
     
                    });
        }
    }
    
    @Override
	public void populateProfile(){
    	Person currentPerson;
    	String personName = "";
    	Gdx.app.log(TAG, "Populating google+ profile");
    	
    	if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
    	    currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
    	    personName = currentPerson.getDisplayName();
    	}else{
    		Gdx.app.log(TAG, "current person is null");
    		return;
    	}
    	
    	Gdx.app.log(TAG, personName);
    	
    	Profile.instance.setUsername(personName);
    	Profile.instance.retrievePlayerProfile();
    	Profile.instance.setDifficulty(2);
    	Profile.instance.updateJsonObject();
    	
    	Gdx.app.log(TAG, ""+Profile.instance.getUserID());
	}
}
