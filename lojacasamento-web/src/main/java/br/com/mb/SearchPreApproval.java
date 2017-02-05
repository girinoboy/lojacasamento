package br.com.mb;

import javax.xml.bind.DatatypeConverter;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.common.domain.DataList;
import br.com.uol.pagseguro.api.common.domain.builder.DateRangeBuilder;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;

public class SearchPreApproval {

	public static void main(String[] args){

		String sellerEmail = "marcleonio@gmail.com";
		String sellerToken = "D19055F3A2D54AD48B37C23BC15545D4";

		final PagSeguro pagSeguro = PagSeguro
				.instance(new SimpleLoggerFactory(), new JSEHttpClient(),
						Credential.sellerCredential(sellerEmail, sellerToken), PagSeguroEnv.SANDBOX);

		try{

			// Busca de assinaturas
			DataList dataList = pagSeguro.preApprovals().search().byDateRange(
					new DateRangeBuilder().between(
							DatatypeConverter.parseDateTime("2016-10-01T00:00:00.000-03:00").getTime(),
							DatatypeConverter.parseDateTime("2016-10-03T15:56:00.000-03:00").getTime()),
					1,
					10
					);
			System.out.println(dataList);

		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
