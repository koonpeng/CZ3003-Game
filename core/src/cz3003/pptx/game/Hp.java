package cz3003.pptx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Hp extends Actor {
	Image hp;
	Label lblhp;
	LabelStyle style;
	BitmapFont font;
	TextureRegion hptexture;
	private  final int  hpheight = 40; // 血条高度
	private   int hpWidth = 320;
	private   int maxHp=100; // 总血量
	private  int currentHp=100; // 当前血量
	private int x;
	private int y;
	
	public Hp(int x,int y,int attacktimes) {
		this.x=x;
		this.y=y;
		maxHp=attacktimes;
		currentHp=attacktimes;
		this.uiini();
	}

	public void uiini() {

		updatehpgraph();
		
		style =new LabelStyle(CusFontStyle.getNormalFont(), CusFontStyle.getNormalFont().getColor());
		lblhp = new Label(currentHp*10+" / "+maxHp*10, style);
		
		lblhp.setPosition(x+40, y-3);
		lblhp.setFontScale(1);
		lblhp.setWidth(hpWidth);
		//lblhp.setWrap(true);
		
	

	}
	public void updatehpgraph()
	{
		Pixmap pixmap = new Pixmap(hpWidth+10, hpheight+3, Format.RGBA8888); // 生成一张64*8的图片

		pixmap.setColor(Color.BLACK); // 设置颜色为黑色

		pixmap.drawRectangle(0, 0, hpWidth, hpheight); // 绘制边框
		pixmap.setColor(Color.RED); // 设置颜色为红色

		pixmap.fillRectangle(1, 1, hpWidth * currentHp / maxHp-2,hpheight - 2); // 绘制血条

		
		Texture pixmaptex = new Texture(pixmap); // 生成图片

		hptexture = new TextureRegion(pixmaptex, hpWidth, hpheight); // 切割图片
	}
	public void minushp()
	{
		currentHp=currentHp-1;
		String txt=currentHp*10+" / "+maxHp*10;
		lblhp.setText(txt);
		

	}

	public boolean isAlive()
	{
		
		
		if(currentHp<=0)
		{
		return false;
		}
		return true;
		
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		//uiini();
		updatehpgraph();
		batch.draw(hptexture,x, y, hpWidth,hpheight);
		
	}

}
