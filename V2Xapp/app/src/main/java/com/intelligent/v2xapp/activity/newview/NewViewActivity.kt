package com.intelligent.v2xapp.activity.newview

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.intelligent.v2xapp.R
import kotlinx.android.synthetic.main.activity_new_view.*

class NewViewActivity : AppCompatActivity() {
    lateinit var sensorManager: SensorManager
    lateinit var listenor: SensorEventListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_view)
        initView()


    }

    private fun initView() {
//return type Unit

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        listenor = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {//return type kotlin.Unit

            }

            override fun onSensorChanged(event: SensorEvent?) {//return type kotlin.Unit
                chaos_view.`val`=   event!!.values[0]
            }


        }
        sensorManager.registerListener(listenor, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(listenor)
    }
}
