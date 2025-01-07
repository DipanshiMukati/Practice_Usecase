package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto. FavoriteListDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.FavoriteListModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "FavoriteListCtl", urlPatterns = { "/ctl/FavoriteListCtl" })
public class  FavoriteListCtl extends BaseCtl{
	@Override
	protected void preload(HttpServletRequest request) {

	
		Map<Integer, String> map = new HashMap();
		map.put(1, "Camera");
		map.put(2, "Laptop");
		map.put(3, "Car");
		
		
		request.setAttribute("imp", map);
	    
	}
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		
		if (DataValidator.isNull(request.getParameter("product"))) {
			request.setAttribute("product", PropertyReader.getValue("error.require", "product"));
			pass = false;
			
		}
		if (DataValidator.isNull(request.getParameter("addedDate"))) {
			request.setAttribute("addedDate", PropertyReader.getValue("error.require", "addedDate"));
			pass = false;
		
		}
				
		if (DataValidator.isNull(request.getParameter("userName"))) {
			request.setAttribute("userName", PropertyReader.getValue("error.require", "userName"));
			pass = false;


		} else if (!DataValidator.isName(request.getParameter("userName"))) {
			request.setAttribute("userName", " Only letter are allowed");
			System.out.println(pass);
			pass = false;		

		}
		if (DataValidator.isNull(request.getParameter("comment"))) {
			request.setAttribute("comment", PropertyReader.getValue("error.require", "comment"));
			pass = false;

		}else if ( request.getParameter("comment").length() > 200) {
			request.setAttribute("comment", "comment contain 200 characters long.");
			pass = false;
			return pass;

		} else if (10 > request.getParameter("comment").length()) {
			request.setAttribute("comment", "comment  must be at least 10 characters long.");
			pass = false;
			return pass;

		}
				
		
	
			return pass;
}


	protected BaseDTO populateDTO(HttpServletRequest request) {
		 FavoriteListDTO dto = new  FavoriteListDTO();
		
         
         System.out.println(request.getParameter("purchaseDate"));      
   
		 dto.setId(DataUtility.getLong(request.getParameter("id")));
		 dto.setProduct(DataUtility.getString(request.getParameter("product")));
         dto.setAddedDate(DataUtility.getDate(request.getParameter("addedDate")));
         dto.setUserName(DataUtility.getString(request.getParameter("userName")));
         dto.setComment(DataUtility.getString(request.getParameter("comment")));
         
         

        populateBean(dto,request);
		

		return dto;

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		FavoriteListModelInt model = ModelFactory.getInstance().getFavoriteListModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			 FavoriteListDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		FavoriteListModelInt model = ModelFactory.getInstance().getFavoriteListModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)) {
			 FavoriteListDTO dto = ( FavoriteListDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);
					
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {
					
					try {
						 model.add(dto);
					 System.out.println("cccccccccccccccccccccccccccccc");
						 ServletUtility.setDto(dto, request);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (ApplicationException e) {
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Login id already exists", request);
					}

				}
				ServletUtility.setDto(dto, request);
				
				
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			 FavoriteListDTO dto = ( FavoriteListDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView. FAVORITELIST_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView. FAVORITELIST_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView. FAVORITELIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}
	
	
	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView. FAVORITELIST_VIEW;
	}

	


}