package PFE.socialShare;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		PNSDrive pns = new PNSDrive();
		pns.fichierMove(new File("E:\\hihi.txt"), new File("E:\\test_renomme\\hihi.txt"));
	}
}
