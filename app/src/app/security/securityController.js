/**
 * Created by Asus on 7/8/2559.
 */
(function () {

  angular.module('app')
    .controller('ModalLoginController',ModalLoginController )
    .controller('ModalLoginInstanceController',ModalLoginInstanceController );

  function serializeData(data) {
    //if this not an object
    if(!angular.isObject(data)){
      return((data == null)?"": data.toString());
    }
    var buffer = [];
    //Serializ each key in the obj
    for (var name in data){
      if(!data.hasOwnProperty(name)){
        continue;
      }
      var value =  data[name];
      buffer.push(
        encodeURIComponent(name)+"="+encodeURIComponent((value == null)?"": value)
      );
    }
    //
    var source = buffer.join("&").replace(/%20/g,"+");
    return(source);
  }
  /**ngInject*/
  // function LoginController($rootScope,$location,$cookies,UserService)
  // {
  //
  //   var vm = this;
  //   vm.rememberMe = false;
  //   vm.login = function () {
  //
  //     UserService.authenticate(serializeData({username:vm.username,password:vm.password}),
  //       function (authenticationResult) {
  //         var authToken = authenticationResult.token;
  //         $rootScope.authToken = authToken;
  //         if (vm.rememberMe) {
  //           $cookies.put('authToken', authToken);
  //         }
  //         UserService.get(function (user) {
  //           $rootScope.user = user;
  //           $location.path("/")
  //         })
  //         //delete $rootScope.error;
  //       },
  //       function(error){
  //         if (error.status == "401"){
  //           $rootScope.error =" user name or passoword is not correct";
  //         }
  //       })
  //   }
  // }
    function ModalLoginController( $uibModal, $log,$rootScope,$location,homeService,securityService) {

    var vm = this;
    vm.animationsEnabled = true;
    vm.rememberMe = false;
    vm.lofinFail =false;
    vm.loginMessage = "";
    vm.open = function (size) {

      var modalInstance = $uibModal.open({
        animation: vm.animationsEnabled,
        templateUrl: 'sign_in.html',
        controller: 'ModalLoginInstanceController',
        controllerAs: 'vm',
        size: size,
        resolve: {
          lofinFail: function () {
            return vm.lofinFail;
          },
          loginMessage: function () {
            return vm.loginMessage;
          }
        }
      });

      modalInstance.result.then(function (data) {

       // vm.rememberMe = data.rememberMe;
        securityService.authenticate(serializeData({username:data.username,password:data.password}),
          function (authenticationResult) {
            var authToken = authenticationResult.token;
            $rootScope.authToken = authToken;
            console.log('auth',authToken)
            /*if (vm.rememberMe) {
              $cookies.put('authToken', authToken, {expires:moment().add(5,'days').toString()});
            }*/
            securityService.get(function (user) {


              $rootScope.user = user;
              console.log('user from secure', $rootScope.user)
                if(data.username=="admin") {
                $location.path('/manage')
              }else{
                $location.path('/')
              }
            });
            //delete $rootScope.error;
          },
          function(){
            vm.lofinFail = true;
              vm.loginMessage =" Username or password is not correct";
            vm.open();
          });
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };

    vm.toggleAnimation = function () {
      vm.animationsEnabled = !vm.animationsEnabled;
    };

  }

// Please note that $uibModalInstance represents a modal window (instance) dependency.
// It is not the same as the $uibModal service used above.

    function ModalLoginInstanceController($uibModalInstance,$location,lofinFail,loginMessage) {
      var vm = this;
      vm.lofinFaild = lofinFail;
      vm.loginMessage = loginMessage;
      vm.ok = function () {

        $uibModalInstance.close({username:vm.username,password:vm.password,rememberMe:vm.rememberMe});

      };

      vm.cancel = function () {
      $uibModalInstance.dismiss('cancel');
        $location.path('/')
    };
  }

})();
