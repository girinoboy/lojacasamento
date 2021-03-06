package br.com.mb;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;

import br.com.dao.GenericDAO;
import br.com.dto.GenericDTO;
@SuppressWarnings("unchecked")
public class GenericMB<T> implements Serializable{
	
	protected static final Logger LOGGER = Logger.getLogger( GenericMB.class.getName() );
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ResourceBundle rb;
	
//	private Class<T> oclass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	//@Inject
	protected GenericDAO<T, Serializable> abstractDAO;
	
//	@EJB
//	protected GenericService<T, Serializable> abstractService;

	protected T abstractDTO;

	protected List<T> abstractList;
	
	private Date currentDate = LocalDate.now().toDate();
	
	protected Boolean visualizar;
	protected Boolean alterar;
	protected Boolean novo;
	
	
	public void inicio() throws Exception{
		abstractList =  abstractDAO.list(abstractDTO);
		if(!abstractList.isEmpty()){
			abstractDTO = abstractList.iterator().next();
		}
		
	}
	
	public GenericMB() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if(fc.getViewRoot() != null)
			rb = ResourceBundle.getBundle("messages",fc.getViewRoot().getLocale());
		
		Class<T>  oclass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		if(abstractDAO == null){
			abstractDAO = new GenericDAO<T, Serializable>(oclass);			
		}
		try {
			abstractDTO = oclass.newInstance();
			abstractList = abstractDAO.list();//
		} catch (Exception e) {
			e.printStackTrace();
			String msg = e.getMessage();
			Exception thrown = e;
			Level level = Level.SEVERE;
			LOGGER.log(level, msg, thrown);
		}
	}
	
	public void editar(){
		
	}
	
	public String redirecionar(String url) throws IOException{
		
	    FacesContext.getCurrentInstance().getExternalContext().redirect(url);
	    System.out.println(abstractDTO);
		return url;
	}
	
	public GenericDTO getUserSession(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return ((GenericDTO) session.getAttribute("usuarioAutenticado"));
	}
	
	public List<GenericDTO> getMenuSession(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return  (List<GenericDTO>) session.getAttribute("listMenuAutenticado");
	}
	public List<GenericDTO> getPerfilSession(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (List<GenericDTO>) session.getAttribute("listPerfilAutenticado");
	}
	//metodo generico que envia mesagens para a tela
	public void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);  
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public void addMessage(String summary,String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  detail);  
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public List<T> getAbstractList() {
		return abstractList;
	}

	public void setAbstractList(List<T> abstractList) {
		this.abstractList = abstractList;
	}

	public T getAbstractDTO() {
		return abstractDTO;
	}

	public void setAbstractDTO(T abstractDTO) {
		this.abstractDTO = abstractDTO;
	}

	public Boolean getVisualizar() {
		return visualizar;
	}

	public void setVisualizar(Boolean visualizar) {
		this.visualizar = visualizar;
	}

	public Boolean getAlterar() {
		return alterar;
	}

	public void setAlterar(Boolean alterar) {
		this.alterar = alterar;
	}

	public Boolean getNovo() {
		return novo;
	}

	public void setNovo(Boolean novo) {
		this.novo = novo;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}


}
