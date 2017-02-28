package br.com.utility;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.LocalDate;

import br.com.dao.GenericDAO;
import br.com.dto.LojaDTO;
import br.com.dto.ProdutoDTO;
import br.com.dto.TransacaoDTO;
import br.com.service.GenericService;
import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.common.domain.DataList;
import br.com.uol.pagseguro.api.common.domain.PaymentItem;
import br.com.uol.pagseguro.api.common.domain.builder.DateRangeBuilder;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.transaction.search.TransactionSummary;

@Singleton
@Startup
//@Stateless
public class Atualiza {

	private String sellerEmail = "marcleonio@gmail.com";
	private String sellerToken = "D19055F3A2D54AD48B37C23BC15545D4";
	final PagSeguro pagSeguro;

	private GenericService<String, Serializable> favouriteWord;
	@Inject
	private GenericDAO<LojaDTO, Serializable> lojaDAO;
	@Inject
	protected GenericDAO<ProdutoDTO, Serializable> produtoDAO;
	@Inject
	protected GenericDAO<TransacaoDTO, Serializable> transacaoDAO;

	public Atualiza() {
		super();
		pagSeguro = PagSeguro.instance(Credential.sellerCredential(sellerEmail, sellerToken), PagSeguroEnv.SANDBOX);
	}
	
	@PostConstruct
	void atStartup() { 
		atualizaLoja();
	}
	
	@Schedule(second="*", minute="*", hour="*/60")
	public void executaTarefa() {
		atualizaLoja();
		System.out.println("Tarefa executada com sucesso!");
	}


	private void atualizaLoja() {
		DataList<? extends TransactionSummary> transactions;
		LojaDTO lojaDTO;
		ProdutoDTO produtoDTO;
		PaymentItem produto;
		TransacaoDTO t;
		Integer idProduto;
		try {
			transactions = pagSeguro.transactions().search().byDateRange(new DateRangeBuilder().between(LocalDate.now().minusMonths(1).toDate(), new Date()), 1, 5000);
			for (TransactionSummary transactionSummary : transactions) {
				t = new TransacaoDTO();
				produto = transactionSummary.getDetail().getItems().get(0);
				idProduto = Integer.valueOf(produto.getId());
				
				produtoDTO = produtoDAO.getById(idProduto);
				if(produtoDTO==null){
					produtoDTO = new ProdutoDTO(idProduto);
					lojaDTO = lojaDAO.getById(new LojaDTO(transactionSummary.getReference()));
					produtoDTO.setLojaDTO(lojaDTO==null?new LojaDTO(transactionSummary.getReference()):lojaDTO);
					produtoDTO.setDescricao(produto.getDescription());
					produtoDTO.setPreco(new BigDecimal(produto.getWeight()==null ? 0:produto.getWeight()));
					produtoDTO.setImagem("produto-sem-imagem.gif");
				}
				t.setProdutoDTO(produtoDTO);
				t.setCode(transactionSummary.getCode());
				t.setGrossAmount(transactionSummary.getGrossAmount());
				t.setExtraAmount(transactionSummary.getExtraAmount());

				TransacaoDTO byId = transacaoDAO.getById(t);
				transacaoDAO.save(byId == null ? t:byId);
			}

		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Timeout
    public void timeout(Timer timer) {
        System.out.println("Current Time : " + timer.toString());
    }
//	
//	@Timeout
//    public void execute1(java.util.Timer timer) {
//        System.out.println("Current Time : " + new Date());
//    }

	public GenericService<String, Serializable> getFavouriteWord() {
		return favouriteWord;
	}

	public void setFavouriteWord(GenericService<String, Serializable> favouriteWord) {
		this.favouriteWord = favouriteWord;
	}
}
