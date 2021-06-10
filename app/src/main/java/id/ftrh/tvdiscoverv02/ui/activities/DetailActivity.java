package id.ftrh.tvdiscoverv02.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import id.ftrh.tvdiscoverv02.ImageSize;
import id.ftrh.tvdiscoverv02.R;
import id.ftrh.tvdiscoverv02.data.api.repository.callback.OnCastCallback;
import id.ftrh.tvdiscoverv02.data.api.repository.callback.OnTvDetailCallback;
import id.ftrh.tvdiscoverv02.data.api.repository.TvShowRepository;
import id.ftrh.tvdiscoverv02.data.local.FavoritTv;
import id.ftrh.tvdiscoverv02.data.local.FavoriteHelper;
import id.ftrh.tvdiscoverv02.data.models.Cast;
import id.ftrh.tvdiscoverv02.data.models.TvShow;
import id.ftrh.tvdiscoverv02.ui.adapters.CastAdapter;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;

public class DetailActivity extends AppCompatActivity {
    private int id;
    private TvShowRepository repository;
    private ImageView ivBackdrop;
    private ImageView ivPoster;
    private ImageView ivCast;
    private TextView tvName;
    private RatingBar rbRate;
    private TextView tvFirstAirDate;
    private TextView tvLastAirDate;
    private TextView tvSeason;
    private TextView tvEpisode;
    private TextView tvOverview, labelOverview;
    private TextView nameCast, charCast;
    private TextView tvError;
    private FavoriteHelper helper;
    private TvShow tvShow;
    private RecyclerView rvCast;
    private LinearLayout errorDetail;
    private Realm backgroundThreadRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Realm.init(DetailActivity.this);

        String realmName = "final project";
        RealmConfiguration config = new RealmConfiguration.Builder().allowWritesOnUiThread(true).
                name(realmName).
                build();

        backgroundThreadRealm = Realm.getInstance(config);

        setActionBar("");
        ivBackdrop = findViewById(R.id.iv_backdrop);
        ivPoster = findViewById(R.id.iv_poster);
        ivCast = findViewById(R.id.ivCast);
        rbRate = findViewById(R.id.rb_rating);
        rvCast = findViewById(R.id.rvCast);
        tvName = findViewById(R.id.tv_name);
        tvFirstAirDate = findViewById(R.id.tv_first_air_date);
        tvLastAirDate = findViewById(R.id.tv_last_air_date);
        tvSeason = findViewById(R.id.tv_season);
        tvEpisode = findViewById(R.id.tv_episode);
        tvOverview = findViewById(R.id.tv_overview);
        labelOverview = findViewById(R.id.label_overview);
        tvError = findViewById(R.id.tv_error);
        nameCast = findViewById(R.id.nameCast);
        charCast = findViewById(R.id.charCast);
        errorDetail = findViewById(R.id.errorDetail);

        if (getIntent() != null) {
            id = getIntent().getIntExtra("ID", 0);
        }
        repository = TvShowRepository.getInstance();
        getRepositoryData(id);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_detail_activity, menu);
        // TODO: switch favourite button state
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent mainActivity = new Intent(DetailActivity.this, MainActivity.class);
                mainActivity.putExtra("SELECTED_FRAGMENT", getIntent().getStringExtra("SELECTED_FRAGMENT"));
                startActivity(mainActivity);
                return true;
            case R.id.item_favorite:
                FavoritTv favTv = filterFavTvById(getIntent().getIntExtra("ID", 0));

                if (favTv != null) {
                    backgroundThreadRealm.executeTransaction(transactionRealm -> {
                        favTv.deleteFromRealm();
                    });
                    item.setIcon(R.drawable.ic_favorite_unchecked);
                } else {
                    FavoritTv tv = new FavoritTv();
                    String title = getIntent().getStringExtra("TITLE");
                    String posterPath = getIntent().getStringExtra("POSTER_PATH");
                    int id = getIntent().getIntExtra("ID", 0);
                    tv.setId(id);
                    tv.setTitle(title);
                    tv.setPosterPath(posterPath);

                    backgroundThreadRealm.executeTransaction (transactionRealm -> transactionRealm.insert(tv));
                    Log.d("Favorite Movie", tv.getTitle());
                    item.setIcon(R.drawable.ic_favorite_checked);
                }
                return true;

            case R.id.item_language_setting:
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private FavoritTv filterFavTvById(int id) {
        RealmQuery<FavoritTv> query = backgroundThreadRealm.where(FavoritTv.class);
        query.equalTo("id", getIntent().getIntExtra("ID", 0));

        FavoritTv favTv = query.findFirst();

        return favTv;
    }

    private void getRepositoryData(int id) {
        repository.getTvDetail(id, new OnTvDetailCallback() {
            @Override
            public void onSuccess(TvShow tvShow, String message) {
                onBindView(tvShow);
                // TODO: Hide error text
            }
            @Override
            public void onFailure(String message) {

            }
        });
        repository.getCasts(id, new OnCastCallback() {
            @Override
            public void onSuccess(List<Cast> castList, String message) {
                rvCast.setLayoutManager(new LinearLayoutManager(DetailActivity.this, RecyclerView.VERTICAL, false));
                rvCast.setAdapter(new CastAdapter(castList, DetailActivity.this));
            }

            @Override
            public void onFailure(String message) {
                errorDetail.setVisibility(View.VISIBLE);
            }
        });
    }

    private void onBindView(TvShow tvShow) {
        this.tvShow = tvShow;
        setActionBar(tvShow.getName());
        Glide.with(this)
                .load(tvShow.getBackdropPath(ImageSize.W500))
                .into(ivBackdrop);
        Glide.with(this)
                .load(tvShow.getPosterPath(ImageSize.W154))
                .into(ivPoster);

        tvName.setText(tvShow.getName());
        rbRate.setRating(tvShow.getVoteAverage() / 2);
        tvLastAirDate.setText(tvShow.getLastAirDate());
        tvFirstAirDate.setText(tvShow.getFirstAirDate());
        tvEpisode.setText(String.valueOf(tvShow.getNumberOfEpisode()));
        tvSeason.setText(String.valueOf(tvShow.getNumberOfSeaon()));
        tvOverview.setText(tvShow.getOverview());

        if (tvShow.getOverview().isEmpty()) {
            labelOverview.setVisibility(View.GONE);
        }
    }

    private void setActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}