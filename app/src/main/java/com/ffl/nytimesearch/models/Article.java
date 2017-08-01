package com.ffl.nytimesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by PJS on 7/28/2017.
 */

public class Article implements Serializable {
    public String webUrl;
    public String thumNail;
    public String headline;

    public String getWebUrl() {
        return webUrl;
    }

    public String getThumNail() {
        return thumNail;
    }

    public String getHeadline() {
        return headline;
    }

    public  Article(JSONObject jsonObject)
    {
    try {
        this.headline=jsonObject.getJSONObject("headline").getString("main");
        this.webUrl=jsonObject.getString("web_url");
        JSONArray multimedia = jsonObject.getJSONArray("multimedia");
        if(multimedia.length()>0){
            JSONObject multimediajson =multimedia.getJSONObject(0);
            this.thumNail="http://www.nytimes.com"+multimediajson.getString("url");
        }else
        {
            this.thumNail="";
        }

    }catch (JSONException e){e.printStackTrace();}
    }

    public static ArrayList<Article> fromJSONArray(JSONArray array)
    {
        ArrayList<Article>results=new ArrayList<>();
        for(int i=0;i<array.length();i++) {
            try {
                results.add(new Article(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
