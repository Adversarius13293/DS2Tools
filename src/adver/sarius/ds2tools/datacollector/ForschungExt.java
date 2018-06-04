package adver.sarius.ds2tools.datacollector;

import net.driftingsouls.ds2.server.entities.Forschung;

/**
 * Extended Foschung class for custom id.
 */
public class ForschungExt extends Forschung {

	private int id;

	public ForschungExt(int id) {
		super();
		this.id = id;
	}

	public void setID(int id) {
		this.id = id;
	}

	@Override
	public int getID() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Forschung [id=" + this.id + ", name=" + this.getName() + "]";
	}
}