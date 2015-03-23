package com.mygdx.game.socialmedia;

public interface TwitterInterface extends SocialMediaInterface {
	
	@Override
	public void loginToSocialMedia();
	
	@Override
	public void publishMaterialToSocialMedia(String message);
	
}
