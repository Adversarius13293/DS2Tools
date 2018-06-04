package adver.sarius.ds2tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Session;

import net.driftingsouls.ds2.server.entities.JumpNode;

public class Data {

	private static Map<Integer, List<JumpNode>> jns;

	public static Map<Integer, List<JumpNode>> getJNs() {
		if (jns == null) {
			Session db = HibernateUtils.createSession();
			List<JumpNode> jumpNodes = (List<JumpNode>) db.createQuery("FROM JumpNode WHERE hidden=false").list();
			db.close();
			jns = new HashMap<Integer, List<JumpNode>>();
			for (JumpNode jn : jumpNodes) {
				List<JumpNode> list = jns.get(jn.getSystem());
				if (list == null) {
					list = new ArrayList<JumpNode>();
					jns.put(jn.getSystem(), list);
				}
				list.add(jn);
			}
		}
		return jns;
	}
	
	/**
	 * Gibt den JN mit der entsprechenden Id zurueck.
	 * @param id Interne Id des JNs.
	 * @return Zur Id gehoeriger JN.
	 */
	public static JumpNode getJN(int id) {
		Optional<JumpNode> result = getJNs().values().stream().flatMap(list -> list.stream()
				.filter(j -> j.getId() == id)).findAny();
		return result.orElse(null); // TODO: some default
	}

	/**
	 * Gibt alle JNs zurück, die beim uebergebenen JN landen wuerden. 
	 * In der Regel gibt es genau 1 Gegenstueck.
	 * 
	 * @param jn Ziel-JN zu dem andere JNs ermittelt werden sollen.
	 * @return Alle JNs die zum JN fuehren.
	 */
	public static Set<JumpNode> getOppositeJNs(JumpNode jn) {
		return getJNs().values().stream().flatMap(list -> list.stream()
				.filter(j -> j.getSystemOut() == jn.getSystem()
					&& j.getXOut() == jn.getX()
					&& j.getYOut() == jn.getY())).collect(Collectors.toSet());
	}
}