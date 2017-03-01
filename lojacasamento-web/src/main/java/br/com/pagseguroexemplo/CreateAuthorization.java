package br.com.pagseguroexemplo;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.application.authorization.AuthorizationRegistration;
import br.com.uol.pagseguro.api.application.authorization.AuthorizationRegistrationBuilder;
import br.com.uol.pagseguro.api.application.authorization.RegisteredAuthorization;
import br.com.uol.pagseguro.api.common.domain.PermissionCode;
import br.com.uol.pagseguro.api.credential.Credential;

public class CreateAuthorization {

	public CreateAuthorization() {
		super();
	}
	
	public static void main(String[] args) {


	    String appId = "app0963714685";
	    String appKey = "E737275B949425C774E94F92454707F6";


	    try{

	      final PagSeguro pagSeguro = PagSeguro.instance(Credential.applicationCredential(appId,
	          appKey), PagSeguroEnv.SANDBOX);


	      // Registra as autorizações
	      AuthorizationRegistration authorizationRegistration =
	          new AuthorizationRegistrationBuilder()
	              .withNotificationUrl("www.lojatesteste.com.br/notification")
	              .withReference("REF_001")
	              .withRedirectURL("www.lojatesteste.com.br")
	              .addPermission(PermissionCode.Code.CREATE_CHECKOUTS)
	              .build();

	      RegisteredAuthorization ra = pagSeguro.authorizations().register(authorizationRegistration);
	      System.out.print(ra);

	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

}
