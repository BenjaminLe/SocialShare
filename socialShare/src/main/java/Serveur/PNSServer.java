package Serveur;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import PFE.socialShare.PNSDrive;


public class PNSServer extends PNSDrive{

	private final ServerSocket srv;
    private final File root;
    protected Socket clt;
    
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
        clt = srv.accept();
      
        System.out.println("Accepted connection : " + clt);
        ObjectOutputStream output = new ObjectOutputStream(clt.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(clt.getInputStream());
        
        int mode = input.readInt();

        switch (mode)
        {
        case 1 : 
        	receptionFichier(input,output);
            break;
        case 2 :
        	envoiFichier(input,output);
            break;
        case 3 : 
            creationDossier(input,output); 
            break;
        case 4 :
        	creationFichier(input,output);
        case 5 :
        	listFilesServer(input,output);
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
    
    public void listFilesServer(ObjectInputStream input, ObjectOutputStream output) throws IOException
    {
       
    	//lecture du pathname envoyé par le client
    	String dir = input.readUTF();
    	List<String> allFiles = new ArrayList<String>();
    	if (dir == null)
    		envoiConfirmation(output,false,"Pathname Manquant");
    		
    	else {
    		this.listeRepertoire(new File(dir), allFiles);
    		try 
            {
    			output = new ObjectOutputStream(clt.getOutputStream());
    			output.writeObject(allFiles);              
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            } 
    		envoiConfirmation(output,true,"Voici le contenu du chemin:"+ dir);
    	}
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
