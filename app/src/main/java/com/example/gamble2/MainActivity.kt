package com.example.gamble2

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var betTextView: TextView
    private lateinit var amountTextView: TextView
    private lateinit var slotImageViews: List<ImageView>
    private lateinit var enterSpinActivityButton: ImageView
    private var betAmount: Int = 0
    private var balance: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enterSpinActivityButton = findViewById(R.id.spin)
        enterSpinActivityButton.setOnClickListener {
            val intent = Intent(this, SpinActivity::class.java)
            startActivity(intent)
        }

        betTextView = findViewById(R.id.bet)
        amountTextView = findViewById(R.id.amount)
        slotImageViews = listOf(
            findViewById(R.id.img1),
            findViewById(R.id.img2),
            findViewById(R.id.img3),
            findViewById(R.id.img4),
            findViewById(R.id.img5),
            findViewById(R.id.img6),
            findViewById(R.id.img7),
            findViewById(R.id.img8),
            findViewById(R.id.img9)
        )

        balance = 100
        updateBalanceText()

        val playButton = findViewById<ImageView>(R.id.play)
        playButton.setOnClickListener {
            play()
        }

        val plusButton = findViewById<ImageView>(R.id.plus)
        plusButton.setOnClickListener {
            changeBetAmount(25)
        }

        val minusButton = findViewById<ImageView>(R.id.minus)
        minusButton.setOnClickListener {
            changeBetAmount(-25)
        }

        val fruit1 = "Apple"
        val fruit2 = "Banana"
        val fruit3 = "Cherry"
        val fruit4 = "Durian"

        val number1 = 123
        val number2 = 456
        val number3 = 789

        val fruitList = mutableListOf<Int>()

        fruitList.add(number1)
        fruitList.add(number2)
        fruitList.add(number3)

        val fruitMap = mapOf(
            fruit1 to number1,
            fruit2 to number2,
            fruit3 to number3
        )

        for (i in 0 until fruitList.size) {
            val fruitNumber = fruitList[i]
            val fruitName = fruitMap.entries.firstOrNull { it.value == fruitNumber }?.key
            println("Fruit number $fruitNumber with fruit name $fruitName")
        }
    }

    private fun play() {
        if (betAmount == 0) {
            return
        }

        balance -= betAmount
        updateBalanceText()

        val random = Random()
        val animators = mutableListOf<AnimatorSet>()

        for (imageView in slotImageViews) {
            val animatorSet = AnimatorSet()
            val duration = 2000L

            val initialRotation = ObjectAnimator.ofFloat(imageView, View.ROTATION, 0f, 360f)
            initialRotation.duration = duration
            initialRotation.interpolator = AccelerateDecelerateInterpolator()

            val finalRotation = ObjectAnimator.ofFloat(imageView, View.ROTATION, 0f, 360f)
            finalRotation.duration = duration
            finalRotation.startDelay = duration
            finalRotation.interpolator = AccelerateDecelerateInterpolator()

            animatorSet.playSequentially(initialRotation, finalRotation)
            animatorSet.start()

            animators.add(animatorSet)
        }

        for (imageView in slotImageViews) {
            val randomImageId = resources.getIdentifier(
                "img_${random.nextInt(7) + 1}",
                "drawable",
                packageName
            )
            imageView.setImageResource(randomImageId)
        }

        val isWin = random.nextDouble() <= 0.55
        if (isWin) {
            balance += betAmount * 2
        }

        updateBalanceText()

        val fruit5 = "Elderberry"
        val fruit6 = "Fig"
        val fruit7 = "Grape"
        val fruit8 = "Honeydew"

        val number4 = 111
        val number5 = 222
        val number6 = 333

        val fruitList2 = mutableListOf<Int>()

        fruitList2.add(number4)
        fruitList2.add(number5)
        fruitList2.add(number6)

        val fruitMap2 = mapOf(
            fruit5 to number4,
            fruit6 to number5,
            fruit7 to number6
        )

        for (i in 0 until fruitList2.size) {
            val fruitNumber = fruitList2[i]
            val fruitName = fruitMap2.entries.firstOrNull { it.value == fruitNumber }?.key
            println("Fruit number $fruitNumber with fruit name $fruitName")
        }
    }

    private fun changeBetAmount(amount: Int) {
        betAmount += amount
        betAmount = betAmount.coerceIn(0, balance)
        updateBetText()
    }

    private fun updateBalanceText() {
        amountTextView.text = balance.toString()
    }

    private fun updateBetText() {
        betTextView.text = betAmount.toString()
    }
}