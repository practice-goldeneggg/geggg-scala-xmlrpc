name := "geggg-scala-xmlrpc"

version := "0.1"

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
    "junit" % "junit" % "4.11" % "test"
    ,"org.scalatest" %% "scalatest" % "2.1.3" % "test"
    ,"org.apache.xmlrpc" % "xmlrpc-dist" % "3.1.3" // http://ws.apache.org/xmlrpc/client.html"
)
