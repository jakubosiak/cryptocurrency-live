package josiak.android.example.cryptocurrency.charts;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;
import android.util.Log;

import josiak.android.example.cryptocurrency.charts.api.CoinMarketCapApi.CoinMarketCap;
import josiak.android.example.cryptocurrency.charts.api.CryptoCompareApi.CryptoCompare;
import josiak.android.example.cryptocurrency.charts.api.PagingBoundaryCallback;
import josiak.android.example.cryptocurrency.charts.data.Crypto;
import josiak.android.example.cryptocurrency.charts.database.CryptoLocalCache;
import josiak.android.example.cryptocurrency.charts.database.CryptoResultFromDatabase;

/**
 * Created by Jakub on 2018-05-25.
 */

public class CryptoRepository {
    private CoinMarketCap coinMarketCapApi;
    private CryptoCompare cryptoCompareApi;
    private CryptoLocalCache cache;
    private Context contextForResources;
    private PagingBoundaryCallback pagingBoundaryCallback;

    private static final int PAGE_SIZE = 50;

    public CryptoRepository(
            CoinMarketCap coinMarketCapApi,
            CryptoCompare cryptoCompareApi,
            CryptoLocalCache cache,
            Context contextForResources) {
        this.coinMarketCapApi = coinMarketCapApi;
        this.cryptoCompareApi = cryptoCompareApi;
        this.cache = cache;
        this.contextForResources = contextForResources;
    }

    public CryptoResultFromDatabase requestCoins() {
        Log.v("RefreshingInRepository", "true");
        pagingBoundaryCallback =
                new PagingBoundaryCallback(coinMarketCapApi, cryptoCompareApi, cache, contextForResources);
        //MutableLiveData<Boolean> fetchingData = pagingBoundaryCallback._fetchingData;

        DataSource.Factory<Integer, Crypto> dataSourceFactory = cache.queryCryptosByRank();

        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build();

        LiveData<PagedList<Crypto>> pagedListData =
                new LivePagedListBuilder<Integer, Crypto>(dataSourceFactory, pagedListConfig)
                        .setBoundaryCallback(pagingBoundaryCallback)
                        .build();

        return new CryptoResultFromDatabase(pagedListData);
    }

    public LiveData<Boolean> refresh() {
        return pagingBoundaryCallback.refresh();
    }
}
