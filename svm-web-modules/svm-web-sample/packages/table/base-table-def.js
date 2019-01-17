import DeptTreeDialog from "../components/dept-tree";
import dict from "../dict";
import { queryAllUsers, saveUser, updateUser, deleteUser } from '../api/user';

export default {
    name: 'SampleBaseTable',
    components: {
        DeptTreeDialog
    },
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
            deptVisible: false,
            sample_province: dict.sample_province,
            dialogFilter: {}
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
            queryAllUsers(this, this.currentPage).then((res) => {
                this.data = res.data.list;
                !this.totalRecords && (this.totalRecords = res.data.total);//只有第一次赋值
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
        doCreate() {
            this.resetCurrentRow();
            this.editVisible = true;
        },
        doEdit(index, row) {
            this.currentRowIndex = index;
            this.currentRow = Object.assign({}, row);
            this.editVisible = true;
        },
        doDelete(index, row) {
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

                deleteUser(this, ids).then((res) => {
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
                updateUser(this, this.currentRow).then((res) => {
                    this.$set(this.data, this.currentRowIndex, this.currentRow);
                    this.editVisible = false;
                });
            } else {
                saveUser(this, this.currentRow).then((res) => {
                    this.currentRow.id = res.data;//设置主键
                    console.log(this.currentRow.id);
                    this.data.unshift(this.currentRow);
                    this.editVisible = false;
                });
            }
        },
        doSelectionChange(val) {
            this.multipleSelection = val;
        },
        doOpenTab(index, row) {
            //按path路由时Tags里没法区分相同组件
            this.$router.push({name: 'BaseForm', params: {id: row.id}});
        },
        doOpenDialog(index, row) {
            this.dialogFilter = {userName: row.name};
            this.deptVisible = true;
        },
        doCloseDialog(data) {
            console.log(data);
            this.deptVisible = false;
        }
    }
}