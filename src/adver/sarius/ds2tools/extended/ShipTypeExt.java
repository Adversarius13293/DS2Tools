package adver.sarius.ds2tools.extended;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import adver.sarius.ds2tools.datacollector.DS2DBInfo;
import net.driftingsouls.ds2.server.config.Weapons;
import net.driftingsouls.ds2.server.ships.ShipType;

/**
 * Extended class for custom id.
 */
public class ShipTypeExt extends ShipType implements DS2DBInfo {
	private int id;

	public ShipTypeExt(int id) {
		super();
		this.id = id;
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
		return "ship_types";
	}

	@Override
	public List<String> getDBKeys() {
		List<String> keys = new ArrayList<>();
		keys.add("id");
		keys.add("adocks");
		keys.add("ablativeArmor");
		keys.add("bounty");
		keys.add("cargo");
		keys.add("chance4Loot");
		keys.add("cost");
		keys.add("crew");
		keys.add("descrip");
		keys.add("deutfactor");
		keys.add("eps");
		keys.add("flags");
		keys.add("groupwrap");
		keys.add("heat");
		keys.add("hide");
		keys.add("hull");
		keys.add("hydro");
		keys.add("jdocks");
		keys.add("lostInEmpChance");
		keys.add("maxheat");
		keys.add("maxunitsize");
		keys.add("minCrew");
		keys.add("modules");
		keys.add("nahrungcargo");
		keys.add("nickname");
		keys.add("panzerung");
		keys.add("picture");
		keys.add("ra");
		keys.add("rd");
		keys.add("recost");
		keys.add("rm");
		keys.add("ru");
		keys.add("sensorrange");
		keys.add("shields");
		keys.add("class");
		keys.add("size");
		keys.add("srs");
		keys.add("torpedodef");
		keys.add("unitspace");
		keys.add("version");
		keys.add("versorger");
		keys.add("weapons");
		keys.add("werft");
		keys.add("ow_werft");
		return keys;
	}

	@Override
	public List<Object> getDBValues() {
		List<Object> values = new ArrayList<>();
		values.add(getId());
		values.add(getADocks());
		values.add(getAblativeArmor());
		values.add(getBounty());
		values.add(getCargo());
		values.add(getChance4Loot());
		values.add(getCost());
		values.add(getCrew());
		values.add(getDescrip());
		values.add(getDeutFactor());
		values.add(getEps());
		values.add(getFlags().stream().map(f -> f.getFlag()).collect(Collectors.joining(" ")));
		values.add(getGroupwrap());
		values.add(getHeat());
		values.add(boolToInt(isHide()));
		values.add(getHull());
		values.add(getHydro());
		values.add(getJDocks());
		values.add(getLostInEmpChance());
		values.add(Weapons.packWeaponList(getMaxHeat()));
		values.add(getMaxUnitSize());
		values.add(getMinCrew());
		values.add(getModules());
		values.add(getNahrungCargo());
		values.add(getNickname());
		values.add(getPanzerung());
		values.add(getPicture());
		values.add(getRa());
		values.add(getRd());
		values.add(getReCost());
		values.add(getRm());
		values.add(getRu());
		values.add(getSensorRange());
		values.add(getShields());
		values.add(getShipClass().ordinal());
		values.add(getSize());
		values.add(boolToInt(hasSrs()));
		values.add(getTorpedoDef());
		values.add(getUnitSpace());
		values.add(getVersion());
		values.add(boolToInt(isVersorger()));
		values.add(Weapons.packWeaponList(getWeapons()));
		values.add(getWerft());
		values.add(getOneWayWerft() != null ? getOneWayWerft().getId() : null);
		return values;
	}
}