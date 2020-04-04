import repository.AppDataInfoRepository
import repository.impl.AppDataInfoRepositoryImpl
import service.VersionManager

class Application {

    companion object {
        @JvmStatic fun main(args: Array<String>){

             var appDataInfoRepository: AppDataInfoRepository = AppDataInfoRepositoryImpl()
             var versionManager: VersionManager = VersionManager(appDataInfoRepository)

            val appDataInfoDTO =
                versionManager.verifyAppVersion("XYAISUA (com.com.br.com.uat; version:1.0.0; SO:iOS 13.3.0 Device: iPhone 8; uuid: IUE-83723-A82SH)")

        }
    }



}