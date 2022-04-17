package com.example.dicesmulti

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val numSides = 6
    private lateinit var rollResultTxt: TextView
    private lateinit var gamePointA: TextView
    private lateinit var gamePointB: TextView

    private var roll: Int = 0
    private var roll2: Int = 0
    private var roll3: Int = 0

    private var scorePlayerA: Int = 0
    private var scorePlayerB: Int = 0
    private var getIdOfSpinner: Int = 0
    private var samePlayerCounter: Int = 0

    private var activePlayerA: Boolean = true
    private var activePlayerB: Boolean = false
    private var isGameEnded: Boolean = false
    private var freeze:Boolean = false
    private var numberOfDices: Int = 2

    private lateinit var dice1Img: ImageView
    private lateinit var dice2Img : ImageView
    private lateinit var dice3Img: ImageView

    private lateinit var spinner: Spinner
    private lateinit var button: Button


    @SuppressLint("CutPasteId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(localClassName, "onCreate")

        dice1Img = findViewById(R.id.dice1Img)
        dice2Img = findViewById(R.id.dice2Img)
        dice3Img = findViewById(R.id.dice3Img)

        rollResultTxt  = findViewById(R.id.ResultText)
        gamePointA = findViewById(R.id.tvGamePointA)
        gamePointB = findViewById(R.id.tvGamePointB)

        val imgToFreeze = findViewById<ImageView>(R.id.dice3Img)
        val choose = resources.getStringArray(R.array.throwsChoose)
        spinner = findViewById(R.id.spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, choose)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                getIdOfSpinner = position + 1
                Toast.makeText(
                    this@MainActivity,
                    "You selected ${adapterView?.getItemAtPosition(position).toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        button = findViewById(R.id.rollButton)

        imgToFreeze.setOnClickListener{
           freezeGame()
        }

        button.setOnClickListener {
            if(isGameEnded){
                resetAfterGameEnded()
                enableSpinner()
            }
            else {
                if (activePlayerA) {
                    when(numberOfDices){
                        0->{
                            roll3 = (1..numSides).random()

                            dice3Img.setImageResource(resolveDrawable(roll3))

                            rollResultTxt.text = "Rolled $roll3"
                            scorePlayerA += (roll3)

                        }
                        1->{
                            roll = (1..numSides).random()
                            roll2 = (1..numSides).random()

                            dice1Img.setImageResource(resolveDrawable(roll))
                            dice2Img.setImageResource(resolveDrawable(roll2))

                            rollResultTxt.text = "Rolled $roll and $roll2"
                            scorePlayerA += (roll + roll2)
                        }
                        2-> {
                            roll = (1..numSides).random()
                            roll2 = (1..numSides).random()
                            roll3 = (1..numSides).random()

                            dice1Img.setImageResource(resolveDrawable(roll))
                            dice2Img.setImageResource(resolveDrawable(roll2))
                            dice3Img.setImageResource(resolveDrawable(roll3))

                            rollResultTxt.text = "Rolled $roll and $roll3 and $roll2"
                            scorePlayerA += (roll + roll2 + roll3)
                        }
                        else->{
                            roll = (1..numSides).random()
                            roll2 = (1..numSides).random()
                            roll3 = (1..numSides).random()

                            dice1Img.setImageResource(resolveDrawable(roll))
                            dice2Img.setImageResource(resolveDrawable(roll2))
                            dice3Img.setImageResource(resolveDrawable(roll3))

                            rollResultTxt.text = "Rolled $roll and $roll3 and $roll2"
                            scorePlayerA += (roll + roll2 + roll3)
                        }
                    }

                    disableSpinner()

                    gamePointA.text = scorePlayerA.toString()
                    samePlayerCounter++
                    if (samePlayerCounter == getIdOfSpinner) {
                        activePlayerA = false
                        activePlayerB = true
                        samePlayerCounter = 0
                    }
                } else {
                    when(numberOfDices){
                        0->{
                            roll3 = (1..numSides).random()

                            dice3Img.setImageResource(resolveDrawable(roll3))

                            rollResultTxt.text = "Rolled $roll3"
                            scorePlayerB += (roll3)

                        }
                        1->{
                            roll = (1..numSides).random()
                            roll2 = (1..numSides).random()

                            dice1Img.setImageResource(resolveDrawable(roll))
                            dice2Img.setImageResource(resolveDrawable(roll2))

                            rollResultTxt.text = "Rolled $roll and $roll2"
                            scorePlayerB += (roll + roll2)
                        }
                        2-> {
                            roll = (1..numSides).random()
                            roll2 = (1..numSides).random()
                            roll3 = (1..numSides).random()

                            dice1Img.setImageResource(resolveDrawable(roll))
                            dice2Img.setImageResource(resolveDrawable(roll2))
                            dice3Img.setImageResource(resolveDrawable(roll3))

                            rollResultTxt.text = "Rolled $roll and $roll3 and $roll2"
                            scorePlayerB += (roll + roll2 + roll3)
                        }
                        else->{
                            roll = (1..numSides).random()
                            roll2 = (1..numSides).random()
                            roll3 = (1..numSides).random()

                            dice1Img.setImageResource(resolveDrawable(roll))
                            dice2Img.setImageResource(resolveDrawable(roll2))
                            dice3Img.setImageResource(resolveDrawable(roll3))

                            rollResultTxt.text = "Rolled $roll and $roll3 and $roll2"
                            scorePlayerB += (roll + roll2 + roll3)
                        }
                    }
                    gamePointB.text = scorePlayerB.toString()
                    samePlayerCounter++
                    checkGameResult()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id =  item.itemId
        if(id == R.id.itemPreference){
            goToActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToActivity(){
        val intent = Intent(this, SettingsActivity::class.java)
        launchSettingsActivity.launch(intent)
    }

    private fun disableSpinner() {
        spinner.isEnabled = false
        spinner.isVisible = false
    }

    @SuppressLint("SetTextI18n")
    private fun resetAfterGameEnded() {
        scorePlayerA = 0
        gamePointA.text = scorePlayerA.toString()
        scorePlayerB = 0
        gamePointB.text = scorePlayerB.toString()
        button.text = "Roll!"
        rollResultTxt.text = "Let's roll"
        isGameEnded = false
    }

    private fun freezeGame(){
        if(freeze) {
            freeze = false
            button.isEnabled = true
        }
        else {
            freeze = true
            button.isEnabled = false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkGameResult(){
        if (samePlayerCounter == getIdOfSpinner) {
            activePlayerA = true
            activePlayerB = false
            samePlayerCounter = 0
            when {
                scorePlayerB == scorePlayerA -> {
                    rollResultTxt.text = "Draw!"
                    button.text = "PLAY AGAIN"
                    isGameEnded = true
                }
                scorePlayerA > scorePlayerB -> {
                    rollResultTxt.text = "Player A won!"
                    button.text = "PLAY AGAIN"
                    isGameEnded = true
                }
                else -> {
                    rollResultTxt.text = "Player B won!"
                    button.text = "PLAY AGAIN"
                    isGameEnded = true
                }
            }
        }
    }

    private fun enableSpinner() {
        spinner.isEnabled = true
        spinner.isVisible = true
    }

    private fun setAlpha(){
        when(numberOfDices){
            0->{
                dice1Img.imageAlpha = 74
                dice2Img.imageAlpha = 74
                dice3Img.imageAlpha = 255

            }
            1->{
                dice1Img.imageAlpha = 255
                dice2Img.imageAlpha = 255
                dice3Img.imageAlpha = 74

            }
            2-> {
                dice1Img.imageAlpha = 255
                dice2Img.imageAlpha = 255
                dice3Img.imageAlpha = 255
            }
            else->{
                dice1Img.imageAlpha = 255
                dice2Img.imageAlpha = 255
                dice3Img.imageAlpha = 255
            }
        }
    }

    private val launchSettingsActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
        if (result.resultCode == RESULT_OK){
            if(scorePlayerA == 0) {
                numberOfDices = result.data!!.getIntExtra("numberOfDices", 2)
                setAlpha()

                Toast.makeText(
                    this@MainActivity,
                    "You are throwing " + ((numberOfDices) + 1).toString() +
                    if(numberOfDices == 0){
                        " dice"
                    }
                    else {
                        " dices"
                    },
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                Toast.makeText(
                    this@MainActivity,
                    "You can not change amount of dices during the game",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun resolveDrawable(value: Int) : Int {
        return when (value) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.dice_1
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("playerAScore", scorePlayerA)
        outState.putInt("playerBScore", scorePlayerB)
        outState.putInt("roll1", roll)
        outState.putInt("roll2", roll2)
        outState.putInt("roll3", roll3)
        outState.putInt("numberOfDices", numberOfDices)
        outState.putInt("scorePlayerCounter", samePlayerCounter)
        outState.putBoolean("isGameEnded", isGameEnded)
        outState.putBoolean("isFrozen", freeze)
        outState.putBoolean("isPlayerA", activePlayerA)
        outState.putBoolean("isPlayerB", activePlayerB)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val playerAScore = savedInstanceState.getInt("playerAScore")
        val playerBScore = savedInstanceState.getInt("playerBScore")
        val savedRoll1 = savedInstanceState.getInt("roll1" )
        val savedRoll2 = savedInstanceState.getInt("roll2")
        val savedRoll3 = savedInstanceState.getInt("roll3")
        val numOfDices = savedInstanceState.getInt("numberOfDices")
        val scorePlayerCount = savedInstanceState.getInt("scorePlayerCounter")
        val isGameEnd = savedInstanceState.getBoolean("isGameEnded")
        val isGameFrozen = savedInstanceState.getBoolean("isFrozen")
        val isPlayerA = savedInstanceState.getBoolean("isPlayerA")
        val isPlayerB = savedInstanceState.getBoolean("isPlayerB")
        scorePlayerA = playerAScore
        scorePlayerB = playerBScore
        roll = savedRoll1
        roll2 = savedRoll2
        roll3 = savedRoll3
        numberOfDices = numOfDices
        gamePointA.text = scorePlayerA.toString()
        gamePointB.text = scorePlayerB.toString()
        samePlayerCounter = scorePlayerCount
        isGameEnded = isGameEnd
        freeze = isGameFrozen
        activePlayerA = isPlayerA
        activePlayerB = isPlayerB
        setAlpha()
        resetAfterGameEnded()
        when(numberOfDices){
            0->{
                rollResultTxt.text = "Rolled $roll3"
                dice3Img.setImageResource(resolveDrawable(roll3))
            }
            1->{
                rollResultTxt.text = "Rolled $roll and $roll2"
                dice1Img.setImageResource(resolveDrawable(roll))
                dice2Img.setImageResource(resolveDrawable(roll2))
            }
            2->{
                rollResultTxt.text = "Rolled $roll and $roll3 and $roll2"
                dice1Img.setImageResource(resolveDrawable(roll))
                dice2Img.setImageResource(resolveDrawable(roll2))
                dice3Img.setImageResource(resolveDrawable(roll3))
            }
            else->{
                rollResultTxt.text = "Rolled $roll and $roll3 and $roll2"
                dice1Img.setImageResource(resolveDrawable(roll))
                dice2Img.setImageResource(resolveDrawable(roll2))
                dice3Img.setImageResource(resolveDrawable(roll3))
            }
        }
    }
}

