package com.frozensparks.wifiadbclient

import android.content.Context
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.format.Formatter
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val check_usb_textview: TextView = findViewById(R.id.check_usb_textview)

        val check_usb_imageview: ImageView = findViewById(R.id.check_usb_imageview)


        val check_wifi_textview: TextView = findViewById(R.id.check_wifi_textview)

        val check_wifi_imageview: ImageView = findViewById(R.id.check_wifi_imageview)


        val relativeLayout: RelativeLayout = findViewById(R.id.relativeLayout)



        val ip_address_textView: TextView = findViewById(R.id.ip_address_textView)


        val haupload = Handler()
        haupload.postDelayed(object : Runnable {
            override fun run() {

                if(isConnected()||isConnectedViaWifi()){
                    relativeLayout.setBackgroundColor(Color.YELLOW)
                    if(isConnected()&&isConnectedViaWifi()){
                        relativeLayout.setBackgroundColor(Color.GREEN)
                    }
                    }
                else{
                    relativeLayout.setBackgroundColor(Color.RED)
                }

                if(isConnected()){
                    check_usb_textview.text = "Connected to computer"
                    check_usb_imageview.visibility = View.VISIBLE

                }
                else{
                    check_usb_textview.text = "NOT connected to computer"
                    check_usb_imageview.visibility = View.INVISIBLE

                }
                if(isConnectedViaWifi()){
                    check_wifi_textview.text = "Connected to WiFi"
                    check_wifi_imageview.visibility = View.VISIBLE

                    val wm =  applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                    val ip = Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
                    ip_address_textView.text = "adb connect "+ip + ":5555"
                }
                else{
                    check_wifi_textview.text = "NOT connected to WiFi"
                    check_wifi_imageview.visibility = View.INVISIBLE
                    ip_address_textView.text = ""


                }
                haupload.postDelayed(this, 10)

            }
        }, 10)





    }

    fun isConnected(): Boolean {
        intent = registerReceiver(null, IntentFilter("android.hardware.usb.action.USB_STATE"))
        return intent.extras.getBoolean("connected")
    }

    private fun isConnectedViaWifi(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return mWifi.isConnected
    }
}
