/**
 * Created by ADMIN on 8/26/2016.
 */

(function() {
  'use strict'
  angular
    .module('app')
    .factory('homeService',homeService);

  /** @ngInject */
  function homeService($http, $q, $log) {
    return {
      getBmi: function() {
        var deferred = $q.defer();
        $http
          .get('http://localhost:8080/bmi', {
            header: {'Content-Type' : 'application/x-www-form-urlencoded'}
          })
          .then(function(response) {
            $log.debug('getBmi');
            return deferred.resolve(response.data);
          },
            function(err) {
              return deferred.reject(err);
            });
        return deferred.promise;
      },
    };
  }




})();
