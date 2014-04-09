package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;


public class MainClient {

	public static void main(String...args) throws IOException
    {
		/*
        PNSClient c1 = new PNSClient(InetAddress.getLocalHost(),1234);
        c1.creationDossier("tata");
        c1.close();
        
        PNSClient c2 = new PNSClient(InetAddress.getLocalHost(),1234);
        c2.creationDossier("tata/toto");
        c2.close();
        
        PNSClient c3 = new PNSClient(InetAddress.getLocalHost(),1234);
        c3.creationFichier("tata/titi.txt");
        c3.close();
		*/
		PNSClient c4 = new PNSClient(InetAddress.getLocalHost(),1234);
        c4.deleteFichier("E:\\test\\testlol.txt");
        c4.close();
		
    }
	
}
