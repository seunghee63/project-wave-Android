package com.song2.wave.Data.GET

import com.song2.wave.Data.model.CustomPlayListData

data class GetEmailCheckResponse (
        val status: Long,
        val success: Boolean,
        val message: String,
        val data: String
)