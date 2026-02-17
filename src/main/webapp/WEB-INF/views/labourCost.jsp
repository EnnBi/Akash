<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<h4 class="card-title">Labour Rate</h4>
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>
			<c:if test="${not empty fail}">
				<div class="alert alert-danger" role="alert">${fail}</div>
			</c:if>

			<c:choose>
				<c:when test="${edit}">
					<c:set value="${pageContext.request.contextPath}/labour-cost/update" var="action" />
					<c:set var="caption" value="Update" />
				</c:when>
				<c:otherwise>
					<c:set value="${pageContext.request.contextPath}/labour-cost/save" var="action" />
					<c:set var="caption" value="Save" />

				</c:otherwise>
			</c:choose>

			<form:form action="${action}" modelAttribute="labourCost" method="post">
				<form:hidden path="id" />
				
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<form:hidden path="id"/>
							<label class="col-sm-3 col-form-label">Product</label>
							<div class="col-sm-9">
								<form:select path="product" class=" form-control" id="product" required="required">
									<form:option value="">Select any Product</form:option>
									<form:options items="${products}" itemLabel="name"
										itemValue="id" />
								</form:select>
								
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Size</label>
							<div class="col-sm-9">
								<form:select path="size" class=" form-control" id="size" required="required">
									<form:option value="">Select any Size</form:option>
									<form:options items="${sizes}" itemLabel="name"
										itemValue="id" />
								</form:select>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Labour Group</label>
							<div class="col-sm-9">
								<form:select path="labourGroup" class=" form-control" id="lg" required="required">
									<form:option value="">Select any Labour Group</form:option>
									<form:options items="${labourGroups}" itemLabel="name"
										itemValue="id" />
								</form:select>
								
							</div>
						</div>
						</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Rate</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control" path="rate" required="required"/>
								
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Loading Rate</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control" path="loadingRate" required="required"/>
								
							</div>
						</div>
						</div>
					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">UnLoading Rate</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control" path="unloadingRate" required="required"/>
								
							</div>
						</div>
					</div>
				</div>
				<div class="form-group row float-right">
					<input type="submit" class="btn btn-success btn-fw"
						 value="${caption}">
				</div>
			</form:form>
		</div>

	</div>
</div>

<div class="col-lg-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<h4 class="card-title">Labour Rate Table</h4>
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Product</th>
							<th>Size</th>
							<th>Group</th>
							<th>Rate</th>
							<th>LoadingRate</th>
							<th>unLoadingRate</th>
							<th>Action</th>
						</tr>
					</thead>
					<c:url var='updatelink' value="/labour-cost/edit" />

					<c:url var="deletelink" value="/labour-cost/delete" />
					<tbody>
						<c:forEach items="${list}" var="lc">
							<tr>

								<td>${lc.product.name}</td>
								<td>${lc.size.name}</td>
								<td>${lc.labourGroup.name}</td>
								<td>${lc.rate}</td>
								<td>${lc.loadingRate}</td>
								<td>${lc.unloadingRate}</td>
								
								<td><a href="${updatelink}/${lc.id}"
									class="btn btn-success btn-fw" style="margin-right: 5px">Edit</a><a
									href="${deletelink}/${lc.id}"
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
						href="${pageContext.request.contextPath}/labour-cost/pageno=${currentPage - 1}"><i
							class="mdi mdi-chevron-left"></i></a></li>
				</c:if>
				<c:forEach var="i" begin="1" end="${totalPages}">
					<c:choose>
						<c:when test="${i==currentPage}">
							<li class="page-item active"><a class="page-link">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link"
								href="<c:url value="/labour-cost/pageno=${i}"/>">${i}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${currentPage!= totalPages}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath}/labour-cost/pageno=${currentPage + 1}"><i
							class="mdi mdi-chevron-right"></i></a></li>
				</c:if>
			</ul>
		</c:if>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/jquery.slim.min.js"></script>
<script>
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
</script>