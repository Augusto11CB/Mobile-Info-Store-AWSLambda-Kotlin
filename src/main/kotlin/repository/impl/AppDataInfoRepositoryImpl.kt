package repository.impl

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import model.AppDataInfo
import repository.AppDataInfoRepository
import utils.DynamoDBUtil
import java.io.IOException

class AppDataInfoRepositoryImpl : AppDataInfoRepository {

    private var dbMapper: DynamoDBMapper

    init {
        val mapperConfig = DynamoDBMapperConfig.builder()
            .withTableNameOverride(DynamoDBMapperConfig.TableNameOverride(AppDataInfo.ENVIRONMENT))
            .build()
        dbMapper = DynamoDBUtil.instance!!.createDbMapper(mapperConfig)!!
    }

    override fun save(appDataInfo: AppDataInfo) {

        dbMapper.save(appDataInfo)
    }

    override fun updateAppData(id: String, version: String) {

        this.findById(id)?.let {
            it.version = version
            dbMapper.save(it)
        }
    }

    override fun getAll(): List<AppDataInfo> {
        return emptyList()
    }

    override fun getBySOAndEnvironment(so: String, environment: String): AppDataInfo {
        return AppDataInfo()
    }

    override fun delete(sku: String): AppDataInfo {
        return AppDataInfo()
    }

    @Throws(IOException::class)
    fun findById(id: String): AppDataInfo? {
        var appDataInfo: AppDataInfo? = null

        val params = HashMap<String, AttributeValue>()
        params["id"] = AttributeValue().withS(id)

        val queryExp = DynamoDBQueryExpression<AppDataInfo>()
//            .withKeyConditionExpression("sku = :v1")
            .withExpressionAttributeValues(params)

        val result = dbMapper.query(AppDataInfo::class.java, queryExp)

        if (result.size > 0) {
            appDataInfo = result[0]
        }
        return appDataInfo
    }

}