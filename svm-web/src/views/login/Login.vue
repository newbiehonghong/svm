<template>
    <div class="login-wrap">
        <div class="ms-title">后台管理系统</div>
        <div class="ms-login">
            <el-form :model="loginInfo" :rules="rules" ref="loginForm" label-width="0px" class="demo-loginForm">
                <el-form-item prop="username">
                    <el-input v-model="loginInfo.username" placeholder="username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" placeholder="password" v-model="loginInfo.password" @keyup.enter.native="handleLogin"></el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click.native.prevent="handleLogin">登录</el-button>
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
                        { required: true, message: '请输入用户名', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            handleLogin() {
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
                    })
                    .catch((err) => {
                        Message.error(err || '验证失败，请重新登录');
                        return false;
                    });
                });
            }
        }
    }
</script>

<style scoped>
    .login-wrap{
        position: relative;
        width:100%;
        height:100%;
    }
    .ms-title{
        position: absolute;
        top:50%;
        width:100%;
        margin-top: -230px;
        text-align: center;
        font-size:30px;
        color: #fff;

    }
    .ms-login{
        position: absolute;
        left:50%;
        top:50%;
        width:300px;
        height:160px;
        margin:-150px 0 0 -190px;
        padding:40px;
        border-radius: 5px;
        background: #fff;
    }
    .login-btn{
        text-align: center;
    }
    .login-btn button{
        width:100%;
        height:36px;
    }
</style>