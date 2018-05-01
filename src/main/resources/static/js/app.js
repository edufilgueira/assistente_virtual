//Criacao do modulo principal da aplicacao
var appAssistenteVirtual = angular.module("appAssistenteVirtual", [ 'ngRoute' ]);

appAssistenteVirtual.config(function($routeProvider, $locationProvider) {

	$routeProvider
	.when("/assunto", {
		templateUrl : 'view/assunto.html',
		controller : 'assuntoController'
	}).when("/assunto/:assuntoId", {
		templateUrl : 'view/assunto-detalhe.html',
		controller : 'assuntoDetalheController'
	}).when("/interacao", {
		templateUrl : 'view/interacao.html',
		controller : 'interacaoController'
	}).when("/assistente", {
		templateUrl : 'view/assistente.html',
		controller : 'assistenteController'
	}).when("/login", {
		templateUrl : 'view/login.html',
		controller : 'loginController'
	}).otherwise({
		rediretTo : '/'
	});
	
	//$locationProvider.html5Mode(true);
	
});