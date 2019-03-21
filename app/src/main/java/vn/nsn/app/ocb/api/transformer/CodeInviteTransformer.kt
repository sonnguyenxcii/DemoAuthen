package vn.nsn.app.ocb.api.transformer

import vn.nsn.app.ocb.api.dto.CodeInviteDTO
import vn.nsn.app.ocb.api.entity.CodeInvite

fun CodeInviteDTO.toCodeInvite(): CodeInvite? {
    return CodeInvite(code)
}