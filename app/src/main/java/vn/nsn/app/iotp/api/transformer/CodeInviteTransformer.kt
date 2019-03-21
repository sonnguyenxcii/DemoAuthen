package vn.nsn.app.iotp.api.transformer

import vn.nsn.app.iotp.api.dto.CodeInviteDTO
import vn.nsn.app.iotp.api.entity.CodeInvite

fun CodeInviteDTO.toCodeInvite(): CodeInvite? {
    return CodeInvite(code)
}