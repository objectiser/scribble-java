/*
 * Copyright 2005 Pi4 Technologies Ltd
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
 * Jul 14, 2005 : Initial version created by gary
 */
package org.scribble.editor.trace.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.scribble.editor.trace.commands.DeleteComponentCommand;
import org.scribble.editor.trace.model.ModelSupport;
import org.scribble.editor.trace.parts.ScenarioBaseEditPart;

/**
 * This is the component edit policy.
 */
public class ScenarioComponentEditPolicy extends ComponentEditPolicy {

	/**
	 * @see ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		Object parent = (getHost().getParent().getModel());
		DeleteComponentCommand deleteCmd = new DeleteComponentCommand();
		
		Object child=(Object)(getHost().getModel());
		
		if (getHost() instanceof ScenarioBaseEditPart) {
			deleteCmd.setScenario(((ScenarioBaseEditPart)getHost()).getScenarioDiagram().getScenario());			
		}
		
		deleteCmd.setChild(child);
		
		if (getHost().getParent() instanceof ScenarioBaseEditPart) {
			deleteCmd.setParent(((ScenarioBaseEditPart)getHost().getParent()).getModel());
		}
		
		int index=ModelSupport.getChildIndex(parent, child);
		
		deleteCmd.setIndex(index);
		
		return deleteCmd;
	}
}
