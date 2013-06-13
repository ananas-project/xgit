package ananas.lib.xgit.task;

import java.util.List;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.XGitException;
import ananas.lib.xgit.XGitFileInfo;
import ananas.lib.xgit.XGitWorkspace;
import ananas.lib.xgit.task.ext.RepoCheck;

public class RepoCheckImpl extends AbstractXGitTask implements RepoCheck {

	@Override
	public void run(XGitTaskContext context) throws XGitException {

		System.out.print(this + ".run(context)");

		XGitWorkspace worksp = context.getRepo().getWorkspace();
		List<String> keys = worksp.listKeys();
		for (String key : keys) {
			XGitFileInfo info = worksp.getFileInfo(key);
			VFile file = info.getFile();
			if (file.exists()) {
				boolean a, b;
				a = info.isDirectory();
				b = file.isDirectory();
				if (a != b) {
					if (a)
						throw new XGitException(
								"the path must be a directory. " + file);
					else
						throw new XGitException("the path must be a file. "
								+ file);
				}
			} else {
				// no this file
				if (info.isRequired()) {
					throw new XGitException("the file is required. " + file);
				}
			}
		}

		// System.out.println(" ........ (done)");
	}

}
