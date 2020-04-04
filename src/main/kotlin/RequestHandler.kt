import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import model.dto.AppDataInfoRequestDTO
import model.dto.AppDataInfoResponseDTO
import repository.AppDataInfoRepository
import repository.impl.AppDataInfoRepositoryImpl
import service.VersionManager


/*
class RequestHandler() : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private var appDataInfoRepository: AppDataInfoRepository = AppDataInfoRepositoryImpl()
    private lateinit var versionManager: VersionManager

    init {
        versionManager = VersionManager(appDataInfoRepository)
    }

    override fun handleRequest(input: APIGatewayProxyRequestEvent, context: Context): APIGatewayProxyResponseEvent {

        val appDataInfoDTO = versionManager.verifyAppVersion(input.headers["User-Agent"])

        return APIGatewayProxyResponseEvent()
            .withBody(appDataInfoDTO.toJSON())
            .withStatusCode(200)
    }

}
*/


class RequestHandler() : RequestHandler<AppDataInfoRequestDTO, AppDataInfoResponseDTO> {

    private var appDataInfoRepository: AppDataInfoRepository = AppDataInfoRepositoryImpl()
    private lateinit var versionManager: VersionManager

    init {
        versionManager = VersionManager(appDataInfoRepository)
    }

    override fun handleRequest(input: AppDataInfoRequestDTO?, context: Context?): AppDataInfoResponseDTO {

        return versionManager.verifyAppVersion(input!!.userAgent)


    }

}