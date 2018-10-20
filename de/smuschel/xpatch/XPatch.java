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

import org.xml.sax.SAXException;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.w3c.dom.*;
import de.smuschel.xpatch.parser.*;
import de.smuschel.xpatch.actions.*;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

/**
 * @author Simon
 *
 */
public class XPatch
{
  static Logger logger = Logger.getLogger(XPatch.class);
  XPatchXMLInputParser inputParser;
  XPatchXMLParser patchParser;
  
  public XPatch ()
  {  	
  }
  
  public boolean patch ( String input, String patch, String output )
  {
  	boolean ret = false;
  	boolean patchOk = false;
  	boolean inputOk = false;
  	
  	logger.debug ( "Creating patch parser" );
  	patchParser = new XPatchXMLParser ( patch );
  	try
  	{
  		logger.debug ( "Attempting to parse patch file" );
  		patchOk = patchParser.parse ();
  	}
  	catch ( IOException io )
  	{
  		logger.error ( io );
  	}
  	catch ( SAXException s )
  	{
  		logger.error ( s );
  	}
  	catch ( ParserConfigurationException pc )
  	{
  		logger.error ( pc );
  	}
  	
  	if ( patchOk )
  	{
  		logger.debug ( "Creating input parser" );
  		inputParser = new XPatchXMLInputParser ( input );
  		try
    	{
    		logger.debug ( "Attempting to parse input file" );
    		inputOk = inputParser.parse ();
    	}
    	catch ( IOException io )
    	{
    		logger.error ( io );
    	}
    	catch ( SAXException s )
    	{
    		logger.error ( s );
    	}
    	catch ( ParserConfigurationException pc )
    	{
    		logger.error ( pc );
    	}
  	}
  	
  	if ( inputOk )
  	{
  		logger.info ( "Patching input file" );
  		Element patchBlock = patchParser.getPatchBlock ();
  		while ( patchBlock != null )
  		{
  			String selectNode = patchBlock.getAttribute ( "node" );
  			logger.debug ( "XPath expression: " + selectNode );
  			try
  			{  				
  				NodeList elementsToPatch = inputParser.getSelectedNodes ( selectNode );
  				for ( int j = 0; j < elementsToPatch.getLength (); j++ )
  				{
  					if ( elementsToPatch.item ( j ).getNodeType () == Node.ELEMENT_NODE )
  					{
	  					Element elementToPatch = ( Element ) elementsToPatch.item ( j );  					
		  				logger.info ( elementToPatch.getNodeName () );  				
		  				logger.debug ( "Element selected" );
		  				logger.debug ( "Executing actions" );
		  				NodeList actions = patchBlock.getChildNodes ();
		  				for ( int i = 0; i < actions.getLength (); i++ )
		  				{
		  					Node n = actions.item ( i );
		  					if ( n.getNodeType () == Node.ELEMENT_NODE )
		  					{
		  						IXPatchAction action = XPActionFactory.getAction ( ( Element ) n );
		  						action.execute ( elementToPatch );
		  						logger.debug ( "Action executed [" + n.getNodeName () + "]" );
		  					}
		  				}
  					}
  				}
  			}
  			catch ( NullPointerException np )
  			{
  				logger.error ( np );
  			}
  			catch ( XPathExpressionException xpe )
  			{
  				logger.error ( xpe );
  			}
  			patchBlock = patchParser.getPatchBlock ();
  		}
  		inputParser.save ( output );
  		logger.info ( "Patch completed" );
  	}
  	
  	return ret;
  }
  
	/**
	 * @param args
	 */
	public static void main ( String [] args )
	{
    BasicConfigurator.configure();
    logger.setLevel ( org.apache.log4j.Level.ERROR );
    logger.info("XPatch 0.0.1alpha");
		if ( args.length == 1 )
		{
			if ( args[0].equals ( "-h") )
			{
				printUsage ();
			}
		}
		else
		if ( args.length == 3 )
		{
			logger.info ( "[INPUT-FILE]\t" + args[0] );
			logger.info ( "[PATCH-FILE]\t" + args[1] );
			logger.info ( "[OUTPUT-FILE]\t" + args[2] );
			
			XPatch patcher = new XPatch ();
			patcher.patch ( args [0], args [1], args [2] );
			
		}
		else
		if ( args.length == 0 )
		{
			XPatch patcher = new XPatch ();
			patcher.patch ( "input.xml", "patch.xml", "output.xml" );
		}
		else
		{
			logger.error ( "Invalid argument count" );
		}
	}

	private static void printUsage ()
	{
		System.out.println ( "--- XPatch 0.0.1alpha ---" );
		System.out.println ( "Usage:" );
		System.out.println ( "java -cp . de.smuschel.xi.xpatch.XPatch Input-File Patch-File [Options]" );
		System.out.println ( "Options:" );
		System.out.println ( "h	Print this screen" );
	}
}
