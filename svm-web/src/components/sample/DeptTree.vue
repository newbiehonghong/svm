<template>
    <el-dialog title="选择部门" :visible="visible" width="50%" :before-close="closeDialog">
        <el-tree ref="deptTree" :data="data" :props="defaultProps" @node-click="doNodeClick"></el-tree>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="doConfirm">确 定</el-button>
            <el-button @click="closeDialog">取 消</el-button>
        </span>
    </el-dialog>
</template>

<script>
    export default {
        props: {
            filter: {type: Object, default: () => {}}
        },
        data() {
            return {
                data: [],
                defaultProps: {
                    children: 'children',
                    label: 'label'
                },
                visible: true
            }
        },
        created() {
            this.getData();
        },
        methods: {
            getData() {
                console.log(this.filter);//可以根据传入的属性过滤数据
                this.data = [{
                    label: 'Level one 1',
                    children: [{
                        label: 'Level two 1-1',
                        children: [{
                            label: 'Level three 1-1-1'
                        }]
                    }]
                }, {
                    label: 'Level one 2',
                    children: [{
                        label: 'Level two 2-1',
                        children: [{
                            label: 'Level three 2-1-1'
                        }]
                    }, {
                        label: 'Level two 2-2',
                        children: [{
                            label: 'Level three 2-2-1'
                        }]
                    }]
                }, {
                    label: 'Level one 3',
                    children: [{
                        label: 'Level two 3-1',
                            children: [{
                                label: 'Level three 3-1-1'
                            }]
                        }, {
                        label: 'Level two 3-2',
                        children: [{
                            label: 'Level three 3-2-1'
                        }]
                    }]
                }, {
                    label: 'Level one 4',
                    children: [{
                        label: 'Level two 4-1',
                            children: [{
                                label: 'Level three 4-1-1'
                            }]
                        }, {
                        label: 'Level two 4-2',
                        children: [{
                            label: 'Level three 4-2-1'
                        }]
                    }]
                }]
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