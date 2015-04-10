package cz3003.pptx.game.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.net.HttpStatus;

public class StreamManager {

	String serverURL = "http://lish0030.ddns.net/pptx-ws";
	String resObj = null;

	public StreamManager() {

	}
	
	public void uploadJson(String filename, Object obj){
		JSONObject jobj = new JSONObject(obj);
		uploadJson(filename, jobj);
	}

	public void uploadJson(String filename, JSONObject jobj) {
		
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("file", filename);
		parameters.put("json", jobj.toString());

		HttpRequest httpGet = new HttpRequest(HttpMethods.GET);
		httpGet.setUrl(serverURL + "/savejson");
		httpGet.setContent(HttpParametersUtils
				.convertHttpParameters(parameters));
		Gdx.net.sendHttpRequest(httpGet, new HttpResponseListener() {
			public void handleHttpResponse(HttpResponse httpResponse) {
				String status = httpResponse.getResultAsString();
				Gdx.app.log("WEB", status);
			}

			public void failed(Throwable t) {
				Gdx.app.log("WEB", "Upload failed");
				// do stuff here based on the failed attempt
			}

			@Override
			public void cancelled() {
				// TODO Auto-generated method stub
				Gdx.app.log("WEB", "Upload cancelled");
			}
		});
	}

	
	public JSONObject readJson(String filename) {
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("file", filename);
		resObj = null;
		HttpRequest httpGet = new HttpRequest(HttpMethods.GET);
		httpGet.setUrl(serverURL + "/getjson");
		httpGet.setContent(HttpParametersUtils
				.convertHttpParameters(parameters));
		Gdx.net.sendHttpRequest(httpGet, new HttpResponseListener() {
			public void handleHttpResponse(HttpResponse httpResponse) {
				resObj = httpResponse.getResultAsString();
				Gdx.app.log("READ", resObj);
			}

			public void failed(Throwable t) {
				Gdx.app.log("WEB", "Retrieve failed");
				// do stuff here based on the failed attempt
			}

			@Override
			public void cancelled() {
				// TODO Auto-generated method stub
				Gdx.app.log("WEB", "Retrieve cancelled");
			}
		});

		// Wait 20 at most
		for (int i = 0; i < 20; i++) {
			if (resObj != null) {
				try {
					JSONObject outRes = new JSONObject(resObj);
					return outRes;
				} catch (JSONException e) {
					Gdx.app.log("JS", "JSON FAIL AT READ");
					return null;
				}
			}
			try {
				TimeUnit.MILLISECONDS.sleep(50);
			} catch (InterruptedException e) {
				Gdx.app.log("WS", "Unable to wait for some reason.");
			}
		}
		return null;
	}
	
	public List<String> getFileList(){
		resObj = null;
		HttpRequest httpGet = new HttpRequest(HttpMethods.GET);
		httpGet.setUrl(serverURL + "/getsaves");
		Gdx.net.sendHttpRequest(httpGet, new HttpResponseListener() {
			public void handleHttpResponse(HttpResponse httpResponse) {
				resObj = httpResponse.getResultAsString();
				Gdx.app.log("READ", resObj);
			}

			public void failed(Throwable t) {
				Gdx.app.log("WEB", "Retrieve failed");
				// do stuff here based on the failed attempt
			}

			@Override
			public void cancelled() {
				// TODO Auto-generated method stub
				Gdx.app.log("WEB", "Retrieve cancelled");
			}
		});

		// Wait 20 times at most
		for (int i = 0; i < 20; i++) {
			if (resObj != null) {
				try {
					JSONArray jAry = new JSONArray(resObj);
					List<String> list = new ArrayList<String>();
					for(int t = 0; t < jAry.length(); t++){
					    list.add(jAry.getString(t));
					}
					return list;
				} catch (JSONException e) {
					Gdx.app.log("JS", "JSON FAIL AT READ");
					return null;
				}			
			} 
			
			try {
				TimeUnit.MILLISECONDS.sleep(50);
			} catch (InterruptedException e) {
				Gdx.app.log("WS", "Unable to wait for some reason.");
			}
		}
		return null;
	}
}
