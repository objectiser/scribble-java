package org.scribble.visit;

import java.util.List;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LDo;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.name.simple.DummyProjectionRoleNode;
import org.scribble.del.ModuleDel;
import org.scribble.main.ScribbleException;

// Basically infers all local choice subject candidates: if *none* are found for a given "choice-unguarded" do-call then the call is pruned (along with parent block and choice as necessary)
// i.e. prune if no choice subject candidates, because that means (passive) role is never enabled and thus not involved
// FIXME: ambiguous choice subject (i.e. > 1 candidate) is checked subsequently by ProjectedChoiceSubjectFixer -- should be better integrated (e.g. reuse ChoiceUnguardedSubprotocolChecker, rather than adhoc LInteractionNode.inferLocalChoiceSubject) -- NOTE: but cannot do all pruning and fixing in one pass, as fixing the subject roles here will interfere with the pruning algorithm (currently it looks for dummy role choices)
public class ProjectedSubprotocolPruner extends ModuleContextVisitor
{
	public ProjectedSubprotocolPruner(Job job)
	{
		super(job);
	}
	
	
	/*@Override
	protected ProjectedSubprotocolPruningEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new ProjectedSubprotocolPruningEnv();
	}*/

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof LProtocolBlock && parent instanceof LChoice)
		{
			LChoice lc = (LChoice) parent;
			LProtocolBlock lb = (LProtocolBlock) child;
			LInteractionSeq lis = lb.getInteractionSeq();
			List<LInteractionNode> ins = lis.getInteractions();
			if (ins.get(0) instanceof LDo)  // Unlike GRecursion.prune, to-prune "do" could be followed by a continuation?
			{
				JobContext jc = getJobContext();
				LDo ld = (LDo) ins.get(0);
				LProtocolDecl lpd = ld.getTargetProtocolDecl(jc, getModuleContext());
				
				//System.out.println("111: " + ld);
				
				ChoiceUnguardedSubprotocolChecker checker = new ChoiceUnguardedSubprotocolChecker(getJob(),
						((ModuleDel) jc.getModule(ld.proto.toName().getPrefix()).del()).getModuleContext(), lc);
				lpd.accept(checker);
				
				//ChoiceUnguardedSubprotocolEnv env = (ChoiceUnguardedSubprotocolEnv) lpd.def.block.del().env();
			
				/*if (lpd.header.getDeclName().toString().endsWith("_suppliersvc"))
				{
					System.out.println("GGG: " + ld + ", " + env.subjs);
				}*/
				//System.out.println("GGG: " + ld + ", " + env.subjs + ", " + checker.SHOULD_PRUNE);

				//if ((lc.subj instanceof DummyProjectionRoleNode) && env.subjs.isEmpty())
				if ((lc.subj instanceof DummyProjectionRoleNode) && checker.shouldPrune())
				{
					//System.out.println("GGG: " + ld + ", " + env.subjs);
					//if (ins.size() > 1)
					{
					return AstFactoryImpl.FACTORY.LProtocolBlock(AstFactoryImpl.FACTORY.LInteractionSeq(ins.subList(1, ins.size())));  // Supports singleton case
					}
					/*else
					{
						return AstFactoryImpl.FACTORY.LProtocolBlock(AstFactoryImpl.FACTORY.LInteractionSeq(Collections.emptyList()));
					}*/
				}
			}
		}
		return super.visit(parent, child);
	}

	@Override
	//protected void subprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	public void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		//super.subprotocolEnter(parent, child);
		super.enter(parent, child);
		child.del().enterProjectedSubprotocolPruning(parent, child, this);
	}
	
	@Override
	//protected ScribNode subprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	public ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveProjectedSubprotocolPruning(parent, child, this, visited);
		//return super.subprotocolLeave(parent, child, visited);
		return super.leave(parent, child, visited);
	}
}
