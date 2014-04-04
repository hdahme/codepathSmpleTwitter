package com.codepath.apps.simpleTwitterApp;

import java.util.ArrayList;

import com.codepath.apps.simpleTwitterApp.models.Tweet;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


public class TweetsClickListener implements OnItemClickListener {
	private ArrayList<Tweet> tweets;
	
	public TweetsClickListener(ArrayList<Tweet> tweets) {
		this.tweets = tweets;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent i = new Intent(view.getContext(), ProfileActivity.class);
		Tweet tweet = tweets.get(position);
		i.putExtra(TimelineActivity.USER_ID_KEY, tweet.getUser().getId());
		view.getContext().startActivity(i);
		
	}

}
