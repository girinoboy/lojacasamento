package br.com.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

/**
 *
 * @author Marcleonio girinoboy@gmail.com
 */
@Entity
@Audited
@Table(name = "perfil")
public class PerfilDTO extends GenericDTO{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//	@Id 
//	@GeneratedValue(strategy= GenerationType.IDENTITY)
//	private Integer id;
	private String nome;
	private String descricao;
	private String observacao;
//	@OneToMany(mappedBy = "perfilDTO", targetEntity = PermissaoMenuDTO.class, fetch = FetchType.LAZY, cascade= {CascadeType.PERSIST, CascadeType.MERGE})
//	//@OrderBy(value ="Perfil.descricao") 
//	private List<PermissaoMenuDTO> permissaoMenu;
	
	@ManyToMany  (cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
	@JoinTable(name = "perfil_menu", joinColumns = { @JoinColumn(name = "perfil_id",
	unique = false, nullable = false, insertable = true,updatable=true) },
	inverseJoinColumns = { @JoinColumn(name = "menu_id",
	unique = false, nullable = false, insertable = true,updatable=true) })
//	@LazyCollection(value = null)@LazyCollection(LazyCollectionOption.EXTRA)
	@OrderBy(value ="dropIndex")
	private List<MenuDTO> listMenu;
	
	/**
	 * 
	 */
	public PerfilDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public PerfilDTO(Integer id) {
		setId(id);
//		this.id=id;
		if(id.equals(1))
			this.nome="Administrador";
	}
	
//	@Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final PerfilDTO other = (PerfilDTO) obj;
//        if (this.id == null){
//        	return false;
//        }
//        if (!this.id.equals(other.id)) {
//            return false;
//        }
//        return true;
//    }
// 
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 31 * hash + this.id;
//        return hash;
//    }
//	
//	public Integer getId() {
//		return id;
//	}
//	public void setId(Integer id) {
//		this.id = id;
//	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
//	public List<PermissaoMenu> getPermissaoMenu() {
//		return permissaoMenu;
//	}
//	public void setPermissaoMenu(List<PermissaoMenu> permissaoMenu) {
//		this.permissaoMenu = permissaoMenu;
//	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<MenuDTO> getListMenu() {
		return listMenu;
	}

	public void setListMenu(List<MenuDTO> listMenu) {
		this.listMenu = listMenu;
	}


}
