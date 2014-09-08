package org.nbb.org.nbb.coins.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.ejb.Stateless;

import org.apache.commons.io.IOUtils;

@Stateless
public class ImageService {
	
	private static String imageDir = "F:\\Media\\Fotos\\2014\\";
	
	public byte[] getImage(String name) throws IOException {
		
		File file = new File(imageDir+name+".jpg");
		
		byte[] result = null;
		
		if(file.isFile() && file.exists() ) {
			FileInputStream fis = new FileInputStream(file);
			result = IOUtils.toByteArray(fis);
		}
		
		return result;
	}

}
