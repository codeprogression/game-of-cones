package com.codeprogression.gameofcones

import io.reactivex.Single

interface DataStore {
    fun fetchCone(): Single<IceCream>
}

class FakeDataStore : DataStore {
    override fun fetchCone(): Single<IceCream> {
        return Single.just(IceCream("Vanilla", "http://www.icecream.com", 120))
//        return Single.error(Throwable("We dropped your cone!"))
    }
}