package org.primefaces.showcase.view.data;
 
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.showcase.domain.Car;
import org.primefaces.showcase.service.CarService;
 
@ManagedBean
@ViewScoped
public class DataListView implements Serializable {
     
    /**
	 * 
	 */
	private static final long serialVersionUID = -5389376885719475696L;

	private List<Car> cars1;
         
    private Car selectedCar;
     
    @ManagedProperty("#{carService}")
    private CarService service;
     
    @PostConstruct
    public void init() {
        cars1 = service.createCars(10);
    }
 
    public List<Car> getCars1() {
        return cars1;
    }
 
    public void setService(CarService service) {
        this.service = service;
    }
 
    public Car getSelectedCar() {
        return selectedCar;
    }
 
    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }
}