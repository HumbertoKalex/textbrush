package com.example.textbrush.view

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.textbrush.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.undoButton).setOnClickListener {
            findViewById<TextBrushView>(R.id.textBrushView).undo()
        }
    }
}