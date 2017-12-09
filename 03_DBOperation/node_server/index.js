var express = require('express')
var mysql = require('mysql')
var app = express()

var connInfo = {
  host     : '',
  user     : '',
  password : '',
  database : ''
}

app.get('/', function(req, res) {
	var ret = {
		name: "hello world"
	}
  res.status(200).send(ret)
  res.end()
})

app.get('/createUser', function (req, res) {	
	var username = req.query.username
	var password = req.query.password
	var realname = req.query.realname
	var ret = {}

	if (username && password && realname) {
		var connection = mysql.createConnection(connInfo)
		connection.connect()

		var sql = "INSERT INTO user (username, password, realname) VALUES ('" + username + "','" + password + "','" + realname + "')"
		connection.query(sql, function (error, results) {
			if (error) {
				ret.status = 'ERROR'
				ret.description = error.code
				res.status(500).send(ret)
				res.end()
			} else {
				console.log(results)
				if (results.affectedRows > 0) {
					ret.status = 'OK'
				} else {
					ret.status = 'FAIL'
				}
				res.status(200).send(ret)
				res.end()
			}
			connection.end()
		})
	} else {
		ret.status = 'ERROR'
		ret.description = 'parameter not enough'
		res.status(400).send(ret)
		res.end()
	}
})

app.get('/deleteUser', function (req, res) {
	var username = req.query.username
	var ret = {}

	if (username) {
		var connection = mysql.createConnection(connInfo)
		connection.connect()

		var sql = "DELETE FROM user WHERE username='" + username + "'"
		connection.query(sql, function (error, results) {

			if (error) {
				ret.status = 'ERROR'
				ret.description = error.code
				res.status(500).send(ret)
				res.end()
			} else {
				if (results.affectedRows > 0) {
					ret.status = 'OK'
				} else {
					ret.status = 'FAIL'
				}
				res.status(200).send(ret)
				res.end()
			}
			connection.end()
		})
	} else {
		ret.status = 'ERROR'
		ret.description = 'parameter not enough'
		res.status(400).send(ret)
		res.end()
	}

})

app.get('/updateUser', function (req, res) {
	var username = req.query.username
	var realname = req.query.realname
	var ret = {}

	if (username && realname) {
		var connection = mysql.createConnection(connInfo)
		connection.connect()

		var sql = "UPDATE user SET realname='" + realname + "' WHERE username='" + username + "'"
		connection.query(sql, function (error, results) {
			if (error) {
				ret.status = 'ERROR'
				ret.description = error.code
				res.status(500).send(ret)
				res.end()
			} else {
				if (results.affectedRows > 0) {
					ret.status = 'OK'
				} else {
					ret.status = 'FAIL'
				}
				res.status(200).send(ret)
				res.end()
			}
			connection.end()
		})
	} else {
		ret.status = 'ERROR'
		ret.description = 'parameter not enough'
		res.status(400).send(ret)
		res.end()
	}

})

app.get('/login', function (req, res) {
	var username = req.query.username
	var password = req.query.password
	var ret = {}

	if (username && password) {
		var connection = mysql.createConnection(connInfo)
		connection.connect()

		var sql = "SELECT * FROM user WHERE username='" + username + "' AND password='" + password + "'"
		connection.query(sql, function (error, results) {
			if (error) {
				ret.status = 'ERROR'
				ret.description = error.code
				res.status(500).send(ret)
				res.end()
			} else {
				if (results.length > 0) {
					ret.status = 'OK'
					ret.data = results[0]
				} else {
					ret.status = 'FAIL'
					ret.data = {}
				}
				res.status(200).send(ret)
				res.end()
			}
			connection.end()
		})
	} else {
		ret.status = 'ERROR'
		ret.description = 'parameter not enough'
		res.status(400).send(ret)
		res.end()
	}

})

app.get('/userList', function (req, res) {
	var ret = {}
	var connection = mysql.createConnection(connInfo)
	connection.connect()

	var sql = "SELECT * FROM user"
	connection.query(sql, function (error, results) {
		if (error) {
			ret.status = 'ERROR'
			ret.description = error.code
			res.status(500).send(ret)
			res.end()
		} else {
			if (results.length >= 0) {
				ret.status = 'OK'
				ret.data = results
			} else {
				ret.status = 'FAIL'
				ret.data = []
			}
			res.status(200).send(ret)
			res.end()
		}
		connection.end()
	})

})

app.listen(3000)