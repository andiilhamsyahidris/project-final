package id.ftrh.tvdiscoverv02.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastResponse {

    @SerializedName("cast")
    private List<Cast> castList;

    public List<Cast> getCasts(){
        return castList;
    }
}
