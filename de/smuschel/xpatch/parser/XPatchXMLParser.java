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

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Simon
 *
 */
public class XPatchXMLParser implements IXPatchParser
{
	Document patchDocument;
	NodeList patchBlocks;
	File patchFile;
	int patchBlockIndex;
	
	public XPatchXMLParser ()
	{
		patchFile = new File ( "patch.xml" );
	}
	
	public XPatchXMLParser ( String patchFile )
	{
		this.patchFile = new File ( patchFile );
	}
	
	public XPatchXMLParser ( File patchFile )
	{
		this.patchFile = patchFile;
	}
	
	/* (non-Javadoc)
	 * @see de.smuschel.xi.xpatch.parser.IXPatchParser#parse(java.io.File)
	 */
	public boolean parse ()
		throws ParserConfigurationException, IOException, SAXException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		patchDocument = db.parse ( patchFile );
		patchBlocks = patchDocument.getElementsByTagName ( "SELECT" );
		return true;
	}

	public Element getPatchBlock ()
		throws NullPointerException
	{
		return ( Element ) patchBlocks.item ( patchBlockIndex++ );
	}
}
