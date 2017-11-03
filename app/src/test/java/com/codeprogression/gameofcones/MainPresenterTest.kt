package com.codeprogression.gameofcones

import android.content.Context
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainPresenterTest {
    val context = mock<Context>()
    val dataStore = mock<DataStore>()
    val fakeIceCream = IceCream("Vanilla", "http://www.icecream.com", 120)

    @get:Rule
    val rule = ImmediateSchedulerRule()

    @Before
    fun setUp() {
    }

    @Test
    fun test_OnCreate_success() {
        whenever(dataStore.fetchCone()).thenReturn(Single.just(fakeIceCream))

        val presenter = MainPresenter(context, dataStore)
        presenter.loadCone().test()
                .assertNoErrors()
                .assertComplete()
                .assertValueAt(0) { it is NetworkingViewState.Loading }
                .assertValueAt(1) {
                    it is NetworkingViewState.Success<*>
                            && it.item == IceCreamViewModel(context, fakeIceCream)
                }
    }

    @Test
    fun test_OnCreate_error() {
        val errorMessage = "There was an error"
        whenever(dataStore.fetchCone()).thenReturn(Single.error(Throwable(errorMessage)))

        val presenter = MainPresenter(context, dataStore)

        presenter.loadCone().test()
                .assertNoErrors()
                .assertComplete()
                .assertValueAt(0) { it is NetworkingViewState.Loading }
                .assertValueAt(1) {
                    it is NetworkingViewState.Error
                            && it.errorMessage == errorMessage
                }
    }
}