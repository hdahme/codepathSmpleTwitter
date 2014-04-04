package com.codepath.apps.simpleTwitterApp.fragments;

import org.json.JSONArray;

import com.codepath.apps.simpleTwitterApp.ProfileActivity;
import com.codepath.apps.simpleTwitterApp.R;
import com.codepath.apps.simpleTwitterApp.TimelineActivity;
import com.codepath.apps.simpleTwitterApp.TweetsAdapter;
import com.codepath.apps.simpleTwitterApp.TweetsClickListener;
import com.codepath.apps.simpleTwitterApp.TwitterClientApp;
import com.codepath.apps.simpleTwitterApp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MentionsFragment extends TweetsListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	public void initTweets(){
		TwitterClientApp.getRestClient().getMentions(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray jsonTweets) {
				// TODO Auto-generated method stub
				tweets = Tweet.fromJson(jsonTweets);
				adapter = new TweetsAdapter(getActivity(), tweets);
				lvTweets.setAdapter(adapter);
				lvTweets.setOnItemClickListener(new TweetsClickListener(tweets));
			}
		});
	}
	
	public void moreTweets() {
		TwitterClientApp.getRestClient().getMentions(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray jsonTweets) {
				// TODO Auto-generated method stub
				if (tweets.size() >= 25) {
					return;
				}
				tweets.addAll(Tweet.fromJson(jsonTweets));
				adapter = new TweetsAdapter(getActivity(), tweets);
				lvTweets.setAdapter(adapter);
				lvTweets.setOnItemClickListener(new TweetsClickListener(tweets));
			}
		});
	}
}
