package com.codeprogression.gameofcones

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), MainView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainPresenter(this, this, FakeDataStore())
    }

    override var networkingViewState: NetworkingViewState by Delegates
            .observable<NetworkingViewState>(NetworkingViewState.Init()) { property, oldValue, newValue ->
                when (newValue) {
                    is NetworkingViewState.Init -> {
                        // set placeholder image
                        findViewById<ImageView>(R.id.icon).setImageURI(null)
                    }
                    is NetworkingViewState.Loading -> {
                        findViewById<TextView>(R.id.title).setText(getString(R.string.loading))
                        findViewById<TextView>(R.id.calories).setText(null)
                        // set progress image
                        findViewById<ImageView>(R.id.icon).setImageURI(null)
                    }
                    is NetworkingViewState.Success<*> -> {
                        val item = newValue.item as? IceCreamViewModel
                        findViewById<TextView>(R.id.title).setText(item?.title())
                        findViewById<ImageView>(R.id.icon).setImageURI(Uri.parse(item?.iconUrl()))
                        findViewById<TextView>(R.id.calories).setText(item?.calorieCount())
                    }
                    is NetworkingViewState.Error -> {
                        // clear title/calories
                        findViewById<TextView>(R.id.title).setText(getString(R.string.error))
                        findViewById<TextView>(R.id.calories).setText(newValue.errorMessage)
                        // set error image
                        findViewById<ImageView>(R.id.icon).setImageURI(null)
                    }
                }
            }
}
