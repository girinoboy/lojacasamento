package br.com.pagseguroexemplo;

import java.math.BigDecimal;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.common.domain.DataList;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.installment.InstallmentDetail;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;


public class InstallmentList {

	  public static void main(String[] args){

	    String sellerEmail = "marcleonio@gmail.com";
	    String sellerToken = "D19055F3A2D54AD48B37C23BC15545D4";

	    try{

	      final PagSeguro pagSeguro = PagSeguro
	          .instance(new SimpleLoggerFactory(), new JSEHttpClient(),
	              Credential.sellerCredential(sellerEmail, sellerToken), PagSeguroEnv.SANDBOX);

	      //Lista as opções de parcelamento

	      DataList<? extends InstallmentDetail> installmentDetails =
//	          pagSeguro.installments().list(new InstallmentListingBuilder()
//	              .withCardBrand("visa")
//	              .withAmount(new BigDecimal(271.00))
//	              .withMaxInstallmentNoInterest(5)
//	          );
null;
	      System.out.println(installmentDetails.getData());

	    }catch (Exception e){
	      e.printStackTrace();
	    }
	  }
	}

