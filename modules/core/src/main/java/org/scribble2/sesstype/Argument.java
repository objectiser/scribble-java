package org.scribble2.sesstype;

import org.scribble2.sesstype.name.KindEnum;

public interface Argument
{
	KindEnum getKindEnum();

	boolean isParameter();
}
