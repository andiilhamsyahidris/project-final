package id.ftrh.tvdiscoverv02;

import java.util.Locale;

public class Const {

    public static final String API_KEY = "db2cb365df74aa3d69177277ba3490a7";
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String IMG_URL = "https://image.tmdb.org/t/p/";
    public static final String IMG_URL_200 = "https://image.tmdb.org/t/p/w200/";

    public static String getLang() {
        switch (Locale.getDefault().toString()) {
            case "in_ID":
                return "id-ID";
            default:
                return "en-US";
        }
    }

}
