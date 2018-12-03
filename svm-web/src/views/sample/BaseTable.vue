<template>
    <div>
        <div class="container">
            <div class="handle-box">
                <el-button class="button" @click="handleCreate" type="primary" icon="el-icon-edit">新增</el-button>
                <el-button class="button" @click="handleDelete" icon="el-icon-delete">删除</el-button>
            </div>
            <el-table :data="data" border style="width: 100%" ref="userTable" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column prop="birthday" label="出生日期" sortable width="150"></el-table-column>
                <el-table-column prop="name" label="姓名" width="150"></el-table-column>
                <el-table-column prop="province" label="省" :formatter="decodeProvince" width="120"></el-table-column>
                <el-table-column label="操作" width="80">
                    <template slot-scope="scope">
                        <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination 
                    :page-size="pageSize" 
                    :total="totalRecords" 
                    layout="prev, pager, next" 
                    @current-change="handleCurrentChange">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
            <el-form ref="userForm" :model="currentRow" label-width="80px">
                <el-form-item label="出生日期">
                    <el-date-picker type="date" placeholder="选择日期" v-model="currentRow.birthday" value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="currentRow.name"></el-input>
                </el-form-item>
                <el-form-item label="省">
                    <el-select v-model="currentRow.province" clearable placeholder="请选择">
                        <el-option v-for="item in sample_province" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="saveEdit">确 定</el-button>
                <el-button @click="editVisible = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import dict from "@/dict";
    import { queryAllUsers, saveUser, updateUser, deleteUser } from '@/api/sample';

    export default {
        name: 'BaseTable',
        data() {
            return {
                data: [],
                currentPage: 1,
                currentRow: {},
                currentRowIndex: -1,
                multipleSelection: [],
                pageSize: 10,
                totalRecords: 0,
                editVisible: false,
                sample_province: dict.sample_province
            }
        },
        created() {
            this.getData();
        },
        methods: {
            handleCurrentChange(val) {
                this.currentPage = val;
                this.getData();
            },
            getData() {
                queryAllUsers(this.currentPage).then((res) => {
                    this.data = res.data.list;
                    this.totalRecords = res.data.pages * this.pageSize;
                })
            },
            decodeProvince(row, column) {
                var value = row.province;
                var item = this.sample_province.find(element => element.value == value);
                return item && item.label || value;
            },
            resetCurrentRow() {
                this.currentRow = {
                    name: null,
                    birthday: null,
                    province: '1',
                    city: '1',
                    salary: null,
                    gender: '1',
                    memo: null
                };
            },
            handleCreate() {
                this.resetCurrentRow();
                this.editVisible = true;
            },
            handleEdit(index, row) {
                this.currentRowIndex = index;
                this.currentRow = Object.assign({}, row);
                this.editVisible = true;
            },
            handleDelete(index, row) {
                const count = this.multipleSelection.length;
                if(count == 0) {
                    this.$alert('请选择要删除的记录！', '提示', {
                        confirmButtonText: '确定'
                    });
                    return;
                }

                this.$confirm('删除不可恢复，是否确定删除？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids = '';
                    for (let i = 0; i < count; i++) {
                        if(i > 0) {
                            ids += ',';
                        }
                        ids += this.multipleSelection[i].id;
                    }

                    deleteUser(ids).then((res) => {
                        for (let i = count - 1; i >= 0; i--) {
                            this.data.splice(i, 1);
                        }   
                        this.multipleSelection = [];
                        this.$refs.userTable.clearSelection();
                    })
                });
            },
            saveEdit() {
                if(this.currentRow.id) {
                    updateUser(this.currentRow).then((res) => {
                        this.$set(this.data, this.currentRowIndex, this.currentRow);
                        this.editVisible = false;
                    });
                } else {
                    saveUser(this.currentRow).then((res) => {
                        this.currentRow.id = res.data;//设置主键
                        this.data.unshift(this.currentRow);
                        this.editVisible = false;
                    });
                }
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
        }
    }

</script>