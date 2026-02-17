<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="col-lg-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<form:form action="${pageContext.request.contextPath}/inventory" method="post"
				modelAttribute="inventorySearch">

				<div class="row">
					<div class="col-md-12">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Product</label>
							<div class="col-sm-9">
								<form:select path="product" class="form-control" id="product" required="required">
									<form:option value="">Select Any Product</form:option>
									<form:options items="${products}" itemLabel="name"
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
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Product</th>
							<th>Size</th>
							<th>Manufactured</th>
							<th>Sold</th>
							<th>Remaining</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${counts}" var="s">
							<tr>
								<td>${s.name}</td>
								<td>${s.size}</td>
								<td>${s.manufactured}</td>
								<td>${s.sold}</td>
								<td>${s.remaining}</td>
							</tr>

						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>