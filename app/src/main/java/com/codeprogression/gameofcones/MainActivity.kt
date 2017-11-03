package com.codeprogression.gameofcones

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.main_activity.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        presenter = MainPresenter(this, FakeDataStore())
        presenter.loadCone()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { state -> networkingViewState = state }
    }

    private var networkingViewState: NetworkingViewState
            by Delegates.observable<NetworkingViewState>(NetworkingViewState.Init()) { _, _, newValue ->
                when (newValue) {
                    is NetworkingViewState.Loading -> {
                        titleView.text = getString(R.string.loading)
                        caloriesView.text = null
                        iconView.setImageURI(null)
                    }
                    is NetworkingViewState.Success<*> -> {
                        val item = newValue.item as? IceCreamViewModel
                        titleView.text = item?.title()
                        iconView.setImageURI(Uri.parse(item?.iconUrl()))
                        caloriesView.text = item?.calorieCount()
                    }
                    is NetworkingViewState.Error -> {
                        titleView.text = getString(R.string.error)
                        caloriesView.text = newValue.errorMessage
                        iconView.setImageURI(null)
                    }
                }
            }
}
