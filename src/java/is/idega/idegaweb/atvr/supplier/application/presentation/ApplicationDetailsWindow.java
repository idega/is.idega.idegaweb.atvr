/*
 * Created on Mar 20, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package is.idega.idegaweb.atvr.supplier.application.presentation;

import is.idega.idegaweb.atvr.supplier.application.business.NewProductApplicationBusiness;

import com.idega.presentation.IWContext;
import com.idega.presentation.PresentationObject;
import com.idega.presentation.Table;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.TextInput;
import com.idega.presentation.ui.Window;

/**
 * @author palli
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ApplicationDetailsWindow extends Window {
	private final static int ACTION_VIEW_FORM = 0;
	private final static int ACTION_SUBMIT_FORM = 1;

	private final static int TYPE_TRIAL = 0;
	private final static int TYPE_SPECIAL = 1;
	private final static int TYPE_MONTH = 2;
	private final static int TYPE_TOBACCO = 3;

	private final static String PARAM_FORM_TYPE = "npa_type";
	private final static String PARAM_FORM_CATEGORY = "npa_cat";
	private final static String PARAM_FORM_SUBMIT = "npa_submit";
	private final static String PARAM_FORM_SUBMIT_X = "npa_submit.x";
	private final static String PARAM_FORM_SUBMIT_Y = "npa_submit.y";

	private final static String PARAM_DESC = "npa_desc";
	private final static String PARAM_DESC2 = "npa_desc2";
	private final static String PARAM_QUANTITY = "npa_qty";
	private final static String PARAM_STRENGTH = "npa_str";
	private final static String PARAM_PRODUCER = "npa_prdc";
	private final static String PARAM_COUNTRY = "npa_ctr";
	private final static String PARAM_BAR_CODE = "npa_bar";
	private final static String PARAM_SUB_CATEGORY = "npa_sub_cat";
	private final static String PARAM_AMOUNT = "npa_amount";
	private final static String PARAM_WEIGHT = "npa_weigth";
	private final static String PARAM_MONOXIDE = "npa_monoxide";
		
	private String _type = null;
	private String _id = null;
	
	private int parseType(String type) {
		if (type == null)
			return TYPE_TRIAL;

		return Integer.parseInt(type);
	}
		
	private void showForm(IWContext iwc) {
		int typeId = parseType(_type);
		
		Form form = new Form();

		if (typeId == TYPE_TRIAL || typeId == TYPE_SPECIAL || typeId == TYPE_MONTH) {
			Table t = new Table(2, 12);
			t.add("L�sing", 1, 1);
			t.add("L�sing 2", 1, 2);
			t.add("Millil�trar", 1, 3);
			t.add("V�nstyrkur", 1, 4);
			t.add("Framlei�andi", 1, 5);
			t.add("Upprunaland", 1, 6);
			t.add("Strikamerki", 1, 7);
			t.add("V�ruflokkur", 1, 8);
			t.add("Flokksdeild", 1, 9);
			t.add("Fl�skur pr. ks.", 1, 10);

			TextInput desc = new TextInput(PARAM_DESC);
			TextInput desc2 = new TextInput(PARAM_DESC2);
			TextInput qty = new TextInput(PARAM_QUANTITY);
			TextInput str = new TextInput(PARAM_STRENGTH);
			TextInput prod = new TextInput(PARAM_PRODUCER);
			TextInput ctry = new TextInput(PARAM_COUNTRY);
			TextInput bar = new TextInput(PARAM_BAR_CODE);
			TextInput bottles = new TextInput(PARAM_AMOUNT);

			t.add(desc, 2, 1);
			t.add(desc2, 2, 2);
			t.add(qty, 2, 3);
			t.add(str, 2, 4);
			t.add(prod, 2, 5);
			t.add(ctry, 2, 6);
			t.add(bar, 2, 7);
			t.add(bottles, 2, 10);

			String selected = iwc.getParameter(PARAM_FORM_CATEGORY);

			DropdownMenu category = (DropdownMenu) getCategoryDropdown(iwc, selected);
			t.add(category, 2, 8);
			t.add(getSubCategoryDropdown(iwc, selected), 2, 9);

			SubmitButton submit = new SubmitButton(PARAM_FORM_SUBMIT, "Geyma");
			submit.setAsImageButton(true);
			t.setAlignment(2, 12, "Right");
			t.add(submit, 2, 12);

			form.add(t);
		}
		else if (typeId == TYPE_TOBACCO) {
			Table t = new Table(2, 10);
			t.add("L�sing", 1, 1);
			t.add("V�ruflokkur", 1, 2);
			t.add("Flokksdeild", 1, 3);
			t.add("Framlei�andi", 1, 4);
			t.add("Upprunaland", 1, 5);
			t.add("Tj�rumagn", 1, 6);
			t.add("�yngd t�baks", 1, 7);
			t.add("Magn kolm�nox��s",1,8);

			TextInput desc = new TextInput(PARAM_DESC);
			TextInput prod = new TextInput(PARAM_PRODUCER);
			TextInput ctry = new TextInput(PARAM_COUNTRY);
			TextInput amount = new TextInput(PARAM_AMOUNT);
			TextInput weight = new TextInput(PARAM_WEIGHT);
			TextInput monoxide = new TextInput(PARAM_MONOXIDE);
			monoxide.setAsFloat();

			t.add(desc, 2, 1);
			t.add(prod, 2, 4);
			t.add(ctry, 2, 5);
			t.add(amount, 2, 6);
			t.add(weight, 2, 7);
			t.add(monoxide,2,8);

			String selected = iwc.getParameter(PARAM_FORM_CATEGORY);

			DropdownMenu category = (DropdownMenu) getCategoryDropdown(iwc, selected);
			t.add(category, 2, 2);
			t.add(getSubCategoryDropdown(iwc, selected), 2, 3);

			SubmitButton submit = new SubmitButton(PARAM_FORM_SUBMIT, "Geyma");
			submit.setAsImageButton(true);
			t.setAlignment(2, 10, "Right");
			t.add(submit, 2, 10);

			form.add(t);
		}

		add(form);
	}
	
	private PresentationObject getCategoryDropdown(IWContext iwc, String selected) {
		DropdownMenu menu = new DropdownMenu(PARAM_FORM_CATEGORY);
		menu.addMenuElement("01.","Rau�v�n");
		menu.addMenuElement("02.","Hv�tv�n");
		menu.addMenuElement("03.","R�sav�n");
		menu.addMenuElement("04.","Frey�iv�n");
		menu.addMenuElement("05.","Styrkt v�n");
		menu.addMenuElement("06.","�vaxtav�n");
		menu.addMenuElement("10.","Brand�");
		menu.addMenuElement("11.","�vaxtabrand�");
		menu.addMenuElement("13.","Visk�");
		menu.addMenuElement("14.","Romm");
		menu.addMenuElement("15.","Tequila o.fl.");
		menu.addMenuElement("16.","�krydda� brenniv�n og vodka");
		menu.addMenuElement("17.","Gin & s�never");
		menu.addMenuElement("18.","Snafs");
		menu.addMenuElement("20.","L�kj�r");
		menu.addMenuElement("21.","Bitterar, kryddv�n, aperit�far");
		menu.addMenuElement("23.","Blanda�ir drykkir");
		menu.addMenuElement("36.","Umb��ir");
		menu.addMenuElement("60.","Lagerbj�r");
		menu.addMenuElement("61.","�l");
		menu.addMenuElement("62.","A�rar bj�rtegundir");
		menu.addMenuElement("89.","Ni�urlag�ir �vextir");
		menu.addMenuElement("90.","Neft�bak");
		menu.addMenuElement("91.","Reykt�bak");
		menu.addMenuElement("92.","Vindlingar");
		menu.addMenuElement("93.","Vindlar");
		menu.addMenuElement("94.","Munnt�bak");
		

		menu.setSelectedElement(selected);

		return menu;
	}

	private PresentationObject getSubCategoryDropdown(IWContext iwc, String category) {
		DropdownMenu menu = new DropdownMenu(PARAM_SUB_CATEGORY);

		menu.addMenuElement("01.1","Rau�v�n - st�rri en 750 ml");
		menu.addMenuElement("01.10","Rau�v�n Argent�na");
		menu.addMenuElement("01.11","Rau�v�n Chile");
		menu.addMenuElement("01.12","Rau�v�n Su�ur-Afr�ka");
		menu.addMenuElement("01.13","Rau�v�n �stral�a, N�ja Sj�land");
		menu.addMenuElement("01.13.1","Rau�v�n Su�ur-�stral�a");
		menu.addMenuElement("01.2","Rau�v�n - minni en 500 ml");
		menu.addMenuElement("01.3","Rau�v�n Frakkland");
		menu.addMenuElement("01.3.1","Rau�v�n Bordeaux/Bergerac");
		menu.addMenuElement("01.3.1.1","Rau�v. Medoc,Graves,Libournais");
		menu.addMenuElement("01.3.2","Rau�v�n B�rgund");
		menu.addMenuElement("01.3.2.1","Rau�v�n Beaujolais");
		menu.addMenuElement("01.3.2.2","Cote de Nuits, Cote de Beaune");
		menu.addMenuElement("01.3.3","Rau�v�n R�n og Pr�vens");
		menu.addMenuElement("01.4","Rau�v�n �tal�a");
		menu.addMenuElement("01.4.1","Rau�v�n Nor�ur-�tal�a");
		menu.addMenuElement("01.4.1.1","Rau�v�n Toskana");
		menu.addMenuElement("01.4.1.2","Rau�v�n Piemonte");
		menu.addMenuElement("01.4.1.3","Rau�v�n Veneto");
		menu.addMenuElement("01.4.2","Rau�v�n Su�ur-�tal�a");
		menu.addMenuElement("01.5","Rau�v�n Sp�nn");
		menu.addMenuElement("01.5.1","Rau�v�n Rioja");
		menu.addMenuElement("01.5.2","Rau�v�n Katal�n�a");
		menu.addMenuElement("01.6","Rau�v�n Port�gal");
		menu.addMenuElement("01.7","Rau�v�n Evr�pa anna�");
		menu.addMenuElement("01.8","Rau�v�n Washington, Oregon");
		menu.addMenuElement("01.9","Rau�v�n Kaliforn�a");
		menu.addMenuElement("01.9.1","Rau�v�n Napa og Sonoma");
		menu.addMenuElement("01.99","Rau�v�n - �nnur");
		menu.addMenuElement("02.1","Hv�tv�n - st�rri en 750 ml");
		menu.addMenuElement("02.10","Hv�tv�n Kaliforn�a");
		menu.addMenuElement("02.10.1","Hv�tv�n Napa og Sonoma");
		menu.addMenuElement("02.11","Hv�tv�n Chile");
		menu.addMenuElement("02.12","Hv�tv�n Su�ur-Afr�ka");
		menu.addMenuElement("02.13","Hv�tv�n �stral�a");
		menu.addMenuElement("02.13.1","Hv�tv�n N�ja Sj�land");
		menu.addMenuElement("02.2","Hv�tv�n - minni en 500 ml");
		menu.addMenuElement("02.3","Hv�tv�n Frakkland");
		menu.addMenuElement("02.3.1","Hv�tv�n Bordeaux");
		menu.addMenuElement("02.3.2","Hv�tv�n B�rgund");
		menu.addMenuElement("02.3.3","Hv�tv�n Alsace");
		menu.addMenuElement("02.3.4","Hv�tv�n Loire");
		menu.addMenuElement("02.4","Hv�tv�n �tal�a");
		menu.addMenuElement("02.5","Hv�tv�n Sp�nn");
		menu.addMenuElement("02.6","Hv�tv�n Port�gal");
		menu.addMenuElement("02.7","Hv�tv�n ��skaland");
		menu.addMenuElement("02.7.1","Hv�tv�n Riesling - Qmp");
		menu.addMenuElement("02.8","Hv�tv�n Evr�pa anna�");
		menu.addMenuElement("02.9","Hv�tv�n Washington og Oregon");
		menu.addMenuElement("02.90","Hv�tv�n - s�t");
		menu.addMenuElement("02.99","Hv�tv�n - �nnur");
		menu.addMenuElement("03.1","R�sav�n st�rri en 750 ml");
		menu.addMenuElement("03.2","R�sav�n Blush - Ro�av�n");
		menu.addMenuElement("03.9","R�sav�n - �nnur");
		menu.addMenuElement("04.1","Champagne");
		menu.addMenuElement("04.2","Frey�v�n Asti");
		menu.addMenuElement("04.9","Frey�iv�n - �nnur");
		menu.addMenuElement("05.1.1","S�rr� - Fino og skyldar tegund");
		menu.addMenuElement("05.1.2","S�rr� - Amontillado og skyldar");
		menu.addMenuElement("05.1.3","S�rr� - Olroso og skyldar teg");
		menu.addMenuElement("05.2.1","Portv�n - hv�t");
		menu.addMenuElement("05.2.2","Portv�n - tunnu�rosku� (Tawny)");
		menu.addMenuElement("05.2.3","Portv�n - rau� (Ruby)");
		menu.addMenuElement("05.2.3.1","Portv�n - �rgangsv�n, rau�");
		menu.addMenuElement("05.9","Styrkt v�n - �nnur");
		menu.addMenuElement("06.1","S�der");
		menu.addMenuElement("06.2","�vaxtav�n");
		menu.addMenuElement("06.3","Hr�sgrj�nav�n");
		menu.addMenuElement("06.9","�vaxtav�n - bl�ndur");
		menu.addMenuElement("10.1","Cognac VS og VSOP");
		menu.addMenuElement("10.1.1","Cognac - �nnur");
		menu.addMenuElement("10.2","Armagnac");
		menu.addMenuElement("10.9","Brand� - �nnur");
		menu.addMenuElement("11.1","Calvados");
		menu.addMenuElement("11.2","Anna� �vaxtabrand�");
		menu.addMenuElement("11.3","Hratbrand� / Grappa");
		menu.addMenuElement("13.1","Visk� - Skoskt");
		menu.addMenuElement("13.1.1","Visk� - Skoskt malt");
		menu.addMenuElement("13.2","Visk� - �rskt");
		menu.addMenuElement("13.9","Visk� - �nnur");
		menu.addMenuElement("14.1","Hv�tt Romm fr� Vestur-Ind�um");
		menu.addMenuElement("14.2","Lj�st Romm fr� Vestur-Ind�um");
		menu.addMenuElement("14.3","D�kkt Romm fr� Vestur-Ind�um");
		menu.addMenuElement("14.9","Romm - �nnur, �.m.t. Kryddromm");
		menu.addMenuElement("15.1","Tequila");
		menu.addMenuElement("16.1","Vodka");
		menu.addMenuElement("16.2","Anna� �krydda� brenniv�n");
		menu.addMenuElement("17.1","Gin");
		menu.addMenuElement("17.2","S�never");
		menu.addMenuElement("18.1","Akvav�t");
		menu.addMenuElement("18.2","An�s");
		menu.addMenuElement("18.9","A�rir Snafsar");
		menu.addMenuElement("20.1","Rj�mal�kj�r");
		menu.addMenuElement("20.2","Hnetu og baunal�kj�r");
		menu.addMenuElement("20.2.1","Kaffi/Kak�l�kj�r");
		menu.addMenuElement("20.2.2","K�kosl�kj�r");
		menu.addMenuElement("20.3","Grasa og Kryddl�kj�r");
		menu.addMenuElement("20.3.1","Mintul�kj�r");
		menu.addMenuElement("20.3.2","L�kj�r me� an�sbrag�i");
		menu.addMenuElement("20.4","�vaxtal�kj�r");
		menu.addMenuElement("20.4.1","Epla/Perul�kj�r");
		menu.addMenuElement("20.4.2","Ferskju/Apr�k�skul�kj�r");
		menu.addMenuElement("20.4.3","S�trusl�kj�r");
		menu.addMenuElement("20.4.4","Berjal�kj�r");
		menu.addMenuElement("20.9","A�rir l�kj�rar");
		menu.addMenuElement("21.1","Bitter");
		menu.addMenuElement("21.2","Kryddv�n");
		menu.addMenuElement("21.2.1","Kryddv�n - Verm�t");
		menu.addMenuElement("21.3","Apert�far");
		menu.addMenuElement("23.1","Blanda�ir drykkir - undir 6,5%");
		menu.addMenuElement("23.9","Blanda�ir drykkir - a�rir");
		menu.addMenuElement("60.1","Lager - lj�s � fl�skum");
		menu.addMenuElement("60.1.1","Lager - lj�s � fl. �slenskur");
		menu.addMenuElement("60.2","Lager - lj�s � d�sum");
		menu.addMenuElement("60.2.1","Lager - lj�s � d�sum �slenskur");
		menu.addMenuElement("60.3","Lager - millid�kkur/d�kkur");
		menu.addMenuElement("60.4","Lager - sterkur a.m.k. 6,2%");
		menu.addMenuElement("60.9","Lager - annar");
		menu.addMenuElement("61.1","�l - Belg�a");
		menu.addMenuElement("61.2","�l - Bretland og �rland");
		menu.addMenuElement("61.3","�l - ��skaland");
		menu.addMenuElement("61.4","�l - St�t og portari");
		menu.addMenuElement("61.9","�l - anna�");
		menu.addMenuElement("62.1","Hveitibj�r");
		menu.addMenuElement("62.2","Lambik");
		menu.addMenuElement("62.9","Annar bj�r");
		menu.addMenuElement("92.1","Vindlingar - tjara 0 til 4 mg");
		menu.addMenuElement("92.2","Vindlingar - tjara 5 til 7 mg");
		menu.addMenuElement("92.3","Vindlingar - tjara yfir 7 mg");
		menu.addMenuElement("93.1","Vindlar t�bak < 3,15");
		menu.addMenuElement("93.2","Vindlar t�bak 3,15 - 4,25");
		menu.addMenuElement("93.3","Vindlar t�bak > 4,25");

		return menu;
	}
	
	private NewProductApplicationBusiness getApplicationBusiness(IWContext iwc) throws Exception {
		return (NewProductApplicationBusiness) com.idega.business.IBOLookup.getServiceInstance(iwc, NewProductApplicationBusiness.class);
	}	
	
	public void main(IWContext iwc) {
		_type = iwc.getParameter("app_type");
		_id = iwc.getParameter("app_id");
				
		add("Details gluggi");
		showForm(iwc);
	}
}
