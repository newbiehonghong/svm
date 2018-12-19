<template>
    <div class="container">
        <div class="form-box">
            <el-form :model="user" ref="userForm" label-width="80px">
                <el-form-item label="姓名">
                    <el-input v-model="user.name"></el-input>
                </el-form-item>
                <el-form-item label="出生日期">
                    <el-date-picker type="date" placeholder="选择日期" v-model="user.birthday" value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
                <el-form-item label="照片">
                    <el-upload
                        class="avatar-uploader"
                        name="picture"
                        :action="uploadURL"
                        :headers="uploadHeaders"
                        :show-file-list="false"
                        :on-success="doAvatarSuccess"
                        :before-upload="beforeAvatarUpload">
                        <img v-if="imageUrl" :src="imageUrl" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过2M</div>
                    </el-upload>
                </el-form-item>
                <el-form-item>
                    <div class="sample-pic"></div>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="doSubmit('userForm')">提交</el-button>
                    <el-button>取消</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import { apiURL, addToken, getToken } from '@/utils/request';
    import { saveUser } from '@/api/sample';

    export default {
        name: 'Upload',
        data: function() {
            return {
                user: {
                    name: '',
                    birthday: '',
                    fileName: '',
                    attachmentId: ''
                },
                imageUrl: ''
            }
        },
        computed: {
            uploadURL() {
                return apiURL + "/sample/user/upload";
            },
            uploadHeaders() {
                let headers = {};
                addToken(headers);
                return headers;
            }
        },
        methods: {
            doAvatarSuccess(res) {
                if(typeof res !== 'number') {
                    this.$message.error('文件上传失败');
                    return;
                }
                this.user.attachmentId = res;
                this.imageUrl = apiURL + "/sample/user/download/" + res + "?" + getToken();
            },
            beforeAvatarUpload(file) {
                const isJPG = (file.type === 'image/jpeg' || file.type === 'image/png');
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('Avatar content must be JPG format!');
                }
                if (!isLt2M) {
                    this.$message.error('Avatar content size can not exceed 2MB!');
                }

                if(isJPG && isLt2M) {
                    this.user.fileName = file.name;
                    return true;
                }
                return false;
            },
            doSubmit(formName) {
                saveUser(this.user).then((res) => {
                    this.$message.success('提交成功！');
                });
            }
        }
    }
</script>

<style>
    .el-upload--text {
        width: 360px;
        height: 180px;
    }
    .sample-pic {
        width: 260px;
        height: 250px;
        background: url(~assets/img/user.jpg);
    }
</style>
