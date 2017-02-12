package org.primefaces.showcase.view.file;
 
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;

import org.hibernate.HibernateException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.dto.LojaDTO;
import br.com.dto.ProdutoDTO;
import br.com.mb.GenericMB;
 
@ManagedBean
@ViewScoped
public class FileUploadView extends GenericMB<LojaDTO>{
     
    /**
	 * 
	 */
	private static final long serialVersionUID = -6415669608030650984L;

	private UploadedFile file;
    
    private String filename;
    private ProdutoDTO produtoDTO = new ProdutoDTO();
    
    public FileUploadView() throws HibernateException, Exception{
//    	List<?> list = abstractDAO.consultaHQL("FROM LojaDTO l WHERE l.nome ="+abstractDTO.getLojaDTO().getNome());
//    	if(!list.isEmpty()){
//	    	LojaDTO lojaDTO = (LojaDTO) list.iterator().next();
//	    	abstractDTO.setLojaDTO(lojaDTO);
//    	}
    	System.out.println(abstractDTO);
    }
    
    public void upload() {
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
        salvarArquivo();
    }

	private void salvarArquivo() {
		
        
        try {
        	List<ProdutoDTO> listProduto = new ArrayList<ProdutoDTO>();
        	
//        	produtoDTO.setDescricao("Descricao do produto");
//        	produtoDTO.setPreco(new BigDecimal(getRandomImageName()));
        	produtoDTO.setImagem(filename);
        	produtoDTO.setLojaDTO(abstractDTO);
			listProduto.add(produtoDTO);
        	abstractDTO.setListProduto(listProduto);;
			abstractDAO.save(abstractDTO);
			produtoDTO = new ProdutoDTO();
			filename = null;
			inicio();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public void handleFileUpload(FileUploadEvent event) {
    	file = event.getFile();
    	
    	filename = file.getFileName().replaceAll(" ", "-");
        byte[] data = file.getContents();
 
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String dir = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" +
		                                    File.separator + "images" + File.separator + "brand" + File.separator;
		String newFileName = dir + filename;
		File file = new File(dir);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        if((new File(newFileName)).exists()){
        	newFileName = dir +"brand_"+ filename;
        }
        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        }
        catch(IOException e) {
            throw new FacesException("Error in writing image.", e);
        }
    	
    	
//    	salvarArquivo();
        FacesMessage message = new FacesMessage(rb.getString("succesful"), event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    private String getRandomImageName() {
        int i = (int) (Math.random() * 4500);
         
        return String.valueOf(i);
    }
    
    public String getFilename() {
        return filename;
    }
 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }

	public ProdutoDTO getProdutoDTO() {
		return produtoDTO;
	}

	public void setProdutoDTO(ProdutoDTO produtoDTO) {
		this.produtoDTO = produtoDTO;
	}
}