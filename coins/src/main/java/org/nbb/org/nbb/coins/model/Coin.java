package org.nbb.org.nbb.coins.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
@Table(name="Coins")
public class Coin implements Serializable {
	
	
	@Id
    @GeneratedValue
    private Long id;
	
	@Column(name = "name")
	private String name;
	
	private String land;
	
	private Integer  year;
	
	@Column(name = "imagename")
	private String imageName;
	
	private byte[] image;
	
	public Coin() {
	}
	
	
	public Coin(long id, String name, String land, int year, String imageName ) {
		this.id = id;
		this.name = name;
		this.land = land;
		this.year = year;
		this.imageName = imageName;
	
	}
	
	public Coin(long id, String name, String land, int year) {
		this.id = id;
		this.name = name;
		this.land = land;
		this.year = year;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}


	
	

}
