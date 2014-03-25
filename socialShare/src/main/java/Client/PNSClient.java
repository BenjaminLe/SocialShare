package Client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import PFE.socialShare.PNSDrive;


public class PNSClient extends PNSDrive{

	public static final String user = System.getProperty("user.name");
    public static final String nom = System.getProperty("user.home");
	
    private final Socket clt;
    private final ObjectInputStream input; 
    private final ObjectOutputStream output; 
    
    public PNSClient(InetAddress address, int port) throws IOException
    {
        clt = new Socket(address,port);
        output = new ObjectOutputStream(clt.getOutputStream());
        input = new ObjectInputStream(clt.getInputStream());
    }
	
    public void close() throws IOException
    {
        input.close();
        output.close();
        clt.close();
    }
    
    public boolean envoiFichier(File fichier) throws IOException
    {
        // TODO
        output.writeInt(1);
        output.flush();
        return receptionConfirmation();
    }
    
    public boolean receptionFichier(String nomFichier, File dst) throws IOException
    {
        // TODO
        output.writeInt(2);
        output.flush();
        return receptionConfirmation();
    }
    
    public boolean creationDossier(String nomDossier) throws IOException
    {
        output.writeInt(3);
        output.writeUTF(nomDossier);
        output.flush();
        return receptionConfirmation();
    }
    
    public boolean creationFichier(String nomFichier) throws IOException
    {
        output.writeInt(4);
        output.writeUTF(nomFichier);
        output.flush();
        return receptionConfirmation();
    }
    
    public boolean receptionConfirmation() throws IOException
    {
        boolean conf = input.readBoolean();
        String msg = input.readUTF();

        if (conf)
            System.out.println("OK : "+msg);
        else
            System.err.println("KO : "+msg);
        
        return conf;
    }
    
}
