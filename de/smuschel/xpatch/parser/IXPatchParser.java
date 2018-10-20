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

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import java.io.IOException;

/**
 * @author Simon
 *
 */
public interface IXPatchParser
{
	public boolean parse () throws ParserConfigurationException, IOException, SAXException;
}
