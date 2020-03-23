<template>
    <div class="container">
        <div class="handle-box">
            <el-button @click="doCreate" type="primary">新增</el-button>
            <el-button @click="doDeleteMulti">删除</el-button>
        </div>
        <el-table :data="data" ref="userTable" @selection-change="doSelectionChange" stripe>
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="birthday" label="出生日期" sortable width="200"></el-table-column>
            <el-table-column prop="name" label="姓名" width="auto"></el-table-column>
            <el-table-column prop="province" label="省" :formatter="decode" width="200"></el-table-column>
            <el-table-column label="操作" width="140">
                <template slot-scope="scope">
                    <el-tooltip content="编辑" placement="bottom">
                        <el-button size="text" icon="el-icon-edit" @click="doEdit(scope)"></el-button>
                    </el-tooltip>
                    <el-tooltip content="删除" placement="bottom">
                        <el-button size="text" icon="el-icon-delete" @click="doDelete(scope)"></el-button>
                    </el-tooltip>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-pagination
                :page-size="pageSize"
                :total="totalRecords"
                layout="prev, pager, next"
                @current-change="doCurrentChange"
            ></el-pagination>
        </div>
    </div>
</template>

<script >
import dict from "@/dict/sample";
import { queryAllUsers, deleteUser } from "@/api/sample/user";

export default {
	name: "UserList",
	data() {
		return {
			data: [],
			multipleSelection: [],
			currentPage: 1,
			pageSize: 10,
			totalRecords: 0
		};
	},
	props: {
		params: { type: Object }
	},
	watch: {
		params(value) {
			if (value) {
				this.refreshRow(value);
			}
		}
	},
	created() {
		this.getData();
	},
	methods: {
		doCurrentChange(val) {
			this.currentPage = val;
			this.getData();
		},
		getData() {
			queryAllUsers(this.currentPage).then(res => {
				this.data = res.list;
				!this.totalRecords && (this.totalRecords = res.total); //只有第一次赋值
			});
		},
		decode(row, column) {
			return dict.decode("sample_" + column.property, row[column.property]);
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
			this.$emit("forward", "edit", "新增规则");
		},
		doEdit({ $index, row }) {
			var params = { row: Object.assign({}, row) };
			this.$emit("forward", "edit", "编辑规则", params);
		},
		doDeleteMulti() {
			const count = this.multipleSelection.length;
			if (count == 0) {
				this.$alert("请选择要删除的记录！", "提示", {
					confirmButtonText: "确定"
				});
				return;
			}

			this.$confirm("删除不可恢复，是否确定删除？", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning"
			}).then(() => {
				let ids = "";
				for (let i = 0; i < count; i++) {
					if (i > 0) {
						ids += ",";
					}
					ids += this.multipleSelection[i].id;
				}

				deleteUser(ids).then(res => {
					for (let i = count - 1; i >= 0; i--) {
						this.data.splice(i, 1);
					}
					this.multipleSelection = [];
					this.$refs.userTable.clearSelection();
				});
			});
		},
		doDelete({ $index, row }) {
			this.$confirm("删除不可恢复，是否确定要删除？", "警告", {
				type: "warning"
			}).then(async () => {
				//await deleteUser(row.id);
				this.data.splice($index, 1);
			});
		},
		doSelectionChange(val) {
			this.multipleSelection = val;
		},
		doOpenTab(index, row) {
			//按path路由时Tags里没法区分相同组件
			this.$router.push({ name: "BaseForm", params: { id: row.id } });
		}
	}
};
</script>