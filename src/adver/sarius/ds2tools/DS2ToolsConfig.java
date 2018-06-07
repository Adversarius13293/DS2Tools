package adver.sarius.ds2tools;

import java.util.Properties;

public class DS2ToolsConfig {

	public PathfinderConfig pathfinder;
	public Datacollector datacollector;
	private Properties properties;

	public DS2ToolsConfig() {
		this.properties = new Properties();
		this.pathfinder = new PathfinderConfig();
		this.datacollector = new Datacollector();
	}

	public DS2ToolsConfig(Properties properties) {
		this();
		addConfig(properties);
	}

	public void addConfig(Properties properties) {
		this.properties.putAll(properties);
	}

	public void clearConfig() {
		this.properties = new Properties();
	}

	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}

	class PathfinderConfig {
		public boolean isDistanceEnabled() {
			return Boolean.parseBoolean(properties.getProperty("pathfinder.distance.enabled", "false"));
		}

		public int getDistanceX() {
			return Integer.parseInt(properties.getProperty("pathfinder.distance.x", "1"));
		}

		public int getDistanceCurrentMax() {
			return Integer.parseInt(properties.getProperty("pathfinder.distance.currentmax", "0"));
		}

		public boolean isPathEnabled() {
			return Boolean.parseBoolean(properties.getProperty("pathfinder.path.enabled", "false"));
		}

		public int getStartSystem() {
			return Integer.parseInt(properties.getProperty("pathfinder.path.start.system", "605"));
		}

		public int getStartX() {
			return Integer.parseInt(properties.getProperty("pathfinder.path.start.x", "1"));
		}

		public int getStartY() {
			return Integer.parseInt(properties.getProperty("pathfinder.path.start.y", "1"));
		}

		public int getEndSystem() {
			return Integer.parseInt(properties.getProperty("pathfinder.path.end.system", "605"));
		}

		public int getEndX() {
			return Integer.parseInt(properties.getProperty("pathfinder.path.end.x", "200"));
		}

		public int getEndY() {
			return Integer.parseInt(properties.getProperty("pathfinder.path.end.y", "120"));
		}

		public int getMaxDistance() {
			return Integer.parseInt(properties.getProperty("pathfinder.path.maxdistance", "1000"));
		}

		public boolean isOnlyBest() {
			return Boolean.parseBoolean(properties.getProperty("pathfinder.path.onlybest", "true"));
		}

		public int getMaxResults() {
			return Integer.parseInt(properties.getProperty("pathfinder.path.maxresults", "5"));
		}
	}

	class Datacollector {
		public boolean isSchiffInfoEnabled() {
			return Boolean.parseBoolean(properties.getProperty("datacollector.schiffinfo.enabled", "false"));
		}

		public String getSchiffinfoDirectory() {
			return properties.getProperty("datacollector.schiffinfo.directory");
		}
	}
}