



<nav class="sidebar sidebar-offcanvas" id="sidebar">
	<ul class="nav">
		<li class="nav-item nav-profile">
			<div class="nav-link">
				<div class="profile-image">
					<img src="${pageContext.request.contextPath}/images/faces/profile.png" alt="image" /> 
					<!--change class online to offline or busy as needed-->
				</div>
				<div class="profile-name">
					<p class="name">Owner</p>
					<p class="designation">Super Admin</p>
				</div>
			</div>
		</li>
		<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/dashboard"> <i
				class="icon-rocket menu-icon"></i> <span class="menu-title">Dashboard</span>
		</a></li>
		<li class="nav-item"><a class="nav-link" data-toggle="collapse"
			href="#bill-book" aria-expanded="false" aria-controls="page-layouts">
				<i class="icon-check menu-icon"></i> <span class="menu-title">BillBook</span>
				<span class="badge badge-danger">2</span>
		</a>
			<div class="collapse" id="bill-book">
				<ul class="nav flex-column sub-menu">
					<li class="nav-item d-none d-lg-block"><a class="nav-link"
						href="${pageContext.request.contextPath}/bill-book">Create</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/bill-book/search">Search</a></li>
					<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/return-goods/search">Add Goods Return</a></li>
					<li class="nav-item"><a class="nav-link"
					href="${pageContext.request.contextPath}/return-goods/edit">Edit Goods Return</a></li>
				</ul>
			</div></li>
		<li class="nav-item d-none d-lg-block"><a class="nav-link"
			data-toggle="collapse" href="#manufacturing" aria-expanded="false"
			aria-controls="sidebar-layouts"> <i class="icon-layers menu-icon"></i>
				<span class="menu-title">Manufacturing</span> <span
				class="badge badge-warning">2</span>
		</a>
			<div class="collapse" id="manufacturing">
				<ul class="nav flex-column sub-menu">
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/manufacture">Create</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/manufacture/search">Search</a></li>
				</ul>
			</div></li>
		<li class="nav-item"><a class="nav-link" data-toggle="collapse"
			href="#day-book" aria-expanded="false" aria-controls="ui-basic">
				<i class="icon-target menu-icon"></i> <span class="menu-title">DayBook</span>
				<span class="badge badge-success">2</span>
		</a>
			<div class="collapse" id="day-book">
				<ul class="nav flex-column sub-menu">
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/day-book">Create</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/day-book/search">Search</a></li>
				</ul>
			</div></li>
		<li class="nav-item"><a class="nav-link" data-toggle="collapse"
			href="#person" aria-expanded="false" aria-controls="ui-advanced">
				<i class="icon-cup menu-icon"></i> <span class="menu-title">Person
					</span> <span class="badge badge-primary">2</span>
		</a>
			<div class="collapse" id="person">
				<ul class="nav flex-column sub-menu">
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/user">Create</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/user/search">Search</a></li>
				</ul>
			</div></li>
		<li class="nav-item"><a class="nav-link" data-toggle="collapse"
			href="#raw-material" aria-expanded="false"
			aria-controls="form-elements"> <i class="icon-flag menu-icon"></i>
				<span class="menu-title">Raw Material</span> <span
				class="badge badge-danger">2</span>
		</a>
			<div class="collapse" id="raw-material">
				<ul class="nav flex-column sub-menu">
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/raw-material">Create</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/raw-material/search">Search</a></li>
				</ul>
			</div></li>
			<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/inventory"> <i
				class="icon-rocket menu-icon"></i> <span class="menu-title">Inventory</span>
		</a></li>

		<li class="nav-item"><a class="nav-link" data-toggle="collapse"
			href="#statement" aria-expanded="false"
			aria-controls="form-elements"> <i class="icon-rocket menu-icon"></i>
				<span class="menu-title">Statement</span> <span
				class="badge badge-danger">3</span>
		</a>
			<div class="collapse" id="statement">
				<ul class="nav flex-column sub-menu">
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/statement">General</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/balanceSheet">Balance Sheet</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/clearDues">Clear Dues</a></li>	
				</ul>
			</div></li>
		
			
			<li class="nav-item"><a class="nav-link" data-toggle="collapse"
			href="#settings" aria-expanded="false"
			aria-controls="form-elements"> <i class="icon-flag menu-icon"></i>
				<span class="menu-title">Settings</span> <span
				class="badge badge-danger">7</span>
		</a>
			<div class="collapse" id="settings">
				<ul class="nav flex-column sub-menu">
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/material-type">Raw Material Type</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/product">Product</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/size">Size</a></li>	
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/vehicle">Vehicle</a></li>	
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/user-type">Person Type</a></li>	
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/labour-group">Labour Group</a></li>	
						<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/labour-cost">Labour Rate</a></li>	
				</ul>
			</div></li>
		
		 
	
		
		<!-- <li class="nav-item"><a class="nav-link"
			href="/site"> <i class="icon-clock menu-icon"></i>
				<span class="menu-title">Site</span>
		</a></li> 
		<li class="nav-item"><a class="nav-link"
			href="/size"> <i class="icon-picture menu-icon"></i>
				<span class="menu-title">Size</span>
		</a></li>
		<li class="nav-item"><a class="nav-link"
			href="/vehicle"> <i class=" menu-icon"></i>
				<span class="menu-title">Vehicle</span>
		</a></li>
		<li class="nav-item"><a class="nav-link"
			href="/user-type"> <i class="icon-cursor menu-icon"></i>
				<span class="menu-title">Person Type</span>
		</a></li>-->
	</ul>
</nav>