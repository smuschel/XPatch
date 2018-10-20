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
import de.smuschel.xpatch.XPatchXMLConstants;

/**
 * @author Simon
 *
 */
public class XPActionFactory
{
	static Logger logger = Logger.getLogger(XPActionFactory.class);
	
	public static IXPatchAction getAction ( Element action )
	{
		String name = action.getNodeName ();
		logger.info ( "Creating action:" + name );
		if ( name.equals ( XPatchXMLConstants.SET ) )
			return new XPSetAction ( action );
		else
		if ( name.equals ( XPatchXMLConstants.ADD ) )
			return new XPAddAction ( action );
		else
		if ( name.equals ( XPatchXMLConstants.COMMENT ) )
			return new XPCommentAction ( action );
		else
		if ( name.equals ( XPatchXMLConstants.COPY ) )
			return new XPCopyAction ( action );
		else
			if ( name.equals ( XPatchXMLConstants.REMOVE ) )
				return new XPRemoveAction ( action );
		
		logger.error ( "Action [" + name + "] not available" );
		return null;
	}
}
