package org.scribble2.parser.ast.global;

import org.scribble2.parser.ast.InteractionNode;


// Alternatively to interface, use GlobalNode subclass with delegation to "super" base (e.g. Choice) classes
public interface GlobalInteraction extends InteractionNode, GlobalNode
{
	
}
