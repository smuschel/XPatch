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
package de.smuschel.xpatch;

/**
 * @author Simon
 *
 */
public interface XPatchXMLConstants
{
	public static final String ADD								= "ADD";
	public static final String SET								= "SET";
	public static final String COPY								= "COPY";
	public static final String COMMENT						= "COMMENT";
	public static final String REMOVE							= "REMOVE";
	
	public static final String destinationNode 		= "destination-node";
	public static final String attribute 					= "attribute";
}
