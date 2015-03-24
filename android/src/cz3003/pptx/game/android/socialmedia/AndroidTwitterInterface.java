package cz3003.pptx.game.android.socialmedia;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.badlogic.gdx.Gdx;

import cz3003.pptx.game.socialmedia.SocialMediaSharedVariable;
import cz3003.pptx.game.socialmedia.TwitterInterface;
import cz3003.pptx.game.socialmedia.TwitterResource;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class AndroidTwitterInterface implements TwitterInterface{
	
	private static final String TAG = AndroidTwitterInterface.class.getName();
	
	private Activity activity;
	
	private static String TWITTER_CONSUMER_KEY = TwitterResource.instance.TWITTER_CONSUMER_KEY;
	private static String TWITTER_CONSUMER_SECRET = TwitterResource.instance.TWITTER_CONSUMER_SECRET;

    // Twitter oauth urls
    private String url_twitter_auth = TwitterResource.instance.URL_TWITTER_AUTH;
    private String url_twitter_oauth_verifier = TwitterResource.instance.URL_TWITTER_OAUTH_VERIFIER;
    private String url_twitter_oauth_token = TwitterResource.instance.URL_TWITTER_OAUTH_TOKEN;
    private String twitter_callback_url = TwitterResource.instance.TWITTER_CALLBACK_URL;

	
    // Twitter
    private static Twitter twitter;
    private static RequestToken requestToken;
    
    public AndroidTwitterInterface(Activity activity){
    	this.activity = activity;
    }

	@Override
	public void loginToSocialMedia() {
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
        builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
        Configuration configuration = builder.build();
        
        TwitterFactory factory = new TwitterFactory(configuration);
        twitter = factory.getInstance();
        
        try {
            requestToken = twitter
                    .getOAuthRequestToken(twitter_callback_url);
            
            String requestURL = requestToken.getAuthenticationURL();
            Gdx.app.log(TAG,"RequestURL : " + requestURL);
            
            TwitterResource.instance.setRequestURL(requestURL);
    		
    		if (requestURL != null){
    			Gdx.app.log(TAG, "intent creating : " + requestURL);
    			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(requestURL));
    			Gdx.app.log(TAG, "intent created");
    			activity.startActivity(intent);
    		}else{
    			Gdx.app.log(TAG, "request URL is null ");
    		}
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}//end of loginTwitter
	
	public void getTwitterUserOauth(){
		
		Uri uri = null;
		
		Gdx.app.log(TAG, "activity null ? " + (activity == null));
		try{
		uri = activity.getIntent().getData();
		}catch(Exception e){
			Gdx.app.log(TAG, "some error occurred in getIntent()");
		}
		
		try{
		if (uri != null && uri.toString().startsWith(twitter_callback_url)){
			String verifier = uri.getQueryParameter(url_twitter_oauth_verifier);
			Gdx.app.log(TAG, "verifier : " + verifier);
			
            AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
            
            Gdx.app.log(TAG, "accessToken = " + accessToken.getToken());

            TwitterResource.instance.setOauth_token(accessToken.getToken());
            TwitterResource.instance.setOauth_token_secret(accessToken.getTokenSecret());
            TwitterResource.instance.setTwitterLoggedIn(true);
             
            //Getting user information
            long userID = accessToken.getUserId();
            User user = twitter.showUser(userID);
            String username = user.getName();
            
            TwitterResource.instance.setUserID(userID);
            TwitterResource.instance.setUsername(username);

           TwitterResource.instance.setTwitterLoggedIn(true);
           SocialMediaSharedVariable.instance.setUserLoggedIn(true);
		}else{
			Gdx.app.log(TAG, "invalid uri");
		}
		 } catch(TwitterException te){
			 Gdx.app.log(TAG, "TwitterException : "+te.getStatusCode());
			 te.printStackTrace();
		 }catch (Exception e) {
             // Check log for login errors
         	Gdx.app.log(TAG, "some error occurred in reading uri");
         	e.printStackTrace();
         }
		
		
	}//end of getTwitterUserOauth
	
	@Override
	public void publishMaterialToSocialMedia(String message){
		
		//check access token and secret is not null
		if (TwitterResource.instance.getOauth_token() == null || 
			TwitterResource.instance.getOauth_token_secret() == null){
			Gdx.app.log(TAG, "Access Token not initialized");
			return;
		}
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
        builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
        
        AccessToken accessToken = new AccessToken(TwitterResource.instance.getOauth_token(),
        	TwitterResource.instance.getOauth_token_secret());
        
        Twitter authorizedTwitter = new TwitterFactory(builder.build()).getInstance(accessToken);
        
		Date date = new Date();
		String mess = "\"Visting to Transylvania\"" + date.getTime();
		Status status;
		try {
			status = authorizedTwitter.updateStatus(mess);
			System.out.println(status.getText());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
}
