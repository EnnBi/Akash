<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="col-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<h4 class="card-title">Manufacture Search</h4>
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>
			<c:if test="${not empty fail}">
				<div class="alert alert-danger" role="alert">${fail}</div>
			</c:if>
			<form:form action="${pageContext.request.contextPath}/manufacture/search" class="form-sample"
				modelAttribute="manufactureSearch" method="post">
				<div class="row">
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Product</label>
							<div class="col-sm-8">
								<form:select path="productId" class=" form-control" id="product">
									<form:option value="">Select any Product</form:option>
									<form:options items="${products}" itemLabel="name"
										itemValue="id" />
								</form:select>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Size</label>
							<div class="col-sm-8">
								<form:select path="sizeId" class=" form-control" id="size">
									<form:option value="">Select any Product</form:option>
									<form:options items="${sizes}" itemLabel="name"
										itemValue="id" />
								</form:select>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Name</label>
							<div class="col-sm-8">
								<form:select class="form-control" path="labourId"
									id="customer" >
									<form:option value="">Select Customer</form:option>
									<form:options items="${labours}" itemLabel="name" itemValue="id"/>
								</form:select>
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Start Date</label>
							<div class="col-sm-8">
								<form:input type="text" class="form-control date" path="startDate"
									required="required" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">End Date</label>
							<div class="col-sm-8">
								<form:input type="text" class="form-control date" path="endDate"
									required="required" />
							</div>
						</div>
					</div>
				</div>
				<div class="form-group row float-right">
							<input type="submit" class="btn btn-success btn-fw"
								value="Submit">
						</div>
			</form:form>
		</div>
	</div>
</div>
<div class="col-lg-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<h4 class="card-title">Manufacture Table</h4>
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Product</th>
							<th>Size</th>
							<th>Date</th>
							<th>Quantity</th>
							<th>Amount</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
					
					<c:forEach items="${manufactures}"  var="manufacture"> 
						<tr>
							<td>${manufacture.product}</td>
							<td>${manufacture.size}</td>
							<td>${manufacture.date}</td>
							<td>${manufacture.quantity}</td>
							<td>${manufacture.amount}</td>
							<td><a href="${pageContext.request.contextPath}/manufacture/edit/${manufacture.id}"
									class="btn btn-success btn-fw" style="margin-right: 5px">Edit</a><a
									href="${pageContext.request.contextPath}/manufacture/delete/${manufacture.id}"
									class="btn btn-danger btn-fw">Delete</a></td>
						</tr>
					</c:forEach>	
					</tbody>
				</table>
			</div>
		</div>
		<c:if test="${totalPages>0}">
			<ul
				class="pagination rounded-flat pagination-success d-flex justify-content-center">
				<c:if test="${currentPage !=1}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath}/manufacture/pageno=${currentPage - 1}"><i
							class="mdi mdi-chevron-left"></i></a></li>
				</c:if>
				<c:forEach var="i" begin="1" end="${totalPages}">
					<c:choose>
						<c:when test="${i==currentPage}">
							<li class="page-item active"><a class="page-link">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link"
								href="<c:url value="/manufacture/pageno=${i}"/>">${i}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${currentPage!= totalPages}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath}/manufacture/pageno=${currentPage + 1}"><i
							class="mdi mdi-chevron-right"></i></a></li>
				</c:if>
			</ul>
		</c:if>
	</div>

</div>
