package com.example.otptest

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var customOTPView: CustomOTPView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customOTPView = findViewById(R.id.customOTPView)
    }

    // Получение значения OTP
    private fun getOTP(): String {
        val otp = StringBuilder()

        for (editText in customOTPView.editTextList) {
            otp.append(editText.text.toString())
        }

        return otp.toString()
    }

}