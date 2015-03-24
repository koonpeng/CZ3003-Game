package cz3003.pptx.game.socialmedia;

public class TwitterResource {
	
	public static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";
	
	//Make TwitterResource a singleton
	public static final TwitterResource instance = new TwitterResource();
	
	private TwitterResource(){}
	
	// Twitter resources
	public static String TWITTER_CONSUMER_KEY = "qzEFDRnNZma4GkXKc86WhkUZs";
    public static String TWITTER_CONSUMER_SECRET = "ZffH2nVZXShGRROx1fEUujOMi7xXOnJKAdHnUJD0XMh1ZibHTW";
	public static final String URL_TWITTER_AUTH = "auth_url";
	public static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
	public static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
	
	private String requestURL;
	private boolean twitterLoggedIn;
	private String oauth_token;
	private String oauth_token_secret;
	
	private long userID;
	private String username;

	public void clear(){
		requestURL = "";
		twitterLoggedIn = false;
		oauth_token = "";
		oauth_token_secret = "";
		userID = 0;
		username = "";
	}
	
	//Setter methods
	public void setOauth_token(String oauth_token) {
		this.oauth_token = oauth_token;
	}
	public void setOauth_token_secret(String oauth_token_secret) {
		this.oauth_token_secret = oauth_token_secret;
	}
	public void setTwitterLoggedIn(boolean twitterLoggedIn) {
		this.twitterLoggedIn = twitterLoggedIn;
	}
	public void setRequestURL(String requestURL){
		this.requestURL = requestURL;
	}
	public void setUserID(long userID){
		this.userID = userID;
	}
	public void setUsername(String username){
		this.username = username;
	}
	
	//Getter methods
	public String getOauth_token() {
		return oauth_token;
	}
	public String getOauth_token_secret() {
		return oauth_token_secret;
	}
	public boolean isTwitterLoggedIn() {
		return twitterLoggedIn;
	}
	public String getRequestURL(){
		return this.requestURL;
	}
	public long getUserID(){
		return this.userID;
	}
	public String getUsername(){
		return this.username;
	}
	
}
