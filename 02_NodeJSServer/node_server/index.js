var express = require('express');
var app = express();

app.get('/', function(req, res) {
	var ret = {
		name: "hello world"
	}
  res.status(200).send(ret);
});

app.get('/test', function (req, res) {	
	var user = req.query.user;
	var ret = {
		loginUser: user
	}
	res.status(200).send(ret)
})

app.get('/users', function (req, res) {
	var user1 = {
		name: 'Peter',
		password: '12345'
	}
	var user2 = {
		name: 'John',
		password: '12345'
	}
	var user3 = {
		name: 'Paul',
		password: '12345'
	}

	var data = [
		user1, user2, user3
	]

	var ret = {
		userList: data
	}

	res.status(200).send(ret)

})
console.log('Start Server...')
app.listen(3000);