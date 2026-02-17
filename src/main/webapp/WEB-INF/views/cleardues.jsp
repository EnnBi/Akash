<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<head>
	<script src="${pageContext.request.contextPath}/js/jquery.slim.min.js"></script>
</head>
<body>
<div class="col-md-12 grid-margin stretch-card">

	<div class="card">
		<div class="card-body">
			<h4 class="card-title">ClearDues</h4>
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>
			<c:if test="${not empty fail}">
				<div class="alert alert-danger" role="alert">${fail}</div>
			</c:if>
			<div class="tab-content tab-content-basic">
				<form:form action="${pageContext.request.contextPath}/clearDues/save" method="post"
					modelAttribute="clearDues" id="form">
					<div class="tab-pane fade active show" role="tabpanel"
						aria-labelledby="home-tab">

						<div class="row">
							<div class="col-md-6">
								<div class="form-group row">
									<form:hidden path="id" id="idDayBook"/>
									
									<label class="col-sm-4 col-form-label">Person Type</label>
									<div class="col-sm-8">
										<select class="form-control" path="" id="type">
											<option value="">Select Person Type</option>
											<c:forEach items="${userTypes}" var="userType">
											<c:choose>
											<c:when test="${clearDues.user.userType.id == userType.id}">
											<option value="${userType.name}" selected="selected">${userType.name}</option>
											</c:when>
											<c:otherwise>
											<option value="${userType.name}">${userType.name}</option>
											</c:otherwise>
											</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group row">
									<label class="col-sm-4 col-form-label">Name</label>
									<div class="col-sm-8">
										<form:select class="form-control" path="user" id="users" required="required">
											<form:option value="">Select Person</form:option>
											<form:options items="${customers}" itemLabel="name"
												itemValue="id" />
										</form:select>
									</div>
								</div>
							</div>

						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group row">
									<label class="col-sm-4 col-form-label">Clear Amount
										</label>
									<div class="col-sm-8">
										<form:input type="text" class="form-control"
											path="amount" id="transactionNumber" required="required"/>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group row">
									<label class="col-sm-4 col-form-label">Owner</label>
									<div class="col-sm-8">
										<form:select class="form-control" path="ownerName" id="users" required="required">
											<form:option value="">Select Owner</form:option>
											<form:options items="${owner}" itemLabel="name"
												itemValue="id" />
										</form:select>
									</div>
								</div>
							</div>
						</div>
						
						
						<div class="row">
							<div class="col-md-6">
								<div class="form-group row">
									<label class="col-sm-4 col-form-label">Current Balance
										</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" id="prevBalance" disabled="disabled" value="${balance}" />
									</div>
								</div>
							</div>
							</div>
					</div>
					<div>
					
						<div class="row">
								<div class="col-md-12">
								<div class="form-group row float-right" style="margin-bottom: 0rem;">
											<input type="submit" class="btn btn-success btn-fw" value="Submit">
									</div>
								</div>
						</div>
					</div>
				</form:form>
			</div>
			
		</div>
	</div>
</div>
						
						
						<div class="col-lg-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<h4 class="card-title">ClearDues Table</h4>
			<%-- <p class="card-description">
				Add class
				<code>.table-striped</code>
			</p> --%>
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>User Name</th>
							<th>Amount</th>
							<th>Date</th>
							<th>Owner</th>
							<th>Action</th>
						</tr>
					</thead>
					<c:url var='updatelink' value="/clearDues/edit" />
					<c:url var="deletelink" value="/clearDues/delete" />
					<tbody>
						<c:forEach items="${list}" var="templist">
							<tr>

								<td>${templist.user.name}</td>
								<td>${templist.amount}</td>
								<td>${templist.date}</td>
								<td>${templist.ownerName.name}</td>
								<td><a href="${updatelink}/${templist.id}"
									class="btn btn-success btn-fw" style="margin-right: 5px">Edit</a><a
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
						href="${pageContext.request.contextPath}/clearDues/pageno=${currentPage - 1}"><i
							class="mdi mdi-chevron-left"></i></a></li>
				</c:if>
				<c:forEach var="i" begin="1" end="${totalPages}">
					<c:choose>
						<c:when test="${i==currentPage}">
							<li class="page-item active"><a class="page-link">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link"
								href="<c:url value="/clearDues/pageno=${i}"/>">${i}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${currentPage!= totalPages}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath}/clearDues/pageno=${currentPage + 1}"><i
							class="mdi mdi-chevron-right"></i></a></li>
				</c:if>
			</ul>
		</c:if>
	</div>

</div>
							
						

	</body>					
						

<script type="text/javascript">
	$(document).ready(function(){
		
		
	
	$('#type').change(function() {
				var name = $(this).val();
				var url = "${pageContext.request.contextPath}/user-type/" + name + "/users";
				$.get(url, function(data) {
					$('#users').find('option').not(':first').remove();
					$.each(data, function(key, value) {
						console.log(value.name);
						$('#users').append(                                                                           	
								$("<option></option>").attr("value", value.id)
										.text(value.name));
					});
				});
			})
	});
	
			
	 $('#users').change(function() {
	    var id = $(this).val();
		console.log(id);
		var urlBalance = "${pageContext.request.contextPath}/bill-book/customer/"+id;
		$.get(urlBalance,function(data){
			$("#prevBalance").val(data);
		});
	}); 
	
	
	
</script>