package Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import PFE.socialShare.PNSDrive;


public class PNSClient extends PNSDrive{

	public static final String user = System.getProperty("user.name");
    public static final String nom = System.getProperty("user.home");
    
	protected Socket clt;
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
      
    //Module 16 en réseau
    public boolean creationDossier(String nomDossier) throws IOException
    {
        output.writeInt(1);
        output.writeUTF(nomDossier);
        output.flush();
        return receptionConfirmation();
    }
    
    //Module 16 en réseau
    public boolean creationFichier(String nomFichier) throws IOException
    {
        output.writeInt(2);
        output.writeUTF(nomFichier);
        output.flush();
        return receptionConfirmation();
    }
     
    //Module 15 en réseau
    public boolean listFilesServer(String pathname) throws IOException
    {
        output.writeInt(3);
        output.writeUTF(pathname);
        output.flush();
        
        ArrayList<String> listeFichiers = new ArrayList<String>();
        try {
            ObjectInputStream objectInput = new ObjectInputStream(clt.getInputStream());
            try {
                Object object = objectInput.readObject();
                listeFichiers =  (ArrayList<String>) object;
	            for(int i=0;i<listeFichiers.size();i++)
	                System.out.println(listeFichiers.get(i));
            } catch (ClassNotFoundException e) {
                System.out.println("The list has not come from the server");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("The socket for reading the object has problem");
            e.printStackTrace();
        }      
        return receptionConfirmation();
    }
    
    //Module 17 en réseau
    public boolean deleteFichier(String pathnameFile) throws IOException
    {
        output.writeInt(4);
        output.writeUTF(pathnameFile);
        output.flush();
        return receptionConfirmation();
    }
     
    //Module 19 en réseau
    public boolean moveFichier(List<String> ancienEtnouveauChemin) throws IOException
    {
        output.writeInt(5);
        output.writeObject(ancienEtnouveauChemin);
        output.flush();
        return receptionConfirmation();
    }
    
    //Module 18 en réseau
    public boolean renameFichier(List<String> ancienEtnouveauChemin) throws IOException
    {
        output.writeInt(6);
        output.writeObject(ancienEtnouveauChemin);
        output.flush();
        return receptionConfirmation();
    }
    
    public boolean pasteFichier(List<String> srcEtdst) throws IOException
    {
        output.writeInt(7);
        output.writeObject(srcEtdst);
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
