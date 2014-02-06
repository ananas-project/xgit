package test.ananas.xgitlite.xmail;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.xml.sax.SAXException;

import ananas.xgitlite.ObjectId;
import ananas.xgitlite.XGLException;
import ananas.xgitlite.XGitLite;
import ananas.xgitlite.local.LocalRepo;
import ananas.xgitlite.local.LocalRepoFinder;
import ananas.xgitlite.xmail.DefaultXMailFactory;
import ananas.xgitlite.xmail.Link;
import ananas.xgitlite.xmail.XCommit;
import ananas.xgitlite.xmail.XCommitBuilder;
import ananas.xgitlite.xmail.XMailFactory;

public class TestXMail {

	public static void main(String[] arg) {
		try {
			(new TestXMail()).run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void run() throws IOException, XGLException, SAXException {

		// add
		LocalRepoFinder finder = XGitLite.getInstance().getRepoFinder(true);
		File path = this.getPath();
		LocalRepo repo = finder.find(path, null)[0];
		XMailFactory xmf = new DefaultXMailFactory();
		XCommitBuilder builder = xmf.createBuilder(repo);
		Link link = builder.addLink(this.findFile(path, ".png"));
		System.out.println("add link " + link.id());
		XCommit commit = builder.build();
		commit.push();
		System.out.println("commit " + commit.getId());

		// checkout
		commit = xmf.getCommit(repo, commit.getId());
		List<Link> links = commit.getLinks();
		for (Link link2 : links) {

			ObjectId id = link2.id();
			String name = link2.name();

			StringBuilder sb = new StringBuilder();
			sb.append("    link ");
			sb.append(id);
			sb.append(" ");
			sb.append(name);
			System.out.println(sb);
		}
	}

	private File findFile(final File path, String suffix) {

		for (File p = path; p != null; p = p.getParentFile()) {
			if (p.isDirectory()) {
				String[] list = p.list();
				for (String name : list) {
					if (name.endsWith(suffix)) {
						return new File(p, name);
					}
				}
			}
		}
		String msg = "cannot find a '" + suffix + "' file in the path: " + path;
		throw new RuntimeException(msg);
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
