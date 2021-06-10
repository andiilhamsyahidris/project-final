package id.ftrh.tvdiscoverv02.data.api.repository.callback;

import java.util.List;

import id.ftrh.tvdiscoverv02.data.models.TvShow;

public interface OnTvShowCallback {
    void onSuccess(int page, List<TvShow> tvShowList);

    void onFailure(String message);
}
