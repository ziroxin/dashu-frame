<template>
  <div class="">
    <!--    操作按钮  -->
    <div style="margin: 10px 0px;text-align: center;">
      <el-button type="primary" icon="el-icon-plus" circle @click="permissionButtonAdd" />
      <el-button type="info" icon="el-icon-edit" circle @click="permissionButtonUpdate" />
      <el-button type="danger" icon="el-icon-delete" circle @click="permissionButtonDelete" />
      <el-button type="warning" icon="el-icon-close" circle @click="permissionButtonClose" />
    </div>
    <!--   表格部分 -->
    <el-table :data="tableData" style="margin-bottom: 20px;" border @selection-change="selectionChangeHandlerOrder">
      <el-table-column type="selection" width="55" header-align="center" align="center" />
      <el-table-column label="元素名称" header-align="center" align="center">
        <template slot-scope="{row}">
          <el-tooltip :key="'tip'+row.permissionId" placement="bottom">
            <div slot="content" :key="'tipcontent'+row.permissionId" style="line-height: 30px;">
              名称：{{ row.permissionTitle }}
              标签：{{ row.permissionName }}
              <br>描述：{{ row.permissionDescription }}
            </div>
            <div style="cursor: pointer;">
              {{ row.permissionTitle }}
            </div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column prop="permissionOrder" label="顺序" width="80" sortable align="center" />
    </el-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" :close-on-click-modal="false">
      <el-form ref="dataForm" :model="temp" :rules="rules" label-position="right" label-width="100px"
               style="width: 500px;margin-left: 50px;"
      >
        <el-form-item label="名称：" prop="permissionTitle">
          <el-input v-model="temp.permissionTitle" />
        </el-form-item>
        <el-form-item label="标记：" prop="permissionName">
          <el-input v-model="temp.permissionName" />
        </el-form-item>
        <el-form-item label="描述：" prop="permissionDescription">
          <el-input v-model="temp.permissionDescription" type="textarea" />
        </el-form-item>
        <el-form-item label="类型：" prop="permissionType">
          <el-radio v-model="temp.permissionType" label="1">按钮</el-radio>
          <el-radio v-model="temp.permissionType" label="3">其他</el-radio>
        </el-form-item>
        <el-form-item label="图标：" prop="permissionIcon">
          <IconPicker v-model="temp.permissionIcon" :icon="temp.permissionIcon" @iconName="getIconName" />
        </el-form-item>
        <el-form-item label="顺序：" prop="permissionOrder">
          <el-input v-model.number="temp.permissionOrder" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">保存</el-button>
        <el-button @click="dialogFormVisible=false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getListById, permissionAdd, permissionDelete, permissionUpdate} from '@/api/permission'
// 引入图标选择器
import IconPicker from '@/views/system/menu/IconPicker/index';

export default {
  name: 'PermissionButton',
  components: {IconPicker},
  props: ['currentPermissionId', 'closeButtonTable'],
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
      textMap: {update: '修改', create: '新增'},
      // 对话框弹出控制
      dialogFormVisible: false,
      // 接口表格显示控制
      apiTableVisible: false,
      temp: [],
      rules: {
        permissionName: [{required: true, message: '请填写标记', trigger: 'blur'}],
        permissionTitle: [{required: true, message: '请填写名称', trigger: 'blur'}],
        permissionOrder: [{required: true, message: '请填写顺序', trigger: 'blur'}, {type: 'number', message: '请填写数字'}]
      }
    }
  },
  watch: {
    //监听数据改变更新表格
    currentPermissionId() {
      this.getList()
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
      getListById(this.currentPermissionId).then(response => {
        this.listLoading = true
        this.tableData = response.data
        this.listLoading = false
      })
    },
    // 每一行的数据
    resetTemp() {
      this.temp = {
        permissionId: '',
        parentId: this.currentPermissionId,
        permissionName: '',
        permissionDescription: '',
        permissionType: '1',
        permissionTitle: '',
        permissionIcon: '',
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
      this.dialogFormVisible = true
      this.dialogStatus = 'create'
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 提交添加数据
    createData() {
      this.$refs['dataForm'].validate((valid) => {
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
          message: '请选择一条数据进行修改',
          type: 'warning'
        })
      } else if (this.changeData.length > 1) {
        this.$message({
          message: '修改时只允许选择一条数据',
          type: 'warning'
        })
      } else {
        const changeData = this.changeData
        this.temp = Object.assign({}, changeData[0])
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
      this.$refs['dataForm'].validate((valid) => {
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
    //删除按钮数据
    permissionButtonDelete() {
      if (this.changeData.length <= 0) {
        this.$message({
          message: '请选择一条数据进行删除!',
          type: 'warning'
        })
      } else {
        const changeData = this.changeData
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          for (var i = 0; i < changeData.length; i++) {
            this.permissionIds.push(changeData[i].permissionId)
          }
          permissionDelete(this.permissionIds).then(response => {
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
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        })
      }
    },
    // 关闭
    permissionButtonClose() {
      this.closeButtonTable()
    }
  }
}
</script>

<style scoped>

</style>
