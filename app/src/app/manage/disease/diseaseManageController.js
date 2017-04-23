/**
 * Created by ADMIN on 12/7/2016.
 */
(function () {
  'use strict';
  angular.module('app')
    .controller('DiseaseManageController', DiseaseManageController)
    .controller('ModalAddDiseaseController',ModalAddDiseaseController)
    .controller('ModalAddDiseaseInstanceController',ModalAddDiseaseInstanceController)
    .controller('ModalUpdateDiseaseController',ModalUpdateDiseaseController)
    .controller('ModalUpdateDiseaseInstanceController',ModalUpdateDiseaseInstanceController);
  /**ngInject*/
  function DiseaseManageController($rootScope,$scope,$http,$log) {
    var vm = this;
    $scope.diseases = [];
    $rootScope.getDiseaseToManage = function() {
      $http
        .get('http://localhost:8080/getDiseases')
        .then(function (result) {
          $scope.diseases = result.data;
          $log.debug('disease ', result.data);
        });
    };
    $rootScope.getDiseaseToManage();

    $scope.setClickedRow = function(disease){
      $rootScope.selectedRow = disease;
    };

    $scope.delete = function(){
      $http({
        method: 'GET',
        url: 'http://localhost:8080/deleteDisease',
        params: {
          name: $rootScope.selectedRow.diseaseName
        }
      })
        .then(function (result) {
         alert("The disease has been deleted.")
          $rootScope.getDiseaseToManage();
        });
    }

  }
  /**ngInject*/
  function ModalAddDiseaseController($http,$uibModal,$log,$location,$scope,$rootScope) {
    var vm = this;

    $scope.switchBool = function (value) {
      $scope[value] = !$scope[value];
    };
    // $rootScope.signUpSuccess = false;
    vm.animationsEnabled = true;
    vm.open = function (size) {


      var modalInstance = $uibModal.open({
        animation: vm.animationsEnabled,
        templateUrl: 'addDisease.html',
        controller: 'ModalAddDiseaseInstanceController',
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
  function ModalAddDiseaseInstanceController($rootScope,$http,$log,$scope,$uibModalInstance,$location) {
    var vm = this;
    $scope.datasTop = [];
    $scope.datasBottom =[];
    $scope.top ={};        // ข้อมูลที่ผูกกับ form
    $scope.bottom ={};


    $http({
      method: 'GET',
      url: 'http://localhost:8080/getAllSetMenu'
    })
      .then(function (result) {
        $scope.datasTop = result.data;
      });


    $scope.SelectData = function (top) {
      $log.debug('error top', top);
      $scope.datasBottom.push(top[0]);
      $log.debug('data bottom', $scope.datasBottom);
      //$scope.datasTop.splice($scope.datasTop.indexOf(top[0]),1);
      $scope.top = {};
    };
    $scope.DeselectData = function (bottom) {
      $log.debug('error bottom', bottom);
      //$scope.datasTop.push(bottom);
      $scope.datasBottom.splice($scope.datasBottom.indexOf(bottom[0]),1);
      $scope.bottom = {};
    };

    vm.ok = function(){
  $log.debug('data bottom', $scope.datasBottom);
      if($scope.datasBottom.length > 0){
        $log.debug('Set not null');
        $http({
          method: 'GET',
          url: 'http://localhost:8080/createDiseaseWithSetMenu',
          params: {
            name: vm.disease.diseaseName,
            cal: vm.disease.cal,
            fat: vm.disease.fat,
            carboh: vm.disease.carboh,
            prot: vm.disease.prote,
            setMenu: $scope.datasBottom
          }

        }).then(function (result) {
          if(result!=null) {
            alert('The disease has been added.')
            $uibModalInstance.dismiss('ok');
          }
          $scope.datasBottom = [];
          $rootScope.getDiseaseToManage();
        });

        }else{
        $log.debug('Set  null');
        $http({
          method: 'GET',
          url: 'http://localhost:8080/createDisease',
          params: {
            name: vm.disease.diseaseName,
            cal: vm.disease.cal,
            fat: vm.disease.fat,
            carboh: vm.disease.carboh,
            prot: vm.disease.prote
          }

        }).then(function (result) {
          if(result!=null) {
            alert('The disease has been added.')
            $uibModalInstance.dismiss('ok');
          }
          $scope.datasBottom = [];

          $rootScope.getDiseaseToManage();
        });
      }

  };

    vm.cancel = function () {
      $uibModalInstance.dismiss('cancel');
      $location.path('/diseaseManage')
    };
  }
  /**ngInject*/
  function ModalUpdateDiseaseController($http,$uibModal,$log,$location,$scope,$rootScope) {
    var vm = this;

    $scope.switchBool = function (value) {
      $scope[value] = !$scope[value];
    };
    // $rootScope.signUpSuccess = false;
    vm.animationsEnabled = true;
    vm.open = function (size) {


      var modalInstance = $uibModal.open({
        animation: vm.animationsEnabled,
        templateUrl: 'updateDisease.html',
        controller: 'ModalUpdateDiseaseInstanceController',
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
  function ModalUpdateDiseaseInstanceController($rootScope,$http,$log,$scope,$uibModalInstance,$location) {
    var vm = this;
    $scope.datasTop = [];
    $scope.datasBottom =[];
    $scope.top ={};        // ข้อมูลที่ผูกกับ form
    $scope.bottom ={};
    $scope.selectedSetMenu = [];


    $http({
      method: 'GET',
      url: 'http://localhost:8080/getAllSetMenu'
    })
      .then(function (result) {
        $scope.datasTop = result.data;
      });

    $http({
      method: 'GET',
      url: 'http://localhost:8080/getSelectedSetMenuByDisease',
      params: {
        name: $rootScope.selectedRow.diseaseName
      }
    })
      .then(function (result) {
        $scope.datasBottom = result.data;
      });


    $scope.SelectData = function (top) {
      $log.debug('error top', top);
      $scope.datasBottom.push(top[0]);
      $log.debug('data bottom', $scope.datasBottom);
      //$scope.datasTop.splice($scope.datasTop.indexOf(top[0]),1);
      $scope.top = {};
    };
    $scope.DeselectData = function (bottom) {
      $log.debug('error bottom', bottom);
      //$scope.datasTop.push(bottom);
      $scope.datasBottom.splice($scope.datasBottom.indexOf(bottom),1);
      $scope.bottom = {};
    };

    vm.edit = function(){
      $log.debug('error', $scope.disease.dName);
      $log.debug('error', $scope.disease.cal);
      $log.debug('error', $scope.disease.fat);
      $log.debug('error', $scope.disease.carboh);
      $log.debug('error', $scope.disease.prote);
      $log.debug('data bottom', $scope.datasBottom);
      if($scope.datasBottom.length > 0){
        $log.debug('Set not null');
        $http({
          method: 'GET',
          url: 'http://localhost:8080/updateDiseaseWithSetMenu',
          params: {
            id: $rootScope.selectedRow.id,
            name: $scope.disease.dName,
            cal: $scope.disease.cal,
            fat: $scope.disease.fat,
            carboh: $scope.disease.carboh,
            prot: $scope.disease.prote,
            setMenu: $scope.datasBottom
          }

        }).then(function (result) {
          if(result!=null) {
            alert('The disease has been updated.')
          }
          $scope.datasBottom = [];
          $uibModalInstance.dismiss('ok');
          $rootScope.getDiseaseToManage();
        });

      }else{
        $log.debug('Set null');
        $http({
          method: 'GET',
          url: 'http://localhost:8080/updateDisease',
          params: {
            id: $rootScope.selectedRow.id,
            name: $scope.disease.dName,
            cal: $scope.disease.cal,
            fat: $scope.disease.fat,
            carboh: $scope.disease.carboh,
            prot: $scope.disease.prote
          }

        }).then(function (result) {
          if(result!=null) {
            alert('The disease has been updated.')
          }
          $scope.datasBottom = [];
          $uibModalInstance.dismiss('ok');
          $rootScope.getDiseaseToManage();
        });
      }

    };
    vm.cancel = function () {
      $uibModalInstance.dismiss('cancel');
      $location.path('/diseaseManage')
    };
  }



})();
