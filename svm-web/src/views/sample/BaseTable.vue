<template>
    <div>
        <div class="container">
            <el-table :data="data" border style="width: 100%" ref="userTable">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column prop="birthday" label="出生日期" sortable width="150"></el-table-column>
                <el-table-column prop="name" label="姓名" width="150"></el-table-column>
                <el-table-column prop="province" label="省" :formatter="decodeProvince" width="120"></el-table-column>
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

        <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="deleteRow">确 定</el-button>
                <el-button @click="delVisible = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import dict from "@/dict";
    import { queryAllUsers, updateUser, deleteUser } from '@/api/sample';

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
                delVisible: false,
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
            handleEdit(index, row) {
                this.currentRowIndex = index;
                this.currentRow = row;
                this.editVisible = true;
            },
            handleDelete(index, row) {
                this.currentRowIndex = index;
                this.currentRow = row;
                this.delVisible = true;
            },
            // 保存编辑
            saveEdit() {
                updateUser(this.currentRow).then((res) => {
                    this.$set(this.data, this.idx, this.currentRow);
                    this.editVisible = false;
                })
            },
            // 确定删除
            deleteRow() {
                var id = this.currentRow.id;
                deleteUser(id).then((res) => {
                    this.data.splice(this.idx, 1);
                    this.delVisible = false;
                })
            }
        }
    }

</script>

<style scoped>
    .del-dialog-cnt {
        font-size: 16px;
        text-align: center
    }
</style>
