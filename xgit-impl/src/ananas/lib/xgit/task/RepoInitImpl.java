package ananas.lib.xgit.task;

import java.io.IOException;
import java.util.List;

import ananas.lib.io.vfs.VFile;
import ananas.lib.xgit.XGitException;
import ananas.lib.xgit.XGitFileInfo;
import ananas.lib.xgit.XGitRepo;
import ananas.lib.xgit.XGitWorkspace;
import ananas.lib.xgit.task.ext.RepoInit;

public class RepoInitImpl extends AbstractXGitTask implements RepoInit {

	@Override
	public void run(XGitTaskContext context) throws XGitException {

		System.out.println(this + ".run(...)");

		XGitRepo repo = context.getRepo();
		XGitWorkspace worksp = repo.getWorkspace();
		VFile dir_this = worksp.getFile(XGitWorkspace.dir_main);
		VFile dir_working = worksp.getFile(XGitWorkspace.dir_working);

		String dotXGit = dir_this.getName();

		if (dotXGit.equals(".xgit")) {
			// with working dir

			if (dir_working.exists()) {
				throw new XGitException("The repo directory is exists. "
						+ dir_working);
			}

		} else {
			// bare repo
			if (dir_this.exists()) {
				throw new XGitException("The repo directory is exists. "
						+ dir_this);
			}
		}

		dir_this.mkdirs();

		List<String> keys = worksp.listKeys();
		for (String key : keys) {
			XGitFileInfo info = worksp.getFileInfo(key);

			System.out.println("" + info);

			if (info.isRequired()) {
				VFile file = info.getFile();
				if (info.isDirectory()) {
					file.mkdirs();
				} else {
					file.getParentFile().mkdirs();
					try {
						file.createNewFile();
					} catch (IOException e) {
						throw new XGitException(e);
					}
				}

			}

		}
	}

	public static class Factory implements XGitTaskRunnableFactory {

		@Override
		public XGitTaskRunnable createTaskRunnable() {
			return new RepoInitImpl();
		}
	}

}
