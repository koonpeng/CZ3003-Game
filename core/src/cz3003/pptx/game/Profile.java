package cz3003.pptx.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.badlogic.gdx.Gdx;

public class Profile{

	private static final String TAG = Profile.class.getName();

	private static final String JSONFilePath = "" + "playerProfile.json";
	private String username;
	private int id;
	private int difficulty;
	private boolean hasProfile;
	
	private static boolean[] stageLockedArray = new boolean[5];
	
	//complete object with all the profiles
	private JSONObject jsonObj;
	private boolean dirtyBit;
	
	//Constructor
	public Profile(String username){
		this.username = username;
		this.id = -1;
		this.difficulty = -1;
		this.dirtyBit = false;
	}
	
	//Setter
	public void setUsername(String username){
		this.dirtyBit = true;
		this.username = username;
	}
	public void setID(int id){
		this.dirtyBit = true;
		this.id = id;
	}
	public void setDifficulty(int difficulty){
		this.dirtyBit = true;
		this.difficulty = difficulty;
	}
	public void setStageLockedArray(boolean[] array){
		this.stageLockedArray = Arrays.copyOf(array, 5);
	}
	
	//Getter
	public String getUsername(){
		return this.username;
	}
	public int getUserID(){
		return this.id;
	}
	public int getDifficulty(){
		return difficulty;	
	}
	public boolean hasProfile(){
		return this.hasProfile;
	}
	public boolean getDirtyBit(){
		return this.dirtyBit;
	}
	public static boolean[] getStageLockedArray(){
		return stageLockedArray;
	}
	
	//Create json file
	private void createTestJsonFile(){
	
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("username", "paul");
			obj.put("id", "100");
			obj.put("stageOneUnlock", false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		writeJSONFile(obj);
	}
	
	//overwrite json file with new data
	public void writeJSONFile(JSONObject obj){
		
		try {
			 
			FileWriter file = new FileWriter(this.JSONFilePath);
			file.write(obj.toString());
			file.flush();
			file.close();
	 
		} catch (IOException e) {
			Gdx.app.log(TAG, e.getMessage());
		}
	}

	//Retrieve player profile from JSON file
	public void retrievePlayerProfile() throws IOException{
		BufferedReader br = null;
		JSONTokener tokener = null;
		JSONObject obj = null;
		String jsonString;
		String stageLocked;
		String stageName;
		
		try{
			br = new BufferedReader(new FileReader(this.JSONFilePath));
			jsonString = br.readLine();
			tokener = new JSONTokener(jsonString);
			
			this.jsonObj = new JSONObject(tokener);
			obj = this.jsonObj.getJSONObject(this.username);
			
			//populate the data
			this.id = (int) obj.get("id");
			this.difficulty = (int) obj.get("difficulty");
			
			for (int i = 0; i < 5; i++){
				stageName = "stageLocked" + (i+1);
				this.stageLockedArray[i] = (boolean) obj.get(stageName);
			}
			br.close();
			
		}catch (FileNotFoundException e){
			Gdx.app.log(TAG, e.getMessage());
		}catch (JSONException e){
			Gdx.app.log(TAG, e.getMessage());
		}catch (IOException e){
			Gdx.app.log(TAG, e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
		}
		
		//if no exception is raised, then we have a valid user profile
		this.hasProfile = true;
	}
	
	//Add / Update new player profile in JSON
	public void updateJsonObject(){
		
		JSONObject obj;
	
		if (this.getDirtyBit()){
			this.jsonObj.remove(this.username);
			obj = generateProfileObject();
			try {
				this.jsonObj.put(this.username, obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			writeJSONFile(this.jsonObj);
		
		}else{
			Gdx.app.log(TAG, "nothing to update");
			return;
		}
	}
	
	public JSONObject generateProfileObject(){
	
		String stageName;
	
		JSONObject obj = new JSONObject();
		try {
			obj.put("username", this.username);
			obj.put("id", this.id);
			obj.put("difficulty", this.difficulty);
			
			for (int i = 0; i < 5; i++){
				stageName = "stageLocked" + (i+1);
				obj.put(stageName, this.stageLockedArray[i]);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
		
	}
}

