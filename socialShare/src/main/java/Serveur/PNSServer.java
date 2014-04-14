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
        	break;
        case 5 :
        	listFilesServer(input,output);
        	break;
        case 6 :
        	suppressionFichier(input,output);
        	break;
        case 7 :
        	fileMove(input,output);
        	break;
        case 8 :
        	fileRename(input,output);
        	break;
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
    			envoiConfirmation(output,true,"Voici le contenu du chemin:"+ dir);
            } 
            catch (IOException e) 
            {
            	envoiConfirmation(output,false,"Erreur dans l'envoi du contenu du répertoire");
                e.printStackTrace();
                
            } 
    		
    	}
    }
    
    public boolean suppressionFichier(ObjectInputStream input, ObjectOutputStream output) throws IOException
    {
    	//lecture du pathname envoyé par le client
    	String pathnameFichier = input.readUTF();
    	
    	try 
        {
    		boolean estSupprime = this.deleteDirOrFile(new File(pathnameFichier)); 
			envoiConfirmation(output,estSupprime,pathnameFichier + " est supprimé.");
		    return estSupprime;
        } 
        catch (IOException e) 
        {
        	envoiConfirmation(output, false ," Une erreur s'est produite, le fichier " + pathnameFichier + " n'est pas supprimé.");
            return false;
        } 
    }
    
    public boolean fileMove(ObjectInputStream input, ObjectOutputStream output) throws IOException
    {        
    	
        ArrayList<String> listeChemins = new ArrayList<String>();
            try {
                Object object = input.readObject();
                listeChemins =  (ArrayList<String>) object;
	            File old_pathname = new File(listeChemins.get(0));
	            File new_pathname = new File(listeChemins.get(1));
	            boolean estdeplace = this.fichierMove(old_pathname, new_pathname); 
	            envoiConfirmation(output,estdeplace,old_pathname.toString() + " a été déplacé ici: " + new_pathname.toString());
	            return estdeplace;
	           
	            } catch (ClassNotFoundException e) {
	            	envoiConfirmation(output, false ," Une erreur s'est produite !");
	                e.printStackTrace();
	                return false;
	            }

	        }  
    	
    public boolean fileRename(ObjectInputStream input, ObjectOutputStream output) throws IOException
    {        
    	
        ArrayList<String> listeChemins = new ArrayList<String>();
            try {
                Object object = input.readObject();
                listeChemins =  (ArrayList<String>) object;
	            File old_pathname = new File(listeChemins.get(0));
	            File new_pathname = new File(listeChemins.get(1));
	            boolean estdeplace = this.fichierMove(old_pathname, new_pathname); 
	            envoiConfirmation(output,estdeplace,old_pathname.toString() + " a été renommé en: " + new_pathname.toString());
	            return estdeplace;
	           
	            } catch (ClassNotFoundException e) {
	            	envoiConfirmation(output, false ," Une erreur s'est produite !");
	                e.printStackTrace();
	                return false;
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
