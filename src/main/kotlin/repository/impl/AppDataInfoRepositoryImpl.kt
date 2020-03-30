package repository

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import model.AppDataInfo
import utils.DynamoDBUtils

class AppDataInfoRepository {

    private var dbMapper: DynamoDBMapper

    init {
        val mapperConfig = DynamoDBMapperConfig.builder()
            .withTableNameOverride(DynamoDBMapperConfig.TableNameOverride(AppDataInfo.ENVIRONMENT))
            .build()
        dbMapper = DynamoDBUtils.instance!!.createDbMapper(mapperConfig)!!
    }

    fun insert(name: String, description: String?) {

    }

    fun updateAppData(id: String, name: String?, description: String?) {

    }

    fun getAll(): List<AppDataInfo> {
        return emptyList()
    }

    fun getByVersionAndModelAndEnvironment(): AppDataInfo {
        return AppDataInfo()
    }

    fun delete(sku: String): AppDataInfo {
        return AppDataInfo()
    }

}