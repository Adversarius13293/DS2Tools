package adver.sarius.ds2tools.datacollector;

import net.driftingsouls.ds2.server.ships.ShipType;

/**
 * Extended ShipType class for custom id.
 */
public class ShipTypeExt extends ShipType {
	private int id;

	public ShipTypeExt(int id) {
		super();
		this.id = id;
	}

	@Override
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
}