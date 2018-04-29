package adver.sarius.ds2tools;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adver.sarius.ds2tools.pathfinder.PathDistanceTupel;
import adver.sarius.ds2tools.pathfinder.Pathfinder;
import net.driftingsouls.ds2.server.Location;

public class Main {
	
	public static void main(String[] args) {
		doPathFinder();
	}
	
	public static void doPathFinder(){
		int maxDistance = 1000;
		boolean onlyBest = true;
		Location start = new Location(2,190,190);
		Location end = new Location(6, 200, 200);
		Set<Integer> jnsToAvoid = new HashSet<Integer>();
		List<PathDistanceTupel> result;
		int maxResults = 5;
		
		Pathfinder pf = new Pathfinder(maxDistance, onlyBest);
		pf.calculatePaths(start, end, jnsToAvoid);
		result = pf.getPathResults();
		
		for(int i = 0; i < Math.min(maxResults, result.size()); i++){
			System.out.println(pf.toString(start, end, result.get(i)));
		}
	}

}
