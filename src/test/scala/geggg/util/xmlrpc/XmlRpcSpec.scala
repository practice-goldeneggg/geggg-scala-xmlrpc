package geggg.util
package xmlrpc

import org.scalatest.BeforeAndAfterAll
import org.scalatest.FunSpec
import org.scalatest.Matchers._

class XmlRpcSpec extends FunSpec with BeforeAndAfterAll {

  val PORT = 29999
  val URL = "http://127.0.0.1:" + PORT
  val ENCODE_NONDEFAULT = "Shift_JIS"

  val client= XmlRpc(URL)
  val clientWithEnc = XmlRpc(URL, ENCODE_NONDEFAULT)
  val server = MockXmlRpcServer(PORT)

  override def beforeAll {
    server.start
  }

  override def afterAll {
    server.shutdown
  }

  describe("Instanciate") {
    describe("when valid") {
      it("should execute correctly") {
        client shouldBe a [DefaultXmlRpc]
        client.baseUrl shouldEqual URL
        client.encoding shouldEqual "UTF-8"
        clientWithEnc shouldBe a [DefaultXmlRpc]
        clientWithEnc.encoding shouldEqual ENCODE_NONDEFAULT
      }
    }

    describe("when invalid") {
      it("should be error by empty url") {
        intercept[Exception] {
          val c = XmlRpc("")
          fail("empty url cannot be blank")
        }
      }
      it("should be error by empty encoding") {
        intercept[Exception] {
          val c = XmlRpc(URL, "")
          fail("empty encoding cannot be blank")
        }
      }
    }

  }

  describe("Method execute using simpleCall") {
    describe("when handler is CalcHandler") {
      it("should return correct Int value 'add' method") {
        client.simpleCall[Int]("CalcHandler.add", 1, 2) shouldEqual 3
      }
      it("should return correct Int value 'subtract' method") {
        client.simpleCall[Int]("CalcHandler.subtract", 1, 2) shouldEqual -1
      }
      it("should return correct Double value 'subtract' method") {
        client.simpleCall[Double]("CalcHandler.divide", 1, 2) shouldEqual 0.5
      }
    }

    describe("when handler is StringHandler") {
      it("should return correct String value 'concat' method") {
        client.simpleCall[String]("StringHandler.concat", "a", "b") shouldEqual "ab"
      }
      it("should return correct Boolean value 'length' method") {
        client.simpleCall[Boolean]("StringHandler.compare", "あ", "い") shouldEqual false
        client.simpleCall[Boolean]("StringHandler.compare", "あ", "あ") shouldEqual true
        clientWithEnc.simpleCall[Boolean]("StringHandler.compare", "あ", "あ") shouldEqual true
      }
    }

    describe("when handler is CollectionHandler") {
      it("should return correct Map value 'toMap' method") {
        client
          .simpleCall[Map[String, String]]("CollectionHandler.toMap", "ア", "イ") shouldEqual Map("ア" -> "イ")
        clientWithEnc
          .simpleCall[Map[String, String]]("CollectionHandler.toMap", "ア", "イ") shouldEqual Map("ア" -> "イ")
      }
      it("should return correct List value 'listReverse' method") {
        client
          .simpleCall[List[Int]]("CollectionHandler.listReverse", List(1, 2, 3)) shouldEqual List(3, 2, 1)
      }
    }
  }

  describe("Method execute using call") {
    // TODO
  }
}
