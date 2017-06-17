/**
 * Created by ADMIN on 12/7/2016.
 */
(function () {
  'use strict';
  angular.module('app')
    .controller('SetMenuManageController', SetMenuManageController)
    .controller('ModalAddSetMenuController',ModalAddSetMenuController)
    .controller('ModalAddSetMenuInstanceController',ModalAddSetMenuInstanceController)
    .controller('ModalUpdateSetMenuController',ModalUpdateSetMenuController)
    .controller('ModalUpdateSetMenuInstanceController',ModalUpdateSetMenuInstanceController);

  /**ngInject*/
  function SetMenuManageController($rootScope,$scope,$http,$log) {
    var vm = this;
    $scope.setMenus = [];
    $rootScope.getSetMenuToManage = function() {
      $http
        .get('/getSetMenuToManage')
        .then(function (result) {
          $scope.setMenus = result.data;
        });
    };
    $rootScope.getSetMenuToManage();

    $scope.setClickedRow = function(setMenu){
      $rootScope.selectedRowSetMenu = setMenu;
    };

    $scope.delete = function(){
      $log.debug('Set menu to delete',$rootScope.selectedRowSetMenu[0]);
      $http({
        method: 'GET',
        url: '/deleteSetMenu',
        params: {
          id: $rootScope.selectedRowSetMenu[0]
        }
      })
        .then(function (result) {
         alert("The set menu has been deleted.");
          $rootScope.getSetMenuToManage();
        });
    }

  }
  /**ngInject*/
  function ModalAddSetMenuController($http,$uibModal,$log,$location,$scope,$rootScope) {
    var vm = this;

    $scope.switchBool = function (value) {
      $scope[value] = !$scope[value];
    };
    // $rootScope.signUpSuccess = false;
    vm.animationsEnabled = true;
    vm.open = function (size) {


      var modalInstance = $uibModal.open({
        animation: vm.animationsEnabled,
        templateUrl: 'addSetMenu.html',
        controller: 'ModalAddSetMenuInstanceController',
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
  function ModalAddSetMenuInstanceController($rootScope,$http,$log,$scope,$uibModalInstance,$location) {
    var vm = this;
    $scope.datasLeft = [];
    $scope.datasRight = [];
    $scope.left = {};        // ข้อมูลที่ผูกกับ form
    $scope.right = {};
    $scope.categories = [];
    $scope.foods = [];

    $http({
      method: 'GET',
      url: '/getFoodToManage'
    })
      .then(function (result) {
        $scope.foods = result.data;
      });


    vm.ok = function () {
      $log.debug('bfast', vm.set.bfast);
      $log.debug('lunch', vm.set.lunch);
      $log.debug('dinner', vm.set.dinner);
      $http({
        method: 'GET',
        url: '/createSetMenu',
        params: {
          bfast: vm.set.bfast,
          lunch: vm.set.lunch,
          dinner: vm.set.dinner
        }

      }).then(function (result) {
        if (result != null) {
          alert('The set menu has been added.')
          $uibModalInstance.dismiss('ok');
        }


        $rootScope.getSetMenuToManage();
      });

      vm.cancel = function () {
        $uibModalInstance.dismiss('cancel');
        $location.path('/setMenuManage')
      };
    };
  }
    /**ngInject*/
    function ModalUpdateSetMenuController($http, $uibModal, $log, $location, $scope, $rootScope) {
      var vm = this;

      $scope.switchBool = function (value) {
        $scope[value] = !$scope[value];
      };
      // $rootScope.signUpSuccess = false;
      vm.animationsEnabled = true;
      vm.open = function (size) {


        var modalInstance = $uibModal.open({
          animation: vm.animationsEnabled,
          templateUrl: 'updateSetMenu.html',
          controller: 'ModalUpdateSetMenuInstanceController',
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
    function ModalUpdateSetMenuInstanceController($rootScope, $http, $log, $scope, $uibModalInstance, $location) {
      var vm = this;
      $scope.datasLeft = [];
      $scope.datasRight = [];
      $scope.left = {};        // ข้อมูลที่ผูกกับ form
      $scope.right = {};
      $scope.selectedIngre = [];
      $scope.categories = [];
      $scope.selectedCategory = [];
      $scope.foods = [];

      $http({
        method: 'GET',
        url: '/getFoodToManage'
      })
        .then(function (result) {
          $scope.foods = result.data;
        });


      vm.edit = function () {
      $log.debug('Set id to update',$rootScope.selectedRowSetMenu[0]);
        $http({
          method: 'GET',
          url: '/updateSetMenu',
          params: {
            id: $rootScope.selectedRowSetMenu[0],
            bfast: $scope.set.bfast,
            lunch: $scope.set.lunch,
            dinner: $scope.set.dinner
          }

        }).then(function (result) {
          if (result != null) {
            alert('The set menu has been updated.')
            $scope.datasRight = [];
            $uibModalInstance.dismiss('ok');
            $rootScope.getSetMenuToManage();
          }

        });

        vm.cancel = function () {
          $uibModalInstance.dismiss('cancel');
          $location.path('/setMenuManage')
        };
      }

    }

})();
