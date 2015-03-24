	package cz3003.pptx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class StoreStage extends Stage {
	Texture texture;
	TextureRegion goodShelfRegion;
	TextureRegion heartRegion;
	TextureRegion goldRegion;
	Image goodsShelf;
	Image gold;
	Image heart;
	
	public StoreStage(){
		super();
		init();
	}
	
	public void init(){
		texture=new Texture(Gdx.files.internal("object.png"));
		goodShelfRegion=new TextureRegion(texture,0,85,510,350);
		goodsShelf=new Image(goodShelfRegion);
		goodsShelf.setSize(480,320);
		
		heartRegion=new TextureRegion(texture,0,0,102,85);
		heart=new Image(heartRegion);
		
		goldRegion=new TextureRegion(texture,102,0,102,85);
		gold=new Image(goldRegion);
		
		gold.setPosition(50, 50);
		heart.setPosition(190, 50);
		
		this.addActor(goodsShelf);
		this.addActor(gold);
		this.addActor(heart);
		heart.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.exit();
				return true;
			}
			
		});
	}
}
