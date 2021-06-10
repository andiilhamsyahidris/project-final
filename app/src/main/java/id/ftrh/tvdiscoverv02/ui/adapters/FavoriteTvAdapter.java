package id.ftrh.tvdiscoverv02.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import id.ftrh.tvdiscoverv02.ImageSize;
import id.ftrh.tvdiscoverv02.R;
import id.ftrh.tvdiscoverv02.data.local.FavoritTv;
import id.ftrh.tvdiscoverv02.ui.adapters.clicklistener.OnItemClickListener;

public class FavoriteTvAdapter extends RecyclerView.Adapter<FavoriteTvAdapter.ViewHolder> {
    private List<FavoritTv> tvList;
    private OnItemClickListener clickListener;

    public FavoriteTvAdapter(List<FavoritTv> tvList) {
        this.tvList = tvList;
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.onBindItemView(tvList.get(i));
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FavoritTv tv;
        ImageView ivPoster;
        TextView tvName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvName = itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(this);
        }


        void onBindItemView(FavoritTv tv) {
            this.tv = tv;
            String imageUri = tv.getPosterPath(ImageSize.W154);
            String title = tv.getTitle();
            Glide.with(itemView.getContext())
                    .load(imageUri)
                    .into(ivPoster);
            tvName.setText(title);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(tv);
        }
    }
}
