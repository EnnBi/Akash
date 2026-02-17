<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<style>
.select2-container .select2-selection--single{
	height: 41px;
}
.select2-container--default .select2-selection--single .select2-selection__rendered{
	margin-top: -5px;
}
</style>
<div class="col-md-12 grid-margin stretch-card">
	<c:if test="${dayBook.transactionType eq 'Expenditure'}">
		<c:set var="exp" value="active"/>
	</c:if>
	<c:if test="${dayBook.transactionType eq 'Revenue' or empty dayBook.transactionType}">
		<c:set var="rev" value="active"/>
	</c:if>
	<div class="card">
		<div class="card-body">
			<h4 class="card-title">Day Book</h4>
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>
			<c:if test="${not empty fail}">
				<div class="alert alert-danger" role="alert">${fail}</div>
			</c:if>
			<ul class="nav nav-tabs tab-solid  tab-solid-primary" role="tablist">
				<li class="nav-item" style="width: 50%"><a class="nav-link  ${rev}"
					id="rev-tab" data-toggle="tab" href="#revenue" role="tab"
					aria-controls="profile" aria-selected="true">Revenue</a></li>
				<li class="nav-item" style="width: 50%"><a
					class="nav-link ${exp}"id="exp-tab" data-toggle="tab"
					href="#expenditure" role="tab" aria-controls="home"
					aria-selected="false">Expenditure</a></li>
			</ul>
			<div class="tab-content tab-content-basic">
				<form:form action="${pageContext.request.contextPath}/day-book/save" method="post"
					modelAttribute="dayBook" id="form">
					<div class="tab-pane fade active show" role="tabpanel"
						aria-labelledby="home-tab">

						<div class="row">
							<div class="col-md-6">
								<div class="form-group row">
									<form:hidden path="id" id="idDayBook"/>
									<form:hidden path="transactionType" id="transactionType"/>
									<label class="col-sm-4 col-form-label">Person Type</label>
									<div class="col-sm-8">
										<form:select class="form-control" path="" id="type">
											<form:option value="">Select Person Type</form:option>
											<form:options items="${userTypes}" itemLabel="name"
												itemValue="name" />
										</form:select>
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
									<label class="col-sm-4 col-form-label">Transaction By</label>
									<div class="col-sm-3">
										<div class="form-radio">
											<label class="form-check-label"> <form:radiobutton 
												class="form-check-input transactionBy" path="transactionBy" required="required"
												id="membershipRadios1" value="Cash"/> Cash <i
												class="input-helper"></i></label>
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-radio">
											<label class="form-check-label"> <form:radiobutton
												class="form-check-input transactionBy" path="transactionBy" required="required"
												id="membershipRadios2" value="Cheque"/> Cheque <i
												class="input-helper"></i></label>
										</div>
									</div>
									<div class="col-sm-2">
										<div class="form-radio">
											<label class="form-check-label"> <form:radiobutton 
												class="form-check-input transactionBy" path="transactionBy" required="required"
												id="membershipRadios2" value="TT"/> TT <i
												class="input-helper"></i></label>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group row">
									<label class="col-sm-4 col-form-label">Date</label>
									<div class="col-sm-8">
										<form:input type="text" class="form-control date" required="required"
											path="date"  />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group row">
									<label class="col-sm-4 col-form-label">Cheque/Transaction
										No.</label>
									<div class="col-sm-8">
										<form:input type="text" class="form-control"
											path="transactionNumber" id="transactionNumber"/>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group row">
									<label class="col-sm-4 col-form-label">Amount</label>
									<div class="col-sm-8">
										<form:input type="text" class="form-control" path="amount"
											required="required" />
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-6">
								<div class="form-group row">
									<label class="col-sm-4 col-form-label" id="person1">Received
										By</label>
									<div class="col-sm-8">
										<form:input type="text" class="form-control"
											path="responsiblePerson" required="required" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group row">
									<label class="col-sm-4 col-form-label" id="accountNumberLbl">Received
										Into</label>
									<div class="col-sm-8">
										<form:select class="form-control" path="accountNumber" required="required"
											id="accountNumber">
											<form:option value="">Select Account Number</form:option>
											<form:options items="${accounts}" itemLabel="accountNumber" 
												itemValue="accountNumber" />
										</form:select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group row">
									<label class="col-sm-2 col-form-label">Description</label>
									<div class="col-sm-10">
										<form:textarea path="description" class="form-control"/>
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
												class="form-check-input" path="status"
												id="membershipRadios1" value="Pending" required="required"/> Pending <i
												class="input-helper"></i></label>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-radio">
											<label class="form-check-label"> <form:radiobutton 
												class="form-check-input" path="status"
												id="membershipRadios2" value="Success" required="required"/> Success <i
												class="input-helper"></i></label>
										</div>
									</div>
								</div>
							</div>
						</div>
						
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
<script src="${pageContext.request.contextPath}/js/jquery.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/js/select2.min.js" defer></script>
<script type="text/javascript">
	$(document).ready(function(){
		
		var transactionType=$('#transactionType').val();
		if(transactionType=='Revenue' || !transactionType){
			$("#accountNumberLbl").text('Received Into');
			$("#person1").text('Received By');
			$('#transactionType').val('Revenue');
		}
		else{
			$("#accountNumberLbl").text('Transferred From');
			$("#person1").text('Transferred By');
			$('#transactionType').val('Expenditure');
		}
	
	});

	$('#rev-tab').click(function() {
		console.log("I m here");
		$("#accountNumberLbl").text('Received Into');
		$("#person1").text('Received By');
		$('#transactionType').val('Revenue');
	});
	$('#exp-tab').click(function() {
		console.log("I m here abrar");
		$("#person1").text('Transferred By');
		$("#accountNumberLbl").text('Transferred From');
		$('#transactionType').val('Expenditure');
	});

	$('.transactionBy').change(function(){
		var value=$(this).val();
		console.log(value);
		if(value != 'Cash'){
			console.log(value +"is the val")
			$('#transactionNumber').attr('required','required');
		}
		else{
			$('#transactionNumber').removeAttr('required','required');
		}
			
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
			
	$('#form').submit(function(){
		var transactionType = $('#transactionType').val();
		var transactionBy = $('input[name="transactionBy"]:checked').val();
		var transactionNumber  = $('#transactionNumber').val();
		var accountNumber=$('#accountNumber').val();
		var status =$('input[name="status"]:checked').val();
		 /* if(transactionType == 'Expenditure' && transactionBy == 'Cheque' && status == 'Success'){
			 $('#accountNumber').removeAttr('required');
		 }
		 if(transactionType == 'Revenue' && transactionBy == 'Cheque' ){
			 $('#accountNumber').removeAttr('required');
			 if(accountNumber != '' && status == 'Success'){
				alert('Please change status to Pending or Choose Account Number');
				return false;
			 }
		} */ 
		
	});
	$(document).ready(function(){
		$('#users').select2();
	});
	
</script>