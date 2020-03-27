package utils

import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig

class DynamoDBUtils private constructor() {

    //"Where de database is"
    private val dynamoDBClient: AmazonDynamoDB? = AmazonDynamoDBClientBuilder.standard()
        .withRegion(Regions.US_EAST_1)
        .build()

    private var dynamoDBMapper: DynamoDBMapper? = null

                                        // Information of the database needed to be connect
    fun createDbMapper(mapperConfig: DynamoDBMapperConfig): DynamoDBMapper? {

        if (this.dynamoDBClient != null)
            dynamoDBMapper = DynamoDBMapper(this.dynamoDBClient, mapperConfig)

        return this.dynamoDBMapper
    }


    companion object {
        private var db_adapter: DynamoDBUtils? = null

        val instance: DynamoDBUtils?
            get() {
                if (db_adapter == null)
                    db_adapter = DynamoDBUtils()

                return db_adapter
            }
    }
}