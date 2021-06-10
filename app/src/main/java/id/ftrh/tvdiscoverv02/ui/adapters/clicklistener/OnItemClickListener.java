package id.ftrh.tvdiscoverv02.ui.adapters.clicklistener;

import id.ftrh.tvdiscoverv02.data.local.FavoritTv;
import id.ftrh.tvdiscoverv02.data.models.TvShow;

public interface OnItemClickListener {
    void onClick(TvShow tvShow);
    void onClick(FavoritTv tv);
}
