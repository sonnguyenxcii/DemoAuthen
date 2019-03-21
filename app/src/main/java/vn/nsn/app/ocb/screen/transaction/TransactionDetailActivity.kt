package vn.nsn.app.ocb.screen.transaction

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.util.Base64
import android.view.View
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.OnClick
import com.centagate.module.account.AccountInfo
import com.centagate.module.authentication.AuthenticationService
import com.centagate.module.common.CompleteEntity
import com.centagate.module.common.Configuration
import com.centagate.module.device.FileSystem
import com.centagate.module.device.PinAuthentication
import com.centagate.module.exception.CentagateException
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import vn.nsn.app.ocb.R
import vn.nsn.app.ocb.extension.textColor
import vn.nsn.app.ocb.mvp.MvpActivity
import vn.nsn.app.ocb.screen.LoginFragment
import vn.nsn.app.ocb.screen.authen.pincode.CreatePinCodeContract
import vn.nsn.app.ocb.screen.authen.pincode.CreatePinCodePresenter
import vn.nsn.app.ocb.util.LanguageUtils
import vn.nsn.app.ocb.view.RegularTextView
import java.io.ByteArrayInputStream
import java.io.StringReader
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.xml.parsers.DocumentBuilderFactory


class TransactionDetailActivity : MvpActivity<CreatePinCodePresenter>(), CreatePinCodeContract {

    @BindView(R.id.tv_tittle)
    lateinit var tvTittle: RegularTextView

    @BindView(R.id.toolbar)
    lateinit var toolbar: ConstraintLayout

    @BindView(R.id.tv_from_account)
    lateinit var tvFromAccount: RegularTextView
    //
    @BindView(R.id.tv_from_account_value)
    lateinit var tvFromAccountValue: RegularTextView

    @BindView(R.id.tv_trans_id)
    lateinit var tvTransId: RegularTextView

    @BindView(R.id.tv_trans_id_value)
    lateinit var tvTransIdValue: RegularTextView

    @BindView(R.id.tv_to_account)
    lateinit var tvToAccount: RegularTextView
    //
    @BindView(R.id.tv_to_account_value)
    lateinit var tvToAccountValue: RegularTextView

    @BindView(R.id.tv_money)
    lateinit var tvMoney: RegularTextView
    //
    @BindView(R.id.tv_money_value)
    lateinit var tvMoneyValue: RegularTextView

    @BindView(R.id.tv_time)
    lateinit var tvTime: RegularTextView
    //
    @BindView(R.id.tv_time_value)
    lateinit var tvTimeValue: RegularTextView

    var dataExtra: String = ""
    var randomString: String = ""

    @BindView(R.id.ln_trans_id)
    lateinit var ln_trans_id: LinearLayout
    @BindView(R.id.ln_from)
    lateinit var ln_from: LinearLayout
    @BindView(R.id.ln_to)
    lateinit var ln_to: LinearLayout
    @BindView(R.id.ln_money)
    lateinit var ln_money: LinearLayout

    override fun initPresenter() {
    }

    override fun attachViewToPresenter() {
    }

    lateinit var languageUtils: LanguageUtils


    override fun initLocale() {
        languageUtils = LanguageUtils(this)
        val lang = languageUtils.loadLocale(this)
        languageUtils.changeLang(lang, this)
    }

    override val layoutResId: Int
        get() = R.layout.activity_transaction_detail //To change initializer of created properties use File | Settings | File Templates.

    override fun setupViews() {

        tvTittle.text = getString(R.string.transaction_approval)
        initSDK()
        getTokenProcess().execute()
    }

    fun getTransactionDetail(): String? {
        var message = ""
        var accountInfo: AccountInfo
        var authenticationService = AuthenticationService()
        val securityDevice = PinAuthentication()
        securityDevice.setPin(preferenceHelper.getPincode())
        var data = getAllData(this)
        if (data != null) {
            if (data.accounts.size > 0) {
                var account = data.accounts[0]
                accountInfo = account.accountInfo
                var requestInfo = authenticationService.getRequestInfo(Build.SERIAL, preferenceHelper.getSession(), accountInfo, securityDevice)
                message = requestInfo.displayMessage
                println("-----message---------" + message)

                dataExtra = message
                randomString = requestInfo.randomString
            }
        }

        return message
    }

    private fun initSDK() {
        //Setup the initialize of SDK
        Configuration.getInstance().webServiceUrl = applicationContext.resources.getString(R.string.app_default_web_service_url)
        Configuration.getInstance().approvalUrl = applicationContext.resources.getString(R.string.app_default_soft_cert_url)
        Configuration.getInstance().timeout = 120
        Configuration.getInstance().serverEncPublicKey = applicationContext.resources.getString(R.string.app_default_server_enc_public_key)
        Configuration.getInstance().serverSignPublicKey = applicationContext.resources.getString(R.string.app_default_server_verify_public_key)

        val rootCertBase64 = "MIIEfTCCA2WgAwIBAgIDG+cVMA0GCSqGSIb3DQEBCwUAMGMxCzAJBgNVBAYTAlVTMSEwHwYDVQQKExhUaGUgR28gRGFkZHkgR3JvdXAsIEluYy4xMTAvBgNVBAsTKEdvIERhZGR5IENsYXNzIDIgQ2VydGlmaWNhdGlvbiBBdXRob3JpdHkwHhcNMTQwMTAxMDcwMDAwWhcNMzEwNTMwMDcwMDAwWjCBgzELMAkGA1UEBhMCVVMxEDAOBgNVBAgTB0FyaXpvbmExEzARBgNVBAcTClNjb3R0c2RhbGUxGjAYBgNVBAoTEUdvRGFkZHkuY29tLCBJbmMuMTEwLwYDVQQDEyhHbyBEYWRkeSBSb290IENlcnRpZmljYXRlIEF1dGhvcml0eSAtIEcyMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv3FiCPH6WTT3G8kYo/eASVjpIoMTpsUgQwE7hPHmhUmfJ+r2hBtOoLTbcJjHMgGxBT4HTu70+k8vWTAi56sZVmvigAf88xZ1gDlRe+X5NbZ0TqmNghPktj+pA4P6or6KFWp/3gvDthkUBcrqw6gElDtGfDIN8wBmIsiNaW02jBEYt9OyHGC0OPoCjM7T3UYH3go+6118yHz7sCtTpJJiaVElBWEaRIGMLKlDliPfrDqBmg4pxRyp6V0etp6eMAo5zvGIgPtLXcwy7IViQyU0AlYnAZG0O3AqP26x6JyIAX2f1PnbU21gnb8s51iruF9G/M7EGwM8CetJMVxpRrPgRwIDAQABo4IBFzCCARMwDwYDVR0TAQH/BAUwAwEB/zAOBgNVHQ8BAf8EBAMCAQYwHQYDVR0OBBYEFDqahQcQZyi27/a9BUFuIMGU2g/eMB8GA1UdIwQYMBaAFNLEsNKR1EwRcbNhyz2h/t2oatTjMDQGCCsGAQUFBwEBBCgwJjAkBggrBgEFBQcwAYYYaHR0cDovL29jc3AuZ29kYWRkeS5jb20vMDIGA1UdHwQrMCkwJ6AloCOGIWh0dHA6Ly9jcmwuZ29kYWRkeS5jb20vZ2Ryb290LmNybDBGBgNVHSAEPzA9MDsGBFUdIAAwMzAxBggrBgEFBQcCARYlaHR0cHM6Ly9jZXJ0cy5nb2RhZGR5LmNvbS9yZXBvc2l0b3J5LzANBgkqhkiG9w0BAQsFAAOCAQEAWQtTvZKGEacke+1bMc8dH2xwxbhuvk679r6XUOEwf7ooXGKUwuN+M/f7QnaF25UcjCJYdQkMiGVnOQoWCcWgOJekxSOTP7QYpgEGRJHjp2kntFolfzq3Ms3dhP8qOCkzpN1nsoX+oYggHFCJyNwq9kIDN0zmiN/VryTyscPfzLXs4Jlet0lUIDyUGAzHHFIYSaRt4bNYC8nY7NmuHDKOKHAN4v6mF56ED71XcLNa6R+ghlO773z/aQvgSMO3kwvIClTErF0UZzdsyqUvMQg3qm5vjLyb4lddJIGvl5echK1srDdMZvNhkREg5L4wn3qkKQmw4TRfZHcYQFHfjDCmrw=="
        val intermediateCertBase64 = "MIIE0DCCA7igAwIBAgIBBzANBgkqhkiG9w0BAQsFADCBgzELMAkGA1UEBhMCVVMxEDAOBgNVBAgTB0FyaXpvbmExEzARBgNVBAcTClNjb3R0c2RhbGUxGjAYBgNVBAoTEUdvRGFkZHkuY29tLCBJbmMuMTEwLwYDVQQDEyhHbyBEYWRkeSBSb290IENlcnRpZmljYXRlIEF1dGhvcml0eSAtIEcyMB4XDTExMDUwMzA3MDAwMFoXDTMxMDUwMzA3MDAwMFowgbQxCzAJBgNVBAYTAlVTMRAwDgYDVQQIEwdBcml6b25hMRMwEQYDVQQHEwpTY290dHNkYWxlMRowGAYDVQQKExFHb0RhZGR5LmNvbSwgSW5jLjEtMCsGA1UECxMkaHR0cDovL2NlcnRzLmdvZGFkZHkuY29tL3JlcG9zaXRvcnkvMTMwMQYDVQQDEypHbyBEYWRkeSBTZWN1cmUgQ2VydGlmaWNhdGUgQXV0aG9yaXR5IC0gRzIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC54MsQ1K92vdSTYuswZLiBCGzDBNliF44v/z5lz4/OYuY8UhzaFkVLVat4a2ODYpDOD2lsmcgaFItMzEUz6ojcnqOvK/6AYZ15V8TPLvQ/MDxdR/yaFrzDN5ZBUY4RS1T4KL7QjL7wMDge87Am+GZHY23ecSZHjzhHU9FGHbTj3ADqRay9vHHZqm8A29vNMDp5T19MR/gd71vCxJ1gO7GyQ5HYpDNO6rPWJ0+tJYqlxvTV0KaudAVkV4i1RFXULSo6Pvi4vekyCgKUZMQWOlDxSq7neTOvDCAHf+jfBDnCaQJsY1L6d8EbyHSHyLmTGFBUNUtpTrw700kuH9zB0lL7AgMBAAGjggEaMIIBFjAPBgNVHRMBAf8EBTADAQH/MA4GA1UdDwEB/wQEAwIBBjAdBgNVHQ4EFgQUQMK9J47MNIMwojPX+2yz8LQsgM4wHwYDVR0jBBgwFoAUOpqFBxBnKLbv9r0FQW4gwZTaD94wNAYIKwYBBQUHAQEEKDAmMCQGCCsGAQUFBzABhhhodHRwOi8vb2NzcC5nb2RhZGR5LmNvbS8wNQYDVR0fBC4wLDAqoCigJoYkaHR0cDovL2NybC5nb2RhZGR5LmNvbS9nZHJvb3QtZzIuY3JsMEYGA1UdIAQ/MD0wOwYEVR0gADAzMDEGCCsGAQUFBwIBFiVodHRwczovL2NlcnRzLmdvZGFkZHkuY29tL3JlcG9zaXRvcnkvMA0GCSqGSIb3DQEBCwUAA4IBAQAIfmyTEMg4uJapkEv/oV9PBO9sPpyIBslQj6Zz91cxG7685C/b+LrTW+C05+Z5Yg4MotdqY3MxtfWoSKQ7CC2iXZDXtHwlTxFWMMS2RJ17LJ3lXubvDGGqv+QqG+6EnriDfcFDzkSnE3ANkR/0yBOtg2DZ2HKocyQetawiDsoXiWJYRBuriSUBAA/NxBti21G00w9RKpv0vHP8ds42pM3Z2Czqrpv1KrKQ0U11GIo/ikGQI31bS/6kA1ibRrLDYGCD+H1QQc7CoZDDu+8CL9IVVO5EFdkKrqeKM+2xLXY2JtwE65/3YR8V3Idv7kaWKK2hJn0KCacuBKONvPi8BDAB"
        val abbCertBase64 = "MIIFojCCBIqgAwIBAgIMZuITy/3QHViWSHSsMA0GCSqGSIb3DQEBCwUAMGYxCzAJBgNVBAYTAkJFMRkwFwYDVQQKExBHbG9iYWxTaWduIG52LXNhMTwwOgYDVQQDEzNHbG9iYWxTaWduIE9yZ2FuaXphdGlvbiBWYWxpZGF0aW9uIENBIC0gU0hBMjU2IC0gRzIwHhcNMTcwNDAzMDk1ODQ3WhcNMTkwNTE1MDc0NjU3WjCBlTELMAkGA1UEBhMCVk4xFDASBgNVBAgMC0hvIENoaSBNaW5oMRQwEgYDVQQHDAtIbyBDaGkgTWluaDEWMBQGA1UECwwNSVQgRGVwYXJ0bWVudDEsMCoGA1UECgwjQU4gQklOSCBDT01NRVJDSUFMIEpPSU5UIFNUT0NLIEJBTksxFDASBgNVBAMMCyouYWJiYW5rLnZuMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAunfSPe6JJ8dS4ubk+m9aREUWkgc1xwvHkPg8VtRBswxLlR0o9c9laPyX/xqW2wG0ZMp8PAYq8FW5SusBBGfPiQarfbU1K8HeHdBXynhYbSDoISB8LvvHeJDoAlrFok8HfYdlKWGnRwM37FIyh0Uezt2pStFXj74ygWEFEST8Un8F+YTE2pvCosch2+xxhiNRdgP48OUz47cBWfoAj+P2NMxgFzFmKT4OyIo1sLgSRcK5VFQ5QoljbdrJZvAPBr+qta3Jfh2UvvDvlmmCcQ5Rk9StV7sTyKw0wQk9Ptb3llZ4axCbLzwoJ6V80zzMVFgE6z0sbUbUqdqPWoa908dhTQIDAQABo4ICHjCCAhowDgYDVR0PAQH/BAQDAgWgMIGgBggrBgEFBQcBAQSBkzCBkDBNBggrBgEFBQcwAoZBaHR0cDovL3NlY3VyZS5nbG9iYWxzaWduLmNvbS9jYWNlcnQvZ3Nvcmdhbml6YXRpb252YWxzaGEyZzJyMS5jcnQwPwYIKwYBBQUHMAGGM2h0dHA6Ly9vY3NwMi5nbG9iYWxzaWduLmNvbS9nc29yZ2FuaXphdGlvbnZhbHNoYTJnMjBWBgNVHSAETzBNMEEGCSsGAQQBoDIBFDA0MDIGCCsGAQUFBwIBFiZodHRwczovL3d3dy5nbG9iYWxzaWduLmNvbS9yZXBvc2l0b3J5LzAIBgZngQwBAgIwCQYDVR0TBAIwADBJBgNVHR8EQjBAMD6gPKA6hjhodHRwOi8vY3JsLmdsb2JhbHNpZ24uY29tL2dzL2dzb3JnYW5pemF0aW9udmFsc2hhMmcyLmNybDBYBgNVHREEUTBPggsqLmFiYmFuay52boINb3dhLmFiYmFuay52boIObWFpbC5hYmJhbmsudm6CFmF1dG9kaXNjb3Zlci5hYmJhbmsudm6CCWFiYmFuay52bjAdBgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwHQYDVR0OBBYEFLKMUkbkHXS7mlQcYychpy87kQh1MB8GA1UdIwQYMBaAFJbeYfG9HBYpUxzAzH07gwBA5hp8MA0GCSqGSIb3DQEBCwUAA4IBAQAFLnDFt6feWDhnrGbqLAuEanxoa6BEE397Ydiy9qqTtARj56M/V0SIOQI9fIJRX+qxKztxtVSxffvexJtT/YijMRExfq4+VFeLH7lEr55fnIbin7MvL4EZdFCZpK6PcR66Y7Y3oT5w6riByLnLCM2aztLOG2e7HmtUz6dECxRk64ONk4ID7H1AvE/dxfWZ56JY8u7xjEFfxNaGeB1FC8txHzc7QepVPOMUmE5UtK21+KDc6X8M3guKXWcmh08wkTslLkgLWEl5JWCYnCyzLaKIaQQ8FIYfWEhTqAMXXqdv3S9NxJS/hLAfDLRePtfTuwqH3qVwhxjY+eFzHngkaS4D"
        val ocbCertBase64 = "MIIFXzCCBEegAwIBAgISAwBMGaAdQSuChSsLD+ikGpdcMA0GCSqGSIb3DQEBCwUAMEoxCzAJBgNVBAYTAlVTMRYwFAYDVQQKEw1MZXQncyBFbmNyeXB0MSMwIQYDVQQDExpMZXQncyBFbmNyeXB0IEF1dGhvcml0eSBYMzAeFw0xODExMjEwODU3MjZaFw0xOTAyMTkwODU3MjZaMB4xHDAaBgNVBAMMEyoucGh1b25nZG9uZ2Jhbmsudm4wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDe9nZWr2GIGHcxZXk0JpMze6sKkmMmuXT3sXcf91GaEZ8alDqf86QofFU9yU+Sy6Y8woYw9qE2y8hnIZOuDpZFlZMEGl5fhL2P/4VzW8LjfgJnsIZV2wyV6wq7FjjgEL5KVgqnwEl8M2Xsh8ECz9f/SfTfebqy6f9B/R/DYNj4jyHvbQyIMckjfYBuVgQDV0gLIBp/pYzaY5LV//2wp7YKqAB8MZsLhGQhCJDLeX5xvQViceduEfJXqQ+zigTYwXtFmufL0XgpREgBsZ6n+JOnCSPgRUWEKT7IFaROnEsQi3ykOUSGK07iqMl4Ics1eKd5jUi2ssWMhN/EqkNee7R9AgMBAAGjggJpMIICZTAOBgNVHQ8BAf8EBAMCBaAwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMAwGA1UdEwEB/wQCMAAwHQYDVR0OBBYEFHKyL7+kC73WuERmemeyp1dQRsfyMB8GA1UdIwQYMBaAFKhKamMEfd265tE5t6ZFZe/zqOyhMG8GCCsGAQUFBwEBBGMwYTAuBggrBgEFBQcwAYYiaHR0cDovL29jc3AuaW50LXgzLmxldHNlbmNyeXB0Lm9yZzAvBggrBgEFBQcwAoYjaHR0cDovL2NlcnQuaW50LXgzLmxldHNlbmNyeXB0Lm9yZy8wHgYDVR0RBBcwFYITKi5waHVvbmdkb25nYmFuay52bjBMBgNVHSAERTBDMAgGBmeBDAECATA3BgsrBgEEAYLfEwEBATAoMCYGCCsGAQUFBwIBFhpodHRwOi8vY3BzLmxldHNlbmNyeXB0Lm9yZzCCAQUGCisGAQQB1nkCBAIEgfYEgfMA8QB3AOJpS64m6OlACeiGG7Y7g9Q+5/50iPukjyiTAZ3d8dv+AAABZzWz++QAAAQDAEgwRgIhAOnptidWgt4LzOzgAaffttBqj4YwoHIGQmHNuz1axthUAiEAn9vi+JV2Es276Sl2XGQXEvUZRDpl69FGn1q0B0thzQ8AdgApPFGWVMg5ZbqqUPxYB9S3b79Yeily3KTDDPTlRUf0eAAAAWc1s/wxAAAEAwBHMEUCIQChUg3fGzRXI5zBmUuz0Jc1Cam7JtM+b4SxuVSF5744hAIgM1Y8ie/jVzoIR4ziOh02aBG9hhK5oMdK0oZVMGHnUEgwDQYJKoZIhvcNAQELBQADggEBAJWzFMF/zemkfPFUBIfSdsugfxTRt6IDieJwZWWpEUw8r3smVGpHn9mkOva/vSAn/dREVgvZ4llM+J2yahz74jIT8qJiE8GUb9/T5Co8ScuWVGOFlAKp8td1XWSut/Z1kBLVAvhJMcy1ybaz1igyIa90teTY7SIJhUWN9VLdDxrTDKmZitiYAF1NRxZYB8SiO53ySuN7LeRqmVNquYIjbd/CFYT8khqDDA8s7brqeDHPEOR4VJbrWfSOwPxXCzZy4b37kxvDai5J8lIrWzr6uilp0GZQ5eAPqrQr9THpO2iM1chTNJNoQoC6XqqTtRgSaySaISQNkN3JYECx0rkr4Wo="
        val testCertBase64 = "MIIFOzCCBCOgAwIBAgIIWiurdYTMOZ4wDQYJKoZIhvcNAQELBQAwgbQxCzAJBgNVBAYTAlVTMRAwDgYDVQQIEwdBcml6b25hMRMwEQYDVQQHEwpTY290dHNkYWxlMRowGAYDVQQKExFHb0RhZGR5LmNvbSwgSW5jLjEtMCsGA1UECxMkaHR0cDovL2NlcnRzLmdvZGFkZHkuY29tL3JlcG9zaXRvcnkvMTMwMQYDVQQDEypHbyBEYWRkeSBTZWN1cmUgQ2VydGlmaWNhdGUgQXV0aG9yaXR5IC0gRzIwHhcNMTcwNjA2MDYxMzAwWhcNMTkwODA1MDcyNjM4WjBAMSEwHwYDVQQLExhEb21haW4gQ29udHJvbCBWYWxpZGF0ZWQxGzAZBgNVBAMMEiouc2VjdXJlbWV0cmljLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAM5HI3XhbDVjPsTxxrjo5BjRycDPvHaSzICtb2kFY1yE6O+C39uiXjCGqKoTuTSm1BpcwnkL2CyFrrBkHhqIoZkeCEF0Fj7JmLtUysFmE4onekYDb/tzGU6jJe/slSn7gvNfGZSfzAkrA7+rJFLDjf+9fHj2BDjZnNmhDSxo6wbzl3LpDFtJdvyaqWsrV1Hb2sJHD/HBifF9HCVQGZP7PLrrrGiLfDYFR4JyqPbPkaNAm/5KEVDlMYf/hfYe1EmgokfBsBCCvx6/sygzCTLLFJGeVMArpztdy/S7m7wvPpw8H7bZdkpbuUD61hSJv6utKap1lXZAgpYVvQnBy/BvZ6kCAwEAAaOCAcIwggG+MAwGA1UdEwEB/wQCMAAwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMA4GA1UdDwEB/wQEAwIFoDA3BgNVHR8EMDAuMCygKqAohiZodHRwOi8vY3JsLmdvZGFkZHkuY29tL2dkaWcyczEtNTQ1LmNybDBdBgNVHSAEVjBUMEgGC2CGSAGG/W0BBxcBMDkwNwYIKwYBBQUHAgEWK2h0dHA6Ly9jZXJ0aWZpY2F0ZXMuZ29kYWRkeS5jb20vcmVwb3NpdG9yeS8wCAYGZ4EMAQIBMHYGCCsGAQUFBwEBBGowaDAkBggrBgEFBQcwAYYYaHR0cDovL29jc3AuZ29kYWRkeS5jb20vMEAGCCsGAQUFBzAChjRodHRwOi8vY2VydGlmaWNhdGVzLmdvZGFkZHkuY29tL3JlcG9zaXRvcnkvZ2RpZzIuY3J0MB8GA1UdIwQYMBaAFEDCvSeOzDSDMKIz1/tss/C0LIDOMC8GA1UdEQQoMCaCEiouc2VjdXJlbWV0cmljLmNvbYIQc2VjdXJlbWV0cmljLmNvbTAdBgNVHQ4EFgQUQfQ7Bz/3gx+srEp+zdVc8G3iDsMwDQYJKoZIhvcNAQELBQADggEBAICLFC4MCez0O+/6aTJuicFi61I/XAojpGIP0fIUzX+N8LawbWHQ3PwV5Hy1CoXTb2xpd7nBse0Pgd+2aCYdOXkJRR37eYU7IrzFnfZeMhaC+a0AQbLsJOjuFAAftayypudqUG6pv9aS6eyCLWfI7PC4kfuGZ+n3UiurX5YhA2cLbqjsKHommhcBFSl/VVWuvamtgNc3ZLE0RYPydFacmwWTmqa1wnvZMN2JiUpxrIioTzh+KuCf5s4KiA/Nr9crdPClXO0QtV4MOnD77sjsWGMoo5g9K2qPz8qJA14Y/dpfPKNa8bF5cgZ40aw7Pfsq7lldBTfqyzvXIvPpF8bG0WY="
        val ocbCertBase64_test = "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"

        var keyStore: KeyStore? = null
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore!!.load(null, null)

            val cf = CertificateFactory.getInstance("X.509")

            val rootCert = cf.generateCertificate(ByteArrayInputStream(Base64.decode(rootCertBase64, Base64.DEFAULT)))
            keyStore.setCertificateEntry("DEMO ROOT", rootCert)

            val intermediateCert = cf.generateCertificate(ByteArrayInputStream(Base64.decode(intermediateCertBase64, Base64.DEFAULT)))
            keyStore.setCertificateEntry("DEMO INTERMEDIATE", rootCert)

            val abbCert = cf.generateCertificate(ByteArrayInputStream(Base64.decode(abbCertBase64, Base64.DEFAULT)))
            keyStore.setCertificateEntry("ABB INTERMEDIATE", abbCert)

            val ocbCert = cf.generateCertificate(ByteArrayInputStream(Base64.decode(ocbCertBase64, Base64.DEFAULT)))
            keyStore.setCertificateEntry("OCB INTERMEDIATE", ocbCert)

            val testCert = cf.generateCertificate(ByteArrayInputStream(Base64.decode(testCertBase64, Base64.DEFAULT)))
            keyStore.setCertificateEntry("TEST INTERMEDIATE", testCert)

            val testCert_office = cf.generateCertificate(ByteArrayInputStream(Base64.decode(ocbCertBase64_test, Base64.DEFAULT)))
            keyStore.setCertificateEntry("TEST office INTERMEDIATE", testCert_office)
        } catch (e: Exception) {
//            Toast.makeText(this@SplashActivity, "AA" + e.message, Toast.LENGTH_SHORT).show()
//            e.printStackTrace()
        }

        //the certs which are trusted to has connection, will reject if doesn't match with server cert
        Configuration.getInstance().connectionKeyStore = keyStore

        //if want to truested all connection to server
        //Configuration.getInstance().setConnectionKeyStore(null);

    }

    private fun parser(data: String) {
        try {

            val dbFactory = DocumentBuilderFactory.newInstance()
            val dBuilder = dbFactory.newDocumentBuilder()
            println("---------data---------" + data)
            var string = "<?xml version=\"1.0\"?>$data"
            val doc = dBuilder.parse(InputSource(StringReader(string)))
            val element = doc.documentElement
            element.normalize()

            val field1s = doc.getElementsByTagName("Field2")
            if (field1s.getLength() > 1) {
                if (preferenceHelper.getLang() == "vi") {
                    val node = field1s.item(0)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element

                        tvFromAccount.text = getValue("Caption", element2)
                        tvFromAccountValue.text = getValue("Data", element2)
                        tvFromAccountValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                } else {
                    val node = field1s.item(1)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element

                        tvFromAccount.text = getValue("Caption", element2)
                        tvFromAccountValue.text = getValue("Data", element2)
                        tvFromAccountValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                }
            } else {
                ln_from.visibility = View.GONE
            }

            val field2s = doc.getElementsByTagName("Field1")
            if (field2s.getLength() > 1) {
                if (preferenceHelper.getLang() == "vi") {
                    val node = field2s.item(0)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element

                        tvTransId.text = getValue("Caption", element2)
                        tvTransIdValue.text = getValue("Data", element2)
                        tvTransIdValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                } else {
                    val node = field2s.item(1)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element

                        tvTransId.text = getValue("Caption", element2)
                        tvTransIdValue.text = getValue("Data", element2)
                        tvTransIdValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                }
            } else {
                ln_trans_id.visibility = View.GONE
            }

            val field3s = doc.getElementsByTagName("Field3")
            if (field3s.getLength() > 1) {
                if (preferenceHelper.getLang() == "vi") {
                    val node = field3s.item(0)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element
                        tvToAccount.text = getValue("Caption", element2)
                        tvToAccountValue.text = getValue("Data", element2)
                        tvToAccountValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                } else {
                    val node = field3s.item(1)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element
                        tvToAccount.text = getValue("Caption", element2)
                        tvToAccountValue.text = getValue("Data", element2)
                        tvToAccountValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                }
            } else {
                ln_to.visibility = View.GONE
            }

            val field4s = doc.getElementsByTagName("Field4")
            if (field4s.getLength() > 1) {
                if (preferenceHelper.getLang() == "vi") {
                    val node = field4s.item(0)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element
                        tvMoney.text = getValue("Caption", element2)
                        tvMoneyValue.text = getValue("Data", element2)
                        tvMoneyValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                } else {
                    val node = field4s.item(1)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element
                        tvMoney.text = getValue("Caption", element2)
                        tvMoneyValue.text = getValue("Data", element2)
                        tvMoneyValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                }
            } else {
                ln_money.visibility = View.GONE
            }
            val field5s = doc.getElementsByTagName("Field5")
            if (field5s.getLength() > 1) {
                if (preferenceHelper.getLang() == "vi") {
                    val node = field5s.item(0)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element
                        tvTime.text = getValue("Caption", element2)
                        tvTimeValue.text = getValue("Data", element2)
                        tvTimeValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                } else {
                    val node = field5s.item(1)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element
                        tvTime.text = getValue("Caption", element2)
                        tvTimeValue.text = getValue("Data", element2)
                        tvTimeValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    private fun getValue(tag: String, element: Element): String {
        val nodeList = element.getElementsByTagName(tag).item(0).getChildNodes()
        val node = nodeList.item(0)
        return node.getNodeValue()
    }

    private fun getAllData(context: Context): CompleteEntity? {
        val fileName = getString(R.string.file_name)//"EXAMPLE_NAME"// make sure always remember the file name

        val fileSystem = FileSystem()
        try {
            val completeEntity = fileSystem.getAccountsFromFile(fileName, context)
            return completeEntity
        } catch (e: Exception) {

            return null
        }

    }

    internal inner class getTokenProcess : AsyncTask<Int, Void, String?>() {

        override fun doInBackground(vararg params: Int?): String? {
            var result: String? = ""
            try {
                result = getTransactionDetail()
            } catch (e: CentagateException) {
                return e.errorCode.toString()
            } catch (e: Exception) {
                return "123"
            }

            return ""
        }

        var progressDialog: ProgressDialog? = null


        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(this@TransactionDetailActivity)
            progressDialog!!.setTitle("")
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        }

        override fun onProgressUpdate(vararg values: Void) {
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(param: String?) {
            progressDialog!!.dismiss()
            parser(dataExtra)
        }
    }


    @OnClick(R.id.btn_submit)
    fun onSubmitClick() {
        val notificationIntent = LoginFragment.newIntent(applicationContext, "otp_advance")
        startActivityForResult(notificationIntent, 1)
    }

    @OnClick(R.id.btn_cancel)
    fun onCancelClick() {
//        val intent = Intent(this, OtpAdvanceActivity::class.java)
//        startActivity(intent)
        finish()
    }

    @OnClick(R.id.img_btn_back)
    fun onBackClick() {
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            val intent = Intent(this, OtpAdvanceActivity::class.java)
            intent.putExtra("data", dataExtra)
            intent.putExtra("randomString", randomString)
            startActivity(intent)
            finish()
        }
    }

}