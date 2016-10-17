/**
 * Created by Asus on 6/8/2559.
 */
(function () {
  'use strict';

  angular
    .module('app')
    .controller('mainDRPController',mainDRPController)

    .controller('MainCtrl',calendarController);


  /** @ngInject */
  function mainDRPController($filter,$http, $log,$scope,$rootScope) {
    var vm = this;
    vm.selectedDate = null;
    vm.bfast = null;
    vm.lunch = null;
    vm.dinner = null;
    vm.firstDayOfWeek = 0;
    $scope.plan = null;
    $scope.showme = false;

    /*CalInfo*/
    vm.bmi = null;
    vm.budget = null;
    vm.net = 0;
   //
    vm.under = 0;
    vm.activity = 0;

    $scope.currentuser = $rootScope.currentuser;
    console.log('current user from DRP',$scope.currentuser);




    /* BMI request*/

    $http({
      method: 'GET',
      url: 'http://localhost:8080/bmi',
      params: {name: $scope.currentuser}
    })
      .then(function (result) {
        $log.debug(result.data[0]);
        vm.bmi = result.data[0]+"("+result.data[1]+")";
        $log.debug('bmi ',result);
      });

    /* BMR request*/
     $http({
     method: 'GET',
     url: 'http://localhost:8080/getBmr',
     params: {name: $scope.currentuser}
     })
     .then(function (result) {
       $log.debug('bmr ',result.data[0]);
     vm.budget = result.data[0];

     });

    /*NET*/
  //  vm.net = vm.diet - vm.activity;

    /*Activity*/

    /*Under*/
  //  vm.under = vm.budget - vm.net;


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

  /** @ngInject */
  function calendarController($http,$scope, $filter, moment, uiCalendarConfig,$log,$rootScope){
    var vm = this;
    vm.currentR=$rootScope.currentrole;
    vm.selectedFoodBfast = null;
    vm.selectedFoodLunch = null;
    vm.selectedFoodDinner = null;

    /*DROPDOWN Food*/
    $scope.foods = [];

    $http
      .get('http://localhost:8080/getFoods')
      .then(function (result) {
        $scope.foods = result.data;
        $log.debug('food ',result.data);
      });

    $scope.setFood=function(food,meal,index){
      $log.debug(meal,food,index);
      $http({
        method: 'GET',
        url: 'http://localhost:8080/setFood',
        params: {
          food: food,
          name: $scope.currentuser,
          meal: meal,
          date: $scope.selectedDate,
          idx: index
        }
      }).then(function(result) {

        $log.debug('set Food'+result.data);
        $scope.requestPlan();


      }, function errorCallback(response) {
        $log.debug('Error set Food',response);
      });
    }


    /*end*/


    /*DROPDOWN Disease*/

    $scope.diseases = [];
if(vm.currentR=='patient') {
  $http
    .get('http://localhost:8080/getDiseases')
    .then(function (result) {
      $scope.diseases = result.data;
      $log.debug('disease ', result.data);
    });

  $scope.setDisease = function () {

    $log.debug(vm.selectedDisease);
    $http({
      method: 'GET',
      url: 'http://localhost:8080/setDisease',
      params: {
        disease: vm.selectedDisease,
        name: $scope.currentuser
      }
    }).then(function (result) {
      //$scope.diseasesName = result.data;
      $log.debug('set Disease');
      //console.log('set disease success', result);
      $scope.requestPlan();
    }, function errorCallback(response) {
      $log.debug('Error set Disease', response);
    });

  };

  $http({
    method: 'GET',
    url: 'http://localhost:8080/getCurrentDisease',
    params: {
      name: $scope.currentuser
    }
  }).then(function (result) {
    $scope.currentDisease = result.data[0];
  }, function errorCallback(response) {
    $log.debug('Error get current Disease', response);
  });
  /*END DROPDOWN*/
}

    $scope.$on('$viewContentLoaded', function(){
      console.log("Test");
    });

    $scope.breakfast = "test bfast";

    //$scope.init = function () {
    //  $timeout(function() {$scope.calendarRefresh();}, 2000);
    //};
    $scope.calendarRefresh = function(){
      //$('#calendar').fullCalendar('render');
      //$('.fc-today-button').click();
      uiCalendarConfig.calendars['myCalendar1'].fullCalendar('render');

    };
    $scope.requestPlan = function(){
      $log.debug('requestPlan');
      /*Diet Plan*/
      $http({
        method: 'GET',
        url: 'http://localhost:8080/getFoodPlan',
        params: {name: $scope.currentuser}
      }).then(function(response) {
        if(response==null){
          //Show please select disease
        }
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

        $scope.setCalDate($scope.calendarDate[0].events[0].start);

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
      for(var i = 0 ; i < plans.length ; i++){

        console.log('i' + i);
        console.log('Two dimension',plans[i]);

        var dailyMeals = plans[i];



        if(dailyMeals){

          for(var i = 0; i < dailyMeals.length;i++){
            var mealDatePlan = new Date(dailyMeals[i][0]);
            console.log('date', mealDatePlan);
            if(_mealModel[mealDatePlan.getTime()]){

              _mealModel[mealDatePlan.getTime()].push({'mealId':dailyMeals[i][1],'food':dailyMeals[i][3]});

              var mealEvent = {
                title: dailyMeals[i][1],
                start: mealDatePlan,
                allDay: true,
                rendering: 'background',
                backgroundColor: '#f26522',
              };

              mealEvents.push(mealEvent);

            }else{
              _mealModel[mealDatePlan.getTime()] = [{'mealId':dailyMeals[i][1],'food':dailyMeals[i][3]}];
            }
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
      var selectedDate = moment(date).format('YYYY-MM-DD');				    // set dateFrom based on user click on calendar
      var dateObj = new Date(selectedDate);
      dateObj.setHours(0);
      dateObj.setMinutes(0);
      dateObj.setSeconds(0);
      dateObj.setMilliseconds(0);

      var dailyMeal = $scope.mealModel[dateObj.getTime()];
      $rootScope.bfast = dailyMeal[0].food;
      $rootScope.lunch = dailyMeal[1].food;
      $rootScope.dinner = dailyMeal[2].food;

      $scope.getBmrValue();


      vm.selectedFoodBfast = "";
      vm.selectedFoodLunch = "";
      vm.selectedFoodDinner = "";
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
