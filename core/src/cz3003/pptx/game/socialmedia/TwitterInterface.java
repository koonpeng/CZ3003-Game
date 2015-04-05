package cz3003.pptx.game.socialmedia;

public interface TwitterInterface extends SocialMediaInterface {
	
	@Override
	public void loginToSocialMedia();
	
	@Override
	public void publishMaterialToSocialMedia(String message);
	
	@Override
	public void populateProfile();
	
}
