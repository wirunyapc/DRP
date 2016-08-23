/**
 * Created by Asus on 6/8/2559.
 */
(function(){
  'use strict';

  var API_PREFIX = 'http://localhost:8080';
  /** @ngInject */
  angular.module('ngResource+apiPrefix', [
    'ngResource'
  ]).decorate('$resource', function ($delegate) {

    return function decoratedResource() {
      arguments[0] = API_PREFIX + arguments[0];
      return $delegate.apply(this, arguments);
    };

  });
})();
