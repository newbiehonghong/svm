<template>
    <el-dialog title="选择部门" visible width="50%" :before-close="closeDialog">
        <el-scrollbar>
            <el-tree ref="deptTree" :props="defaultProps" @node-click="doNodeClick" 
                lazy :load="doLoadNodes" style="height: 300px"></el-tree>
        </el-scrollbar>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="doConfirm">确 定</el-button>
            <el-button @click="closeDialog">取 消</el-button>
        </span>
    </el-dialog>
</template>

<script>
    import { queryAllDept } from '../api/dept';

    export default {
        name: 'SampleDeptTree',
        props: {
            filter: {type: Object, default: () => {}}
        },
        data() {
            return {
                data: null,
                defaultProps: {
                    label: 'name'
                }
            }
        },
        created() {
            //请求数据方法时异步的，数据没回来时树节点的load方法就被调用了，此时数据还是空的呢
            //this.getData();
        },
        methods: {
            doLoadNodes(node, resolve) {
                if(node.level == 0) {
                    queryAllDept(this).then((res) => {
                        this.data = res.data;
                        resolve(this.filterData("0"));
                    });
                } else {
                    resolve(this.filterData(node.data.id));
                }
            },
            filterData(parentId) {
                var rows = [];
                for(let i = this.data.length - 1; i >= 0; i--) {
                    if(this.data[i].parent == parentId) {
                        rows.unshift(this.data.splice(i, 1)[0]);//用unshift插入数组头部保证节点原始顺序
                    }
                }
                return rows;
            },
            doNodeClick(data) {
                //console.log(data);
            },
            doConfirm() {
                var node = this.$refs.deptTree.getCurrentNode();
                var label = node ? node.label : '';
                this.closeDialog(null, label);    
            },
            closeDialog(evt, data) {
                //此处需要通知父组件修改visible属性，不能直接调用this.visible = false，否则报错
                //[Vue warn]: Avoid mutating a prop directly since the value will be overwritten whenever the parent component re-renders. Instead, 
                //use a data or computed property based on the prop's value. Prop being mutated: "visible"
                //this.$emit('update:visible', false);
                this.$emit('close', data);
            }
        }
    }
</script>