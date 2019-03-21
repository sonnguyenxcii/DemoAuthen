package vn.nsn.app.iotp.api.transformer

import vn.nsn.app.iotp.api.dto.LoginResponseDTO
import vn.nsn.app.iotp.api.entity.LoginResponse

fun LoginResponseDTO.toLoginResponse(): LoginResponse {
    return LoginResponse(
            userDTO?.toUser(),
            metaDTO?.toMeta())
}