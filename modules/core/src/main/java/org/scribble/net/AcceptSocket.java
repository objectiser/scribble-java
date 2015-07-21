package org.scribble.net;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

public abstract class AcceptSocket extends ScribSocket
{
	protected AcceptSocket(SessionEndpoint ep)
	{
		super(ep);
	}

	public void accept(ScribServerSocket ss, Role role) throws IOException, ScribbleRuntimeException
	{
		use();
		this.ep.register(role, ss.accept());
	}
}