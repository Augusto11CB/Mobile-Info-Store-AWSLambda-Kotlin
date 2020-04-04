package response

import model.dto.AppDataInfoResponseDTO

data class StatusAppVersionResponse(val appDataInfoDTO: AppDataInfoResponseDTO) : GenericResponse()