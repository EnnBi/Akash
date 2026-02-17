<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<style>
.table th, .jsgrid .jsgrid-table th,
.table td, .jsgrid .jsgrid-table td {
  border-top: 1px solid #000000;
  border-collapse:collapse;
}
.select2-container--default .select2-selection--multiple .select2-selection__choice{
	font-size: 1rem !important;
	}
	.select2-container--default .select2-selection--single .select2-selection__rendered{
	line-height: 10px
	}
</style>
<div class="col-lg-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<form:form action="${pageContext.request.contextPath}/statement" method="post"
				modelAttribute="statementSearch">

				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Person Type</label>
							<div class="col-sm-9">
								<form:select path="userType" class="form-control" id="type" required="required">
									<form:option value="">Select Any Person Type</form:option>
									<form:options items="${userTypes}" itemLabel="name"
										itemValue="name" />
								</form:select>
							</div>

						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Person</label>
							<div class="col-sm-9">
								<form:select path="user" class=" form-control" id="users" required="required">
									<form:option value="">Select Any Person</form:option>
									<form:options items="${users}" itemLabel="name"
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
					<button class="btn btn-primary btn-fw" type="submit" id="Search" name="excel">Export to Excel</button>
				</div>
				<div class="form-group row float-right">
					<button class="btn btn-success btn-fw" type="submit" id="Search" name="view" style="margin-right: 2rem;">Submit</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
<fmt:parseDate value="${date}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
<fmt:formatDate value="${parsedDate}" var="newParsedDate" type="date" pattern="dd-MM-yyyy" />
<c:if test="${driverStatement}"> 

<div class="col-lg-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<div class="row">
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Name:</label>
							<label class="col-sm-8 col-form-label">${user.name}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Address:</label>
							<label class="col-sm-8 col-form-label">${user.address}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Contact:</label>
							<label class="col-sm-8 col-form-label">${user.contact}</label>
						</div>
					</div>
					
			</div>
			<div class="row">
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-7 col-form-label">Balance upto ${prevDate}:</label>
							<label class="col-sm-5 col-form-label">${previousBalance}</label>
						</div>
					</div>
					
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Credit(b/w dates):</label>
							<label class="col-sm-6 col-form-label">${totalCredit}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Debit(b/w dates):</label>
							<label class="col-sm-6 col-form-label">${totalDebit}</label>
						</div>
					</div>
				</div>
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Date</th>
							<th>Receipt No.</th>
							<th>Name</th>
							<th>Site</th>
							<th>Carriage</th>
							<th>Loading</th>
							<th>Unloading</th>
							<th>Debit</th>
							<th>Credit</th>
							<th>Balance</th>
							<th>Reference</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach items="${statements}" var="s">
							<tr>
								<td>${s.date}</td>
								<td>${s.receiptNumber}</td>
								<td>${s.customerName}</td>
								<td>${s.site}</td>
								<td>${s.carraige}</td>
								<td>${s.loading}</td>
								<td>${s.unloading}</td>
								<td>${s.debit}</td>
								<td>${s.credit}</td>
								<td>${s.balance}</td>
								<td>${s.reference}</td>
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
</c:if>
<c:if test="${dealerStatement}"> 
<div class="col-lg-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<div class="row">
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Name:</label>
							<label class="col-sm-8 col-form-label">${user.name}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Address:</label>
							<label class="col-sm-8 col-form-label">${user.address}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Contact:</label>
							<label class="col-sm-8 col-form-label">${user.contact}</label>
						</div>
					</div>
					
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group row">
						<label class="col-sm-6 col-form-label">Balance upto ${prevDate}:</label>
						<label class="col-sm-6 col-form-label">${previousBalance}</label>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group row">
						<label class="col-sm-6 col-form-label">Credit(b/w dates):</label>
						<label class="col-sm-6 col-form-label">${totalCredit}</label>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group row">
						<label class="col-sm-6 col-form-label">Debit(b/w dates):</label>
						<label class="col-sm-6 col-form-label">${totalDebit}</label>
					</div>
				</div>
			</div>
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Date</th>
							<th>Challan No.</th>
							<th>Material</th>
							<th>Quantity</th>
							<th>Unit</th>
							<th>Rate</th>
							<th>Debit</th>
							<th>Credit</th>
							<th>Balance</th>
							<th>Reference</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach items="${statements}" var="s">
							<tr>
								<td>${s.date}</td>
								<td>${s.chalanNumber}</td>
								<td>${s.product}</td>
								<td>${s.quantity}</td>
								<td>${s.unit}</td>
								<td>${s.amount}</td>
								<td>${s.debit}</td>
								<td>${s.credit}</td>
								<td>${s.balance}</td>
								<td>${s.reference}</td>
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
</c:if>
<c:if test="${labourStatement}"> 
<div class="col-lg-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<div class="row">
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Name:</label>
							<label class="col-sm-8 col-form-label">${user.name}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Address:</label>
							<label class="col-sm-8 col-form-label">${user.address}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Contact:</label>
							<label class="col-sm-8 col-form-label">${user.contact}</label>
						</div>
					</div>
					
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Balance upto ${prevDate}:</label>
							<label class="col-sm-6 col-form-label">${previousBalance}</label>
						</div>
					</div>
					
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Credit(b/w dates):</label>
							<label class="col-sm-6 col-form-label">${totalCredit}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Debit(b/w dates):</label>
							<label class="col-sm-6 col-form-label">${totalDebit}</label>
						</div>
					</div>  
				</div>
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Date</th>
							<th>Product</th>
							<th>Quantity</th>
							<th>Rate</th>
							<th>Loading</th>
							<th>Unloading</th>
							<th>Debit</th>
							<th>Credit</th>
							<th>Balance</th>
							<th>Reference</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach items="${statements}" var="s">
							<tr>
								<td>${s.date}</td>
								<td>${s.product}</td>
								<td>${s.quantity}</td>
								<td>${s.amount}</td>
								<td>${s.loading}</td>
								<td>${s.unloading}</td>
								<td>${s.debit}</td>
								<td>${s.credit}</td>
								<td>${s.balance}</td>
								<td>${s.reference}</td>
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
</c:if>
<c:if test="${ownerStatement}"> 
<div class="col-lg-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<div class="row">
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Name:</label>
							<label class="col-sm-8 col-form-label">${user.name}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Address:</label>
							<label class="col-sm-8 col-form-label">${user.address}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Contact:</label>
							<label class="col-sm-8 col-form-label">${user.contact}</label>
						</div>
					</div>
					
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Balance upto ${prevDate}:</label>
							<label class="col-sm-6 col-form-label">${previousBalance}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Credit(b/w dates):</label>
							<label class="col-sm-6 col-form-label">${totalCredit}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Debit(b/w dates):</label>
							<label class="col-sm-6 col-form-label">${totalDebit}</label>
						</div>
					</div>
				</div>
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Date</th>
							<th>User</th>
							<th>Transaction Via</th>
							<th>Transaction No.</th>
							<th>Responsible Person</th>
							<th>Debit</th>
							<th>Credit</th>
							<th>Balance</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach items="${statements}" var="s">
							<tr>
								<td>${s.date}</td>
								<td>${s.user}</td>
								<td>${s.transactionBy}</td>
								<td>${s.transactionNumber}</td>
								<td>${s.responsiblePerson}</td>
								<td>${s.debit}</td>
								<td>${s.credit}</td>
								<td>${s.balance}</td>
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
</c:if>
<c:if test="${customerStatement}"> 
<div class="col-lg-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<div class="row">
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Name:</label>
							<label class="col-sm-8 col-form-label">${user.name}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Address:</label>
							<label class="col-sm-8 col-form-label">${user.address}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Contact:</label>
							<label class="col-sm-8 col-form-label">${user.contact}</label>
						</div>
					</div>
					
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Balance upto ${prevDate}:</label>
							<label class="col-sm-6 col-form-label">${previousBalance}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Credit(b/w dates):</label>
							<label class="col-sm-6 col-form-label">${totalCredit}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Debit(b/w dates):</label>
							<label class="col-sm-6 col-form-label">${totalDebit}</label>
						</div>
					</div>
				</div>
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Date</th>
							<th>Receipt No.</th>
							<th>Vehicle</th>
							<th>Address/Site</th>
							<th>
							<table>
								<thead>
								<tr>
								<th>Product</th>
								<th>Quantity</th>
								<th>Unit Price</th>
								<th>Amount/Item</th>
								</tr>
								</thead>
							</table>
							</th>
							<th>All Items Total</th>
							<th>Loading</th>
							<th>Unloading</th>
							<th>Carriage</th>
							<th>Discount</th>
							<th>Debit</th>
							<th>Credit</th>
							<th>Balance</th>
							<th>Reference</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach items="${statements}" var="s">
							<tr>
								<td>${s.date}</td>
								<td>${s.receiptNumber}</td>
								<td>${s.vehicleNo}</td>
								<td>${s.address}</td>
								<td>
									<table>
									<c:forEach items="${s.sales}" var="sale">
									<tr>
										<td>${sale.product}</td>
										<td>${sale.quantity}</td>
										<td>${sale.unitPrice}</td>
										<td>${sale.amount}</td>
									</tr>
								</c:forEach> 
								</table>
								</td>
								<td>${s.amount}</td>
								<td>${s.loading}</td>
								<td>${s.unloading}</td>
								<td>${s.carraige}</td>
								<td>${s.discount}</td>
								<td>${s.debit}</td>
								<td>${s.credit}</td>
								<td>${s.balance}</td>
								<td>${s.reference}</td>
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
</c:if>
<script src="${pageContext.request.contextPath}/js/jquery.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/js/select2.min.js" defer></script>
<script>
	$(document).ready(function(){
		$('#users').select2();

	});
$('#type').change(
		function() {
			var name = $(this).val();
			var url = "${pageContext.request.contextPath}/user-type/" + name + "/users";
			$.get(url, function(data) {
				$('#users').find('option').not(':first').remove();
				$.each(data, function(key, value) {
					$('#users').append(
							$("<option></option>").attr("value", value.id)
									.text(value.name));
				});
			});
		})
		

</script>