/**
 * 
 */
package br.com.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name="loja")
public class LojaDTO extends GenericDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4272002021428868740L;
	
	@Column(unique = true)
	private String nome;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "lojaDTO", targetEntity = ProdutoDTO.class, fetch = FetchType.LAZY, cascade= {CascadeType.PERSIST,CascadeType.REFRESH})
	private List<ProdutoDTO> listProduto;

	public LojaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LojaDTO(String reference) {
		this.nome = reference;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<ProdutoDTO> getListProduto() {
		return listProduto;
	}

	public void setListProduto(List<ProdutoDTO> listProduto) {
		this.listProduto = listProduto;
	}

}
