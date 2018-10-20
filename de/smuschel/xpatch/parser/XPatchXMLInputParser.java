/**
 	This file is part of XPatch.

	XPatch is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.
	
	XPatch is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	
	You should have received a copy of the GNU General Public License
	along with XPatch.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.smuschel.xpatch.parser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Simon
 *
 */
public class XPatchXMLInputParser implements IXPatchParser
{
	private Document inputDocument;
	private File inputFile;
	
	public XPatchXMLInputParser ()
	{
		inputFile = new File ( "input.xml" );
	}
	
	public XPatchXMLInputParser ( String inputFile )
	{
		this.inputFile = new File ( inputFile );
	}
	
	public XPatchXMLInputParser ( File inputFile )
	{
		this.inputFile = inputFile;
	}
	
	/* (non-Javadoc)
	 * @see de.smuschel.xi.xpatch.parser.IXPatchParser#parse()
	 */
	public boolean parse ( ) throws ParserConfigurationException, IOException,
			SAXException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		inputDocument = db.parse ( inputFile );
		
		return true;
	}
	
	public Element getSelectedNode ( String path )
		throws XPathExpressionException
	{
		XPathFactory factory = XPathFactory.newInstance();
    javax.xml.xpath.XPath xpath = factory.newXPath();
    XPathExpression expr = xpath.compile(path);

    Object result = expr.evaluate( inputDocument, XPathConstants.NODE );

		return ( Element ) result;
	}
	
	public NodeList getSelectedNodes ( String path )
		throws XPathExpressionException
	{
		XPathFactory factory = XPathFactory.newInstance();
	  javax.xml.xpath.XPath xpath = factory.newXPath();
	  XPathExpression expr = xpath.compile(path);
	
	  Object result = expr.evaluate( inputDocument, XPathConstants.NODESET );
	
		return ( NodeList ) result;
	}

	public void save ( String path )
	{
		try
		{
			
			FileOutputStream bout = new FileOutputStream ( path );
			DOMSource source = new DOMSource ( inputDocument );
			StreamResult result = new StreamResult ( new OutputStreamWriter ( bout, "utf-16" ) );
			TransformerFactory tf = TransformerFactory.newInstance();
			tf.setAttribute("indent-number", new Integer(2));
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			serializer.transform( source, result );
		}
		catch ( Exception e) 
		{
			e.printStackTrace ();
		}
	}
}
