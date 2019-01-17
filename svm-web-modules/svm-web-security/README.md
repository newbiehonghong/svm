## 用vue写的一个组件示例工程，主要目的是学习vue项目分模块开发
	
## 使用

1. npm install --save svm-web-security
2. 在Vue项目里的main.js需要设置一个全局的ajax对象，模块里用它来请求后台数据
```
Vue.prototype.$api = _request;
//_request中包含一个request对象，是一个axios实例
```