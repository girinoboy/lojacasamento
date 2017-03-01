package br.com.pagseguroexemplo;

import java.math.BigDecimal;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.common.domain.ShippingType;
import br.com.uol.pagseguro.api.common.domain.builder.AddressBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentItemBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PhoneBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.ReceiverBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.SenderBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.ShippingBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.SplitBuilder;
import br.com.uol.pagseguro.api.common.domain.enums.Currency;
import br.com.uol.pagseguro.api.common.domain.enums.State;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.transaction.register.SplitPaymentRegistrationBuilder;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;

public class CreateSplitPaymentWithBankSlip {

	public static void main(String[] args){
		String sellerEmail = "marcleonio@gmail.com";
		String sellerToken = "D19055F3A2D54AD48B37C23BC15545D4";
		    
	    final PagSeguro pagSeguro = PagSeguro
	        .instance(new SimpleLoggerFactory(), new JSEHttpClient(),
	            Credential.sellerCredential(sellerEmail, sellerToken), PagSeguroEnv.SANDBOX);

	    try{

	      // Checkout transparente (split) com boleto
	      TransactionDetail bankSlipSplitTransaction;
	      bankSlipSplitTransaction = pagSeguro.transactions().register(new SplitPaymentRegistrationBuilder()
	          .withPaymentMode("default")
	          .withCurrency(Currency.BRL)
	          .addItem(new PaymentItemBuilder()//
	              .withId("0001")//
	              .withDescription("Produto PagSeguroI") //
	              .withAmount(new BigDecimal(99999.99))//
	              .withQuantity(1)
	              .withWeight(1000))

	          .addItem(new PaymentItemBuilder()//
	              .withId("0002")//
	              .withDescription("Produto PagSeguroII") //
	              .withAmount(new BigDecimal(99999.98))//
	              .withQuantity(2)
	              .withWeight(750)
	          )
	          .withNotificationURL("www.sualoja.com.br/notification")
	          .withReference("LIBJAVA_DIRECT_PAYMENT")
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
	                  .withStreet("Av. PagSeguro"))
	          )
	          .withPrimaryReceiver(new ReceiverBuilder()
	              .withPublicKey("publickey1")
	              .withSplit(new SplitBuilder()
	                  .withFeePercent(new BigDecimal(50))
	              )
	          )
	          .addReceiver(new ReceiverBuilder()
	              .withPublicKey("publickey1")
	              .withSplit(new SplitBuilder()
	                  .withFeePercent(new BigDecimal(50))
	              )
	          )
	          .addReceiver(new ReceiverBuilder()
	              .withPublicKey("publickey2")
	              .withSplit(new SplitBuilder()
	                  .withFeePercent(new BigDecimal(50))
	              )
	          )
	      ).withBankSlip();
	      System.out.println(bankSlipSplitTransaction);

	    }catch (Exception e){
	      e.printStackTrace();
	    }
	  }
	}
