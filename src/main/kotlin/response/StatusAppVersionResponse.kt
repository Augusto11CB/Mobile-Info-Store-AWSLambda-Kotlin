package response

import model.dto.AppDataInfoDTO

data class StatusAppVersionResponse(val appDataInfoDTO: AppDataInfoDTO) : GenericResponse()