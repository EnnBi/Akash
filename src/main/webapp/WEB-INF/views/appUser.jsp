<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<h4 class="card-title">Person</h4>
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>
			<c:if test="${not empty fail}">
				<div class="alert alert-danger" role="alert">${fail}</div>
			</c:if>

			<c:choose>
				<c:when test="${edit}">
					<c:set value="${pageContext.request.contextPath}/user/update" var="action" />
					<c:set var="caption" value="Update" />
				</c:when>
				<c:otherwise>
					<c:set value="${pageContext.request.contextPath}/user/save" var="action" />
					<c:set var="caption" value="Save" />

				</c:otherwise>
			</c:choose>

			<form:form action="${action}" modelAttribute="user" method="post">
				<form:hidden path="id" />

				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">User Type</label>
							<div class="col-sm-9">
							<form:select path="userType" class=" form-control">
								<form:option value="">Select User Type</form:option>
								<form:options items="${UserList}" itemLabel="name"
									itemValue="id" />
							</form:select>
							<form:errors class="err" path="userType" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Contact</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control" path="contact" placeholder="Enter Contact "/>
								<form:errors path="contact" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Contact 2</label>
							<div class="col-sm-9">
							<form:input type="text" class="form-control" path="contactTwo" placeholder="Enter Contact Three "/>
							
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Contact 3</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control" path="contactThree" placeholder="Enter Contact Two"/>
								
							</div>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Name</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control" path="name" />
								<form:errors path="name" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Address</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control" path="address" />
								<form:errors path="address" />
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
								<form:errors path="ledgerNumber" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Acc. Number</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control"
									path="accountNumber" />
								<form:errors path="accountNumber" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Labour Group</label>
							<div class="col-sm-9">
								<form:select path="labourGroup" class=" form-control">
								<form:option value="">Select Labour Group</form:option>
								<form:options items="${labourGroups}" itemLabel="name"
									itemValue="id" />
							</form:select>
								<form:errors path="labourGroup" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Code</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control"
									path="code" />
								<form:errors path="code" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
							<div class="col-md-12">
								<div class="form-group row">
									<label class="col-sm-4 col-form-label">Status</label>
									<div class="col-sm-4">
										<div class="form-radio">
											<label class="form-check-label"> <form:radiobutton 
												class="form-check-input transactionBy" path="active" required="required"
												id="membershipRadios1" value="true"/> Active <i
												class="input-helper"></i></label>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-radio">
											<label class="form-check-label"> <form:radiobutton
												class="form-check-input transactionBy" path="active" required="required"
												id="membershipRadios2" value="false"/> Inactive <i
												class="input-helper"></i></label>
										</div>
									</div>
								</div>
							</div>
						</div>
				<%-- <div class="row">
					<div class="col-md-12">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Sites</label>
							<div class="col-sm-9">
							<form:select path="sites" class="form-control" multiple="multiple">
								<form:option value="">Select Sites</form:option>
								<form:options items="${siteList}" itemLabel="name"
									itemValue="id" />
							</form:select>
							</div>
						</div>
					</div>
				</div> --%>
				<div class="form-group row float-right">
					<input type="submit" class="btn btn-success btn-fw"
						 value="${caption}">
				</div>
			</form:form>
		</div>

	</div>
</div>

