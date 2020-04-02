package repository

import model.AppDataInfo

interface AppDataInfoRepository {

    fun save(appDataInfo: AppDataInfo)

    fun updateAppData(id: String, version: String)

    fun findBySOAndEnvironment(so: String, environment: String): AppDataInfo?

}
