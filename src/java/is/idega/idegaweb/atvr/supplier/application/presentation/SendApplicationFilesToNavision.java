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
import com.idega.idegaweb.IWBundle;
import com.idega.presentation.Block;
import com.idega.presentation.IWContext;
import com.idega.presentation.Table;
import com.idega.presentation.ui.SubmitButton;

import is.idega.idegaweb.atvr.supplier.application.business.NewProductApplicationBusiness;
import is.idega.idegaweb.atvr.supplier.application.data.NewProductApplication;

import java.io.FileWriter;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class does something very clever.....
 * 
 * @author <a href="palli@idega.is">Pall Helgason</a>
 * @version 1.0
 */
public class SendApplicationFilesToNavision extends Block {
	private final static String IW_BUNDLE_IDENTIFIER = "is.idega.idegaweb.atvr";

	protected final static String PARAM_FORM_SUBMIT = "npaa_submit";

	protected final static String FILE_LOCATION_PARAMETER = "npaa_file_location";

	public void main(IWContext iwc) {
		control(iwc);
	}

	private void control(IWContext iwc) {
		if (iwc.isParameterSet(PARAM_FORM_SUBMIT))
			sendApplications(iwc);
		showApplications(iwc);
	}
	
	private void sendApplications(IWContext iwc) {
		try {
			Collection col = getApplicationBusiness(iwc).getAllApplications();
			
			if (col != null) {
				IWBundle bundle = getBundle(iwc);
				String fileLocation = bundle.getProperty(FILE_LOCATION_PARAMETER);
				if (fileLocation == null || fileLocation.equals("")) {
					add("Engin skr�arsta�setning skilgreind � bundle");
					return;	
				}
				
				FileWriter writer = new FileWriter(fileLocation);
				Iterator it = col.iterator();
				while (it.hasNext()) {
					NewProductApplication appl = (NewProductApplication) it.next();
					StringBuffer line = new StringBuffer();
					line.append(appl.getApplicationType());   //Tegund ums�knar
					line.append(";");
					line.append("1111111119");                //kennitala birgja
					line.append(";");
					line.append("9999");                      //v�run�mer
					line.append(";");
					line.append("99-9999");                   //v�run�mer gamla
					line.append(";");
					line.append(appl.getDescription());       //L�sing
					line.append(";");
					line.append(appl.getDescription2());      //L�sing 2
					line.append(";");
					line.append(appl.getQuantity());          //Magn (ml)
					line.append(";");
					line.append(appl.getStrength());          //Styrkur(%)
					line.append(";");
					line.append(appl.getProducer());          //Framlei�andi
					line.append(";");
					line.append(appl.getCountryOfOrigin());   //Framlei�sluland
					line.append(";");
					line.append(appl.getBarCode());           //Strikamerki
					line.append(";");
					line.append("01.1");                      //Flokksdeild
					line.append(";");
					line.append(appl.getAmount());            //Fj�ldi � kassa
					line.append(";");
					line.append(appl.getApplicationSent());   //Dags. ums�knar
					line.append(";");
					line.append(appl.getAmount());            //Magn tj�ru
					line.append(";");
					line.append(appl.getWeigth());            //�yngd t�baks
					line.append(";");
					line.append(appl.getPrice());             //Ver�
					line.append(";");
					line.append("");                          //V�run�mer birgja
					line.append(";");
					line.append(appl.getCarbonMonoxide());    //Koltv�s�ringur
					line.append("\n");
					
					writer.write(line.toString());
				}
				
				writer.close();
			}
			
			add("Skr� send");
			
			return;
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private void showApplications(IWContext iwc) {
		try {
			Collection col = getApplicationBusiness(iwc).getAllApplications();
			if (col != null) {
				int size = col.size();

				Table t = new Table(4, size + 3);
				t.add("Tegund", 1, 1);
				t.add("L�sing", 2, 1);
				t.add("Ums�kn fr�", 3, 1);
				t.add("Dags. ums�knar", 4, 1);

				int i = 2;
				Iterator it = col.iterator();
				while (it.hasNext()) {
					NewProductApplication appl = (NewProductApplication) it.next();
//					CheckBox check = new CheckBox();
//					t.add(check, 1, i);
					String type = appl.getApplicationType();
					if (type.equals("0"))
						t.add("Reynsla", 1, i);
					else if (type.equals("1"))
						t.add("S�rlisti", 1, i);
					else if (type.equals("2"))
						t.add("M�na�arfl.", 1, i);
					else if (type.equals("3"))
						t.add("T�bak", 1, i);

					t.add(appl.getDescription(), 2, i);
					User supplier = appl.getSupplier();
					t.add(supplier.getName(), 3, i);
					t.add(appl.getApplicationSent().toString(), 4, i);
					i++;
				}

				SubmitButton submit = new SubmitButton(PARAM_FORM_SUBMIT, "Senda � skr�");
				submit.setAsImageButton(true);
				t.setAlignment(4, size + 3, "Right");
				t.add(submit, 4, size + 3);

				add(t);
			}
			else {
				this.add("Engar �sendar ums�knir");
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