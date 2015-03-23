package com.mygdx.game.socialmedia;

public class SocialMediaSharedVariable {
	
	
	public static final SocialMediaSharedVariable instance = 
			new SocialMediaSharedVariable();
	
	private SocialMediaSharedVariable(){};
	
	private boolean twitterBtnClicked;
	private boolean googleBtnClicked;
	private boolean userLoggedIn;
	private boolean desktopApplication;
	private boolean logOutBtnClicked;
	private TwitterInterface twitterInterface;
	private GooglePlusInterface googlePlusInterface;
	private SocialMediaActivityInterface smaInterface;
	
	public void clear(){
		twitterBtnClicked = false;
		googleBtnClicked = false;
		userLoggedIn = false;
		logOutBtnClicked = false;
	}

	//Getter methods
	public boolean isTwitterBtnClicked() {
		return twitterBtnClicked;
	}
	public boolean isGoogleBtnClicked() {
		return googleBtnClicked;
	}
	public boolean isUserLoggedIn() {
		return userLoggedIn;
	}
	
	public TwitterInterface getTwitterInterface(){
		return this.twitterInterface;
	}
	public GooglePlusInterface getGooglePlusInterface(){
		return this.googlePlusInterface;
	}
	public SocialMediaActivityInterface getSocialMediaActivityInterface(){
		return this.smaInterface;
	}
	public boolean isDesktopApplication(){
		return this.desktopApplication;
	}
	public boolean isLogOutBtnClicked(){
		return this.logOutBtnClicked;
	}

	//Setter methods
	public void setTwitterBtnClicked(boolean twitterBtnClicked) {
		this.twitterBtnClicked = twitterBtnClicked;
	}
	public void setGoogleBtnClicked(boolean googleBtnClicked) {
		this.googleBtnClicked = googleBtnClicked;
	}
	public void setUserLoggedIn(boolean userLoggedIn) {
		this.userLoggedIn = userLoggedIn;
	}
	public void setTwitterInterface(TwitterInterface twitterInferface){
		this.twitterInterface = twitterInferface;
	}
	public void setGooglePlusInterface(GooglePlusInterface googlePlusInterface){
		this.googlePlusInterface = googlePlusInterface;
	}
	public void setSocialMediaActivityInterface(SocialMediaActivityInterface smaInterface){
		this.smaInterface = smaInterface;
	}
	public void setDesktopApplication(boolean bool){
		this.desktopApplication = bool;
	}
	public void setlogOutBtnClicked(boolean bool){
		this.logOutBtnClicked = bool;
	}
}
