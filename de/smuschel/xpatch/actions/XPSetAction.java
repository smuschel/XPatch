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
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Attr;

/**
 * @author Simon
 *
 */
public class XPSetAction implements IXPatchAction
{
	static Logger logger = Logger.getLogger(XPSetAction.class);
	Element setAction;
	
	public XPSetAction ( Element setAction )
	{
		this.setAction = setAction;
	}
	
	/* (non-Javadoc)
	 * @see de.smuschel.xi.xpatch.actions.IXPatchAction#execute(org.w3c.dom.Element)
	 */
	public boolean execute ( Element rootNode )
	{
		NamedNodeMap attributes = setAction.getAttributes ();
		for ( int i = 0; i < attributes.getLength (); i++ )
		{
			Attr a = ( Attr ) attributes.item ( i );
			rootNode.setAttribute ( a.getName (), a.getValue () );
			logger.debug ( "Setting attribute " + a.getName () + ":" + a.getValue () );
		}
		return true;		
	}

}
