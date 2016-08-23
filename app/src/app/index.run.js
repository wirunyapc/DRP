(function() {
  'use strict';

  angular
    .module('app')
    .run(runBlock)
    .run(runSecurity);

  /** @ngInject */
  function runSecurity($rootScope,$location,$cookies,securityService) {
    var originalPath = $location.path('/');
    var authToken = $cookies.get('authToken');
    //$log.debug('token',authToken);
    if(authToken){
      $rootScope.authToken = authToken;
      securityService.get(function (user) {
        $rootScope.user = user;
        $location.path(originalPath);
      });
    }
    var removeErrorMsg = $rootScope.$on('$viewContentLoaded',function () {
      delete $rootScope.error;
    });
    removeErrorMsg();
    $rootScope.hasRole = function (role) {
      if ($rootScope.user == undefined){
        return false;
      }
      if ($rootScope.user.roles[role] == undefined){
        return false;
      }
      return $rootScope.user.roles[role];
    };
    $rootScope.logout = function () {
      delete $rootScope.user;
      delete $rootScope.authToken;
      $cookies.remove('authToken');
      $location.path('/')
    };
    $location.path('/');
  }

  /** @ngInject */
  function runBlock($log,$rootScope) {

    $rootScope.hasRole = function (role) {
      if ($rootScope.user == undefined){
        return false;
      }
      if ($rootScope.user.roles[role] == undefined){
        return false;
      }
      return $rootScope.user.roles[role];
    }
    $log.debug('runBlock end');
  }

})();
