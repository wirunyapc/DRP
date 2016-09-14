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
    vm.bfast = null;
    vm.lunch = null;
    vm.dinner = null;
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

    $scope.selectDisease = function(){
      $http({
        method:'PUT',
        url: 'http://localhost:8080/disease/?disease=:diseaseName'
      })
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
  function calendarController ($http,$scope, $filter, moment, uiCalendarConfig, $timeout){
   // var dietPlan = [[["2016-09-10 00:00:00.0","1","ทอดมันปลา",],["2016-09-10 00:00:00.0","2","ผัดแตงกวาไก่",],["2016-09-10 00:00:00.0","3","กุ้งผัดหน่อไม้ฝรั่ง",],["2016-09-11 00:00:00.0","1","ข้าวต้มกุ้ง",],["2016-09-11 00:00:00.0","2","ปลาทับทิมนึ่งซีอิ้ว",],["2016-09-11 00:00:00.0","3","ปลากะพงลวกจิ้ม",],["2016-09-12 00:00:00.0","1","แกงเหลืองปักษ์ใต้",],["2016-09-12 00:00:00.0","2","ข้าวผัดมันกุ้ง",],["2016-09-12 00:00:00.0","3","ขนมจีบ",],["2016-09-13 00:00:00.0","1","ทอดมันปลา",],["2016-09-13 00:00:00.0","2","ผัดแตงกวาไก่",],["2016-09-13 00:00:00.0","3","กุ้งผัดหน่อไม้ฝรั่ง",],["2016-09-14 00:00:00.0","1","ข้าวต้มกุ้ง",],["2016-09-14 00:00:00.0","2","ปลาทับทิมนึ่งซีอิ้ว",],["2016-09-14 00:00:00.0","3","ปลากะพงลวกจิ้ม",],["2016-09-15 00:00:00.0","1","แกงเหลืองปักษ์ใต้",],["2016-09-15 00:00:00.0","2","ข้าวผัดมันกุ้ง",],["2016-09-15 00:00:00.0","3","ขนมจีบ",],["2016-09-16 00:00:00.0","1","ปลานึ่งสมุนไพร",],["2016-09-16 00:00:00.0","2","มะละกอผัดหมู",],["2016-09-16 00:00:00.0","3","น้ำพริกหนุ่ม",]]];
    $scope.$on('$viewContentLoaded', function(){
      console.log("Test");
    });

    $scope.breakfast = "test bfast";

    //$scope.init = function () {
    //  $timeout(function() {$scope.calendarRefresh();}, 2000);
    //};
    $scope.calendarRefresh = function(){
      $('#calendar').fullCalendar('render');
      $('.fc-today-button').click();
    };
    $scope.requestPlan = function(){
      /*Diet Plan*/
      $http({
        method: 'GET',
        url: 'http://localhost:8080/getFoodPlan'
      }).then(function(response) {
        console.log('PLANNNNNNN',response.data);
        $scope.dietPlan = response.data;
        //$rootScope.plan = response.data;
       // $scope.plan = response.data;
       //$scope.calendarDate[0].events[0].splice(0, $scope.calendarDate[0].events[0].length)
        $scope.calendarDate = [
          {
            events: $scope.convertEvents(response.data)
          }
        ];
        $('#calendar').fullCalendar('today');
        $('#calendar').fullCalendar('removeEvents');
        $('#calendar').fullCalendar('addEventSource',$scope.convertEvents(response.data));
      });
    }
    $scope.requestPlan();

    $scope.mealModel = [];

    $scope.convertEvents = function(plans){
      if(!plans)
        return;

      var _mealModel = [];
      var mealEvents = [];
      for(var i = 0 ; i < plans[0].length ; i++){

        console.log('i' + i);
        console.log('Two dimension',plans[0][i]);

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
        events: $scope.convertEvents($scope.dietPlan)
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
      $scope.bfast = dailyMeal[0].food;
      $scope.lunch = dailyMeal[1].food;
      $scope.dinner = dailyMeal[2].food;

      console.log('setCalDate: dailyMeal',dailyMeal);
      console.log('selectDate',moment(date).valueOf());
      $scope.calendarDate[0].events[0].start = selectedDate;				    // update Calendar event dateFrom
      $scope.selectedDate = $filter('date')(selectedDate, 'yyyy-MM-dd');		// update $scope.dateFrom
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
        //background: '#f26522',
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
