package org.scribble2.net;

import java.io.IOException;

import org.scribble2.net.session.SessionEndpoint;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleRuntimeException;

public abstract class SendSocket extends ScribSocket
{
	protected SendSocket(SessionEndpoint ep)
	{
		super(ep);
	}

	protected void writeScribMessage(Role role, ScribMessage msg) throws IOException, ScribbleRuntimeException
	{
		//System.out.println("Write: " + msg);

		use();
		//this.ep.getSocketWrapper(role).writeObjectAndFlush(o);
		//this.ep.getSocketWrapper(role).dos.write(this.ep.smf.toBytes(msg));
		this.ep.smf.writeMessage(this.ep.getSocketWrapper(role).dos, msg);
	}
}
