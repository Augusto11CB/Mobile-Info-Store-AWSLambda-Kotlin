package service

import enum.StatusAppVersionEnum
import model.AppDataInfo
import model.dto.AppDataInfoDTO
import repository.AppDataInfoRepository
import utils.VersionComparatorUtil
import java.security.InvalidParameterException
import javax.management.InvalidAttributeValueException

class VersionManager(private val appDataInfoRepoRepository: AppDataInfoRepository) {

    fun verifyAppVersion(userAgent: String?): AppDataInfoDTO {


        userAgent?.let { it ->

            val userAppDataInfo = this.extractAppDataInfo(it)

            val appDataInfo =
                appDataInfoRepoRepository.getBySOAndEnvironment(userAppDataInfo.so!!, userAppDataInfo.environment!!)


            appDataInfo?.let {

                if (VersionComparatorUtil.compareVersion(appDataInfo.version!!, userAppDataInfo.version!!) > 0) {

                    appDataInfoRepoRepository.updateAppData(appDataInfo.id, userAppDataInfo.version!!)

                    return AppDataInfoDTO(
                        userAppDataInfo.version!!,
                        userAppDataInfo.version!!,
                        StatusAppVersionEnum.LATEST_VERSION
                    )
                } else {
                    return AppDataInfoDTO(
                        userAppDataInfo.version!!,
                        appDataInfo.version!!,
                        StatusAppVersionEnum.OUT_OF_DATE_VERSION
                    )
                }

            }
        }

        throw InvalidParameterException("Invalid user-agent")
    }

    private fun extractAppDataInfo(userAgent: String): AppDataInfo {
//        "XYAISUA (com.com.br.com.com; version:1.0.0; SO:iOS 13.3.1; Device: iPhone 8; uuid: IUE-83723-A82SH)"
//        "XYAISUA (com.com.br.com.com; version:1.0.0; SO:Android 13.3.1; Device: iPhone 8; uuid: IUE-83723-A82SH)"

        // 1. split string in ";"
        // 2. lock for version and SO
        //3 split string in ":"
        //4. store verison and make if (contains(android/ios))

        val userAgentSeparatedInfo = userAgent.split(";")
        var appDataInfo = AppDataInfo()

        val listAppVersionAndSO = userAgentSeparatedInfo
            .filter {
                filterSOandAppVersion(it)
            }.map {
                it.getPair()
            }.forEach {
                when {
                    it.first.toLowerCase() == AppDataInfo.SO -> appDataInfo.so = it.second
                    it.first.toLowerCase() == AppDataInfo.VERSION -> appDataInfo.version = it.second
                    it.first.toLowerCase() == AppDataInfo.ENVIRONMENT -> appDataInfo.environment = it.second
                }
            }

//        return appVersionAndSO.toMap()
        return AppDataInfo()

    }

    fun String.getPair(): Pair<String, String> {
        val x = this.split(":")

        if (x.size > 1) {
            return Pair(x[0], x[1])
        } else if (x.contains("com.")) { //TODO change for bk
            return Pair(AppDataInfo.ENVIRONMENT, this.split("(").last())
        }

        throw InvalidAttributeValueException("user-agent wrong format")
    }

    private fun filterSOandAppVersion(attribute: String): Boolean {
        return attribute.contains("version")
            .or(attribute.contains("SO"))
            .or(attribute.contains(".uat"))
            .or(attribute.contains(".dev"))
            .or(attribute.contains(".prd"))
    }
}