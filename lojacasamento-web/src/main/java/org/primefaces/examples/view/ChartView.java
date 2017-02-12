package org.primefaces.examples.view;
 
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.joda.time.LocalDate;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;

import br.com.dto.ProdutoDTO;
import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.common.domain.DataList;
import br.com.uol.pagseguro.api.common.domain.builder.DateRangeBuilder;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.transaction.search.TransactionSummary;

import org.primefaces.model.chart.ChartSeries;
 
@ManagedBean
@ViewScoped
public class ChartView implements Serializable {
 
    private BarChartModel barModel;
    private HorizontalBarChartModel horizontalBarModel;
    private String sellerEmail = "marcleonio@gmail.com";
    private String sellerToken = "D19055F3A2D54AD48B37C23BC15545D4";
    final PagSeguro pagSeguro;
    
    public ChartView(){
    	
    	pagSeguro = PagSeguro.instance(Credential.sellerCredential(sellerEmail,
    			sellerToken), PagSeguroEnv.SANDBOX);
    }
 
    @PostConstruct
    public void init() {

        createBarModels();
    }
 
    public BarChartModel getBarModel() {
        return barModel;
    }
     
    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }
 
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 120);
        boys.set("2005", 100);
        boys.set("2006", 44);
        boys.set("2007", 150);
        boys.set("2008", 25);
 
        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 110);
        girls.set("2007", 135);
        girls.set("2008", 120);
 
        model.addSeries(boys);
        model.addSeries(girls);
         
        return model;
    }
     
    private void createBarModels() {
        createBarModel();
        createHorizontalBarModel();
    }
     
    private void createBarModel() {
        barModel = initBarModel();
         
        barModel.setTitle("Bar Chart");
        barModel.setLegendPosition("ne");
         
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Gender");
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Births");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }
     
    private void createHorizontalBarModel() {
        horizontalBarModel = new HorizontalBarChartModel();
 
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
//        boys.set("2004", 50);
//        boys.set("2005", 96);
//        boys.set("2006", 44);
//        boys.set("2007", 55);
        boys.set("Arrecadado", 25);
 
        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
//        girls.set("2004", 52);
//        girls.set("2005", 60);
//        girls.set("2006", 82);
//        girls.set("2007", 35);
        girls.set("Arrecadado", 120);
        
        ChartSeries undefined = new ChartSeries();
        undefined.setLabel("email1");
        undefined.set("Arrecadado", 10);
        
        ChartSeries undefined2 = new ChartSeries();
        undefined2.setLabel("email2");
        undefined2.set("Arrecadado", 40);
 
        horizontalBarModel.addSeries(boys);
        horizontalBarModel.addSeries(girls);
        horizontalBarModel.addSeries(undefined);
        horizontalBarModel.addSeries(undefined2);
         
//        horizontalBarModel.setTitle("Horizontal and Stacked");
//        horizontalBarModel.setLegendPosition("e");
        horizontalBarModel.setStacked(true);
        horizontalBarModel.setAnimate(true);
//        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
//        xAxis.setLabel("Births");
//        xAxis.setMin(0);
//        xAxis.setMax(200);
         
//        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
//        yAxis.setLabel("Gender");        
    }
    
    public void criarDinamicamente(){
    	
    }
    
    public HorizontalBarChartModel creatBar(ProdutoDTO produtoDTO){
    	HorizontalBarChartModel horizontalBarModel = new HorizontalBarChartModel();
    	horizontalBarModel.setStacked(true);
    	horizontalBarModel.setAnimate(true);
    	try {
    		final DataList<? extends TransactionSummary> transactions =
    				pagSeguro.transactions().search().byReference(produtoDTO.getLojaDTO().getNome());

    		
    		for (TransactionSummary transactionSummary : transactions) {
    			if(transactionSummary.getDetail().getItems().get(0).getId().equals(produtoDTO.getId().toString())){
    				ChartSeries produto = new ChartSeries();
    				//produto.setLabel(produtoDTO.getId().toString());
    				produto.set("Arrecadado", transactionSummary.getGrossAmount().subtract(transactionSummary.getExtraAmount()));
    				horizontalBarModel.addSeries(produto);
    			}
    		}

    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	
    	if(horizontalBarModel.getSeries().isEmpty()){
    		ChartSeries produto = new ChartSeries();
    		produto.set("Arrecadado",new BigDecimal(0));
			horizontalBarModel.addSeries(produto);
		}
    	return horizontalBarModel;

    }

}