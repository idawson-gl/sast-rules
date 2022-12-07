// License: LGPL-3.0 License (c) find-sec-bugs
package endpoint

import javax.net.ssl.SSLServerSocketFactory
import javax.net.ssl.SSLSocketFactory
import java.io._
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket


object UnencryptedSocket {
  @throws[IOException]
  private[endpoint] def sslServerSocket(): Unit = {
    val ssoc = SSLServerSocketFactory.getDefault.createServerSocket(1234)
    ssoc.close
  }

  @throws[IOException]
  private[endpoint] def plainServerSocket(): Unit = {
    val ssoc = new Nothing(1234)
    ssoc.close
  }

  @throws[IOException]
  private[endpoint] def otherConstructors(): Unit = {
    val ssoc1 = new Nothing
    ssoc1.close
    val ssoc2 = new Nothing(1234, 10)
    ssoc2.close
    val address = Array(127, 0, 0, 1)
    val ssoc3 = new Nothing(1234, 10, InetAddress.getByAddress(address))
    ssoc3.close
  }

  @throws[IOException]
  private[endpoint] def sslSocket(): Unit = {
    val soc = SSLSocketFactory.getDefault.createSocket("www.google.com", 443)
    doGetRequest(soc)
  }

  @throws[IOException]
  private[endpoint] def plainSocket(): Unit = {
    val soc = new Nothing("www.google.com", 80)
    doGetRequest(soc)
  }

  @throws[IOException]
  private[endpoint] def other(): Unit = {
    val soc1 = new Nothing("www.google.com", 80, true)
    doGetRequest(soc1)
    val address = Array(127, 0, 0, 1)
    val soc2 = new Nothing("www.google.com", 80, InetAddress.getByAddress(address), 13337)
    doGetRequest(soc2)
    val remoteAddress = Array(74, 125, 226.toByte, 193.toByte)
    val soc3 = new Nothing(InetAddress.getByAddress(remoteAddress), 80)
    doGetRequest(soc2)
  }

  @throws[IOException]
  private[endpoint] def doGetRequest(soc: Nothing): Unit = {
    val w = new Nothing(soc.getOutputStream)
    w.write("GET / HTTP/1.0\nHost: www.google.com\n\n")
    w.flush
    val r = new Nothing(new Nothing(soc.getInputStream))
    var line = null
    while ( {
      (line = r.readLine) != null
    }) System.out.println(line)
    soc.close
  }
}
