/**
 * Created by Asus on 7/8/2559.
 */
(function () {
  'use strict'
  angular.module('app').factory('securityService',securityService)
  /**ngInject*/
  function securityService($resource){
    return $resource('/user/:action',{},
      {authenticate:
      {
        method:'POST',
        params:{'action':'authenticate'},
        header: {'Content-Type' : 'application/x-www-form-urlencoded'}
      }
      })
  }
})();
