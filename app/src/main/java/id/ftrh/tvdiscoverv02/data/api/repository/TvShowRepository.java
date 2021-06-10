package id.ftrh.tvdiscoverv02.data.api.repository;

import android.support.annotation.NonNull;

import id.ftrh.tvdiscoverv02.Const;
import id.ftrh.tvdiscoverv02.data.api.Service;
import id.ftrh.tvdiscoverv02.data.api.repository.callback.OnCastCallback;
import id.ftrh.tvdiscoverv02.data.api.repository.callback.OnSearchCallback;
import id.ftrh.tvdiscoverv02.data.api.repository.callback.OnTvDetailCallback;
import id.ftrh.tvdiscoverv02.data.api.repository.callback.OnTvShowCallback;
import id.ftrh.tvdiscoverv02.data.models.Cast;
import id.ftrh.tvdiscoverv02.data.models.CastResponse;
import id.ftrh.tvdiscoverv02.data.models.TvShow;
import id.ftrh.tvdiscoverv02.data.models.TvShowResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowRepository {
    private static TvShowRepository repository;
    private Service service;

    private TvShowRepository(Service service) {
        this.service = service;
    }

    public static TvShowRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Const.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            repository = new TvShowRepository(retrofit.create(Service.class));
        }
        return repository;
    }

    public void getTvShow(String sortBy, int page, final OnTvShowCallback callback) {
        service.getResults(sortBy, Const.API_KEY, Const.getLang(), page)
                .enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getResults() != null) {
                                    callback.onSuccess(response.body().getPage(), response.body().getResults());
                                } else {
                                    callback.onFailure("response.body().getResults() is null");
                                }
                            } else {
                                callback.onFailure("response.body() is null");
                            }
                        } else {
                            callback.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                        callback.onFailure(t.getLocalizedMessage());
                    }
                });
    }

    public void getTvDetail(int id, final OnTvDetailCallback callback) {
        service.getDetail(id, Const.API_KEY, Const.getLang())
                .enqueue(new Callback<TvShow>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShow> call, @NonNull Response<TvShow> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                callback.onSuccess(response.body(), response.message());
                            } else {
                                callback.onFailure("response.body() is null");
                            }
                        } else {
                            callback.onFailure(response.message() + ", Error Code : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShow> call, @NonNull Throwable t) {
                        callback.onFailure(t.getLocalizedMessage());
                    }
                });
    }

    public void search(String query, int page, final OnSearchCallback callback) {
        service.search(Const.API_KEY, query, Const.getLang(), page)
                .enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getResults() != null) {
                                    callback.onSuccess(response.body().getResults(), response.message(), response.body().getPage());
                                } else {
                                    callback.onFailure("No Results");
                                }
                            } else {
                                callback.onFailure("response.body() is null");
                            }
                        } else {
                            callback.onFailure(response.message() + " : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                        callback.onFailure(t.getLocalizedMessage());
                    }
                });
    }
    public void getCasts(int tvId, final OnCastCallback callback) {
        service.getCast(tvId, Const.API_KEY)
                .enqueue(new Callback<CastResponse>() {
                    @Override
                    public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body() != null) {
                                    callback.onSuccess(response.body().getCasts(), response.message());
                                } else {
                                    callback.onFailure("No Casts");
                                }
                            } else {
                                callback.onFailure("response.body() is null");
                            }
                        } else {
                            callback.onFailure(response.message() + " : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<CastResponse> call, Throwable t) {
                        callback.onFailure(t.getLocalizedMessage());
                    }
                });
    }
}