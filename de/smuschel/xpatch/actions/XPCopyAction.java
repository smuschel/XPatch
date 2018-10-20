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
package de.smuschel.xpatch.actions;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import de.smuschel.xpatch.XPatchXMLConstants;

/**
 * @author Simon
 * 
 */
public class XPCopyAction implements IXPatchAction
{

	static Logger logger = Logger.getLogger ( XPCopyAction.class );

	Element copyAction;

	public XPCopyAction ( Element copyAction )
	{
		this.copyAction = copyAction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.smuschel.xi.xpatch.actions.IXPatchAction#execute(org.w3c.dom.Element)
	 */
	public boolean execute ( Element rootNode )
	{
		try
		{
			logger.debug ( "Selecting destination-node: " + copyAction.getAttribute ( XPatchXMLConstants.destinationNode ) );
			Element targetElement = getSelectedNode ( rootNode, copyAction.getAttribute ( XPatchXMLConstants.destinationNode ) );
			
			Node copy = rootNode.cloneNode ( true );
			targetElement.appendChild ( copy );
		}
		catch ( Exception e )
		{
			logger.error ( e );
		}
		return false;
	}

	private Element getSelectedNode ( Element rootNode, String path )
			throws XPathExpressionException
	{
		XPathFactory factory = XPathFactory.newInstance ();
		javax.xml.xpath.XPath xpath = factory.newXPath ();
		XPathExpression expr = xpath.compile ( path );

		Object result = expr.evaluate ( rootNode.getOwnerDocument (), XPathConstants.NODE );

		return ( Element ) result;
	}

}
