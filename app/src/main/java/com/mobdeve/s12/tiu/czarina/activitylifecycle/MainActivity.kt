package com.mobdeve.s12.tiu.czarina.activitylifecycle

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
import com.mobdeve.s12.tiu.czarina.activitylifecycle.databinding.ActivityFormBinding
import com.mobdeve.s12.tiu.czarina.activitylifecycle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){


    val TAG = "MAINACTIVITY"

    private lateinit var cameraObserver: MyCameraObserver
    lateinit var binding:ActivityMainBinding

    //init and oncreate are only called once at the start of application
    init{
        Log.i(TAG, "init")
    }

    var startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result: ActivityResult ->

        if(result.resultCode == Activity.RESULT_OK){
            //process data from form activity
            var bundle = result.data!!.extras
                Log.i("RESULT", bundle!!.getString("name","NO NAME PASSES"))
            Log.i("RESULT", bundle!!.getString("email","NO EMAIL PASSES"))

        }
        else{
            Snackbar.make(binding.root,
            "UNSUCCESSFUL", Snackbar.LENGTH_SHORT).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i(TAG, "onCreate")


        //this is the same as applicationContext
        cameraObserver = MyCameraObserver(this){
            Log.i(TAG, "Creating Camera Instance")
        }

        binding.buttonform.setOnClickListener{
            var gotoFormActivity = Intent(applicationContext, FormActivity::class.java)
            //startActivity(gotoFormActivity)
            startForResult.launch(gotoFormActivity)
        }

    }

    /*
        When the activity loads for the first time it calls on
        onCreate, onStart, and onResume

        When its minimize it calls on
        onPause, and onStop

        Then when its open again it calls on
        onStart and onResume
    */


    /*
        onStart is when your activity appears to the user
        this is more on UI the screen just loads
     */
    override fun onStart(){
        super.onStart()
        Log.i(TAG, "onStart")
        cameraObserver.onStart()
    }
    /*
        where your activity begins
        This is more on the process side so like okay
        the activity has loaded you can now play music
        or animation or something
     */
    override fun onResume(){
        super.onResume()
        Log.i(TAG, "onResume")
    }

    /*
        ex:before the UI disappears you save it
        pause the content
        game saved, music paused, etc
     */
    override fun onPause(){
        super.onPause()
        Log.i(TAG, "onPause")
        cameraObserver.onPause()

    }

    /*
        This is when screen disappers or is brought to the background
    */
    override fun onStop(){
        super.onStop()
        Log.i(TAG, "onStop")
        cameraObserver.onStop()
    }

    /*
        When your application closes
        ex:you want to make sure database connection is closed
        anything that needs to be closed when the app stops
        close background process and services once application ends
        ex: gps will stop working as it consumes alot of battery
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }


    /*
        ABOVE is the OLD METHOD of doing this
        BELOW is the NEW METHOD
     */

    /*
        when the MyCameraObserver is instantiated it will execute the callback
        thats why you're passing creating camera instance, passing a function
     */
    internal class MyCameraObserver(private val context:Context,
                                    private val callback: (String)->Unit)
        : DefaultLifecycleObserver{ //make camera more lifecycle aware, can reuse onStart, onPause, etc

        private var INNERTAG = "OBSERVER"

        fun onStart() {
            Log.i(INNERTAG, "onStart: Turn on Camera")
        }

        fun onPause() {
            Log.i(INNERTAG, "onPause: Pause Camera Usage")
        }

        fun onStop() {
            Log.i(INNERTAG, "onStop: Stop Camera Usage")
        }
    }

}