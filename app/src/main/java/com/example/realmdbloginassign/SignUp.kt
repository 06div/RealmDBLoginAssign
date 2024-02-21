package com.example.realmdbloginassign

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.realmdbloginassign.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.Success.setOnClickListener(){
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun signup(view: View){

        val email = binding.etUserSignup.text.toString()
        val pswd = binding.etPasswordSignup.text.toString()

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Enter Email", Toast.LENGTH_LONG).show()
            return
        }
        if(email.length < 10){
            Toast.makeText(this,"Email should contain at least 10 characters", Toast.LENGTH_LONG).show()
            return
        }

        if(TextUtils.isEmpty(pswd)){
            Toast.makeText(this,"Enter Password", Toast.LENGTH_LONG).show()
            return
        }

        if(!isValidPasswordFormat(pswd)){
            Toast.makeText(this,"Password must contain 7 Characters, 1 UpperCase Alphabet, 1 SpecialCharacter and Numeric values",
                Toast.LENGTH_LONG).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, pswd)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    binding.Success.visibility
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    Toast.makeText(
                        baseContext,
                        "Account Created.",
                        Toast.LENGTH_SHORT,
                    ).show()

                    binding.Success.visibility

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "${task.exception}",
                        Toast.LENGTH_LONG,
                    ).show()

                }
            }
    }
    fun isValidPasswordFormat(password: String): Boolean {
        val passwordREGEX = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=*])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$");
        return passwordREGEX.matcher(password).matches()
    }
}