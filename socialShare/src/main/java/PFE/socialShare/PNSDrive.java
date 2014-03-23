package PFE.socialShare;

import java.io.*;
import java.util.List;

public class PNSDrive {

	// Module 17
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success)
					return false;
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}

	// Module 18 & 19
	public static boolean fichierRenameOrMove(File ancien_nom, File nouveau_nom) {

		if (ancien_nom.renameTo(nouveau_nom))
			return true;
		else
			return false;

	}

	// Module 16
	public static boolean fichierCreate(File fichier, boolean ford) {

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
	public static void listeRepertoire(File path, List<String> allFiles) {

		if (path.isDirectory()) {
			File[] list = path.listFiles();
			if (list != null) {
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
			allFiles.add(currentFilePath);
		}
	}

}
