<template>
    <div>
        <div class="container">
            <div class="handle-box">
                <el-button class="button" @click="handleCreate" type="primary" icon="el-icon-edit">新增</el-button>
            </div>
            <el-table :data="data" border style="width: 100%" ref="userTable">
                <el-table-column prop="name" label="账号" width="150"></el-table-column>
                <el-table-column prop="nickname" label="姓名" width="150"></el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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
                <el-form-item label="账号">
                    <el-input v-model="currentRow.name"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="currentRow.nickname"></el-input>
                </el-form-item>
                <el-form-item label="密码" v-if="isCreate">
                    <el-input type="password" v-model="currentRow.password"></el-input>
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
    import { queryAllUsers, saveUser, updateUser, deleteUser } from '@/api/security';

    export default {
        name: 'BaseTable',
        data() {
            return {
                data: [],
                currentPage: 1,
                currentRow: {},
                currentRowIndex: -1,
                pageSize: 10,
                totalRecords: 0,
                editVisible: false,
                isCreate: false
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
            resetCurrentRow() {
                this.currentRow = {
                    "name": null,
                    "nickname": null,
                    "password": null
                };
            },
            handleCreate() {
                this.resetCurrentRow();
                this.isCreate = true;
                this.editVisible = true;
            },
            handleEdit(index, row) {
                this.currentRowIndex = index;
                this.currentRow = Object.assign({}, row);
                this.isCreate = false;
                this.editVisible = true;
            },
            handleDelete(index, row) {
                this.$confirm('删除不可恢复，是否确定删除？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteUser(row.id).then((res) => {
                        this.data.splice(index, 1);
                    })
                });
            },
            saveEdit() {
                if(this.isCreate) {
                    saveUser(this.currentRow).then((res) => {
                        this.data.unshift(this.currentRow);
                        this.editVisible = false;
                    });
                } else {
                    updateUser(this.currentRow).then((res) => {
                        this.$set(this.data, this.currentRowIndex, this.currentRow);
                        this.editVisible = false;
                    });
                }
            }
        }
    }

</script>
