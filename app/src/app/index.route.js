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
      .when('/signUp', {
        templateUrl: 'app/User/sign_up.html',
        controller: 'ModalRegisterController',
        controllerAs: 'vm'
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
        controller: 'MainManageController',
        controllerAs: 'vm'
      })
      .when('/diseaseManage',{
        templateUrl: 'app/manage/disease/diseaseManage.html',
        controller: 'DiseaseManageController',
        controllerAs: 'vm'
      })
      .when('/foodManage',{
        templateUrl: 'app/manage/food/foodManage.html',
        controller: 'FoodManageController',
        controllerAs: 'vm'
      })
      .when('/setMenuManage',{
        templateUrl: 'app/manage/setMenu/setMenuManage.html',
        controller: 'SetMenuManageController',
        controllerAs: 'vm'
      })
      .when('/ingredientManage',{
        templateUrl: 'app/manage/ingredient/ingredientManage.html',
        controller: 'IngreManageController',
        controllerAs: 'vm'
      })
      .otherwise({
        //redirectTo: '/'
      });
  }

})();
