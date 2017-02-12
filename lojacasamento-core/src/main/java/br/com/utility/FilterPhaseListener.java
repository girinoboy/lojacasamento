/**
 * 
 */
package br.com.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

/**
 * @author marcleonio.medeiros
 *
 */
public class FilterPhaseListener implements PhaseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -666895419021337416L;

	private String pagina = null;
	private Properties props;
    private String nomeDoProperties = "constantes.properties";
	/**
	 * 
	 */
	public FilterPhaseListener() {
		props = new Properties();
		System.out.print("procura o resource no mesmo diretorio do .class:");
		System.out.println(getClass().getResourceAsStream(nomeDoProperties));
		System.out.print("procura no CLASSPATH:");
		System.out.println(getClass().getClassLoader().getResourceAsStream(nomeDoProperties));
		
		System.out.println(Thread.currentThread().getContextClassLoader().getResourceAsStream(nomeDoProperties));
		System.out.println(Thread.currentThread().getContextClassLoader().getSystemResourceAsStream(nomeDoProperties));
		System.out.println(ResourceBundle.getBundle("constantes").getString("USUARIO_AUTENTICADO"));
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(nomeDoProperties);
        try{
                props.load(in);
                in.close();
        }
        catch(IOException e){e.printStackTrace();}
	}
	
	 private String getValor(String chave){
	         return (String)props.getProperty(chave);
	 }

	public void afterPhase(PhaseEvent event) {/*
        FacesContext fc = event.getFacesContext();
        String viewId = fc.getViewRoot().getViewId();
        NavigationHandler nh = fc.getApplication().getNavigationHandler();        
        if (viewId != null) {
            boolean loginPage = viewId.lastIndexOf("login") > -1 ? true : false;
            boolean indexPage = viewId.lastIndexOf("index") > -1 ? true : false;
            boolean deniedPage = viewId.lastIndexOf("acessoNegado") > -1 ? true : false;
            if (!loginPage && !loggedIn()) {
                nh.handleNavigation(fc, null, "pretty:login");
            }
            if (!loginPage && loggedIn() && !deniedPage && !indexPage) {
                if (!hasAccess(viewId)) {
                    nh.handleNavigation(fc, null, "pretty:acessoNegado");
                }
            }
        }*/
		FacesContext fc = event.getFacesContext();
		String viewId = fc.getViewRoot().getViewId();
		//NavigationHandler nh = fc.getApplication().getNavigationHandler();
		try {
			if (viewId != null && !viewId.equals("/")) {
				HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
				Object usuario =  session.getAttribute(getValor("USUARIO_AUTENTICADO"));
				String pagina = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();
				
				if(usuario != null){
					if(pagina.equals("/"+getValor("PAGINA_LOGIN"))){//caso esteja autenticado redireciona para index
//						session.removeAttribute(Constantes.USUARIO_AUTENTICADO);
//						session.removeAttribute(Constantes.INDEX_CONTROLLER);
//						nh.handleNavigation(fc, null, "pretty:login");
//						FacesContext.getCurrentInstance().getExternalContext().redirect(getValor("PAGINA_INDEX"));
					}
				}else if(!pagina.equals("/"+getValor("PAGINA_LOGIN"))){
					session.removeAttribute(getValor("USUARIO_AUTENTICADO"));
					session.removeAttribute(getValor("INDEX_CONTROLLER"));
					//nh.handleNavigation(fc, null, "pretty:acessoNegado");
//					FacesContext.getCurrentInstance().getExternalContext().redirect(getValor("PAGINA_LOGIN"));
				}
			}else{
				FacesContext.getCurrentInstance().getExternalContext().redirect(getValor("PAGINA_LOGIN"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void beforePhase(PhaseEvent event) {

	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	public String getPagina() {
		return pagina;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

}
