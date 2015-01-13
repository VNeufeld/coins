package org.nbb.org.nbb.coins.model;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class CarBean {
	@Inject
	private Logger log;
	
	public CarBean() {
		
	}
	
	public CarBean(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@PostConstruct
	public void init() {
		log.info("@PostConstruct CarBean "+this);
	}
	
	private long id;
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
