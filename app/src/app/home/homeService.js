/**
 * Created by ADMIN on 8/26/2016.
 */
(function() {
  'use strict'
  angular
    .module('app')
    .factory('homeService',homeService)


  /** @ngInject */
  function homeService($resource){
    return $resource('/plan',
      {get:{method:'GET'}
      });
  }


})();
