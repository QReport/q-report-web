angular.module('qreport')
.controller('dashboard', function($scope, $http, $location){
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