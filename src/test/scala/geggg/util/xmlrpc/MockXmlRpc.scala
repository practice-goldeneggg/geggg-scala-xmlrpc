package geggg.util
package xmlrpc

import org.apache.xmlrpc.server._
import org.apache.xmlrpc.webserver._

class MockXmlRpcServer(private val port: Int) {

  var webServer: WebServer = _

  def start() {
    webServer = new WebServer(port)
    val xmlRpcServer = webServer.getXmlRpcServer

    val phm = new PropertyHandlerMapping
    phm.load(Thread.currentThread.getContextClassLoader,
             "handlers.properties")
    xmlRpcServer.setHandlerMapping(phm)

    val serverConfig =
      xmlRpcServer
      .getConfig
      .asInstanceOf[XmlRpcServerConfigImpl]
    serverConfig.setEnabledForExtensions(true)
    serverConfig.setContentLengthOptional(false)

    webServer.start()
  }

  def shutdown() {
    webServer.shutdown
  }

}

object MockXmlRpcServer {

  def apply(port: Int) = new MockXmlRpcServer(port)

}

class CalcHandler {

  def add(i1: Int, i2: Int): Int = i1 + i2
  def subtract(i1: Int, i2: Int): Int = i1 - i2
  def divide(i1: Int, i2: Int): Double = i1.toDouble / i2.toDouble

}

class StringHandler {

  def concat(s1: String, s2: String): String = s1 + s2
  def compare(s1: String, s2: String): Boolean = s1 equals s2

}

class CollectionHandler {

  def toMap(key: String, value: Any): Map[String, Any] = Map(key -> value)
  def listReverse(list: List[Int]): List[Int] = list.reverse

}
