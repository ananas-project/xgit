package test.ananas.xgitlite.xmail;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import ananas.xgitlite.Repo;
import ananas.xgitlite.XGitLite;
import ananas.xgitlite.local.LocalRepoFinder;
import ananas.xgitlite.xmail.DefaultXMailFactory;
import ananas.xgitlite.xmail.Link;
import ananas.xgitlite.xmail.XCommit;
import ananas.xgitlite.xmail.XCommitBuilder;
import ananas.xgitlite.xmail.XMailFactory;

public class TestXMail {

	public static void main(String[] arg) {
		(new TestXMail()).run();
	}

	private void run() {
		LocalRepoFinder finder = XGitLite.getInstance().getRepoFinder(true);
		File path = this.getPath();
		Repo repo = finder.find(path, null)[0];
		XMailFactory xmf = new DefaultXMailFactory();
		XCommitBuilder builder = xmf.createBuilder(repo);
		Link link = builder.addLink(this.findFile(path, ".png"));
		System.out.println("add link " + link.id());
		XCommit commit = builder.build();
		commit.push();

	}

	private File findFile(File path, String suffix) {
		for (; path != null; path = path.getParentFile()) {
			if (path.isDirectory()) {
				String[] list = path.list();
				for (String name : list) {
					if (name.endsWith(suffix)) {
						return new File(path, name);
					}
				}
			}
		}
		return null;
	}

	private File getPath() {
		try {
			URL url = this.getClass().getProtectionDomain().getCodeSource()
					.getLocation();
			return new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

}
