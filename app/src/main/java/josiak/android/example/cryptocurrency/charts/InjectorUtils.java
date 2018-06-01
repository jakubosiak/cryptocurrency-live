package josiak.android.example.cryptocurrency.charts;

import android.content.Context;

import josiak.android.example.cryptocurrency.charts.api.CoinMarketCapApi;
import josiak.android.example.cryptocurrency.charts.api.CryptoCompareApi;
import josiak.android.example.cryptocurrency.charts.api.HttpClient;
import josiak.android.example.cryptocurrency.charts.database.CryptoLocalCache;
import josiak.android.example.cryptocurrency.charts.database.CryptocurrencyChartsDatabase;
import josiak.android.example.cryptocurrency.charts.ui.MainListViewModelFactory;
import okhttp3.OkHttpClient;

/**
 * Created by Jakub on 2018-05-25.
 */

public class InjectorUtils {
    public static OkHttpClient provideHttpClient(){
        return HttpClient.getInstance();
    }

    private static AppExecutors provideAppExecutors(){
        return AppExecutors.getInstance();
    }

    private static CryptoLocalCache provideCryptoLocalCache(Context context){
        CryptocurrencyChartsDatabase database = CryptocurrencyChartsDatabase.getInstance(context);
        return new CryptoLocalCache(database.cryptoDao(), provideAppExecutors());
    }

    private static CryptoRepository provideCryptoRepository(Context context){
        return new CryptoRepository(
                CoinMarketCapApi.create(),
                CryptoCompareApi.create(),
                provideCryptoLocalCache(context),
                context);
    }

    public static MainListViewModelFactory provideMainListViewModelFactory(Context context){
        return new MainListViewModelFactory(provideCryptoRepository(context));
    }
}
