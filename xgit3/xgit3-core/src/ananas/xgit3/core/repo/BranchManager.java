package ananas.xgit3.core.repo;

import java.util.List;

public interface BranchManager {

	Branch getBranch(String name);

	List<String> listBranchNames();

	Branch createBranch(String name);

}
