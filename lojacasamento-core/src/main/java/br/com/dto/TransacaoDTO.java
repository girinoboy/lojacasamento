package br.com.dto;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name="transacao")
public class TransacaoDTO extends GenericDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2888043118596027975L;
	@Column(unique = true)
	private String code;
	private BigDecimal extraAmount;
	private BigDecimal grossAmount;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade ={CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "produto_id", insertable = true, updatable = false, nullable = false)
	private ProdutoDTO produtoDTO;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public BigDecimal getExtraAmount() {
		return extraAmount;
	}
	public void setExtraAmount(BigDecimal extraAmount) {
		this.extraAmount = extraAmount;
	}
	public BigDecimal getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(BigDecimal grossAmount) {
		this.grossAmount = grossAmount;
	}
	public ProdutoDTO getProdutoDTO() {
		return produtoDTO;
	}
	public void setProdutoDTO(ProdutoDTO produtoDTO) {
		this.produtoDTO = produtoDTO;
	}

}
