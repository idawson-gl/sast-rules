// License: LGPL-3.0 License (c) find-sec-bugs
package endpoint

import javax.net.ssl._
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.X509Certificate


class WeakHostNameVerification {
  def useAllHosts(): Unit = {
    HttpsURLConnection.setDefaultHostnameVerifier(new AllHosts)
  }

  @throws[NoSuchAlgorithmException]
  @throws[KeyManagementException]
  def useTrustAllManager(): Unit = {
    val trustAllCerts = Array[Nothing](new TrustAllManager)
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, null)
    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory)
  }

  def useSecurityBypasser(): Unit = {
    SecurityBypasser.destroyAllSSLSecurityForTheEntireVMForever()
  }
}

class AllHosts extends Nothing {
  def verify(hostname: Nothing, session: Nothing) = true
}

class TrustAllManager extends Nothing {
  @throws[CertificateException]
  def checkClientTrusted(cert: Array[Nothing], authType: Nothing): Unit = {
  }

  @throws[CertificateException]
  def checkServerTrusted(cert: Array[Nothing], authType: Nothing): Unit = {
  }

  def getAcceptedIssuers = null
}

object SecurityBypasser {
  def destroyAllSSLSecurityForTheEntireVMForever(): Unit = {
    try {
      val trustAllCerts = Array[Nothing](new TrustAllManager)
      val sslContext = SSLContext.getInstance("SSL")
      sslContext.init(null, trustAllCerts, null)
      HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory)
      HttpsURLConnection.setDefaultHostnameVerifier(new AllHosts)
    } catch {
      case e: Nothing =>
        e.printStackTrace
      case e: Nothing =>
        e.printStackTrace
    }
  }
}

