angular.module('qreport')
.controller('admin-users', function($scope, $http){
    $scope.users = []

    $scope.loadUsers = function(){
        $http({
            url: 'admin/users',
            method: 'GET'
        }).then(function successCallback(response){
            if(response.status != 404 && response.data.ok){
                $scope.users = response.data.value
            }
        })
    }
})