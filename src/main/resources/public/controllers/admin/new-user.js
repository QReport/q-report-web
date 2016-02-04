angular.module('qreport')
.controller('new-user-modal', function($scope, $http, $httpParamSerializerJQLike){
    $scope.availableLevels = ['MASTER', 'HELPER', 'GUEST']
    $scope.login = ''
    $scope.password = ''
    $scope.level = 'GUEST'
    $scope.fullServerAccess = true
    $scope.servers = [{text: 'unknown'}]

    $scope.addUser = function(){
        $http({
            method: 'POST',
            url: 'admin/users/add',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            data: $httpParamSerializerJQLike({
                login: $scope.login,
                password: $scope.password,
                level: $scope.level,
                fullServerAccess: $scope.fullServerAccess,
                servers: JSON.stringify($scope.servers.map(function(s){ return s.text}))
            })
        }).then(function successCallback(){
            $scope.login = ''
            $scope.password = ''
            $scope.$parent.loadUsers()
        })
    }
})