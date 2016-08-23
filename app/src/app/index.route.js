(function() {
  'use strict';

  angular
    .module('app')
    .config(routeConfig);

  function routeConfig($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'app/main/main.html',
        controller: 'MainController',
        controllerAs: 'vm'
      })
      .when('/myaccount',{
      templateUrl: 'app/User/myAccount.html',
      controller: 'MyAccountController',
      controllerAs: 'vm'
      })
      .when('/home', {
        templateUrl: 'app/home/home.html',
        controller: 'mainDRPController',
        controllerAs: 'vm'
      })
      .otherwise({
        redirectTo: '/'
      });
  }

})();
