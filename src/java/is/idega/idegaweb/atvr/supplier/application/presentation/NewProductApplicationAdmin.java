/*
 * $Id:$
 *
 * Copyright (C) 2002 Idega hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 *
 */
package is.idega.idegaweb.atvr.supplier.application.presentation;

import com.idega.core.user.data.User;
import com.idega.presentation.Block;
import com.idega.presentation.IWContext;
import com.idega.presentation.Table;
import com.idega.presentation.text.Link;
import com.idega.presentation.ui.CheckBox;
import com.idega.presentation.ui.SubmitButton;

import is.idega.idegaweb.atvr.supplier.application.business.NewProductApplicationBusiness;
import is.idega.idegaweb.atvr.supplier.application.data.NewProductApplication;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class does something very clever.....
 * 
 * @author <a href="palli@idega.is">Pall Helgason</a>
 * @version 1.0
 */
public class NewProductApplicationAdmin extends Block {
	private final static String IW_BUNDLE_IDENTIFIER = "is.idega.idegaweb.atvr";

	protected final static String PARAM_FORM_SUBMIT = "npaa_submit";
	protected final static String PARAM_CHECKBOX = "npaa_checkbox";

	public void main(IWContext iwc) {
		control(iwc);
	}

	private void control(IWContext iwc) {
		if (iwc.isParameterSet(PARAM_FORM_SUBMIT))
			confirmApplications(iwc);
		showApplications(iwc);
	}

	private void confirmApplications(IWContext iwc) {
		String values[] = iwc.getParameterValues(PARAM_CHECKBOX);
		
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				System.out.println("values["+i+"] = " + values[i]);	
			}	
		}
	}

	private void showApplications(IWContext iwc) {
		try {
			Collection col = getApplicationBusiness(iwc).getAllApplications();
			if (col != null) {
				int size = col.size();

				Table t = new Table(5, size + 3);
				t.add("Tegund", 2, 1);
				t.add("L�sing", 3, 1);
				t.add("Ums�kn fr�", 4, 1);
				t.add("Dags. ums�knar", 5, 1);

				int i = 2;
				Iterator it = col.iterator();
				while (it.hasNext()) {
					NewProductApplication appl = (NewProductApplication) it.next();
					CheckBox check = new CheckBox(PARAM_CHECKBOX);
					check.setValue(((Integer)appl.getPrimaryKey()).intValue());
					t.add(check, 1, i);
					String type = appl.getApplicationType();
					if (type.equals("0"))
						t.add(new Link("Reynsla"), 2, i);
					else if (type.equals("1"))
						t.add(new Link("S�rlisti"), 2, i);
					else if (type.equals("2"))
						t.add(new Link("M�na�arfl."), 2, i);
					else if (type.equals("3"))
						t.add(new Link("T�bak"), 2, i);

					t.add(appl.getDescription(), 3, i);
					User supplier = appl.getSupplier();
					t.add(supplier.getName(), 4, i);
					t.add(appl.getApplicationSent().toString(), 5, i);
					i++;
				}

				SubmitButton submit = new SubmitButton(PARAM_FORM_SUBMIT, "Stadfesta");
				submit.setAsImageButton(true);
				t.setAlignment(5, size + 3, "Right");
				t.add(submit, 5, size + 3);

				add(t);
			}
			else {
				this.add("Engar n�jar ums�knir");
			}
			return;
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		add("Unable to get applications");
	}

	public String getBundleIdentifier() {
		return IW_BUNDLE_IDENTIFIER;
	}

	private NewProductApplicationBusiness getApplicationBusiness(IWContext iwc) throws Exception {
		return (NewProductApplicationBusiness) com.idega.business.IBOLookup.getServiceInstance(iwc, NewProductApplicationBusiness.class);
	}
}