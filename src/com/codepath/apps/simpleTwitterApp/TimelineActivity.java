package com.codepath.apps.simpleTwitterApp;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.simpleTwitterApp.models.Tweet;
import com.codepath.apps.simpleTwitterApp.EndlessScrollListener;
import com.codepath.apps.simpleTwitterApp.R;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends ActionBarActivity {
	
	private ListView lvTweets;
	private TweetsAdapter adapter;
	private ArrayList<Tweet> tweets;

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
				System.out.print("HELLO");
				tweets.addAll(Tweet.fromJson(jsonTweets));
				adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
			}
			
		});
		
	}
	
	public void onComposeClick(MenuItem mi) {
		
	}

}
