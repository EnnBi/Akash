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
			<h4 class="card-title">Goods Return</h4>
			<c:choose>
				<c:when test="${edit}">
					<c:set value="${pageContext.request.contextPath}/return-goods/update" var="action" />
					<c:set var="caption" value="Update" />
				</c:when>
				<c:otherwise>
					<c:set value="${pageContext.request.contextPath}/return-goods/add" var="action" />
					<c:set var="caption" value="Save" />

				</c:otherwise>
			</c:choose>
			<form:form action="${action}" class="form-sample"
				modelAttribute="goodsReturn" method="post">
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<form:hidden path="id"/>
							<label class="col-sm-4 col-form-label">Receipt No.</label>
							<div class="col-sm-8">
								<form:input type="text" class="form-control"
									path="receiptNumber" required="required" id="receipt" readonly="true"/>
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
							<label class="col-sm-4 col-form-label">Name</label>
							<div class="col-sm-8">
								<form:select class="form-control" path="customer" id="customer"
									required="required" readonly="readonly">
									<form:option value="">Select Customer</form:option>
									<c:forEach var="customer" items="${customers}">
									 <c:if test="${goodsReturn.customer.id eq customer.id}">
									 	<form:option value="${customer.id}" selected="selected"
											contact="${customer.contact}" address="${customer.address}">
											<c:out value="${customer.name} ${customer.address}" />
										</form:option>
									 </c:if>
									 <c:if test="${goodsReturn.customer.id ne customer.id}">
										 <form:option value="${customer.id}"
												contact="${customer.contact}" address="${customer.address}" >
												<c:out value="${customer.name} ${customer.address}" />
											</form:option>
									 </c:if>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Site</label>
							<div class="col-sm-8">
								<form:input type="text" class="form-control" path="sites" readonly="true" />
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
									readonly="true" value="${goodsReturn.customer.contact}"/>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-4 col-form-label">Address</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="address" value="${goodsReturn.customer.address}"
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
				
				<hr>
				<div class="row">
					<div class="col-md-12">
						<div class="form-group row">
							<div class="col-sm-11">
								<p class="card-description">Sales</p>
							</div>
							
						</div>
					</div>
				</div>
				<hr>
				<c:forEach items="${goodsReturn.sales}" var="sale" varStatus="loop">
					<div class="row sales-row">
						<div class="col-md-12">
							<div class="form-group row">
								<div class="col-sm-3">
									<form:hidden path="sales[${loop.index}].id"/>
									<form:select class="form-control product" required="required"
										path="sales[${loop.index}].product" readonly="true" id="Product">
										<form:option value="">Select Products</form:option>
										<form:options items="${products}" itemLabel="name"
											itemValue="id" />
									</form:select>
								</div>
								<div class="col-sm-2">
									<form:select class="form-control size" path="sales[${loop.index}].size"
										required="required" readonly="true" id="Size">
										<option value="">Select Size</option>
										<form:options items="${sizes}" itemLabel="name" itemValue="id"/>
									</form:select>
								</div>
								<div class="col-sm-2">
									<form:input type="text" class="form-control quantity"
										pattern="^[0-9]\d{0,9}(\.\d{1,3})?%?$" placeholder="Quantity"
										path="sales[${loop.index}].quantity" required="required" />
								</div>
								<div class="col-sm-2">
									<form:input type="text" class="form-control unit"
										pattern="^[0-9]\d{0,9}(\.\d{1,3})?%?$" placeholder="Unit Price"
										path="sales[${loop.index}].unitPrice" required="required" />
								</div>
								<div class="col-sm-2">
									<form:input type="text" class="form-control amount"
										placeholder="Amount" path="sales[${loop.index}].amount" readonly="true" />
								</div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-danger delete">
										<i class="mdi mdi-delete"></i>
									</button>
								</div>
									
								
							</div>
						</div>
					</div>
				</c:forEach>
				
					<div class="col-md-6">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group row">
									<div class="col-sm-6 col-form-label">
										<label class="float-right">Loading:</label>
									</div>
									<div class="col-sm-4">
										<form:input type="text" class="form-control" pattern="^[0-9]\d{0,9}(\.\d{1,3})?%?$"
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
										<form:input type="text" class="form-control" pattern="^[0-9]\d{0,9}(\.\d{1,3})?%?$"
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
											id="carraige" pattern="^[0-9]\d{0,9}(\.\d{1,3})?%?$" />
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
						
					
						
						<div class="form-group row float-right">
							<input type="submit" class="btn btn-success btn-fw"
								value="${caption}" name="save">
						</div>
						
				</div>
				
			</form:form>

		</div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/jquery.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/js/select2.min.js"></script>
<script>
var idArr=[];
$('#customer').select2();
$('#loaders').select2();
$('#unloaders').select2();

	$(document)
			.ready(
					function() {
						$("#add")
								.click(
										function() {
											$(".sales-row:last").clone()
													.insertAfter(
															".sales-row:last");
											$(".sales-row:last").find(':input')
													.val('');
											$(".sales-row:last").find('input[type="hidden"]').remove();
											
											updateIndex();
										});

						$(document).on(
								'click',
								'.delete',
								function() {
									if ($('.sales-row').length > 1) {
										$(this).parent().parent().parent()
												.parent().remove();
										updateIndex();
										updateTotal();
									}

								});

						$(document).on(
								'change',
								'.product',
								function() {

									var productElement = $(this);
									var sizeElmnt = productElement.parent()
											.parent().parent().find('.size');
									var id = productElement.val();
									var url = "${pageContext.request.contextPath}/product/" + id + "/sizes";
									console.log(url);
									$.get(url, function(data) {
										console.table(data);
										sizeElmnt.find('option').not(':first')
												.remove();
										$.each(data, function(key, value) {
											sizeElmnt.append($(
													"<option></option>").attr(
													"value", value.id).text(
													value.name));
										});
									});

								});

						$('#customer').change(
								function() {

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
										$('#site').find('option').not(':first')
												.remove();
										$.each(data, function(key, value) {
											$('#site').append(
													$("<option></option>")
															.attr("value",
																	value.id)
															.text(value.name));
										});
									});
								});

						function updateIndex() {
							$('.sales-row').get().forEach(
									function(entry, index, array) {
										console.log()
										$(entry).find('input, select').each(
												function() {
													var name = $(this).attr(
															'name').replace(
															/\[(.+?)\]/g,
															"[" + index + "]");
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
							amount -= Number(loadingAmt);
							amount -= Number(unloadingAmt);
							amount -= Number(carraige);
							$('#total').val(amount);
							$('#balance').val(amount);
							updatePaid();
						}

						function updatePaid() {
							var total = Number($('#total').val());
							var paid = Number($('#paid').val());
							var discount = Number($('#discount').val());
							$('#balance').val(total -discount-paid);
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
									var newOption2 = new Option(data.code+" "+data.name, data.id, false,false);
									$('#unloaders').append(newOption2).trigger('change');
								});
								$('#unloaders').val(idArr).trigger('change');
							});
					});

				});


</script>
