package br.com.utility;

import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

@ManagedBean
public class LocaleController {
	private Locale currentLocale = new Locale("pt", "BR");
	
	public LocaleController(){
		FacesContext fc = FacesContext.getCurrentInstance();
		currentLocale = fc.getViewRoot().getLocale();//local do navegador
//		ResourceBundle rb = ResourceBundle.getBundle("br.com.messages.messages",currentLocale);
//		String valor ="themes";
//		String mensagem = rb.getString(valor);
//		System.out.println(mensagem);
	}

	public void englishLocale() {  
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();  
		currentLocale = Locale.US;  
		viewRoot.setLocale(currentLocale);  
	}  

	public void portugueseLocale() {  
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();  
		currentLocale = new Locale("pt", "BR");  
		viewRoot.setLocale(currentLocale);  
	}  

	public Locale getCurrentLocale() {  
		return currentLocale;  
	}  
}  
