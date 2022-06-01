<template>
  <div class="app-container">
    <el-row :gutter="24">
      <el-col :span="elColSpanValue">
        <!--  操作按钮  -->
        <div style="margin-bottom: 20px;">
          <el-button type="primary" icon="el-icon-plus" @click="permissionAdd">新增
          </el-button>
          <el-button type="info" icon="el-icon-edit" @click="permissionUpdate">修改
          </el-button>
          <el-button type="danger" icon="el-icon-delete" @click="permissionDelete">删除
          </el-button>
          <el-button type="warning" icon="el-icon-sort" @click="permissionUpdateParent">修改上下级
          </el-button>
        </div>
        <!-- 表格部分 -->
        <el-table :data="tableData" row-key="permissionId" style="margin-bottom: 20px;" border
                  :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
                  @selection-change="selectionChangeHandlerOrder">
          <el-table-column type="selection" width="50" header-align="center" align="center"/>
          <el-table-column prop="permissionTitle" label="菜单名称" sortable>
            <template v-slot="scope">
              <li class="menu-item">
                <item :icon="scope.row.permissionIcon" :title="scope.row.permissionTitle"/>
                <el-tag v-if="scope.row.permissionType === '0'" disable-transitions>路由</el-tag>
                <el-tag v-if="scope.row.permissionType === '2'" disable-transitions type="success">外链</el-tag>
              </li>
            </template>
          </el-table-column>
          <el-table-column prop="permissionName" label="菜单标记" sortable/>
          <el-table-column prop="permissionRouter" label="菜单地址" sortable/>
          <el-table-column prop="permissionComponent" label="组件" sortable/>
          <el-table-column fixed="right" label="操作" width="160" header-align="center" align="center">
            <template slot-scope="{row}">
              <el-button type="text" @click="subordinatesAdd(row)">添加下级</el-button>
              <el-button type="text" @click="openButtonTable(row)">按钮</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col v-if="buttonTableVisible" :span="8" :style="rightStyle">
        <PermissionButton :current-permission-id="currentPermissionId" :close-button-table="closeButtonTable"/>
      </el-col>
    </el-row>

    <!--    添加和修改窗口-->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :model="temp" :rules="rules" label-position="right" label-width="100px"
               style="width: 500px; margin-left: 50px;">
        <el-form-item label="菜单类型：" prop="permissionType">
          <el-radio v-model="temp.permissionType" label="0" @change="toggleRouterShow">路由</el-radio>
          <el-radio v-model="temp.permissionType" label="2" @change="toggleRouterShow">外链</el-radio>
        </el-form-item>
        <el-form-item label="菜单名称：" prop="permissionTitle">
          <el-input v-model="temp.permissionTitle"/>
        </el-form-item>
        <el-form-item v-if="routerShow" label="菜单标记：" prop="permissionName">
          <el-input v-model="temp.permissionName"/>
        </el-form-item>
        <el-form-item label="菜单描述：" prop="permissionDescription">
          <el-input v-model="temp.permissionDescription" type="textarea"/>
        </el-form-item>
        <el-form-item label="菜单图标：" prop="permissionIcon">
          <IconPicker v-model="temp.permissionIcon" :icon="temp.permissionIcon" @iconName="getIconName"/>
        </el-form-item>
        <el-form-item label="菜单地址：" prop="permissionRouter">
          <el-input v-model="temp.permissionRouter"/>
        </el-form-item>
        <el-form-item v-if="routerShow" label="组件地址：" prop="permissionComponent">
          <el-input v-model="temp.permissionComponent"/>
        </el-form-item>
        <el-form-item label="菜单顺序：" prop="permissionOrder">
          <el-input v-model.number="temp.permissionOrder" :permission-order="temp.permissionOrder"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitJudgment(dialogStatus)">保存</el-button>
        <el-button @click="dialogFormVisible=false">取消</el-button>
      </div>
    </el-dialog>
    <!--    修改上下级菜单-->
    <el-dialog :visible.sync="parentDialogVisible" width="400px">
      <el-form ref="parentDataForm" :model="temp2" :rules="rules2" label-position="right" label-width="130px">
        <el-divider content-position="center">请选择父级菜单</el-divider>
        <el-tree :data="tableData" :props="{children: 'children',label: 'permissionTitle'}"
                 @node-click="handleNodeClick"></el-tree>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPermissionParent()">保存</el-button>
        <el-button @click="parentDialogVisible=false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  permissionAdd,
  permissionDelete,
  permissionList,
  permissionTreeList,
  permissionUpdate
} from '@/api/permission.js'
// 引入图标选择器
import IconPicker from '@/views/system/menu/IconPicker/index';
// 引入按钮组件
import PermissionButton from '@/views/system/menu/permissionButton/index'
// 菜单项目
import Item from '@/layout/components/Sidebar/Item';

export default {
  components: {IconPicker, PermissionButton, Item},
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
      currentPermissionId: '',
      // 对话框属性
      dialogStatus: '',
      textMap: {update: '修改', create: '新增', subordinates: '添加下级'},
      // 对话框弹出控制
      dialogFormVisible: false,
      // 按钮表格显示控制
      buttonTableVisible: false,
      // <el-col>的span值
      elColSpanValue: 24,
      temp: {},
      rules: {
        permissionTitle: [{required: true, message: '请填写菜单名称', trigger: 'blur'}],
        permissionName: [{required: true, message: '请填写菜单标记', trigger: 'blur'}],
        permissionRouter: [{required: true, message: '请填写路由地址', trigger: 'blur'}],
        permissionComponent: [{required: true, message: '请填写组件地址', trigger: 'blur'}],
        permissionOrder: [{required: true, message: '请填写菜单顺序', trigger: 'blur'}, {type: 'number', message: '请填写数字'}]
      },
      routerShow: true,
      // 修改上下级关系表单
      temp2: {},
      rules2: {},
      parentDialogVisible: false
    }
  },
  computed: {
    rightStyle() {
      return 'background-color:#f6f6f6;border-radius:5px;height:' + (window.innerHeight - 125) + 'px;'
    }
  },
  created() {
    this.getPermissionTreeList()
    this.resetTemp();
  },
  methods: {
    toggleRouterShow(val) {
      this.routerShow = val === '0'
    },
    //查询下拉树
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
        permissionType: '0',
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
    // 修改上下级关系
    permissionUpdateParent() {
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
        this.temp2 = Object.assign({}, changeData[0])
        this.parentDialogVisible = true
        this.$nextTick(() => {
          // 清除表单验证
          this.$refs['parentDataForm'].clearValidate()
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
      if (row.permissionType === '1') {
        this.$message({
          message: '按钮无法添加下级！',
          type: 'warning'
        })
      } else {
        this.resetTemp()
        this.temp.parentId = row.permissionId
        this.dialogFormVisible = true
        this.dialogStatus = 'subordinates'
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      }
    },
    // 对话框提交判断
    submitJudgment(dialogStatus) {
      if (dialogStatus === 'update') {
        this.updateData()
      } else {
        this.createData()
      }
    },
    //点击[按钮]，打开右侧按钮面板
    openButtonTable(row) {
      this.buttonTableVisible = true
      this.elColSpanValue = 16
      this.currentPermissionId = row.permissionId
    },
    //关闭右侧按钮面板
    closeButtonTable() {
      this.buttonTableVisible = false
      this.elColSpanValue = 24
    },
    handleNodeClick(node) {
      console.dir(node)
    }
  }
}
</script>
<style>
.menu-item {
  list-style: none;
  display: inline;
}
.menu-item > span {
  padding: 0px 5px !important;
}
</style>
