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

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

/**
 * @author Simon
 *
 */
public class XPAddAction implements IXPatchAction
{
	static Logger logger = Logger.getLogger(XPSetAction.class);
	Element addAction;
	
	public XPAddAction ( Element addAction )
	{
		this.addAction = addAction;
	}
	
	/* (non-Javadoc)
	 * @see de.smuschel.xi.xpatch.actions.IXPatchAction#execute(org.w3c.dom.Element)
	 */
	public boolean execute ( Element rootNode )
	{
		NodeList nl = addAction.getChildNodes ();
		Element importElem = null;
		for ( int i = 0; i < nl.getLength (); i++ )
		{
			Node n = nl.item ( i );
			if ( n.getNodeType () == Node.ELEMENT_NODE )
			{
				importElem = ( Element ) n;
				rootNode.appendChild ( rootNode.getOwnerDocument ().importNode ( importElem, true ) );	
			}
		}		
		
		NamedNodeMap attributes = addAction.getAttributes ();
		for ( int i = 0; i < attributes.getLength (); i++ )
		{
			Attr a = ( Attr ) attributes.item ( i );
			if ( !rootNode.hasAttribute ( a.getName () ) )
				rootNode.setAttribute ( a.getName (), a.getValue () );
			else
				logger.debug ( "Attribute exists:" + a.getName () + ", nothing changed" );
		}
		
		return true;		
	}

}
