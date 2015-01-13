package org.nbb.org.nbb.coins.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.nbb.org.nbb.coins.model.CarBean;
import org.nbb.org.nbb.coins.model.CarRequest;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class CarController implements Serializable {
	@Inject
	private Logger log;

//	@Produces
//	@Named
//	private CarRequest newCarRequest;
	
	private List<CarBean>   cars = new ArrayList<CarBean>();
	
	@Named("cars")
	public List<CarBean> getCars() {
		log.info("car controller "+ this + " cars " + cars);
		return cars;
	}


	@PostConstruct
	public void init() {
		log.info("@PostConstruct CarController");
//		newCarRequest = new CarRequest();
	}

	public void retrieveCars(CarRequest carRequest) {
		log.info("retrieveCars cars " + carRequest.getPickupDate());
		cars.clear();
		
		CarBean car = new CarBean(1,"Ford");
		cars.add(car);

	}
	
	public void edit(CarBean carEdit) {
		log.info("edit car " + carEdit.getId());
		for (CarBean car : cars) {
			if(car.getId() == carEdit.getId())
				car.setId(car.getId() + 1);
		}

	}

	public void delete(CarBean carEdit) {
		log.info("delet car " + carEdit.getId());
		for (CarBean car : cars) {
			if(car.getId() == carEdit.getId()) {
				cars.remove(car);
				break;
			}
		}

	}
	

}
