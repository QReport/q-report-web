var app = angular.module('qreport', ['ngRoute'])

app.run(function($rootScope){
    $rootScope.formatTimestampRelative = function(millis){
        return moment(millis).fromNow()
    }
})

app.config(function($routeProvider){
    $routeProvider
    .when("/auth", {
        templateUrl: 'auth.html'
    })
    .when("/dashboard", {
        templateUrl: 'dashboard.html'
    })
    .when("/ticket/:id", {
        templateUrl: 'ticket.html'
    })
    .otherwise({
        redirectTo: '/auth'
    })
})

app.controller('auth', function($scope, $http, $httpParamSerializerJQLike, $location){
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

app.controller('dashboard', function($scope, $http, $location){
    $scope.tickets = []

    $scope.formatTimestampRelative = function(millis){
        return moment(millis).fromNow()
    }

    $scope.formatTimestamp = function(millis, format){
        return moment(millis).format(format)
    }

    $scope.shortenText = function(text, limiter, ellipsis){
        if(text.length >= limiter){
            return text.substring(0, limiter) + (ellipsis ? "..." : "")
        } else {
            return text
        }
    }

    $scope.loadTickets = function(){
        $http({
           method: 'GET',
           url: '/admin/reports'
        }).then(function successCallback(response){
            $scope.tickets = response.data.value
            $scope.tickets.sort(function(t0, t1){
                return t1.messages[0].timestamp - t0.messages[0].timestamp
            })
            console.log(response)
        }, function errorCallback(response){
            if(response.status == 401 || response.status == 404){
                $location.path('/auth')
            }
        })
    }
})

app.controller('ticket', function($scope, $http, $location, $routeParams, $httpParamSerializerJQLike){
    var ticketId = $routeParams.id
    $scope.newMessage = ''
    $scope.availableStatuses = ['OPEN', 'CLOSED', 'IN_PROGRESS']
    $scope.selectedStatus = ''


    $scope.loadTicketData = function(){
        $http({
           method: 'GET',
           url: '/admin/reports/' + ticketId
        }).then(function successCallback(response){
            $scope.ticket = response.data.value
            $scope.selectedStatus = response.data.value.status
            console.log(response)
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