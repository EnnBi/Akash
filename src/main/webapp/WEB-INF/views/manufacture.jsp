<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<style>
.select2-container--default .select2-selection--multiple .select2-selection__choice{
font-size: 1rem !important;
}

</style>
<div class="col-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<h4 class="card-title">Manufacturing</h4>
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>
			<c:if test="${not empty fail}">
				<div class="alert alert-danger" role="alert">${fail}</div>
			</c:if>
			<form:form action="${pageContext.request.contextPath}/manufacture/save" modelAttribute="manufacture"
				method="post">
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Product</label>
							<div class="col-sm-9">
								<form:select path="product" class=" form-control" id="product">
									<form:option value="">Select any Product</form:option>
									<form:options items="${products}" itemLabel="name"
										itemValue="id" />
								</form:select>
								<form:errors class="err" path="product" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Date</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control date" path="date" required="required"/>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Size</label>
							<div class="col-sm-9">
								<select class="form-control" id="size" name="size">
									<option value="">Select size</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Labour Group</label>
							<div class="col-sm-6">
								<form:hidden path="cpu" id="cpu"/>
								<form:select path="labourGroup" class=" form-control" id="labourGroup" required="required">
									<form:option value="">Select any Labour Group</form:option>
									<form:options items="${labourGroups}" itemLabel="name"
										itemValue="id" />
								</form:select>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group row">
							<label class="col-sm-6 col-form-label">Cement</label>
							<div class="col-sm-6">
								<form:input class="form-control" placeholder="Cement" path="cement" required="required"/>
							</div>
						</div>
					</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-md-12">
						<div class="form-group row">
							<div class="col-sm-11">
								<p class="card-description">Labour Details</p>
							</div>
							<div class="float-right">
								<button type="button" class="btn btn-primary" id="add">Add</button>
							</div>
						</div>
					</div>
				</div>
				<hr>
				<div class="labour-info">
					<div class="row">
						<div class="col-md-4">
							<div class="form-group row">
								<label class="col-sm-4 col-form-label qtyLabel" >Quantity #1</label>
								<div class="col-sm-8"> 
									<form:input class="form-control quantity" placeholder="Quantity" path="labourInfo[0].quantity" required="required"/>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="form-group row">
								<label class="col-sm-6 col-form-label">Total Amount</label>
								<div class="col-sm-6">
									<form:input class="form-control totalAmt" placeholder="Total Amount" path="labourInfo[0].totalAmount" readonly="true"/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group row">
								<label class="col-sm-7 col-form-label">Amount Per Head</label>
								<div class="col-sm-5">
									<form:input class="form-control amountPerHead" placeholder="Amount Per Head" path="labourInfo[0].amountPerHead" readonly="true"/>
								</div>
							</div>
						</div>
						<div class="col-sm-1">
							<button type="button" class="btn btn-danger delete">
								<i class="mdi mdi-delete"></i>
							</button>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group row">
								<label class="col-sm-1 col-form-label">Labours</label>
								<div class="col-sm-11">
									<form:select path="labourInfo[0].labours" class=" form-control labors"  required="required"
										multiple="multiple">
										<form:options items="${labours}" itemLabel="name"
											itemValue="id" />
									</form:select>
									<form:errors class="err" path="labourInfo[0].labours" />
								</div>
							</div>
						</div>
					</div>
					<hr style="border: 1px dotted #d0d0d0">
				</div>
				
				<div class="row">

					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Total Amount</label>
							<div class="col-sm-9">
								<form:input path="totalAmount" class="form-control" id="totalAmount" readonly="true"/>
								<form:errors class="err" path="totalAmount" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Total Quantity</label>
							<div class="col-sm-9">
								<form:input path="totalQuantity" class="form-control" id="totalQuantity" readonly="true"/>
								<form:errors class="err" path="totalQuantity" />
							</div>
						</div>
					</div>
					
				</div>
			
			<div class="form-group row float-right">
							<input type="submit" class="btn btn-success btn-fw"
								value="Submit">
						
			</div>
		</form:form>
		</div>
	</div>
	
</div> 
<select class="labSel" name="labourInfo[0].labours" multiple="multiple"  style="display:none">
</select>
<script src="${pageContext.request.contextPath}/js/jquery.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/js/select2.min.js" defer></script>
<script type="text/javascript">
$(document).ready(function(){    
	var idArr=[];
	$('.labors').select2();
	
	
	
	$("#add").click(function(){
		$(".labour-info:last").clone().insertAfter(".labour-info:last");
		$('.labors:last').parent().empty().append($('.labSel:last').clone());
		$('.labSel:first').addClass('form-control labors').removeClass('labSel');
		$('.labors:last').prop('required',true);
		$('.labors:last').select2();
		$('.labors:last').val(idArr).trigger('change');
		$(".labour-info:last").find(':input').val('');
		updateIndex()
	});
	
	$(document).on('click','.delete',function(e){
		if($('.labour-info').length>1)
			$(this).parent().parent().parent().remove();
		updateIndex();
		 updateAmountAndQuantity(); 
	})
	
	$('#labourGroup').change(function(){
		getRate();
		var id = $(this).val();
		var url = "${pageContext.request.contextPath}/user/labour-group/" + id;
		
		$.get(url,function(data){
			$('.labSel:last').find('option').remove();
			$.each(data, function(key, value) {
			$('.labSel:last').append($(
			"<option></option>").attr(
					"value", value.id).text(
							value.code+" "+value.name));
			});
			
			$('.labors').get().forEach(function(entry, index, array) {
				$(entry).find('option').remove();
				$.each(data, function(key, value) {
					$(entry).append($(
							"<option></option>").attr(
							"value", value.id).text(
							value.code+" "+value.name));
					idArr.push(value.id);
				});
				$(entry).val(idArr).trigger('change');
			});
		});
	});
	
	$('#product').change(function(){
		var id = $(this).val();
		var url = "${pageContext.request.contextPath}/product/" + id + "/sizes";
		$.get(url,function(data){
			$('#size').find('option').not(':first')
			.remove();
			$.each(data, function(key, value) {
				$('#size').append($(
						"<option></option>").attr(
						"value", value.id).text(
						value.name));
			});
		});
	})
	
	$(document).on('change','#cpu ,.quantity,.labors',function(){
		debugger;
		 updateAmountAndQuantity();
	});
	
	function   updateAmountAndQuantity(){
		var cpu = Number($('#cpu').val());
		var totalQuantity=0,totalAmount=0;
		$('.quantity').get().forEach(function(entry, index, array) {
			var labourInfoDiv = entry.closest(".labour-info")
			var quantity = Number($(entry).val());
			var totalAmt = Number(cpu*quantity);
			$(labourInfoDiv).find(".totalAmt").val(totalAmt.toFixed(2));
			var length  =  $(labourInfoDiv).find(".labors :selected").length;
			aph = length>0?Number(totalAmt/length):0;
			$(labourInfoDiv).find('.amountPerHead').val(aph.toFixed(2));
			totalAmount=totalAmount+totalAmt;
			totalQuantity=totalQuantity+quantity;
		});
		$('#totalQuantity').val(Number(totalQuantity.toFixed(2)));
		$('#totalAmount').val(Number(totalAmount.toFixed(2)));
	}
	
	function updateIndex() {
		$('.labour-info').get().forEach(function(entry, index, array) {
				$(entry).find('.qtyLabel').text('Quantity #'+(index+1))
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
	$(document).on('change','#labourGroup,#product,#size',function(){
		 getRate();
		 /* console.log('going to set selected')
		// $(".labors option").prop("selected", true);
		 console.log($('.labors:last'));
		 $('.labors:last').select2('destroy').find('option').prop('selected', 'selected').end().select2();
		 //$('.labors:last').trigger('change');  */
		 
	});
	function getRate(){
		var lg = $('#labourGroup').val();
		var product=$('#product').val();
		var size=$('#size').val();
		var url = "${pageContext.request.contextPath}/labour-cost/rate?product="+product+"&size="+size+"&labourGroup="+lg;
		$.get(url,function(data){
			$('#cpu').val(data);
		});
	}

});
</script>
		
