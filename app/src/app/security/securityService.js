/**
 * Created by Asus on 7/8/2559.
 */
(function () {
  'use strict';
  angular
    .module('app', [])
    .factory('securityService',['$resource',
  function ($resource){
    //return {
    //  authenticate: function(data){
    //    return $http.post('/user/authenticate',data);
    //  },
    //  get: function(){
    //    return $http.get('user');
    //  }
    //};
    return $resource('/user/:action',{},
      {authenticate:
      {
        method:'POST',
        params:{'action':'authenticate'},
        header: {'Content-Type' : 'application/x-www-form-urlencoded'}
      }
      })

  }]);
})();
