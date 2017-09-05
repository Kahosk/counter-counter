package com.kahosk.counter

import android.content.res.ColorStateList
import android.gesture.Gesture
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.*
import android.widget.TextView


import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val start_life = 20
    var player1_life = 20
    var player2_life = 20
    var player1_rotation = 0F
    var player2_rotation = 180F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            player1_life = savedInstanceState.getInt("player1")
            player2_life = savedInstanceState.getInt("player2")
            player1_rotation = savedInstanceState.getFloat("player1_rotation")
            player2_rotation = savedInstanceState.getFloat("player2_rotation")
        }
        setInitials()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.restart -> restart(20)
            R.id.restart_commander -> restart(30)
            R.id.restart_multi-> restart(40)
            R.id.rotate1 -> setRotation1()
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.putInt("player1", player1_life)
        savedInstanceState?.putInt("player2", player2_life)
        savedInstanceState?.putFloat("player1_rotation", player1_rotation)
        savedInstanceState?.putFloat("player2_rotation", player2_rotation)
    }

    private fun setInitials() {
        player1.text = player1_life.toString()
        player2.text = player2_life.toString()
        setRotation1()
        setRotation2()
        updateProgress()
        setOnHoldButtons()
        setTextTouch()
    }

    private fun updateProgress(p1: Int = player1_life,  p2: Int = player2_life) {
        var total_life = p1+p2
        battleBar.max = total_life
        battleBar.progress = p1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            battleBar.progressTintList = ColorStateList.valueOf(Color.BLUE)
            battleBar.progressBackgroundTintList = ColorStateList.valueOf(Color.RED)
        }
    }


    private fun setOnHoldButtons() {
        btn_plus1.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v:View, event:MotionEvent):Boolean {
                var actual_life = (player1_life+1+((event.eventTime-event.downTime)/250).toInt())
                player1.text = actual_life.toString()
                if (event.action == MotionEvent.ACTION_UP){
                    player1_life = actual_life
                    updateProgress()
                    return true
                }
                updateProgress(p1 = actual_life)
                return false
            }
        })

        btn_plus2.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v:View, event:MotionEvent):Boolean {
                var actual_life = (player2_life+1+((event.eventTime-event.downTime)/250).toInt())
                player2.text = actual_life.toString()
                if (event.action == MotionEvent.ACTION_UP){
                    player2_life = actual_life
                    updateProgress()
                    return true
                }
                updateProgress(p2 = actual_life)
                return false
            }
        })

        btn_minus1.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v:View, event:MotionEvent):Boolean {
                var actual_life = (player1_life-1-((event.eventTime-event.downTime)/250).toInt())
                player1.text = actual_life.toString()
                if (event.action == MotionEvent.ACTION_UP){
                    player1_life = actual_life
                    updateProgress()
                    return true
                }
                updateProgress(p1 = actual_life)
                return false
            }
        })

        btn_minus2.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v:View, event:MotionEvent):Boolean {
                var actual_life = (player2_life-1-((event.eventTime-event.downTime)/250).toInt())
                player2.text = actual_life.toString()
                if (event.action == MotionEvent.ACTION_UP){
                    player2_life = actual_life
                    updateProgress()
                    return true
                }
                updateProgress(p2 = actual_life)
                return false
            }
        })


    }
    private fun setRotation1(): Boolean {
        player1_rotation = if (player1_rotation == 0F) 180F else 0F
        buttonLayout1.rotation = player1_rotation
        player1.rotation = player1_rotation
        return true
    }
    private fun setRotation2(): Boolean {
        player2_rotation = if (player2_rotation == 0F) 180F else 0F
        buttonLayout2.rotation = player2_rotation
        player2.rotation = player2_rotation
        return true
    }
    private fun restart(life: Int): Boolean {
        player1_life = life
        player1.text = player1_life.toString()
        player2_life = life
        player2.text = player2_life.toString()
        updateProgress()
        return true
    }

    private fun setTextTouch() {
        player1.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v:View, event:MotionEvent):Boolean {

                var actual_life = if(event.getAxisValue(MotionEvent.AXIS_Y) < player1.height/2) {
                    player1_life+1+((event.eventTime-event.downTime)/250).toInt() }
                else { player1_life-1-((event.eventTime-event.downTime)/250).toInt()}


                player1.text = actual_life.toString()
                if (event.action == MotionEvent.ACTION_UP){
                    player1_life = actual_life
                    updateProgress()
                    return true
                }
                updateProgress(p1 = actual_life)
                return false
            }
        })
        player2.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v:View, event:MotionEvent):Boolean {

                var actual_life = if(event.getAxisValue(MotionEvent.AXIS_Y) < player2.height/2) {
                    player2_life+1+((event.eventTime-event.downTime)/250).toInt() }
                else { player2_life-1-((event.eventTime-event.downTime)/250).toInt()}


                player2.text = actual_life.toString()
                if (event.action == MotionEvent.ACTION_UP){
                    player2_life = actual_life
                    updateProgress()
                    return true
                }
                updateProgress(p2 = actual_life)
                return false
            }
        })
    }


}





