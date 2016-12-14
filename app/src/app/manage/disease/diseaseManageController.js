/**
 * Created by ADMIN on 12/7/2016.
 */
(function () {
  'use strict';
  angular.module('app')
    .controller('DiseaseManageController', DiseaseManageController)
    .controller('ModalAddDiseaseController',ModalAddDiseaseController)
    .controller('ModalAddDiseaseInstanceController',ModalAddDiseaseInstanceController);
  /**ngInject*/
  function DiseaseManageController($scope,$http,$log) {
    var vm = this;
    $scope.selectedRow = null;
    $scope.diseases = [];

    $http
      .get('http://localhost:8080/getDiseases')
      .then(function (result) {
        $scope.diseases = result.data;
        $log.debug('disease ', result.data);
      });

    $scope.setClickedRow = function(index){
      $scope.selectedRow = index;
    };

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
  function ModalAddDiseaseInstanceController($http,$log,$scope,$uibModalInstance,$location) {
    var vm = this;
    $http({
      method: 'GET',
      url: 'http://localhost:8080/getAllSetMenu'
    })
      .then(function (result) {
        $scope.optionsList2 = result.data;
      });

    $scope.optionsList = [
      {id: 1,  name : "Javaaaaaaaaaaaaaaaaaaaaaaaa"},
      {id: 2,  name : "C"},
      {id: 3,  name : "C++"},
      {id: 4,  name : "AngularJs"},
      {id: 5,  name : "JavaScript"}
    ];

    vm.cancel = function () {
      $uibModalInstance.dismiss('cancel');
      $location.path('/diseaseManage')
    };
  }



})();
