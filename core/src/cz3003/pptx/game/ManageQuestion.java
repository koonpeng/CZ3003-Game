package cz3003.pptx.game;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;


public class ManageQuestion {

	private JSONObject test;
	private String[] custom_test;
	private String userid ="";
	
	public ManageQuestion(String id) throws IOException{
		userid =id;
		if(testTrue()==true){
			loadQns(userid);
			
		}
	}
	
	private boolean testTrue(){
		File file = new File(userid+"student.txt");
		if(file.exists()){
			return true;
		}
		else{
			return false;
		}
	}
	
	//get wns lit length
	public int getLength(){
		if(testTrue()){
		for(int i=0; i<200;i++){
			if(custom_test[i]!=null){
				
			}
		
			else{
				return i;}
		}}
		return -1;
		
	}
	
	//get qns of position
		public String[] getQnsPos(int pos) throws JSONException{
			
			if(custom_test[pos].isEmpty()||custom_test[pos]==null){
				return null;
			}
			else{ 
				
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
	
	//load qns if user have existing test
	private void loadQns(String id) throws IOException{
		File file = new File(id+"student.txt");
		custom_test = new String[100];
		custom_test = readFile(file);
	}
	
	//read file
	private static String[] readFile(File f) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(f));
		String[] qna = new String[20];
		String line = null;
		int i=0;
		while ((line = br.readLine()) != null) {
			qna[i] = line;
			
			i++;
		}
		br.close();
		return qna;	
	}
	
	
	public void editQns(String[] qns,int pos) throws JSONException{
		if(pos<getLength()){
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
		}
	}
	
	public void commitQns(){
		try {
 
			File file = new File(userid+"student.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			else if(file.exists()){
				file.delete();
			}
			FileWriter fw = new FileWriter(file,false);
			BufferedWriter bw = new BufferedWriter(fw);
			int i=0;
			while(i != getLength()){
			bw.write(custom_test[i]+"\n");
			i++;
			}
			bw.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	public static void clearQns(String id){
		File file = new File(id+"student.txt");
		if(file.exists()){
			file.delete();
		}
	}
	
	
}
