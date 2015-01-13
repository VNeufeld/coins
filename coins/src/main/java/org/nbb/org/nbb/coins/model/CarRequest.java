package org.nbb.org.nbb.coins.model;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("carRequest")
@RequestScoped
public class CarRequest  {
	private Date pickupDate;
	private Date dropoffDate;
	
	public Date getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}
	public Date getDropoffDate() {
		return dropoffDate;
	}
	public void setDropoffDate(Date dropoffDate) {
		this.dropoffDate = dropoffDate;
	}

}
