package com.codepath.apps.simpleTwitterApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.util.Log;
import com.codepath.apps.simpleTwitterApp.fragments.UserTimelineFragment;
import com.codepath.apps.simpleTwitterApp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class ProfileActivity extends ActionBarActivity {
	
	private User user;
	private long uid;
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		loadProfileInfo();
		populateFragment();
		
	}
	
	public void loadProfileInfo() {
		
		uid = (long) getIntent().getLongExtra(TimelineActivity.USER_ID_KEY, 0);
		TwitterClientApp.getRestClient().getUserInfoById(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray jsonUserArray) {
				try {
					user = User.fromJson(jsonUserArray.getJSONObject(0));
					getActionBar().setTitle("@"+user.getScreenName());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		}, uid);
	}
	
	public void populateFragment(){
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(uid);
		ft.replace(R.id.frameTimeline, userTimelineFragment);
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
}
