package com.codepath.apps.simpleTwitterApp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.simpleTwitterApp.models.Tweet;
import com.codepath.apps.simpleTwitterApp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends ActionBarActivity {
	
	private ListView lvTweets;
	private TweetsAdapter adapter;
	private ArrayList<Tweet> tweets;
	private User authenticatedUser;
	
	public static final int COMPOSE_REQUEST = 100;
	public static final String COMPOSE_KEY = "compose";
	public static final String STATUS_KEY = "status";
	public static final String TIMESTAMP_KEY = "created_at";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		initTweets();
		
		lvTweets.setOnScrollListener(new EndlessScrollListener(3) {
			public void onLoadMore(int page, int totalItemsCount) {
				moreTweets();
			}
		});
		
		if (authenticatedUser == null){
			TwitterClientApp.getRestClient().getCurrentlyAuthenticatedUser(new JsonHttpResponseHandler() {

				@Override
				public void onSuccess(JSONObject jsonUser) {
					try {
						authenticatedUser = new User(jsonUser.getString("name"), jsonUser.getString("id_str"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getMenuInflater().inflate(R.menu.timeline, menu);
		//getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		//getActionBar().setCustomView(R.layout.action_bar);
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
	
	public void initTweets(){
		TwitterClientApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray jsonTweets) {
				// TODO Auto-generated method stub
				tweets = Tweet.fromJson(jsonTweets);
				adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);

			}
			
		});
	}
	
	public void moreTweets() {
		TwitterClientApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray jsonTweets) {
				// TODO Auto-generated method stub
				if (tweets.size() >= 25) {
					return;
				}
				tweets.addAll(Tweet.fromJson(jsonTweets));
				adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
			}
			
		});
		
	}
	
	public void onComposeClick(MenuItem mi) {
		Intent i = new Intent(this, NewTweetActivity.class);
		startActivityForResult(i, COMPOSE_REQUEST);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == COMPOSE_REQUEST) {
	     String newStatus = (String) data.getExtras().getSerializable(STATUS_KEY);
	     String creationTimestamp = (String) data.getExtras().getSerializable(TIMESTAMP_KEY);
	     Toast.makeText(this, "Posting tweet", Toast.LENGTH_SHORT).show();
	     initTweets();
	     
	     // Since Twitter writes may be slow, artificially load the new tweet, 
	     // if it hasn't been propagated to all servers yet
	     if (!tweets.get(0).getBody().equals(newStatus) && 
	    		 !tweets.get(0).getTimestampShort().equals(creationTimestamp)) {
	    	 		tweets.add(0, new Tweet(authenticatedUser, newStatus, creationTimestamp));
	    	 		// Commented out for now, getting IllegalMonitorStateException
	    	 		//lvTweets.notify();
	     }
	  }
	}

}
