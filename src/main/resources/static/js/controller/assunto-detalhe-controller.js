appAssistenteVirtual.controller("assuntoDetalheController", function ($scope,$routeParams, $http ){
	
	$scope.assunto={};
	$scope.desabilitarBotao = $routeParams.assuntoId == 0 ? true : false;
	
	$http.get("assunto/buscarporid/"+$routeParams.assuntoId).then(function (response){
		$scope.assunto= response.data;
	}, function (response){
		console.log(response);	
	});
	
	$scope.novo = function (){
		$scope.desabilitarBotao = false;
	}
	
	$scope.cancelar = function (){
		$scope.assunto={};
		$scope.desabilitarBotao = true;
		$scope.frmAssunto.$setPristine(true);
	}
	
	$scope.salvar= function (){
		if ($scope.frmAssunto.$valid){
			$http({method:'POST', url:'assunto/salvar',data:$scope.assunto})
			.then(function (response){
				$scope.cancelar();
				$scope.frmAssunto.$setPristine(true);
			}, function (response){
				console.log(response.data);			
			});
		
		}else {
			window.alert("Dados inválidos!");
		}
	}
	
} );