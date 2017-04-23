package uniovi.asw.hello.listeners;

import javax.annotation.ManagedBean;

import org.apache.log4j.Logger;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Created by herminio on 28/12/16.
 */
@ManagedBean
@SessionScope
public class MessageListener {

	private static final Logger logger = Logger.getLogger(MessageListener.class);

	public static Logger getLogger() {
		return logger;
	}


}
