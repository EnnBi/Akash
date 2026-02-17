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
			<form:form modelAttribute="balanceSheet" action="${pageContext.request.contextPath}/balanceSheet" method="post" >
			
				
				<div class="row">
				<div class="col-md-12">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Person Type</label>
							
							
								<form:select path="userType" class="form-control"  required="required">
									<form:option value="">Select Any Person Type</form:option>
									<form:options items="${userTypes}" itemLabel="name"
										itemValue="name" />
								</form:select>
						</div>
						
						<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Start Date</label>
							<div class="col-sm-8">
								<form:input type="text" class="form-control date" path="startDate"
									required="required" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">End Date</label>
							<div class="col-sm-8">
								<form:input type="text" class="form-control date" path="endDate"
									required="required" />
							</div>
						</div>
					</div>
				</div>
				<div class="form-group row float-right">
					<button class="btn btn-success btn-fw" type="submit" id="Search" name="view" style="margin-right: 2rem;">Submit</button>
				</div>
				<div class="form-group row float-right">
					<button class="btn btn-success btn-fw" type="submit" id="Search" name="excel" style="margin-right: 2rem;">Exxport to Excel</button>
				</div>
				</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
<div class="col-lg-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<div class="row">
					
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Total Credit:</label>
							<label class="col-sm-8 col-form-label">${totalCredit}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Total Debit:</label>
							<label class="col-sm-8 col-form-label">${totalDebit}</label>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Total Balance:</label>
							<label class="col-sm-8 col-form-label">${totalBalance}</label>
						</div>
					</div>
					
				</div>
				<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Name</th>
							<th>Contact No.</th>
							<th>Address</th>
							<th>Credit</th>
							<th>Debit</th>
							<th>Balance</th>
							

						</tr>
					</thead>
					<tbody>
						<c:forEach items="${balanceSheets}" var="s">
							<tr>
								<td>${s.user.name}</td>
								<td>${s.user.contact}</td>
								<td>${s.user.address}</td>
								<td>${s.credit}</td>
								<td>${s.debit}</td>
								<td>${s.balance}</td>
								
							</tr>

						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	<%-- 	<c:if test="${totalPages>0}">
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
 --%>
	</div>
</div>
				
<%-- <fmt:parseDate value="${date}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
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
				<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Balance upto ${prevDate}:</label>
							<label class="col-sm-6 col-form-label">${previousBalance}</label>
						</div>
					</div>
					
					<div class="col-md-2">
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Date:</label>
							<label class="col-sm-8 col-form-label">${newParsedDate}</label>
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
				<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Balance upto ${prevDate}:</label>
							<label class="col-sm-6 col-form-label">${previousBalance}</label>
						</div>
					</div>
					
					<div class="col-md-2">
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Date:</label>
							<label class="col-sm-8 col-form-label">${newParsedDate}</label>
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
				<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Balance upto ${prevDate}:</label>
							<label class="col-sm-6 col-form-label">${previousBalance}</label>
						</div>
					</div>
					
					<div class="col-md-2">
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Date:</label>
							<label class="col-sm-8 col-form-label">${newParsedDate}</label>
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
				<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Balance upto ${prevDate}:</label>
							<label class="col-sm-6 col-form-label">${previousBalance}</label>
						</div>
					</div>
					
					<div class="col-md-2">
					</div>
					<div class="col-md-4">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Date:</label>
							<label class="col-sm-8 col-form-label">${newParsedDate}</label>
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
				<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Total Balance:</label>
							<label class="col-sm-6 col-form-label">${previousBalance}</label>
						</div>
					</div>
					
					
				</div>
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Name</th>
							<th>Address</th>
							<th>Contact</th>
							<th>Balance</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${statements}" var="s">
							<tr>
								<td>${s.}</td>
								<td>${s.receiptNumber}</td>
								<td>${s.vehicleNo}</td>
								<td>${s.address}</td>
								
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
<!-- <script>
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
		

</script> --> --%>