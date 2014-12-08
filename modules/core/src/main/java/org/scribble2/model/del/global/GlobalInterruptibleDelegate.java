package org.scribble2.model.del.global;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.context.ModuleContext;
import org.scribble2.model.Choice;
import org.scribble2.model.Do;
import org.scribble2.model.InteractionNode;
import org.scribble2.model.InteractionSequence;
import org.scribble2.model.Interrupt;
import org.scribble2.model.Interruptible;
import org.scribble2.model.ModelNode;
import org.scribble2.model.Parallel;
import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.Recursion;
import org.scribble2.model.del.CompoundInteractionNodeDelegate;
import org.scribble2.model.global.GlobalChoice;
import org.scribble2.model.visit.JobContext;
import org.scribble2.model.visit.SubprotocolVisitor;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.sesstype.Message;
import org.scribble2.sesstype.ScopedMessage;
import org.scribble2.sesstype.ScopedMessageSignature;
import org.scribble2.sesstype.ScopedSubprotocolSignature;
import org.scribble2.sesstype.SubprotocolSignature;
import org.scribble2.sesstype.name.Operator;
import org.scribble2.sesstype.name.Role;
import org.scribble2.sesstype.name.Scope;
import org.scribble2.sesstype.name.ScopedMessageParameter;
import org.scribble2.util.MessageMap;
import org.scribble2.util.ScribbleException;

public class GlobalInterruptibleDelegate extends CompoundInteractionNodeDelegate
{
	public void leave(Interruptible<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>, ? extends Interrupt> intt,
			WellFormedChoiceChecker checker)
	{
		WellFormedChoiceEnv ienv = checker.getEnv();
		WellFormedChoiceEnv merged = merge((WellFormedChoiceEnv) intt.block.getEnv());
		merged.initial.merge(ienv.initialInterrupts);
		intt.setEnv(merged);

		WellFormedChoiceEnv parent = ienv.pop();
		parent = parent.merge(merged);
		checker.setEnv(parent);
	}
}
