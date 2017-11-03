package com.codeprogression.gameofcones

import android.content.Context
import io.reactivex.schedulers.Schedulers

class MainPresenter(
        private val context: Context,
        private val view: MainView,
        private val dataStore: DataStore
) {
    fun onCreate() {
        view.networkingViewState = NetworkingViewState.Loading()
        dataStore.fetchCone()
                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()) // <-- not unit testable without robolectric
                .subscribe(
                        { iceCream ->
                            view.networkingViewState = NetworkingViewState.Success(IceCreamViewModel(context, iceCream))
                        },
                        { error ->
                            view.networkingViewState = NetworkingViewState.Error(error.message)
                        }
                )
    }
}