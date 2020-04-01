package repository

import model.AppDataInfo

interface AppDataInfoRepository {

    fun save(appDataInfo: AppDataInfo)

    fun updateAppData(id: String, version: String)

    fun getAll(): List<AppDataInfo>

    fun getBySOAndEnvironment(so: String, environment: String): AppDataInfo?

    fun delete(sku: String): AppDataInfo

}
