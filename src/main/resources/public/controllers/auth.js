angular.module('qreport')
.controller('auth', function($scope, $http, $httpParamSerializerJQLike, $location, AuthService){
    $scope.error = ''
    $scope.login = ''
    $scope.password = ''

    $scope.checkAlreadyAuthorized = function(){
        AuthService.isLoggedIn().then(function(response){
            if(response.data.ok) $location.path('/dashboard')
        })
    }

    $scope.auth = function(){
        AuthService.login($scope.login, $scope.password, function(){
            $location.path('/dashboard')
        }, function(){
            $scope.error = "Error! Invalid login or password"
        })
    }
})