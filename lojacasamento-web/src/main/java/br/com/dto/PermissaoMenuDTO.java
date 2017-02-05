package br.com.dto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "permissao_menu")
public class PermissaoMenuDTO extends GenericDTO{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//	@Id 
//	@GeneratedValue(strategy= GenerationType.IDENTITY)
//    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="menu_id", referencedColumnName = "id", insertable=true,updatable=true,nullable=false)
    private MenuDTO menuDTO;
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="perfil_id", referencedColumnName = "id",nullable=false)
    private PerfilDTO perfilDTO;

   /* Getters e Setters */
/*
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PermissaoMenu other = (PermissaoMenu) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }*/
    /*
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.id;
        return hash;
    }*/

//    public Integer getId() {
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

	public PerfilDTO getPerfilDTO() {
		return perfilDTO;
	}

	public void setPerfilDTO(PerfilDTO perfilDTO) {
		this.perfilDTO = perfilDTO;
	}

	
}

