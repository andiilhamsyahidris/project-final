package id.ftrh.tvdiscoverv02.data.models;

import com.google.gson.annotations.SerializedName;

import id.ftrh.tvdiscoverv02.Const;
import id.ftrh.tvdiscoverv02.ImageSize;

public class Cast {
    @SerializedName("profile_path")
    private String profile_path;
    @SerializedName("name")
    private String name;
    @SerializedName("character")
    private String character;

    public String getProfilePath(ImageSize size) {
        return Const.IMG_URL + size.getValue() + profile_path;
    }
    public String getName() {
        return name;
    }
    public String getCharacter() {
        return character;
    }
}
