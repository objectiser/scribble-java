/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and others contributors as indicated
 * by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.scribble.editor.trace.properties;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.savara.common.util.MessageUtils;
import org.savara.scenario.model.Parameter;

public class ParametersPropertySection extends AbstractPropertySection {
	
	private ParameterListPropertySection m_parameters=
		new ParameterListPropertySection("parameter",
				"Parameters", "Parameters", 0, 100);
	//private ScenarioDesigner m_designer=null;

	private static final Logger logger=Logger.getLogger(ParametersPropertySection.class.getName());
	
	public ParametersPropertySection() {
	}
	
    public void aboutToBeShown() {
    	super.aboutToBeShown();
    	
    	m_parameters.aboutToBeShown();
    }
    
    public void aboutToBeHidden() {
    	super.aboutToBeHidden();

    	m_parameters.aboutToBeHidden();
    }
    
    public void createControls(Composite parent,
            TabbedPropertySheetPage aTabbedPropertySheetPage) {
        super.createControls(parent, aTabbedPropertySheetPage);
	
        Composite composite = getWidgetFactory()
					.createFlatFormComposite(parent);

        m_parameters.setToolTip("The parameters");
        m_parameters.setTextGap(100);
        m_parameters.createControls(composite,
        		aTabbedPropertySheetPage);        
    }
    
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);
        
        m_parameters.setInput(part, selection);
        
        //if (part instanceof ScenarioDesigner) {
        //	m_designer = (ScenarioDesigner)part;
        //}
    }
    
    public void refresh() {    	
    	super.refresh();
    	
    	m_parameters.refresh();
    }
    
    public int getMinimumHeight() {
    	return(400);
    }
    
    public void dispose() {    	
    	super.dispose();
    	
    	m_parameters.dispose();
    }
	
	public class ParameterListPropertySection extends AbstractListPropertySection {
		
		public ParameterListPropertySection(String property, String displayName,
				String label, int start, int end) {
			super(property, displayName, label, start, end);
		}

		/**
		 * This method requests a new list object to add to the
		 * list.
		 * 
		 * @return The new object, or null if no object to add
		 */
		protected Object createNewObject() {
			Parameter param=new Parameter();
			param.setType("");
			param.setValue("");
			
			ParameterEditor pe=new ParameterEditor(getPart().getSite().getShell());
			
			pe.setParameter(param);
			
			return((Parameter)pe.open());
		}
		
		/**
		 * This method requests that the supplied object be edited.
		 * 
		 * @param obj The object to edit
		 * @return Changed object, or null if not changed
		 */
		protected Object editObject(Object obj) {
			
			if ((obj instanceof Parameter) == false) {
				logger.severe("Edited object was not parameter");
				return(false);
			}
			
			Parameter p=new Parameter();
			p.setType(((Parameter)obj).getType());
			p.setValue(((Parameter)obj).getValue());
			
			ParameterEditor pe=new ParameterEditor(getPart().getSite().getShell());
			
			pe.setParameter(p);
			
			return(pe.open());
		}

		protected String getDisplayValue(Object sourceValue) {
	    	String ret="";

	    	if (sourceValue instanceof Parameter) {
	    		Parameter p=(Parameter)sourceValue;
	    		
	    		if (p.getType() != null && p.getType().trim().length() > 0) {
	    			ret += p.getType();
	    		}

	    		ret += " [";
	    		
	    		if (p.getValue() != null && p.getValue().trim().length() > 0) {
	    			ret += p.getValue();
	    		}

	    		ret += "]";
	    	}
	    	
	    	return(ret);
	    }
	}
	
	public class ParameterEditor extends Dialog {
		private Parameter m_parameter=null;
		private Combo m_type=null;
		private Text m_value=null;
		private boolean m_ok=false;
		
        public ParameterEditor(Shell parent, int style) {
            super (parent, style);
	    }
        
	    public ParameterEditor(Shell parent) {
	    	this (parent, 0);
	    }
	    
	    public void setParameter(Parameter p) {
	    	m_parameter = p;
	    }
	    
	    public Object open() {
	    	Shell parent = getParent();
	    	
	    	final Shell dialog = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	    	dialog.setText(getText());
	    	
	    	Button messageFileButton = new Button (dialog, SWT.PUSH);
	    	messageFileButton.setText ("Value File Path:");
	    	
	    	m_value = new Text(dialog, SWT.NONE);

	    	if (m_parameter != null && m_parameter.getValue() != null &&
	    			m_parameter.getValue().trim().length() > 0) {
	    		m_value.setText(m_parameter.getValue());
	    	}
	    	
	    	/* GPB: Change message file to text content
	    	messageFileButton.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
				public void widgetSelected(SelectionEvent evt) {
					FileDialog fd=new FileDialog(dialog);
					
					if (m_designer != null && m_designer.getFile() != null) {
						fd.setFilterPath(m_designer.getFile().getParent().getRawLocation().toPortableString());
					}

					String filename=fd.open();
					
					if (filename != null) {
						String fullPath=filename;
						
						// Find relative path
						if (m_designer != null && m_designer.getFile() != null) {
							Path p=new Path(filename);
							IPath relativePath=p.makeRelativeTo(m_designer.getFile().getParent().getLocation());
							
							filename = relativePath.toPortableString();
						}
						
						m_value.setText(filename);
						
						// If type has not been set, then see whether this
						// can be obtained from the message content
						if (m_type.getText() == null || m_type.getText().trim().length() == 0) {
							m_type.setText(getMessageType(fullPath));
						}
					}
				}
				
				public void widgetDefaultSelected(SelectionEvent evt) {					
				}
			});
			*/

			Label typeLabel = new Label (dialog, SWT.NONE);
	    	typeLabel.setText("Content Type:");
	    	
	    	m_type = new Combo(dialog, SWT.NONE);
	    	
	    	if (m_parameter != null && m_parameter.getType() != null &&
	    			m_parameter.getType().trim().length() > 0) {
	    		m_type.setText(m_parameter.getType());
	    	}
	    	
	    	Button okButton = new Button (dialog, SWT.PUSH);
	    	okButton.setText ("&OK");
	    	Button cancelButton = new Button (dialog, SWT.PUSH);
	    	cancelButton.setText ("&Cancel");
	    	
	    	FormLayout form = new FormLayout();
	    	form.marginWidth = form.marginHeight = 8;
	    	dialog.setLayout(form);
	    	
	    	FormData messageFileButtonData = new FormData();
	    	messageFileButtonData.top = new FormAttachment(4);
	    	messageFileButtonData.width = 120;
	    	messageFileButtonData.height = 30;
	    	messageFileButton.setLayoutData(messageFileButtonData);
	    	
	    	FormData valueData = new FormData();
	    	valueData.left = new FormAttachment(messageFileButton, 8);
	    	valueData.width = 400;
	    	valueData.height = 30;
	    	m_value.setLayoutData(valueData);
	    	
	    	FormData label2Data = new FormData();
	    	label2Data.top = new FormAttachment(messageFileButton, 8);
	    	label2Data.width = 120;
	    	label2Data.height = 30;
	    	typeLabel.setLayoutData(label2Data);
	    	
	    	FormData typeData = new FormData();
	    	typeData.left = new FormAttachment(typeLabel, 8);
	    	typeData.top = new FormAttachment(m_value, 8);
	    	typeData.width = 400;
	    	typeData.height = 30;
	    	m_type.setLayoutData(typeData);
	    	
	    	FormData okData = new FormData();
	    	okData.top = new FormAttachment(m_type, 8);
	    	okData.left = new FormAttachment(0, 150);
	    	okData.width = 80;
	    	okButton.setLayoutData(okData);
	    	
	    	FormData cancelData = new FormData();
	    	cancelData.left = new FormAttachment(okButton, 8);
	    	cancelData.top = new FormAttachment(m_type, 8);
	    	cancelData.width = 80;
	    	cancelButton.setLayoutData(cancelData);
	    	
	    	dialog.setDefaultButton(okButton);
	    	dialog.pack();
	    	dialog.open();

	    	okButton.addSelectionListener(new SelectionListener() {

				public void widgetDefaultSelected(SelectionEvent arg0) {
					widgetSelected(arg0);
				}

				public void widgetSelected(SelectionEvent arg0) {
					m_parameter.setType(m_type.getText());
					m_parameter.setValue(m_value.getText());
					m_ok = true;
					dialog.dispose();
				}	    		
	    	});
	    	
	    	cancelButton.addSelectionListener(new SelectionListener() {

				public void widgetDefaultSelected(SelectionEvent arg0) {
					widgetSelected(arg0);
				}

				public void widgetSelected(SelectionEvent arg0) {
					dialog.dispose();
				}
	    	});
	    	
	    	dialog.open();
	    	Display display = parent.getDisplay();
	    	while (!dialog.isDisposed()) {
	    		if (!display.readAndDispatch()) display.sleep();
	    	}
	    	
	    	return(m_ok ? m_parameter : null);
	    }
	}
	
	/**
	 * This method returns the message type associated with the
	 * supplied file content.
	 * 
	 * @param filePath The file path
	 * @return The message type, or null if it could not be determined
	 */
	protected String getMessageType(String filePath) {
		String ret=null;
		
		try {
			java.io.InputStream is=new java.io.FileInputStream(filePath);
			
			byte[] b=new byte[is.available()];
			is.read(b);
			
			is.close();
			
			String content=new String(b);
			
			ret = MessageUtils.getMessageType(content);
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to obtain message type from the content of file '"+filePath+"'", e);
		}
		
		return (ret);
	}
}
