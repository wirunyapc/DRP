/**
 * Created by ADMIN on 11/12/2016.
 */
(function () {
  'use strict';
  angular.module('app')
    .controller('ModalSelectIngredientController',ModalSelectIngredientController)
    .controller('ModalSelectIngredientInstanceController',ModalSelectIngredientInstanceController);

  /**ngInject*/
  function ModalSelectIngredientController($uibModal,$log,$scope,$rootScope) {
    var vm = this;

    $scope.switchBool = function (value) {
      $scope[value] = !$scope[value];
    };
    // $rootScope.signUpSuccess = false;
    vm.animationsEnabled = true;
    vm.open = function (size) {


      var modalInstance = $uibModal.open({
        animation: vm.animationsEnabled,
        templateUrl: 'ingredient.html',
        controller: 'ModalSelectIngredientInstanceController',
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
  function ModalSelectIngredientInstanceController($scope,$rootScope,$http,$uibModalInstance,$log,$location) {
    var vm = this;
    $scope.datasLeft = [];
    $scope.datasRight =[];
    $scope.left ={};        // ข้อมูลที่ผูกกับ form
    $scope.right ={};
    $scope.selectedIngre = [];
    $scope.selectedIngredients = [];
    $scope.ingredients = [];


    vm.getIngredientsToSelect = function() {
      $http({
        method: 'GET',
        url: '/getIngredientsToSelect',
        params: {
          name: $rootScope.currentuser
        }
      }).then(function (result) {
        $scope.datasLeft = result.data;
        $log.debug('Ingredient to select', $scope.ingredients);
      });
    };

    vm.getIngredientsToSelect();

    vm.getSelectedIngredients = function() {
      $http({
        method: 'GET',
        url: '/getSelectedIngredients',
        params: {
          name: $rootScope.currentuser
        }
      }).then(function (result) {
        $scope.datasRight = result.data;
        $log.debug('Selected ingredient from server', $scope.selectedIngredients);
      });
    };

    vm.getSelectedIngredients();

    $scope.SelectData = function (left) {
      $log.debug('error left', left);
      $scope.datasRight.push(left[0]);
      $log.debug('data right', $scope.datasRight);
      //$scope.datasTop.splice($scope.datasTop.indexOf(top[0]),1);
      $scope.left = {};
    };
    $scope.DeselectData = function (right) {
      $log.debug('error right', right);
      //$scope.datasTop.push(bottom);
      $scope.datasRight.splice($scope.datasRight.indexOf(right[0]),1);
      $scope.right = {};
    };

    vm.ok = function(){
      $log.debug('error', $scope.datasRight);
      if($scope.datasRight.length > 0){
        $log.debug('Set not null');
        $http({
          method: 'GET',
          url: '/updatePlanWithIngredient',
          params: {
            name: $rootScope.currentuser,
            ingredients: $scope.datasRight
          }

        }).then(function (result) {
          if(result!=null) {
            $rootScope.requestPlan();
            $scope.datasRight = [];
            $uibModalInstance.dismiss('ok');
            alert('The diet plan has been updated.');
          }
        });

      }else{
        $log.debug('Set null');
        $http({
          method: 'GET',
          url: '/updatePlanWithOutIngredient',
          params: {
            name: $rootScope.currentuser
          }

        }).then(function (result) {
          if(result!=null) {
            $rootScope.requestPlan();
            $scope.datasRight = [];
            $uibModalInstance.dismiss('ok');
            alert('The diet plan has been updated.');
          }

        });
      }

    };



    vm.cancel = function () {
      $uibModalInstance.dismiss('cancel');
      $location.path('/home')
    };
  }
})();
