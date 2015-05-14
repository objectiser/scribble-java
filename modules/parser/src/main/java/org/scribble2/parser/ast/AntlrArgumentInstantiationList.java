package org.scribble2.parser.ast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ArgumentInstantiation;
import org.scribble2.model.ArgumentInstantiationList;
import org.scribble2.model.ArgumentNode;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.parser.AntlrConstants.AntlrNodeType;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.ast.name.AntlrAmbiguousName;
import org.scribble2.parser.util.Util;

public class AntlrArgumentInstantiationList
{
	//public static final String EMPTY_ARGUMENTLIST = "EMPTY_ARGUMENT_LIST";

	// Similar to parseParameterDeclList
	public static ArgumentInstantiationList parseArgumentInstantiationList(ScribbleParser parser, CommonTree ct)
	{
		List<ArgumentInstantiation> as = new LinkedList<>();
		for (CommonTree a : getArgumentChildren(ct))
		{
			AntlrNodeType type = Util.getAntlrNodeType(a);
			if (type == AntlrNodeType.MESSAGESIGNATURE)
			{
				//as.add((ArgumentInstantiation) parser.parse(a));
				ArgumentNode arg = (ArgumentNode) parser.parse(a);
				as.add(ModelFactoryImpl.FACTORY.ArgumentInstantiation(arg));
			}
			else
			{
				as.add(ModelFactoryImpl.FACTORY.ArgumentInstantiation(AntlrAmbiguousName.toAmbiguousNameNode(a)));
			}
		}
		//return new ArgumentInstantiationList(as);
		return ModelFactoryImpl.FACTORY.ArgumentInstantiationList(as);
	}

	public static List<CommonTree> getArgumentChildren(CommonTree ct)
	{
		if (ct.getChildCount() == 0)
		{
			return Collections.emptyList();
		}
		return Util.toCommonTreeList(ct.getChildren());
	}
}
