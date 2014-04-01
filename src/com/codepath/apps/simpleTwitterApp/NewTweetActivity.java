package com.codepath.apps.simpleTwitterApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codepath.apps.simpleTwitterApp.models.Tweet;
import com.codepath.apps.simpleTwitterApp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class NewTweetActivity extends ActionBarActivity {
	
	private EditText etBody;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_tweet);
		etBody = (EditText) findViewById(R.id.etBody);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_tweet, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onComposeClick(View view) {
		String status = etBody.getText().toString();
		
		TwitterClientApp.getRestClient().postTweet(new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject jsonTweet) {
				Intent data = new Intent();	
				try {
					data.putExtra(TimelineActivity.STATUS_KEY, etBody.getText().toString());
					data.putExtra(TimelineActivity.TIMESTAMP_KEY, jsonTweet.getString("created_at"));
					setResult(RESULT_OK, data);
					Toast.makeText(getBaseContext(), "Posting Tweet", Toast.LENGTH_SHORT).show();
					finish();
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(getBaseContext(), "There was an error posting tweet", Toast.LENGTH_SHORT).show();
				}
			}
			
		}, status);
	}
	
	public void onCancelClick(View view) {
		finish();
	}

}
