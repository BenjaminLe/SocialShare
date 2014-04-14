package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


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
		List<String> new_And_old_Ways = new ArrayList<String>();
		String old_pathname = "C:\\Users\\BenjaminLe\\Desktop\\Musique Yoga\\yoga1.mp3";
		String new_pathname = "E:\\yoga_test\\yoga1.mp3";
		new_And_old_Ways.add(old_pathname);
		new_And_old_Ways.add(new_pathname);
        c4.pasteFichier(new_And_old_Ways);
        c4.close();
		
    }
	
}
