package br.com.mb;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.session.CreatedSession;

/**
 * @author PagSeguro Internet Ltda.
 */
public class CreateApplicationSession {

  public static void main(String[] args){

	  String appId = "app0963714685";
	  String appKey = "E737275B949425C774E94F92454707F6";

    try {
      final PagSeguro pagSeguro = PagSeguro.instance(Credential.applicationCredential(appId,
          appKey), PagSeguroEnv.SANDBOX);

      // Criacao de sessao de Aplicacao
      CreatedSession createdSessionApplication = pagSeguro.sessions()
          .create("843D9A7D2BFD4D1C871C79EE63FE9421");
      System.out.println(createdSessionApplication.getId());
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}

