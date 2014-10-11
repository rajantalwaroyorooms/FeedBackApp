package com.example.feedback;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class FeedBackAsyncTask extends AsyncTask<String, Void, String> {
	
	private ProgressDialog progressDialog;
	private JSONObject jsonObject;
	private Context _context;
	
	public FeedBackAsyncTask(Context context, JSONObject object) {
		_context = context;
		jsonObject = object;
		
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(_context, "Please Wait", 
				                              "Sending feedback",true,false);
		progressDialog.show();
	}

	@Override
	protected String doInBackground(String... params) {
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		progressDialog.dismiss();
	}

}
