angular.module('qreport')
.controller('new-user-modal', function($scope, $http, $httpParamSerializerJQLike){
    $scope.login = ''
    $scope.password = ''
    $scope.editUsers = false
    $scope.fullServerAccess = true
    $scope.permissions = [{server: "unknown", view: true, modify: true}]

    $scope.addPermission = function(){
        $scope.permissions.push({server: "new", view: true, modify: true})
    }

    $scope.removePermission = function(index){
        $scope.permissions.splice(index, 1)
        console.log(index)
    }

    $scope.addUser = function(){
        $http({
            method: 'POST',
            url: 'admin/users/add',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            data: $httpParamSerializerJQLike({
                login: $scope.login,
                password: $scope.password,
                editUsers: $scope.editUsers,
                fullServerAccess: $scope.fullServerAccess,
                serverPermissions: JSON.stringify($scope.permissions)
            })
        }).then(function successCallback(){
            $scope.login = ''
            $scope.password = ''
            $scope.$parent.loadUsers()
        })
    }
})