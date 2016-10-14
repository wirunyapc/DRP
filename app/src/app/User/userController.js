/**
 * Created by Asus on 10/8/2559.
 */
(function () {
  'use strict'
  angular.module('app').controller('ModalRegisterController',ModalRegisterController)
    .controller('ModalRegisterInstanceController',ModalRegisterInstanceController)
    .controller('MyAccountController',MyAccountController);

  /**ngInject*/
  function ModalRegisterController(userService,$uibModal,$log,$location,$rootScope,$scope) {
    var vm = this;
    $scope.switchBool = function (value) {
      $scope[value] = !$scope[value];
    };
    $rootScope.signUpSuccess = false;
    vm.animationsEnabled = true;
    vm.open = function (size) {

      var modalInstance = $uibModal.open({
        animation: vm.animationsEnabled,
        templateUrl: 'sign_up.html',
        controller: 'ModalRegisterInstanceController',
        controllerAs: 'vm',
        size: size
      });

      modalInstance.result.then(function (data) {
        userService.save(data);
        $rootScope.signUpSuccess = true;
        $location.path('/');

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

  function ModalRegisterInstanceController($uibModalInstance,$log,$location) {
    var vm = this;
    vm.ok = function () {
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
    }

    vm.cancel = function () {
      $uibModalInstance.dismiss('cancel');
      $location.path('/')
    };
  }
  function MyAccountController($rootScope,$http,$log,$scope,$location) {
    var vm = this;
    vm.loadingstatus = false;

/*    queryUserService.get({name: $rootScope.user.username}, function (data) {
        vm.user = data;

      },function () {
        vm.loadingstatus = true;
        vm.loadingMessage ="Cannot load user detail";
      }
    );*/

    vm.cancel = function () {
      $location.path('/home')
    };

    $http({
      method: 'GET',
      url: 'http://localhost:8080/getUser',
      params: {name: $rootScope.currentuser}
    }).then(function (result) {

        $log.debug('user ',result);
        vm.user = result.data;
      });

    vm.edit = function () {


    }
  }

})();
