'use strict';

// Declare app level module which depends on views, and core components
angular.module('myApp', [
  'ngRoute',
  'ngResource',
  'game',
  'start',
  'services',
  'ngMaterial',
  'ngAnimate',
  'end'
]).
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  //$locationProvider.hashPrefix('');
  //$locationProvider.html5Mode(true);

  $routeProvider.when('/start',{
    template: '<start></start>'
  }).when('/game',{
    template: '<game></game>'
  }).when('/end',{
    template: '<end></end>'
  }).otherwise({redirectTo: '/start'});
}]);
