package adver.sarius.ds2tools.datacollector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.driftingsouls.ds2.server.config.Weapons;
import net.driftingsouls.ds2.server.ships.ShipType;

public class SQLWriter {
	
	private String directory;
	
	public SQLWriter(String directory){
		this.directory = directory;
	}
	
	public void writeShipTypes(List<ShipType> shipTypes) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(directory + "/ship_types_insert.sql"));
			for (ShipType t : shipTypes) {
				writer.write(getInsertQuery(getShipTypeDBKeys(), getShipTypeDBValues(t), "ship_types"));
				writer.newLine();
			}
			writer.close();
		} catch (IOException ex) {
			System.out.println(" Can't write to file: " + ex);
		}
	}

	private String getInsertQuery(List<String> keys, List<Object> values, String table) {
		String pre = keys.stream().collect(Collectors.joining("`, `", "INSERT INTO `ds2`.`"+table+"` (`", "`) VALUES ('"));
		return values.stream().map(o -> o != null ? o.toString() : null).collect(Collectors.joining("', '", pre, "');")).replaceAll("'null'", "null");
	}
	
	public List<String> getShipTypeDBKeys() {
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
	
	public List<Object> getShipTypeDBValues(ShipType shipType){
		List<Object> values = new ArrayList<>();
		values.add(shipType.getId());
		values.add(shipType.getADocks());
		values.add(shipType.getAblativeArmor());
		values.add(shipType.getBounty());
		values.add(shipType.getCargo());
		values.add(shipType.getChance4Loot());
		values.add(shipType.getCost());
		values.add(shipType.getCrew());
		values.add(shipType.getDescrip());
		values.add(shipType.getDeutFactor());
		values.add(shipType.getEps());
		values.add(shipType.getFlags().stream().map(f -> f.getFlag()).collect(Collectors.joining(" ")));
		values.add(shipType.getGroupwrap());
		values.add(shipType.getHeat());
		values.add(boolToInt(shipType.isHide()));
		values.add(shipType.getHull());
		values.add(shipType.getHydro());
		values.add(shipType.getJDocks());
		values.add(shipType.getLostInEmpChance());
		values.add(Weapons.packWeaponList(shipType.getMaxHeat()));
		values.add(shipType.getMaxUnitSize());
		values.add(shipType.getMinCrew());
		values.add(shipType.getModules());
		values.add(shipType.getNahrungCargo());
		values.add(shipType.getNickname());
		values.add(shipType.getPanzerung());
		values.add(shipType.getPicture());
		values.add(shipType.getRa());
		values.add(shipType.getRd());
		values.add(shipType.getReCost());
		values.add(shipType.getRm());
		values.add(shipType.getRu());
		values.add(shipType.getSensorRange());
		values.add(shipType.getShields());
		values.add(shipType.getShipClass().ordinal());
		values.add(shipType.getSize());
		values.add(boolToInt(shipType.hasSrs()));
		values.add(shipType.getTorpedoDef());
		values.add(shipType.getUnitSpace());
		values.add(shipType.getVersion());
		values.add(boolToInt(shipType.isVersorger()));
		values.add(Weapons.packWeaponList(shipType.getWeapons()));
		values.add(shipType.getWerft());
		values.add(shipType.getOneWayWerft() != null ? shipType.getOneWayWerft().getId() : null);
		return values;
	}
	public int boolToInt(boolean bool){
		return bool ? 1 : 0; 
	}
}