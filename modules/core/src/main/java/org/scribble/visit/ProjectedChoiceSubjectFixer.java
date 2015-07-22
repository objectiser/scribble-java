package org.scribble.visit;

import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;

public class ProjectedChoiceSubjectFixer extends ModuleContextVisitor
{
	public ProjectedChoiceSubjectFixer(Job job)
	{
		super(job);
	}

	@Override
	protected final void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.enter(parent, child);
		child.del().enterProjectedChoiceSubjectFixing(parent, child, this);
	}
	
	@Override
	protected ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveProjectedChoiceSubjectFixing(parent, child, this, visited);
		return super.leave(parent, child, visited);
	}
}
