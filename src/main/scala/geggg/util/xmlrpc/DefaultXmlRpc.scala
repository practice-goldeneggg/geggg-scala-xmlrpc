package geggg.util
package xmlrpc

import java.net.URL
import org.apache.xmlrpc.client.{XmlRpcClientConfigImpl, XmlRpcClient}

class DefaultXmlRpc(val url: String, val enc: String) extends {
  val baseUrl = url
  val encoding = enc
} with XmlRpc {

  private val config = new XmlRpcClientConfigImpl
  config.setServerURL(new URL(baseUrl))
  config.setEncoding(encoding)
  config.setEnabledForExtensions(true)

  private val client = new XmlRpcClient
  client.setConfig(config)

  implicit def anys2objects(anys: Seq[Any]) =
    anys.map { _.asInstanceOf[Object] }.toArray

  def simpleCall[A](method: String, params: Any*): A = {
    client.execute(method, params).asInstanceOf[A]
  }

  def call[A, B](method: String, params: Any*)(converter: A => B): B =
    converter(simpleCall(method, params: _*))

}
