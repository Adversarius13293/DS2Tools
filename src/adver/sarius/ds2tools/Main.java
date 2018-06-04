package adver.sarius.ds2tools;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adver.sarius.ds2tools.pathfinder.PathDistanceTupel;
import adver.sarius.ds2tools.pathfinder.Pathfinder;
import net.driftingsouls.ds2.server.Location;

public class Main {
	
	public static void main(String[] args) {
		doPathFinder();
//		doBiggestShortestDistance();
	}
	
	/**
	 * Den kürzesten Weg von a nach b finden.
	 */
	public static void doPathFinder(){
		int maxDistance = 1000;
		boolean onlyBest = false;
		Location start = new Location(605,200, 120);
		Location end = new Location(605, 200, 120);
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

	/**
	 * Die am weitesten voneinander entfernten Punkte innerhalb eines Systems finden.
	 * Fuer Eta Nebular ausgelegt. Einiges an Optimierungsbedarf.
	 * 
	 * Wie groß der Abstand der am weitesten voneinander entfernten Punkte ist
	 * 
	 */
	// TODO: arschlangsam
	public static void doBiggestShortestDistance(){
		Pathfinder pf = new Pathfinder(400, true);
		int width = 400;
		int height = 200;
		int system = 605;
		
		int counter = 0;
		PathDistanceTupel max = new PathDistanceTupel(Collections.emptyList(), 205);
		for(int x = 1; x <= width; x++) {
			System.out.println("starting with x="+x);
			for(int y = 1; y <= height; y++) {
				System.out.println("starting with y="+y);
				Location start = new Location(system, x, y);
				for(int x2 = x; x2 <= width; x2++) {
					for(int y2 = 1; y2 <= height; y2++) {
						// TODO: correct index instead of skipping?
						if(x2-x <= max.getDistance() && Math.abs(y2-y) <= max.getDistance()){
							continue;
						}
						Location end = new Location(system, x2, y2);
						pf.calculatePaths(start, end, null);
						counter++;
						PathDistanceTupel result = pf.getPathResults().get(0);
						if(result.getDistance() > max.getDistance()){
							max = result;
							System.out.printf("(%d) New max distance: %s \n", counter, pf.toString(start, end, max));
						}
					}
				}
			}
		}
		System.out.printf("(%d) Finished: %s",counter, max.getDistance()); // TODO: Max start/end fehlt
	}
}