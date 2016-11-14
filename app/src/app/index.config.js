(function() {
  'use strict';

  angular
    .module('app')
    .config(config)
    .config(configFailRequestRedirect);

  /** @ngInject */
  function config($logProvider, toastrConfig) {
    // Enable log
    $logProvider.debugEnabled(true);

    // Set options third-party lib
    toastrConfig.allowHtml = true;
    toastrConfig.timeOut = 3000;
    toastrConfig.positionClass = 'toast-top-right';
    toastrConfig.preventDuplicates = true;
    toastrConfig.progressBar = true;
  }
  /** @ngInject */
  function configFailRequestRedirect($locationProvider,$httpProvider) {
    /*Register error
     * nauthentication requests*/
    $httpProvider.interceptors.push(function ($q,$rootScope,$location) {
      return{
        'responseError': function (rejection) {
          var status = rejection.status;
          var config = rejection.config;
          var method = config.method;
          var url = config.url;

          if (status==404){
            $location.path("/");
          }else {
            $rootScope.error = method + "on"+url+"failed with status"+ status;
          }
          return $q.reject(rejection);
        }

      }
    });
    var exampleAppConfig={
      useAuthTokenHeader:true
    };
    $httpProvider.interceptors.push(function ($q, $rootScope) {
      return {
        'request': function (config) {
          if (angular.isDefined($rootScope.authToken)) {
            var authToken = $rootScope.authToken;
            if (exampleAppConfig.useAuthTokenHeader) {
              config.headers['X-Auth-Token'] = authToken;
            } else {
              config.url = config.url + "?token=" + authToken;
            }
          }
          return config || $q.when(config);
        }
      }
    })
  }



})();
