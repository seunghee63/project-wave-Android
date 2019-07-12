package com.song2.wave.UI.Signup

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.song2.wave.R
import kotlinx.android.synthetic.main.activity_signup_genre.*
import okhttp3.MediaType
import okhttp3.RequestBody

class SignupGenreActivity : AppCompatActivity(), View.OnClickListener {

    val genre = ArrayList<String>()

    lateinit var receivedImgUri : Uri
    lateinit var genreArr : Array<ImageView>
    lateinit var selectedGenreArr : ArrayList<String>

    override fun onClick(v: View?) {
        for(i in 0..7){
            if (v!!.id == genreArr[i].getId()) {
                selectedGenreArr.add("m" + ((i+1).toString()))
                genre.add("m" + (i.toString()))
                Toast.makeText(applicationContext, "장르 " +"m" + (i+1).toString() + "번 버튼 선택", Toast.LENGTH_LONG).show()
            }
        }
        for(i in 0..selectedGenreArr.size-1)
            Log.v("act", "장르 배열 값 = " + selectedGenreArr[i])

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_genre)

        receivedImgUri = intent.getParcelableExtra<Parcelable>("imageUri") as Uri
        selectedGenreArr = ArrayList<String>()

        var popImg : ImageView = findViewById(R.id.img_signup_genre_act_pop)
        var balladImg : ImageView = findViewById(R.id.img_signup_genre_act_ballad)
        var danceImg : ImageView = findViewById(R.id.img_signup_genre_act_dance)
        var aqusticImg : ImageView = findViewById(R.id.img_signup_genre_act_aqustic)
        var rockImg : ImageView = findViewById(R.id.img_signup_genre_act_rock)
        var soulImg : ImageView = findViewById(R.id.img_signup_genre_act_soul)
        var hiphopImg : ImageView = findViewById(R.id.img_signup_genre_act_hiphop)
        var eletronicImg : ImageView = findViewById(R.id.img_signup_genre_act_eletronic)


        genreArr = arrayOf<ImageView>(balladImg, popImg, aqusticImg, danceImg, hiphopImg, soulImg, rockImg, eletronicImg)
        for(i in 0..7){
            genreArr[i].setOnClickListener(this)
        }

        btn_signup_genre_nex.setOnClickListener {
            var intent = Intent(applicationContext, SignupMoodActivity::class.java)
            intent.putExtra("imageUri",receivedImgUri)
            intent.putExtra("genreList",genre)
            startActivity(intent)
        }
    }
}
