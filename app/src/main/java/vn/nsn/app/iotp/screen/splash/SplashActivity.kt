package vn.nsn.app.iotp.screen.splash

//import com.centagate.module.common.Configuration
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.text.TextUtils
import android.util.Base64
import com.centagate.module.common.CompleteEntity
import com.centagate.module.common.Configuration
import com.centagate.module.device.FileSystem
import com.centagate.module.device.PinAuthentication
import com.centagate.module.exception.CentagateException
import com.centagate.module.otp.OtpService
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import vn.nsn.app.iotp.R
import vn.nsn.app.iotp.mvp.MvpActivity
import vn.nsn.app.iotp.screen.LoginActivity
import vn.nsn.app.iotp.screen.main.MainActivity
import vn.nsn.app.iotp.screen.selectlanguage.SelectLanguageActivity
import vn.nsn.app.iotp.util.showRetryDialog
import java.io.ByteArrayInputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit

class SplashActivity : MvpActivity<SplashPresenterContract>(), SplashViewContract {
    private val timeout = 120 //in second, timeout of request

    override val layoutResId: Int
        get() = R.layout.activity_splash

    private val showPeepSubject: PublishSubject<Any> = PublishSubject.create()

    override fun initPresenter() {
        presenter = SplashPresenter(this)
    }

    override fun attachViewToPresenter() {
        presenter.attachView(this)
    }

    override fun showError(message: String) {
        showRetryDialog(this) {
        }
    }

    override fun setupViews() {
        Fabric.with(this, Crashlytics());

        initSDK()

//        val completeEntity = getAllData(applicationContext)
//        if (completeEntity != null) {
//            ActivateProcess().execute()
//        } else {
            startCount()
//        }
    }

    fun startCount() {
        compositeDisposable.add(showPeepSubject.debounce(2000, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    gotoNext()
                })
        showPeepSubject.onNext(true)

    }

    internal inner class ActivateProcess : AsyncTask<Int, Void, Int>() {
        override fun doInBackground(vararg params: Int?): Int {
            var result: Boolean? = false
            try {
                result = activateSample()
            } catch (e: CentagateException) {
                return e.errorCode
            } catch (e: Exception) {
                return 123
            }

            return 1
        }

        var progressDialog: ProgressDialog? = null


        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(this@SplashActivity)
            progressDialog!!.setTitle("")
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        }

        override fun onProgressUpdate(vararg values: Void) {
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(param: Int?) {
            progressDialog!!.dismiss()
            startCount()
        }
    }

    @SuppressLint("HardwareIds")
    @Throws(Exception::class)
    fun activateSample(): Boolean? {

        var result: Boolean? = false

        //this will be the security key of every important data in the SDK
        val securityDevice = PinAuthentication()
        //set Pin or Password
        securityDevice.setPin(preferenceHelper.getPincode())


        try {
            val completeEntity = getAllData(applicationContext)
            if (completeEntity != null) {
                if (completeEntity.accounts != null && completeEntity.accounts[0] != null) {
                    var account = completeEntity.accounts[0]
                    val otpService = OtpService()
                    val synchronizeStatus = otpService.syncOtp(Build.SERIAL, account, securityDevice)
                }
            }

            result = true


        } catch (e: Exception) {

            println("-----------------------" + e.message)
            e.printStackTrace()
            throw e
        }

        return result
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

    private fun initSDK() {
        //Setup the initialize of SDK
        Configuration.getInstance().webServiceUrl = applicationContext.resources.getString(R.string.app_default_web_service_url)
        Configuration.getInstance().approvalUrl = applicationContext.resources.getString(R.string.app_default_soft_cert_url)
        Configuration.getInstance().timeout = timeout
        Configuration.getInstance().serverEncPublicKey = applicationContext.resources.getString(R.string.app_default_server_enc_public_key)
        Configuration.getInstance().serverSignPublicKey = applicationContext.resources.getString(R.string.app_default_server_verify_public_key)

        val rootCertBase64 = "MIIEfTCCA2WgAwIBAgIDG+cVMA0GCSqGSIb3DQEBCwUAMGMxCzAJBgNVBAYTAlVTMSEwHwYDVQQKExhUaGUgR28gRGFkZHkgR3JvdXAsIEluYy4xMTAvBgNVBAsTKEdvIERhZGR5IENsYXNzIDIgQ2VydGlmaWNhdGlvbiBBdXRob3JpdHkwHhcNMTQwMTAxMDcwMDAwWhcNMzEwNTMwMDcwMDAwWjCBgzELMAkGA1UEBhMCVVMxEDAOBgNVBAgTB0FyaXpvbmExEzARBgNVBAcTClNjb3R0c2RhbGUxGjAYBgNVBAoTEUdvRGFkZHkuY29tLCBJbmMuMTEwLwYDVQQDEyhHbyBEYWRkeSBSb290IENlcnRpZmljYXRlIEF1dGhvcml0eSAtIEcyMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv3FiCPH6WTT3G8kYo/eASVjpIoMTpsUgQwE7hPHmhUmfJ+r2hBtOoLTbcJjHMgGxBT4HTu70+k8vWTAi56sZVmvigAf88xZ1gDlRe+X5NbZ0TqmNghPktj+pA4P6or6KFWp/3gvDthkUBcrqw6gElDtGfDIN8wBmIsiNaW02jBEYt9OyHGC0OPoCjM7T3UYH3go+6118yHz7sCtTpJJiaVElBWEaRIGMLKlDliPfrDqBmg4pxRyp6V0etp6eMAo5zvGIgPtLXcwy7IViQyU0AlYnAZG0O3AqP26x6JyIAX2f1PnbU21gnb8s51iruF9G/M7EGwM8CetJMVxpRrPgRwIDAQABo4IBFzCCARMwDwYDVR0TAQH/BAUwAwEB/zAOBgNVHQ8BAf8EBAMCAQYwHQYDVR0OBBYEFDqahQcQZyi27/a9BUFuIMGU2g/eMB8GA1UdIwQYMBaAFNLEsNKR1EwRcbNhyz2h/t2oatTjMDQGCCsGAQUFBwEBBCgwJjAkBggrBgEFBQcwAYYYaHR0cDovL29jc3AuZ29kYWRkeS5jb20vMDIGA1UdHwQrMCkwJ6AloCOGIWh0dHA6Ly9jcmwuZ29kYWRkeS5jb20vZ2Ryb290LmNybDBGBgNVHSAEPzA9MDsGBFUdIAAwMzAxBggrBgEFBQcCARYlaHR0cHM6Ly9jZXJ0cy5nb2RhZGR5LmNvbS9yZXBvc2l0b3J5LzANBgkqhkiG9w0BAQsFAAOCAQEAWQtTvZKGEacke+1bMc8dH2xwxbhuvk679r6XUOEwf7ooXGKUwuN+M/f7QnaF25UcjCJYdQkMiGVnOQoWCcWgOJekxSOTP7QYpgEGRJHjp2kntFolfzq3Ms3dhP8qOCkzpN1nsoX+oYggHFCJyNwq9kIDN0zmiN/VryTyscPfzLXs4Jlet0lUIDyUGAzHHFIYSaRt4bNYC8nY7NmuHDKOKHAN4v6mF56ED71XcLNa6R+ghlO773z/aQvgSMO3kwvIClTErF0UZzdsyqUvMQg3qm5vjLyb4lddJIGvl5echK1srDdMZvNhkREg5L4wn3qkKQmw4TRfZHcYQFHfjDCmrw=="
        val intermediateCertBase64 = "MIIE0DCCA7igAwIBAgIBBzANBgkqhkiG9w0BAQsFADCBgzELMAkGA1UEBhMCVVMxEDAOBgNVBAgTB0FyaXpvbmExEzARBgNVBAcTClNjb3R0c2RhbGUxGjAYBgNVBAoTEUdvRGFkZHkuY29tLCBJbmMuMTEwLwYDVQQDEyhHbyBEYWRkeSBSb290IENlcnRpZmljYXRlIEF1dGhvcml0eSAtIEcyMB4XDTExMDUwMzA3MDAwMFoXDTMxMDUwMzA3MDAwMFowgbQxCzAJBgNVBAYTAlVTMRAwDgYDVQQIEwdBcml6b25hMRMwEQYDVQQHEwpTY290dHNkYWxlMRowGAYDVQQKExFHb0RhZGR5LmNvbSwgSW5jLjEtMCsGA1UECxMkaHR0cDovL2NlcnRzLmdvZGFkZHkuY29tL3JlcG9zaXRvcnkvMTMwMQYDVQQDEypHbyBEYWRkeSBTZWN1cmUgQ2VydGlmaWNhdGUgQXV0aG9yaXR5IC0gRzIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC54MsQ1K92vdSTYuswZLiBCGzDBNliF44v/z5lz4/OYuY8UhzaFkVLVat4a2ODYpDOD2lsmcgaFItMzEUz6ojcnqOvK/6AYZ15V8TPLvQ/MDxdR/yaFrzDN5ZBUY4RS1T4KL7QjL7wMDge87Am+GZHY23ecSZHjzhHU9FGHbTj3ADqRay9vHHZqm8A29vNMDp5T19MR/gd71vCxJ1gO7GyQ5HYpDNO6rPWJ0+tJYqlxvTV0KaudAVkV4i1RFXULSo6Pvi4vekyCgKUZMQWOlDxSq7neTOvDCAHf+jfBDnCaQJsY1L6d8EbyHSHyLmTGFBUNUtpTrw700kuH9zB0lL7AgMBAAGjggEaMIIBFjAPBgNVHRMBAf8EBTADAQH/MA4GA1UdDwEB/wQEAwIBBjAdBgNVHQ4EFgQUQMK9J47MNIMwojPX+2yz8LQsgM4wHwYDVR0jBBgwFoAUOpqFBxBnKLbv9r0FQW4gwZTaD94wNAYIKwYBBQUHAQEEKDAmMCQGCCsGAQUFBzABhhhodHRwOi8vb2NzcC5nb2RhZGR5LmNvbS8wNQYDVR0fBC4wLDAqoCigJoYkaHR0cDovL2NybC5nb2RhZGR5LmNvbS9nZHJvb3QtZzIuY3JsMEYGA1UdIAQ/MD0wOwYEVR0gADAzMDEGCCsGAQUFBwIBFiVodHRwczovL2NlcnRzLmdvZGFkZHkuY29tL3JlcG9zaXRvcnkvMA0GCSqGSIb3DQEBCwUAA4IBAQAIfmyTEMg4uJapkEv/oV9PBO9sPpyIBslQj6Zz91cxG7685C/b+LrTW+C05+Z5Yg4MotdqY3MxtfWoSKQ7CC2iXZDXtHwlTxFWMMS2RJ17LJ3lXubvDGGqv+QqG+6EnriDfcFDzkSnE3ANkR/0yBOtg2DZ2HKocyQetawiDsoXiWJYRBuriSUBAA/NxBti21G00w9RKpv0vHP8ds42pM3Z2Czqrpv1KrKQ0U11GIo/ikGQI31bS/6kA1ibRrLDYGCD+H1QQc7CoZDDu+8CL9IVVO5EFdkKrqeKM+2xLXY2JtwE65/3YR8V3Idv7kaWKK2hJn0KCacuBKONvPi8BDAB"
        val abbCertBase64 = "MIIFojCCBIqgAwIBAgIMZuITy/3QHViWSHSsMA0GCSqGSIb3DQEBCwUAMGYxCzAJBgNVBAYTAkJFMRkwFwYDVQQKExBHbG9iYWxTaWduIG52LXNhMTwwOgYDVQQDEzNHbG9iYWxTaWduIE9yZ2FuaXphdGlvbiBWYWxpZGF0aW9uIENBIC0gU0hBMjU2IC0gRzIwHhcNMTcwNDAzMDk1ODQ3WhcNMTkwNTE1MDc0NjU3WjCBlTELMAkGA1UEBhMCVk4xFDASBgNVBAgMC0hvIENoaSBNaW5oMRQwEgYDVQQHDAtIbyBDaGkgTWluaDEWMBQGA1UECwwNSVQgRGVwYXJ0bWVudDEsMCoGA1UECgwjQU4gQklOSCBDT01NRVJDSUFMIEpPSU5UIFNUT0NLIEJBTksxFDASBgNVBAMMCyouYWJiYW5rLnZuMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAunfSPe6JJ8dS4ubk+m9aREUWkgc1xwvHkPg8VtRBswxLlR0o9c9laPyX/xqW2wG0ZMp8PAYq8FW5SusBBGfPiQarfbU1K8HeHdBXynhYbSDoISB8LvvHeJDoAlrFok8HfYdlKWGnRwM37FIyh0Uezt2pStFXj74ygWEFEST8Un8F+YTE2pvCosch2+xxhiNRdgP48OUz47cBWfoAj+P2NMxgFzFmKT4OyIo1sLgSRcK5VFQ5QoljbdrJZvAPBr+qta3Jfh2UvvDvlmmCcQ5Rk9StV7sTyKw0wQk9Ptb3llZ4axCbLzwoJ6V80zzMVFgE6z0sbUbUqdqPWoa908dhTQIDAQABo4ICHjCCAhowDgYDVR0PAQH/BAQDAgWgMIGgBggrBgEFBQcBAQSBkzCBkDBNBggrBgEFBQcwAoZBaHR0cDovL3NlY3VyZS5nbG9iYWxzaWduLmNvbS9jYWNlcnQvZ3Nvcmdhbml6YXRpb252YWxzaGEyZzJyMS5jcnQwPwYIKwYBBQUHMAGGM2h0dHA6Ly9vY3NwMi5nbG9iYWxzaWduLmNvbS9nc29yZ2FuaXphdGlvbnZhbHNoYTJnMjBWBgNVHSAETzBNMEEGCSsGAQQBoDIBFDA0MDIGCCsGAQUFBwIBFiZodHRwczovL3d3dy5nbG9iYWxzaWduLmNvbS9yZXBvc2l0b3J5LzAIBgZngQwBAgIwCQYDVR0TBAIwADBJBgNVHR8EQjBAMD6gPKA6hjhodHRwOi8vY3JsLmdsb2JhbHNpZ24uY29tL2dzL2dzb3JnYW5pemF0aW9udmFsc2hhMmcyLmNybDBYBgNVHREEUTBPggsqLmFiYmFuay52boINb3dhLmFiYmFuay52boIObWFpbC5hYmJhbmsudm6CFmF1dG9kaXNjb3Zlci5hYmJhbmsudm6CCWFiYmFuay52bjAdBgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwHQYDVR0OBBYEFLKMUkbkHXS7mlQcYychpy87kQh1MB8GA1UdIwQYMBaAFJbeYfG9HBYpUxzAzH07gwBA5hp8MA0GCSqGSIb3DQEBCwUAA4IBAQAFLnDFt6feWDhnrGbqLAuEanxoa6BEE397Ydiy9qqTtARj56M/V0SIOQI9fIJRX+qxKztxtVSxffvexJtT/YijMRExfq4+VFeLH7lEr55fnIbin7MvL4EZdFCZpK6PcR66Y7Y3oT5w6riByLnLCM2aztLOG2e7HmtUz6dECxRk64ONk4ID7H1AvE/dxfWZ56JY8u7xjEFfxNaGeB1FC8txHzc7QepVPOMUmE5UtK21+KDc6X8M3guKXWcmh08wkTslLkgLWEl5JWCYnCyzLaKIaQQ8FIYfWEhTqAMXXqdv3S9NxJS/hLAfDLRePtfTuwqH3qVwhxjY+eFzHngkaS4D"
        val ocbCertBase64 = "MIIFXzCCBEegAwIBAgISAwBMGaAdQSuChSsLD+ikGpdcMA0GCSqGSIb3DQEBCwUAMEoxCzAJBgNVBAYTAlVTMRYwFAYDVQQKEw1MZXQncyBFbmNyeXB0MSMwIQYDVQQDExpMZXQncyBFbmNyeXB0IEF1dGhvcml0eSBYMzAeFw0xODExMjEwODU3MjZaFw0xOTAyMTkwODU3MjZaMB4xHDAaBgNVBAMMEyoucGh1b25nZG9uZ2Jhbmsudm4wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDe9nZWr2GIGHcxZXk0JpMze6sKkmMmuXT3sXcf91GaEZ8alDqf86QofFU9yU+Sy6Y8woYw9qE2y8hnIZOuDpZFlZMEGl5fhL2P/4VzW8LjfgJnsIZV2wyV6wq7FjjgEL5KVgqnwEl8M2Xsh8ECz9f/SfTfebqy6f9B/R/DYNj4jyHvbQyIMckjfYBuVgQDV0gLIBp/pYzaY5LV//2wp7YKqAB8MZsLhGQhCJDLeX5xvQViceduEfJXqQ+zigTYwXtFmufL0XgpREgBsZ6n+JOnCSPgRUWEKT7IFaROnEsQi3ykOUSGK07iqMl4Ics1eKd5jUi2ssWMhN/EqkNee7R9AgMBAAGjggJpMIICZTAOBgNVHQ8BAf8EBAMCBaAwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMAwGA1UdEwEB/wQCMAAwHQYDVR0OBBYEFHKyL7+kC73WuERmemeyp1dQRsfyMB8GA1UdIwQYMBaAFKhKamMEfd265tE5t6ZFZe/zqOyhMG8GCCsGAQUFBwEBBGMwYTAuBggrBgEFBQcwAYYiaHR0cDovL29jc3AuaW50LXgzLmxldHNlbmNyeXB0Lm9yZzAvBggrBgEFBQcwAoYjaHR0cDovL2NlcnQuaW50LXgzLmxldHNlbmNyeXB0Lm9yZy8wHgYDVR0RBBcwFYITKi5waHVvbmdkb25nYmFuay52bjBMBgNVHSAERTBDMAgGBmeBDAECATA3BgsrBgEEAYLfEwEBATAoMCYGCCsGAQUFBwIBFhpodHRwOi8vY3BzLmxldHNlbmNyeXB0Lm9yZzCCAQUGCisGAQQB1nkCBAIEgfYEgfMA8QB3AOJpS64m6OlACeiGG7Y7g9Q+5/50iPukjyiTAZ3d8dv+AAABZzWz++QAAAQDAEgwRgIhAOnptidWgt4LzOzgAaffttBqj4YwoHIGQmHNuz1axthUAiEAn9vi+JV2Es276Sl2XGQXEvUZRDpl69FGn1q0B0thzQ8AdgApPFGWVMg5ZbqqUPxYB9S3b79Yeily3KTDDPTlRUf0eAAAAWc1s/wxAAAEAwBHMEUCIQChUg3fGzRXI5zBmUuz0Jc1Cam7JtM+b4SxuVSF5744hAIgM1Y8ie/jVzoIR4ziOh02aBG9hhK5oMdK0oZVMGHnUEgwDQYJKoZIhvcNAQELBQADggEBAJWzFMF/zemkfPFUBIfSdsugfxTRt6IDieJwZWWpEUw8r3smVGpHn9mkOva/vSAn/dREVgvZ4llM+J2yahz74jIT8qJiE8GUb9/T5Co8ScuWVGOFlAKp8td1XWSut/Z1kBLVAvhJMcy1ybaz1igyIa90teTY7SIJhUWN9VLdDxrTDKmZitiYAF1NRxZYB8SiO53ySuN7LeRqmVNquYIjbd/CFYT8khqDDA8s7brqeDHPEOR4VJbrWfSOwPxXCzZy4b37kxvDai5J8lIrWzr6uilp0GZQ5eAPqrQr9THpO2iM1chTNJNoQoC6XqqTtRgSaySaISQNkN3JYECx0rkr4Wo="
        val ocbCertBase64_test = "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"
        val testCertBase64 = "MIIFOzCCBCOgAwIBAgIIWiurdYTMOZ4wDQYJKoZIhvcNAQELBQAwgbQxCzAJBgNVBAYTAlVTMRAwDgYDVQQIEwdBcml6b25hMRMwEQYDVQQHEwpTY290dHNkYWxlMRowGAYDVQQKExFHb0RhZGR5LmNvbSwgSW5jLjEtMCsGA1UECxMkaHR0cDovL2NlcnRzLmdvZGFkZHkuY29tL3JlcG9zaXRvcnkvMTMwMQYDVQQDEypHbyBEYWRkeSBTZWN1cmUgQ2VydGlmaWNhdGUgQXV0aG9yaXR5IC0gRzIwHhcNMTcwNjA2MDYxMzAwWhcNMTkwODA1MDcyNjM4WjBAMSEwHwYDVQQLExhEb21haW4gQ29udHJvbCBWYWxpZGF0ZWQxGzAZBgNVBAMMEiouc2VjdXJlbWV0cmljLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAM5HI3XhbDVjPsTxxrjo5BjRycDPvHaSzICtb2kFY1yE6O+C39uiXjCGqKoTuTSm1BpcwnkL2CyFrrBkHhqIoZkeCEF0Fj7JmLtUysFmE4onekYDb/tzGU6jJe/slSn7gvNfGZSfzAkrA7+rJFLDjf+9fHj2BDjZnNmhDSxo6wbzl3LpDFtJdvyaqWsrV1Hb2sJHD/HBifF9HCVQGZP7PLrrrGiLfDYFR4JyqPbPkaNAm/5KEVDlMYf/hfYe1EmgokfBsBCCvx6/sygzCTLLFJGeVMArpztdy/S7m7wvPpw8H7bZdkpbuUD61hSJv6utKap1lXZAgpYVvQnBy/BvZ6kCAwEAAaOCAcIwggG+MAwGA1UdEwEB/wQCMAAwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMA4GA1UdDwEB/wQEAwIFoDA3BgNVHR8EMDAuMCygKqAohiZodHRwOi8vY3JsLmdvZGFkZHkuY29tL2dkaWcyczEtNTQ1LmNybDBdBgNVHSAEVjBUMEgGC2CGSAGG/W0BBxcBMDkwNwYIKwYBBQUHAgEWK2h0dHA6Ly9jZXJ0aWZpY2F0ZXMuZ29kYWRkeS5jb20vcmVwb3NpdG9yeS8wCAYGZ4EMAQIBMHYGCCsGAQUFBwEBBGowaDAkBggrBgEFBQcwAYYYaHR0cDovL29jc3AuZ29kYWRkeS5jb20vMEAGCCsGAQUFBzAChjRodHRwOi8vY2VydGlmaWNhdGVzLmdvZGFkZHkuY29tL3JlcG9zaXRvcnkvZ2RpZzIuY3J0MB8GA1UdIwQYMBaAFEDCvSeOzDSDMKIz1/tss/C0LIDOMC8GA1UdEQQoMCaCEiouc2VjdXJlbWV0cmljLmNvbYIQc2VjdXJlbWV0cmljLmNvbTAdBgNVHQ4EFgQUQfQ7Bz/3gx+srEp+zdVc8G3iDsMwDQYJKoZIhvcNAQELBQADggEBAICLFC4MCez0O+/6aTJuicFi61I/XAojpGIP0fIUzX+N8LawbWHQ3PwV5Hy1CoXTb2xpd7nBse0Pgd+2aCYdOXkJRR37eYU7IrzFnfZeMhaC+a0AQbLsJOjuFAAftayypudqUG6pv9aS6eyCLWfI7PC4kfuGZ+n3UiurX5YhA2cLbqjsKHommhcBFSl/VVWuvamtgNc3ZLE0RYPydFacmwWTmqa1wnvZMN2JiUpxrIioTzh+KuCf5s4KiA/Nr9crdPClXO0QtV4MOnD77sjsWGMoo5g9K2qPz8qJA14Y/dpfPKNa8bF5cgZ40aw7Pfsq7lldBTfqyzvXIvPpF8bG0WY="
        val iotpCert = "MIIHODCCBiCgAwIBAgIMed072OXY7veBdGFJMA0GCSqGSIb3DQEBCwUAMGYxCzAJ" +
                "BgNVBAYTAkJFMRkwFwYDVQQKExBHbG9iYWxTaWduIG52LXNhMTwwOgYDVQQDEzNH" +
                "bG9iYWxTaWduIE9yZ2FuaXphdGlvbiBWYWxpZGF0aW9uIENBIC0gU0hBMjU2IC0g" +
                "RzIwHhcNMTgwNjA4MDE0NzAxWhcNMjAwNzEyMDgzNjEzWjCBkzELMAkGA1UEBhMC" +
                "Vk4xFDASBgNVBAgMC0hvIENoaSBNaW5oMRQwEgYDVQQHDAtIbyBDaGkgTWluaDEU" +
                "MBIGA1UECwwLSVQgRGl2aXNpb24xKzApBgNVBAoMIk9yaWVudCBDb21tZXJjaWFs" +
                "IEpvaW50IFN0b2NrIEJhbmsxFTATBgNVBAMMDCoub2NiLmNvbS52bjCCASIwDQYJ" +
                "KoZIhvcNAQEBBQADggEPADCCAQoCggEBAL62v5jimE1PCwXMRIjOgd7aU+VzJ8Tm" +
                "v41VXUHu0iZGkooCCKfqqCR0VZO6jgzA+D5NXb1Cpo48sNwN/3GhhR8eDXpC3Rjv" +
                "91Ld9rmxTraj6zLbB1V8808oB7rUW17ku3OZNNZT5co2S/asSN4Mn11N3pIC52CJ" +
                "ne+2rUgE4rV4ksk0hjzM5cyMsNX4sY/FXRjs2XXkXQbL1E/vr9MrZ/Jf0qpuSsmf" +
                "+MTRJWUZq2Fi39Ay4pa7OLJuMKGZnc92aP4A8sPc2kjxYbKTxihaLN+lHrIhVyOs" +
                "/NYnWj2RwXKAmtaRGQ6Ob4iVAR6PSNKJv2xxifDaIcAfK6xxm8E9HoMCAwEAAaOC" +
                "A7YwggOyMA4GA1UdDwEB/wQEAwIFoDCBoAYIKwYBBQUHAQEEgZMwgZAwTQYIKwYB" +
                "BQUHMAKGQWh0dHA6Ly9zZWN1cmUuZ2xvYmFsc2lnbi5jb20vY2FjZXJ0L2dzb3Jn" +
                "YW5pemF0aW9udmFsc2hhMmcycjEuY3J0MD8GCCsGAQUFBzABhjNodHRwOi8vb2Nz" +
                "cDIuZ2xvYmFsc2lnbi5jb20vZ3Nvcmdhbml6YXRpb252YWxzaGEyZzIwVgYDVR0g" +
                "BE8wTTBBBgkrBgEEAaAyARQwNDAyBggrBgEFBQcCARYmaHR0cHM6Ly93d3cuZ2xv" +
                "YmFsc2lnbi5jb20vcmVwb3NpdG9yeS8wCAYGZ4EMAQICMAkGA1UdEwQCMAAwSQYD" +
                "VR0fBEIwQDA+oDygOoY4aHR0cDovL2NybC5nbG9iYWxzaWduLmNvbS9ncy9nc29y" +
                "Z2FuaXphdGlvbnZhbHNoYTJnMi5jcmwwbQYDVR0RBGYwZIIMKi5vY2IuY29tLnZu" +
                "ghdhdXRvZGlzY292ZXIub2NiLmNvbS52boIPbWFpbC5vY2IuY29tLnZugg5vd2Eu" +
                "b2NiLmNvbS52boIOd3d3Lm9jYi5jb20udm6CCm9jYi5jb20udm4wHQYDVR0lBBYw" +
                "FAYIKwYBBQUHAwEGCCsGAQUFBwMCMB0GA1UdDgQWBBQzLAvqykB7wlul8SvaMi8m" +
                "bNPKijAfBgNVHSMEGDAWgBSW3mHxvRwWKVMcwMx9O4MAQOYafDCCAX8GCisGAQQB" +
                "1nkCBAIEggFvBIIBawFpAHYAVYHUwhaQNgFK6gubVzxT8MDkOHhwJQgXL6OqHQcT" +
                "0wwAAAFj3RNa5wAABAMARzBFAiBEG5ODHAwzhXO70MkYRguc3HmhxwI3uRvl7sYu" +
                "p4Q6mAIhAJVJ2oHm0rsiGOY6wohG1BrqufJ65qsic0VA+lflhkXcAHcAh3W/51l8" +
                "+IxDmV+9827/Vo1HVjb/SrVgwbTq/16ggw8AAAFj3RNboQAABAMASDBGAiEAmhPI" +
                "dkMkBcDa9ACiwdhOC3pYB1aFt1yayiapFhwWM1gCIQCAJYyaDk4S5q99D2NMnaAn" +
                "fbJYt6lNdgF0sQ/dYlfPLgB2AKS5CZC0GFgUh7sTosxncAo8NZgE+RvfuON3zQ7I" +
                "DdwQAAABY90TXEkAAAQDAEcwRQIhAKtqXNABihA8Ru45sPD+mp6DXVSRXMj2E29y" +
                "WYoEYlk8AiBxkPibFWVjZUG3NZt3ce5cnZah/LNvbOqCLdT7wUyzxzANBgkqhkiG" +
                "9w0BAQsFAAOCAQEACGnA+egYIV+lm2ekww97RIplEADNShKXHXk3BfmvMDtMM36R" +
                "h9ajCQuZLduGIRsWo1a8+DC0tYRsB8Xzw/Nh5z+FT/1i6VgrxF7i1D5GuE1k1dBe" +
                "gjsbRWX1aCzMXs4Iuo9BKd3RScHi/h3cud6hlxEFn2PXRFzppFaSmRW+5fgVJh6i" +
                "HE39ZlOQuh7M+KtaLNIvdYtZTghFIvZl7fhUGODfMxyP46KteJ8P2v4wLCfSl1Ag" +
                "OZg2y8el2ykXBdsuVTVvmgw/XEIdqpUd03E55Hg3AeAEco8+WS0d4RBsxUA+HRwO" +
                "AAZRtz0+0OqcoTqk/FsN+KgqGyo5PRQ2HqR6zA=="
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

            val iotpCert = cf.generateCertificate(ByteArrayInputStream(Base64.decode(iotpCert, Base64.DEFAULT)))
            keyStore.setCertificateEntry("IOTP GOLIVE", iotpCert)

        } catch (e: Exception) {
//            Toast.makeText(this@SplashActivity, "AA" + e.message, Toast.LENGTH_SHORT).show()
//            e.printStackTrace()
        }

        //the certs which are trusted to has connection, will reject if doesn't match with server cert
        Configuration.getInstance().connectionKeyStore = keyStore

        //if want to truested all connection to server
        //Configuration.getInstance().setConnectionKeyStore(null);

    }

    override fun gotoNext() {

        if (TextUtils.isEmpty(preferenceHelper.getPincode())) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }

    }
}