package id.ftrh.tvdiscoverv02.data.api;

import id.ftrh.tvdiscoverv02.data.models.Cast;
import id.ftrh.tvdiscoverv02.data.models.CastResponse;
import id.ftrh.tvdiscoverv02.data.models.TvShow;
import id.ftrh.tvdiscoverv02.data.models.TvShowResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    @GET("tv/{sort_by}")
    Call<TvShowResponse> getResults(
            @Path("sort_by") String sortBy,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("tv/{id}")
    Call<TvShow> getDetail(
            @Path("id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("movie/{id}/credits")
    Call<CastResponse> getCast(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );

    @GET("search/tv")
    Call<TvShowResponse> search(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("language") String language,
            @Query("page") int page
    );


}
