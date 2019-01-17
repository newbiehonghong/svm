'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
    mode: '"development"',
	NODE_ENV: '"development"',
	HOST: 'localhost',
	PORT: 8080,
	BASE_API: '"http://localhost:8088/svm"'
})
