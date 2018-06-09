package adver.sarius.ds2tools.datacollector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SQLWriter {

	private String directory;

	public SQLWriter(String directory) {
		this.directory = directory;
	}

	public <T> void writeList(List<T> toWrite) {
		if (toWrite == null || toWrite.isEmpty()) {
			System.out.println("Nothing to write.");
			return;
		}
		if (!(toWrite.get(0) instanceof DS2DBInfo)) {
			System.out.println("Can't write class: " + toWrite.get(0).getClass());
			return;
		}
		DS2DBInfo info = (DS2DBInfo) toWrite.get(0);
		String table = info.getDBTable();

		String insert = info.getDBKeys().stream()
				.collect(Collectors.joining("`, `", "INSERT INTO `ds2`.`" + table + "` (`", "`) VALUES ('"));
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(directory + "/" + table + "_insert.sql"));
			for (Object o : toWrite) {
				info = (DS2DBInfo) o;
				writer.write(info.getDBValues().stream().map(i -> i != null ? i.toString() : null)
						.collect(Collectors.joining("', '", insert, "');")).replaceAll("'null'", "null"));
				writer.newLine();
			}
			writer.close();
		} catch (IOException ex) {
			System.out.println("Can't write to file: " + ex);
		}
	}
}