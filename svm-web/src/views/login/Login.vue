<template>
    <div class="login-wrap">
        <div class="login-title">SVM前台交互应用示例</div>
        <div class="login-panel">
            <el-form :model="loginInfo" :rules="rules" ref="loginForm">
                <el-form-item prop="username">
                    <el-input v-model="loginInfo.username" placeholder="用户名" v-focus @keyup.enter.native="changeFocus">
                        <span slot="prepend" class="icon-user"></span>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" v-model="loginInfo.password" ref="password" placeholder="密码" @keyup.enter.native="doLogin">
                        <span slot="prepend" class="icon-lock"></span>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click.native.prevent="doLogin">登录</el-button>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
    import { Message } from 'element-ui';
    import { login, logout } from '@/api/login';
    import { asyncRouterMap, filterAsyncRouter } from '@/router';

    export default {
        data: function() {
            return {
                loginInfo: {
                    username: '',
                    password: ''
                },
                rules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            changeFocus() {
                this.$refs.password.focus();
            },
            doLogin() {
                this.$refs.loginForm.validate(valid => {
                    if (!valid) {
                        return false;
                    }

                    login(this.loginInfo.username, this.loginInfo.password).then(response => {
                        var data = response.data;
                        this.$store.commit('SET_TOKEN', data.token);
                        this.$store.commit('SET_NICKNAME', data.nickname);
                        let routes = filterAsyncRouter(asyncRouterMap, data.permissions);
                        this.$store.commit('SET_ROUTES', routes);//Sidebar.vue里无法从this.$router上取路由数据
                        this.$router.addRoutes(routes);
                        this.$router.push('/');
                    }).catch((err) => {
                        Message.error(err || '验证失败，请重新登录');
                        return false;
                    });
                });
            }
        }
    }
</script>

<style>
    .login-wrap {
        position: relative;
        width: 100%;
        height: 100%;
    }
    .login-wrap .login-title {
        position: absolute;
        top: 50%;
        width: 100%;
        margin-top: -230px;
        text-align: center;
        font-size: 30px;
        color: #fff;

    }
    .login-wrap .login-panel {
        position: absolute;
        left: 50%;
        top: 50%;
        width: 300px;
        height: 160px;
        margin:-150px 0 0 -190px;
        padding: 40px;
        border-radius: 5px;
        background-color: rgba(255,255,255, 0.3);
    }
    .login-wrap .login-btn {
        text-align: center;
    }
    .login-wrap .login-btn button{
        width:100%;
        height:36px;
    }
    .login-wrap .el-input-group__prepend {
        padding: 0 10px;
    }
    .login-wrap .icon-user,
    .login-wrap .icon-lock {
        font-size: 20px;
    }
</style>