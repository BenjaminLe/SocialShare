package Serveur;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import PFE.socialShare.PNSDrive;


public class PNSServer extends PNSDrive{

	private final ServerSocket srv;
    private final File root;
	
    public PNSServer(File root, int port) throws IOException
    {
        srv = new ServerSocket(port);
        this.root = root;
    }
    
    public void close() throws IOException
    {
        srv.close();
    }
    
    public void service() throws IOException
    {
        Socket clt = srv.accept();

        ObjectOutputStream output = new ObjectOutputStream(clt.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(clt.getInputStream());

        int mode = input.readInt();

        switch (mode)
        {
        case 1 : 
            envoiFichier(input,output); 
            break;
        case 2 :
            receptionFichier(input,output);
            break;
        case 3 : 
            creationDossier(input,output); 
            break;
        case 4 :
        	creationFichier(input,output);
        default : 
            envoiConfirmation(output,false,"Mode non reconnu.");
        }
        
        input.close();
        output.close();
    }
    
    
    public boolean envoiFichier(ObjectInputStream input, ObjectOutputStream output) throws IOException
    {
        //TODO
        envoiConfirmation(output,false,"Mode non implémenté.");
        return false;
    }
    
    public boolean receptionFichier(ObjectInputStream input, ObjectOutputStream output) throws IOException
    {
        // TODO
        envoiConfirmation(output,false,"Mode non implémenté.");
        return false;
    }
    
    public boolean creationDossier(ObjectInputStream input, ObjectOutputStream output) throws IOException
    {
        String dir = input.readUTF();
        
        String[] tab = dir.split("/\\\\");
        
        File parent = root;
        for (String child : tab)
            parent = new File(parent,child);

        try
        {
            boolean res = parent.mkdir();
            envoiConfirmation(output,res,parent.getAbsolutePath());
            return res;
        }
        catch (SecurityException e)
        {
            envoiConfirmation(output,false,e.toString());
            return false;
        }
    }
    
    public boolean creationFichier(ObjectInputStream input, ObjectOutputStream output) throws IOException
    {
        String dir = input.readUTF();
        
        String[] tab = dir.split("/\\\\");
        
        File parent = root;
        for (String child : tab)
            parent = new File(parent,child);

        try
        {
            boolean res = parent.createNewFile();
            envoiConfirmation(output,res,parent.getAbsolutePath());
            return res;
        }
        catch (SecurityException e)
        {
            envoiConfirmation(output,false,e.toString());
            return false;
        }
    }
    
    public void envoiConfirmation(ObjectOutputStream output, boolean conf, String msg) throws IOException
    {
        output.writeBoolean(conf);
        output.writeUTF(msg);
        output.flush();
    }
    
    
}
