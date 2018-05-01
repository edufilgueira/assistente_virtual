appAssistenteVirtual.controller("assistenteController", function($scope, $http){
	
	$scope.interacao = {}
	$scope.interacao.texto="";
	$scope.interacao.resultado="";

	

	$scope.enviar= function (){

		$http({
	        method : 'GET',
	        url : 'http://localhost:8080/robo/'+$scope.interacao.texto,
	        transformResponse: function(response)
	                           {
	                                    //alert("Success() starts here");
	        							$scope.interacao.resultado = response;          
	                                    return response;                    
	                           }
	     }).then(function success(modResponse) {
	        //alert(JSON.stringify(modResponse));
	        //alert(JSON.stringify(modResponse.data));
	        //$scope.playlists.splice(index, 1);
	    });
		
	}
	
	

	
});