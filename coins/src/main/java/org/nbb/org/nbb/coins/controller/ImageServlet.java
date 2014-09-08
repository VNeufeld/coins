package org.nbb.org.nbb.coins.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.nbb.org.nbb.coins.service.CoinService;
import org.nbb.org.nbb.coins.service.ImageService;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	private CoinService coinService;

	@Inject
	private ImageService imageService;

	/**
	 * Default constructor.
	 */
	public ImageServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String imageId = String.valueOf(request.getPathInfo().substring(1)); // Gets
																				// string
																				// that
																				// goes
																				// after
																				// "/images/".
		
		//byte[] image = imageService.getImage("20140904_141457");
		byte[] image = imageService.getImage(imageId);

		response.setHeader("Content-Type","image/jpeg");

		BufferedOutputStream output = null;

		try {
			
			output = new BufferedOutputStream(response.getOutputStream());
			IOUtils.write(image, output);
			
		} finally {
			if (output != null)
				try {
					output.close();
				} catch (IOException logOrIgnore) {
				}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
