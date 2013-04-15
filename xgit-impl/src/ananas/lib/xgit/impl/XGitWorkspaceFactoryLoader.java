package ananas.lib.xgit.impl;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import ananas.lib.blueprint3.Blueprint;
import ananas.lib.blueprint3.dom.BPDocument;
import ananas.lib.blueprint3.lang.BPEnvironment;
import ananas.lib.blueprint3.tools.R_file_generator;
import ananas.lib.dtml.DTWorkspaceFactory;
import ananas.lib.util.CommandLinePropertiesUtil;

public class XGitWorkspaceFactoryLoader {

	public static void main(String[] args) {
		// gen R.java file
		Properties prop = CommandLinePropertiesUtil.argumentsToProperties(args);
		(new MyRGen()).run(prop);
	}

	private static class MyRGen {

		public void run(Properties prop) {
			prop.setProperty("-base-dir", this.getBaseDir());
			prop.setProperty("-res-dir", "src");
			prop.setProperty("-gen-dir", "gen");
			prop.setProperty("-R", "true");
			prop.setProperty("-accept-file", ".xml");
			prop.setProperty("-accept-xml-file", ".xml");
			prop.setProperty("-accept-attr", "id");

			String[] args = CommandLinePropertiesUtil
					.propertiesToArguments(prop);
			R_file_generator.main(args);

		}

		private String getBaseDir() {
			try {
				URL url = this.getClass().getProtectionDomain().getCodeSource()
						.getLocation();
				File dir = new File(url.toURI());
				String str = dir.getParent();
				return str;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	}

	public DTWorkspaceFactory getFactory() {

		try {

			String nslist[] = {
					"ananas.lib.blueprint3.loader.eom.EomReflectInfo",
					"ananas.lib.dtml.dom.DtmlNsInfo" };
			BPEnvironment envi = Blueprint.Util.getInstance()
					.defaultEnvironment();
			for (String ns : nslist) {
				envi.loadNamespace(ns, true);
			}

			String url = R.file.xgit_dtml_xml;
			BPDocument doc = Blueprint.Util.loadDocument(url);
			DTWorkspaceFactory factory = (DTWorkspaceFactory) doc
					.getRootElement().getTarget(true);

			return factory;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
