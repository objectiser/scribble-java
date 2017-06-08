/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.del.local;

import java.util.Arrays;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ProtocolDef;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.local.LProtocolDef;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ProtocolDefDel;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.SubprotocolSig;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.env.InlineProtocolEnv;

public class LProtocolDefDel extends ProtocolDefDel
{
	public LProtocolDefDel()
	{

	}

	@Override
	protected LProtocolDefDel copy()
	{
		LProtocolDefDel copy = new LProtocolDefDel();
		copy.inlined = this.inlined;
		return copy;
	}

	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		CommonTree blame = ((LProtocolDecl) parent).header.getSource();  // Cf., GProtocolDefDel
		SubprotocolSig subsig = inl.peekStack();
		LProtocolDef def = (LProtocolDef) visited;
		LProtocolBlock block = (LProtocolBlock) ((InlineProtocolEnv) def.block.del().env()).getTranslation();	
		RecVarNode recvar = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(blame,  // The parent do would probably be the better blame source
				RecVarKind.KIND, inl.getSubprotocolRecVar(subsig).toString());
		LRecursion rec = AstFactoryImpl.FACTORY.LRecursion(blame, recvar, block);
		LInteractionSeq lis = AstFactoryImpl.FACTORY.LInteractionSeq(blame, Arrays.asList(rec));
		LProtocolDef inlined = AstFactoryImpl.FACTORY.LProtocolDef(def.getSource(), AstFactoryImpl.FACTORY.LProtocolBlock(blame, lis));
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		LProtocolDefDel copy = setInlinedProtocolDef(inlined);
		return (LProtocolDef) ScribDelBase.popAndSetVisitorEnv(this, inl, (LProtocolDef) def.del(copy));
	}
	
	@Override
	public LProtocolDef getInlinedProtocolDef()
	{
		return (LProtocolDef) super.getInlinedProtocolDef();
	}

	@Override
	public LProtocolDefDel setInlinedProtocolDef(ProtocolDef<? extends ProtocolKind> inlined)
	{
		return (LProtocolDefDel) super.setInlinedProtocolDef(inlined);
	}
}
