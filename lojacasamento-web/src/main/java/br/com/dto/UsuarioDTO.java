/**
 * 
 */
package br.com.dto;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

/**
 * @author marcleonio.medeiros
 *
 */
@Entity
@Audited
@Table(name="usuario")
public class UsuarioDTO extends GenericDTO{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//	@Id 
//	@GeneratedValue(strategy= GenerationType.IDENTITY)
//	private Integer id;
	@Column(nullable=false)
	private String usuario;
	private String matricula;
	private String telefone;
	@NotAudited
	@Column(nullable=false)
	private String senha;
	private String nome;
	@Column(name="data_nascimento",columnDefinition="date")
	private Date dataNascimento;
	private String cpf;
	private String email;
	private String tema;
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name = "perfil_id", insertable = true, updatable = true, nullable = true)
	private PerfilDTO perfilDTO;
	
//	@ManyToMany  (cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
//	@JoinTable(name = "usuario_perfil", joinColumns = { @JoinColumn(name = "usuario_id",   
//	unique = false, nullable = false, insertable = true,updatable=true) },   
//	inverseJoinColumns = { @JoinColumn(name = "perfil_id",   
//	unique = false, nullable = false, insertable = true,updatable=true) }) 
////	@LazyCollection(value = null)@LazyCollection(LazyCollectionOption.EXTRA)
//	private List<PerfilDTO> listPerfil;
	
	/**
	 * 
	 */
	public UsuarioDTO() {
		if(tema==null){
			tema = "bootstrap";
		}
	}
	
//	@Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final UsuarioDTO other = (UsuarioDTO) obj;
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
//	public UsuarioDTO(String nome, int i) {
//		this.id = i;
//		this.nome = nome;
//	}
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}


	public PerfilDTO getPerfilDTO() {
		if(perfilDTO==null){
			perfilDTO = new PerfilDTO();
		}
		return perfilDTO;
	}


	public void setPerfilDTO(PerfilDTO perfilDTO) {
		this.perfilDTO = perfilDTO;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
