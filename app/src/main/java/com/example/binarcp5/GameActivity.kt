package com.example.binarcp5

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private lateinit var handRock1: ImageView
    private lateinit var handPaper1: ImageView
    private lateinit var handScissor1: ImageView
    private lateinit var handRock2: ImageView
    private lateinit var handPaper2: ImageView
    private lateinit var handScissor2: ImageView
    private lateinit var viewResult: ImageView
    private lateinit var textRefresh: TextView
    private lateinit var logPlayer: String

    private lateinit var closeImage : ImageView

    private val player1 = Player("Player 1")
    private val player2 = Player("Player 2")
    private val controller = Controller()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // plus point karna hapus <ImageView> di deklarasinya, kemaren di contoh masih ada.
        // bisa maksimalin hint dari IDE nya
        handRock1 = findViewById(R.id.iv_rock_p1)
        handPaper1 = findViewById(R.id.iv_paper_p1)
        handScissor1 = findViewById(R.id.iv_scissor_p1)
        handRock2 = findViewById(R.id.iv_rock_p2)
        handPaper2 = findViewById(R.id.iv_paper_p2)
        handScissor2 = findViewById(R.id.iv_scissor_p2)
        viewResult = findViewById(R.id.iv_result)
        textRefresh = findViewById(R.id.tv_message)
        val ivRefresh = findViewById<ImageView>(R.id.iv_refresh)

        closeImage = findViewById(R.id.iv_back)

        setOnClick(handRock1, 1)
        setOnClick(handPaper1, 2)
        setOnClick(handScissor1, 3)

        ivRefresh.setOnClickListener {
            clearView()
        }

        closeImage.setOnClickListener {
            // untuk mem finish activity yang sedang aktif
            finish()

            // alternatif bisa pakai, untuk mensimulasikan tombol back
            onBackPressed()
        }
    }

    private fun setOnClick(view: ImageView, idView: Int) {
        // plus point buat ini
        // dengan di bikin central di 1 method, codingan lebih rapi dan mudah di baca
        view.setOnClickListener {

            isHandEnabled(false)
            setPlayer1(view, idView)
            setPlayer2()
            startGame()
        }
    }

    private fun isHandEnabled(enable: Boolean) {
        // plus point buat disable viewnya, tp lebih bagus kalau namanya diganti setHandEnabled()
        handRock1.isEnabled = enable
        handPaper1.isEnabled = enable
        handScissor1.isEnabled = enable
    }

    private fun setPlayer1(view: ImageView, handId: Int) {

        player1.handId = handId
        setHand(view, handId, true)
        logPlayer = player1.showLogPlayer()
        logD(logPlayer)
    }

    private fun setPlayer2() {

        player2.handId = (1..3).random()
        when (player2.handId) {
            1 -> setHand(handRock2, 1, true)
            2 -> setHand(handPaper2, 2, true)
            3 -> setHand(handScissor2, 3, true)
        }
        logPlayer = player2.showLogPlayer()
        logD(logPlayer)
    }

    private fun setHand(view: ImageView, handId: Int, isSelected: Boolean) {

        view.setImageResource(setHandImage(handId))
        when (isSelected) {
            true -> view.setBackgroundResource(R.drawable.ic_hand_background)
            false -> view.setBackgroundResource(0)
        }
    }

    private fun setHandImage(handImage: Int): Int {

        return when (handImage) {
            1 -> R.drawable.ic_batu
            2 -> R.drawable.ic_kertas
            3 -> R.drawable.ic_gunting
            else -> 0
        }
    }

    private fun startGame() {

        val idPlayer1 = player1.handId
        val handPlayer1 = player1.hand
        val idPlayer2 = player2.handId
        val handPlayer2 = player2.hand
        val resultGame = controller.gameRule(idPlayer1, idPlayer2)

        logD("Start Game = $handPlayer1 VS $handPlayer2")
        if (idPlayer1 == resultGame) {
            showImageResult(1)
            return
        }
        if (idPlayer2 == resultGame) {
            showImageResult(2)
            return
        }
        showImageResult(resultGame)
    }

    private fun showImageResult(result: Int) {

        val getTextResult = controller.showWinner(result)
        viewResult.setImageResource(setImageResult(result))
        textRefresh.text = "Refresh"
        Toast.makeText(this, getTextResult, Toast.LENGTH_SHORT).show()
        logD("Result = $getTextResult")

        val dialogBuilder = AlertDialog.Builder(this)

        val viewCustom = LayoutInflater.from(this).inflate(R.layout.dialog_winner, null, false)

        dialogBuilder.setView(viewCustom)

        val dialog = dialogBuilder.create()

        val btnRefresh = viewCustom.findViewById<Button>(R.id.iv_refresh)
        val btnBack = viewCustom.findViewById<Button>(R.id.iv_back)
        val tvResult = viewCustom.findViewById<TextView>(R.id.iv_result)

        tvResult.text = getTextResult

        btnRefresh.setOnClickListener {
            dialog.dismiss()
            clearView()
        }

        btnBack.setOnClickListener {
            finish()
        }
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show()

    }

    private fun setImageResult(resultImage: Int): Int {

        return when (resultImage) {
            0 -> R.drawable.ic_draw
            1 -> R.drawable.ic_pemain1
            2 -> R.drawable.ic_pemain2
            else -> R.drawable.ic_vs
        }
    }

    private fun clearView() {

        isHandEnabled(true)
        viewResult.setImageResource(setImageResult(3))
        clearViewPlayer1()
        clearViewPlayer2()
        logD("Button Refresh Clicked")
    }

    private fun clearViewPlayer1() {

        setHand(handRock1, 1, false)
        setHand(handPaper1, 2, false)
        setHand(handScissor1, 3, false)
    }

    private fun clearViewPlayer2() {

        setHand(handRock2, 1, false)
        setHand(handPaper2, 2, false)
        setHand(handScissor2, 3, false)
    }

    private fun logD(message: String) {

        Log.i(GameActivity::class.java.simpleName, message)
    }

}