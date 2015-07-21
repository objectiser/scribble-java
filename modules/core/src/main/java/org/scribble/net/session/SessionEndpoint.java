package org.scribble.net.session;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.ScribMessageFormatter;
import org.scribble.net.SocketWrapper;
import org.scribble.sesstype.name.Role;

// FIXME: factor out between role-endpoint based socket and channel-endpoint sockets
public class SessionEndpoint
{
	public final Session sess;
	//public final Principal self;
	public final Role self;
	public final ScribMessageFormatter smf;
	
	private boolean complete = false;

	//private final Map<Role, Principal> remroles = new HashMap<Role, Principal>();  // Doesn't include self
	private final Map<Role, SocketWrapper> sockets = new HashMap<Role, SocketWrapper>();   // Includes SelfSocketEndpoint

	//protected final LocalProtocolDecl root;
	//public final Monitor monitor;

	//protected SessionEndpoint(Session sess, Principal self) throws ScribbleException, IOException
	protected SessionEndpoint(Session sess, Role self, ScribMessageFormatter smf) //throws ScribbleException, IOException
	{
		this.sess = sess;
		this.self = self;
		this.smf = smf;

		/*ProtocolName lpn = Projector.makeProjectedProtocolName(sess.proto, this.self.role);
		this.root = (LocalProtocolDecl) projections.get(lpn.getPrefix()).protos.get(0);*/
		//this.monitor = createMonitor(sess.impath, sess.source, sess.proto, self);
	}
	
	//protected void setCompleted()
	public void setCompleted()
	{
		this.complete = true;	
	}
	
	public boolean isCompleted()
	{
		return this.complete;
	}

	// Only for remote endpoints (self SocketEndpoint is done in above constructor; but not recorded in role-principal map)
	//public void register(Principal remote, SocketWrapper sw) //throws IOException
	public void register(Role role, SocketWrapper sw) //throws IOException
	{
		/*this.remroles.put(remote.role, remote);
		this.sockets.put(remote.role, new SocketEndpoint(remote.role, this.inputq, sw));*/
		//this.sockets.put(remote.role, new SocketEndpoint(sw));
		//this.sockets.put(role, new SocketEndpoint(sw));
		this.sockets.put(role, sw);
	}
	
	/*public Set<Role> getRemoteRoles()
	{
		return this.remroles.keySet();
	}
	
	public Principal getRemotePrincipal(Role role)
	{
		return this.remroles.get(role);
	}*/
	
	//public SocketEndpoint getSocketEndpoint(Role role)
	public SocketWrapper getSocketWrapper(Role role) throws ScribbleRuntimeException
	{
		if (!this.sockets.containsKey(role))
		{
			throw new ScribbleRuntimeException(this.self + " is not connected to: " + role);
		}
		return this.sockets.get(role);
	}
	
	public void close()
	{
		//for (SocketEndpoint se : this.sockets.values())
		for (SocketWrapper se : this.sockets.values())
		{
			try
			{
				se.close();
			}
			catch (IOException e)
			{
				// FIXME:
			}
		}		
	}

	/*// proto is fullname
	private static Monitor createMonitor(List<String> impath, String source, ProtocolName proto, Role self) throws ScribbleException, IOException
	{
		// FIXME: API (strings vs non-strings, simple vs full names)
		Job job = Tool.getWellFormedJob(impath, source);
		Map<ModuleName, Module> projections = Tool.getProjections(job, proto.getSimpleName().toString(), self.toString());
		Map<ProtocolName, ProtocolState> graphs = Tool.buildGraphs(job, projections);

		ProtocolName lpn = Projector.makeProjectedProtocolName(proto, self);
		System.out.println("[DEBUG] Projected graph: " + graphs.get(lpn).toDot());
		
		return new Monitor(graphs, lpn);
	}*/
	
	/*public Set<Role> getRemoteRoles()
	{
		return this.remroles.keySet();
	}
	
	public Principal getRemotePrincipal(Role role)
	{
		return this.remroles.get(role);
	}*/
}