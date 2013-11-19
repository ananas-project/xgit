package ananas.objectbox.body.json;

import ananas.objectbox.IObjectBody;
import ananas.objectbox.IObjectHead;

public abstract class JsonBody implements IObjectBody, IJsonBody {

	private IObjectHead _head;

	@Override
	public final boolean bindHead(IObjectHead head) {
		if (this._head == null && head != null) {
			this._head = head;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public final IObjectHead getHead() {
		return this._head;
	}

}
