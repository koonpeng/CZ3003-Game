package cz3003.pptx.game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class AndroidView {
    
  private static FitViewport port;
  public static FitViewport getview()
  {
	  if(port==null)
	  {
		  port=new FitViewport(720	, 1280);
		 
	  }
	 return port;
  }
  
}