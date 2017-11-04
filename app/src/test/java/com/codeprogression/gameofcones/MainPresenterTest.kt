package com.codeprogression.gameofcones

import android.content.Context
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class MainPresenterTest {
    val context = mock<Context>()
    val view = mock<MainView>()
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

        val presenter = MainPresenter(context, view, dataStore)
        presenter.onCreate()

        verify(view).networkingViewState = isA<NetworkingViewState.Loading>()
        verify(view).networkingViewState = isA<NetworkingViewState.Success<IceCream>>()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun test_OnCreate_error() {
        val errorMessage = "There was an error"
        whenever(dataStore.fetchCone()).thenReturn(Single.error(Throwable(errorMessage)))

        val presenter = MainPresenter(context, view, dataStore)
        presenter.onCreate()

        verify(view).networkingViewState = isA<NetworkingViewState.Loading>()
        verify(view).networkingViewState = isA<NetworkingViewState.Error>()
        verifyNoMoreInteractions(view)
    }
}

class ImmediateSchedulerRule : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                val trampoline = Schedulers.trampoline()
                RxJavaPlugins.setIoSchedulerHandler { trampoline }
                RxJavaPlugins.setComputationSchedulerHandler { trampoline }
                RxJavaPlugins.setNewThreadSchedulerHandler { trampoline }
                RxAndroidPlugins.setMainThreadSchedulerHandler{ trampoline }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}