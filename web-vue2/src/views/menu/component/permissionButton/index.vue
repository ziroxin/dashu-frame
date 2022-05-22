<template>
  <div>
    <!--    操作按钮  -->
    <el-button type="primary" icon="el-icon-edit" style="margin-bottom: 20px;" @click="permissionButtonAdd">新增</el-button>
    <el-button type="primary" icon="el-icon-edit" style="margin-bottom: 20px;" @click="permissionButtonUpdate">修改</el-button>
    <el-button type="danger" icon="el-icon-edit" style="margin-bottom: 20px;" @click="permissionButtonDelete">删除</el-button>

    <!--   表格部分 -->
    <el-table :data="tableData" style="margin-bottom: 20px;" border @selection-change="selectionChangeHandlerOrder">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="permissionTitle" label="按键名称" sortable />
      <el-table-column label="操作">
        <el-button type="text">接口</el-button>
      </el-table-column>
    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :model="temp" label-position="left" label-width="100px" style="width: 500px;margin-left: 50px;">

        <el-form-item label="资源名称：" prop="permissionName">
          <el-input v-model="temp.permissionName" />
        </el-form-item>

        <el-form-item label="资源描述：" prop="permissionName">
          <el-input v-model="temp.permissionDescription" />
        </el-form-item>

        <el-form-item label="资源类型：" prop="permissionType">
          <el-radio v-model="temp.permissionType" label="1">按钮</el-radio>
          <el-radio v-model="temp.permissionType" label="3">其他</el-radio>
        </el-form-item>

        <el-form-item label="资源标题：" prop="permissionTitle">
          <el-input v-model="temp.permissionTitle" />
        </el-form-item>

        <el-form-item label="资源图标：" prop="permissionComponent">
          <IconPicker v-model="temp.permissionIcon" :icon="temp.permissionIcon" @iconName="getIconName" />
        </el-form-item>

        <el-form-item label="资源标记：" prop="permissionRouter">
          <el-input v-model="temp.permissionRouter" />
        </el-form-item>

        <el-form-item label="组件地址：" prop="permissionComponent">
          <el-input v-model="temp.permissionComponent" />
        </el-form-item>

        <el-form-item label="资源顺序：" prop="permissionOrder">
          <el-input v-model="temp.permissionOrder" />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible=false">取消</el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">提交</el-button>
      </div>

    </el-dialog>
  </div>
</template>

<script>
import {getListById, permissionAdd, permissionDelete, permissionUpdate} from '@/api/permission'
// 引入图标选择器
import IconPicker from '@/views/menu/component/IconPicker/index';

export default {
  name: 'PermissionButton',
  components: {IconPicker},
  props: ['buttonTableData'],
  data() {
    return {
      tableData: [],
      // 查询数据条数
      total: 0,
      // 分页属性
      listQuery: {page: 1, limit: 10},
      // 勾选的数据
      changeData: [],
      // 勾选的ID
      permissionIds: [],
      // 对话框属性
      dialogStatus: '',
      textMap: {update: '修改', create: '新增', subordinates: '添加下级'},
      // 对话框弹出控制
      dialogFormVisible: false,
      temp:[]
    }
  },
  created() {
    this.getList()
    this.resetTemp();
  },
  methods: {
    // 表格勾选
    selectionChangeHandlerOrder(val) {
      this.changeData = val
    },
    // 查询数据
    getList() {
      getListById(this.ButtonTableData.permissionId).then(response => {
        this.listLoading = true
        this.tableData = response.data
        this.listLoading = false
      })
    },
    // 每一行的数据
    resetTemp() {
      this.temp = {
        permissionId: '',
        parentId: '-1',
        permissionName: '',
        permissionDescription: '',
        permissionType: '1',
        permissionTitle: '',
        permissionIcon: '',
        permissionRouter: '',
        permissionComponent: '',
        permissionIsShow: '1',
        permissionIsEnabled: '1',
        permissionOrder: 0
      }
    },
    // 查询图标名称
    getIconName(value) {
      this.temp.permissionIcon = value;
    },
  //  点击添加按钮后
    permissionButtonAdd() {
      this.resetTemp()
      this.temp.parentId = this.ButtonTableData.permissionId
      this.dialogFormVisible = true
      this.dialogStatus = 'create'
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 提交添加数据
    createData() {
      this.$refs['dataForm'].validate((valid) =>{
        if (valid) {
          permissionAdd(this.temp).then(response => {
            this.dialogFormVisible = false
            if (response.data) {
              this.$message({
                type: 'success',
                message: '添加成功！'
              });
              this.getList()
            } else {
              this.$message({
                type: 'error',
                message: '添加失败！'
              });
            }
          })
        }
      })
    },
    // 点击修改按钮后
    permissionButtonUpdate() {
      if (this.changeData.length <= 0) {
        this.$message({
          message:'请选择一条数据进行修改',
          type:'warning'
        })
      }else if (this.changeData.length > 1) {
        this.$message({
          message:'修改时只允许选择一条数据',
          type:'warning'
        })
      }else{
        const changeData = this.changeData
        this.temp = Object.assign({},changeData[0])
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          // 清除表单验证
          this.$refs['dataForm'].clearValidate()
        })
      }
    },
    // 提交修改数据
    updateData() {
      this.$refs['dataForm'].validate((valid) =>{
        if (valid) {
          permissionUpdate(this.temp).then(response => {
            this.dialogFormVisible = false
            if (response.data) {
              this.$message({
                type: 'success',
                message: '修改成功！'
              });
              this.getList()
            } else {
              this.$message({
                type: 'error',
                message: '修改失败！'
              });
            }
          })
        }
      })
    },
    permissionButtonDelete() {
      if (this.changeData.length <= 0) {
        this.$message({
          message:'请选择一条数据进行删除!',
          type:'warning'
        })
      }else{
        const changeData = this.changeData
        this.$confirm('此操作将永久删除该文件, 是否继续?','提示',{
          confirmButtonText:'确定',
          cancelButtonText:'取消',
          type:'warning'
        }).then(() =>{
          for (var i = 0; i < changeData.length; i++) {
            this.permissionIds.push(changeData[i].permissionId)
          }
          permissionDelete(this.permissionIds).then(response =>{
            if (response.data) {
              this.$message({
                type: 'success',
                message: '删除成功！'
              });
              this.getList()
            } else {
              this.$message({
                type: 'error',
                message: '删除失败！'
              });
            }
          })
        }).catch(() =>{
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
