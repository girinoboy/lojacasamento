package br.com.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;

import br.com.dto.GenericDTO;

/**
 *
 * @author Marcleonio
 */
@Entity
@Audited
@Table(name = "menu")
@DynamicUpdate(value=true)
public class MenuDTO extends GenericDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6533458643532037859L;
	//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	@Id 
//	@GeneratedValue(strategy= GenerationType.IDENTITY)
//	private Integer id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "menu_id", insertable = true, updatable = true, nullable = true)
	private MenuDTO menuDTO;
	@Column(nullable=false,updatable = true,insertable = true)
	private String nome;
	private String comando;
	private String url;
	private String outcome;
	private String icone;
	@Column(name="ativo_inativo")
	private Boolean ativoInativo;
	@Column(name="drop_index")
	@OrderBy(value="dropIndex")
	private Integer dropIndex;
	
	@ManyToMany  (cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
	@JoinTable(name = "perfil_menu", joinColumns = { @JoinColumn(name = "menu_id",
	unique = false, nullable = false, insertable = true,updatable=true) },
	inverseJoinColumns = { @JoinColumn(name = "perfil_id",
	unique = false, nullable = false, insertable = true,updatable=true) })
	private List<PerfilDTO> listPerfil;
	
	@OneToMany(mappedBy = "menuDTO", targetEntity = PermissaoMenuDTO.class, fetch = FetchType.LAZY, cascade= {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PermissaoMenuDTO> permissaoMenu;
	

	//colocar isso em uma interface depois para diferenciar os menus de questionarios
	//private String url;
	@Column(name="icone_nativo")
	private String iconeNativo;
/*
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Menu other = (Menu) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}*/
/*
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 67 * hash + this.id;
		return hash;
	}*/
	public MenuDTO() { ativoInativo = false; setAtivoInativo(false);}

	public MenuDTO(Integer id) {
		setId(id);
//		this.id = id;
	}


//	@Override
//	public String toString() {
//		return descricao;
//	}

//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

	public MenuDTO getMenuDTO() {
		return menuDTO;
	}

	public void setMenuDTO(MenuDTO menuDTO) {
		this.menuDTO = menuDTO;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getComando() {
		return comando;
	}

	public void setComando(String comando) {
		this.comando = comando;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}

	public String getIconeNativo() {
		return iconeNativo;
	}

	public void setIconeNativo(String iconeNativo) {
		this.iconeNativo = iconeNativo;
	}
	
	public Boolean getAtivoInativo() {
		return ativoInativo;
	}

	public void setAtivoInativo(Boolean ativoInativo) {
		this.ativoInativo = ativoInativo;
	}
	
	public Integer getDropIndex() {
		return dropIndex;
	}

	public void setDropIndex(Integer dropIndex) {
		this.dropIndex = dropIndex;
	}

	/**
	 * @return the permissaoMenu
	 */
	public List<PermissaoMenuDTO> getPermissaoMenu() {
		return permissaoMenu;
	}

	/**
	 * @param permissaoMenu the permissaoMenu to set
	 */
	public void setPermissaoMenu(List<PermissaoMenuDTO> permissaoMenu) {
		this.permissaoMenu = permissaoMenu;
	}

	public List<PerfilDTO> getListPerfil() {
		return listPerfil;
	}

	public void setListPerfil(List<PerfilDTO> listPerfil) {
		this.listPerfil = listPerfil;
	}

}