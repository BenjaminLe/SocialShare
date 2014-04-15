package PFE.socialShare;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		PNSDrive pns = new PNSDrive();
		pns.logPath = "E:\\yoga_test";
		List<String> allFiles = new ArrayList<String>();
		pns.listeRepertoire(new File(pns.logPath), allFiles);
	}
}
