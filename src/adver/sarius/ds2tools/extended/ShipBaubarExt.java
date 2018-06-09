package adver.sarius.ds2tools.extended;

import java.util.ArrayList;
import java.util.List;

import adver.sarius.ds2tools.datacollector.DS2DBInfo;
import net.driftingsouls.ds2.server.ships.ShipBaubar;
import net.driftingsouls.ds2.server.ships.ShipType;

/**
 * Extended class for custom id.
 */
public class ShipBaubarExt extends ShipBaubar implements DS2DBInfo {
	private int id;

	public ShipBaubarExt(ShipType shipType) {
		super(shipType);
		// for a consistent ID regardless of entries, since I currently don't
		// have the live ID
		this.id = shipType.getId();
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getDBTable() {
		return "ships_baubar";
	}

	@Override
	public List<String> getDBKeys() {
		List<String> keys = new ArrayList<>();
		keys.add("id");
		keys.add("costs");
		keys.add("crew");
		keys.add("dauer");
		keys.add("ekosten");
		keys.add("flagschiff");
		keys.add("race");
		keys.add("werftslots");
		keys.add("res1_id");
		keys.add("res2_id");
		keys.add("res3_id");
		keys.add("type");
		return keys;
	}

	@Override
	public List<Object> getDBValues() {
		List<Object> values = new ArrayList<>();
		values.add(getId());
		values.add(getCosts().save());
		values.add(getCrew());
		values.add(getDauer());
		values.add(getEKosten());
		values.add(boolToInt(isFlagschiff()));
		values.add(getRace());
		values.add(getWerftSlots());
		values.add(getRes(1) != null ? getRes(1).getID() : null);
		values.add(getRes(2) != null ? getRes(2).getID() : null);
		values.add(getRes(3) != null ? getRes(3).getID() : null);
		values.add(getType().getId());
		return values;
	}
}