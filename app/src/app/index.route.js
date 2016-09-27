(function() {
  'use strict';

  angular
    .module('app')
    .config(routeConfig);

  function routeConfig($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'app/components/welcome/welcome.html'

      })
      .when('/home', {
        templateUrl: 'app/home/home.html',
        controller: 'mainDRPController',
        controllerAs: 'vm'
      })
          .when('/myaccount',{
      templateUrl: 'app/User/myAccount.html',
      controller: 'MyAccountController',
      controllerAs: 'vm'
      })
      .when('/manage',{
        templateUrl: 'app/manage/mainManage.html',
        controller: 'ModalLoginController',
        controllerAs: 'vm'
      })
      .otherwise({
        redirectTo: '/'
      });
  }

})();
