<template>
    <div class="container">
        <div class="form-box form1">
            <el-form label-width="80px">
                <el-form-item label="prepend">
                    <el-input placeholder="用户名">
                        <span slot="prepend" class="icon-user"></span>
                    </el-input>
                </el-form-item>
                <el-form-item label="dropdown">
                    <el-dropdown>
                        <span>管理员</span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item>退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'SampleTest',
        data: function() {
            return {};
        }
    }
</script>

<style>
    .form1 {
        font-size: 22px;
    }

    .el-input-group__prepend {
        padding: 0 10px;
    }

    .el-dropdown-menu__item {
        color: red;
    }
    .icon-user {
        font-size: 20px;
    }
</style>