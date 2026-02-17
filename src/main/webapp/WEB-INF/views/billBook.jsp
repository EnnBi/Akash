<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<style>
.select2-container--default .select2-selection--multiple .select2-selection__choice{
font-size: 1rem !important;
}
.select2-container--default .select2-selection--single .select2-selection__rendered{
line-height: 10px
}
</style>
<div class="col-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<div class="row">
				<div class="col-md-10">
					<h4 class="card-title">Bill Book</h4>
				</div>
				<div class="float-right">
					<button type="button" class="btn btn-primary" id="addCustomer" data-toggle="modal" data-target="#modal">Add Customer</button>
				</div>
			</div>

			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>
			<c:if test="${not empty fail}">
				<div class="alert alert-danger" role="alert">${fail}</div>
			</c:if>
			<form:form action="${pageContext.request.contextPath}/bill-book/save" class="form-sample"
				modelAttribute="billBook" method="post" id="billForm" >
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Receipt No.</label>
							<div class="col-sm-8">
								<form:input type="text" class="form-control"
									path="receiptNumber" required="required" id="receipt" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Date</label>
							<div class="col-sm-8">
								<form:input type="text" class="form-control date" path="date"
									required="required" />
									
							</div>
						</div>
					</div>

				</div>
				<div class="row">

					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Vehicle</label>
							<div class="col-sm-8">
								<form:select class="form-control" path="vehicle" id="vehicle">
									<form:option value="">Select any Vehicle</form:option>
									<form:options items="${vehicles}" itemLabel="number"
										itemValue="id" />
								</form:select>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Other Vehicle</label>
							<div class="col-sm-8">
								<form:input class="form-control" type="text" path="otherVehicle" />
							</div>
						</div>

					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Name</label>
							<div class="col-sm-8">
								<form:select class="form-control" path="customer" id="customer"
									required="required">
									<form:option value="">Select Customer</form:option>
									<c:forEach var="customer" items="${customers}">
										<form:option value="${customer.id}"
											contact="${customer.contact}" address="${customer.address}" contact2="${customer.contactTwo}" contact3="${customer.contactThree}">
											<c:out value="${customer.name} ${customer.address}" />
										</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Site</label>
							<div class="col-sm-8">
								<form:input type="text" class="form-control" path="sites" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Phone No.</label>
							<div class="col-sm-8">
								<input class="form-control" type="text" id="contact"
									readonly="true" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Address</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="address"
									readonly="true">
							</div>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Contact 2</label>
							<div class="col-sm-8">
								<input class="form-control" type="text" id="contact2"
									readonly="true" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Contact3</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="contact3"
									readonly="true">
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
							<div class="form-group row">
								<label class="col-sm-4 col-form-label">Loader Labour Group</label>
								<div class="col-sm-8">
									<form:select  class="form-control" id="labourGroup" path="labourGroup" >
										<form:option value="">Select any Labour Group</form:option>
										<form:options items="${labourGroups}" itemLabel="name" itemValue="id"/>
									</form:select>
								</div>
							</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Unloader Labour Group</label>
							<div class="col-sm-8">
								<form:select  class="form-control" id="unloaderGroup" path="unloaderLabourGroup" >
										<form:option value="">Select any Labour Group</form:option>
										<form:options items="${labourGroups}" itemLabel="name" itemValue="id"/>
									</form:select>
							</div>
						</div>
					</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-md-12">
						<div class="form-group row">
							<div class="col-sm-11">
								<p class="card-description">Sales</p>
							</div>
							<div class="float-right">
								<button type="button" class="btn btn-primary" id="add">Add</button>
							</div>
						</div>
					</div>
				</div>
				<hr>
				<div class="row sales-row">
					<div class="col-md-12">
						<div class="form-group row">
							<div class="col-sm-3">
								<form:select class="form-control product" required="required"
									path="sales[0].product" id="products">
									<form:option value="">Select Products</form:option>
									<form:options items="${products}" itemLabel="name"
										itemValue="id" />
								</form:select>
							</div>
							<div class="col-sm-2">
								<form:select class="form-control size" path="sales[0].size"
									required="required">
									<option value="">Select Size</option>
								</form:select>
							</div>
							<div class="col-sm-2">
								<form:input type="text" class="form-control quantity"
									pattern="^\d{1,6}(\.\d{1,2})?$" placeholder="Quantity"
									path="sales[0].quantity" required="required" />
							</div>
							<div class="col-sm-2">
								<form:input type="text" class="form-control unit"
									pattern="^\d{1,6}(\.\d{1,2})?$" placeholder="Unit Price"
									path="sales[0].unitPrice" required="required" />
							</div>
							<div class="col-sm-2">
								<form:input type="text" class="form-control amount"
									placeholder="Amount" path="sales[0].amount" readonly="true" />
							</div>
							<div class="col-sm-1">
								<button type="button" class="btn btn-danger delete">
									<i class="mdi mdi-delete"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group row">
									<label class="col-sm-4 col-form-label">Loaders</label>
									<div class="col-sm-8">
										<form:select class="form-control" path="loaders" id="loaders"
											multiple="multiple">
											
										</form:select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group row">
									<label class="col-sm-4 col-form-label">Unloaders</label>
									<div class="col-sm-8">
										<form:select class="form-control" path="unloaders"
											id="unloaders" multiple="multiple">
											
										</form:select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group row">
									
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group row">
									<div class="col-sm-6 col-form-label">
										<label class="float-right">Loading:</label>
									</div>
									<div class="col-sm-4">
										<form:input type="text" class="form-control" 
											path="loadingAmount" id="loadingAmount" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 ">
								<div class="form-group row">
									<div class="col-sm-6 col-form-label">
										<label class="float-right">Unloading:</label>
									</div>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="unloadingAmount" id="unloadingAmount" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 ">
								<div class="form-group row">
									<div class="col-sm-6 col-form-label">
										<label class="float-right">Carriage:</label>
									</div>
									<div class="col-sm-4">
										<form:input type="text" class="form-control" path="carraige"
											id="carraige"  />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 ">
								<div class="form-group row">
									<div class="col-sm-6 col-form-label">
										<label class="float-right">Total:</label>
									</div>
									<div class="col-sm-4">
										<form:input type="text" class="form-control" path="total"
											id="total" readonly="true" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 ">
								<div class="form-group row">
									<div class="col-sm-6 col-form-label">
										<label class="float-right">Paid:</label>
									</div>
									<div class="col-sm-4">
										<form:input type="text" class="form-control" path="paid"
											id="paid" pattern="^[0-9]\d{0,9}(\.\d{1,3})?%?$*" required="required" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 ">
								<div class="form-group row">
									<div class="col-sm-6 col-form-label">
										<label class="float-right">Discount:</label>
									</div>
									<div class="col-sm-4">
										<form:input type="text" class="form-control" path="discount"
											id="discount"  />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 ">
								<div class="form-group row">
									<div class="col-sm-6 col-form-label">
										<label class="float-right">Balance:</label>
									</div>
									<div class="col-sm-4">
										<form:input type="text" class="form-control" path="balance"
											readonly="true" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 ">
								<div class="form-group row">
									<div class="col-sm-6 col-form-label">
										<label class="float-right"> Previous Balance:</label>
									</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" 
											readonly="true" id="prevBalance" name="prevBalance"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-12 ">
								<div class="form-group row">
									<div class="col-sm-6 col-form-label">
										<label class="float-right"> Total Balance:</label>
									</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" 
											readonly="true"  id="totalBalance" name="finalBalance"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="form-group row float-right">
							<input type="submit" class="btn btn-success btn-fw"
								value="Submit" name="save" id="addBill">
						</div>
						<div class="form-group row float-right">
							<input type="submit" class="btn btn-primary btn-fw" style="margin-right: 2rem;"
								value="Submit And Print" name="print">
						</div>
					</div>
				</div>
			</form:form>

		</div>
	</div>
</div>
<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel-4"  aria-modal="true">
                    <div class="modal-dialog" role="document">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title" id="exampleModalLabel-4">Add Customer</h5>
                          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                          </button>
                        </div>
                        <form id="modalForm">
                        <div class="modal-body">
                            <div class="form-group">
                              <label for="recipient-name" class="col-form-label">Name</label>
                              <input type="text" class="form-control" id="modalName" name="name" required="required">
                            </div>
                            <div class="form-group">
                              <label for="message-text" class="col-form-label">Contact</label>
                              <input type="text" class="form-control" id="modalContact" name="contact" required="required">
                            </div>
                            <div class="form-group">
                              <label for="message-text" class="col-form-label">Address</label>
                              <input type="text" class="form-control" id="modalAddress" name="address" required="required">
                            </div>
                            <div class="form-group">
                              <label for="message-text" class="col-form-label">Ledger Number</label>
                              <input type="text" class="form-control" id="modalAddress" name="ledgerNumber" required="required">
                            </div>
                        </div>
                        <div class="modal-footer">
                          <button type="submit" class="btn btn-success" id="modalSubmit">Submit</button>
                          <button type="button" class="btn btn-light" data-dismiss="modal">Close</button>
                        </div>
                        </form>
                      </div>
                    </div>
                  </div>
<script src="${pageContext.request.contextPath}/js/jquery.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/js/select2.min.js" defer></script>
<script type="text/javascript">
	
	$(document).ready(function(){
		var idArr=[];
		$('#customer').select2();
		$('#loaders').select2();
		$('#unloaders').select2();
		
		
		$("#add").click(function(){
			$(".sales-row:last").clone().insertAfter(".sales-row:last");
			$(".sales-row:last").find(':input').val('');
			
			updateIndex();
		});

		$(document).on('click','.delete',function() {
			if ($('.sales-row').length > 1) {
				$(this).parent().parent().parent().parent().remove();
				updateIndex();
				updateTotal();
			}
		});

		$(document).on('change','.product',function() {
			var productElement = $(this);
			var sizeElmnt = productElement.parent().parent().parent().find('.size');
			var id = productElement.val();
			var url = "${pageContext.request.contextPath}/product/" + id + "/sizes";
			console.log(url);
			$.get(url, function(data) {
				console.table(data);
				sizeElmnt.find('option').not(':first').remove();
				$.each(data, function(key, value) {
					sizeElmnt.append($(
							"<option></option>").attr(
							"value", value.id).text(
							value.name));
				});
			});

		});

		$('#customer').change(function() {
			var id = $(this).val();
			var address = $('option:selected', this).attr('address');
			var contact = $('option:selected', this).attr('contact');
			var contact2 = $('option:selected', this).attr('contact2');
			var contact3 = $('option:selected', this).attr('contact3');
			$('#address').val(address);
			$('#contact').val(contact);
			$('#contact2').val(contact2);
			$('#contact3').val(contact3);
		
			var url = "${pageContext.request.contextPath}/user/" + id + "/sites";
			$.get(url, function(data) {
				console.table(data);
				$('#site').find('option').not(':first').remove();
				$.each(data, function(key, value) {
					$('#site').append(
							$("<option></option>")
									.attr("value",
											value.id)
									.text(value.name));
				});
			});
			
			var urlBalance = "${pageContext.request.contextPath}/bill-book/customer/"+id;
			$.get(urlBalance,function(data){
				$("#prevBalance").val(data);
			});
		});

		function updateIndex() {
			$('.sales-row').get().forEach(function(entry, index, array) {
				$(entry).find('input, select').each(function() {
					var name = $(this).attr('name').replace(/\[(.+?)\]/g,"[" + index + "]");
					$(this).attr('name', name)
				});
			});
		}

						$(document).on(
								'change',
								'.quantity,.unit',
								function() {
									var currentElmnt = $(this);
									var quantity, unit, amount = 1;
									if (currentElmnt.attr('class').search(
											'quantity') > -1) {
										quantity = currentElmnt.val();
										unit = currentElmnt.closest(
												'.sales-row').find('.unit')
												.val();
									} else {
										unit = currentElmnt.val();
										quantity = currentElmnt.closest(
												'.sales-row').find('.quantity')
												.val();
									}
									amount = currentElmnt.closest('.sales-row')
											.find('.amount').val();
									if (unit && quantity)
										currentElmnt.closest('.sales-row')
												.find('.amount').val(
														unit * quantity);

									updateTotal();
								});

						$('#loadingAmount,#unloadingAmount,#carraige')
								.change(
										function() {
											if ($(this).attr('id').search(
													'loadingAmount') > -1)
												if (Number($('#loadingAmount')
														.val()))
													$('#loaders').attr(
															'required',
															'required');
												else
													$('#loaders').removeAttr(
															'required');

											if ($(this).attr('id').search(
													'unloadingAmount') > -1)
												if (Number($('#unloadingAmount')
														.val()))
													$('#unloaders').attr(
															'required',
															'required');
												else
													$('#unloaders').removeAttr(
															'required');

											updateTotal();

										});

						$('#paid').change(function() {
							updatePaid();
						});
						$('#discount').change(function() {
							updatePaid();
						});

						function updateTotal() {
							var amount = 0;
							$('.sales-row').find('.amount').each(function() {
								var amt = $(this).val();
								amount = amount + Number(amt);
							});
							var loadingAmt = $('#loadingAmount').val() ? $(
									'#loadingAmount').val() : 0;
							var unloadingAmt = $('#unloadingAmount').val() ? $(
									'#unloadingAmount').val() : 0;
							var carraige = $('#carraige').val() ? $('#carraige')
									.val()
									: 0;

									
							amount += Number(loadingAmt);
							amount += Number(unloadingAmt);
							amount += Number(carraige);
							$('#total').val(amount);
							$('#balance').val(amount);
							updatePaid();
						}

						function updatePaid() { 
							var total = Number($('#total').val());
							var paid = Number($('#paid').val());
							var discount = Number($('#discount').val());
							var balance = total -discount-paid;
							$('#balance').val(balance);
							var prevbal= Number($('#prevBalance').val());
							$('#totalBalance').val(balance + prevbal);
						}

						$('#receipt').change(function() {
							var number = $(this).val();
							var url = "${pageContext.request.contextPath}/bill-book/receipt/" + number;
							$.get(url, function(data) {
								if (data) {
									alert('Receipt Number already exists');
									$('#receipt').val('');
								}
							});
						});

						
						$('#modalSubmit').click(function(e){
							console.log('submit')
							e.preventDefault();
							var values = $('#modalForm').serialize();
							var url="${pageContext.request.contextPath}/user/customer?"+values;
							$.get(url, function(data) {
								alert('Customer saved successfully')
								
								if (data) {
								
									$('#customer').append($("<option></option>")
						                    .attr("value", data.id)
						                    .attr("contact",data.contact)
						                    .attr("address",data.address)
						                    .text(data.name)); 
								}
							}).fail(function() {
							    alert('Customer Already Exists'); // or whatever
							});
						});
						
						$('#labourGroup').change(function(){
							var id = $(this).val();
							var url = "${pageContext.request.contextPath}/user/labour-group/" + id;
							$.get(url,function(data){
								$('#loaders').empty().trigger("change");
								$.each(data, function(key, value) {
									$('#loaders').append(
											$("<option></option>").attr(
												"value", value.id).text(
													value.code+" "+value.name));
									idArr.push(value.id);
								});
								var id =  $("#vehicle").val();
								var url = "${pageContext.request.contextPath}/vehicle/"+id+"/driver";

								$.get(url,function(data){
									var newOption = new Option(data.code+" "+data.name, data.id, false, false);
									$('#loaders').append(newOption).trigger('change');
								});
								$('#loaders').val(idArr).trigger('change');
							});
					});

					$('#unloaderGroup').change(function(){
							var id = $(this).val();
							var url = "${pageContext.request.contextPath}/user/labour-group/" + id;
							$.get(url,function(data){
								$('#unloaders').empty().trigger("change");
								$.each(data, function(key, value) {
									$('#unloaders').append(
											$("<option></option>").attr(
												"value", value.id).text(
													value.code+" "+value.name));
									idArr.push(value.id);
								});
								var id =  $("#vehicle").val();
								var url = "${pageContext.request.contextPath}/vehicle/"+id+"/driver";

								$.get(url,function(data){
									var newOption2 = new Option(data.code+" "+data.name, data.id, false, false);
									$('#unloaders').append(newOption2).trigger('change');
								});
								$('#unloaders').val(idArr).trigger('change');
							});
					});
						
					$('#vehicle').change(function(){
						var id = $(this).val();
						var url = "${pageContext.request.contextPath}/vehicle/"+id+"/driver";
						$.get(url,function(data){
							if(data){
								$('#loaders').empty().trigger("change");
								$('#unloaders').empty().trigger("change");
								
								var newOption = new Option(data.code+" "+data.name, data.id, false, false);
								$('#unloaders').append(newOption).trigger('change');
								var newOption2 = new Option(data.code+" "+data.name, data.id, false, false);
								$('#loaders').append(newOption2).trigger('change');
							}
						});
					});
					
					
					
					

	 
});
	
	
	
</script>
