package org.scribble.model.global;

import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

public class GSend extends GIOAction
{
	public GSend(Role subj, Role obj, MessageId<?> mid, Payload payload)
	{
		super(subj, obj, mid, payload);
	}
	
	@Override
	public boolean isSend()
	{
		return true;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GSend))
		{
			return false;
		}
		return ((GSend) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof GSend;
	}

	@Override
	protected String getCommSymbol()
	{
		return "!";
	}
}
