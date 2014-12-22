package adver.sarius.pathfinder;

import java.util.List;

public class PathDistanceTupel implements Comparable<PathDistanceTupel>{
	private List<Integer> path;
	private int distance;
	
	public PathDistanceTupel(){

	}

	public PathDistanceTupel(List<Integer> path, int distance) {
		this.path = path;
		this.distance = distance;
	}

	
	public List<Integer> getPath() {
		return path;
	}

	public void setPath(List<Integer> path) {
		this.path = path;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	public int compareTo(PathDistanceTupel o) {
		return this.distance - o.distance;
	}
	
	

}
