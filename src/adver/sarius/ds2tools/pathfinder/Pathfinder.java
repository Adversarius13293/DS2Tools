package adver.sarius.ds2tools.pathfinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.driftingsouls.ds2.server.Location;
import net.driftingsouls.ds2.server.entities.JumpNode;

import org.hibernate.Session;

import adver.sarius.ds2tools.Data;
import adver.sarius.ds2tools.HibernateUtils;

public class Pathfinder {
	
	/** Maximale Distanz, bevor mit der Suche auf dem Pfad abgebrochen wird. **/
	private int maxTestDist = Integer.MAX_VALUE;
	
	/** Liste, in der alle besuchten Pfade gesammelt werden. **/
	private List<PathDistanceTupel> pathsList;
	
	/** Gibt an ob nur eine Route, naemlich die kuerzeste, gefunden werden soll. **/
	private boolean onlyBest = false;
	
	/** Die bisher minimalste Distanz. Wird nur benutzt, wenn {@code onlyBest} true ist. **/
	private int minDist;
	
	public Pathfinder() {
		clear();
	}
	public Pathfinder(int maxTestDist, boolean onlyBest) {
		this();
		this.maxTestDist = maxTestDist;
		this.onlyBest = onlyBest;
	}

	public void setOnlyBest(boolean onlyBest) {
		this.onlyBest = onlyBest;
	}

	public void setMaxTestDist(int maxTestDist) {
		this.maxTestDist = maxTestDist;
	}

	private void clear() {
		pathsList = new ArrayList<PathDistanceTupel>();
		minDist = Integer.MAX_VALUE;
	}
	
	public static void main(String[] args) {
		HibernateUtils.initConnection();
		Pathfinder p = new Pathfinder();
		Location start = new Location(1, 169, 489);
		Location end = new Location(75, 28, 95);
//		Location end = new Location(41, 8, 35);
		Set<Integer> jnsToAvoid = new HashSet<Integer>();
		List<Integer> jnsUsed = new ArrayList<Integer>();
		int distance = 0;
		p.maxTestDist = 500;		
		
		p.fillPathList(start, end, jnsToAvoid, jnsUsed, distance);
		Collections.sort(p.pathsList);
		for(int i = 0; i< Math.min(p.pathsList.size(), 8); i++){
			System.out.println(p.toString(start, end, p.pathsList.get(i)));
		}
//		calculateAllJnDistance();
	}
	// Distanz: 789: 2:164/195 --> 2:44/44 --> 1:25/28 --> 1:39/31 --> 1:32/348 --> 1:98/296 --> 1:178/217 --> 1:192/267 --> 75:139/221 --> 75:256/218 --> 81:10/284 --> 81:70/20 --> 6:69/43 --> 6:196/157
	
	/*
	public static void calculateAllJnDistance(){
		Pathfinder p = new Pathfinder();
		
		List<PathDistanceTupel> maxPath = new ArrayList<PathDistanceTupel>();
		maxPath.add(new PathDistanceTupel(new ArrayList<Integer>(), -1));
		List<String> maxPathString = new ArrayList<String>();
		
//		List<String> visitedJNs = new ArrayList<String>();
		for(List<JumpNode> nodeList : p.jns.values()){
			for(JumpNode jump : nodeList){
				
				for(List<JumpNode> nodeListInner : p.jns.values()){
					for(JumpNode jumpInner : nodeListInner){
//						if(!visitedJNs.contains(jumpInner.getId() + "|" + p.getOppositeJN(jumpInner))){
						if(jumpInner.getSystem() != jump.getSystem()){
//							visitedJNs.add(p.getOppositeJN(jumpInner) + "|" + jumpInner.getId());
							p.clear();
							p.fillPathList(jump.getLocation(), jumpInner.getLocation(), new HashSet<Integer>(), new ArrayList<Integer>(), 0);
							Collections.sort(p.pathsList);
							try{
							if(p.pathsList.get(0).getDistance() > maxPath.get(0).getDistance()){
								maxPath.clear();
								maxPath.add(p.pathsList.get(0));
								maxPathString.clear();
								maxPathString.add(p.toString(jump.getLocation(), jumpInner.getLocation(), p.pathsList.get(0)));
								p.maxDist = maxPath.get(0).getDistance(); 		//
								System.out.println("----");
								System.out.println(p.maxDist);
								System.out.println(maxPathString.get(0));
							} else if(p.pathsList.get(0).getDistance() == maxPath.get(0).getDistance()){
								maxPath.add(p.pathsList.get(0));
								maxPathString.add(p.toString(jump.getLocation(), jumpInner.getLocation(), p.pathsList.get(0)));
							}
							} catch(Exception ex){
//								System.out.println(p.toString(jump.getLocation()) + p.toString(jumpInner.getLocation()));
							}
						}
					}
				}
			}
		}
		
		for(String s : maxPathString){
			System.out.println(s);
		}
	}
	*/
	
	public String toString(Location start, Location end, PathDistanceTupel path) {
		StringBuilder builder = new StringBuilder();

		builder.append("Distanz: ");
		builder.append(path.getDistance());
		builder.append(": ");
		builder.append(this.toString(start));
		// TODO: Gucken ob Queries zu rechenintensiv sind
		for (Integer id : path.getPath()) {
			JumpNode jump = Data.getJN(id);
			builder.append(" --> ");
			builder.append(this.toString(jump.getLocation()));
			builder.append(" -JN-> ");
			builder.append(this.toString(new Location(jump.getSystemOut(), jump.getXOut(), jump.getYOut())));
		}
		builder.append(" --> ");
		builder.append(this.toString(end));
		return builder.toString();
	}
	
	private String toString(Location loc) {
		return loc.getSystem() + ":" + loc.getX() + "/" + loc.getY();
	}
	
	/**
	 * Berechnet die Pfade vom Start zum Ende, ohne die JNs aus dem uebergebenen Set zu verwenden.
	 * Letztes Ergebnis kann mit {@link #getPathResults()} abgerufen werden.
	 * 
	 * @param start Startpunkt.
	 * @param end Endpunkt.
	 * @param jnsToAvoid Set der JN-IDs die nicht genutzt werden sollen oder null.
	 */
	public void calculatePaths(Location start, Location end, Set<Integer> jnsToAvoid) {
		clear();
		if (jnsToAvoid == null) {
			jnsToAvoid = new HashSet<Integer>();
		}
		fillPathList(start, end, jnsToAvoid, new ArrayList<Integer>(), 0);
	}
	
	/**
	 * Berechnet rekursiv alle Wege vom {@code start} bis {@code end}.
	 * 
	 * @param start Startpunkt.
	 * @param end Endpunkt.
	 * @param jnsToAvoid Set mit JN-IDs die nicht beutzt werden sollen. Ggf. inklusive bereits genutzter JNs.
	 * @param jnsUsed Liste der bereits genutzten JNs in entsprechender Reihenfolge.
	 * @param distance Bisher zurueck gelegte Distanz.
	 */
	private void fillPathList(Location start, Location end, Set<Integer> jnsToAvoid, List<Integer> jnsUsed, int distance) {
		// Wenn Start und Ziel im selben System liegen.
		if (end.getSystem() == start.getSystem()) {
			distance += getDistance(start, end);
			if (distance <= maxTestDist) {
				if (onlyBest) {
					if (distance < minDist) {
						minDist = distance;
						pathsList.clear();
						pathsList.add(new PathDistanceTupel(new ArrayList<Integer>(jnsUsed), distance));
					}
				} else {
					pathsList.add(new PathDistanceTupel(new ArrayList<Integer>(jnsUsed), distance));
				}
			}
			distance -= getDistance(start, end);
		}

		int tmpDistance;
		Set<Integer> tmpAvoid;
		List<Integer> tmpUsed;

		// Jeden JN im Startsystem nutzen falls erlaubt.
		List<JumpNode> sysJNs = Data.getJNs().get(start.getSystem());
		for (JumpNode jn : sysJNs) {
			if (jnsToAvoid.contains(jn.getId())) {
				continue;
			}
			tmpAvoid = new HashSet<Integer>(jnsToAvoid);
			tmpAvoid.add(jn.getId());
			Data.getOppositeJNs(jn).stream().map(JumpNode::getId).forEach(tmpAvoid::add);

			tmpUsed = new ArrayList<Integer>(jnsUsed);
			tmpUsed.add(jn.getId());

			tmpDistance = distance + getDistance(start, jn.getLocation());
			if (tmpDistance > maxTestDist) {
				continue;
			} else {
				if (onlyBest && distance > minDist) {
					continue;
				}
				fillPathList(new Location(jn.getSystemOut(), jn.getXOut(), jn.getYOut()), end, tmpAvoid, tmpUsed,
						tmpDistance);
			}
		}
	}
	
	/**
	 * @return Die der Distanz entsprechend sortiere Liste alle gefundenen Pfade.
	 */
	public List<PathDistanceTupel> getPathResults() {
		Collections.sort(pathsList);
		return this.pathsList;
	}
	
	// TODO: Vllt noch auslagern
	public int getDistance(Location l1, Location l2) {
		return Math.max(Math.abs(l1.getX() - l2.getX()), Math.abs(l1.getY() - l2.getY()));
	}
}