<template>
    <el-dialog title="修改密码" visible width="400px" :show-close="false">
        <el-form ref="pwdForm" :model="pwdObj" :rules="rules" :inline-message="true" label-position="left">
            <el-form-item label="当前密码" prop="oldPwd">
                <el-input type="password" v-model="pwdObj.oldPwd"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPwd">
                <el-input type="password" v-model="pwdObj.newPwd"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="newPwd2">
                <el-input type="password" v-model="pwdObj.newPwd2"></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer">
            <el-button type="primary" @click="doSavePwd">确 定</el-button>
            <el-button type="info" @click="closeDlg">取 消</el-button>
        </span>
    </el-dialog>
</template>
<script>
    import {changePwd} from "@/api/login";

    export default {
        data() {
            return {
                pwdObj: {
                    oldPwd: "",
                    newPwd: "",
                    newPwd2: ""
                },
                rules: {
                    oldPwd: [{required: true, message: "请输入当前密码", trigger: "blur"}],
                    newPwd: [{required: true, message: "请输入新密码", trigger: "blur"}],
                    newPwd2: [{required: true, message: "请输入确认密码", trigger: "blur"}]
                }
            };
        },
        computed: {
            username() {
                return this.$store.getters.nickname;
            }
        },
        methods: {
            doSavePwd() {
                this.$refs["pwdForm"].validate(valid => {
                    if (!valid) {
                        return false;
                    }

                    if (this.pwdObj.newPwd != this.pwdObj.newPwd2) {
                        this.$message({
                            message: "两次输入的新密码不一致",
                            type: "error"
                        });
                        return;
                    }

                    changePwd(this.username, this.pwdObj.oldPwd, this.pwdObj.newPwd).then(response => {
                        this.$message({
                            message: "密码修改成功",
                            type: "success"
                        });
                        this.closeDlg();
                    });
                });
            },
            closeDlg() {
                this.$emit("close");
            }
        }
    };
</script>
