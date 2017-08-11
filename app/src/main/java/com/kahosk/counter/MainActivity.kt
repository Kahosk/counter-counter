package com.kahosk.counter

import android.gesture.Gesture
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val start_life = 20
    var player1_life = 20
    var player2_life = 20
    var player1_rotation = 180F
    var player2_rotation = 0F

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
        setClickButtons()
        setOnHoldButtons()
        setTextTouch()
    }

    private fun updateProgress(p1: Int = player1_life,  p2: Int = player2_life) {
        var total_life = p1+p2
        battleBar.max = total_life
        battleBar.progress = p1
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
    private fun setRotation1() {
        buttonLayout1.rotation = player1_rotation
        player1.rotation = player1_rotation
    }
    private fun setRotation2() {
        buttonLayout2.rotation = player2_rotation
        player2.rotation = player2_rotation
    }

    private fun setClickButtons() {

        btn_refresh1.setOnClickListener {
            player1_life = start_life
            player1.text = player1_life.toString()
            updateProgress()
        }
        btn_refresh2.setOnClickListener {
            player2_life = start_life
            player2.text = player2_life.toString()
            updateProgress()
        }
        btn_rotate1.setOnClickListener {
            player1_rotation = if (player1_rotation == 0F) 180F else 0F
            setRotation1()
        }
        btn_rotate2.setOnClickListener {
            player2_rotation = if (player2_rotation == 0F) 180F else 0F
            setRotation2()
        }

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





