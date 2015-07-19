package org.scribble.visit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.NonRoleArgKind;
import org.scribble.sesstype.kind.NonRoleParamKind;
import org.scribble.sesstype.name.Name;
import org.scribble.sesstype.name.RecVar;
import org.scribble.sesstype.name.Role;

// Disambiguates ambiguous PayloadTypeOrParameter names and inserts implicit Scope names
public class NameDisambiguator extends ModuleVisitor
{
  // For implicit scope generation: reset per ProtocolDecl
	//private int counter = 1;

	private Set<Role> roles = new HashSet<>();
	private Map<String, NonRoleParamKind> params = new HashMap<>();
	private Set<RecVar> recvars = new HashSet<RecVar>();

	public NameDisambiguator(Job job)
	{
		super(job);
	}

	/*public ScopeNode getFreshScope()
	{
		return new ScopeNode(null, Scope.IMPLICIT_SCOPE_PREFIX + "." + counter++);
	}*/

	@Override
	//public NameDisambiguator enter(ModelNode parent, ModelNode child) throws ScribbleException
	public void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.enter(parent, child);
		child.del().enterDisambiguation(parent, child, this);
	}
	
	@Override
	public ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveDisambiguation(parent, child, this, visited);
		return super.leave(parent, child, visited);
	}
	
	public void clear()
	{
		//this.counter = 1;
		this.roles.clear();
		this.params.clear();
		this.recvars.clear();  // Should be unnecessary
	}
	
	public void addRole(Role role)
	{
		this.roles.add(role);
	}
	
	public boolean isBoundRole(Role role)
	{
		return this.roles.contains(role);
	}

	public void addParameter(Name<? extends NonRoleParamKind> param, NonRoleParamKind kind)
	{
		this.params.put(param.toString(), kind);
	}
	
	// name is a simple name (compound names are not ambiguous)
	public boolean isBoundParameter(Name<? extends NonRoleArgKind> name)  // ArgKind allows AmbigNames
	{
		return this.params.containsKey(name.toString());
	}

	public NonRoleParamKind getParameterKind(Name<? extends NonRoleArgKind> name)  // ArgKind allows AmbigNames
	{
		return this.params.get(name.toString());
	}

	public void addRecVar(RecVar rv)
	{
		this.recvars.add(rv);
	}

	public boolean isBoundRecVar(RecVar rv)
	{
		return this.recvars.contains(rv);
	}
	
	public void removeRecVar(RecVar rv)
	{
		this.recvars.remove(rv);
	}
}
