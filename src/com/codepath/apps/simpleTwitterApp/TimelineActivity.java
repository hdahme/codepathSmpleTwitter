package com.codepath.apps.simpleTwitterApp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.simpleTwitterApp.fragments.HomeTimelineFragment;
import com.codepath.apps.simpleTwitterApp.fragments.MentionsFragment;
import com.codepath.apps.simpleTwitterApp.fragments.TweetsListFragment;
import com.codepath.apps.simpleTwitterApp.models.Tweet;
import com.codepath.apps.simpleTwitterApp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends ActionBarActivity implements TabListener {
	
	private ArrayList<Tweet> tweets;
	private User authenticatedUser;
	
	public static final int COMPOSE_REQUEST = 100;
	public static final String COMPOSE_KEY = "compose";
	public static final String STATUS_KEY = "status";
	public static final String USER_ID_KEY = "user";
	public static final String TIMESTAMP_KEY = "created_at";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		if (authenticatedUser == null){
			TwitterClientApp.getRestClient().getCurrentlyAuthenticatedUser(new JsonHttpResponseHandler() {

				@Override
				public void onSuccess(JSONObject jsonUser) {
					authenticatedUser = User.fromJson(jsonUser);
				}
			});
		}
		setupNavigationTabs();
	}

	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab().setText("Home")
				.setTag("HomeTimelineFragment").setIcon(R.drawable.ic_home).setTabListener(this);
		Tab tabMentions = actionBar.newTab().setText("Mentions")
				.setTag("MentionsFragment").setIcon(R.drawable.ic_mentions).setTabListener(this);
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
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
	
	public void onComposeClick(MenuItem mi) {
		Intent i = new Intent(this, NewTweetActivity.class);
		startActivityForResult(i, COMPOSE_REQUEST);
	}
	
	public void onProfileClick(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra(USER_ID_KEY, authenticatedUser.getId());
		startActivity(i);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == COMPOSE_REQUEST) {
	     String newStatus = (String) data.getExtras().getSerializable(STATUS_KEY);
	     String creationTimestamp = (String) data.getExtras().getSerializable(TIMESTAMP_KEY);
	     Toast.makeText(this, "Posting tweet", Toast.LENGTH_SHORT).show();
	     
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

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if (tab.getTag().equals("HomeTimelineFragment")) {
			fts.replace(R.id.frameContainer, new HomeTimelineFragment());
		} else {
			fts.replace(R.id.frameContainer, new MentionsFragment());
		}
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
