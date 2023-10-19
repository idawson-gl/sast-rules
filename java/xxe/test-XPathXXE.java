// License: LGPL-3.0 License (c) find-sec-bugs
package xxe;

import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class XPathXXE {
    public static void main(String[] args) throws Exception {
        unsafe1(args[0]);
        unsafe2(args[0]);
        unsafe3(args[0]);

    }
    public static void unsafe1(String str) throws Exception {
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        df.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        df.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        DocumentBuilder builder = df.newDocumentBuilder();

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        XPathExpression xPathExpr = xpath.compile("/xmlhell/text()");

        String result = xPathExpr.evaluate( builder.parse(str) );
    }

    public static void unsafe2(String str) throws Exception {
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        //df.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        //df.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        DocumentBuilder builder = df.newDocumentBuilder();

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        XPathExpression xPathExpr = xpath.compile("/xmlhell/text()");

        String result = xPathExpr.evaluate(new InputSource(str));
    }

    public static void unsafe3(String str) throws Exception {
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        //df.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        //df.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        DocumentBuilder builder = df.newDocumentBuilder();

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        XPathExpression xPathExpr = xpath.compile("/xmlhell/text()");

        xPathExpr.evaluate(new InputSource(str), null);
    }
}
