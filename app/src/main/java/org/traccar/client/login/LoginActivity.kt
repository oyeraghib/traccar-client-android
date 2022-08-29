package org.traccar.client.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.traccar.client.MainActivity
import org.traccar.client.MainApplication
import org.traccar.client.databinding.ActivityLoginBinding

const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {

    //Initialise binding
    private lateinit var binding: ActivityLoginBinding

    //Global variable for email and password
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //When pressed Login Button
        binding.btnLogin.setOnClickListener {
            loginToAccount()
        }
    }

    //Logs the user into the account
    private fun loginToAccount() {
        //Checks for credentials
        if (!verifyCredentials()) {
            Log.d(TAG, "Invalid data entered")
            Toast.makeText(this, "Check your email/password", Toast.LENGTH_SHORT).show()
        } else {
            //If all the credentials passes
            lifecycleScope.launch(Dispatchers.IO) {
                email = binding.etEmail.text.toString()

                //Create instance of {@userPreference} to store value in data store
                val userPreferences = (application as? MainApplication)!!.dataStore
                userPreferences.saveAuth(email)

                moveToMainActivity()
            }
        }
    }

    /** Function for validating email and password */
    private fun verifyCredentials(): Boolean {
        var valid = true

        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString()

        //Validating email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Invalid email"
            valid = false

        } else if (TextUtils.isEmpty(email)) {
            binding.etEmail.error = "Email can't be empty"
            valid = false

        } else {
            binding.etEmail.error = null
        }


        //Validating Password
        if (TextUtils.isEmpty(password)) {
            binding.etPassword.error = "Password can't be empty"
            valid = false

        } else {
            binding.etPassword.error = null
        }

        return valid
    }

    //Move to main activity
    private fun moveToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}