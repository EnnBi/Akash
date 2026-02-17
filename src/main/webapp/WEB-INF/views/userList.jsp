<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-lg-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<form:form action="${pageContext.request.contextPath}/user/search" method="post"
				modelAttribute="appUserSearch">

				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label" id="basic-addon2">Person
								Type</label>
							<div class="col-sm-9">
								<form:select path="userTypeId" class="form-control">
									<form:option value="">Select Any Person Type</form:option>
									<form:options items="${UserList}" itemLabel="name"
										itemValue="id" />
								</form:select>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Name</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control" path="name" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Contact</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control" path="contact" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Account Number</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control"
									path="accountNumber" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Ledger Number</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control" path="ledgerNumber" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label" id="basic-addon2">Labour
								Group</label>
							<div class="col-sm-9">
								<form:select path="labourGroupId" class="form-control">
									<form:option value="">Select Any Labour Group</form:option>
									<form:options items="${labourGroups}" itemLabel="name"
										itemValue="id" />
								</form:select>
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
			<h4 class="card-title">AppUser Table</h4>
			<%-- <p class="card-description">
				Add class
				<code>.table-striped</code>
				
			</p> --%>
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
							<th>User Type</th>
							<th>Name</th>
							<th>Contact</th>
							<th>Ledger Number</th>
							<th>Account Number</th>
							<th>Action</th>

						</tr>
					</thead>
					<c:url var='updatelink' value="/user/edit" />

					<c:url var="deletelink" value="/user/delete" />
					<c:forEach items="${appUser}" var="templist">


						<tbody>
							<tr>

								<td>${templist.userType.name}</td>
								<td>${templist.name}</td>\
								<td>
								${templist.contact} </br>
								${templist.contactTwo}  </br>
								${templist.contactThree}	
								</td>
								<td>${templist.ledgerNumber}</td>
								<td>${templist.accountNumber}</td>
								<td><a href="${updatelink}/${templist.id}"
									class="btn btn-success btn-fw" style="margin-right: 5px">Edit</a><a
									href="${deletelink}/${templist.id}"
									class="btn btn-danger btn-fw">Delete</a></td>
							</tr>
						</tbody>
					</c:forEach>
				</table>

			</div>
		</div>
		<c:if test="${totalPages>0}">
		<ul
			class="pagination rounded-flat pagination-success d-flex justify-content-center">
			<c:if test="${currentPage !=1}">
				<li class="page-item"><a class="page-link"
					href="${pageContext.request.contextPath}/user/pageno=${currentPage - 1}"><i class="mdi mdi-chevron-left"></i></a></li>
			</c:if>
			<c:forEach var="i" begin="1" end="${totalPages}">
				<c:choose>
					<c:when test="${i==currentPage}">
						<li class="page-item active"><a class="page-link">${i}</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link"
							href="<c:url value="/user/pageno=${i}"/>">${i}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${currentPage!= totalPages}">
				<li class="page-item"><a class="page-link"
					href="${pageContext.request.contextPath}/user/pageno=${currentPage + 1}"><i
						class="mdi mdi-chevron-right"></i></a></li>
			</c:if>
		</ul>
	</c:if>
	</div>
	
</div>