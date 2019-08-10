package com.asif.urbandictionary.network;

import android.content.Context;
import android.util.Log;

import com.asif.urbandictionary.module.DictResponse;
import com.asif.urbandictionary.module.JSONResponse;
import com.asif.urbandictionary.observer.RepositoryObserver;
import com.asif.urbandictionary.observer.Subject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class HTTPRequestManager implements Subject {
    private static final String TAG = HTTPRequestManager.class.getSimpleName();

    private List<DictResponse> list;
    private ArrayList<RepositoryObserver> mObservers;
    static Context context;
    private static HTTPRequestManager httpRequestManager;

    private HTTPRequestManager(){
        mObservers = new ArrayList<>();
        list = new ArrayList<DictResponse>();
    }

    /**
     * Singleton implementation of the class
     * @param contextt
     * @return HTTPRequestManager object
     */
    public static HTTPRequestManager getHTTPRequestManager(Context contextt){
        context=contextt;
        if(httpRequestManager == null){
            httpRequestManager = new HTTPRequestManager();
        }

        return httpRequestManager;
    }

    /**
     * Calling urban dictionary API to get word defination
     * @param word
     */
    public void getWordDefinitionAPI(String word) {
        APIService apiService = ServiceFactory.createRetrofitService(APIService.class, context);
        Observable<JSONResponse> observable = apiService.getWordDefinition("define?term="+word);
        observable.subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .map(new Function<JSONResponse, JSONResponse>() {
                    @Override
                    public JSONResponse apply(JSONResponse dictResponse) throws Exception {
                        return dictResponse;
                    }
                }).subscribe(new Observer<JSONResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(JSONResponse response) {
//                    if (response!=null && response.list != null) {
                        setUserData(response.list);
//                    }

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void registerObserver(RepositoryObserver repositoryObserver) {
        if(!mObservers.contains(repositoryObserver)) {
            mObservers.add(repositoryObserver);
        }
    }

    @Override
    public void removeObserver(RepositoryObserver repositoryObserver) {
        if(mObservers.contains(repositoryObserver)) {
            mObservers.remove(repositoryObserver);
        }
    }

    @Override
    public void notifyObservers() {
        for (RepositoryObserver observer: mObservers) {
            observer.onUserDataChanged(list);
        }
    }

    /**
     *
     * @param list observer callback function for update UI
     */
    public void setUserData(List<DictResponse> list) {
        this.list = list;
        notifyObservers();
    }
}
