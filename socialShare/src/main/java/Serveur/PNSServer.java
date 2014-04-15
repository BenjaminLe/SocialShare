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
    
    public void service() throws IOException, ClassNotFoundException
    {
    	//Le serveur est en écoute d'une connexion cliente
        clt = srv.accept();
        System.out.println("Accepted connection : " + clt);
        //On ouvre les flux en entrée et sortie
        ObjectOutputStream output = new ObjectOutputStream(clt.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(clt.getInputStream());
        
        int mode = input.readInt();

        switch (mode)
        {
        case 1 : 
            creationDossier(input,output); 
            break;
        case 2 :
        	creationFichier(input,output);
        	break;
        case 3 :
        	listFilesServer(input,output);
        	break;
        case 4 :
        	suppressionFichier(input,output);
        	break;
        case 5 :
        	fileMove(input,output);
        	break;
        case 6 :
        	fileRename(input,output);
        	break;
        case 7 :
        	copierFichier(input,output);
        	break;
        default : 
            envoiConfirmation(output,false,"Mode non reconnu.");
        }
        //On ferme les flux entrée et sortie
        input.close();
        output.close();
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
    
    //autre module en réseau
    public boolean copierFichier(ObjectInputStream input, ObjectOutputStream output) throws ClassNotFoundException {  //Methode permettant la copie d'un fichier 
         boolean resultat = false; 
         File source = null;
         File destination = null;
         
        // Declaration des flux 
        FileInputStream sourceFile=null; 
        FileOutputStream destinationFile=null;
        ArrayList<String> srcAndDst = new ArrayList<String>();
        try { 
            	Object object = input.readObject();
            	srcAndDst =  (ArrayList<String>) object;
            	
                // Création du fichier :
            	source = new File(srcAndDst.get(0));
            	destination = new File(srcAndDst.get(1));
 
              // destination.createNewFile(); 
                // Ouverture des flux 
                sourceFile = new FileInputStream(source); 
                destinationFile = new FileOutputStream(destination); 
                // Lecture par segment de 0.5Mo  
                byte buffer[]=new byte[524288000]; 
                int nbLecture; 
                while( (nbLecture = sourceFile.read(buffer)) != -1 ) { 
                        destinationFile.write(buffer, 0, nbLecture); 
                }  
                 
                // Copie réussie 
                resultat = true; 
                envoiConfirmation(output,resultat,"Le fichier "+source.getName() + " a bien été copié !");
        } catch( FileNotFoundException f ) { 
        	System.out.println("fichier non trouvé!");
        } catch( IOException e ) { 
        	System.out.println("Problème IO!");
        } finally { 
                // Quoi qu'il arrive, on ferme les flux 
                try { 
                        sourceFile.close(); 
                } catch(Exception e) { } 
                try { 
                        destinationFile.close(); 
                } catch(Exception e) { } 
        }  
       return( resultat ); 
    }
    
    public void envoiConfirmation(ObjectOutputStream output, boolean conf, String msg) throws IOException
    {
        output.writeBoolean(conf);
        output.writeUTF(msg);
        output.flush();
    }
        
}
