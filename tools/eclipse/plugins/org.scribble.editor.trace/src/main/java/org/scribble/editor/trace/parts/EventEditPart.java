/*
 * Copyright 2005-8 Pi4 Technologies Ltd
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
 * 15 May, 2008 : Initial version created by gary
 */
package org.scribble.editor.trace.parts;

import org.savara.scenario.model.*;
import org.scribble.editor.trace.figures.*;

/**
 * This class provides a 'lifeline item' part.
 */
public abstract class EventEditPart extends ScenarioBaseEditPart {

	public EventEditPart(Object elem) {
		super(elem);
	}
	
	public int getXOffset() {
		return(-10);
	}
	
	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	protected void refreshVisuals() {
		((EventFigure)getFigure()).setErrorExpected(
				((Event)getModel()).isErrorExpected());
		
		super.refreshVisuals();
	}

    public int getHeight() {
    	int ret=20; //ViewSupport.getHeight(getModel());
    	
    	return(ret);
    }
    
    public int getWidth() {
    	int ret=30; //ViewSupport.getWidth(getModel());
    	
    	return(ret);
    }
}
