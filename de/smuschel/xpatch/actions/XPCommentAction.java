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

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;


import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Simon
 *
 */
public class XPCommentAction implements IXPatchAction
{
	static Logger logger = Logger.getLogger(XPCommentAction.class);
	Element addAction;
	
	public XPCommentAction ( Element addAction )
	{
		this.addAction = addAction;
	}
	
	/* (non-Javadoc)
	 * @see de.smuschel.xi.xpatch.actions.IXPatchAction#execute(org.w3c.dom.Element)
	 */
	public boolean execute ( Element rootNode )
	{
		try
		{
			logger.debug ( "Creating comment node" );
			Node comment = rootNode.getOwnerDocument ().createComment ( "XPatch commented out\n" );
			rootNode.getParentNode ().appendChild ( comment );
			rootNode.getParentNode ().removeChild ( rootNode );	
			logger.debug ( "Removed original node" );
			//comment.appendChild ( rootNode );
			ByteArrayOutputStream bout = new ByteArrayOutputStream ();
			DOMSource source = new DOMSource ( rootNode );
			StreamResult result = new StreamResult ( new OutputStreamWriter ( bout, "utf-8" ) );
			TransformerFactory tf = TransformerFactory.newInstance();
			//tf.setAttribute("indent-number", new Integer(2));
			Transformer serializer = tf.newTransformer();
			//serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			serializer.transform( source, result );
			comment.setTextContent ( "XPatch commented out:\n" + new String ( bout.toByteArray () ).substring ( 38 ) );
			logger.debug ( "Turned node into comment" );
		}
		catch ( Exception e )
		{
			logger.error ( e );
		}
		
		return true;		
	}
}
