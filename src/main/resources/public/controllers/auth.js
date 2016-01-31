angular.module('qreport')
.controller('auth', function($scope, $http, $httpParamSerializerJQLike, $location){
    $scope.error = ''
    $scope.login = ''
    $scope.password = ''
    $scope.auth = function() {
        $http({
            url: 'auth',
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            data: $httpParamSerializerJQLike({
                login: $scope.login,
                password: $scope.password
            })
        }).then(function(response){
            if(response.data.ok){
                $location.path('/dashboard')
            } else {
                $scope.error = response.data.error
            }
            console.log(response)
        })
    }
})