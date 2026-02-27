<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<h4 class="card-title">Cement Consumption</h4>
			<form:form action="${pageContext.request.contextPath}/manufacture/cement-consumption/search"
				modelAttribute="manufactureSearch" method="post">
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Start Date</label>
							<div class="col-sm-8">
								<form:input type="text" class="form-control date" path="startDate" required="required"/>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">End Date</label>
							<div class="col-sm-8">
								<form:input type="text" class="form-control date" path="endDate" required="required"/>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group row float-right">
					<input type="submit" class="btn btn-success btn-fw" value="Submit">
				</div>
			</form:form>
		</div>
	</div>
</div>
<c:if test="${not empty manufactures}">
<div class="col-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<h4 class="card-title">Total Cement Consumption: ${totalCement}</h4>
		</div>
	</div>
</div>
<div class="col-lg-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Product</th>
							<th>Size</th>
							<th>Date</th>
							<th>Quantity</th>
							<th>Amount</th>
							<th>Cement</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${manufactures}" var="m">
							<tr>
								<td>${m.product.name}</td>
								<td>${m.size.name}</td>
								<td>${m.date}</td>
								<td>${m.totalQuantity}</td>
								<td>${m.totalAmount}</td>
								<td>${m.cement}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<c:if test="${totalPages > 0}">
			<ul class="pagination rounded-flat pagination-success d-flex justify-content-center">
				<c:if test="${currentPage != 1}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath}/manufacture/cement-consumption/pageno=${currentPage - 1}">
						<i class="mdi mdi-chevron-left"></i></a></li>
				</c:if>
				<c:forEach var="i" begin="1" end="${totalPages}">
					<c:choose>
						<c:when test="${i == currentPage}">
							<li class="page-item active"><a class="page-link">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link"
								href="<c:url value="/manufacture/cement-consumption/pageno=${i}"/>">${i}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${currentPage != totalPages}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath}/manufacture/cement-consumption/pageno=${currentPage + 1}">
						<i class="mdi mdi-chevron-right"></i></a></li>
				</c:if>
			</ul>
		</c:if>
	</div>
</div>
</c:if>
