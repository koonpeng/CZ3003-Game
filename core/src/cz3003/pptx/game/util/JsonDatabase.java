package cz3003.pptx.game.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
	private static final String DIR_PATH = "bin/json_resource/";
	private static final String FILE_EXT = ".json";
	
	private boolean ProfileJsonExists;
	String filePath;
	
	public void storeAsJson(Object obj, String fileName){
	
		Gdx.app.log(TAG, "Storing object as Json");
	
		JSONObject jObj = new JSONObject(obj);
		
		try{
		
			filePath = this.DIR_PATH + fileName + this.FILE_EXT;
			FileHandle fileHandle = Gdx.files.local(filePath);
			
			//check if file exists
			ProfileJsonExists = 
				Gdx.files.local(filePath).exists();
			
			//make the file if it does not exists
			if (!ProfileJsonExists){
				fileHandle.file().getParentFile().mkdirs();
				fileHandle.file().createNewFile();
			}

			// True means append, false means overwrite.
			fileHandle.writeString(jObj.toString(), false);
			
			
		}catch(IOException e){
			Gdx.app.log(TAG, e.getMessage());
		}catch(Exception e){
			Gdx.app.log(TAG, e.getMessage());
		}
		
		System.out.println(jObj);
	}
	
	public JSONObject retrieveAsJson(String fileName){
	
		Gdx.app.log(TAG, "Retrieving Json File");

		String jString;
		JSONObject jObj = null;
		JSONTokener jTokener = null;
		BufferedReader bufferedReader = null;
		
		filePath = this.DIR_PATH + fileName + this.FILE_EXT;
		
		//check if file exists
		ProfileJsonExists = 
			Gdx.files.local(filePath).exists();
			
		if (!ProfileJsonExists){
			Gdx.app.log(TAG, filePath + " does not exists.");
			return null;
		}
		
		try{
		
			FileHandle fileHandle = Gdx.files.local(filePath);
			bufferedReader = new BufferedReader(fileHandle.reader());
			jString = bufferedReader.readLine();
			jTokener = new JSONTokener(jString);
			jObj = new JSONObject(jTokener);

		}catch(IOException e){
			Gdx.app.log(TAG, e.getMessage());
		}catch (JSONException e){
			Gdx.app.log(TAG, e.getMessage());
		}catch (Exception e){
			Gdx.app.log(TAG, e.getMessage());
		}finally{
			try {
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return jObj;
	}
	
	//implement methods to convert JsonObject to required object here.
}