/*
 * Copyright 2005-7 Pi4 Technologies Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Change History:
 * Jul 7, 2005 : Initial version created by gary
 */
package org.scribble.editor.trace.commands;

import org.scribble.editor.trace.model.*;
import org.scribble.editor.trace.model.Role;
import org.scribble.editor.trace.tools.CreateLinksTool;

/**
 * This class implements the activity creation command.
 */
public class PasteComponentCommand
			extends org.eclipse.gef.commands.Command {
	
	public PasteComponentCommand() {
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		
		// Check if child is a scenario event
		/* GPB: TO CHECK
		if (m_child instanceof RoleEvent &&
				m_role != null) {
			((RoleEvent)m_child).setRole(m_role);
		}
		*/
		
		// note that the model adds the ports to the node in this call
		ModelSupport.addChild(m_parent, m_child, m_index);
	}
	
	public Object getParent() {
		return(m_parent);
	}
	
	public void setRole(Role role) {
		m_role = role;
	}
	
	public void redo() {
		ModelSupport.addChild(m_parent, m_child, m_index);
	}
	
	public void setChild(Object newNode) {
		m_child = newNode;
	}
	
	public void setIndex(int index) {
		m_index = index;
	}
	
	public void setParent(Object newParent) {
		m_parent = newParent;
	}
	
	public void undo() {
		for (int i=0; i < m_additionalLinks.size(); i++) {
			/* TODO: GPB: need scenario
			((Group)m_child).getScenario().getMessageLinks().remove(
						m_additionalLinks.get(i));
				*/
		}
		
		m_additionalLinks.clear();

		ModelSupport.removeChild(m_parent, m_child);
	}

	private Object m_child=null;
	private Object m_parent=null;
	private Role m_role=null;
	private int m_index = -1;
	private java.util.Vector m_additionalLinks=new java.util.Vector();
}
