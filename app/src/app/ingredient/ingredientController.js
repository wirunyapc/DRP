/**
 * Created by ADMIN on 11/12/2016.
 */
(function () {
  'use strict';
  angular.module('app')
    .controller('ModalIngredientController',ModalIngredientController)
    .controller('ModalIngredientInstanceController',ModalIngredientInstanceController);

  /**ngInject*/
  function ModalIngredientController($uibModal,$log,$scope,$rootScope) {
    var vm = this;
    $rootScope.ingredients=[];



    $scope.switchBool = function (value) {
      $scope[value] = !$scope[value];
    };
    // $rootScope.signUpSuccess = false;
    vm.animationsEnabled = true;
    vm.open = function (size) {


      var modalInstance = $uibModal.open({
        animation: vm.animationsEnabled,
        templateUrl: 'ingredient.html',
        controller: 'ModalIngredientInstanceController',
        controllerAs: 'vm',
        size: size
      });

      modalInstance.result.then(function (data) {

      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });


    };

    vm.toggleAnimation = function () {
      vm.animationsEnabled = !vm.animationsEnabled;
    };







  }

// Please note that $uibModalInstance represents a modal window (instance) dependency.
// It is not the same as the $uibModal service used above.
  /**ngInject*/
  function ModalIngredientInstanceController($scope,$rootScope,$http,$uibModalInstance,$log,$location) {
    var vm = this;
    $scope.selectedValues = [];
    $scope.selectedIngredient = [];
    /*vm.ok = function () {
     $log.debug('error', vm.user.username);
     $log.debug('error', vm.user.password);
     $log.debug('error', vm.user.email);
     $log.debug('error', vm.user.dob);
     $log.debug('error', vm.user.gender);
     $log.debug('error', vm.user.role);
     $log.debug('error', vm.user.weight);
     $log.debug('error', vm.user.height);
     $log.debug('error', vm.user.duration);


     if (vm.user.username != "" && vm.user.password != "" && vm.user.email != "") {
     $uibModalInstance.close({
     username: vm.user.username,
     password: vm.user.password,
     email: vm.user.email,
     dob: vm.user.dob,
     name: vm.user.name,
     lastName: vm.user.lastName,
     gender: vm.user.gender,
     roles: [{roleName: vm.role}],
     weight: vm.user.weight,
     height: vm.user.height,
     duration: vm.user.duration
     });
     }
     ;
     }*/
    /*    $http
     .get('http://localhost:8080/getIngredientsToSelect')
     .then(function (result) {
     $rootScope.ingredients = result.data;
     $log.debug('Ingredient ', result.data);
     });*/
    vm.getIngredientsToSelect = function() {
      $http({
        method: 'GET',
        url: 'http://localhost:8080/getIngredientsToSelect',
        params: {
          name: $rootScope.currentuser
        }
      }).then(function (result) {
        $scope.ingredients = result.data;
        $log.debug('Ingredient to select', $rootScope.ingredients);
      });
    };

    vm.getSelectedIngredients = function() {
      $http({
        method: 'GET',
        url: 'http://localhost:8080/getSelectedIngredients',
        params: {
          name: $rootScope.currentuser
        }
      }).then(function (result) {
        $scope.selectedIngredients = result.data;
        $log.debug('Selected ingredient from server', $scope.selectedIngredients);
      });
    };

    vm.getIngredientsToSelect();
    vm.getSelectedIngredients();

    /* Watch selectedValues model*/
    $scope.$watch('selected', function(nowSelected){
      $scope.selectedValues = [];
      $log.debug('Im now selected',nowSelected);
      if( ! nowSelected ){
        $log.debug('Im now not selected',nowSelected);
        // here we've initialized selected already
        // but sometimes that's not the case
        // then we get null or undefined
        return;
      }
      angular.forEach(nowSelected, function(val){
        $scope.selectedValues.push( val.ingredientName );
      });
    });

    /*Watch selectedIngredient model*/
    $scope.$watch('selected', function(nowSelected){
      $scope.selectedIngredient = [];
      $log.debug('Im now selected',nowSelected);
      if( ! nowSelected ){
        $log.debug('Im now not selected',nowSelected);
        // here we've initialized selected already
        // but sometimes that's not the case
        // then we get null or undefined
        return;
      }
      angular.forEach(nowSelected, function(val){
        $scope.selectedIngredient.push(val);
      });
    });


    vm.setIngredient = function(){
      $http({
        method: 'GET',
        url: 'http://localhost:8080/setPlanByIngredients',
        params: {
          name: $rootScope.currentuser
        }
      }).then(function (result) {
        $rootScope.requestPlan();
        $uibModalInstance.dismiss('ok');
        $log.debug('Set ingredient result', result);
      });
    };

    vm.deselectIngredient = function () {
      $http({
        method: 'GET',
        url: 'http://localhost:8080/deselectIngredient',
        params: {
          ingredient: $scope.selectedIngredient,
          name: $rootScope.currentuser
        }
      }).then(function (result) {
        vm.getIngredientsToSelect();
        vm.getSelectedIngredients();
        $log.debug('Set ingredient result', result);
      });
    };

    vm.selectIngredient = function(){
      $http({
        method: 'GET',
        url: 'http://localhost:8080/selectIngredient',
        params: {
          ingredient: $scope.selectedValues,
          name: $rootScope.currentuser
        }
      }).then(function (result) {
        vm.getIngredientsToSelect();
        vm.getSelectedIngredients();
        $log.debug('Set ingredient result', result);
      });
    };



    vm.cancel = function () {
      $uibModalInstance.dismiss('cancel');
      $location.path('/home')
    };
  }
})();
