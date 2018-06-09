package adver.sarius.ds2tools.datacollector;

import java.util.List;

public interface DS2DBInfo {

	public String getDBTable();

	public List<String> getDBKeys();

	public List<Object> getDBValues();
	
	// TODO: put in some util class?
	default int boolToInt(boolean bool){
		return bool ? 1 : 0;
	}
}