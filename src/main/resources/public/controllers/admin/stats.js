var ticketChartTemplate = "<ul class=\"<%=name.toLowerCase()%>-legend\">" +
                                                          "<% for (var i=0; i<segments.length; i++){%>" +
                                                                  "<li>" +
                                                                      "<span style=\"color:<%=segments[i].fillColor%>\">" +
                                                                              "<%if(segments[i].label){%><%=segments[i].label%><%}%>" +
                                                                          "</li>" +
                                                                      "</span><%}%>" +
                                                                  "</ul>"


angular.module('qreport')
.controller('admin-stats', function($scope, $http){
    $scope.ticketsChartData = []
    $scope.ticketsChartLabels = []
    $scope.ticketsChartOptions = {
        legendTemplate: ticketChartTemplate
    }

    $scope.activeUsersData = [[]]
    $scope.activeUsersLabels = []
    $scope.activeUsersSeries = [['Most active']]

    $scope.loadStatistics = function(){
        $http({
            method: 'GET',
            url: 'admin/stats'
        }).then(function successCallback(response){
                if(response.data.ok){
                    //fetch pie chart
                    $scope.ticketsChartData = []
                    $scope.ticketsChartLabels = []
                    var tickets = response.data.value.tickets
                    for(var key in tickets){
                        $scope.ticketsChartLabels.push(key)
                        $scope.ticketsChartData.push(tickets[key])
                    }

                    //fetch bar graph
                    $scope.activeUsersData = [[]]
                    $scope.activeUsersLabels = []
                    var users = response.data.value.users
                    for(var key in users){
                        $scope.activeUsersData[0].push(users[key])
                        $scope.activeUsersLabels.push(key)
                    }
                }
            }
        )
    }
})