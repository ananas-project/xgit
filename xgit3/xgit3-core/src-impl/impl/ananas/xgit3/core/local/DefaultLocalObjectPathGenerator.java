package impl.ananas.xgit3.core.local;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ananas.xgit3.core.HashID;
import ananas.xgit3.core.local.LocalObjectPathGenerator;

public class DefaultLocalObjectPathGenerator implements
		LocalObjectPathGenerator {

	private final String _pattern;
	private int[] _stanza_list;

	public DefaultLocalObjectPathGenerator(String pathPattern) {
		this._pattern = pathPattern;
	}

	@Override
	public String gen(HashID id) {
		StringBuilder sb = new StringBuilder();
		String s = id.toString();
		int[] array = this.getStanzaList();
		int i = 0;
		for (int stanza : array) {
			sb.append(s.substring(i, i + stanza));
			sb.append(File.separatorChar);
			i += stanza;
		}
		sb.append(s.substring(i));
		return sb.toString();
	}

	private int[] getStanzaList() {
		int[] array = this._stanza_list;
		if (array != null) {
			return array;
		}
		List<Integer> list = new ArrayList<Integer>();
		char[] chs = this._pattern.toCharArray();
		int cc = 0;
		for (char ch : chs) {
			if (ch == '/' || ch == '\\') {
				// new stanza
				if (cc > 0) {
					list.add(cc);
					cc = 0;
				}
			} else {
				cc++;
			}
		}
		array = new int[list.size()];
		for (int i = array.length - 1; i >= 0; i--) {
			array[i] = list.get(i);
		}
		this._stanza_list = array;
		return array;
	}

	@Override
	public File gen(HashID id, File base) {
		String off = this.gen(id);
		return new File(base, off);
	}

}
