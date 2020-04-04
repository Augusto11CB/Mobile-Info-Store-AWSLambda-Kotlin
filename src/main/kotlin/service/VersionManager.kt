package service

import enum.StatusAppVersionEnum
import model.AppDataInfo
import model.dto.AppDataInfoResponseDTO
import repository.AppDataInfoRepository
import utils.VersionComparatorUtil
import java.security.InvalidParameterException
import javax.management.InvalidAttributeValueException

class VersionManager(private val appDataInfoRepoRepository: AppDataInfoRepository) {

    fun verifyAppVersion(userAgent: String?): AppDataInfoResponseDTO {


        userAgent?.let { it ->

            val userAppDataInfo = this.extractAppDataInfo(it)

            val appDataInfo =
                appDataInfoRepoRepository.findBySOAndEnvironment(userAppDataInfo.so!!, userAppDataInfo.environment!!)


            appDataInfo?.let {

                if (VersionComparatorUtil.compareVersion(userAppDataInfo.version!!, appDataInfo.version!!) > 0) {

                    appDataInfoRepoRepository.updateAppData(appDataInfo.getId()!!, userAppDataInfo.version!!)

                    return AppDataInfoResponseDTO(
                        userAppDataInfo.version!!,
                        userAppDataInfo.version!!,
                        StatusAppVersionEnum.LATEST_VERSION
                    )

                } else {

                    return AppDataInfoResponseDTO(
                        userAppDataInfo.version!!,
                        appDataInfo.version!!,
                        StatusAppVersionEnum.OUT_OF_DATE_VERSION
                    )
                }
            }

            appDataInfoRepoRepository.save(userAppDataInfo)
        }

        throw InvalidParameterException("Invalid user-agent")
    }

    private fun extractAppDataInfo(userAgent: String): AppDataInfo {
//        "XYAISUA (com.com.br.com.com; version:1.0.0; SO:iOS 13.3.1; Device: iPhone 8; uuid: IUE-83723-A82SH)"
//        "XYAISUA (com.com.br.com.com; version:1.0.0; SO:Android 13.3.1; Device: Motorola XI; uuid: IUE-83938-432SH)"

        // 1. split string in ";"
        // 2. lock for version and SO
        // 3 split string in ":"
        // 4. store verison and make if (contains(android/ios))

        val userAgentSeparatedInfo = userAgent.split(";")
        var appDataInfo = AppDataInfo()

        val listAppVersionAndSO = userAgentSeparatedInfo
            .filter { result ->
                filterSOandAppVersion(result)
            }.map {
                it.getPair()
            }.forEach {
                when {
                    it.first.toLowerCase() == AppDataInfo.SO -> appDataInfo.so = it.second
                    it.first.toLowerCase() == AppDataInfo.VERSION -> appDataInfo.version = it.second
                    it.first.toLowerCase() == AppDataInfo.ENVIRONMENT -> appDataInfo.environment = it.second
                }
            }

        return appDataInfo

    }

    fun String.getPair(): Pair<String, String> {

        var x = this.split(":")
        
        if (x.size > 1) {
            return Pair(x[0].trim(), x[1].trim())
        } else { //TODO change for BK
            x = this.split("(")

            if (x.last().contains("com.")) return Pair(AppDataInfo.ENVIRONMENT, x.last())
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