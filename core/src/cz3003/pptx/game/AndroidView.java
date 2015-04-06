package cz3003.pptx.game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class AndroidView {
    
  private static StretchViewport port;
  public static StretchViewport getview()
  {
	  if(port==null)
	  {
		
		  port=new StretchViewport(PPTXGame.GAME_WIDTH	, PPTXGame.GAME_HEIGHT);
		 
	  }
	 return port;
  }
  
}