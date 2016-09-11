/**
 * Created by Asus on 6/8/2559.
 */
(function () {
  'use strict';

  angular
    .module('app')
    .controller('mainDRPController', ['$scope', '$http' , mainDRPController])
    .controller('bmiController', bmiController);



  /** @ngInject */
  function mainDRPController($scope, $http,$filter) {
    var vm = this;
    vm.selectedDate = null;
    vm.firstDayOfWeek = 0;
    vm.setDirection = function(direction) {
      vm.direction = direction;
    };
    vm.dayClick = function(date) {
      vm.msg = "You clicked " + $filter("date")(date, "MMM d, y h:mm:ss a Z");
    };
    vm.prevMonth = function(data) {
      vm.msg = "You clicked (prev) month " + data.month + ", " + data.year;
    };
    vm.nextMonth = function(data) {
      vm.msg = "You clicked (next) month " + data.month + ", " + data.year;
    };
    vm.setDayContent = function(date) {
      // You would inject any HTML you wanted for
      // that particular date here.
      return "<p>date</p>";
    };

    $scope.fetchPlan = function(){
      $http({
        method: 'GET',
        url: 'http://localhost:8080/plan'
      }).then(function(response) {
        console.log(response.data);
      });
    };

  }
 function bmiController($cookies,$rootScope,securityService) {


   /*  var value = this;
    if(user) {
    console.log("I'm user");
    homeService.get({weight: $rootScope.user.weight, height: $rootScope.user.height},
    function (data) {
    value = data;


    });
    }*/

   var authToken = $cookies.get('authToken');
   if(authToken){
     $rootScope.authToken = authToken;
     securityService.get(function (user) {
       $rootScope.user = user;
       console.log($rootScope.user)
          });
   }

 }
})();
