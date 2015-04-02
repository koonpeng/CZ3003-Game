package cz3003.pptx.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Iterator;

public class ProfileJson {

	private static final String TAG = ProfileJson.class.getName();

	private static final String JSONFilePath = "" + "playerProfile.json";
	private String username;
	private int id;
	private int difficulty;
	private boolean hasProfile;
	private boolean stageOneUnlocked;
	private boolean stageTwoUnlocked;
	private boolean stageThreeUnlocked;
	private boolean stageFourUnlocked;
	private boolean stageFiveUnlocked;
	private boolean stageSixUnlocked;
	
	//complete object with all the profiles
	private JSONObject jsonObj;
	private boolean dirtyBit;
	
	//Constructor
	public ProfileJson(String username){
		this.username = username;
		this.id = -1;
		this.difficulty = -1;
	}
	
	//Setter
	public void setUsername(String username){
		this.dirtyBit = bool;
		this.username = username;
	}
	public void setID(int id){
		this.dirtyBit = bool;
		this.id = id;
	}
	public void setDifficulty(int difficulty){
		this.dirtyBit = true;
		this.difficulty = difficulty;
	}
	public void setStageOneUnlocked(boolean bool){
		this.dirtyBit = true;
		this.stageOneUnlocked = bool;
	}
	public void setStageTwoUnlocked(boolean bool){
		this.dirtyBit = true;
		this.stageTwoUnlocked = bool;
	}
	public void setStageThreeUnlocked(boolean bool){
		this.dirtyBit = true;
		this.stageThreeUnlocked = bool;
	}
	public void setStageFourUnlocked(boolean bool){
		this.dirtyBit = true;
		this.stageFourUnlocked = bool;
	}
	public void setStageFiveUnlocked(boolean bool){
		this.dirtyBit = true;
		this.stageFiveUnlocked = bool;
	}
	public void setStageSixUnlocked(boolean bool){
		this.dirtyBit = true;
		this.stageSixUnlocked = bool;
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
	public boolean isStageOneUnlocked(){
		return this.stageOneUnlocked;
	}
	public boolean isStageTwoUnlocked(){
		return this.stageTwoUnlocked;
	}
	public boolean isStageThreeUnlocked(){
		return this.stageThreeUnlocked;
	}
	public boolean isStageFourUnlocked(){
		return this.stageFourUnlocked;
	}
	public boolean isStageFiveUnlocked(){
		return this.stageFiveUnlocked;
	}
	public boolean isStageSixUnlocked(){
		return this.stageSixUnlocked;
	}
	public boolean getDirtyBit(){
		return this.dirtyBit;
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
		
		try{
			br = new BufferedReader(new FileReader(this.JSONFilePath));
			jsonString = br.readLine();
			tokener = new JSONTokener(jsonString);
			
			this.jsonObj = new JSONObject(tokener);
			obj = this.jsonObj.getJSONObject(this.username);
			
			//populate the data
			this.id = (int) obj.get("id");
			this.difficulty = (int) obj.get("difficulty");
			this.stageOneUnlocked = (boolean) obj.get(stageOneUnlocked);
			this.stageTwoUnlocked = (boolean) obj.get(stageTwoUnlocked);
			this.stageThreeUnlocked = (boolean) obj.get(stageThreeUnlocked);
			this.stageFourUnlocked = (boolean) obj.get(stageFourUnlocked);
			this.stageFiveUnlocked = (boolean) obj.get(stageFiveUnlocked);
			this.stageSixUnlocked = (boolean) obj.get(stageSixUnlocked);
			
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
	public void updateJsonObject(){\
		
		JSONObject obj;
	
		if (this.getDirtyBit()){
			this.jsonObj.remove(this.getUsername);
			obj = generateProfileObject();
			try {
				this.jsonObj.put(this.getUsername, obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			writeJSONFile(this.jsonObj);
		
		}else{
			Gdx.app.log(TAG, "nothing to update")
			return;
		}
	}
	
	public JSONObject generateProfileObject(){
		JSONObject obj = new JSONObject();
		try {
			obj.put("username", this.username);
			obj.put("id", this.id);
			obj.put("difficulty", this.difficulty);
			obj.put("stageOneUnlocked", this.stageOneUnlocked);
			obj.put("stageTwoUnlocked", this.stageTwoUnlocked);
			obj.put("stageThreeUnlocked", this.stageThreeUnlocked);
			obj.put("stageFourUnlocked", this.stageFourUnlocked);
			obj.put("stageFiveUnlocked", this.stageFiveUnlocked);
			obj.put("stageSixUnlocked", this.stageSixUnlocked);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
		
	}
}

