package id.ftrh.tvdiscoverv02.data.api.repository.callback;

import java.util.List;

import id.ftrh.tvdiscoverv02.data.models.Cast;

public interface OnCastCallback {
    void onSuccess(List<Cast> castList, String message);
    void onFailure(String message);
}
