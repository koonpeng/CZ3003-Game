package cz3003.pptx.game.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class JsonDatabase {
	private static final String TAG = JsonDatabase.class.getName();
	private static final String DIR_PATH = "bin/json_resource";
	
	private boolean fileExist;
	private String filePath;
	
	public void storeAsJson(Object obj){
		
	}
	
	public Object retrieveAsJson(Object obj, String fileName){
		
		//check if file exist
		fileExist = 
				Gdx.files.local(this.DIR_PATH + fileName).exists();
		if (fileExist == false){
			Gdx.app.log(TAG, "Profile json does not exists");
		}
		
		
		return obj;
	}
}
