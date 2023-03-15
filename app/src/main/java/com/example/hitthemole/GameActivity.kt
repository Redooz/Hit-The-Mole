package com.example.hitthemole

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TableRow
import com.example.hitthemole.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private var time: Long = 0
    private var level: Int = 0
    private var points: Int = 0
    private var rows: Int = 3
    private var columns: Int = 3

    private var posMole: Int = 0
    private var buttons: MutableList<Button> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)

            binding = ActivityGameBinding.inflate(layoutInflater)

            setContentView(binding.root)
            drawInfo()
            loadGameComponents()

            Thread {
                drawMole()
            }.start()
        } catch (ex: java.lang.Exception) {
            error(ex)
        }

    }

    fun drawInfo() {
        binding.txtViewScore.text = "Score: ${this.points}"
        binding.txtViewDificultad.text = "Level: ${this.level}"
        binding.txtViewTime.text = "Time: ${this.time}"
    }

    fun drawMole() {
        for (time in 0..100) {
            this.posMole = generatePosMole()
            runOnUiThread {
                clearButtons()
                buttons.get(this.posMole).text = "Here is the mole!"
            }
            Thread.sleep(1000)
        }
    }

    fun clearButtons() {
        for (button in this.buttons) {
            button.text = button.id.toString()
        }
    }

    fun loadGameComponents() {
        var tableRow: TableRow? = null
        for (item in 0 until (this.rows * this.columns)) {
            if (item % 3 == 0) {
                tableRow = TableRow(this)
                val layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL
                tableRow.layoutParams = layoutParams
                binding.gameLayout.addView(tableRow)
            }
            var button = Button(this)
            button.text = "$item"
            button.id = item

            button.setOnClickListener {
                if (posMole == it.id) {
                    this.points += 100
                } else {
                    this.points -= 100
                }
                drawInfo()
            }

            tableRow?.addView(button)
            buttons.add(button)
        }
    }

    fun generatePosMole(): Int {
        var to = 0
        var from = this.columns * this.rows

        return Random.nextInt(to, from)
    }
}