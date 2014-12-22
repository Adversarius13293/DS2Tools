package adver.sarius;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.driftingsouls.ds2.server.entities.JumpNode;

import org.hibernate.Session;

public class Data {

	
	private static Map<Integer, List<JumpNode>> jns;
	
	public static Map<Integer, List<JumpNode>> getJNs(){
		if(jns==null){
			Session db = HibernateUtils.createSession();
			List<JumpNode> jumpNodes = (List<JumpNode>)db.createQuery("FROM JumpNode WHERE hidden=false").list();
			db.close();
			jns = new HashMap<Integer, List<JumpNode>>();
			for(JumpNode jn : jumpNodes){
				List<JumpNode> list = jns.get(jn.getSystem());
				if(list == null){
					list = new ArrayList<JumpNode>();
					jns.put(jn.getSystem(), list);
				}
				list.add(jn);
			}
		}
		return jns;
	}
	
	/**
	 * Gibt die ID des gegenueberliegenenden JNs zurueck, falls es diesen gibt. 
	 * Ansonsten wird -1 zurueckgegeben.
	 * 
	 * @param jn
	 * @return
	 */
	public static int getOppositeJN(JumpNode jn){
		Session db = HibernateUtils.createSession(); // TODO: Gucken ob zu viele creates
		List<?> opJns = db.createQuery("FROM JumpNode WHERE systemOut=:sys AND xOut=:x AND yOut=:y AND system=:systemOut AND x=:xOut and y=:yOut")
			.setInteger("sys", jn.getSystem())
			.setInteger("x", jn.getX())
			.setInteger("y", jn.getY())
			.setInteger("systemOut", jn.getSystemOut())
			.setInteger("xOut", jn.getXOut())
			.setInteger("yOut", jn.getYOut()).list();
		db.close();
		if(opJns.isEmpty()){
//			return jn.getId();
			return -1;
		} else{
			return ((JumpNode)opJns.get(0)).getId();
		} // TODO: Was wenn mehr als 1 JN?
		
	}

	
	
}
