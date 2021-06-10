package id.ftrh.tvdiscoverv02.data.api.repository.callback;

import java.util.List;

import id.ftrh.tvdiscoverv02.data.models.TvShow;

public interface OnSearchCallback {
    void onSuccess(List<TvShow> tvShowList, String msg, int page);

    void onFailure(String msg);
}
