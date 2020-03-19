<template>
    <div class="container">
        <el-form ref="userForm" :model="row" label-width="80px">
            <el-form-item label="出生日期">
                <el-date-picker
                    type="date"
                    placeholder="选择日期"
                    v-model="row.birthday"
                    value-format="yyyy-MM-dd"
                ></el-date-picker>
            </el-form-item>
            <el-form-item label="姓名">
                <el-input v-model="row.name"></el-input>
            </el-form-item>
            <el-form-item label="省">
                <el-select v-model="row.province" clearable placeholder="请选择">
                    <el-option
                        v-for="item in cache_province"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    ></el-option>
                </el-select>
            </el-form-item>

            <div class="footer-box">
                <el-button type="primary" @click="doSave">提 交</el-button>
                <el-button @click="goBack">返 回</el-button>
            </div>
        </el-form>
    </div>
</template>
<script>
import dict from "@/dict/sample";
import { saveUser, updateUser } from "@/api/sample/user";

export default {
	data() {
		return {
			row: {},
			added: false,
			cache_province: dict.sample_province
		};
	},
	props: {
		params: { type: Object }
	},
	created() {
		this.init();
	},
	methods: {
		init() {
			if (this.params && this.params.row) {
				this.row = this.params.row;
			} else {
				this.added = true;
				this.resetRow();
			}
		},
		resetRow() {
			this.row = {
				name: "",
				birthday: "",
				province: "1",
				city: "1",
				salary: "",
				gender: "1",
				memo: ""
			};
		},
		doSave() {
			this.$refs.userForm.validate(valid => {
				if (!valid) {
					return false;
				}

				if (this.added) {
					saveUser(this.row).then(res => {
						this.$message({ message: "保存成功", type: "success" });
						this.goBack(res, true);
					});
				} else {
					updateUser(this.row).then(res => {
						this.$message({ message: "保存成功", type: "success" });
						this.goBack(res, false);
					});
				}
			});
		},
		goBack(row, added) {
			this.$emit("backward", "list", { row, added });
		}
	}
};
</script>