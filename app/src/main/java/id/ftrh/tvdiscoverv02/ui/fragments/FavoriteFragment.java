package id.ftrh.tvdiscoverv02.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import id.ftrh.tvdiscoverv02.ImageSize;
import id.ftrh.tvdiscoverv02.R;
import id.ftrh.tvdiscoverv02.data.local.FavoritTv;
import id.ftrh.tvdiscoverv02.data.models.TvShow;
import id.ftrh.tvdiscoverv02.ui.activities.DetailActivity;
import id.ftrh.tvdiscoverv02.ui.adapters.FavoriteTvAdapter;
import id.ftrh.tvdiscoverv02.ui.adapters.clicklistener.OnItemClickListener;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FavoriteFragment extends Fragment implements OnItemClickListener {
    private RecyclerView recyclerView;
    private Realm backgroundThreadRealm;
    private FavoriteTvAdapter adapter;;
    private ConstraintLayout clEmpty;


    public FavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Realm.init(getContext());

        String realmName = "final project";
        RealmConfiguration config = new RealmConfiguration.Builder().allowWritesOnUiThread(true).
                name(realmName).
                build();

        backgroundThreadRealm = Realm.getInstance(config);

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = view.findViewById(R.id.rv_favorite);
        clEmpty = view.findViewById(R.id.clEmpty);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        List<FavoritTv> favoriteTvs = (List) backgroundThreadRealm.where(FavoritTv.class).findAll();

        if (favoriteTvs.size() == 0) {
            clEmpty.setVisibility(View.VISIBLE);
        } else {
            adapter = new FavoriteTvAdapter(favoriteTvs);
            adapter.setClickListener(FavoriteFragment.this);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(linearLayoutManager);
            clEmpty.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        List<FavoritTv> favoriteTvs = (List) backgroundThreadRealm.where(FavoritTv.class).findAll();
        if (favoriteTvs.size() == 0) {
            clEmpty.setVisibility(View.VISIBLE);
        } else {
            adapter = new FavoriteTvAdapter(favoriteTvs);
            adapter.setClickListener(FavoriteFragment.this);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
            clEmpty.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(TvShow tvShow) {

    }
    @Override
    public void onClick(FavoritTv tv) {
        Intent detailActivity = new Intent(getContext(), DetailActivity.class);
        detailActivity.putExtra("ID", tv.getId());
        detailActivity.putExtra("TITLE", tv.getTitle());
        detailActivity.putExtra("POSTER_PATH", tv.getPosterPath(ImageSize.W154));
        detailActivity.putExtra("SELECTED_FRAGMENT", "tv_show");
        startActivity(detailActivity);
    }




}
