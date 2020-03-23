<template>
    <div class="container">
        <el-row class="header-box">
            <el-col :span="12">
                <el-input
                    v-model="filters.name"
                    placeholder="输入姓名过滤"
                    prefix-icon="el-icon-search"
                    style="width:200px"
                ></el-input>
            </el-col>
            <el-col :span="12" class="right">
                <el-button @click="doCreate" type="primary" icon="el-icon-edit">新增</el-button>
            </el-col>
        </el-row>

        <el-table :data="rows" stripe>
            <el-table-column prop="name" label="账号" width="200"></el-table-column>
            <el-table-column prop="nickname" label="姓名" width="auto"></el-table-column>
            <el-table-column label="操作" width="260">
                <template slot-scope="scope">
                    <el-tooltip content="分配角色" placement="bottom">
                        <el-button size="text" icon="icon-group" @click="doRole(scope)"></el-button>
                    </el-tooltip>
                    <el-tooltip content="编辑" placement="bottom">
                        <el-button size="text" icon="el-icon-edit" @click="doEdit(scope)"></el-button>
                    </el-tooltip>
                    <el-tooltip content="删除" placement="bottom">
                        <el-button size="text" icon="el-icon-delete" @click="doDelete(scope)"></el-button>
                    </el-tooltip>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
import { queryAllUsers, deleteUser } from "@/api/security/user";
export default {
	data() {
		return {
			data: [],
			filters: {
				name: ""
			}
		};
	},
	props: {
		params: { type: Object }
	},
	computed: {
		rows: function() {
			if (this.filters.name == "") {
				return this.data;
			}
			return this.data.filter(item => {
				return item.nickname.indexOf(this.filters.name) == 0;
			});
		}
	},
	watch: {
		params(value) {
			if (value) {
				this.refreshRow(value);
			}
		}
	},
	created() {
		this.init();
	},
	methods: {
		init() {
			queryAllUsers().then(res => {
				this.data = res;
			});
		},
		refreshRow({ row, added }) {
			if (added) {
				this.data.unshift(row);
			} else {
				for (let i = 0, end = this.data.length; i < end; i++) {
					if (row.id == this.data[i].id) {
						this.$set(this.data, i, row);
						break;
					}
				}
			}
		},
		doCreate() {
			this.$emit("forward", "edit", "新增用户");
		},
		doEdit({ $index, row }) {
			var params = { row: Object.assign({}, row) };
			this.$emit("forward", "edit", "编辑用户", params);
		},
		doDelete({ $index, row }) {
			this.$confirm("删除不可恢复，是否确定删除？", "警告", {
				type: "warning"
			}).then(() => {
				deleteUser(row.id).then(res => {
					this.data.splice($index, 1);
				});
			});
		},
		doRole({ $index, row }) {
			var params = { id: row.id };
			this.$emit("forward", "role", "分配角色", params);
		}
	}
};
</script>
