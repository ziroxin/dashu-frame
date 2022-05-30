<template>
  <div class="app-container">
    <el-row>
      <el-col :span="9">
        <!--        角色管理按钮-->
        <div style="margin-bottom: 20px;">
          <el-button type="primary" plain icon="el-icon-edit" @click="roleAdd">新增</el-button>
          <el-button type="success" plain icon="el-icon-edit" @click="roleUpdate">修改</el-button>
          <el-button type="danger" icon="el-icon-delete" @click="roleDelete">删除</el-button>
        </div>
        <!--        角色管理表格-->
        <el-table ref="roleTable" :data="tableData" stripe style="width: 95%" border
                  @selection-change="handleTableSelectChange"
        >
          <el-table-column type="selection" width="50" align="center" header-align="center" />
          <el-table-column label="角色" align="center">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="right" :title="scope.row.roleName+'（'+scope.row.roleOrder+'）'"
                          :content="'描述：'+scope.row.roleDescription"
              >
                <span slot="reference" style="cursor: pointer;">{{ scope.row.roleName }}</span>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="100">
            <template v-slot="scope">
              <el-button type="text" size="small" @click="setMyApi(scope.row)">
                设置权限
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--        分页-->
        <el-pagination style="text-align: center;" background layout="total, pager"
                       :page-size="pager.limit" :current-page="pager.page"
                       :total="totalCount" @current-change="handleCurrentChange"
        />
        <!--        添加修改弹窗-->
        <el-dialog :title="titleMap[dialogType]" :visible.sync="dialogFormVisible">
          <el-form ref="roleDateForm" :model="temp" label-position="left" label-width="100px"
                   style="width: 500px; margin-left: 50px;"
          >
            <el-form-item label="角色名称" prop="roleName" :rules="{required: true, message: '角色名称不能为空'}">
              <el-input v-model="temp.roleName" />
            </el-form-item>
            <el-form-item label="角色描述" prop="roleDescription">
              <el-input v-model="temp.roleDescription" type="textarea" />
            </el-form-item>
            <el-form-item label="角色顺序" prop="roleOrder"
                          :rules="[{required: true, message: '角色顺序不能为空'},{type: 'number', message: '必须为数字'}]"
            >
              <el-input v-model.number="temp.roleOrder" />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="saveRoles">保存</el-button>
            <el-button @click="dialogFormVisible=false">取消</el-button>
          </div>
        </el-dialog>
      </el-col>
      <el-col :span="15" style="padding-left: 20px;border-left: 1px solid #dedede;">
        <!--        资源权限表格-->
        <div style="margin-bottom: 20px;">
          <el-button type="primary" :disabled="isSaveBtn" @click="saveRolePermission()">保存关联API</el-button>
          <el-button @click="toggleTableOprate">全部{{ isExpand ? '收起' : '展开' }}</el-button>
        </div>
        <el-table ref="permissionTable" style="width: 100%;" borderhighlight-current-row
                  :default-expand-all="isExpand" :data="tableData2" row-key="permissionId"
                  :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
                  @selection-change="handleTable2SelectChange"
        >
          <el-table-column type="selection" width="50" align="center" header-align="center" />
          <el-table-column label="路由/外链" sortable width="180px">
            <template v-slot="scope">
              {{ scope.row.permissionTitle }}
              <el-tag v-if="scope.row.permissionType === '0'" disable-transitions>路由</el-tag>
              <el-tag v-if="scope.row.permissionType === '2'" disable-transitions type="success">外链</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="按钮/其他" sortable>
            <template v-slot="scope">
              <el-checkbox-group v-model="selectPermissionApiList" style="line-height: 50px;">
                <el-checkbox v-for="perm in scope.row.buttonList" :key="perm.permissionId"
                             :label="perm.permissionId" border style="margin-left: 0px!important;"
                >
                  {{ perm.permissionTitle }}
                </el-checkbox>
              </el-checkbox-group>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {addRole, deleteRoles, getPermissionList, getRoleList, saveRolePermission, updateRole} from '@/api/role'

export default {
  data() {
    return {
      //角色表格数据
      pager: {page: 1, limit: 10},
      totalCount: 0,
      tableData: [],
      tableSelectRows: [],
      roleId: '',
      //添加修改弹窗
      titleMap: {add: '添加角色', update: '修改角色'},
      dialogType: '',
      dialogFormVisible: false,
      temp: {},
      //右侧资源列表数据
      tableData2: [],
      currentRow: 0,
      isExpand: true,
      selectPermissionApiList: [],
      selectPermissionApiList2: [],
      isSaveBtn: true
    }
  },
  created() {
    this.loadRoleList()
    this.loadPermissionTreeList()
    this.resetTemp()
  },
  methods: {
    //加载角色列表
    loadRoleList() {
      getRoleList(this.pager)
        .then((response) => {
          const {data} = response
          this.totalCount = data.total
          this.tableData = data.records
        })
    },
    //选中行
    handleTableSelectChange(rows) {
      this.tableSelectRows = rows
    },
    //分页
    handleCurrentChange(page) {
      this.pager.page = page
    },
    //清空temp数据
    resetTemp() {
      this.temp = {
        roleId: '',
        roleName: '',
        roleDescription: '',
        roleOrder: 1
      }
    },
    //角色管理
    roleAdd() {
      this.resetTemp()
      this.dialogFormVisible = true
      this.dialogType = 'add'
      this.$nextTick(() => {
        this.$refs['roleDateForm'].clearValidate()
      })
    },
    roleUpdate() {
      if (this.tableSelectRows.length <= 0) {
        this.$message({message: '请选择一条数据修改！', type: 'warning'})
      } else if (this.tableSelectRows.length > 1) {
        this.$message({message: '修改时，只允许选择一条数据！', type: 'warning'})
      } else {
        // 修改弹窗
        this.temp = Object.assign({}, this.tableSelectRows[0])
        this.dialogType = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['roleDateForm'].clearValidate()
        })
      }
    },
    roleDelete() {
      if (this.tableSelectRows.length <= 0) {
        this.$message({message: '请选择一条数据删除！', type: 'warning'})
      } else {
        this.$confirm('确定要删除吗?', '删除提醒', {
          confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
        }).then(() => {
          // 执行删除
          const roleIds = this.tableSelectRows.map(r => r.roleId)
          deleteRoles(roleIds).then(response => {
            const {code} = response
            if (code === '200') {
              this.$message({type: 'success', message: '删除成功！'})
              this.loadRoleList()
            } else {
              this.$message({type: 'error', message: '删除失败！'})
            }
          })
        })
      }
    },
    saveRoles() {
      this.$refs['roleDateForm'].validate((valid) => {
        if (valid) {
          if (this.dialogType === 'update') {
            updateRole(this.temp).then(response => {
              const {code} = response
              if (code === '200') {
                this.$message({type: 'success', message: '修改成功！'})
                this.loadRoleList()
                this.dialogFormVisible = false
              } else {
                this.$message({type: 'error', message: '修改失败！'})
              }
            })
          } else {
            addRole(this.temp).then(response => {
              const {code} = response
              if (code === '200') {
                this.$message({type: 'success', message: '添加成功！'})
                this.loadRoleList()
                this.dialogFormVisible = false
              } else {
                this.$message({type: 'error', message: '添加失败！'})
              }
            })
          }
        }
      })
    },
    //加载资源列表树
    loadPermissionTreeList() {
      let params = this.roleId === '' ? {} : {'roleId': this.roleId}
      this.$nextTick(() => {
        getPermissionList(params).then(response => {
          const {data} = response
          this.tableData2 = data
          this.$nextTick(() => {
            this.selectPermissionApiList = []
            this.toggleRowSelectionAll(this.tableData2)
          })
        })
      })
    },
    //展开和收起
    toggleTableOprate() {
      if (this.isExpand) {
        this.isExpand = false
      } else {
        this.isExpand = true
      }
      this.toggleRowExpansionAll(this.tableData2, this.isExpand)
    },
    toggleRowExpansionAll(data, isExpansion) {
      data.forEach((item) => {
        this.$refs.permissionTable.toggleRowExpansion(item, isExpansion)
        if (item.children !== undefined && item.children !== null) {
          this.toggleRowExpansionAll(item.children, isExpansion)
        }
      })
    },
    //设置权限按钮
    setMyApi(row) {
      this.$refs.roleTable.clearSelection()
      this.$refs.roleTable.toggleRowSelection(row, true)
      this.roleId = row.roleId
      this.loadPermissionTreeList()
    },
    toggleRowSelectionAll(data) {
      data.forEach(item => {
        //按钮列表
        item.buttonList.forEach(btn => {
          if (btn.hasPermission) {
            this.selectPermissionApiList.push(btn.permissionId)
          }
        })
        //权限列表
        this.$refs.permissionTable.toggleRowSelection(item, item.hasPermission)
        if (item.children !== undefined && item.children !== null) {
          this.toggleRowSelectionAll(item.children)
        }
      })
    },
    //权限选中行
    handleTable2SelectChange(rows) {
      this.selectPermissionApiList2 = []
      rows.forEach(row => {
        this.selectPermissionApiList2.push(row.permissionId)
      })
    },
    //保存角色权限
    saveRolePermission() {
      let pids = []
      pids.push(...this.selectPermissionApiList)
      pids.push(...this.selectPermissionApiList2)
      saveRolePermission({'roleId': this.roleId, 'permissionIds': pids}).then(response => {
        const {code} = response
        if (code === '200') {
          this.$notify({
            title: '保存成功',
            message: '保存权限成功！',
            type: 'success'
          })
        }
      })
    }
  }
}
</script>
