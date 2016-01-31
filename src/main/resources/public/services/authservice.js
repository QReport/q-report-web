angular.module('qreport')
.factory('AuthService', function($http, $httpParamSerializerJQLike){
    var currentUser;

    return {
        login: function(login, password, onLogin, onError) {
            $http({
               url: 'auth',
               method: 'POST',
               headers: {'Content-Type': 'application/x-www-form-urlencoded'},
               data: $httpParamSerializerJQLike({
                    login: login,
                    password: password
               })
            }).then(function(response){
               console.log(response)
               if(response.data.ok){
                    currentUser = response.data.value
                    if(onLogin) onLogin()
               } else {
                    if(onError) onError()
               }
            })
        },
        logout: function(){
            return $http.post('/logout')
        }
        ,
        isLoggedIn: function(){
            return $http.get('/isLoggedIn')
        }
        ,
        getCurrentUser: function(){
            return currentUser
        }

    }
})