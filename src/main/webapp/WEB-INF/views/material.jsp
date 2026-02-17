<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-12 grid-margin">
	<div class="card">
		<div class="card-body">
			<h4 class="card-title">Material Type</h4>
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">${success}</div>
			</c:if>
			<c:if test="${not empty fail}">
				<div class="alert alert-danger" role="alert">${fail}</div>
			</c:if>

			<c:choose>
				<c:when test="${edit}">
					<c:set value="${pageContext.request.contextPath}/material-type/update" var="action" />
					<c:set var="caption" value="Update" />
				</c:when>
				<c:otherwise>
					<c:set value="${pageContext.request.contextPath}/material-type/save" var="action" />
					<c:set var="caption" value="Save" />

				</c:otherwise>
			</c:choose>

			<form:form action="${action}" modelAttribute="material" method="post">
				<form:hidden path="id" />
				<div class="row">
					<div class="col-md-12">
						<div class="form-group row">
							<label class="col-sm-3 col-form-label">Name</label>
							<div class="col-sm-9">
								<form:input type="text" class="form-control" path="name" />
								<form:errors path="name" />
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
			<h4 class="card-title">Material Type Table</h4>
			<%-- <p class="card-description">
				Add class
				<code>.table-striped</code>
			</p> --%>
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Name</th>
							<th>Action</th>

						</tr>
					</thead>
					<c:url var='updatelink' value="${pageContext.request.contextPath}/material-type/edit" />

					<c:url var="deletelink" value="${pageContext.request.contextPath}/material-type/delete" />
					<tbody>
						<c:forEach items="${list}" var="templist">
							<tr>

								<td>${templist.name}</td>
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
				class="pagination rounded-flat pagination-success d-flex justify-content-center mt-10">
				<c:if test="${currentPage !=1}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath}/material-type/pageno=${currentPage - 1}"><i
							class="mdi mdi-chevron-left"></i></a></li>
				</c:if>
				<c:forEach var="i" begin="1" end="${totalPages}">
					<c:choose>
						<c:when test="${i==currentPage}">
							<li class="page-item active"><a class="page-link">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link"
								href="<c:url value="/material-type/pageno=${i}"/>">${i}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${currentPage!= totalPages}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath}/material-type/pageno=${currentPage + 1}"><i
							class="mdi mdi-chevron-right"></i></a></li>
				</c:if>
			</ul>
		</c:if>
	</div>
</div>