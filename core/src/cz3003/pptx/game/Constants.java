package cz3003.pptx.game;

public class Constants {
	public static int QuestionStageOn=3;
	public static int SelectionStageOn=2;
	public static int LoginStageOn=1;
	public static int StoreStageOn=4;
	public static int StageFlag=-1;
	public static int ResultStageOn=5;
	public static int SCREENWIDTH =720;
	public static int SCREENHEIGHT=1280;

	// Visible game world is 5 meters wide
	public static final float VIEWPORT_WIDTH = 5f;
	// Visible game world is 5 meters tall
	public static final float VIEWPORT_HEIGHT = 5f;
	
	public static final float VIEWPORT_GUI_WIDTH = 720f;
	public static final float VIEWPORT_GUI_HEIGHT = 1280;
	
	// Location of description file for texture atlas
	public static final String TEXTURE_ATLAS_OBJECTS =
		"images/image1.pack";
	public static final String TEXTURE_ATLAS_UI =
		"images/appone-ui.pack";
	public static final String TEXTURE_ATLAS_LIBGDX_UI =
		"images/uiskin.atlas";
	// Location of description file for skins
	public static final String SKIN_LIBGDX_UI =
		"images/uiskin.json";
	public static final String SKIN_APPONE_UI =
		"images/appone-ui.json";
	
}
