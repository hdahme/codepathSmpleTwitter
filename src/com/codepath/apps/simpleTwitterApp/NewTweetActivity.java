package com.codepath.apps.simpleTwitterApp;

import com.codepath.apps.simpleTwitterApp.models.Tweet;
import com.codepath.apps.simpleTwitterApp.models.User;

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
import android.os.Build;

public class NewTweetActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_tweet);
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
	
	public void onComposeClick(MenuItem mi, View view) {
		String name = "harrison";
		String body = "hello";
		String timestamp = "right : now :";
		
		Tweet tweet = new Tweet(new User(name), body, timestamp);
		Intent data = new Intent();		
		data.putExtra(TimelineActivity.COMPOSE_KEY, tweet);
		setResult(RESULT_OK, data);
		finish();
	}

}
