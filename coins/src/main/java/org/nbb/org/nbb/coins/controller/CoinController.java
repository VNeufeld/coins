/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nbb.org.nbb.coins.controller;

import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.nbb.org.nbb.coins.data.CoinSessionStore;
import org.nbb.org.nbb.coins.model.Coin;
import org.nbb.org.nbb.coins.service.CoinService;
import org.nbb.org.nbb.coins.service.ImageService;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
@Model
public class CoinController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private CoinService coinService;

    @Inject
    private ImageService imageService;
    
    @Inject
    private CoinSessionStore coinSessionStore;
    
    @Produces
    @Named
    private Coin newCoin;
    
	@Inject
	private Logger log;
	
	@ManagedProperty(value="#{param.id}")
	private Long id; // +setter
  

    @PostConstruct
    public void initNewMember() {
       	log.info("initNewMember id = "+id);
       	
    	newCoin = new Coin();
    }

    public String add() throws Exception {
        try {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Added!", "Added successful");
            facesContext.addMessage(null, m);
            initNewMember();
            // only test, save seesion object in coinSessionStore
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.getSessionMap().remove("ID");
            
            coinSessionStore.setCoin(null);
            
            return "CoinEdit";
            
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Add unsuccessful");
            facesContext.addMessage(null, m);
        }
        return null;
    }
    
    public String cancel() throws Exception {
           return "StartCoins";
    }

    public String getImage(Coin coin) throws Exception {
    	 String url = "/images/"+coin.getImageName();
    	 return url;
    }
    
    
    public String edit(Coin coin) throws Exception {
        try {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edit!" +coin.getName() , "Edit successful ");
            facesContext.addMessage(null, m);
            newCoin = coin;
            coinSessionStore.setCoin(coin);

            return "CoinEdit";
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Edit unsuccessful");
            facesContext.addMessage(null, m);
        }
        return null;
    }

    public String save(Coin coin) throws Exception {
        try {
            // only test, save seesion object in coinSessionStore
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            Long idx = (Long)context.getSessionMap().get("ID");
            
            Coin coinx = coinSessionStore.getCoin();
            
            log.info("Coinx from store "+coinx);
            if( coinx != null) {
            	coin.setId(coinx.getId());
            	if ( coin.getImageName() != null && coin.getImageName().length() > 0) {
            		byte[] image = imageService.getImage(coin.getImageName());
            		if( image != null && image.length > 0)
            			coin.setImage(image);
            	}
            }

        	coinService.save(coin);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved! "+idx , "Edit successful ");
            facesContext.addMessage(null, m);
            return "StartCoins";
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Saved unsuccessful");
            facesContext.addMessage(null, m);
        }
        return null;
    }
    
    public void saveImage(Coin coin) throws Exception {
        try {
            
            Coin coinx = coinSessionStore.getCoin();
            
            log.info("Coinx from store "+coinx);
            Long idx = null; 
            if( coinx != null) {
            	coin.setId(coinx.getId());
            	idx = coinx.getId();
            }
        	
        	coinService.saveImage(coin);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved image! "+idx , "Edit successful ");
            facesContext.addMessage(null, m);
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Saved unsuccessful");
            facesContext.addMessage(null, m);
        }
    }
    

    public void delete(Coin coin) throws Exception {
        try {
        	coinService.delete(coin);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Deleted!" +coin.getName(), "Delete successful ");
            facesContext.addMessage(null, m);
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Delete unsuccessful");
            facesContext.addMessage(null, m);
        }
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Adding failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }

}
