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
		
		PNSClient c4 = new PNSClient(InetAddress.getByName("192.168.43.69"),1234);
		
		List<String> new_And_old_Ways = new ArrayList<String>();
		String old_pathname = "\\\\SARAH-PC\\Users\\Sarah\\Desktop\\Musique Ali\\ING5\\Reggada.mp3";
		String new_pathname = "\\\\BENJAMINLE-PC\\test_soutenance\\Reggada.mp3";
		new_And_old_Ways.add(old_pathname);
		new_And_old_Ways.add(new_pathname);
        c4.pasteFichier(new_And_old_Ways);
        
    /*    c4.logPath = "E:\\test_soutenance";
		c4.listFilesServer("\\\\SARAH-PC\\Users\\Sarah\\Desktop\\Musique Ali\\ING5\\Divers");
        c4.close();
		*/
    }
	
}
