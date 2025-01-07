<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.FavoriteListCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>FavoriteList View</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/utilities.js"></script>
<script type="text/javascript">
	function numberLength(input) {
		if (input.value.length > 10) {
			input.value = input.value.slice(0, 10);
		}
	}
</script>
<style type="text/css">
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
	background-image: linear-gradient(to bottom right, white, pink);
	background-repeat: no-repeat;
	background-size: 100%;
	padding-bottom: 11px;
}

.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/download (3).jpeg');
	background-size: cover;
	padding-top: 6%;
}
</style>
</head>
<body class="hm">
	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>
	<main>
	<form action="<%=ORSView.FAVORITELIST_CTL%>" method="post">
		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.FavoriteListDTO"
			scope="request"></jsp:useBean>
		<div class="row pt-3">
			<div class="col-md-4 mb-4"></div>
			<div class="col-md-4 mb-4">
				<div class="card input-group-addon">
					<div class="card-body">
						<%
							long id = DataUtility.getLong(request.getParameter("id"));
						%>
						<%
							if (dto.getId() != null && id > 0) {
						%>
						<h3 class="text-center default-text text-primary">Update
							FavoriteList</h3>
						<%
							} else {
						%>
						<h3 class="text-center default-text text-primary">Add
							FavoriteList</h3>
						<%
							}
						%>

						<div>
							<%
								Map map = (Map) request.getAttribute("imp");
							%>
							<h4 align="center">
								<%
									if (!ServletUtility.getSuccessMessage(request).isEmpty()) {
								%>
								<div class="alert alert-success alert-dismissible">
									<button type="button" class="close" data-dismiss="alert">&times;</button>
									<%=ServletUtility.getSuccessMessage(request)%>
								</div>
								<%
									}
								%>
							</h4>

							<h4 align="center">
								<%
									if (!ServletUtility.getErrorMessage(request).isEmpty()) {
								%>
								<div class="alert alert-danger alert-dismissible">
									<button type="button" class="close" data-dismiss="alert">&times;</button>
									<%=ServletUtility.getErrorMessage(request)%>
								</div>
								<%
									}
								%>
							</h4>
							<input type="hidden" name="id" value="<%=dto.getId()%>">
						</div>

						<!-- Input Fields -->
						<!-- Product ------------------------------------------------------------------------------------------------------------------------------>


						<span class="pl-sm-5"><b>Product</b><span
							style="color: red;">*</span></span></br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-paper-plane grey-text"
											style="font-size: 1rem;"></i>
									</div>
								</div>
								<%=HTMLUtility.getList1("product", String.valueOf(dto.getProduct()), map)%>
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("product", request)%></font></br>

						<!--addedDate---------------------------------------------------------------------------------------------------------------------------------->
						<span class="pl-sm-5"><b>AddedDate</b> <span
							style="color: red;">*</span></span></br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-calendar grey-text" style="font-size: 1rem;"></i>
									</div>
								</div>
								<input type="text" id="datepicker" name="addedDate"
									class="form-control" placeholder=" Enter AddedDate "
									readonly="readonly"
									value="<%=DataUtility.getDateString(dto.getAddedDate())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("addedDate", request)%></font></br>

						<!--addedDate---------------------------------------------------------------------------------------------------------------------------------->

						<span class="pl-sm-5"><b> UserName</b> <span
							style="color: red;">*</span></span> </br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-user grey-text" style="font-size: 1rem;"></i>
									</div>
								</div>
								<input type="text" class="form-control" name="userName"
									placeholder=" Enter userName"
									oninput="handleLetterInput(this, 'userNameError', 10)"
									onblur="validateLetterInput(this, 'userNameError', 10)"
									value="<%=DataUtility.getStringData(dto.getUserName())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5" id="userNameError"> <%=ServletUtility.getErrorMessage("userName", request)%></font></br>




						<!--  ----------------------------------------------------------------------------------------------------------------------------->
						<span class="pl-sm-5"><b>Notes Or Comment</b> <span
							style="color: red;">*</span></span></br>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-user-circle grey-text"
											style="font-size: 1rem;"></i>
									</div>
								</div>
								<textarea name="comment" class="form-control" 
									placeholder="Enter comment"
									oninput="handleLetterInput(this, 'commentError', 200)"
									onblur="validateLetterInput(this, 'commentError', 200)"
									rows="2" cols="2"><%=DataUtility.getStringData(dto.getComment())%></textarea>
							</div>
						</div>
						<font color="red" class="pl-sm-5" id="commentError"> <%=ServletUtility.getErrorMessage("comment", request)%></font></br>


						<!-- Buttons ------------------------------------------------------------------------------------------------------------------------------------->
						<%
							if (dto.getId() != null && id > 0) {
						%>
						<div class="text-center">
							<input type="submit" name="operation"
								class="btn btn-success btn-md"
								value="<%=FavoriteListCtl.OP_UPDATE%>"> <input
								type="submit" name="operation" class="btn btn-warning btn-md"
								value="<%=FavoriteListCtl.OP_CANCEL%>">
						</div>
						<%
							} else {
						%>
						<div class="text-center">
							<input type="submit" name="operation"
								class="btn btn-success btn-md"
								value="<%=FavoriteListCtl.OP_SAVE%>"> <input
								type="submit" name="operation" class="btn btn-warning btn-md"
								value="<%=FavoriteListCtl.OP_RESET%>">
						</div>
						<%
							}
						%>
					</div>
				</div>
			</div>
			<div class="col-md-4 mb-4"></div>
		</div>
	</form>
	</main>
	<%@include file="FooterView.jsp"%>
</body>
</html>
