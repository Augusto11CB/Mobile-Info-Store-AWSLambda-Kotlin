package utils

import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig

class DynamoDBUtil private constructor() {

    //"Where we specify where de database is"
    private val dynamoDBClient: AmazonDynamoDB? = AmazonDynamoDBClientBuilder.standard()
        .withRegion(Regions.US_EAST_1)
        .build()

    private var dynamoDBMapper: DynamoDBMapper? = null

                                        //Where we pass as parameter the information of the database needed to be connect
    fun createDbMapper(mapperConfig: DynamoDBMapperConfig): DynamoDBMapper? {

        if (this.dynamoDBClient != null)
            dynamoDBMapper = DynamoDBMapper(this.dynamoDBClient, mapperConfig)

        return this.dynamoDBMapper
    }


    companion object {
        private var db_adapter: DynamoDBUtil? = null

        val instance: DynamoDBUtil?
            get() {
                if (db_adapter == null)
                    db_adapter = DynamoDBUtil()

                return db_adapter
            }
    }
}