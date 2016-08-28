/**
 * Created by Asus on 6/8/2559.
 */
(function () {
  'use strict';

  angular
    .module('app')
    .controller('mainDRPController', mainDRPController);

  /** @ngInject */
  function mainDRPController($filter) {
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


  }


})();
