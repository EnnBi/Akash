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
<script src="${pageContext.request.contextPath}/js/jquery.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/js/select2.min.js" defer></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#sizee').select2();
});
</script>

<div class="col-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<h4 class="card-title">Product</h4>
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>
			<c:if test="${not empty fail}">
				<div class="alert alert-danger" role="alert">${fail}</div>
			</c:if>

			<c:choose>
				<c:when test="${edit}">
					<c:set value="${pageContext.request.contextPath}/product/update" var="action" />
					<c:set var="caption" value="Update" />
				</c:when>
				<c:otherwise>
					<c:set value="${pageContext.request.contextPath}/product/save" var="action" />
					<c:set var="caption" value="Save" />

				</c:otherwise>
			</c:choose>

			<form:form action="${action}" modelAttribute="product" method="post">
				<form:hidden path="id" />
				<div class="row">
					<div class="col-md-12">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Name</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control" path="name"
									placeholder="Enter Product Name" />
								<form:errors path="name" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Sizes</label>
							<div class="col-sm-9">
								<form:select path="sizes" class=" form-control" id="sizee">
									<form:option value="">Select Sizes</form:option>
									<form:options items="${sizeList}" itemLabel="name"
										itemValue="id" />
								</form:select>
								<form:errors class="err" path="sizes" />
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
			<h4 class="card-title">Product Table</h4>
			<%-- <p class="card-description">
				Add class
				<code>.table-striped</code>
			</p> --%>
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Product Name</th>
							<th>Product Size</th>
							<th>Action</th>
						</tr>
					</thead>
					<c:url var='updatelink' value="/product/edit" />
					<c:url var="deletelink" value="/product/delete" />
					<tbody>
						<c:forEach items="${list}" var="templist">
							<tr>
								<td>${templist.name}</td>
								<td><c:forEach items="${templist.sizes}" var="size"> ${size.name}  <br>  </c:forEach></td>
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
						href="${pageContext.request.contextPath}/product/pageno=${currentPage - 1}"><i
							class="mdi mdi-chevron-left"></i></a></li>
				</c:if>
				<c:forEach var="i" begin="1" end="${totalPages}">
					<c:choose>
						<c:when test="${i==currentPage}">
							<li class="page-item active"><a class="page-link">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link"
								href="<c:url value="/product/pageno=${i}"/>">${i}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${currentPage!= totalPages}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath}/product/pageno=${currentPage + 1}"><i
							class="mdi mdi-chevron-right"></i></a></li>
				</c:if>
			</ul>
		</c:if>
	</div>

</div>

