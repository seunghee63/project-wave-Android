package com.song2.wave.UI.Signup

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.song2.wave.Data.GET.GetOriginArtistResponse
import com.song2.wave.Data.model.RealArtistData
import com.song2.wave.R
import com.song2.wave.Util.Network.ApiClient
import com.song2.wave.Util.Network.NetworkService
import kotlinx.android.synthetic.main.activity_signup_select_artist.*
import retrofit2.Call
import retrofit2.Response

class SignupSelectArtistActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy { ApiClient.getRetrofit().create(NetworkService::class.java)
    }

    lateinit var artistDataArr : ArrayList<RealArtistData>
    lateinit var receivedImgUri : Uri
    lateinit var requestManager : RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_select_artist)

        receivedImgUri = intent.getParcelableExtra<Parcelable>("imageUri") as Uri
        requestManager = Glide.with(this)

        Log.v("asdf","이미지 = " + receivedImgUri)

        registerImage()

        btn_signup_artist_next.setOnClickListener {
            var intent = Intent(applicationContext, SignupGenreActivity::class.java)
            intent.putExtra("imageUri",receivedImgUri)
            startActivity(intent)
        }
    }

    fun registerImage(){

        artistDataArr = ArrayList<RealArtistData>()

        getOriginArtistResponse()
    }

    fun getOriginArtistResponse(){

        val getOriginArtistResponse = networkService.getOriginArtistResponse("application/json")

        getOriginArtistResponse.enqueue(object : retrofit2.Callback<GetOriginArtistResponse>{
            override fun onResponse(call: Call<GetOriginArtistResponse>, response: Response<GetOriginArtistResponse>) {

                if (response.isSuccessful) {
                    val originArtistData = response.body()!!.data

                    for (i in originArtistData.indices){
                        artistDataArr.add(RealArtistData(originArtistData[i].originArtistIdx.toInt(), originArtistData[i].originArtistImg, originArtistData[i].originArtistName))
                    }

                    recycler_signup_artist_selecct.adapter = SignupArtistAdapter(artistDataArr,requestManager)
                    recycler_signup_artist_selecct.layoutManager = GridLayoutManager(applicationContext, 3)

                }
            }

            override fun onFailure(call: Call<GetOriginArtistResponse>, t: Throwable) {

            }

        })


    }

}
