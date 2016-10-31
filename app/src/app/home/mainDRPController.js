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
    vm.selectedDisease = null;
   // $rootScope.notEnoughFood=false;
    $rootScope.selectDisease=false;

    /*DROPDOWN Food*/
    $scope.foods = [];
    if(vm.currentR=="member") {
      $http
        .get('http://localhost:8080/getFoods')
        .then(function (result) {
          $scope.foods = result.data;
          $log.debug('food ', result.data);
        });
    }
    if(vm.currentR=="patient"){
      $http({
        method: 'GET',
        url: 'http://localhost:8080/getFoodsByDisease',
        params: {
          name: $scope.currentuser
        }
      })
        .then(function (result) {
          $scope.foods = result.data;
          $log.debug('food ', result.data);
        });
    }

    $scope.setFood=function(food,meal,index){
      $log.debug(meal,food,index);
      $scope.getTotalDietCal();
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
    //if(vm.selectedDisease==null){
    //  $log.debug('disease is null');
    //  $rootScope.selectDisease = true;
    //}else {
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
        $rootScope.selectDisease = true;
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
    $scope.getTotalDietCal = function(){
      $http({
        method: 'GET',
        url: 'http://localhost:8080/getTotalDietCal',
        params: {
          date: $scope.selectedDate,
          name: $scope.currentuser
        }
      })
        .then(function (result) {
          $rootScope.totalDietCal=result.data;

        });

    };
    $scope.requestPlan = function(){
      $log.debug('requestPlan');
      /*Diet Plan*/
      $http({
        method: 'GET',
        url: 'http://localhost:8080/getFoodPlan',
        params: {name: $scope.currentuser}
      }).then(function(response) {
        $log.debug('response from request plan',response.data[0]);
        //if(response.data[0]=="error"){
        //  $rootScope.notEnoughFood = true;
        //}
        if(response.data[0]=="null"){
          $rootScope.selectDisease = true;
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
      }, function errorCallback(response) {
       //Not enought food to solve
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

          for(var j = 0; j < dailyMeals.length;j++){
            var mealDatePlan = new Date(dailyMeals[j][0]);
            console.log('date', mealDatePlan);
            if(_mealModel[mealDatePlan.getTime()]){

              _mealModel[mealDatePlan.getTime()].push({'mealId':dailyMeals[j][1],'food':dailyMeals[j][3],'index': dailyMeals[j][2]});



            }else{
              _mealModel[mealDatePlan.getTime()] = [{'mealId':dailyMeals[j][1],'food':dailyMeals[j][3],'index': dailyMeals[j][2]}];
              var mealEvent = {
                title: dailyMeals[j][1],
                start: mealDatePlan,
                allDay: true,
                rendering: 'background',
                backgroundColor: '#f26522',
              };

              mealEvents.push(mealEvent);
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

      var dailyMeals = $scope.mealModel[dateObj.getTime()];
      var bfastMeals = dailyMeals.filter(function(food){
        return food.mealId == 1;
      }).sort(function(a,b){
        return a.index - b.index;
      });

      if(bfastMeals.length > 0){
        $rootScope.bfast1 = bfastMeals[0].food;
        $rootScope.bfast2 = bfastMeals[1].food;
        $rootScope.bfast3 = bfastMeals[2].food;
      }

      var lunchMeals = dailyMeals.filter(function(food){
        return food.mealId == 2;
      }).sort(function(a,b){
        return a.index - b.index;
      });


      if(lunchMeals.length > 0){
        $rootScope.lunch1 = lunchMeals[0].food;
        $rootScope.lunch2 = lunchMeals[1].food;
        $rootScope.lunch3 = lunchMeals[2].food;
      }

      var dinnerMeals = dailyMeals.filter(function(food){
        return food.mealId == 3;
      }).sort(function(a,b){
        return a.index - b.index;
      });


      if(lunchMeals.length > 0){
        $rootScope.dinner1 = dinnerMeals[0].food;
        $rootScope.dinner2 = dinnerMeals[1].food;
        $rootScope.dinner3 = dinnerMeals[2].food;
      }



      //$scope.getBmrValue();


      vm.selectedFoodBfast1 = "";
      vm.selectedFoodBfast2 = "";
      vm.selectedFoodBfast3 = "";
      vm.selectedFoodLunch1 = "";
      vm.selectedFoodLunch2 = "";
      vm.selectedFoodLunch3 = "";
      vm.selectedFoodDinner1 = "";
      vm.selectedFoodDinner2 = "";
      vm.selectedFoodDinner3 = "";

      console.log('setCalDate: dailyMeal',dailyMeals);
      console.log('selectDate',moment(date).valueOf());
      $scope.calendarDate[0].events[0].start = selectedDate;				    // update Calendar event dateFrom
      $scope.selectedDate = $filter('date')(selectedDate, 'yyyy-MM-dd');		// update $scope.dateFrom
      $scope.getTotalDietCal();
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
