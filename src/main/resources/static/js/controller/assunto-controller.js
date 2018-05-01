appAssistenteVirtual.controller("assuntoController", function($scope, $http, $timeout){

	$scope.assuntos=[];
	$scope.assunto={};
	

	
	$scope.excluir=function(assunto){
		$http({method:'DELETE', url:$scope.$location.path()+'/excluir/'+assunto.id})
		.then(function (response){
			$scope.consultar($scope.currentPage);
		});	
		
	};
	
	$scope.reativar=function(assunto){
		$http({method:'DELETE', url:$scope.$location.path()+'/reativar/'+assunto.id})
		.then(function (response){
			$scope.consultar($scope.currentPage);
		});	
		
	};
	
	
	$scope.alterar= function (cli){
		$scope.assunto = angular.copy(cli);
	};
	
	$scope.cancelar=function (){
		$scope.assunto={};
	};
	

	/*BUSCA E PAGIÇÃO GRIDVIEW*/
	$scope.assunto.pesquisa = "";
	$scope.currentPage = 1;
    $scope.pageSize = 10;
    $scope.pages = [];
    
    $scope.consultar = function(index) {
    	$scope.maxPagina();
        $scope.currentPage = index;
        $http({method:'GET', url:$scope.$location.path()+'/pesquisar/'+$scope.pageSize+"&"+$scope.currentPage+"&"+$scope.assunto.pesquisa})
		.then(function (response){
			$scope.assuntos=response.data;			
		});
    }
    
    $scope.maxPagina = function(){
    	var texto = $scope.assunto.pesquisa == "" ? "nulo" : $scope.assunto.pesquisa;
    	$http({
	        method:'GET',
	        url:$scope.$location.path()+'/pesquisarquantidade/'+texto,
	        transformResponse: function(response)
	        {
	        	var maxPagina = Math.ceil(response/$scope.pageSize);  
	        	var lista = [];
	        	for(i=1; i <= maxPagina; i++)
	        		lista.push(i);
	        	$scope.pages = lista;
	            return response;                    
	        }
	        }).then(function success(modResponse) {
	    });
    }
    $scope.consultar(1);
    /*SELECIONAR QUANTIDADE DE PAGINAÇÃO*/
    $scope.qntPorPagina = function(){
    	$scope.pageSize = $scope.assunto.qntPorPagina;
    	$scope.consultar(1);
    }
    /*FIM PAGIÇÃO*/
	
});