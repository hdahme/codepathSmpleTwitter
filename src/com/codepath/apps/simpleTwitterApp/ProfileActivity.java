package com.codepath.apps.simpleTwitterApp;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpleTwitterApp.fragments.UserTimelineFragment;
import com.codepath.apps.simpleTwitterApp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends ActionBarActivity {
	
	private User user;
	private long uid;
	private TextView tvUserName;
	private TextView tvDescription;
	private TextView tvNumFollowers;
	private TextView tvNumFollowing;
	private ImageView ivTimelineProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		tvDescription = (TextView) findViewById(R.id.tvDesciption);
		tvNumFollowers = (TextView) findViewById(R.id.tvNumFollowers);
		tvNumFollowing = (TextView) findViewById(R.id.tvNumFollowing);
		ivTimelineProfile = (ImageView) findViewById(R.id.ivUserTimelineProfile);
		
		loadProfileInfo();
		populateTimeline();
		
	}
	
	public void loadProfileInfo() {
		
		uid = (long) getIntent().getLongExtra(TimelineActivity.USER_ID_KEY, 0);
		TwitterClientApp.getRestClient().getUserInfoById(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray jsonUserArray) {
				try {
					user = User.fromJson(jsonUserArray.getJSONObject(0));
					getActionBar().setTitle("@"+user.getScreenName());
					tvUserName.setText(user.getScreenName());
					tvNumFollowers.setText(user.getFollowersCount() + " Followers");
					tvNumFollowing.setText(user.getFriendsCount() + " Following");
					tvDescription.setText(user.getDescription());
					ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivTimelineProfile);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		}, uid);
	}
	
	public void populateTimeline(){
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
