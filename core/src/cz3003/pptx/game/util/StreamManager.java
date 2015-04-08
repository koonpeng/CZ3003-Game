package cz3003.pptx.game.util;

import java.util.HashMap;

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
	JSONObject resObj = null;

	public StreamManager() {

	}

	public void uploadJson(JSONObject jobj, String filename) {
		HashMap<String, String> parameters = new HashMap();
		parameters.put("fname", filename);
		parameters.put("json", jobj.toString());

		HttpRequest httpGet = new HttpRequest(HttpMethods.POST);
		httpGet.setUrl(serverURL + "/saveJson");
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
		HashMap<String, String> parameters = new HashMap();
		parameters.put("fname", filename);
		resObj = null;
		HttpRequest httpGet = new HttpRequest(HttpMethods.GET);
		httpGet.setUrl(serverURL + "/getJson");
		httpGet.setContent(HttpParametersUtils
				.convertHttpParameters(parameters));
		Gdx.net.sendHttpRequest(httpGet, new HttpResponseListener() {
			public void handleHttpResponse(HttpResponse httpResponse) {
				String responseJson = httpResponse.getResultAsString();
				try {
					resObj = new JSONObject(responseJson);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Gdx.app.log("JS", "JSON FAIL AT READ");
					e.printStackTrace();
				}
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

		// Wait 10s at most
		for (int i = 0; i < 10; i++) {
			if (resObj != null) {
				return resObj;
			}
			try {
				wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Gdx.app.log("WEB", "RESPONSE WAIT UNABLE TO, WHY");
				e.printStackTrace();
			}
		}
		return null;
	}
}
