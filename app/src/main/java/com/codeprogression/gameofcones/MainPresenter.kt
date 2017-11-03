package com.codeprogression.gameofcones

import android.content.Context
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MainPresenter(
        private val context: Context,
        private val dataStore: DataStore
) {
    fun loadCone(): Observable<NetworkingViewState> {
        return Observable.concat(
                Observable.just(NetworkingViewState.Loading()),
                dataStore.fetchCone()
                        .subscribeOn(Schedulers.io())
                        .map { iceCream ->
                            NetworkingViewState.Success(IceCreamViewModel(context, iceCream)) as NetworkingViewState
                        }
                        .onErrorReturn {
                            NetworkingViewState.Error(it.message)
                        }.toObservable())
    }
}