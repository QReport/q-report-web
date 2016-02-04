angular.module('qreport')
.controller('admin-users', function($scope, $http, AuthService){
    $scope.users = []
    $scope.canRemove = false

    $scope.init = function(){
        if(AuthService.getCurrentUser() != null){
            $scope.canRemove = AuthService.getCurrentUser().admin
        } else {
            AuthService.isLoggedIn().then(function(res) {
                AuthService.setCurrentUser(res.data.value)
                $scope.canRemove = AuthService.getCurrentUser().admin
            })
        }
    }

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

    $scope.removeUser = function(username){
        $http({
            url: 'admin/users/' + username,
            method: 'DELETE'
        }).then(function(res){
            $scope.loadUsers()
        })
    }

})