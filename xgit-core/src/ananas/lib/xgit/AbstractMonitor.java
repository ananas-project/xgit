package ananas.lib.xgit;

import java.io.File;

public interface AbstractMonitor {

	File getFile();

	boolean init();

	boolean check();

}
