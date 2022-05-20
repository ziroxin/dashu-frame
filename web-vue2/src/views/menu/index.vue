<template>
  <div class="app-container">
    <el-row :gutter="24">
      <el-col :span="elColSpanValue">
        <!--  操作按钮  -->
        <el-button type="primary" icon="el-icon-edit" style="margin-bottom: 20px;" @click="permissionAdd">新增</el-button>
        <el-button type="primary" icon="el-icon-edit" style="margin-bottom: 20px;" @click="permissionUpdate">修改
        </el-button>
        <el-button type="danger" icon="el-icon-edit" style="margin-bottom: 20px;" @click="permissionDelete">删除
        </el-button>

        <!-- 表格部分 -->
        <el-table :data="tableData" row-key="permissionId" style="margin-bottom: 20px;" border
                  :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
                  @selection-change="selectionChangeHandlerOrder"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column prop="permissionTitle" label="资源标题" sortable width="190">
            <template v-slot="scope">
              {{ scope.row.permissionTitle }}
              <el-tag v-if="scope.row.permissionType === '0'" disable-transitions>路由</el-tag>
              <el-tag v-if="scope.row.permissionType === '1'" disable-transitions type="warning">按钮</el-tag>
              <el-tag v-if="scope.row.permissionType === '2'" disable-transitions type="success">外链</el-tag>
              <el-tag v-if="scope.row.permissionType === '3'" disable-transitions type="info">其他</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="permissionRouter" label="资源标记" sortable width="190" />
          <el-table-column prop="permissionComponent" label="组件地址" sortable width="200" />
          <el-table-column prop="permissionIcon" label="资源图标" sortable width="200" />
          <el-table-column fixed="right" label="操作" width="160">
            <template slot-scope="{row}">
              <el-button type="text" @click="openButtonTable(row)">按钮</el-button>
              <el-button type="text">接口</el-button>
              <el-button type="text" @click="subordinatesAdd(row)">添加下级</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页组件 -->
        <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit"
                    @pagination="getList"
        />
      </el-col>

      <el-col v-if="buttontableVisible" :span="10">
        <PermissionButton :button-table-data="ButtonTableData" />
      </el-col>
    </el-row>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :model="temp" label-position="left" label-width="100px"
               style="width: 500px; margin-left: 50px;"
      >
        <el-form-item label="资源名称：" prop="permissionName">
          <el-input v-model="temp.permissionName" />
        </el-form-item>

        <el-form-item label="资源描述：" prop="permissionDescription">
          <el-input v-model="temp.permissionDescription" />
        </el-form-item>

        <el-form-item label="资源类型：" prop="permissionType">
          <el-select v-model="temp.permissionType" placeholder="请选择" style="width: 400px;">
            <el-option v-for="item in permissionOptions" :key="item.value" :label="item.label"
                       :value="item.value"
            />
          </el-select>
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
        <el-button type="primary" @click="submitJudgment(dialogStatus)">提交
        </el-button>
      </div>

    </el-dialog>

  </div>
</template>
<script>
// 引入分页组件
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import {permissionAdd, permissionDelete, permissionList, permissionUpdate} from '@/api/permission.js'
// 引入图标选择器
import IconPicker from './component/IconPicker/index';
// 引入按钮组件
import PermissionButton from './component/permissionButton/index'

import {permissionTreeList} from '@/api/api';

export default {
  components: {Pagination, IconPicker, PermissionButton},
  data() {
    return {
      // 表格数据
      tableData: [],
      // 查询数据条数
      total: 0,
      // 分页属性
      listQuery: {page: 1, limit: 10},
      // 勾选的数据
      changeData: [],
      // 勾选的ID
      permissionIds: [],
      // 传给按钮组件的数据
      ButtonTableData: {},
      // 对话框属性
      dialogStatus: '',
      textMap: {update: '修改', create: '新增', subordinates: '添加下级'},
      // 对话框弹出控制
      dialogFormVisible: false,
      // 按钮表格显示控制
      buttontableVisible: false,
      elColSpanValue: 24,
      // 下拉选项
      permissionOptions: [
        {value: '0', label: '路由'},
        {value: '1', label: '页面资源'},
        {value: '2', label: '外链'},
        {value: '3', label: '其他'}
      ],
      temp: {}
    }
  },
  created() {
    this.getList();
    this.getPermissionTreeList()
    this.resetTemp();
  },
  methods: {
    async getPermissionTreeList() {
      this.listLoading = true
      const {data} = await permissionTreeList()
      this.tableData = data
      this.listLoading = false
    },
    resetTemp() {
      this.temp = {
        permissionId: '',
        parentId: '-1',
        permissionName: '',
        permissionDescription: '',
        permissionType: '',
        permissionTitle: '',
        permissionIcon: '',
        permissionRouter: '',
        permissionComponent: '',
        permissionIsShow: '1',
        permissionIsEnabled: '1',
        permissionOrder: 0
      }
    },
    // 查询表格数据
    getList() {
      permissionList().then(response => {
        console.log(response)
        this.tableData = response.data
        this.total = response.data.length
      })
    },
    // 查询图标名称
    getIconName(value) {
      this.temp.permissionIcon = value;
    },
    // 表格勾选
    selectionChangeHandlerOrder(val) {
      this.changeData = val
    },
    // 点击新增按钮后
    permissionAdd() {
      this.resetTemp()
      this.dialogFormVisible = true
      this.dialogStatus = 'create'
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 新增数据提交
    createData() {
      // 表单验证
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          permissionAdd(this.temp).then(response => {
            this.tableData.unshift(this.temp)
            this.dialogFormVisible = false
            if (response.data) {
              this.$message({
                type: 'success',
                message: '添加成功！'
              });
              this.getList();
              this.getPermissionTreeList()
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
    permissionUpdate() {
      if (this.changeData.length <= 0) {
        this.$message({
          message: '请选择一条数据进行修改！',
          type: 'warning'
        });
      } else if (this.changeData.length > 1) {
        this.$message({
          message: '修改时只允许选择一条数据！',
          type: 'warning'
        });
      } else {
        const changeData = this.changeData
        // Object.assign：把changeData[0]的值复制到集合{}
        this.temp = Object.assign({}, changeData[0])
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          // 清除表单验证
          this.$refs['dataForm'].clearValidate()
        })
      }
    },
    //  提交修改数据
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          permissionUpdate(this.temp).then(response => {
            //  遍历数据
            const index = this.tableData.findIndex(v => v.permissionId === this.temp.permissionId)
            //  替换数据
            this.tableData.splice(index, 1, this.temp)
            this.dialogFormVisible = false

            if (response.data) {
              this.$message({
                type: 'success',
                message: '修改成功！'
              });
              this.getList();
              this.getPermissionTreeList()
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
    // 数据删除
    permissionDelete() {
      if (this.changeData.length <= 0) {
        this.$message({
          message: '请选择一条数据进行删除！',
          type: 'warning'
        });
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
              this.getList();
              this.getPermissionTreeList()
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
        });
      }
    },
    // 添加下级
    subordinatesAdd(row) {
      this.resetTemp()
      this.temp.parentId = row.permissionId
      this.dialogFormVisible = true
      this.dialogStatus = 'subordinates'
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    // 对话框提交判断
    submitJudgment(dialogStatus) {
      if (dialogStatus === 'update') {
        this.updateData()
      }else{
        this.createData()
      }
    },
    openButtonTable(row) {
      this.buttontableVisible = true
      this.elColSpanValue = 14
      this.ButtonTableData = row
    }
  }
}
</script>
<style>
</style>
