package com.song2.wave.UI.Main.Scoring


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.song2.wave.Data.GET.GetPlaylistResponse
import com.song2.wave.Data.model.PlayListData
import com.song2.wave.Data.model.SongData
import com.song2.wave.UI.Main.Scoring.Adapter.ScoringWaitAdapter
import com.song2.wave.Util.Network.ApplicationController
import com.song2.wave.Util.Network.NetworkService
import kotlinx.android.synthetic.main.fragment_scoring_wait.*
import retrofit2.Call
import retrofit2.Response

class ScoringWaitingFragment : Fragment() {

    val networkService: NetworkService by lazy { ApplicationController.instance.networkService
    }

    lateinit var authorization_info : String

    lateinit var songDataArr : ArrayList<SongData>
    lateinit var requestManager : RequestManager
    lateinit var songFieldData : ArrayList<String?>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v: View = inflater.inflate(com.song2.wave.R.layout.fragment_scoring_wait, container, false)

        authorization_info = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4IjoxMDUsImlhdCI6MTU2MjcyMjQ5MCwiZXhwIjoxNTY1MzE0NDkwfQ.CdVtW28EY4XOWV_xlt2dlYFMdEdFcIRN6lmsmJ8_jKQ"

        songDataArr = ArrayList<SongData>()
        requestManager = Glide.with(this)
        getRateReadyResponse()

        //insertData(v)

        return v
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun insertData(v : View){
        songDataArr.add(SongData("https://t1.daumcdn.net/cfile/tistory/2442394558BBBD1934", "좋은날", "아이유(IU)", "송제민", songFieldData))
        songDataArr.add(SongData("http://cdn.news2day.co.kr/news-images/peg/news/201709/8r1YZtmQRWoSic7Q6fv6i3cnEuj2RP0sqJwwEWGa-wm-1505700400.jpg", "가을아침", "아이유(IU)", "송제민", songFieldData))
        songDataArr.add(SongData("data:image/jpeg;base64,K9z4dklLE0/DKH3MOorln2gfzjf0p9KUro6h9pllfaVulKVxHMKUpQClKUApSlAf/Z", "Zeze", "아이유(IU)", "송제민", songFieldData))
        songDataArr.add(SongData("https://cphoto.asiae.co.kr/listimglink/1/2014051608371615808_1.jpg", "꽃갈피", "아이유(IU)", "송제민", songFieldData))
        songDataArr.add(SongData("https://pgnqdrjultom1827145.cdn.ntruss.com/img/f8/b9/f8b99005f6cc026302a55f0cba36c19ecbf1f2109f36639664a1c4217bbb41cd_v1.jpg", "무릎", "아이유(IU)", "송제민", songFieldData))

    }

    //rateReady
    fun getRateReadyResponse(){
        val getRateReadyResponse = networkService.getRateReadyResponse("application/json",authorization_info)

        getRateReadyResponse.enqueue(object : retrofit2.Callback<GetPlaylistResponse>{
            override fun onFailure(call: Call<GetPlaylistResponse>, t: Throwable) {
                Log.e("scoring tab rateReady song list fail", t.toString())
            }

            override fun onResponse(call: Call<GetPlaylistResponse>, response: Response<GetPlaylistResponse>) {
                if (response.isSuccessful) {
                    val playlistDataList: PlayListData = response.body()!!.data

                    if(playlistDataList == null)
                        return

                    for(i in playlistDataList.songList.indices)
                        songDataArr.add(SongData(playlistDataList.songList[i].artwork, playlistDataList.songList[i].originTitle,playlistDataList.songList[i].originArtistName, playlistDataList.songList[i].coverArtistName, playlistDataList.songList[i].genre))

                    recycler_scoring_wait_frag_list.adapter = ScoringWaitAdapter(songDataArr, requestManager)
                    recycler_scoring_wait_frag_list.layoutManager = LinearLayoutManager(context)
                }
            }

        })
    }
}