/**
 * Created by ADMIN on 12/7/2016.
 */
(function () {
  'use strict';
  angular.module('app')
    .controller('FoodManageController', FoodManageController)
    .controller('ModalAddFoodController',ModalAddFoodController)
    .controller('ModalAddFoodInstanceController',ModalAddFoodInstanceController)
    .controller('ModalUpdateFoodController',ModalUpdateFoodController)
    .controller('ModalUpdateFoodInstanceController',ModalUpdateFoodInstanceController);
  /**ngInject*/
  function FoodManageController($rootScope,$scope,$http,$log) {
    var vm = this;
    $scope.foods = [];
    $rootScope.getFoodToManage = function() {
      $http
        .get('/getFoodToManage')
        .then(function (result) {
          $scope.foods = result.data;
          $log.debug('food ', result.data);
        });
    };
    $rootScope.getFoodToManage();

    $scope.setClickedRow = function(food){
      $rootScope.selectedRowFood = food;
    };

    $scope.delete = function(){
      $http({
        method: 'GET',
        url:'/deleteFood',
        params: {
          id: $rootScope.selectedRowFood.foodId
        }
      })
        .then(function (result) {
         alert("The food has been deleted.");
          $rootScope.getFoodToManage();
        });
    }

  }
  /**ngInject*/
  function ModalAddFoodController($http,$uibModal,$log,$location,$scope,$rootScope) {
    var vm = this;

    $scope.switchBool = function (value) {
      $scope[value] = !$scope[value];
    };
    // $rootScope.signUpSuccess = false;
    vm.animationsEnabled = true;
    vm.open = function (size) {


      var modalInstance = $uibModal.open({
        animation: vm.animationsEnabled,
        templateUrl: 'addFood.html',
        controller: 'ModalAddFoodInstanceController',
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
  function ModalAddFoodInstanceController($rootScope,$http,$log,$scope,$uibModalInstance,$location) {
    var vm = this;
    $scope.datasLeft = [];
    $scope.datasRight =[];
    $scope.left ={};        // ข้อมูลที่ผูกกับ form
    $scope.right ={};
    $scope.categories = [];


    $http({
      method: 'GET',
      url: '/getAllIngredient'
    })
      .then(function (result) {
        $scope.datasLeft = result.data;
      });

    $http({
      method: 'GET',
      url: '/getAllCategory'
    })
      .then(function (result) {
        $scope.categories = result.data;
        $log.debug('Categories',$scope.categories);
      });

    $scope.SelectData = function (left) {
      $log.debug('select left', left);
      $scope.datasRight.push(left[0]);
      $log.debug('select right', $scope.datasRight);
      //$scope.datasTop.splice($scope.datasTop.indexOf(top[0]),1);
      $scope.left = {};
    };
    $scope.DeselectData = function (right) {
      $log.debug('select right', right);
      //$scope.datasTop.push(bottom);
      $scope.datasRight.splice($scope.datasRight.indexOf(right[0]),1);
      $scope.right = {};
    };

    vm.ok = function(){
  $log.debug('data right', $scope.datasRight);
      if($scope.datasRight.length > 0){
        $log.debug('Set not null');
        $http({
          method: 'GET',
          url: '/createFoodWithIngre',
          params: {
            name: vm.food.foodName,
            cal: vm.food.cal,
            fat: vm.food.fat,
            carboh: vm.food.carboh,
            prote: vm.food.prote,
            category: vm.food.category,
            amount: vm.food.amount,
            unit: vm.food.unit,
            ingredients: $scope.datasRight
          }

        }).then(function (result) {
          if(result!=null) {
            alert('The food has been added.')
            $uibModalInstance.dismiss('ok');
          }
          $scope.datasRight = [];
          $rootScope.getFoodToManage();
        });

        }else{
        $log.debug('Set  null');
        $http({
          method: 'GET',
          url: '/createFood',
          params: {
            name: vm.food.foodName,
            cal: vm.food.cal,
            fat: vm.food.fat,
            carboh: vm.food.carboh,
            prote: vm.food.prote,
            category: vm.food.category,
            amount: vm.food.amount,
            unit: vm.food.unit
          }

        }).then(function (result) {
          if(result!=null) {
            alert('The food has been added.')
            $uibModalInstance.dismiss('ok');
          }
          $scope.datasRight = [];

          $rootScope.getFoodToManage();
        });
      }

  };

    vm.cancel = function () {
      $uibModalInstance.dismiss('cancel');
      $location.path('/foodManage')
    };
  }
  /**ngInject*/
  function ModalUpdateFoodController($http,$uibModal,$log,$location,$scope,$rootScope) {
    var vm = this;

    $scope.switchBool = function (value) {
      $scope[value] = !$scope[value];
    };
    // $rootScope.signUpSuccess = false;
    vm.animationsEnabled = true;
    vm.open = function (size) {


      var modalInstance = $uibModal.open({
        animation: vm.animationsEnabled,
        templateUrl: 'updateFood.html',
        controller: 'ModalUpdateFoodInstanceController',
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
  function ModalUpdateFoodInstanceController($rootScope,$http,$log,$scope,$uibModalInstance,$location) {
    var vm = this;
    $scope.datasLeft = [];
    $scope.datasRight =[];
    $scope.left ={};        // ข้อมูลที่ผูกกับ form
    $scope.right ={};
    $scope.selectedIngre = [];
    $scope.categories = [];
    $scope.selectedCategory=[];

    $http({
      method: 'GET',
      url: '/getAllCategory'
    })
      .then(function (result) {
        $scope.categories = result.data;
        $log.debug('Categories',$scope.categories);
      });

    $http({
      method: 'GET',
      url: '/getSelectedCategory',
      params: {
        name: $rootScope.selectedRowFood.foodNameEng
      }
    })
      .then(function (result) {
        $scope.selectedCategory = result.data[0];
        $log.debug('Selected Categories',$scope.selectedCategory);
      });

    $http({
      method: 'GET',
      url: '/getAllIngredient'
    })
      .then(function (result) {
        $scope.datasLeft = result.data;
      });

    $http({
      method: 'GET',
      url: '/getSelectedIngreByFood',
      params: {
        id: $rootScope.selectedRowFood.foodId
      }
    })
      .then(function (result) {
        $scope.datasRight = result.data;
      });


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
      $scope.datasRight.splice($scope.datasRight.indexOf(right),1);
      $scope.right = {};
    };

    vm.edit = function(){
      $log.debug('error', $scope.food.foodName);
      $log.debug('error', $scope.food.cal);
      $log.debug('error', $scope.food.fat);
      $log.debug('error', $scope.food.carboh);
      $log.debug('error', $scope.food.protein);
      $log.debug('error', $scope.food.amount);
      $log.debug('error', $scope.food.unit);
      $log.debug('error', $scope.food.category);
      $log.debug('error', $scope.datasRight);
      if($scope.datasRight.length > 0){
        $log.debug('Set not null');
        $http({
          method: 'GET',
          url: '/updateFoodWithIngredient',
          params: {
            id: $rootScope.selectedRowFood.foodId,
            name: $scope.food.foodName,
            cal: $scope.food.cal,
            fat: $scope.food.fat,
            carboh: $scope.food.carboh,
            prote: $scope.food.protein,
            category: $scope.food.category,
            amount: $scope.food.amount,
            unit: $scope.food.unit,
            ingredients: $scope.datasRight
          }

        }).then(function (result) {
          if(result!=null) {
            alert('The food has been updated.')
          }
          $scope.datasRight = [];
          $uibModalInstance.dismiss('ok');
          $rootScope.getFoodToManage();
        });

      }else{
        $log.debug('Set null');
        $http({
          method: 'GET',
          url: '/updateFood',
          params: {
            id: $rootScope.selectedRowFood.foodId,
            name: $scope.food.foodName,
            cal: $scope.food.cal,
            fat: $scope.food.fat,
            carboh: $scope.food.carboh,
            prote: $scope.food.protein,
            category: $scope.food.category,
            amount: $scope.food.amount,
            unit: $scope.food.unit
          }

        }).then(function (result) {
          if(result!=null) {
            alert('The food has been updated.')
          }
          $scope.datasBottom = [];
          $uibModalInstance.dismiss('ok');
          $rootScope.getFoodToManage();
        });
      }

    };
    vm.cancel = function () {
      $uibModalInstance.dismiss('cancel');
      $location.path('/foodManage')
    };
  }



})();
