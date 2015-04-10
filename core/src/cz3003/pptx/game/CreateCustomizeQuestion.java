package cz3003.pptx.game;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;


public class CreateCustomizeQuestion {
	
	private JSONObject test;
	private String[] custom_test;
	private int count =0;
	private int size=0;
	private String userid ="";
	
	public CreateCustomizeQuestion(String id, int size) throws IOException{
		custom_test = new String[size];
		userid =id;
		count =0;
		this.size = size;
	}
	
	//check if user have a test created already
	private boolean testTrue(String id){

		File file = new File("sdcard/" + userid+".txt");

		if(file.exists()){
			return true;
		}
		else{
			return false;
		}
	}
	
//	//load qns if user have existing test
//	private String[] loadQns(int id) throws IOException{
//		File file = new File(id+"student.txt");
//		String[] test = new String[5];
//		test = readFile(file);
//		return test;
//	}
	
	//add qns by position [B][Title][A][B][C][D][ANSWER]
	public void addQns(String[] qns, int pos) throws JSONException{
		if(pos<size){
		test = new JSONObject();
		if(qns[0].equals("A")){
			test.put("Type", qns[0]);
			test.put("Question", qns[1]);
			test.put("Answer", qns[2]);
		}
		else if(qns[0].equals("B")){
			test.put("Type", qns[0]);
			test.put("Question", qns[1]);
			test.put("A", qns[2]);
			test.put("B", qns[3]);
			test.put("C", qns[4]);
			test.put("D", qns[5]);
			test.put("Answer", qns[6]);
		}
		custom_test[pos] = test.toString();
		count++;
		}
	}
	
	
	//get qns of position
	public String[] getQnsPos(int pos) throws JSONException{
		if(custom_test[pos]==null){
			return null;
		}
		else
		{
		JSONObject review = new JSONObject(custom_test[pos]);
		String[] qns = null;
		if(review.getString("Type").equals("A")){
			qns = new String[3];
			qns[0] = review.getString("Type");
			qns[1] = review.getString("Question");
			qns[2] = review.getString("Answer");
		}
		else if(review.getString("Type").equals("B")){
			qns = new String[7];
			qns[0] = review.getString("Type");
			qns[1] = review.getString("Question");
			qns[2] = review.getString("A");
			qns[3] = review.getString("B");
			qns[4] = review.getString("C");
			qns[5] = review.getString("D");
			qns[6] = review.getString("Answer");
		}
		return qns;}
	}
	
	//write out qns to file(change to append)
	public void commitQns(){
		
		

		
			  File logFile = new File("sdcard/" + userid+".txt");
		        if (!logFile.exists()) {
		            try {
		                logFile.createNewFile();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		        try {
		            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
		            int i=0;
		            while(i != size){
		            buf.append(custom_test[i]+"\n");
		            i++;
		            }
		            buf.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }


		
		
		}
	
	}
	

