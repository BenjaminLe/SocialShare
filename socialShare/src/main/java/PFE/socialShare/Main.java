package PFE.socialShare;

import java.io.File;
import java.util.ArrayList;



/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
     
    	PNSDrive pns = new PNSDrive();
    	
		ArrayList<String> allFiles = new ArrayList<String>();
		pns.listeRepertoire(new File("c:\\path"), allFiles);
    	
    }
}
