/**
 * Created by Asus on 6/8/2559.
 **/
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
    $rootScope.budget = null;
    $rootScope.net = 0;


    vm.activity = 0;

    $scope.currentuser = $rootScope.currentuser;



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
     $rootScope.budget = result.data[0];

     });

    $scope.requestAc = function(){
      /*$http({
       method: 'GET',
       headers: {Authorization: 'Bearer https://runkeeper.com/apps/token',
       Accept: 'application/vnd.com.runkeeper.FitnessActivityFeed+json'},
       uri: 'http://api.runkeeper.com/fitnessActivities'
       })
       .then(function (result) {

       });*/
      $rootScope.totalCalAct = $rootScope.calculateActivities();
      $rootScope.net = $rootScope.totalDietCal - $rootScope.totalCalAct;
      $log.debug('Net value',$rootScope.net);
      $rootScope.underCal = $rootScope.budget - $rootScope.net;

    };

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
    $log.debug('Role in used', vm.currentR);
    vm.selectedFoodBfast = null;
    vm.selectedFoodLunch = null;
    vm.selectedFoodDinner = null;
    vm.selectedDisease = null;
   // $rootScope.notEnoughFood=false;
    $rootScope.selectDisease=false;
    $rootScope.activities = null;
    $rootScope.totalCalAct = 0;

    /*Activity*/


    $rootScope.calculateActivities = function (){
      var activity = $rootScope.activities["items"];
      var total = 0;
      total = 0;
      for(var i=0;i<activity.length;i++){
        var cal = activity[i].total_calories;
        total += cal;
      }
    return total;
    };

    $scope.requestAccessToken = function (){
      var connectDiv = document.querySelector('#connectRun');
      var disConnectDiv = document.querySelector('#disConnectRun');
      $http({
        method: 'GET',
        url: 'http://localhost:8080/connectRunKeeper'
      }).then(function(result){
        $log.debug('Response access token', result.data);
        if(result.data == ""){
          $scope.requestAccessToken();
        }else{
          $rootScope.activities = result.data;
          $rootScope.calculateActivities();

          connectDiv.style.display = "none";
          disConnectDiv.style.display = "";
        }
      });
    };

    $scope.connectRunKeeper = function(isConnect){
     // window.location.replace("https://runkeeper.com/apps/authorize?redirect_uri=http%3A%2F%2Flocalhost%3A3000%2Fhome&scope=notifications&state=3(%230%2F!~&response_type=code&client_id=f18e3497b59c4329b683ed2bebe2d2cc");
      //window.location.replace("http://localhost:8080/login/runkeeper");
      var connectDiv = document.querySelector('#connectRun');
      var disConnectDiv = document.querySelector('#disConnectRun');

      if(isConnect) {
        $http.jsonp("http://localhost:8080/login/runkeeper")

        $scope.requestAccessToken();


      }else{
        disConnectDiv.style.display = "none";
        connectDiv.style.display = "";
        $rootScope.net = $rootScope.totalDietCal;
        $rootScope.totalCalAct = 0;

      }
    };
    /*DROPDOWN Food*/

    $scope.foods = [];
    $scope.caloriesMeal = { 'bFast': 0 , 'lunch':0, 'dinner':0};
    $scope.requestSetFoods = function(){
      if(vm.currentR=="member") {
        $http({
          method: 'GET',
          url: 'http://localhost:8080/getFoods',
          params: {
            name: $scope.currentuser
          }
        })
          .then(function (result) {
            $scope.foods = result.data;

            $scope.foodSetsBreakfast = $scope.foods.filter(function(food){
              return $scope.filterSetFoodsByCal($scope.caloriesMeal.bFast , food);
            });

            $scope.foodSetsLunch = $scope.foods.filter(function(food){
              return $scope.filterSetFoodsByCal($scope.caloriesMeal.lunch , food);
            });

            $scope.foodSetsDinner = $scope.foods.filter(function(food){
             return $scope.filterSetFoodsByCal($scope.caloriesMeal.dinner , food);
            });


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
            $scope.foodSetsBreakfast = $scope.foods.filter(function(food){
              return $scope.filterSetFoodsByCal($scope.caloriesMeal.bFast , food);
            });

            $scope.foodSetsLunch = $scope.foods.filter(function(food){
              return $scope.filterSetFoodsByCal($scope.caloriesMeal.lunch , food);
            });

            $scope.foodSetsDinner = $scope.foods.filter(function(food){
              return $scope.filterSetFoodsByCal($scope.caloriesMeal.dinner , food);
            });


            $log.debug('food ', result.data);

          });
      }
    };

    $scope.filterSetFoodsByCal = function(baseCal,food){
        if(baseCal == 0){
          return food;
        }else {
            var cal =  parseInt(food[1]);
            return  cal <= baseCal;
        }
    };


    $scope.setFood=function(setMenu,meal){
      //$log.debug(setMenu,meal,index);
      $scope.getTotalDietCal();
      $http({
        method: 'GET',
        url: 'http://localhost:8080/setFood',
        params: {
          set: setMenu,
          name: $scope.currentuser,
          meal: meal,
          date: $scope.selectedDate
        }
      }).then(function(result) {

        $log.debug('set Food'+result.data);
        $rootScope.requestPlan();


      }, function errorCallback(response) {
        $log.debug('Error set Food',response);
      });
    };


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
      }).then(function () {
        //$scope.diseasesName = result.data;
        $log.debug('set Disease');
        //console.log('set disease success', result);
        $rootScope.requestPlan();
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
          $rootScope.underCal = $rootScope.budget-$rootScope.totalDietCal;
          $rootScope.net = $rootScope.totalDietCal;
          $log.debug("Under calories", $rootScope.underCal);
        });

    };
    $rootScope.requestPlan = function(){
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
    };
    $scope.requestSetFoods();
    $rootScope.requestPlan();

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

              _mealModel[mealDatePlan.getTime()].push({'mealId':dailyMeals[j][1],'food':dailyMeals[j][3],'index': dailyMeals[j][2],'calories':dailyMeals[j][4]});



            }else{
              _mealModel[mealDatePlan.getTime()] = [{'mealId':dailyMeals[j][1],'food':dailyMeals[j][3],'index': dailyMeals[j][2],'calories':dailyMeals[j][4]}];
              var mealEvent = {
                title: dailyMeals[j][1],
                start: mealDatePlan,
                allDay: true,
                rendering: 'background',
                backgroundColor: '#f26522'
              };

              mealEvents.push(mealEvent);
            }
          }

        }


      }

      $scope.mealModel = _mealModel;
      console.log('mealModel',$scope.mealModel);
      return mealEvents;

    };

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

        var bfastCal = parseInt(bfastMeals[0].calories);
        $scope.caloriesMeal.bFast = bfastCal;

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

        var lunchCal = parseInt(lunchMeals[0].calories);
        $scope.caloriesMeal.lunch = lunchCal;

      }



      var dinnerMeals = dailyMeals.filter(function(food){
        return food.mealId == 3;
      }).sort(function(a,b){
        return a.index - b.index;
      });


      if(dinnerMeals.length > 0){
        $rootScope.dinner1 = dinnerMeals[0].food;
        $rootScope.dinner2 = dinnerMeals[1].food;
        $rootScope.dinner3 = dinnerMeals[2].food;

        var dinnerCal = parseInt(dinnerMeals[0].calories);
        $scope.caloriesMeal.dinner = dinnerCal;

      }



      //$scope.getBmrValue();


      vm.selectedFoodBfast = "";
      vm.selectedFoodLunch = "";
      vm.selectedFoodDinner = "";


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
        dayClick : $scope.setCalDate
        //background: '#f26522',
      }
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
