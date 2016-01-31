angular.module('qreport')
.controller('navigation', function($scope, $http, $location, AuthService){
    $scope.logout = function(){
        AuthService.logout().then(function(res){
            $location.path('auth')
        })
    }
})