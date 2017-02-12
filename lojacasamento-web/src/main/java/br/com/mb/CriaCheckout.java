package br.com.mb;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.dto.LojaDTO;
import br.com.dto.ProdutoDTO;
import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.checkout.CheckoutRegistrationBuilder;
import br.com.uol.pagseguro.api.checkout.RegisteredCheckout;
import br.com.uol.pagseguro.api.common.domain.ShippingType;
import br.com.uol.pagseguro.api.common.domain.builder.AcceptedPaymentMethodsBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.AddressBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.ConfigBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentItemBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentMethodBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentMethodConfigBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PhoneBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.SenderBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.ShippingBuilder;
import br.com.uol.pagseguro.api.common.domain.enums.ConfigKey;
import br.com.uol.pagseguro.api.common.domain.enums.Currency;
import br.com.uol.pagseguro.api.common.domain.enums.PaymentMethodGroup;
import br.com.uol.pagseguro.api.common.domain.enums.State;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;

@ManagedBean
@ViewScoped
public class CriaCheckout extends GenericMB<LojaDTO>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2692057285671231734L;
	//sandbox
	String sellerEmail = "marcleonio@gmail.com";
    String sellerToken = "D19055F3A2D54AD48B37C23BC15545D4";
	//sandbox
	private BigDecimal valorDoacao,valorUsual;
	private String checkoutCode;
	private String url;
	final PagSeguro pagSeguro;
	private ProdutoDTO produtoDTO;
	
	
	
	public CriaCheckout() {
		if(abstractDTO != null)
		System.out.println(abstractDTO.getNome());
		valorDoacao = new BigDecimal(0);
		 pagSeguro = PagSeguro
		          .instance(new SimpleLoggerFactory(), new JSEHttpClient(),
		              Credential.sellerCredential(sellerEmail, sellerToken), PagSeguroEnv.SANDBOX);
	}	
	
//	@PostConstruct
//	public void inicio(){
//		System.out.println(abstractDTO);
//	}
	
	public void test(){
		System.out.println(abstractDTO);
	}
	
	public void buscar() {
		try {
			abstractList =  abstractDAO.list(abstractDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void geraCheckoutCode(){
		try {

//		      final PagSeguro pagSeguro = PagSeguro
//		          .instance(new SimpleLoggerFactory(), new JSEHttpClient(),
//		              Credential.sellerCredential(sellerEmail, sellerToken), PagSeguroEnv.SANDBOX);

		      //Criando um checkout
//		      RegisteredCheckout registeredCheckout = pagSeguro.checkouts().register(//
//		          new CheckoutRegistrationBuilder() //
//		              .withCurrency(Currency.BRL) //
//		              .withExtraAmount(BigDecimal.ONE) //
//		              .withReference("XXXXXX")
//		              .withSender(new SenderBuilder()//
//		                  .withEmail("comprador@uol.com.br")//
//		                  .withName("Jose Comprador")
//		                  .withCPF("99999999999")
//		                  .withPhone(new PhoneBuilder()//
//		                      .withAreaCode("61") //
//		                      .withNumber("99999999"))) //
//		              .withShipping(new ShippingBuilder()//
//		                  .withType(ShippingType.Type.SEDEX) //
//		                  .withCost(BigDecimal.ZERO)//
//		                  .withAddress(new AddressBuilder() //
//		                      .withPostalCode("73340512")
//		                      .withCountry("BRA")
//		                      .withState(State.DF)//
//		                      .withCity("Planaltina")
//		                      .withComplement("")
//		                      .withDistrict("Jardim Roriz")
//		                      .withNumber("35")
//		                      .withStreet("Av. Independência")))
//
//		              .addItem(new PaymentItemBuilder()//
//		                  .withId("0001")//
//		                  .withDescription("Produto Escolhido 1") //
//		                  .withAmount(valorDoacao)//
//		                  .withQuantity(1)
//		                  .withWeight(1000))
//
//		              
//
//		              //Para definir o a inclusão ou exclusão de um meio você deverá utilizar três parâmetros: o parâmetro que define a configuração do grupo,
//		              // o grupo de meios de pagamento e o nome do meio de pagamento.
//		              // No parâmetro que define a configuração do grupo você informará se o grupo ou o meio de pagamento será incluído ou excluído.
//		              // Já no grupo você informará qual o grupo de meio de pagamento que receberá a configuração definida anteriormente.
//		              // Você poderá incluir e excluir os grupos de meios de pagamento Boleto, Débito, Cartão de Crédito, Depósito Bancário e Saldo PagSeguro.
//		              // Já no parâmetro nome você informará qual o meio de pagamento que receberá a configuração definida anteriormente. Os meios são as bandeiras e bancos disponíveis.
//		              //Atenção:  - Não é possível incluir e excluir o mesmo grupo de meio de pagamento (Ex.: incluir e excluir o grupo CREDIT_CARD).
//		              // - Não é possível incluir um grupo e um meio do mesmo grupo (Ex.: incluir grupo cartão e bandeira visa na mesma chamada);
//		              // - Não é possível excluir um grupo e um meio do mesmo grupo (Ex.: excluir grupo cartão e bandeira visa na mesma chamada);
//		              // - Não é possível incluir e excluir o mesmo meio de pagamento (Ex.: incluir e excluir a bandeira VISA).
//
//		              .withAcceptedPaymentMethods(new AcceptedPaymentMethodsBuilder()
//		                 
//		            		  .addInclude(new PaymentMethodBuilder()
//		            				  .withGroup(PaymentMethodGroup.BANK_SLIP)
//		            				  )
//		            		  .addInclude(new PaymentMethodBuilder()
//		            				  .withGroup(PaymentMethodGroup.CREDIT_CARD)
//		            				  )
//		                  
//		              )
//
//		              //Para definir o percentual de desconto para um meio de pagamento você deverá utilizar três parâmetros: grupo de meios de pagamento, chave configuração de desconto e o percentual de desconto. No parâmetro de grupo você deve informar um dos meios de pagamento disponibilizados pelo PagSeguro. Após definir o grupo é necessário definir os a configuração dos campos chave e valor.
////		              .addPaymentMethodConfig(new PaymentMethodConfigBuilder()
////		                  .withPaymentMethod(new PaymentMethodBuilder()
////		                      .withGroup(PaymentMethodGroup.CREDIT_CARD)
////		                  )
////		                  .withConfig(new ConfigBuilder()
////		                      .withKey(ConfigKey.DISCOUNT_PERCENT)
////		                      .withValue(new BigDecimal(00.00))
////		                  )
////		              )
////		              .addPaymentMethodConfig(new PaymentMethodConfigBuilder()
////		                  .withPaymentMethod(new PaymentMethodBuilder()
////		                      .withGroup(PaymentMethodGroup.BANK_SLIP)
////		                  )
////		                  .withConfig(new ConfigBuilder()
////		                      .withKey(ConfigKey.DISCOUNT_PERCENT)
////		                      .withValue(new BigDecimal(00.00))
////		                  )
////		              )
//
//		              //Para definir o parcelamento você deverá utilizar três parâmetros: grupo, chave e valor.
//		              // No parâmetro grupo você informará qual o meio pagamento que receberá as configurações.
//		              // Para limitar o parcelamento você deve informar o meio de pagamento Cartão de crédito (CREDIT_CARD).
//		              //Após definir o grupo você deverá definir as configurações nos campos chave e valor.
//		              // Estes devem ser definidos com a chave MAX_INSTALLMENTS_LIMIT que define a configuração de limite de parcelamento e como valor o número de parcelas que você deseja apresentar ao cliente (de 2 a 18 parcelas).
//
//		              .addPaymentMethodConfig(new PaymentMethodConfigBuilder()
//		                  .withPaymentMethod(new PaymentMethodBuilder()
//		                      .withGroup(PaymentMethodGroup.CREDIT_CARD)
//		                  )
//		                  .withConfig(new ConfigBuilder()
//		                      .withKey(ConfigKey.MAX_INSTALLMENTS_LIMIT)
//		                      .withValue(new BigDecimal(10))
//		                  )
//		              )
//		              .addPaymentMethodConfig(new PaymentMethodConfigBuilder()
//		                  .withPaymentMethod(new PaymentMethodBuilder()
//		                      .withGroup(PaymentMethodGroup.CREDIT_CARD)
//		                  )
//		                  .withConfig(new ConfigBuilder()
//		                      .withKey(ConfigKey.MAX_INSTALLMENTS_NO_INTEREST)
//		                      .withValue(new BigDecimal(5))
//		                  )
//		              )
//		      );
//		      System.out.println(registeredCheckout.getRedirectURL());
//		      url = registeredCheckout.getRedirectURL();
//		      checkoutCode = registeredCheckout.getCheckoutCode();
		      valorUsual = valorDoacao;
		      System.out.println(abstractDTO);
		    }catch (Exception e){
		      e.printStackTrace();
		    }
		
		
	}
	
	public void redirecionar(){
		System.out.println(this.valorDoacao);
		System.out.println(valorDoacao);
		System.out.println(checkoutCode);
		System.out.println(url);
		System.out.println(valorUsual);
		try {

//		      final PagSeguro pagSeguro = PagSeguro
//		          .instance(new SimpleLoggerFactory(), new JSEHttpClient(),
//		              Credential.sellerCredential(sellerEmail, sellerToken), PagSeguroEnv.SANDBOX);

		      //Criando um checkout
		      RegisteredCheckout registeredCheckout = pagSeguro.checkouts().register(//
		          new CheckoutRegistrationBuilder() //
		              .withCurrency(Currency.BRL) //
		              .withExtraAmount(BigDecimal.ONE) //
		              .withReference(abstractDTO.getNome())//gerar um numero aleatorio
		              .withSender(new SenderBuilder()//
		                  .withEmail("comprador@uol.com.br")//
		                  .withName("Jose Comprador")
		                  .withCPF("99999999999")
		                  .withPhone(new PhoneBuilder()//
		                      .withAreaCode("61") //
		                      .withNumber("99999999"))) //
		              .withShipping(new ShippingBuilder()//
		                  .withType(ShippingType.Type.SEDEX) //
		                  .withCost(BigDecimal.ZERO)//
		                  .withAddress(new AddressBuilder() //
		                      .withPostalCode("73340512")
		                      .withCountry("BRA")
		                      .withState(State.DF)//
		                      .withCity("Planaltina")
		                      .withComplement("")
		                      .withDistrict("Jardim Roriz")
		                      .withNumber("35")
		                      .withStreet("Av. Independência")))

		              .addItem(new PaymentItemBuilder()//
		                  .withId(produtoDTO.getId().toString())//
		                  .withDescription(produtoDTO.getDescricao()) //
		                  .withAmount(valorUsual)//
		                  .withQuantity(1)
		                  .withWeight(1000))

		              

		              //Para definir o a inclusão ou exclusão de um meio você deverá utilizar três parâmetros: o parâmetro que define a configuração do grupo,
		              // o grupo de meios de pagamento e o nome do meio de pagamento.
		              // No parâmetro que define a configuração do grupo você informará se o grupo ou o meio de pagamento será incluído ou excluído.
		              // Já no grupo você informará qual o grupo de meio de pagamento que receberá a configuração definida anteriormente.
		              // Você poderá incluir e excluir os grupos de meios de pagamento Boleto, Débito, Cartão de Crédito, Depósito Bancário e Saldo PagSeguro.
		              // Já no parâmetro nome você informará qual o meio de pagamento que receberá a configuração definida anteriormente. Os meios são as bandeiras e bancos disponíveis.
		              //Atenção:  - Não é possível incluir e excluir o mesmo grupo de meio de pagamento (Ex.: incluir e excluir o grupo CREDIT_CARD).
		              // - Não é possível incluir um grupo e um meio do mesmo grupo (Ex.: incluir grupo cartão e bandeira visa na mesma chamada);
		              // - Não é possível excluir um grupo e um meio do mesmo grupo (Ex.: excluir grupo cartão e bandeira visa na mesma chamada);
		              // - Não é possível incluir e excluir o mesmo meio de pagamento (Ex.: incluir e excluir a bandeira VISA).

		              .withAcceptedPaymentMethods(new AcceptedPaymentMethodsBuilder()
		                 
		            		  .addInclude(new PaymentMethodBuilder()
		            				  .withGroup(PaymentMethodGroup.BANK_SLIP)
		            				  )
		            		  .addInclude(new PaymentMethodBuilder()
		            				  .withGroup(PaymentMethodGroup.CREDIT_CARD)
		            				  )
		                  
		              )

		              //Para definir o percentual de desconto para um meio de pagamento você deverá utilizar três parâmetros: grupo de meios de pagamento, chave configuração de desconto e o percentual de desconto. No parâmetro de grupo você deve informar um dos meios de pagamento disponibilizados pelo PagSeguro. Após definir o grupo é necessário definir os a configuração dos campos chave e valor.
//		              .addPaymentMethodConfig(new PaymentMethodConfigBuilder()
//		                  .withPaymentMethod(new PaymentMethodBuilder()
//		                      .withGroup(PaymentMethodGroup.CREDIT_CARD)
//		                  )
//		                  .withConfig(new ConfigBuilder()
//		                      .withKey(ConfigKey.DISCOUNT_PERCENT)
//		                      .withValue(new BigDecimal(00.00))
//		                  )
//		              )
//		              .addPaymentMethodConfig(new PaymentMethodConfigBuilder()
//		                  .withPaymentMethod(new PaymentMethodBuilder()
//		                      .withGroup(PaymentMethodGroup.BANK_SLIP)
//		                  )
//		                  .withConfig(new ConfigBuilder()
//		                      .withKey(ConfigKey.DISCOUNT_PERCENT)
//		                      .withValue(new BigDecimal(00.00))
//		                  )
//		              )

		              //Para definir o parcelamento você deverá utilizar três parâmetros: grupo, chave e valor.
		              // No parâmetro grupo você informará qual o meio pagamento que receberá as configurações.
		              // Para limitar o parcelamento você deve informar o meio de pagamento Cartão de crédito (CREDIT_CARD).
		              //Após definir o grupo você deverá definir as configurações nos campos chave e valor.
		              // Estes devem ser definidos com a chave MAX_INSTALLMENTS_LIMIT que define a configuração de limite de parcelamento e como valor o número de parcelas que você deseja apresentar ao cliente (de 2 a 18 parcelas).

		              .addPaymentMethodConfig(new PaymentMethodConfigBuilder()
		                  .withPaymentMethod(new PaymentMethodBuilder()
		                      .withGroup(PaymentMethodGroup.CREDIT_CARD)
		                  )
		                  .withConfig(new ConfigBuilder()
		                      .withKey(ConfigKey.MAX_INSTALLMENTS_LIMIT)
		                      .withValue(new BigDecimal(10))
		                  )
		              )
		              .addPaymentMethodConfig(new PaymentMethodConfigBuilder()
		                  .withPaymentMethod(new PaymentMethodBuilder()
		                      .withGroup(PaymentMethodGroup.CREDIT_CARD)
		                  )
		                  .withConfig(new ConfigBuilder()
		                      .withKey(ConfigKey.MAX_INSTALLMENTS_NO_INTEREST)
		                      .withValue(new BigDecimal(5))
		                  )
		              )
		      );
		      System.out.println(registeredCheckout.getRedirectURL());
		      FacesContext.getCurrentInstance().getExternalContext().redirect(registeredCheckout.getRedirectURL());
		      checkoutCode = registeredCheckout.getCheckoutCode();
		    }catch (Exception e){
		      e.printStackTrace();
		    }
//		try {
//			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
////			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
////		    externalContext.redirect(url);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	

	public BigDecimal getValorDoacao() {
		return valorDoacao;
	}

	public void setValorDoacao(BigDecimal valorDoacao) {
		this.valorDoacao = valorDoacao;
	}


	public String getCheckoutCode() {
		return checkoutCode;
	}


	public void setCheckoutCode(String checkoutCode) {
		this.checkoutCode = checkoutCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ProdutoDTO getProdutoDTO() {
		return produtoDTO;
	}

	public void setProdutoDTO(ProdutoDTO produtoDTO) {
		this.produtoDTO = produtoDTO;
	}

}
