package br.com.pagseguroexemplo;

import java.util.Date;

import org.joda.time.LocalDate;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.common.domain.DataList;
import br.com.uol.pagseguro.api.common.domain.builder.DateRangeBuilder;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.transaction.search.TransactionSummary;

public class SearchTransactions {

	  public static void main(String[] args){
	    try {
	    	String sellerEmail = "marcleonio@gmail.com";
		    String sellerToken = "D19055F3A2D54AD48B37C23BC15545D4";

	      final PagSeguro pagSeguro = PagSeguro.instance(Credential.sellerCredential(sellerEmail,
	          sellerToken), PagSeguroEnv.SANDBOX);
	      LocalDate data1 = LocalDate.now(),data2 = LocalDate.now();
	      System.out.println(data2);
	      final DataList<? extends TransactionSummary> transactions =
	          pagSeguro.transactions().search().byDateRange(new DateRangeBuilder().between(data1.parse("2017-02-01").toDate(), new Date()), 1, 1000);
	      System.out.println(transactions.size());
	      for (TransactionSummary transactionSummary : transactions) {
			System.out.println(transactionSummary.getDetail());
		}
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	  }
	}
