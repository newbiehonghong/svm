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
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload">
                        <img v-if="imageUrl" :src="imageUrl" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过2M</div>
                    </el-upload>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit('userForm')">提交</el-button>
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
        name: 'upload',
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
                return apiURL + "/user/upload";
            },
            uploadHeaders() {
                let headers = {};
                addToken(headers);
                return headers;
            }
        },
        methods: {
            handleAvatarSuccess(res) {
                if(typeof res !== 'number') {
                    this.$message.error('文件上传失败');
                    return;
                }
                this.user.attachmentId = res;
                this.imageUrl = apiURL + "/user/download/" + res + "?" + getToken();
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
            onSubmit(formName) {
                saveUser(this.user).then((res) => {
                    this.$message.success('提交成功！');
                });
            }
        }
    }
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
