package com.codepath.apps.simpleTwitterApp.models;

import java.io.Serializable;

import org.json.JSONObject;

public class User extends BaseModel implements Serializable{
	private String name;
	private String id;
	
	public User() {
		super();
	}
	
    public User(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}

	public String getName() {
		if (this.name == null) {
			this.name = getString("name");
		}
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
    
    public String getIdStr() {
    	return this.id;
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