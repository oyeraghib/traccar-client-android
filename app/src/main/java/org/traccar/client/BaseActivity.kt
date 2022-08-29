package org.traccar.client

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import org.traccar.client.login.LoginActivity

class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userPreferences = (application as? MainApplication)!!.dataStore

        userPreferences.authToken.asLiveData().observe(this, Observer {
            if (it == null) {
                moveToLoginScreen()
            } else {
                moveToMainScreen()
            }
        })
    }

    private fun moveToMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun moveToLoginScreen() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}