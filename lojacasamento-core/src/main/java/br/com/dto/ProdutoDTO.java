/**
 * 
 */
package br.com.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.envers.Audited;

/**
 * @author mark
 *
 */
@Entity
@Audited
@Table(name="produto")
public class ProdutoDTO extends GenericDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2008902018315549495L;
	
	private String imagem;
	private String descricao;
	private BigDecimal preco;
	@ManyToOne(fetch = FetchType.EAGER, cascade ={CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "loja_id", insertable = true, updatable = false, nullable = false)
	private LojaDTO lojaDTO;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "produtoDTO", targetEntity = TransacaoDTO.class, fetch = FetchType.LAZY, cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	private List<TransacaoDTO> listTransacao;

	/**
	 * 
	 */
	public ProdutoDTO() {
		lojaDTO = new LojaDTO();
	}
	public ProdutoDTO(LojaDTO lojaDTO) {
		this.lojaDTO = lojaDTO;
	}

	public ProdutoDTO(Integer id) {
		this.setId(id);
	}
	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public LojaDTO getLojaDTO() {
		return lojaDTO;
	}

	public void setLojaDTO(LojaDTO lojaDTO) {
		this.lojaDTO = lojaDTO;
	}
	public List<TransacaoDTO> getListTransacao() {
		return listTransacao;
	}
	public void setListTransacao(List<TransacaoDTO> listTransacao) {
		this.listTransacao = listTransacao;
	}

}
