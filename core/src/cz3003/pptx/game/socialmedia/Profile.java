package cz3003.pptx.game.socialmedia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Profile{
	
	//Set Profile class as singleton
	public static Profile instance = new Profile();

	private static final String TAG = Profile.class.getName();
	private static final int STAGE_LEVELS = 5;

	private final String JSONFilePath = "bin/json_resource/" + "playerProfile.json";
	private String username;
	private int id;
	private int difficulty;
	private boolean hasProfile;
	private boolean ProfileJsonExists;
	
	private boolean[] stageLockedArray;
	private int[] stageHighScoreArray;
	
	//complete object with all the profiles
	private JSONObject jsonObj;
	
	private boolean dirtyBit;
	
	private Profile(){
		Gdx.app.log(TAG, "Profile instance created");
		
		String locRoot = Gdx.files.getLocalStoragePath();
		Gdx.app.log(TAG, locRoot);
		
		ProfileJsonExists = 
				Gdx.files.local(this.JSONFilePath).exists();
		if (ProfileJsonExists == false){
			Gdx.app.log(TAG, "Profile json does not exists");
		}
		
		if (SocialMediaSharedVariable.instance.isDesktopApplication()){
			Gdx.app.log(TAG, "Desktop app detected");
			this.username = "Default user";
			this.difficulty = 2;
			this.id = 9;
			this.hasProfile = true;
			this.stageLockedArray = new boolean[]
				{false,false,true,true,true};
			this.stageHighScoreArray = new int[]
					{20,0,0,0,0};
			this.dirtyBit = true;
		}
	};
	
	public void setNewProfile(String username){
		Gdx.app.log(TAG, "New Profile Set");
		this.username = username;
		this.id = -1;
		this.difficulty = -1;
		this.dirtyBit = true;
		this.hasProfile = true;
		this.stageLockedArray = new boolean[]
			{false,true,true,true,true};
		this.stageHighScoreArray = new int[]
				{0,0,0,0,0};
	};
	
	//Setter - set dirty bit to true if any set method is used
	//dirty bit is used to determine if update is required or not
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
		this.dirtyBit = true;
		stageLockedArray = new boolean[array.length];
		this.stageLockedArray = Arrays.copyOf(array, array.length);
		
		//Enforce that stage 1 is always unlocked
		this.stageLockedArray[0] = false;
	}
	
	public void setStageHighScoreArray(int[] array){
		this.dirtyBit = true;
		stageHighScoreArray = new int[array.length];
		stageHighScoreArray = Arrays.copyOf(array, array.length);
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
	public boolean[] getStageLockedArray(){
		if (this.stageLockedArray == null) {
			return new boolean[]{false,true,true,true,true};
		}
		return stageLockedArray;
	}
	
	public int[] getStageHighScoreArray(){
		if (this.stageHighScoreArray == null) {
			return new int[]{0,0,0,0,0};
		}
		return stageHighScoreArray;
	}
	
	//Create Json file
	public void createTestJsonObj(){
		Gdx.app.log(TAG, "Create Test Json");
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("username", "paul");
			obj.put("id", "100");
			obj.put("stageOneUnlock", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		writeJSONFile(obj);
	}
	
	//Write Json file to local drive
	public void writeJSONFile(JSONObject obj){
		
		Gdx.app.log(TAG, "Writing Json File");
		
		try {
			FileHandle fileHandle = Gdx.files.local(this.JSONFilePath);
			if (ProfileJsonExists){
				// True means append, false means overwrite.
				fileHandle.writeString(obj.toString(), false);
			}else{
				fileHandle.file().getParentFile().mkdirs();
				fileHandle.file().createNewFile();
				fileHandle.writeString(obj.toString(), false);
			}
	 
		} catch (IOException e) {
			Gdx.app.log(TAG, e.getMessage());
		}
	}

	//Retrieve player profile from JSON file
	public void retrievePlayerProfile(){
		
		this.dirtyBit = false;
		BufferedReader br = null;
		JSONTokener tokener = null;
		JSONObject obj = null;
		String jsonString;
		String stageName;
		
		try{
			FileHandle fileHandle = Gdx.files.local(this.JSONFilePath);
			br = new BufferedReader(fileHandle.reader());
			jsonString = br.readLine();
			tokener = new JSONTokener(jsonString);
			
			
			this.jsonObj = new JSONObject(tokener);
			
			try{
			obj = this.jsonObj.getJSONObject(this.username);
			}catch (JSONException e){
				Gdx.app.log(TAG, "username not found in class");
				this.setNewProfile(this.username);
				br.close();
				return;
			}
			
			//populate the data
			this.id = (int) obj.get("id");
			this.difficulty = (int) obj.get("difficulty");
			
			for (int i = 0; i < this.STAGE_LEVELS; i++){
				stageName = "stageLocked" + (i+1);
				this.stageLockedArray[i] = (boolean) obj.get(stageName);
			}	
			
			for (int i = 0; i < this.STAGE_LEVELS; i++){
				stageName = "stageHighScore" + (i+1);
				this.stageHighScoreArray[i] = (int) obj.get(stageName);
			}
			
		}catch (FileNotFoundException e){
			Gdx.app.log(TAG, e.getMessage());
		}catch (JSONException e){
			Gdx.app.log(TAG, e.getMessage());
		}catch (IOException e){
			Gdx.app.log(TAG, e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				Gdx.app.log(TAG, e.getMessage());
			}
		}
		
		//if no exception is raised, then we have a valid user profile
		this.hasProfile = true;
	}
	
	//Add / Update new player profile in JSON
	public void updateJsonObject(){
		
		Gdx.app.log(TAG, "Updating Json File");
		
		JSONObject obj;
	
		if (this.getDirtyBit()){
			//if username is not found, this stmt does nothing
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
	
	private JSONObject generateProfileObject(){
	
		Gdx.app.log(TAG, "Generating Profile Json");
		
		String stageName;
	
		JSONObject obj = new JSONObject();
		try {
			obj.put("username", this.username);
			obj.put("id", this.id);
			obj.put("difficulty", this.difficulty);

			for (int i = 0; i < this.STAGE_LEVELS; i++){
				stageName = "stageLocked" + (i+1);
				obj.put(stageName, this.stageLockedArray[i]);
			}
			for (int i = 0; i < this.STAGE_LEVELS; i++){
				stageName = "stageHighScore" + (i+1);
				obj.put(stageName, this.stageHighScoreArray[i]);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
		
	}
}

