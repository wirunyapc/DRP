/**
 * Created by ADMIN on 12/7/2016.
 */
(function () {
  'use strict';
  angular.module('app')
    .controller('IngreManageController', IngreManageController)
    .controller('ModalAddIngreController',ModalAddIngreController)
    .controller('ModalAddIngreInstanceController',ModalAddIngreInstanceController)
    .controller('ModalUpdateIngreController',ModalUpdateIngreController)
    .controller('ModalUpdateIngreInstanceController',ModalUpdateIngreInstanceController);
  /**ngInject*/
  function IngreManageController($rootScope,$scope,$http,$log) {
    var vm = this;
    $scope.ingredients = [];
    $rootScope.getIngredientToManage = function() {
      $http
        .get('/getAllIngredient')
        .then(function (result) {
          $scope.ingredients = result.data;
        });
    };
    $rootScope.getIngredientToManage();

    $scope.setClickedRow = function(ingre){
      $rootScope.selectedRowIngredient = ingre;
    };

    $scope.delete = function(){
      $http({
        method: 'GET',
        url: '/deleteIngredient',
        params: {
          id: $rootScope.selectedRowIngredient.id
        }
      })
        .then(function (result) {
         alert("The ingredient has been deleted.");
          $rootScope.getIngredientToManage();
        });
    }

  }
  /**ngInject*/
  function ModalAddIngreController($http,$uibModal,$log,$location,$scope,$rootScope) {
    var vm = this;

    $scope.switchBool = function (value) {
      $scope[value] = !$scope[value];
    };
    // $rootScope.signUpSuccess = false;
    vm.animationsEnabled = true;
    vm.open = function (size) {


      var modalInstance = $uibModal.open({
        animation: vm.animationsEnabled,
        templateUrl: 'addIngredient.html',
        controller: 'ModalAddIngreInstanceController',
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

    $scope.roles = [
      {roleId: 1, roleName: "Administrator"},
      {roleId: 2, roleName: "Super User"}
    ];

    $scope.user = {
      userId: 1,
      username: "JimBob",
      roles: [$scope.roles[0]]
    };
  }

  /**ngInject*/
  function ModalAddIngreInstanceController($rootScope,$http,$log,$scope,$uibModalInstance,$location) {
    var vm = this;
    $scope.datasLeft = [];
    $scope.datasRight = [];
    $scope.left = {};        // ข้อมูลที่ผูกกับ form
    $scope.right = {};
    $scope.categories = [];


    $http({
      method: 'GET',
      url: '/getAllCategory'
    })
      .then(function (result) {
        $scope.categories = result.data;
        $log.debug('Categories', $scope.categories);
      });

    vm.ok = function () {

      $http({
        method: 'GET',
        url: '/createIngredient',
        params: {
          name: vm.ingredient.ingredientName,
          category: vm.ingredient.category
        }

      }).then(function (result) {
        if (result != null) {
          alert('The ingredient has been added.')
          $uibModalInstance.dismiss('ok');
        }
        $scope.datasRight = [];

        $rootScope.getIngredientToManage();
      });

    };
      vm.cancel = function () {
        $uibModalInstance.dismiss('cancel');
        $location.path('/ingredientManage')
      };
    };

    /**ngInject*/
    function ModalUpdateIngreController($http, $uibModal, $log, $location, $scope, $rootScope) {
      var vm = this;

      $scope.switchBool = function (value) {
        $scope[value] = !$scope[value];
      };
      // $rootScope.signUpSuccess = false;
      vm.animationsEnabled = true;
      vm.open = function (size) {


        var modalInstance = $uibModal.open({
          animation: vm.animationsEnabled,
          templateUrl: 'updateIngredient.html',
          controller: 'ModalUpdateIngreInstanceController',
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

    /**ngInject*/
    function ModalUpdateIngreInstanceController($rootScope, $http, $log, $scope, $uibModalInstance, $location) {
      var vm = this;
      $scope.datasLeft = [];
      $scope.datasRight = [];
      $scope.left = {};        // ข้อมูลที่ผูกกับ form
      $scope.right = {};
      $scope.selectedIngre = [];
      $scope.categories = [];
      $scope.selectedCategory = [];

      $http({
        method: 'GET',
        url: '/getAllCategory'
      })
        .then(function (result) {
          $scope.categories = result.data;
          $log.debug('Categories', $scope.categories);
        });

      $http({
        method: 'GET',
        url: '/getSelectedCategory',
        params: {
          name: $rootScope.selectedRowIngredient.ingredientName
        }
      })
        .then(function (result) {
          $scope.selectedCategory = result.data[0];
          $log.debug('Selected Categories', $scope.selectedCategory);
        });

      vm.edit = function () {
        $log.debug('error', $scope.ingredient.ingredientName);
        $log.debug('error', $scope.ingredient.category);

          $http({
            method: 'GET',
            url: '/updateIngredient',
            params: {
              id: $rootScope.selectedRowIngredient.id,
              name: $scope.ingredient.ingredientName,
              category: $scope.ingredient.category
            }

          }).then(function (result) {
            if (result != null) {
              alert('The ingredient has been updated.')
            }
            $scope.datasRight = [];
            $uibModalInstance.dismiss('ok');
            $rootScope.getIngredientToManage();
          });


      };
      vm.cancel = function () {
        $uibModalInstance.dismiss('cancel');
        $location.path('/ingredientManage')
      };
    }



})();
