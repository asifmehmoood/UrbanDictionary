package com.asif.urbandictionary.module;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class DictResponse {

    @SerializedName("definition")
    public String definition;

    @SerializedName("permalink")
    public String permalink;

    @SerializedName("thumbs_up")
    public int thumbs_up;

    @SerializedName("sound_urls")
    public ArrayList sound_urls;

    @SerializedName("author")
    public String author;

    @SerializedName("word")
    public String word;

    @SerializedName("defid")
    public int defid;

    @SerializedName("current_vote")
    public String current_vote;

    @SerializedName("written_on")
    public Date written_on;

    @SerializedName("example")
    public String example;

    @SerializedName("thumbs_down")
    public int thumbs_down;

    public DictResponse(){

    }

    /**
     * testing with some dummy values
     * @param thumbs_up
     * @param thumbs_down
     */
    public DictResponse(int thumbs_up ,int thumbs_down) {
        this.thumbs_up = thumbs_up;
        this.thumbs_down = thumbs_down;
    }

}
