/**
 * Created by Asus on 6/8/2559.
 */
(function () {
  'use strict';

  angular
    .module('app')
    .controller('mainDRPController', mainDRPController)
    .controller('bmiController', bmiController)
    .controller('MainCtrl',calendarController);


  /** @ngInject */
  function mainDRPController($filter,$http, $log,$scope,$rootScope) {
    var vm = this;
    vm.selectedDate = null;
    vm.firstDayOfWeek = 0;
    vm.bmi = null;
    $scope.plan = null;

    /* BMI request*/
      $http.get('http://localhost:8080/bmi', {})
        .then(function (result) {
          $log.debug(result.data[0]);
          vm.bmi = result.data[0]+"/"+result.data[1];
          $log.debug('bmi ',result);
        });


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
        url: 'http://localhost:8080/getFoodPlan'
      }).then(function(response) {
        console.log(response.data);
        $rootScope.plan = response.data;
      });
    };


  }
  function bmiController(homeService, $log) {

    /*
     $http({
     method: 'GET',
     url: 'http://localhost:8080/bmi'
     }).then(function(response) {
     console.log(response.data);
     $scope.bmi = response.data;
     });
     */
    var vm = this;



  }
  function calendarController ($scope, $filter, moment, uiCalendarConfig){
    var dietPlan = $scope.plan;


    $scope.requestPlan = function(){
      /*Diet Plan*/
      $http({
        method: 'GET',
        url: 'http://localhost:8080/getFoodPlan'
      }).then(function(response) {
        console.log(response.data);
        $rootScope.plan = response.data;
        $scope.plan = response.data;
        $scope.convertEvents($scope.plan);
      });
    }
    $scope.requestPlan();

    $scope.mealModel = [];

    $scope.convertEvents = function(plans){
      var _mealModel = [];
      var mealEvents = [];
      for(var i = 0 ; i < plans[0].length ; i++){
        var mealDate = new Date(startDate.getTime());
        mealDate.setDate(startDate.getDate() + i);

        var dd = mealDate.getDate();
        var mm = mealDate.getMonth() + 1;
        var y = mealDate.getFullYear();

        var someFormattedDate = mm + '/' + dd + '/' + y;

        //console.log('convert events ' + someFormattedDate);
        console.log('i' + i);
        console.log('Two dimension',plans[0][i]);
        //console.log('dailyMeal',plans[0][0][i]);

        //////
        var dailyMeals = plans[0][i];
        var mealDatePlan = new Date(plans[0][i][0]);

        console.log('date', mealDatePlan);
        if(dailyMeals){
          if(_mealModel[mealDatePlan.getTime()]){

            _mealModel[mealDatePlan.getTime()].push({'mealId':dailyMeals[1],'food':dailyMeals[2]});

            var mealEvent = {
              title: dailyMeals[1],
              start: mealDatePlan,
              allDay: true,
              rendering: 'background',
              backgroundColor: '#f26522',
            };

            mealEvents.push(mealEvent);

          }else{
            _mealModel[mealDatePlan.getTime()] = [{'mealId':dailyMeals[1],'food':dailyMeals[2]}];
          }
        }


      }

      $scope.mealModel = _mealModel;
      console.log('mealModel',$scope.mealModel);
      return mealEvents;

    }

    $scope.calendarDate = [
      {
        events: $scope.convertEvents(dietPlan)
      }
    ];

    $scope.setCalDate = function(date, jsEvent, view) {
      //console.log('selectDate',date);
      var selectedDate = moment(date).format('YYYY-MM-DD');				    // set dateFrom based on user click on calendar
      var dateObj = new Date(selectedDate);
      dateObj.setHours(0);
      dateObj.setMinutes(0);
      dateObj.setSeconds(0);
      dateObj.setMilliseconds(0);

      var dailyMeal = $scope.mealModel[dateObj.getTime()];
      console.log('setCalDate: dailyMeal',dailyMeal);
      console.log('selectDate',moment(date).valueOf());
      $scope.calendarDate[0].events[0].start = selectedDate;				    // update Calendar event dateFrom
      $scope.selectedDate = $filter('date')(selectedDate, 'yyyy-MM-dd');;		// update $scope.dateFrom
      console.log('$scope.uiConfig', $scope.uiConfig);
      console.log('uiCalendarConfig', uiCalendarConfig);
      console.log('clickedDate',date);
      console.log('jsEvent',jsEvent);
      console.log('view',view);
    };

    $scope.uiConfig = {
      calendar : {
        editable : false,
        aspectRatio: 2,
        header : {
          left : 'title',
          center : '',
          right : 'today prev,next'
        },
        dayClick : $scope.setCalDate,
        background: '#f26522',
      },
    };

    $scope.calendarTab = 1;

    $scope.selectCalTab = function(tab){		// function to set which calendar tab is shown in html via ng-if
      if(tab==1){
        $scope.calendarTab = 1;
      } else {
        $scope.calendarTab = 2;
      }
    };


  }





})();
