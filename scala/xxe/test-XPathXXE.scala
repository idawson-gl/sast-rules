// License: LGPL-3.0 License (c) find-sec-bugs
import org.xml.sax.InputSource
import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathExpression
import javax.xml.xpath.XPathFactory


object XPathXXE {
  @throws[Exception]
  def main(args: Array[String]): Unit = {
    unsafe1(args(0))
    unsafe2(args(0))
    unsafe3(args(0))
  }

  @throws[Exception]
  def unsafe1(str: String): Unit = {
    val df = DocumentBuilderFactory.newInstance
    df.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "")
    df.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "")
    val builder = df.newDocumentBuilder
    val xPathFactory = XPathFactory.newInstance
    val xpath = xPathFactory.newXPath
    val xPathExpr = xpath.compile("/xmlhell/text()")
    val result = xPathExpr.evaluate(builder.parse(str))
  }

  @throws[Exception]
  def unsafe2(str: String): Unit = {
    val df = DocumentBuilderFactory.newInstance
    //df.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    //df.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
    val builder = df.newDocumentBuilder
    val xPathFactory = XPathFactory.newInstance
    val xpath = xPathFactory.newXPath
    val xPathExpr = xpath.compile("/xmlhell/text()")
    val result = xPathExpr.evaluate(new InputSource(str))
  }

  @throws[Exception]
  def unsafe3(str: String): Unit = {
    val df = DocumentBuilderFactory.newInstance
    val builder = df.newDocumentBuilder
    val xPathFactory = XPathFactory.newInstance
    val xpath = xPathFactory.newXPath
    val xPathExpr = xpath.compile("/xmlhell/text()")
    xPathExpr.evaluate(new InputSource(str), null)
  }
}
