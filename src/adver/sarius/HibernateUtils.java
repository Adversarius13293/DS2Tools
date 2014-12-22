package adver.sarius;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

import net.driftingsouls.ds2.server.config.StarSystem;
import net.driftingsouls.ds2.server.entities.JumpNode;

public class HibernateUtils {
	
	public static SessionFactory factory;
		
	public static Session createSession(){
		if(factory==null) HibernateUtils.initConnection();
		Session session = factory.openSession();
		session.setDefaultReadOnly(true);
		return session;
	}
	
	public static void initConnection(){
		try{
//			factory = new AnnotationConfiguration().addAnnotatedClass(JumpNode.class).configure().buildSessionFactory();
			Configuration config = new Configuration().configure();
			ServiceRegistryBuilder builder = new ServiceRegistryBuilder();
			builder.applySettings(config.getProperties());

			factory = config.buildSessionFactory(builder.buildServiceRegistry());
			
		} catch(HibernateException ex){
			System.out.println("Fehler: " + ex + "\nA:" + ex.getCause());
		}
	}

}
