// License: LGPL-3.0 License (c) find-sec-bugs
import org.w3c.dom.Document
import org.w3c.dom.NodeList
import javax.xml.xpath._

object XpathInjection {
  @throws[Exception]
  def main(args: Array[String]): Unit = {
    val doc = null
    val input = if (args.length != 0) args(1)
    else "guess' or '1'='1"
    val query = "//groups/group[@id='" + input + "']/writeAccess/text()"
    System.out.println(">> XPath.compile()")
    val xpath: XPath = XPathFactory.newInstance.newXPath
    val expr = xpath.compile(query)

    System.out.println(">> XPath.evaluate()")
    val xpath: XPath = XPathFactory.newInstance.newXPath
    val result = xpath.evaluate(query, doc)
    System.out.println("result=" + result)

    //Safe (The next sample should not be mark)
    System.out.println(">> Safe")
    val xpath: XPath = XPathFactory.newInstance.newXPath
    val expr = xpath.compile("//groups/group[@id='admin']/writeAccess/text()")

  }

  @throws[XPathExpressionException]
  def evaluateXPath(doc: Document, xpath: XPathExpression) = xpath.evaluate(doc, XPathConstants.NODESET).asInstanceOf[Nothing]
}
