package com.example.binarcp5

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class HomeActivity : AppCompatActivity() {

    lateinit var imageBinar : ImageView
    lateinit var btnLoad : Button
    lateinit var ivSingle : ImageView
    lateinit var ivComp : ImageView


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        imageBinar = findViewById(R.id.iv_binar)
        btnLoad = findViewById(R.id.btn_load)
        ivSingle = findViewById(R.id.imageViewHomePlayerPlayer)
        ivComp = findViewById(R.id.imageViewHomePlayerComp)

        btnLoad.setOnClickListener {
            Glide.with(this)
                .load("https://i.ibb.co/zJHYGBP/binarlogo.jpg")
                .circleCrop()
                .into(imageBinar)
        }

        val layoutRoot = findViewById<ConstraintLayout>(R.id.layout_home)
        val namaUser = intent.getStringExtra("DATA_USER_NAME")
        val txtPlayer = findViewById<TextView>(R.id.textHomePlayerPlayer)
        val txtComp = findViewById<TextView>(R.id.textHomePlayerComp)

        Snackbar.make(layoutRoot, "Selamat Datang $namaUser", Snackbar.LENGTH_LONG).show()

        txtPlayer.text = "$namaUser VS Pemain 2"
        txtComp.text = "$namaUser VS Comp"

        ivSingle.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
        ivComp.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
    }

}