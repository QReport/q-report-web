angular.module('qreport')
.controller('ticket', function($scope, $http, $location, $routeParams, $httpParamSerializerJQLike){
    $scope.error = false
    var ticketId = $routeParams.id
    $scope.newMessage = ''
    $scope.availableStatuses = ['OPEN', 'CLOSED', 'IN_PROGRESS']
    $scope.selectedStatus = ''


    $scope.loadTicketData = function(){
        $http({
           method: 'GET',
           url: '/admin/reports/' + ticketId
        }).then(function successCallback(response){
            if(response.data.ok){
                $scope.ticket = response.data.value
                $scope.selectedStatus = response.data.value.status
                console.log(response)
            } else {
                $scope.error = true
            }
        }, function errorCallback(response){
            if(response.status == 401 || response.status == 404){
                $location.path('/auth')
            }
        })
    }

    $scope.updateStatus = function(){
        $http({
            method: 'POST',
            url: 'admin/reports/' + ticketId + '/updateStatus',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            data: $httpParamSerializerJQLike({
                status: $scope.selectedStatus
            })
        }).then(function successCallback(response){
            if(response.data.ok){
                $scope.loadTicketData()
            }
        })
    }

    $scope.addMessage = function(){
        $http({
            method: 'POST',
            url: 'admin/reports/' + ticketId + '/addMessage',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            data: $httpParamSerializerJQLike({
                text: $scope.newMessage
            })
        }).then(function successCallback(response){
            if(response.data.ok){
                $scope.loadTicketData()
                $scope.newMessage = ''
            }
        }, function errorCallback(response){
            console.log(response)
        })
    }

})