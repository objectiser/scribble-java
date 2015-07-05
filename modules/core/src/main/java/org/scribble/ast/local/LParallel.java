package org.scribble.ast.local;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.Parallel;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ScribNodeBase;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ProjectedChoiceSubjectFixer;

public class LParallel extends Parallel<Local> implements LCompoundInteractionNode
{
	public LParallel(List<LProtocolBlock> blocks)
	{
		super(blocks);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LParallel(getBlocks());
	}
	
	@Override
	public LParallel clone()
	{
		//List<:ProtocolBlock> blocks = ScribUtil.cloneList(getBlocks());
		throw new RuntimeException("TODO: " + this);
	}
	
	@Override
	protected LParallel reconstruct(List<? extends ProtocolBlock<Local>> blocks)
	{
		ScribDel del = del();
		LParallel lp = new LParallel(castBlocks(blocks));
		lp = (LParallel) lp.del(del);
		return lp;
	}

	@Override
	public List<LProtocolBlock> getBlocks()
	{
		return castBlocks(this.blocks);
	}

	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		return getBlocks().get(0).getInteractionSeq().getActions().get(0).inferLocalChoiceSubject(fixer);
	}
	
	private static List<LProtocolBlock> castBlocks(List<? extends ProtocolBlock<Local>> blocks)
	{
		return blocks.stream().map((b) -> (LProtocolBlock) b).collect(Collectors.toList());
	}
}
