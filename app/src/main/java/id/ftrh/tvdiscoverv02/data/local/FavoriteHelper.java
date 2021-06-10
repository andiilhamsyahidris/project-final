package id.ftrh.tvdiscoverv02.data.local;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;

public class FavoriteHelper {
    private static Realm backgroundThreadRealm;
    private static FavoriteHelper favHelper;

    public FavoriteHelper(Realm realm) {
        backgroundThreadRealm = realm;
    }

    public static FavoriteHelper getInstance(Realm realm) {

        if (favHelper == null) {
            favHelper = new FavoriteHelper(realm);
        }

        return favHelper;
    }

    public FavoritTv findTvById(int id) {
        RealmQuery<FavoritTv> query = backgroundThreadRealm.where(FavoritTv.class);
        query.equalTo("id", id);
        FavoritTv result = query.findFirst();

        return result;
    }

    public void insertTv(String title, String posterPath, int id) {
        FavoritTv tv = new FavoritTv();
        tv.setPosterPath(posterPath);
        tv.setTitle(title);
        tv.setId(id);
        backgroundThreadRealm.executeTransaction (transactionRealm -> {
            transactionRealm.insert(tv);
        });
    }
}
