package ananas.xgit3.core.repo;

import ananas.xgit3.core.HashID;

public interface Branch {

	String getName();

	HashID getHead();
}
