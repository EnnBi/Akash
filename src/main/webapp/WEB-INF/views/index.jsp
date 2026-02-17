<div class="col-md-8">
	<div class="row">
		<div class="col-md-6 col-lg-4 grid-margin stretch-card">
			<div class="card">
				<div class="card-body">
					<div class="d-flex align-items-center justify-content-md-center">
						<div class="ml-3">
							<p class="mb-0">Today's Revenue</p>
							<h6>0000</h6>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-lg-4 grid-margin stretch-card">
			<div class="card">
				<div class="card-body">
					<div class="d-flex align-items-center justify-content-md-center">
						<div class="ml-3">
							<p class="mb-0">Today's Expenditure</p>
							<h6>0000</h6>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-6 col-lg-4 grid-margin stretch-card">
			<div class="card">
				<div class="card-body">
					<div class="d-flex align-items-center justify-content-md-center">
						<div class="ml-3">
							<p class="mb-0">Balance</p>
							<h6>0000</h6>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-12  grid-margin stretch-card">
			<div class="card">
				<div class="card-body">
					<h4 class="card-title">Bar chart</h4>
					<canvas id="bar-chart"></canvas>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="col-md-4 grid-margin stretch-card">
	<div class="card">
		<div class="card-body">
			<h4 class="card-title">To-do</h4>
			<div class="col-md-12">
			<form>
			<div class="form-group row">
			
				<input type="text" class="form-control date"
					 style="margin-bottom: 5px" id="todoDate" required="required">
				<textarea  class="form-control" id="todoText" required="required" rows="3" style="margin-bottom: 5px" placeholder="What do you need to do today?"></textarea>
				<button type="submit"
					class="add btn btn-primary font-weight-bold todo-list-add-btn " id="addTodo">Add</button>
			
			</div>
			</form>
			</div>
			<div class="list-wrapper" style="overflow-y: scroll; height:400px;">
				<ul class="d-flex flex-column-reverse todo-list todo-list-custom" id="todoList">
					<li>
						<div class="events py-4 px-3">
							<div class="wrapper d-flex mb-2">
								<i class="mdi mdi-circle-outline text-primary mr-2"></i> <span>Feb
									11 2018</span>
							</div>
							<p class="mb-0 font-weight-thin text-gray">Creating component
								page build a js based app</p>
							<p class="text-gray mb-0"></p>
						</div> <i class="remove mdi mdi-close-circle-outline"></i>
					</li>
					
				</ul>
			</div>
		</div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/jquery.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/vendors/chart.js/Chart.min.js"></script>
<script>
	$(document).ready(function() {
		getToDoList();
		var url = "${pageContext.request.contextPath}/bar-chart";
		var names = [];
		var dataSet = {};
		var labels = [];
		initChart();
		/* $.get(url,function(data){
			data.forEach(function(item,index){
				if(names.indexOf(item.name)<0)
					names.push(item.name);
				
				console.log("length  "+dataSet[item.name].length);
				dataSet[item.name].push(item.quantity);
				if(labels.indexOf(item.date)<0)
					labels.push(item.data);
			});
			
			console.log(dataSet);
		}); */

		function initChart() {
			var ctx = document.getElementById("bar-chart");
			var data = {
				datasets : [ {
					type : 'bar',
					label : 'Product 1',
					data : [ 10, 20, 30, 40 ],
					backgroundColor : '#00c7ff'

				}, {
					type : 'bar',
					label : 'Product 2',
					data : [ 20, 10, 4, 15 ],
					backgroundColor : '#ff003f'
				} ],
				labels : [ 'January', 'February', 'March', 'April' ]
			}

			var options = {
				scales : {
					y : {
						beginAtZero : true
					}
				}
			}
			var myBarChart = new Chart(ctx, {
				type : 'bar',
				data : data,
				options : options
			});
		}
		
		$('#addTodo').click(function(){
			var date=$('#todoDate').val();
			var text=$('#todoText').val();
			if(!date || !text){
				alert('Please enter data properly');
				return;
			}
			var todo={
					'date':date,
					'text':text
			};
			var url="${pageContext.request.contextPath}/todo";
			$.ajax({
				type:"POST",
				url:url,
				data:JSON.stringify(todo),
				dataType:'json',
				contentType:'application/json',
				success:function(item){
					getToDoList();
					$('#todoDate').val('');
					$('#todoText').val('');
					alert('To-Do saved successfully');
				}
			});
		});
		
		function getToDoList(){
			var url="${pageContext.request.contextPath}/todo";
			$.get(url,function(data){
				$.each(data, function(index,item){
					$('#todoList').append('<li> <div class="events py-4 px-3"> <div class="wrapper d-flex mb-2">'+
								'<i class="mdi mdi-circle-outline text-primary mr-2"></i>'+
								'<span>'+item.date+'</span>'+
							'</div>'+
							'<p class="mb-0 font-weight-thin text-gray">'+item.text+'</p>'+
							'<p class="text-gray mb-0"></p>'+
						'</div> <i class="remove mdi mdi-close-circle-outline" id='+item.id+'></i>'+
					'</li>')
					
				});
			});
		}
		
		$(document).on('click','.mdi-close-circle-outline',function(){
			var confirm=window.confirm('Are you sure,you want to delete this');
			if(confirm){
				$(this).parent().remove();
				var id=$(this).attr('id');
				var url='${pageContext.request.contextPath}/todo/delete/'+id;
				$.get(url,function(data){
					
				});
			}
			
		});
		
	});
</script>




