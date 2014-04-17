package PFE.socialShare;

import java.io.*;
import java.util.List;
import javax.swing.JApplet;
import java.awt.*;
import java.applet.*;

public class PNSDrive extends JApplet {
	/*
	******* Fonctionnalités en local ********
	*/

	//Path à initialiser
	String logPath;
	
	// Module 17
	public boolean deleteDirOrFile(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirOrFile(new File(dir, children[i]));
				if (!success)
					return false;
			}
		}
		
		boolean estSupprime = dir.delete();
		System.out.println(dir.toString() + " est supprimé !");
		// The directory is now empty so delete it
		return estSupprime;
	}

	// Module 18
	public boolean fichierRename(File ancien_nom, File nouveau_nom) {

		if (ancien_nom.renameTo(nouveau_nom))
			return true;
		else
			return false;

	}

	// Module 19
		public boolean fichierMove(File ancien_emplacement, File nouvelle_emplacement) {

			if (ancien_emplacement.renameTo(nouvelle_emplacement))
				return true;
			else
				return false;

		}
	
	// Module 16
	public boolean fichierCreate(File fichier, boolean ford) {

		boolean fichierCree = false;

		if (fichier.exists()) {
			System.out.println("Le fichier existe déjà");

		} else {
			try {
				if (ford == false) {
					if (fichier.createNewFile()) {
						System.out.println("Création du fichier réussi");
						fichierCree = true;
					} else {
						System.out.println("Création du fichier echoué");
					}
				} else {
					if (fichier.mkdir()) {
						System.out.println("Création du dossier réussi");
						fichierCree = true;
					} else {
						System.out.println("Création du dossier echoué");

					}
				}

			} catch (IOException exception) {
				System.out.println("Erreur " + exception.getMessage());
			}

		}

		return fichierCree;
	}

	// Module 15
	public void listeRepertoire(File path, List<String> allFiles) {

		if (path.isDirectory()) {
			
			File[] list = path.listFiles();
			if (list != null) {
				System.out.println(path);
		//		this.ecrireFichier(logPath+"\\log.txt", path.toString()+"\n");
				allFiles.add(path.toString());
				for (int i = 0; i < list.length; i++) {
					// Appel récursif sur les sous-répertoires
					listeRepertoire(list[i], allFiles);			
				}
			} else {
				System.err.println(path + " : Erreur de lecture.");
			}

		}

		else {
			String currentFilePath = path.getAbsolutePath();
			System.out.println(currentFilePath);
		//	this.ecrireFichier(logPath+"\\log.txt", currentFilePath+"\n");
			allFiles.add(currentFilePath);
		}
	}

	public void ecrireFichier(String path, String text) {
	//	PrintWriter ecri;
		FileWriter writer = null;
		try {
		/*	ecri = new PrintWriter(new FileWriter(path));
			ecri.print(text);
			ecri.flush();
			ecri.close();
			*/
			 writer = new FileWriter(path, true);
		     writer.write(text,0,text.length());
		     writer.close();
			
		}// try
		catch (NullPointerException a) {
			System.out.println("Erreur : pointeur null");
		} catch (IOException a) {
			System.out.println("Problème d'IO");
		}
	}// ecrire

	public String lireFichier(String path) {
		BufferedReader lect;
		String tmp = "";
		try {
			lect = new BufferedReader(new FileReader(path));
			while (lect.ready() == true) {
				tmp += lect.readLine();
			}// while
		}// try
		catch (NullPointerException a) {
			System.out.println("Erreur : pointeur null");
		} catch (IOException a) {
			System.out.println("Problème d'IO");
		}
		return tmp;
	}// lecture
	
}
