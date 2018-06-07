package adver.sarius.ds2tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import adver.sarius.ds2tools.datacollector.SchiffInfoProcessor;
import adver.sarius.ds2tools.pathfinder.PathDistanceTupel;
import adver.sarius.ds2tools.pathfinder.Pathfinder;
import net.driftingsouls.ds2.server.Location;

public class Main {
	
	private static DS2ToolsConfig config;
	
	public static void main(String[] args) {
		try {
			Properties props = new Properties();
			if (args.length == 1) {
				System.out.println("Loading file " + args[0]);
				props.load(new FileInputStream(args[0]));
			} else {
				System.out.println("Loading default config file config.properties");
				props.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));
				new DS2ToolsConfig(props);
			}
			config = new DS2ToolsConfig(props);
		} catch (IOException ex) {
			System.out.println("Failed to read properties: " + ex);
			System.exit(0);
		}
		
		if(config.pathfinder.isPathEnabled()){
			doPathFinder();
		}
		if(config.pathfinder.isDistanceEnabled()){
			doBiggestShortestDistance();
		}
		if(config.datacollector.isSchiffInfoEnabled()){
			doSchiffInfoProcessor();
		}
		System.out.println("Finished");
	}
	
	
	public static void doSchiffInfoProcessor(){
		try{
			// TODO: use 1.html instead of ship1.html
			File[] files = new File(config.datacollector.getSchiffinfoDirectory())
					.listFiles((dir, name) -> name.matches("\\d+\\.html"));
			
			for(File file : files){
				BufferedReader reader = new BufferedReader(new FileReader(file));
				SchiffInfoProcessor sip = new SchiffInfoProcessor();
				sip.readPage(reader, sip.toInt(sip.subString(file.getName(), null,".html")));
			}
		} catch(IOException ex){
			System.out.println("Failed to read SchiffInfo files: " + ex);
		}
	}
		
	/**
	 * Den kürzesten Weg von a nach b finden.
	 */
	public static void doPathFinder(){
		int maxDistance = config.pathfinder.getMaxDistance();
		boolean onlyBest = config.pathfinder.isOnlyBest();
		Location start = new Location(config.pathfinder.getStartSystem(), config.pathfinder.getStartX(), config.pathfinder.getStartY());
		Location end = new Location(config.pathfinder.getEndSystem(), config.pathfinder.getEndX(), config.pathfinder.getEndY());
		Set<Integer> jnsToAvoid = new HashSet<Integer>();
		List<PathDistanceTupel> result;
		int maxResults = config.pathfinder.getMaxResults();

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
	 */
	// TODO: arschlangsam
	public static void doBiggestShortestDistance(){
		Pathfinder pf = new Pathfinder(400, true);
		int width = 400;
		int height = 200;
		int system = 605;
		
		int counter = 0;
		PathDistanceTupel max = new PathDistanceTupel(Collections.emptyList(), 205);
		for(int x = config.pathfinder.getDistanceX(); x <= width; x++) {
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