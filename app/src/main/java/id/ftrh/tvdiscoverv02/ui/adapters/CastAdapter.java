package id.ftrh.tvdiscoverv02.ui.adapters;

import android.content.Context;
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
import id.ftrh.tvdiscoverv02.data.models.Cast;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {
    private List<Cast> castList;
    private Context context;

    public CastAdapter(List<Cast> castList, Context mContext) {
        this.castList = castList;
        this.context = mContext;
        System.out.println(castList.size());
    }

    @NonNull
    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_cast, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastAdapter.ViewHolder viewHolder, int i) {
        String imgUri = castList.get(i).getProfilePath(ImageSize.W342);
        Glide.with(viewHolder.itemView.getContext())
                .load(imgUri)
                .into(viewHolder.ivProfile);
        viewHolder.tvChar.setText(castList.get(i).getCharacter());
        viewHolder.tvName.setText(castList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile;
        TextView tvName, tvChar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.ivCast);
            tvName = itemView.findViewById(R.id.nameCast);
            tvChar = itemView.findViewById(R.id.charCast);
        }
    }
}


