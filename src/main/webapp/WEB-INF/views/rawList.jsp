<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-lg-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<form:form action="${pageContext.request.contextPath}/raw-material/search" method="post"
				modelAttribute="rawMaterialSearch">

				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Select Dealer</label>
							<div class="col-sm-9">
								<form:select path="appUserId" class="form-control">
									<form:option value="">Select Any Dealer</form:option>
									<form:options items="${userList}" itemLabel="name"
										itemValue="id" />
								</form:select>
							</div>

						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label"> Material Type</label>
							<div class="col-sm-9">
								<form:select path="materialTypeId" class=" form-control">
									<form:option value="">Select Any Material Type</form:option>
									<form:options items="${rawList}" itemLabel="name"
										itemValue="id" />
								</form:select>
							</div>

						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Start Date</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control date" path="startDate" />

							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">End Date</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control date" path="endDate" />

							</div>
						</div>
					</div>
				</div>
				<div class="form-group row float-right">
					<button class="btn btn-success btn-fw" type="submit" id="Search">Submit</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
<div class="col-lg-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<h4 class="card-title">Raw Material Table</h4>
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>
			<c:if test="${not empty fail}">
				<div class="alert alert-danger" role="alert">${fail}</div>
			</c:if>
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Dealer</th>
							<th>Material Type</th>
							<th>Date</th>
							<th>Chalan Number</th>
							<th>Quantity</th>
							<th>Amount</th>
							<th>Unit</th>
							<th>Action</th>

						</tr>
					</thead>
					<c:url var='updatelink' value="/raw-material/edit" />

					<c:url var="deletelink" value="/raw-material/delete" />
					<tbody>
						<c:forEach items="${rawMaterial}" var="templist">
							<tr>
								<td>${templist.dealer.name}</td>
								<td>${templist.material.name}</td>
								<td>${templist.date}</td>
								<td>${templist.chalanNumber}</td>
								<td>${templist.quantity}</td>
								<td>${templist.amount}</td>
								<td>${templist.unit}</td>
								<td><a href="${updatelink}/${templist.id}"
									class="btn btn-success btn-fw">Edit</a><a
									href="${deletelink}/${templist.id}"
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
						href="${pageContext.request.contextPath}/raw-material/${currentPage - 1}"><i
							class="mdi mdi-chevron-left"></i></a></li>
				</c:if>
				<c:forEach var="i" begin="1" end="${totalPages}">
					<c:choose>
						<c:when test="${i==currentPage}">
							<li class="page-item active"><a class="page-link">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link"
								href="<c:url value="/raw-material/${i}"/>">${i}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${currentPage!= totalPages}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath}/raw-material/${currentPage + 1}"><i
							class="mdi mdi-chevron-right"></i></a></li>
				</c:if>
			</ul>
		</c:if>

	</div>
</div>