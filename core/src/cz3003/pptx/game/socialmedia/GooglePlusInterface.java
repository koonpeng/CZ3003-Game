package cz3003.pptx.game.socialmedia;

public interface GooglePlusInterface extends SocialMediaInterface{
	
	@Override
	public void loginToSocialMedia();
	
	@Override
	public void publishMaterialToSocialMedia(String message);
	
	public void logoutOfSocialMedia();
}
