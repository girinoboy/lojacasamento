package br.com.pagseguroexemplo;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;

public class SearchTransactionByNotificationCode {

	public static void main(String[] args){
		try {
			String sellerEmail = "marcleonio@gmail.com";
			String sellerToken = "D19055F3A2D54AD48B37C23BC15545D4";

			final PagSeguro pagSeguro = PagSeguro.instance(Credential.sellerCredential(sellerEmail,
					sellerToken), PagSeguroEnv.SANDBOX);

			TransactionDetail transaction = pagSeguro.transactions().search().byNotificationCode("91922B2602C102C19DEAA4413F9EA8011698");
			System.out.println(transaction);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
