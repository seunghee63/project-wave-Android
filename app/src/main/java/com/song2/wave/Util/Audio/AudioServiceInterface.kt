package com.song2.wave.Util.Audio

import android.app.Activity
import android.content.*
import android.os.IBinder
import android.util.Log
import com.song2.wave.UI.MainPlayer.MainPlayerActivity

import java.util.ArrayList

class AudioServiceInterface(context: Context) {
    private var mServiceConnection: ServiceConnection? = null
    private var mService: AudioService? = null
    var songTitle: String = ""
    var originArtistName: String = ""
    var coverartistName: String = ""

    val isPlaying: Boolean
        get() = if (mService != null) {
            mService!!.isPlaying()
        } else false

    val audioItem: AudioAdapter.AudioItem?
        get() = if (mService != null) {
            mService!!.audioItem
        } else null

    init {
        mServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                mService = (service as AudioService.AudioServiceBinder).service
            }

            override fun onServiceDisconnected(name: ComponentName) {
                mServiceConnection = null
                mService = null
            }
        }
        context.bindService(Intent(context, AudioService::class.java).setPackage(context.packageName), mServiceConnection!!, Context.BIND_AUTO_CREATE)
    }

    fun setPlayList(audioIds: ArrayList<Long>) {
        if (mService != null) {
            mService!!.setPlayList(audioIds)
        }
    }

    //    public void play(int position) {
    //        if (mService != null) {
    //            Log.v("asdf","테스트1");
    //            mService.play(position);
    //        }
    //    }

    fun play(context : Context, _id: String, songUrl: String, originArtist: String, coverArtist: String, songName: String) {
        if (mService != null) {

            var pref = context.getSharedPreferences("auto", Activity.MODE_PRIVATE)

            val token = pref.getString("songNum", "")
            Log.v("d", "서비스 실행, id값 = " + _id)
            Log.v("d", "서비스 실행, 토큰값 = " + pref.getString("songNum", ""))
            if(pref.getString("songNum", "").equals(_id)){
                Log.v("d", "서비스 실행, 동일 노래 = " + token) }

                Log.v("d", "서비스 실행X, id값 = " + _id)
                var editor: SharedPreferences.Editor = pref.edit()
                editor.putString("songNum", _id) // 토란  key값으로 userID 데이터를 저장한다.
                editor.commit()

                songTitle = songName
                originArtistName = originArtist
                coverartistName = coverArtist
                mService!!.play(_id, songUrl, originArtist, coverArtist, songName)
            }

    }

    fun play() {
        if (mService != null) {
            mService!!.play()
        }
    }

    fun togglePlay() {
        if (isPlaying) {
            mService!!.pause()
        } else {
            mService!!.play()
        }
    }

    fun pause() {
        if (mService != null) {
            mService!!.play()
        }
    }

    fun forward() {
        if (mService != null) {
            mService!!.forward()
        }
    }

    fun rewind() {
        if (mService != null) {
            mService!!.rewind()
        }
    }
}