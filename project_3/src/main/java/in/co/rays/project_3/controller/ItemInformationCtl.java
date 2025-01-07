package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ItemInformationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ItemInformationModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "ItemInformationCtl", urlPatterns = { "/ctl/ItemInformationCtl" })
public class ItemInformationCtl extends BaseCtl {

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

		if (DataValidator.isNull(request.getParameter("title"))) {
			request.setAttribute("title", PropertyReader.getValue("error.require", " title"));
			pass = false;
////		}else if (!DataValidator.isNamemaxlegntten(request.getParameter("title"))) {
//			request.setAttribute("title", "title should be 20 characters");
//			pass = false;
//			

		}

		if (DataValidator.isNull(request.getParameter("overview"))) {
			request.setAttribute("overview", PropertyReader.getValue("error.require", "overview"));
			System.out.println(pass);
			pass = false;
			/*
			 * }else if
			 * (!DataValidator.isNamemaxlegntfifty(request.getParameter("description"))) {
			 * request.setAttribute("description", "description should be 20 characters");
			 * pass = false;
			 */

		}
		if (!OP_UPDATE.equalsIgnoreCase(request.getParameter("operation"))) {

		
			if (DataValidator.isNull(request.getParameter("cost"))) {
				request.setAttribute("cost", PropertyReader.getValue("error.require", "cost"));
				pass = false;


			} else if (!DataValidator.isInteger(request.getParameter("cost"))) {
				request.setAttribute("cost", " Only numbers are allowed");
				System.out.println(pass);
				pass = false;

			}
			
			
			if (DataValidator.isNull(request.getParameter("purchaseDate"))) {
				request.setAttribute("purchaseDate", PropertyReader.getValue("error.require", "purchaseDate"));
				pass = false;

			}

			if (DataValidator.isNull(request.getParameter("category"))) {
				request.setAttribute("category", PropertyReader.getValue("error.require", "category"));
				pass = false;
			}

			
		}
		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		ItemInformationDTO dto = new ItemInformationDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setTitle(DataUtility.getString(request.getParameter("title")));
		dto.setOverview(DataUtility.getString(request.getParameter("overview")));
	    dto.setCost(DataUtility.getInt(request.getParameter("cost")));
		dto.setPurchaseDate(DataUtility.getDate(request.getParameter("purchaseDate")));
		dto.setCategory(DataUtility.getString(request.getParameter("category")));

		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		ItemInformationModelInt model = ModelFactory.getInstance().getItemInformationModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			ItemInformationDTO dto;
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		ItemInformationModelInt model = ModelFactory.getInstance().getItemInformationModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			ItemInformationDTO dto = (ItemInformationDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);

					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {

					try {
						model.add(dto);

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

			ItemInformationDTO dto = (ItemInformationDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.ITEMINFORMATION_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ITEMINFORMATION_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ITEMINFORMATION_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ITEMINFORMATION_VIEW ;
	}

}
