package vn.nsn.app.ocb.api.transformer

import vn.nsn.app.ocb.api.dto.LoginResponseDTO
import vn.nsn.app.ocb.api.entity.LoginResponse

fun LoginResponseDTO.toLoginResponse(): LoginResponse {
    return LoginResponse(
            userDTO?.toUser(),
            metaDTO?.toMeta())
}