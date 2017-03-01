package br.com.mb;

import java.io.UnsupportedEncodingException;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;

public class SearchTransactionByCode {

	public static void main(String[] args) throws UnsupportedEncodingException {
		try {
			String sellerEmail = "marcleonio@gmail.com";
			String sellerToken = "D19055F3A2D54AD48B37C23BC15545D4";

			final PagSeguro pagSeguro = PagSeguro.instance(Credential.sellerCredential(sellerEmail,
					sellerToken), PagSeguroEnv.SANDBOX);

			TransactionDetail transaction = pagSeguro.transactions().search().byCode("1D8B0440C00748CF980870A945A8A31A");
			System.out.println(transaction);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
