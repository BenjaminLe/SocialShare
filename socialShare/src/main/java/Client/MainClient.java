package Client;

import java.io.IOException;
import java.net.InetAddress;

public class MainClient {

	public static void main(String...args) throws IOException
    {
     /*   PNSClient c1 = new PNSClient(InetAddress.getLocalHost(),1234);
        c1.creationDossier("tata");
        c1.close();
        
        PNSClient c2 = new PNSClient(InetAddress.getLocalHost(),1234);
        c2.creationDossier("tata/toto");
        c2.close();
        
        PNSClient c3 = new PNSClient(InetAddress.getLocalHost(),1234);
        c3.creationDossier("plop/titi");
        c3.close();	*/
		
		PNSClient c1 = new PNSClient(InetAddress.getLocalHost(),1234);
        c1.creationFichier("tata");
        c1.close();
		
    }
	
}
