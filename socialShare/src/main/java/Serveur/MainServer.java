package Serveur;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainServer {

	public static void main(String...args) throws IOException, ClassNotFoundException
    {
      PNSServer s = new PNSServer(new File("E:\\test\\"),1234);
     // for (int i=1; i<=3; i++)
            s.service();            
            s.close();	
    }
	
}
