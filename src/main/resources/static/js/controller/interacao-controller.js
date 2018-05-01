appAssistenteVirtual.controller("interacaoController", function($scope, $http){
	
	$scope.interacoes=[];
	$scope.interacao={};
	
	$scope.assuntos=[];

	
	carregar= function (){
		$http({method:'GET', url:'http://localhost:8080/interacaoapi'})
		.then(function (response){
			$scope.interacoes=response.data;
			
		} , function (response){
			console.log(response.data);
			console.log(response.status);
			
		});
		
		$http({method:'GET', url:'http://localhost:8080/assunto/buscartodos'})
		.then(function (response){
			$scope.assuntos=response.data;
			
		} , function (response){
			console.log(response.data);
			console.log(response.status);
			
		});
	};
	
	$scope.filtrarInteracoes = function(){
		console.log("carregar filtro");
	}
	
	$scope.salvar= function (){
		
		//if ($scope.frmCedula.$valid){
			$http({method:'POST', url:'http://localhost:8080/interacaoapi',data:$scope.interacao})
			.then(function (response){
				//$scope.interacoes.push(response.data) ;
				carregar();
				$scope.interacao.texto = "";
				//$scope.frmCedula.$setPristine(true);
				
				
			} , function (response){
				console.log(response.data);
				console.log(response.status);
				
			});
		
		//}else {
		//	window.alert("Dados inválidos!");
		//}
	}
	
	$scope.excluir=function(interacao){
		$http({method:'DELETE', url:'http://localhost:8080/interacaoapi/'+interacao.id})
		.then(function (response){
			
			pos = $scope.interacoes.indexOf(interacao);
			$scope.interacoes.splice(pos , 1);
			
		} , function (response){
			console.log(response.data);
			console.log(response.status);
			
		});	
		
	};
	
	
	$scope.alterar= function (cli){
		$scope.interacao = angular.copy(cli);
	};
	
	
	$scope.cancelar=function (){
		$scope.interacao={};
	};
	
	carregar();	
	

	
});