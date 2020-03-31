package model.dto

import com.google.gson.Gson
import enum.StatusAppVersionEnum

class AppDataInfoDTO(

    val currentAppVersion: String,
    val latestAppVersion: String,
    val statusAppVersionEnum: StatusAppVersionEnum
)

fun AppDataInfoDTO.toJSON(): String {
    val gson = Gson()

    return gson.toJson(this)
}