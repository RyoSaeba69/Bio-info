package controllers;

import java.io.File;

public class FileController {
	private static File fichier;

	public static File getFichier() {
		return fichier;
	}

	public static void setFichier(File fichier) {
		FileController.fichier = fichier;
	}
}
