package com.codepath.apps.simpleTwitterApp.models;

import org.json.JSONObject;

public class User extends BaseModel {
	private String name;
	
	public User() {
		super();
	}
	
    public User(String name) {
		super();
		this.name = name;
	}

	public String getName() {
        this.name = getString("name");
        return this.name;
    }

    public long getId() {
        return getLong("id");
    }

    public String getScreenName() {
        return getString("screen_name");
    }

    public String getProfileBackgroundImageUrl() {
        return getString("profile_background_image_url");
    }
    
    public String getProfileImageUrl() {
        return getString("profile_image_url");
    }

    public int getNumTweets() {
        return getInt("statuses_count");
    }

    public int getFollowersCount() {
        return getInt("followers_count");
    }

    public int getFriendsCount() {
        return getInt("friends_count");
    }

    public static User fromJson(JSONObject json) {
        User u = new User();

        try {
            u.jsonObject = json;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return u;
    }


}