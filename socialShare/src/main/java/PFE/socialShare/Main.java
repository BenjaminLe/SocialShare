package PFE.socialShare;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		PNSDrive pns = new PNSDrive();
		if(args[2].equals("f"))
			pns.fichierCreate(new File(args[1]), false);
		else
			if(args[2].equals("d"))
				pns.fichierCreate(new File(args[1]), true);
	}
}
