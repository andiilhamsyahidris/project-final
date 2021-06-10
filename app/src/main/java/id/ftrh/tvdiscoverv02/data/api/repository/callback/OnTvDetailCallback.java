package id.ftrh.tvdiscoverv02.data.api.repository.callback;

import id.ftrh.tvdiscoverv02.data.models.Cast;
import id.ftrh.tvdiscoverv02.data.models.TvShow;

public interface OnTvDetailCallback {
    void onSuccess(TvShow tvShow, String message);

    void onFailure(String message);
}
