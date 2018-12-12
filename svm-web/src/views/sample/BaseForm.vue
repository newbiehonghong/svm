<template>
    <div class="container">
        <div class="form-box">
            <el-form :model="user" ref="userForm" :rules="rules" label-width="80px">
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="user.name"></el-input>
                </el-form-item>
                <el-form-item label="出生日期" prop="birthday">
                    <el-date-picker type="date" placeholder="选择日期" v-model="user.birthday" value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
                <el-form-item label="省" prop="province">
                    <el-select v-model="user.province" clearable placeholder="请选择">
                        <el-option v-for="item in sample_province" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="市">
                    <el-select v-model="user.city" placeholder="请选择">
                        <el-option v-for="item in sample_city" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="薪资" prop="salary">
                    <el-input v-model.number="user.salary"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-radio-group v-model="user.gender">
                        <el-radio label="1">男</el-radio>
                        <el-radio label="0">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input type="textarea" rows="5" v-model="user.memo"></el-input>
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
    import dict from "@/dict/sample";
    import { saveUser } from '@/api/sample';

    export default {
        name: 'BaseForm', //keep-alive要求组件必须有name
        data: function() {
            return {
                user: {
                    name: '',
                    birthday: '',
                    province: '1',
                    city: '1',
                    salary: '',
                    gender: '1',
                    memo: ''
                },
                sample_province: dict.sample_province,
                sample_city: dict.sample_city,
                rules: {
                    name: [
                        { required: true, message: '请输入姓名', trigger: 'blur' },
                        { max: 10, message: '最多不能超过10个字符', trigger: 'blur' }
                    ],
                    province: [
                        { required: true, message: '请选择省', trigger: 'change' }
                    ],
                    birthday: [
                        { required: true, message: '请选择出生日期', trigger: 'change' }//如果加了【type: 'date'】会报错，取到的是string，但是会当场date来用所以出错
                    ],
                    salary: [
                        { type: 'number', message: '薪资必须为数字'}
                    ]
                }
            }
        },
        methods: {
            onSubmit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (!valid) {
                        return false;
                    }

                    saveUser(this.user).then((res) => {
                        this.$message.success('提交成功！');
                    });
                });
            }
        }
    }
</script>