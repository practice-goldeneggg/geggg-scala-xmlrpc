package geggg.util
package xmlrpc

trait XmlRpc {

  val baseUrl: String
  val encoding: String

  require(baseUrl != "")
  require(encoding != "")

  def call[A](method: String, params: Any*): A

  def call[A, B](method: String, params: Any*)(converter: A => B): B

}

object XmlRpc {

  def apply(url: String, enc: String = "UTF-8") = new DefaultXmlRpc(url, enc)

}
