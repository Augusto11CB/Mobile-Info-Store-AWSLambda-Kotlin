package model.dto

import com.google.gson.Gson
import enum.StatusAppVersionEnum

data class AppDataInfoRequestDTO(val userAgent:String = "")

data class AppDataInfoResponseDTO(

    val currentAppVersion: String,
    val latestAppVersion: String,
    val statusAppVersionEnum: StatusAppVersionEnum
)

fun AppDataInfoResponseDTO.toJSON(): String {
    val gson = Gson()

    return gson.toJson(this)
}