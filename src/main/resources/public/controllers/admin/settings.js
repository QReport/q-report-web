angular.module('qreport')
.controller('user-settings', function($scope, $http, $httpParamSerializerJQLike, AuthService){
    $scope.prevPassword = ''
    $scope.newPassword = ''

    $scope.changePassword = function(){
       $http({
            url: 'admin/users/update',
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            data: $httpParamSerializerJQLike({
                prevPassword: $scope.prevPassword,
                newPassword: $scope.newPassword
            })
        })
    }
})