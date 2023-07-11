package com.example.otptest

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var customOTPView: CustomOTPView
    private lateinit var textTimer: TextView
    private lateinit var textSendAgain: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customOTPView = findViewById(R.id.customOTPView)
        textTimer = findViewById(R.id.txt_timer)
        textSendAgain = findViewById(R.id.repeat_after)

        setListeners()

    }

    private fun setListeners() {

        object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val number = millisUntilFinished / 1000
                textTimer.text = "$number c"
            }

            override fun onFinish() {
                textTimer.text = "Отправить снова"
                textSendAgain.visibility = View.GONE
                textTimer.setTextColor(Color.parseColor("#1F3CAE"))
            }
        }.start()


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