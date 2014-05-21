# geggg-scala-xmlrpc
This is XML-RPC client library for Scala.

## Usage Example

```scala
object Hoge {

  def main(args: Array[String]) {
    val xmlrpc = XmlRpc("[ENTRY POINT URL]", "[ENCODING = UTF-8]")

    // execute XML-RPC method "getName" using call
    // (Type parameter indicates type of result)
    val idParam = args(0).toInt
    val name = xmlrpc.call[String]("getName", idParam)
    println(name) // String

    // execute XML-RPC method "getName" using call with converting result
    // (1st type parameter indicates type of original result)
    // (2nd type parameter indicates type of converted result)
    val res = xmlrpc.call[String, ApiResult]("getName", idParam) { result =>  // result is String
      ApiResult(idParam, result)
    }
    println(res.id)
    println(res.name)
  }

}

case class ApiResult(id: Int, name: String)
