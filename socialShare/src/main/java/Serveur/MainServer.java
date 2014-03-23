package Serveur;

import java.io.File;
import java.io.IOException;

public class MainServer {

	public static void main(String...args) throws IOException
    {
        PNSServer s = new PNSServer(new File("C:\\test\\"),1234);

        for (int i=1; i<=3; i++)
            s.service();
        
        s.close();
    }
	
}
