package com.codepath.apps.simpleTwitterApp.fragments;

import org.json.JSONArray;

import com.codepath.apps.simpleTwitterApp.R;
import com.codepath.apps.simpleTwitterApp.TweetsAdapter;
import com.codepath.apps.simpleTwitterApp.TwitterClientApp;
import com.codepath.apps.simpleTwitterApp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.widget.ListView;

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
			}
		});
	}
}
