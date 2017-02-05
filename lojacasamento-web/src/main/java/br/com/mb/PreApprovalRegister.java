package br.com.mb;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.common.domain.ShippingType;
import br.com.uol.pagseguro.api.common.domain.builder.AddressBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.DateRangeBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PhoneBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PreApprovalBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.SenderBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.ShippingBuilder;
import br.com.uol.pagseguro.api.common.domain.enums.Currency;
import br.com.uol.pagseguro.api.common.domain.enums.State;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.preapproval.PreApprovalRegistrationBuilder;
import br.com.uol.pagseguro.api.preapproval.RegisteredPreApproval;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;


public class PreApprovalRegister {

	  public static void main(String[] args){

	    String sellerEmail = "marcleonio@gmail.com";
	    String sellerToken = "D19055F3A2D54AD48B37C23BC15545D4";

	    try{

	      final PagSeguro pagSeguro = PagSeguro
	          .instance(new SimpleLoggerFactory(), new JSEHttpClient(),
	              Credential.sellerCredential(sellerEmail, sellerToken), PagSeguroEnv.SANDBOX);


	      //Assinatura
	      RegisteredPreApproval registeredPreApproval = pagSeguro.preApprovals().register(
	          new PreApprovalRegistrationBuilder()
	              .withCurrency(Currency.BRL)
	              .withExtraAmount(BigDecimal.ONE)
	              .withReference("XXXXXX")
	              .withSender(new SenderBuilder()//
	                  .withEmail("comprador@uol.com.br")//
	                  .withName("Jose Comprador")
	                  .withCPF("99999999999")
	                  .withPhone(new PhoneBuilder()//
	                      .withAreaCode("99") //
	                      .withNumber("99999999"))) //
	              .withShipping(new ShippingBuilder()//
	                  .withType(ShippingType.Type.SEDEX) //
	                  .withCost(BigDecimal.TEN)//
	                  .withAddress(new AddressBuilder() //
	                      .withPostalCode("99999999")
	                      .withCountry("BRA")
	                      .withState(State.SP)//
	                      .withCity("Cidade Exemplo")
	                      .withComplement("99o andar")
	                      .withDistrict("Jardim Internet")
	                      .withNumber("9999")
	                      .withStreet("Av. PagSeguro")))
	              .withPreApproval(new PreApprovalBuilder()
	                  .withCharge("manual")
	                  .withName("Seguro contra roubo do Notebook Prata")
	                  .withDetails("Cada dia 28 será cobrado o valor de R$100,00 referente ao seguro " +
	                      "contra roubo do Notebook Prata")
	                  .withAmountPerPayment(BigDecimal.TEN)
	                  .withMaxTotalAmount(new BigDecimal(200))
	                  .withMaxAmountPerPeriod(BigDecimal.TEN)
	                  .withMaxPaymentsPerPeriod(2)
	                  .withPeriod("monthly")
	                  .withDateRange(new DateRangeBuilder()
	                      .between(
	                          new Date(),
	                          DatatypeConverter.parseDateTime("2017-09-27T23:59:59.000-03:00")
	                              .getTime())
	                  )
	              )
	              .withRedirectURL("http:localhost.WEB/redirect")
	              .withNotificationURL("http:localhost.WEB/notification")
	      );
	      System.out.println(registeredPreApproval.getRedirectURL());
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	  }
	}
