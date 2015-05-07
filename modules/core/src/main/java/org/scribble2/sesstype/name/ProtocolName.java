package org.scribble2.sesstype.name;

import org.scribble2.sesstype.kind.ProtocolKind;


// Potentially qualified/canonical protocol name; not the AST primitive identifier
//public class ProtocolName<K extends ProtocolKind> extends MemberName<K>
public class ProtocolName extends MemberName<ProtocolKind>
{
	private static final long serialVersionUID = 1L;

	//public ProtocolName(K kind, ModuleName modname, String membname)
	//public ProtocolName(K kind, ModuleName modname, ProtocolName<K> membname)
	public ProtocolName(ModuleName modname, ProtocolName membname)
	{
		//super(KindEnum.PROTOCOL, modname, membname);
		//super(kind, modname, membname);
		super(ProtocolKind.KIND, modname, membname);
	}
	
	//public ProtocolName(K kind, String membname)
	public ProtocolName(String membname)
	{
		//super(KindEnum.PROTOCOL, membname);
		super(ProtocolKind.KIND, membname);
	}

	@Override
	//public ProtocolName<K> getSimpleName()
	public ProtocolName getSimpleName()
	{
		//return new ProtocolName<>(this.kind, getLastElement());
		return new ProtocolName(getLastElement());
	}
}
