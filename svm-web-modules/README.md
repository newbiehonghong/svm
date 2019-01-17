## 基于VUE的一个分模块开发的示例
	
svm-web-stater是框架工程，svm-web-sample和svm-web-security是两个独立模块。

两个模块升级时发布到npm仓库，框架工程可以更新到新版本。

这样不利于调试，每个模块都应该有一个用于测试的框架，类似于element-ui里的examples,
但是一个开发人员要同时开发多个模块时还是比较麻烦。
