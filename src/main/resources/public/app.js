var app = angular.module('qreport', ['ngRoute', 'chart.js'])


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
    .when("/admin", {
        templateUrl: 'admin/admin-global.html'
    })
    .when("/admin/users", {
        templateUrl: 'admin/admin-users.html'
    })
    .when("/admin/stats", {
        templateUrl: 'admin/admin-stats.html'
    })
    .otherwise({
        redirectTo: '/auth'
    })
})
